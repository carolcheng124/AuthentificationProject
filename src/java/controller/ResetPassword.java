/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccessObject.ActivityLogDao;
import utilities.CheckDateTime;
import dataAccessObject.NonceDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.IPAddress;
import model.Nonce;
import model.UserAccountInfo;

/**
 *
 * @author shao dai
 */
@WebServlet(name = "ResetPassword", urlPatterns = {"/resetpassword"})
public class ResetPassword extends HttpServlet {
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
        
        HttpSession session = request.getSession();
        String nonce = request.getParameter("token");
        boolean passwordMismatch = Boolean.parseBoolean(request.getParameter("pmm"));
        boolean passwordUsedBefore = Boolean.parseBoolean(request.getParameter("pub"));
        Timestamp timestamp;
        NonceDao nonceDao = new NonceDao();
        ActivityLogDao logDao = new ActivityLogDao();
        IPAddress ipAddress = new IPAddress();
        
        //get client ip addr and request URI for activity log
        String sysSource = request.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(request);
        
        
        UserAccountInfo loginUser = null;
        if (session.getAttribute("user") != null) {
            loginUser = (UserAccountInfo)session.getAttribute("user");
        }
        
        
        if (nonce != null && !nonce.equals("")) {
            timestamp = nonceDao.getNonceCreatetime(nonce);
            if (CheckDateTime.isValid(timestamp)) {
                printPasswordForm(request, response, nonce, passwordMismatch, passwordUsedBefore);
            } else {
                printExpired(request, response);
            }
        } else if (loginUser != null) {
            printPasswordForm(request, response, "userLogged", passwordMismatch, passwordUsedBefore);
        }
        
        //log activity of expired link on reset pw, and update previoud reset pw record description
        Nonce n = nonceDao.getNonceByNonceValue(nonce);
        int logID = logDao.logForgotPwResult(ipAddr, sysSource, "(forgot pw)expired reset pw link", n.getAccountInfoID());
        //check if previous sent link record exist, if it does, update the record description
        int id = logDao.checkResetPwSentLink(n.getAccountInfoID());
        if(logID > 0 && id > 0){
            logDao.updatePreResetPwRecord("expired(logid " + logID + ")", id);           
        }
               
        
        response.sendRedirect("error");
        
    } 
    
    protected void printPasswordForm(HttpServletRequest request, HttpServletResponse response, String nonce, boolean passwordMismatch, boolean passwordUsedBefore) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Reset Password</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<nav class=\"navbar navbar-default\">\n" +
                "            <div class=\"container\">\n" +
                "                <div class=\"navbar-header\">\n" +
                "                    <a class=\"navbar-brand\" href=\"login.jsp\">IS2731</a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </nav>");
            out.println("<div class=\"container\"><div class=\"jumbotron\">");
            out.println("<h2>ResetPassword</h2>");
            out.println("<form name=\"resetform-password\" method=\"POST\" action=\"resetpasswordservice\">");
            out.println("<div class=\"alert alert-info\">Passwords must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters</div>");
            if (passwordMismatch) {
                out.println("<div class=\"alert alert-warning\">Your passwords do not match, please re-enter your new password.</div>");
            }
            if (passwordUsedBefore) {
                out.println("<div class=\"alert alert-warning\">Choose a password you haven't previously used with this account.</div>");
            }
            out.println("<input type=\"hidden\" name=\"token\" value=\"" + nonce +  "\">");
            out.println("Enter your new password: <input name=\"password\" type=\"password\" autofocus pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters\"/>");
            out.println("<br/><br/>");
            out.println("Confirm your new password: <input name=\"confirm_password\" type=\"password\" pattern=\"(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}\" title=\"Must contain at least one number and one uppercase and lowercase letter, and at least 8 or more characters\"/><br/><br/><input type=\"submit\"></form>");
            out.println("</div></div>");
            out.println("<footer class=\"footer\">\n" +
                "            <div class=\"container\">\n" +
                "                <p class=\"text-muted\">&copy; 2016 E-Commerce Security &middot; <a href=\"#\">Privacy</a>\n" +
                "                    &middot; <a href=\"#\">Terms</a></p>\n" +
                "            </div>\n" +
                "        </footer>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    protected void printExpired(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>\n" +
                        "    <head>\n" +
                        "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                        "        <title>Error</title>\n" +
                        "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css\" />\n" +
                        "    </head>\n" +
                        "    <body>\n" +
                        "        <nav class=\"navbar navbar-default\">\n" +
                        "            <div class=\"container\">\n" +
                        "                <div class=\"navbar-header\">\n" +
                        "                    <a class=\"navbar-brand\" href=\"login.jsp\">IS2731</a>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </nav>\n" +
                        "        <div class=\"container\">\n" +
                        "            <div class=\"jumbotron\">\n" +
                        "                <h2>Oops!</h2>\n" +
                        "                    <div class=\"alert alert-warning\">The requested link has expired.</div>\n" +
                        "                    <div><a href=\"login.jsp\">Click here</a> to return to the login page.</div>\n" +
                        "            </div>\n" +
                        "        </div>\n" +
                        "        <footer class=\"footer\">\n" +
                        "            <div class=\"container\">\n" +
                        "                <p class=\"text-muted\">&copy; 2016 E-Commerce Security &middot; <a href=\"#\">Privacy</a>\n" +
                        "                    &middot; <a href=\"#\">Terms</a></p>\n" +
                        "            </div>\n" +
                        "        </footer>\n" +
                        "    </body>\n" +
                        "</html>");
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
