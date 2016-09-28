/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dataAccessObject.ActivityLogDao;
import dataAccessObject.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.IPAddress;
import model.UserAccountInfo;

/**
 *This servlet is to change the roleID
 * @author Carol
 */
public class RoleManageServlet extends HttpServlet {

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
        
         //log role update activity
        ActivityLogDao logDao = new ActivityLogDao();
        IPAddress ipAddress = new IPAddress();        
        //get client ip addr and request URI for activity log
        String sysSource = request.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(request);
        
        
        System.out.println("==in role manage servlet==");
        
        String message = "";
        Boolean isSuccess = false;
        
         System.out.println("userEmail: " +request.getParameter("userEmail"));
         System.out.println("roleChoice" + Integer.parseInt(request.getParameter("roleChoice")));
         
         //get the id of current user from session
         UserAccountInfo user = new UserAccountInfo();
         user =(UserAccountInfo) request.getSession().getAttribute("user");
         int currentID = user.getId();
         
         //get the id for whose role is going to change
         String email = request.getParameter("userEmail");
         UserDao userDao = new UserDao();
         int id = userDao.getUserIDByEmail(email);
        
         //check whether the current user is changing his own role
         if(currentID == id){
            message = "You cannot change your own role!";
         }else if(id > 0) {
             //user exist
            isSuccess = user.roleUpdate(id, Integer.parseInt(request.getParameter("roleChoice")));
            logDao.logUpdateActivity(ipAddr, sysSource, "role update", id);
            if(isSuccess == true){
                message = "Congratulations! You cange the role successfully!";
            }else{
                message = "Sorry! You didn't change the role successfully!";
            }
         }else if(id == 0) {
             //user does not exist
            logDao.logUpdateActivity(ipAddr, sysSource, "role update failed: user doesn't exist", id);
            message ="Sorry! The user dosen't exist";
         }
         
         request.setAttribute("message", message);
         
        
          //forward server's request to jsp
        getServletContext().getRequestDispatcher("/roleManage.jsp").forward(request, response);  
        
//         response.setContentType("text/html;charset=UTF-8");
//         try (PrintWriter out = response.getWriter()) {
//         /* TODO output your page here. You may use following sample code. */
//         out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
//         out.println("</body>");
//         out.println("</html>");
         
          
         
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
