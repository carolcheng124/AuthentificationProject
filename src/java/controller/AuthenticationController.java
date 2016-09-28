package controller;

import dataAccessObject.ActivityLogDao;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dataAccessObject.AuthenticationDao;
import dataAccessObject.HostileDao;
import dataAccessObject.UserDao;
import javax.servlet.RequestDispatcher;
import model.IPAddress;
import model.UserAccountInfo;

public class AuthenticationController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static Integer MAX_NUMBER_OF_ATTEMPTS = 3;
    ActivityLogDao logDao = new ActivityLogDao();

    public AuthenticationController() {
        // TODO Auto-generated constructor stub
        super();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        AuthenticationDao auth = new AuthenticationDao();
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String passwd = req.getParameter("password");

        Integer lastAttempt = (Integer) session.getAttribute("lastAttempt");

        System.out.println("Number of login attempts :" + lastAttempt);

        //get client ip addr and request URI for activity log
        IPAddress ipAddress = new IPAddress();
        String sysSource = req.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(req);

        if (lastAttempt == null) {
            session.setAttribute("lastAttempt", 1);
        } else if (lastAttempt >= MAX_NUMBER_OF_ATTEMPTS) {
            // Redirect servlet to Hostile module
            System.out.println("Number of login attempts :" + lastAttempt);

            System.out.println("Redirect to Hostile Module");
            HostileDao hostileDao = new HostileDao();
            hostileDao.WriteHostileToDB(lastAttempt, ipAddr, sysSource);
        }

        Integer account_id = auth.validateUser(username, passwd);
        if (account_id != null && account_id != -1 && account_id != -2 && account_id != -3) {
            //log activity of succeed logon
            logDao.logSucceedLogon(ipAddr, sysSource, account_id);

            // User authenticated successfully
            // User account ID has been retrieved and
            // can used in session attribute to track for access control module
            System.out.println("Logged In Successfully");
            // remove lastAttempt from session object
            session.removeAttribute("lastAttempt");
            // add account_id into session for access control
            session.setAttribute("account_id", account_id);
            
            
            //retrieve user bean by userID, and set it in session
            //by Hanwei
            UserDao dao = new UserDao();
            UserAccountInfo loginUser = new UserAccountInfo();
            loginUser = dao.retrieveUserInfo(account_id);
            System.out.println("==retrieve user email by account_id: " + loginUser.getEmailAddress());
            session.setAttribute("user", loginUser);
            resp.setContentType("text/xml");  
            out.write("Success");
            //end 

        } else {
            //log failed login attempt according to the returned value of validateUser                    
            switch (account_id) {
                case -1:
                    logDao.logPwFailedLoginAttempt(ipAddr, sysSource,"(login)unmatch password", account_id);
                    break;
                case -2:
                    logDao.logEmailFailedLoginAttempt(ipAddr, sysSource, username);
                    break;
                case -3:
                    logDao.logPwFailedLoginAttempt(ipAddr, sysSource, "(login)non exist password",account_id);
                    break;
                default:
                    break;
            }

            // authentication failed
            out.println("Username Or Password Is Incorrect");
            
            if (lastAttempt == null) {
                lastAttempt = 1; //if first failed login attempt
                session.setAttribute("lastAttempt", lastAttempt);
            } else {
                session.setAttribute("lastAttempt", lastAttempt + 1);
            }
            out.close();
            out.flush();
        }
    }
}