 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DbConnect.DbConnection;
import dataAccessObject.ActivityLogDao;
import dataAccessObject.HostileDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import model.IPAddress;

import model.ResetPasswordObj;

/**
 *
 * @author shao dai
 */
@WebServlet(name = "securityquestions", urlPatterns = {"/securityquestions"})
public class GetQuestions extends HttpServlet {

    
    private final int MAX_EMAIL_ATTEMPTS = 2;
    private final String SYSTEM_SOURCE = "EmailForm";
    ActivityLogDao logDao = new ActivityLogDao();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        int emailAttempts = 0;
        String emailString = "";
        IPAddress ipAddress = new IPAddress();
        
        if (session == null) { //No session yet
            session = request.getSession();
            session.setAttribute("emailAttempts", emailAttempts);
            session.setAttribute("emailString", emailString);
        } else { //Session already created
            if (session.getAttribute("emailAttempts") != null) {
                emailAttempts = (Integer)session.getAttribute("emailAttempts");
            } 
            if (session.getAttribute("emailString") != null) {
                emailString = (String)session.getAttribute("emailString");
            }
        }  
        
        //get client ip addr and request URI for activity log
            String sysSource = request.getRequestURI();
            String ipAddr = ipAddress.getClientIpAddress(request);
            
        
        if(emailAttempts > MAX_EMAIL_ATTEMPTS) {
            HostileDao hostileDao = new HostileDao();
            hostileDao.WriteHostileToDB(emailAttempts, ipAddr, SYSTEM_SOURCE);
        } else {
            String email = request.getParameter("email");
            //Check if email exists
            //if not exists -> redirect back to forgotpassword.jsp
            //if exists -> get questions -> redirect with questions and question ids
            String account_info_id = getIDFromEmailDB(email);

            if (account_info_id.equals("")) { //if no email match
                emailAttempts++;
                emailString += email + ":" + SYSTEM_SOURCE + ";";
                
                //log email failed activity on forgot pw form
                logDao.logEmailFailedOnForgotPw(ipAddr, sysSource, email);
                session.setAttribute("emailAttempts", emailAttempts);       
                session.setAttribute("emailString", emailString);
                
                response.sendRedirect("forgotpassword");
            } else {
                String previousEmail = "";
                
                if(session.getAttribute("previousEmail") != null) {
                    previousEmail = session.getAttribute("previousEmail").toString();
                }
                
                if (session.getAttribute("resetPasswordObj") == null || !email.equals(previousEmail)) {
                    String[] questions = getQuestionsFromIDDB(account_info_id);
                    if (questions != null) {
                        // reset the session on success
                        session.invalidate();
                        session = request.getSession();
                        ResetPasswordObj resetPasswordObj = new ResetPasswordObj(account_info_id, questions[0], questions[1]);
                        session.setAttribute("previousEmail", email);
                        session.setAttribute("resetPasswordObj", resetPasswordObj);
                        request.setAttribute("question_string", questions[1]);
                        RequestDispatcher rd = request.getRequestDispatcher("questions");
                        rd.forward(request, response);
                    } else {
                        //error
                    }
                } else {
                    ResetPasswordObj resetPasswordObj = (ResetPasswordObj)session.getAttribute("resetPasswordObj");
                    session.setAttribute("resetPasswordObj", resetPasswordObj);
                    request.setAttribute("question_string", resetPasswordObj.securityQuestion);
                    RequestDispatcher rd = request.getRequestDispatcher("questions");
                    rd.forward(request, response);
                }
                
            }  
        }
    
    }
    
    protected String getIDFromEmailDB(String email) {
        Connection connection;
        PreparedStatement preparedStatement;
        
        try {
            connection = DbConnection.getConnection();
            String selectSQL = "SELECT id FROM INFSCI2731.account_info WHERE email_addr = ?";
            preparedStatement = connection.prepareStatement(selectSQL);  
            preparedStatement.setString(1, email);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if (rs.next()) {
                return rs.getString("id"); 
            } else {
                return "";
            }
  
        } catch (SQLException e) {
                e.printStackTrace();
                return "";
        }   
    }
    
    protected String[] getQuestionsFromIDDB(String account_info_id) {
        Connection connection;
        PreparedStatement preparedStatement;
        
        try {
            connection = DbConnection.getConnection();
            String selectSQL = "SELECT security_question.question, security_question_answer.id FROM security_question, security_question_answer WHERE security_question_answer.security_question_id = security_question.id AND security_question_answer.account_info_id = ? ORDER BY RAND() LIMIT 0,1;";
            preparedStatement = connection.prepareStatement(selectSQL);  
            preparedStatement.setString(1, account_info_id);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            boolean val = rs.next();
            if (!val) {
                return null;
            } else {
                String[] questions = new String[2];
                questions[0] = rs.getString("security_question_answer.id");
                questions[1] = rs.getString("security_question.question");
                return questions;
            }
           
  
        } catch (SQLException e) {
                e.printStackTrace();
                return null;
        } 
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
