<%@page import="org.apache.commons.lang3.StringUtils" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
      <!-- Compiled and minified CSS -->
      <link rel="stylesheet" href="lib/materialize.min.css">

      <!-- Compiled and minified JavaScript -->
      <script src="lib/materialize.min.js"></script>
       <script src="./lib/sweetalert.min.js"></script>
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<style>
.loader {
        border: 3px solid #f3f3f3;
        border-radius: 50%;
        border-top: 3px solid #3498db;
        width: 60px;
        height: 60px;
        -webkit-animation: spin 1s linear infinite; 
        animation: spin 1s linear infinite;
      }
      
   
      @-webkit-keyframes spin {
        0% { -webkit-transform: rotate(0deg); }
        100% { -webkit-transform: rotate(360deg); }
      }
      
      @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
      }


/* The Modal (background) */
.modal1 {
  display: none; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 1; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */
.modal-content1 {
  background-color: #fefefe;
  margin: auto;
  margin-top: 10%;
  padding: 20px;
  border: 1px solid #888;
  width: 32%;
  height:32%;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  margin-left: 97%;
  margin-top: -3%;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
</style>

</head>
<body style="background-image: url(./images/properback.png);background-repeat: no-repeat;background-size: cover;">
        <!-- Page Content goes here -->
        <div class="conatiner">
<div class="row">
<div class="col m4 offset-m4">
<div class="card">
<div class="card-content" style="margin-top:20%;border: 1px solid white; background-color: rgb(158, 158, 240);">
    <h5 class="center-align" style="font-weight: bold;color: black;" >Admin Login</h5>
    <div class="form">
<form method="POST"  id="adminLogin" enctype="multipart/form-data">
<%
String message =(String) session.getAttribute("logoutStatus");

if(StringUtils.isNotBlank(message) && StringUtils.equalsIgnoreCase(message,"success")){

%>
 <label style="color:green; font-weight: bold;  font-size: 15px;margin-bottom:10%">Log Out Successfully</label>
<%
session.removeAttribute("logoutStatus");
}
%>
<br>
    <label for="first_name" style="color:black; font-weight: bold;  font-size: 15px;">Enter Your Name<strong class="text-danger"style="color:red;">*</strong></label>
    <input placeholder="Enter Your Name" id="first_name" name="studentName" type="text" class="validate">
    <label for="first_name" style="color:black; font-weight: bold;  font-size: 15px;">Enter Your Email Id<strong class="text-danger"style="color:red;">*</strong></label>
    <input placeholder="Enter Your Name" id="first_name" name="emailId" type="text" class="validate">
   <label for="student_id" style="color:black;font-weight: bold;  font-size: 15px;">Choose Security Question<strong class="text-danger"style="color:red;">*</strong></label>
  <select class="browser-default" name="securityQuestion" style="margin-bottom: 2%;" id="Survey_Qs">
    <option>--Select--</option>
    <option>Your Favourite Color</option>
    <option>Your  Favourite Food</option>
    <option>Your Home Town</option>
    <option>Your Favourite Game</option>
    <option>Your Pet Name</option>
    <option>Your Hobby</option>
    <option>Your Date Of Birth</option>
    <option>Your Year Of Birth</option>
</select>
<label for="securityQuestionAnswer" style="color:black;font-weight: bold;font-size: 15px;">Enter Security Question's Answer<strong class="text-danger"style="color:red;">*</strong></label>
<input placeholder="Enter Security Question's Answer" name="securityQuestionAnswer" id="securityQuestionAnswer" type="text" class="validate">
<label for="first_name" style="color:black; font-weight: bold;font-size: 15px;">Password<strong class="text-danger" style="color:red;">*</strong></label>
<input placeholder="Enter Your Password" name="securityPin" id="first_name" type="password" class="validate">
<div class="btn-group center-align"  role="group">
    <button type="submit" id="loginSubmit" value="submit"  class="btn #2196f3 blue">Submit</button>
    <button type="clear" value="Clear" class="btn #2196f3 blue" >Clear</button>
  </div>
</form>



    </div>

    <div id="myModal" class="modal1">

        <!-- Modal content -->
        <div class="modal-content1">
          <span class="close" style="display: none;" >&times;</span>
          <div class="loader1" style="display:none;">
            <div class="loader" style="margin: auto;margin-left:40%;margin-top:7%;"> 
            </div>
      
      <h5 id="loaderText" style="margin: auto;margin-left:30%;margin-top:0%;color: rgb(30, 169, 224);  letter-spacing: 5px; padding: 10px;">Please wait....</h5>       
        </div>
        </div>
      
      </div>

   
</div>





</div>



</div>




</div>

      </div>

      <script>

      
      
	$("#loginSubmit").click(function (event) {
   	 event.preventDefault();
     var form = $('#adminLogin')[0];
       // Create an FormData object 
       var data = new FormData(form);
       // disabled the submit button
       $.ajax({
         type: "POST",
         enctype: 'multipart/form-data',
         url: "/Temple_Website/adminLogin",
         data: data,
         processData: false,
         contentType: false,
         success: function (data, textStatus, jqXHR) {
        	 if(data.includes("jsp")){
	        	   window.location.href = data;
        	 }else{
              	  swal("Error", data, "error");
        	 }
         },
         error: function (jqXHR, textStatus, errorThrown) {
       	  $(".loader1").hide();
         	$('#myModal').hide();
       	  swal("Error", textStatus, "error");
         }
       });

   });
      </script>
</body>
</html>