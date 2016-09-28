/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccessObject.ActivityLogDao;
import dataAccessObject.UserDao;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.QuestionAnswer;
import model.Authentication;
import model.IPAddress;
import model.UserAccountInfo;
/**
 *This class is the controller to deal with user information in signup page
 * @author Hanwei Cheng
 */
@WebServlet(name = "AccountInfoServlet", urlPatterns = {"/AccountInfoServlet"})
public class RegistrationServlet extends HttpServlet {

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
        System.out.println("==in servlet==");
        UserAccountInfo newUser = new UserAccountInfo();
     
        System.out.println("==in servlet==" + "firstname: "+ request.getParameter("firstname")+" lastname: "+request.getParameter("lastname"));
//        request.setAttribute("user", user); 

        IPAddress ipAddress = new IPAddress();
//        String sysSource = request.getRequestURL().toString();
        String sysSource = request.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(request);
        ActivityLogDao logDao = new ActivityLogDao();

        //check input
        String str="";
        String firstName = request.getParameter("firstname").trim();
        firstName = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase();
        String lastName = request.getParameter("lastname").trim();       
        lastName = lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
        String email = request.getParameter("email").trim().toLowerCase();       
        String password = request.getParameter("password").trim();
        String retypepassword = request.getParameter("retypepassword").trim();
        int q1 = Integer.parseInt(request.getParameter("secQue1"));
        int q2 = Integer.parseInt(request.getParameter("secQue2"));
        int q3 = Integer.parseInt(request.getParameter("secQue3"));
             
        
            if(!firstName.matches("[a-zA-Z]+")) {
                str += "First name may only contain letters. ";
            }
            if(!lastName.matches("[a-zA-Z]+")) {
                str += "Last name may only contain letters. ";
            }
             //check email availability  
            UserDao userDao = new UserDao();
            if(userDao.checkIfEmailExist(email) >=1) {
                str += "Email address is not available. ";
            }
            //check if entered pws equal to each other
            if(!password.equals(retypepassword)) {
                str += "Password doesn't match. ";
            }                      
            //check if security questions are same
            if(!(q1 != q2 && q1 != q3 && q2 != q3)) {
                str += "Securiry questions cannot be the same. ";
            }
        
            if(!str.equals("")) {
                //
                logDao.logAccessAttempt(ipAddr, sysSource, "registration err: " + str);
                request.setAttribute("msg", str + "please try again.");
                RequestDispatcher rd = request.getRequestDispatcher("/registration.jsp");
                rd.forward(request, response);                 
                
            }else {
                
                //create a normal user
                int accountId = newUser.register(firstName, lastName, email, 1); //modified by Siwei in order to get the new generated account id

                //create a Super Admin user
        //        int accountId = newUser.register(request.getParameter("firstname"), request.getParameter("lastname"), request.getParameter("email"), 3);


                //create a session and put user object in session
                HttpSession session = request.getSession();
                session.setAttribute("user", newUser);
                //debug
                UserAccountInfo testUser = (UserAccountInfo)session.getAttribute("user");
                System.out.println("==test user bean in session ==" +testUser.getEmailAddress());


                //log activity of new created account, by Siwei                
                logDao.logNewAccountCreated(ipAddr, sysSource, accountId);


                //process the password and store it in database
                Authentication auth = new Authentication();
                auth.passwordProcess(accountId, request.getParameter("password"));

                //set security Question &answer 1 
                QuestionAnswer qa1 = new QuestionAnswer();
                qa1.questionAnswerProcess(accountId, Integer.parseInt(request.getParameter("secQue1")), request.getParameter("answer1").toLowerCase());

                //set security question &answer 2
                QuestionAnswer qa2 = new QuestionAnswer();
                qa2.questionAnswerProcess(accountId, Integer.parseInt(request.getParameter("secQue2")), request.getParameter("answer2").toLowerCase());


                //set security question 3
                QuestionAnswer qa3 = new QuestionAnswer();
                qa3.questionAnswerProcess(accountId, Integer.parseInt(request.getParameter("secQue3")), request.getParameter("answer3").toLowerCase());

                //forward server's request to jsp
                getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);   
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
