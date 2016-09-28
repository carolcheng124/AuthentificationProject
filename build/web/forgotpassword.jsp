<%-- 
    Document   : resetpassword.jsp
    Created on : Mar 31, 2016, 10:32:00 PM
    Author     : shao dai
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password page</title>
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
                <h2>Reset Password</h2>
                <% if (session.getAttribute("emailAttempts") != null && (Integer) session.getAttribute("emailAttempts") > 0) { %>
                <div class="alert alert-warning">That username does not exist.</div>
                <% }%>
                <form name="resetform-email" method="POST" action="GetQuestions">
                    <label>Enter your email:  <input name="email" type="email" autofocus/> </label>
                        <input type="submit" value="Submit" />
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
