/* 
 *  @author Siwei Jiao
 */


var checkEmail_true = 0;
var checkSecQuestionNotSame_true=0;
var checkStrongOfPassword_true=0;
var comfirmRetypePassword_true=0;

function checkUserEmail() {
    var email = $.trim($("#userEmail").val());
    $("#errEmailMsg").text("");
    $("#userEmail").parent().removeClass("has-error");
    
    if (email.length >= 1) {
        var pattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!pattern.test(email)) {
            $("#errEmailMsg").html("Please enter a valid email address.");           
            $("#userEmail").parent().addClass("has-error");
            
        } 
    } else {
        $("#userEmail").parent().addClass("has-error");       
        $("#errEmailMsg").html("Email address cannot be empty.");
    }
}

function checkFirstName() {
    var fname = $.trim($("#firstname").val());
    if (fname.length >= 1) {
        if (/[^a-zA-Z]/.test(fname)) {
            $("#errFname").html("First name may only contain letters and max length is 30 charaters without spaces.");
             $("#firstname").parent().addClass("has-error"); 
        } else {
             $("#firstname").parent().removeClass("has-error");
             $("#errFname").text("");
        }
    } else {
            $("#firstname").parent().addClass("has-error");                      
            $("#errFname").html("First name cannot be empty."); 
    }
}


function checkLastName() {
    var lname = $.trim($("#lastname").val());
    if (lname.length >= 1) {
        if (/[^a-zA-Z]/.test(lname)) {
            $("#errLname").html("Last name may only contain letters and max length is 30 charaters without spaces.");
             $("#lastname").parent().addClass("has-error");                      
        } else {
             $("#lastname").parent().removeClass("has-error");
             $("#errLname").text("");
        }
    } else {
            $("#lastname").parent().addClass("has-error");                      
            $("#errLname").html("Last name cannot be empty.");                 
    }
}

/**
 * check availability of user entered email 
 * @returns {undefined}
 */
function checkEmail() {
    var email = $.trim($("#inputEmail").val());
    $("#emailMsg").removeClass("errMsg");
    $("#emailMsg").removeClass("avaliable");
    $("#inputEmail").parent().removeClass("has-error");
   
    if (email.length >= 1) {

        var pattern = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (!pattern.test(email)) {
            $("#emailMsg").html("Please enter a valid email address.");
            $("#emailMsg").addClass("errMsg");
            $("#inputEmail").parent().addClass("has-error");
            $('input[type="submit"]').attr('disabled','disabled');
            // add a logic:  first make the submit button unavaliable, if all the function have be check ok, make the submit avaliable
            // return 0 means this function has error, not to make the submit button avaliable.
            return 0;
        } else {
            $("#inputEmail").parent().removeClass("has-error");
            $("#emailMsg").text("");
            $("#emailMsg").html("<span class=\"checking\">Checking email availability...</span>");
            $.ajax({
                type: "POST",
                url: "CheckEmailAvailability",
                data: "email=" + email,
                success: function (data) {

                    if (data === "notAvailable") {
                        $("#emailMsg").html("Sorry, " + email + " is not available.");
                        $("#emailMsg").addClass("errMsg");
                        $("#inputEmail").parent().addClass("has-error");
                        //$('input[type="submit"]').attr('disabled','disabled');
                        return 0;
                    } else if (data === "available") {
                        $("#emailMsg").html(email + " is available.");
                        $("#emailMsg").addClass("avaliable");
                        //if other 3 check whether input has error function return true, we should make the submit button avaliable because this fuction also is true.
                        checkEmail_true = 1;
                        if(checkEmail_true&&checkSecQuestionNotSame_true&&checkStrongOfPassword_true&&comfirmRetypePassword_true){
                            //remove the disable attribute of submit
                             $('input[type="submit"]').removeAttr('disabled');
                        }
                        
                        // return 1 means all the function avaliavle
                        return 1;
                      
                    }
                }
            });
        }
    } else {
        $("#inputEmail").parent().addClass("has-error");
        $("#emailMsg").addClass("errMsg");
        $("#emailMsg").html("Email address cannot be empty.");
//        $('input[type="submit"]').attr('disabled','disabled');
        return 0;
    }
}

/**
 * check if retyped password match the first entry
 * @returns {undefined}
 */
