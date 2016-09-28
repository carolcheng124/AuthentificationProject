<%-- 
    Document   : Account Creation Module
    Created on : Apr 11, 2016, 11:10:07 AM
    Author     : Hanwei
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="dataAccessObject.SecurityQuestionDao"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">

		<title>Registration</title>
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
		
		<style>
		.container{
			padding:20px;
			text-align: center;
		}
		.form-group{
			margin:10px 0px !important;
		}
		.container marketing{
		
		}
		.row{
			margin:0px;
		}
		.input-group{
			float:left !important;
			margin:10px 0px;
		}
		#register{
			width:100%;
			margin:20px auto;
			
		}
                .errMsg {
                    color: red;
                }
                .avaliable {
                    color: green;
                }
                .checking {
                    color: blue;
                }
		</style>

	</head>

	<body>
		 
            <%
                SecurityQuestionDao sd = new SecurityQuestionDao();
                ResultSet rs = sd.getSecurityQuestions();
                
                ArrayList seqIds = new ArrayList();
                ArrayList seqStrings = new ArrayList();
                if (rs != null) {
                    while(rs.next()){
                        seqIds.add(rs.getInt("id"));
                        seqStrings.add(rs.getString("question"));
                    }
                }
            %>
		<!-- container -->
	    <div class="container">
			<!--inner container-->
	    	<div class="jumbotron col-md-8 col-md-offset-2">

		    	<h3 class="form-signin-heading">Registration</h3>
		    	<hr>
                        <div class="errMsg">
                            <%
                                    if(null!=request.getAttribute("msg")) {
                                        out.println(request.getAttribute("msg"));
                                    }
                                %>  
                        </div>
	    		<!-- <div class="col-md-6 col-md-offset-3"style="padding:0"> -->
	    		<!--form-->
                <jsp:useBean id="user" scope="request" class="model.UserAccountInfo"></jsp:useBean>
		    	<form class="form-horizontal" method="post" action="RegistrationServlet" onsubmit="return validateRegisterForm()" >
		    		<!--Name-->
		    		<div class="row">
		    			<!--firstname-->
			    		<div class="col-md-6 input-group" style="padding-right:2px">
				        	<span class="input-group-addon" id="firstName">First Name</span>
					        <input type="text" name="firstname"id="firstname" class="form-control" aria-describedby="basic-addon1" maxlength="30" onkeyup="checkFirstName(); return false;" required/>
                                        </div>
						
					<!--lastname-->
				      	<div class="col-md-6 input-group" style="padding-left:2px">
				        	<span class="input-group-addon" id="lastName">Last Name</span>
					        <input type="text" name="lastname" id="lastname" class="form-control" placeholder="" aria-describedby="basic-addon1" maxlength="30" onkeyup="checkLastName(); return false;" required/>
				      	</div>
				</div> <!-- row -->
                                <div class="row">
                                    <div id="errFname" class="errMsg col-md-6 "></div>
                                    <div id="errLname" class="errMsg col-md-6 "></div>
                                    
                                </div>

			      	<!--email-->
			      	<div class="input-group">
                                        <span class="input-group-addon" id="basic-addon1">Email</span>
                                        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="john@gmail.com" autofocus aria-describedby="basic-addon1" maxlength="254" onkeyup="checkEmail(); return false;" required>
					</div>
                                        <div id="emailMsg"></div>

				<!--password-->
				<div class="input-group">
					<span class="input-group-addon" id="basic-addon1">Password</span>
					<input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password"  value="" onkeyup="checkStrongOfPassword(); return false;" required>
				</div>
                                <div id="errStrongLevel" class="errMsg"></div>
                                
                                <!--upgrade role-->
                                <div class="input-group">
					<span class="input-group-addon" id="basic-addon1">Re-type Password</span>
					<input type="password" id="inputRetypePassword" name="retypepassword" class="form-control" placeholder="Password" value="" onkeyup="comfirmRetypePassword(); return false;" required>
				</div>
                                        <div id="errRetypePw" class="errMsg"></div>
                                        

                                

					<!--Security Question 1-->
					<div>
					<label class="control-label">Security Question 1</label>
						<select id="Question1" name="secQue1" class="form-control" onblur="checkSecQuestionNotSame(); return false;" required>
                                                <%
                                                    for(int i = 0; i < seqIds.size(); i++) {
                                                        %>
                                                        <option value="<%=seqIds.get(i)%>"><%=seqStrings.get(i)%></option>
                                                        <%
                                                    }
                                                %>
						</select>
					</div>

					<!-- answer 1-->
					<div class="form-group">
						<label class="control-label">Answer 1:</label>
						<input type="text" name="answer1" class="form-control" placeholder="Input your answer for question 1" required >
					</div>



					<!--Security Question 2-->
					<div class="dropdown">
					<label class="control-label">Security Question 2</label>
						<select id="Question2" name="secQue2" class="form-control" onblur="checkSecQuestionNotSame(); return false;" required>
							<%
                                                    for(int i = 0; i < seqIds.size(); i++) {
                                                        %>
                                                        <option value="<%=seqIds.get(i)%>"><%=seqStrings.get(i)%></option>
                                                        <%
                                                    }
                                                %>
						</select>
					</div>
					
					<!-- answer 2-->
					<div class="form-group">
						<label class="control-label">Answer 2:</label>
						<input type="text" name="answer2" class="form-control" placeholder="Input your answer for question 2" required>
					</div>

					
					<!--Security Question 3-->
					<div class="dropdown">
					<label class="control-label">Security Question 3</label>
						<select id="Question3" name="secQue3" class="form-control" onblur="checkSecQuestionNotSame(); return false;" required>
							<%
                                                    for(int i = 0; i < seqIds.size(); i++) {
                                                        %>
                                                        <option value="<%=seqIds.get(i)%>"><%=seqStrings.get(i)%></option>
                                                        <%
                                                    }
                                                %>
						</select>
					</div>

					<!-- answer 3-->
					<div class="form-group">
						<label class="control-label">Answer 3:</label>
						<input type="text" name="answer3" class="form-control" placeholder="Input your answer for question 3"  required>
					</div>
                                        
                                        <div id="errQuestionChooseSame" class="errMsg"></div>
					
					<!--submit button-->
					<div id="register">
						<input name="submit" class="btn btn-lg btn-default btn-block" type="submit" value="Submit"/>
                                                <div id="errForm" class="errMsg"></div>

					</div>
		    	</form>

		    	 <!-- FOOTER -->
				<div class="container marketing">

					<footer>
						<p>
							&copy; 2016 E-Commerce Security &middot; <a href="#">Privacy</a>
							&middot; <a href="#">Terms</a>
						</p>
					</footer>

				</div> <!-- FOOTER -->
		    	
		    </div><!--jom-->
			
                    
		   

	    </div><!--container-->
            <!--testing-->
<!--                  firstname:<--jsp:getProperty name="user" property="firstName"/>
                    lastname:<--jsp:getProperty name="user" property="lastName"/> 
                    email:<--jsp:getProperty name="user" property="emailAddress"/>
                    role: <--jsp:getProperty name="user" property="access_role_id"/>
		-->
	<!--jQuery (necessary for Bootstrap's JavaScript plugins) -->
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous">
    	</script>
        <script src="js/formCheck.js" type="text/javascript"></script>

	</body>
</html>
