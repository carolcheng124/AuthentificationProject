<%-- 
    Document   : RBACtest3
    Created on : April 19, 2016, 7:38:49 PM
    Author     : Hanwei Cheng
--%>


<%@page import="dataAccessObject.UserDao"%>
<%@page import="model.IPAddress"%>
<%@page import="dataAccessObject.ActivityLogDao"%>
<%@page import="model.UserAccountInfo"%>
<%@page import="java.util.List"%>
<%@ page import="controller.RBAC" %> 
<%@ page import="dataAccessObject.RBACDao" %> 

<%
    //log RBAC activity
        ActivityLogDao logDao = new ActivityLogDao();
        IPAddress ipAddress = new IPAddress();        
        //get client ip addr and request URI for activity log
        String sysSource = request.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(request);
    //check whether the role ID of the user has priviledge for current page
    if (request.getSession().getAttribute("user") != null) {
        UserAccountInfo user = (UserAccountInfo) session.getAttribute("user");
        RBACDao accessControl = new RBACDao();
        List<Integer> UserPool = accessControl.getRolebyPath("roleManage.jsp");
        if (!UserPool.contains(user.getAccess_role_id())) {
            //log access denied activity
            logDao.logRBPCheck(ipAddr, sysSource, "RBAC access denied to roleManage.jsp", user.getId());
            response.sendRedirect("index.jsp");
        }
        //log access successfully activity
        logDao.logRBPCheck(ipAddr, sysSource, "RBAC access successfully to roleManage.jsp", user.getId());
    } else {
        //log no session activity
        logDao.logAccessAttempt(ipAddr, sysSource, "no session attribute user set up, access denied to roleManage.jsp");
        response.sendRedirect("login.jsp");
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>test3</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

    </head>
    <body>

        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">IS2731</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li><a href="index.jsp">Home</a></li>
                        <li><a href="hostilelist.jsp">Hostile</a></li>
                        <li><a href="activitylog.jsp">Activity Log</a></li>
                        <li class="active"><a href="roleManage.jsp">Role Management</a></li>
                        <li><a href="admin.jsp">Admin Page</a></li>
                        <li><a href="RBACtest.jsp">RBAC Test</a></li>
                    </ul>
                    <ul class="nav navbar-nav navbar-right">
                        <!--put the name on the navigation bar-->
                        <br>
                        <%                            if (request.getSession().getAttribute("user") != null) {
                                UserAccountInfo user = (UserAccountInfo) session.getAttribute("user");
                                out.print("<a href='#'>" + user.getFirstName() + user.getLastName() + "</a>");
                                out.print("&nbsp;&nbsp;|&nbsp;&nbsp;");
                                out.print("<a href='LogOut'>Log Out</a>");
                            }
                        %>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </nav>


        <!-- Begin page content -->
        <div class="container" style="margin-top:100px;margin-bottom:250px;">

            <div class="page-header">
                <h1>Role Management</h1>
            </div>
            

            <!--management area-->
            <div class="ManagementArea" style="margin-top:30px">

                <form class="form-inline" action="RoleManageServlet" method="post">
                    <div class="form-group">
                        <label for="userEmail">User Email</label>
                        <input name="userEmail" id="userEmail" type="text" class="form-control"  placeholder="john@example.com" maxlength="254" onkeyup="checkUserEmail(); return false;" required>
                    </div>

                    <select name="roleChoice" class="form-control">
                        <option value="1">User</option>
                        <option value ="2">Administrator</option>
                    </select>

                    <button type="submit" class="btn btn-default">Change</button>
                </form>
                <div class="message">
                    <!--show message when change role successfully-->
                    <%
                        if(request.getAttribute("message")!= null && !request.getAttribute("message").equals("")){    
                               out.print(request.getAttribute("message"));
                        }
                    %>
                    <div id="errEmailMsg" style="color: red;"></div>
                   
                </div>
            </div><!--management area-->


        </div><!--container-->

        <footer class="footer">
            <div class="container">
                <p class="text-muted">&copy; 2016 E-Commerce Security &middot; <a href="#">Privacy</a>
                    &middot; <a href="#">Terms</a></p>
            </div>
        </footer>


        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous">
        </script>
        <script src="js/formCheck.js" type="text/javascript"></script>

    </body>
</html>
