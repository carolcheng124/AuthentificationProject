/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DbConnect.DbConnection;
import dataAccessObject.ActivityLogDao;
import dataAccessObject.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.IPAddress;

/**
 *
 * @author Siwei Jiao
 */
@WebServlet(name = "CheckEmailAvailability", urlPatterns = {"/CheckEmailAvailability"})
public class CheckEmailAvailability extends HttpServlet {

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
              
       String email = request.getParameter("email").trim();
       
       UserDao userDao = new UserDao();
       int count = userDao.checkIfEmailExist(email);
       
       //get client ip addr and request URI for activity log
        String sysSource = request.getRequestURI();
        IPAddress ipAddress = new IPAddress();
        String ipAddr = ipAddress.getClientIpAddress(request);
        ActivityLogDao logDao = new ActivityLogDao();
             
       if(count >= 1 ){
           //log the activity to track if user try to get info about registered email addresses
           logDao.logAccessAttempt(ipAddr, sysSource, "check email availability: taken email " + email);
            response.setContentType("text/plain");
            response.getWriter().write("notAvailable"); 
        }else if(count == 0 ) {
           logDao.logAccessAttempt(ipAddr, sysSource, "check email availability: available email " + email);
            response.setContentType("text/plain");
            response.getWriter().write("available");                 
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
