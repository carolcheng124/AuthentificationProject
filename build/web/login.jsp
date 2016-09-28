<%-- 
    Document   : Login Module
    Created on : Apr 4, 2016, 11:10:07 AM
    Author     : Siddharth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Login Page</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" />
    <link href="css/login.css" rel="stylesheet"/>

</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<div class="form-group"></div>
			<form name="loginForm" class="form-horizontal login" method="post">

				<div class="form-group">

					<label class="col-sm-2 control-label">User Name</label>

					<div class="col-sm-4">
						<input type="text" class="form-control" id="username"
							name="username" required placeholder="Enter Username Name">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">Password</label>

					<div class="col-sm-4">
						<input type="password" class="form-control" id="password"
							name="password" required placeholder="Enter Password">
					</div>
				</div>
				<div class="form-group col-sm-2 control-label" >
				<h5 id="wrongCrendials"></h5>
				</div>
				<div class="form-group col-sm-2 control-label">
					<button type="button" class="btn btn-default" onclick="authenticate();">Submit</button>
				</div>
			</form>
			<form class="form-horizontal login">
				<div class="form-group col-sm-2 control-label">
                <a type="button" href="forgotpassword"  class="btn btn-default">Forgot Password</a>
				</div>
			</form>
                        <div id="registerDiv">
                            <br/>
                            <br/>
                           New user? Register <a href="registration.jsp">here</a>.
                        </div>
		</div>
	</div>
	<!-- FOOTER -->
	<div class="container marketing">

		<footer>
			<p>
				&copy; 2016 E-Commerce Security &middot; <a href="#">Privacy</a>
				&middot; <a href="#">Terms</a>
			</p>
		</footer>

	</div>
	<!-- /.container -->
	<script>
        function validateForm() {
        var x = document.forms["loginForm"]["username"].value;
        var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if(!re.test(x)){
        	document.getElementById("wrongCrendials").innerHTML = "Enter A Correct Email Address";
        	return false;
        	}
        return true;
        }
        
        
		function authenticate() {
			if(!validateForm()){
				document.getElementById("wrongCrendials").innerHTML = "Please Enter A Valid Email ID";
			}else{
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
			    	if (xhttp.readyState == 4 && xhttp.status == 200) {
                                    var response = xhttp.responseText;
                                    if( response.toString()==="Success"){
                                        
                                            window.location = "index.jsp";
                                    }else{
                                        document.getElementById("wrongCrendials").innerHTML = response;   
                                    }  		
			    	}
			  	};
			  	var username = document.forms["loginForm"]["username"].value;
			  	var password = document.forms["loginForm"]["password"].value;
				xhttp.open("POST", "AuthenticationController", true);
				xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
				xhttp.send("username="+username+"&password="+password);	
			}
		}
	</script>
	<script
		src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>
</body>
</html>