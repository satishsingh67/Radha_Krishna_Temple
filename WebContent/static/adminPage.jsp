<%@page import="com.temple.model.Admin" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
    
Admin user=(Admin)session.getAttribute("user");
if(user==null){
	response.sendRedirect("adminLogin.jsp");
	return;
} 
    
%>
    
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta
            name="viewport"
            content="width=device-width, 
				initial-scale=1.0"
        />
        <title>Radhe Krishna Temple</title>
        <link rel="stylesheet" href="./css/adminPageCSS.css" />
        <link rel="stylesheet" href="./css/adminResponsive.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous" />
        <script src="./lib/sweetalert.min.js"></script>
        
      <style>
      
       /* The Close Button */
            .close {
                color: #ffffff;
                float: right;
                margin-left: 97%;
                margin-top: -3%;
                font-size: 28px;
                font-weight: bold;
                background-color: black;
                border-radius: 5px;
            }

            .close:hover,
            .close:focus {
                color: rgb(215, 230, 17);
                text-decoration: none;
                cursor: pointer;
            }

            /* The Modal (background) */
            .modal1 {
                display: none;
                /* Hidden by default */
                position: fixed;
                /* Stay in place */
                z-index: 1;
                /* Sit on top */
                padding-top: 100px;
                /* Location of the box */
                left: 0;
                top: 0;
                width: 100%;
                /* Full width */
                height: 100%;
                /* Full height */
                overflow: auto;
                /* Enable scroll if needed */
                background-color: rgb(0, 0, 0);
                /* Fallback color */
                background-color: rgba(0, 0, 0, 0.4);
                /* Black w/ opacity */
            }

            /* Modal Content */
            .modal-content1 {
                background-color: #fffff0;
                border-radius: 25px;
                border: 2px solid #73ad21;
                padding: 20px;
                margin: auto;
                margin-top: 10%;
                padding: 20px;
                border: 1px solid #fffff0;
                width: 30%;
                height: 30%;
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

            .loader {
                animation: rotate 1s infinite;
                height: 50px;
                width: 50px;
            }

            .loader:before,
            .loader:after {
                border-radius: 50%;
                content: "";
                display: block;
                height: 20px;
                width: 20px;
            }

            .loader:before {
                animation: ball1 1.5s infinite;
                background-color: #cb2025;
                box-shadow: 30px 0 0 #f8b334;
                margin-bottom: 10px;
            }

            .loader:after {
                animation: ball2 1.5s infinite;
                background-color: #00a096;
                box-shadow: 30px 0 0 #97bf0d;
            }

            @keyframes rotate {
                0% {
                    -webkit-transform: rotate(0deg) scale(0.8);
                    -moz-transform: rotate(0deg) scale(0.8);
                }

                50% {
                    -webkit-transform: rotate(360deg) scale(1.2);
                    -moz-transform: rotate(360deg) scale(1.2);
                }

                100% {
                    -webkit-transform: rotate(720deg) scale(0.8);
                    -moz-transform: rotate(720deg) scale(0.8);
                }
            }

            @keyframes ball1 {
                0% {
                    box-shadow: 30px 0 0 #f8b334;
                }

                50% {
                    box-shadow: 0 0 0 #f8b334;
                    margin-bottom: 0;
                    -webkit-transform: translate(15px, 15px);
                    -moz-transform: translate(15px, 15px);
                }

                100% {
                    box-shadow: 30px 0 0 #f8b334;
                    margin-bottom: 10px;
                }
            }

            @keyframes ball2 {
                0% {
                    box-shadow: 30px 0 0 #97bf0d;
                }

                50% {
                    box-shadow: 0 0 0 #97bf0d;
                    margin-top: -20px;
                    -webkit-transform: translate(15px, 15px);
                    -moz-transform: translate(15px, 15px);
                }

                100% {
                    box-shadow: 30px 0 0 #97bf0d;
                    margin-top: 0;
                }
            }
      </style>  
        
        
    </head>

    <body>
        <!-- for header part -->
        <header>
            <div class="logosec">
                <div class="logo">Radha Krishna Temple</div>
                <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210182541/Untitled-design-(30).png" class="icn menuicn" id="menuicn" alt="menu-icon" />
            </div>
            <h5>Welcome :<%= user.getFullName()%></h5>
            <div class="message">
                <div class="dp">
                    <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210180014/profile-removebg-preview.png" class="dpicn" alt="dp" />
                </div>
            </div>
        </header>

        <div class="main-container">
            <div class="navcontainer" style="border: 2px solid skyblue;">
                <nav class="nav">
                    <div class="nav-upper-options">
                        <div class="nav-option option1" id="noticeBoard">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210182148/Untitled-design-(29).png" class="nav-img" alt="Notice" />
                            <h3>Notice</h3>
                        </div>

                        <div class="nav-option option2" id="donationBoard">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210183322/9.png" class="nav-img" alt="Donation" />
                            <h3>Donation</h3>
                        </div>

                        <div class="nav-option option3" id="expanseBoard">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210183320/5.png" class="nav-img" alt="Expenses" />
                            <h3>Expenses</h3>
                        </div>
                        <div class="nav-option option4">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210183323/10.png" class="nav-img" alt="blog" />
                            <h3>Profile</h3>
                        </div>
                        <div class="nav-option logout" id="logout">
                            <img src="https://media.geeksforgeeks.org/wp-content/uploads/20221210183321/7.png" class="nav-img" alt="logout" />
                            <h3>Logout</h3>
                        </div>
                    </div>
                </nav>
            </div>
            <div class="main" style="height: 700px;" id="adminMainPanel">
                <!-- Notice Section -->
                <!-- Donation Section -->
            </div>
        </div>

        <div class="modal" id="uploadNoticeForm" tabindex="-1" style="background-color: rgb(0, 0, 0); background-color: rgba(0, 0, 0, 0.4);" role="dialog">
            <div class="modal-dialog" role="document" style="margin-top: 5%;">
                <div class="modal-content">
                    <div class="modal-header" style="background-color: Turquoise;">
                        <h5 class="modal-title" id="noticeHeader">Add Notice</h5>
                    </div>
                    <div class="modal-body" style="background-color: antiquewhite;">
                        <form id="uploadNoticeFormPanel">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Notice Title<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="noticeTitle" id="noticeTitleText" aria-describedby="emailHelp" placeholder="Enter Notice Title" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Description<strong class="text-danger" style="color: red;">*</strong></label>
                                <textarea class="form-control" name="noticeDescrition" id="noticeDescription" placeholder="Enter Notice Description" rows="3"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlFile1">Supporting Document</label>
                                <input type="file" name="file" class="form-control-file" id="exampleFormControlFile1" />
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer" style="background-color: Wheat;">
                        <button type="button" class="btn btn-primary" id="submitNotice">Submit</button>
                        <button type="button" class="btn btn-secondary" id="closeNotice" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="uploadDonationForm" tabindex="-1" style="background-color: rgb(0, 0, 0); background-color: rgba(0, 0, 0, 0.4);" role="dialog">
            <div class="modal-dialog" role="document" style="margin-top: 5%;">
                <div class="modal-content">
                    <div class="modal-header" style="background-color: Turquoise;">
                        <h5 class="modal-title" id="donationHeader">Upload Donation</h5>
                    </div>
                    <div class="modal-body" style="background-color: antiquewhite;">
                        <form id="donorFormId">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Donor Full Name <strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="name" id="donorName" aria-describedby="emailHelp" placeholder="Enter Donor Full Name" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Donor Mobile Number<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="mobileNumber" id="donorMobileNumber" aria-describedby="emailHelp" placeholder="Enter Donor Mobile Number" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Address<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="address" id="donorAddress" aria-describedby="emailHelp" placeholder="Enter Address" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Donation Amount<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="donationAmount" id="donationAmount" aria-describedby="emailHelp" placeholder="Enter Amount" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Remarks</label>
                                <input type="text" class="form-control" name="remarks" id="remarksd" aria-describedby="emailHelp" placeholder="Enter Remarks" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Donation Date<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="date" class="form-control" name="donationDate" id="donationDate" aria-describedby="emailHelp" placeholder="Enter Amount" />
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlFile1">Supporting Document<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="file" name="file" class="form-control-file" id="donationDocument" />
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer" style="background-color: Wheat;">
                        <button type="button" class="btn btn-primary" id="submitDonationForm">Submit</button>
                        <button type="button" class="btn btn-secondary" id="closeDonation" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal" id="uploadExpanseForm" tabindex="-1" style="background-color: rgb(0, 0, 0); background-color: rgba(0, 0, 0, 0.4);" role="dialog">
            <div class="modal-dialog" role="document" style="margin-top: 5%;">
                <div class="modal-content">
                    <div class="modal-header" style="background-color: Turquoise;">
                        <h5 class="modal-title" id="expanseHeader">Add Expenses</h5>
                    </div>
                    <div class="modal-body" style="background-color: antiquewhite;">
                        <form id="expenseFormId">
                            <div class="form-group">
                                <label for="exampleInputEmail1">Expense Name<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="text" class="form-control" name="expenseName" id="expenseName" aria-describedby="emailHelp" placeholder="Enter Expense Name" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Expense Details<strong class="text-danger" style="color: red;">*</strong></label>
                                <textarea class="form-control" name="expenseDetails" id="expenseDetails" rows="3" placeholder="Enter Expense Details"></textarea>
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Expense Amount<strong class="text-danger" style="color: red;"></strong></label>
                                <input type="text" class="form-control" name="expenseAmount" id="expenseAmount" aria-describedby="emailHelp" placeholder="Enter Amount" />
                            </div>
                            <div class="form-group">
                                <label for="exampleInputEmail1">Expense Date<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="date" class="form-control" name="expenseDate" id="expenseDate" aria-describedby="emailHelp" placeholder="Enter Amount" />
                            </div>
                            <div class="form-group">
                                <label for="exampleFormControlFile1">Supporting Document<strong class="text-danger" style="color: red;">*</strong></label>
                                <input type="file" name="file" class="form-control-file" id="exampleFormControlFile1" />
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer" style="background-color: Wheat;">
                        <button type="button" class="btn btn-primary" id="submitExpense">Submit</button>
                        <button type="button" class="btn btn-secondary" id="closeExpense" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div style="text-align: center; margin-top: 2%; background-color: antiquewhite; height: 10%;">
            <h4 style="font-size: medium; text-decoration: dashed;">Developed By : Satish Singh</h4>
        </div>
                             <!--==========Loader Model Start ==========-->
                            <div id="myModal1" class="modal1">
                                <!-- Modal content -->
                                <div class="modal-content1">
                                    <span class="close" style="display: none;">&times;</span>
                                    <div class="loader1" style="display: none;">
                                        <div class="loader" style="margin: auto; margin-left: 40%; margin-top: 7%;"></div>

                                        <h5 style="margin: auto; margin-left: 30%; margin-top: 0%; color: rgb(30, 169, 224); letter-spacing: 5px; padding: 10px; font-size: 18px;">
                                            Please wait....
                                        </h5>
                                    </div>
                                </div>
                            </div>
                            <!--==========Loader Model End ==========-->
        <script src="./js/adminJs.js"></script>
        <script src="./js/adminUtilityFile.js"></script>
        <script src="./js/adminManageExpense.js"></script>
        <script src="./js/adminManageDonor.js"></script>
    </body>
</html>
