<%-- 
    Document   : questions
    Created on : Apr 2, 2016, 5:36:23 PM
    Author     : shao dai
--%>

<%@page import="dataAccessObject.ActivityLogDao"%>
<%@page import="model.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    ActivityLogDao logDao = new ActivityLogDao();
    IPAddress ipAddress = new IPAddress();
    String sysSource = request.getRequestURI();
    String ipAddr = ipAddress.getClientIpAddress(request);
    if (session.getAttribute("resetPasswordObj") == null) {
        //log accesss attempt to this page without session attribute resetPasswordObj set
        logDao.logAccessAttempt(ipAddr, sysSource, "no session attribute resetPasswordObj set up, access denied to qustions.jsp");

        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Security Questions</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    </head>
    <body>
        <nav class="navbar navbar-default">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="login.jsp">IS2731</a>
                </div>
            </div>
        </nav>
        <div class="container">
            <div class="jumbotron">
                <h2>Security Questions</h2>
                    <div class="alert alert-info">Please answer this security question to continue.</div>
                    <% if (session.getAttribute("questionAttempts") != null && (Integer) session.getAttribute("questionAttempts") > 0) { %>
                    <div class="alert alert-warning">Your supplied answer does not match our records</div>
                    <% }%>
                    <form name="resetform-question" method="POST" action="AnswerQuestions">
                        <div>${requestScope.question_string}</div><br/>
                        <input name="security_answer" placeholder="Your answer" autofocus/>
                        <input type="submit">
                    </form>
            </div>
        </div>
        <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy; 2016 E-Commerce Security &middot; <a href="#">Privacy</a>
                    &middot; <a href="#">Terms</a></p>
            </div>
        </footer>
    </body>
</html>
