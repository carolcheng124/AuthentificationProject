<%-- 
    Document   : index
    Created on : Mar 30, 2016, 7:38:49 PM
    Author     : Siwei Jiao
    More content by: Hanwei Cheng
--%>


<%@page import="model.IPAddress"%>
<%@page import="dataAccessObject.ActivityLogDao"%>
<%@page import="model.UserAccountInfo"%>
<%@page import="java.util.List"%>
<%@ page import="controller.RBAC" %> 
<%@ page import="dataAccessObject.RBACDao" %> 

<%
    //log access denied activity
        ActivityLogDao logDao = new ActivityLogDao();
        IPAddress ipAddress = new IPAddress();        
        //get client ip addr and request URI for activity log
        String sysSource = request.getRequestURI();
        String ipAddr = ipAddress.getClientIpAddress(request);
    //check whether the role ID of the user has priviledge for current page
    if(request.getSession().getAttribute("user") == null){
        //log no session activity
        logDao.logAccessAttempt(ipAddr, sysSource, "no session attribute user set up, access denied to index.jsp");
            response.sendRedirect("login.jsp");
        }
        
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Index</title>
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
            <li class="active"><a href="index.jsp">Home</a></li>
            <li><a href="hostilelist.jsp">Hostile</a></li>
            <li><a href="activitylog.jsp">Activity Log</a></li>
            <li><a href="roleManage.jsp">Role Management</a></li>
            <li><a href="admin.jsp">Admin Page</a></li>
            <li><a href="RBACtest.jsp">RBAC Test</a></li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
<!--            <li><a href="LogOut">Log Out</a></li>-->
            <!--<li id="user" value= "><a href="#"><span class="glyphicon glyphicon-user"></span></a></li>-->
            <!--put the name on the navigation bar-->
                <br>
                        <% 
                                if(request.getSession().getAttribute("user")!=null){
                                   UserAccountInfo user = (UserAccountInfo)session.getAttribute("user");
                                 out.print("<a href='profile.jsp'>" + user.getFirstName() + " " +user.getLastName() +"</a>");
                                 out.print("&nbsp;&nbsp;|&nbsp;&nbsp;");
                                 out.print("<a href='LogOut'>Log Out</a>");
                                }
                        %>
                  
            </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
   
               

    
    <!-- Begin page content -->
    <div class="container" style="margin-top:100px; margin-bottom:250px;">
      <div class="page-header">
        <h1>Authentication Project</h1>
      </div>
    </div>

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

    </body>
</html>