function comfirmRetypePassword() {
    var inputPassword = $("#inputPassword").val();
    var inputRetypePassword = $("#inputRetypePassword").val();
    $("#errRetypePw").parent().removeClass("has-error");
    $("#errRetypePw").text("");
   

    if ($("#inputPassword").parent().hasClass("has-error") || inputPassword === "") {
        $("#inputRetypePassword").parent().addClass("has-error");
        $("#errRetypePw").html("Please enter a valid password first!");
        return 0;
    } else {
        if(inputRetypePassword.length >=1) {
            if (inputRetypePassword === inputPassword) {
                $("#inputRetypePassword").parent().removeClass("has-error");
                $("#errRetypePw").text("");
                comfirmRetypePassword_true=1;
                if(checkEmail_true&&checkSecQuestionNotSame_true&&checkStrongOfPassword_true&&comfirmRetypePassword_true){
                            //remove the disable attribute of submit
                             $('input[type="submit"]').removeAttr('disabled');
                        }
                return 1;
           
            } else {
                $("#inputRetypePassword").parent().addClass("has-error");
                $("#errRetypePw").text("Password doesn't match!");
//                $('input[type="submit"]').attr('disabled','disabled');
                return 0;
            }           
        }else {
            $("#inputRetypePassword").parent().addClass("has-error");
            $("#errRetypePw").text("Please type your password again.");
//            $('input[type="submit"]').attr('disabled','disabled');
            return 0;
            
        }

    }
}


//////////////////////////////////////////////////////////////////////
// get whether the password is strong enough 
//////////////////////////////////////////////////////////////////////
function checkStrongOfPassword() {
    var inputPassword = $("#inputPassword").val();
    var modes = 0;
    $("#inputPassword").parent().removeClass("has-error");
    $("#errStrongLevel").text("");
    if (inputPassword.length < 8)
    {
        $("#inputPassword").parent().addClass("has-error");
        $("#errStrongLevel").html("Password must have 8 characters or more!");
//         $('input[type="submit"]').attr('disabled','disabled');
        return 0;
    }
    if (/\d/.test(inputPassword))
        modes++; //number
    if (/[a-z]/.test(inputPassword))
        modes++; //lowercase
    if (/[A-Z]/.test(inputPassword))
        modes++; //uppercase  
    if (/\W/.test(inputPassword))
        modes++; //special char
    
    

    switch (modes) {
        case 0:
            $("#inputPassword").parent().addClass("has-error");
            $("#errStrongLevel").html("Password must have 8 characters or more!");
            $('input[type="submit"]').attr('disabled','disabled');
            return 0;
        case 1:
        case 2:
            $("#inputPassword").parent().addClass("has-error");
            $("#errStrongLevel").html("Password must have 3 types of Uppercase, Lowercase, number, Special Character");
            $('input[type="submit"]').attr('disabled','disabled');
            return 0;
            break;
            
        case 3:
        case 4:
            $("#inputPassword").parent().removeClass("has-error");
            $("#errStrongLevel").text("");
            checkStrongOfPassword_true=1;
            if(checkEmail_true&&checkSecQuestionNotSame_true&&checkStrongOfPassword_true&&comfirmRetypePassword_true){
                            //remove the disable attribute of submit
                             $('input[type="submit"]').removeAttr('disabled');
                        }
         
            return 1;
            break;

    }
}


//////////////////////////////////////////////////////////////////////
// check whether the security question is the same in signup.jsp
//////////////////////////////////////////////////////////////////////
function checkSecQuestionNotSame(){

      var SecQ1 =  $("#Question1").val();
      var SecQ2 =  $("#Question2").val();
      var SecQ3 =  $("#Question3").val();
      
      if((SecQ1 == SecQ2) || (SecQ2 == SecQ3) || (SecQ1 == SecQ3))
      {
           $("#Question3").addClass("has-error");
            $("#errQuestionChooseSame").html("Security Question could not be the same!");
            $('input[type="submit"]').attr('disabled','disabled');
            return 0;
      }
      else{
          $("#Question3").removeClass("has-error");
            $("#errQuestionChooseSame").text("");
            checkSecQuestionNotSame_true=1;
            if(checkEmail_true&&checkSecQuestionNotSame_true&&checkStrongOfPassword_true&&comfirmRetypePassword_true){
                            //remove the disable attribute of submit
                             $('input[type="submit"]').removeAttr('disabled');
                        }

            return 1;
      }
      
}


function validateRegisterForm() {    
    checkFirstName();
    checkLastName();
    checkEmail();
    checkStrongOfPassword();
    comfirmRetypePassword();
    checkSecQuestionNotSame();
    
          
    if($("#firstname").parent().hasClass("has-error") || $("#lastname").parent().hasClass("has-error") 
        || $("#inputEmail").parent().hasClass("has-error") || $("#inputPassword").parent().hasClass("has-error")
        || $("#inputRetypePassword").parent().hasClass("has-error") || $("#Question1").parent().hasClass("has-error") 
        || $("#Question2").parent().hasClass("has-error") || $("#Question3").parent().hasClass("has-error")) {
            $("#errForm").text("Please correct this form first.");                
            return false;
    }else {
         return true;
    }
    
}


/////////////////////
///add function to disable the submit button, enable it only all the problem is solved
$(document).ready(function() {
//    if ( $('#abc').length ) //If checking if the element exists, use .length
//        alert("yes");
//    $('input[type="submit"]').attr('disabled','disabled');
});