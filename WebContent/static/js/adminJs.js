let menuicn = document.querySelector(".menuicn");
let nav = document.querySelector(".navcontainer");

menuicn.addEventListener("click", () => {
    nav.classList.toggle("navclose");
});

$(document).ready(function () {

    $("#adminMainPanel").empty();
    mangeNotice();

    $("#noticeBoard").click(function () {
        $("#adminMainPanel").empty();
        mangeNotice();
    });

    $("#logout").click(function () {
        window.location.href = "/Temple_Website/adminLogin?action=logout";
    });

    $("#donationBoard").click(function () {
        $("#adminMainPanel").empty();
        manageDonation();
    });

    $("#expanseBoard").click(function () {
        $("#adminMainPanel").empty();
        manageExpenses();
    });

    $(document).on("click", "#uploadNotice", function () {
        $("#noticeHeader").text("Add Notice");
        $("#uploadNoticeForm").show();
    });

    $(document).on("click", "#editNotice", function () {
        $("#noticeHeader").text("Edit Notice");
        var noticeTitle;
        var noticeDescription;
        $(".row-select input:checked").each(function () {
            noticeTitle = $(this).closest("tr").find("td").eq(3).text();
            noticeDescription = $(this).closest("tr").find("td").eq(4).text();
        });

        $("#noticeTitleText").val(noticeTitle);
        $("#noticeDescription").val(noticeDescription);
        $("#uploadNoticeForm").show();
    });

    $(document).on("click", "#noticeRefresh", function () {
    	//showing loader
        $('#myModal1').show();
        $(".loader1").show();
        var searchName = $("#noticeSearchInput").val();

        fetchNoticeForAdmin(searchName);
        $('#myModal1').hide();
        $(".loader1").hide();
    });

    $(document).on("click", "#noticeSearchButton", function () {
    	//showing loader
        $('#myModal1').show();
        $(".loader1").show();
        var searchName = $("#noticeSearchInput").val();

        fetchNoticeForAdmin(searchName);
        $('#myModal1').hide();
        $(".loader1").hide();
    });

    $(document).on("click", "#deleteNotice", function () {
    	//calling loader
        $('#myModal1').show();
    	$(".loader1").show();
        var deleteId = new Array();
        $(".row-select input:checked").each(function () {
            var id;
            id = $(this).closest("tr").find("td").eq(1).text();
            deleteId.push(id);
        });
        deleteNotice(deleteId);
    });

    $("#closeNotice").click(function () {
        $("#uploadNoticeFormPanel")[0].reset();
        $("#uploadNoticeForm").hide();
    });

    $("#ckbCheckAll").click(function (e) {
        $(this).closest("table").find("td input:checkbox").prop("checked", this.checked);
    });
});

function mangeNotice() {
	 //Calling Loader
	 $('#myModal1').show();
	 $(".loader1").show();
    var childDiv =
        '<div class="report-container" id="test2" style="border: 2px solid tomato;height: 550px;">' +
        '<div class="report-header">' +
        '  <h5 class="recent-Articles">Manage Notice</h5> ' +
        "</div> " +
        '<div style="margin-top: 5px;">' +
        '  <button type="button" id="uploadNotice" class="view" style="background-color:sandybrown;" >Add</button> ' +
        '  <button class="view" style="background-color:navy;" id="editNotice">Edit</button> ' +
        '  <button class="view" style="background-color:crimson;" id="deleteNotice">Delete</button> ' +
        '   <input type="text" id="noticeSearchInput" placeholder="Serach with Notice Title" style="font-size: medium;width:200px;height: 40px;border:1px solid skyblue;  border-radius: 25px;text-align: center; margin-left: 2%;">' +
        '  <button class="btn btn-primary" type="button" disabled="true" id="noticeSearchButton"' +
        '  style="font-size: medium;margin-left: 2%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Search</button>' +
        '<button class="btn btn-primary" id="noticeRefresh" type="button" style="font-size: medium;margin-left: 1%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Refresh</button>' +
        '<label for="exampleFormControlInput1" class="form-label"	id="noticeCount" style="margin-left: 5px;font-size: large;"></label>' +
        "</div>" +
        '<div	style="height: 400px;overflow-y:auto;">' +
        ' <table class="table table-striped" style="font-size: medium;height: 400px;">' +
        '     <thead	style="  position: sticky; top: 0;background-color: white;">' +
        "         <tr>" +
        '             <td><input type="checkbox" id="ckbCheckAll" />    &nbsp;   </td>' +
        '              <th scope="col" style="text-align:center">Sl.No</th>' +
        '              <th scope="col" style="text-align:center">Notice Title</th>' +
        '              <th scope="col" style="text-align:center">Notice Details</th>' +
        '             <th scope="col" style="text-align:center">Create User</th>' +
        '             <th scope="col" style="text-align:center">Create Date</th>' +
        '             <th scope="col" style="text-align:center">Update User</th>' +
        '             <th scope="col" style="text-align:center">Update Date</th>' +
        '            <th scope="col" style="text-align:center">Attachment</th>' +
        "        </tr>" +
        "     </thead>" +
        '     <tbody id="adminNotceTable">' +
        "     </tbody>" +
        "  </table>" +
        "  </div>" +
        "</div>" +
        "</div>";
    $("#adminMainPanel").append(childDiv);

    $("#noticeSearchInput").on("input", function () {
        var input = this.value;
        if (input.trim().length > 0) {
            $("#noticeSearchButton").prop("disabled", false);
        } else {
            $("#noticeSearchButton").prop("disabled", true);
        }
    });

    fetchNoticeForAdmin(null);
    //hiding loader
    $('#myModal1').hide();
	$(".loader1").hide();
}

function manageDonation() {
	 //Calling Loader
	 $('#myModal1').show();
	 $(".loader1").show();
    var childDiv =
        '<div class="report-container" id="test2" style="border: 2px solid tomato;height: 550px;">' +
        '<div class="report-header">' +
        '  <h5 class="recent-Articles">Manage Donation</h5> ' +
        "</div> " +
        '<div style="margin-top: 5px;">' +
        '  <button class="view" style="background-color:sandybrown;" id="uploadDonation">Add</button> ' +
        '  <button class="view" style="background-color:navy;" id="editDonation">Edit</button> ' +
        '  <button class="view" style="background-color:crimson" id="deleteDonation">Delete</button> ' +
        '   <input type="text" id="donationSearchField" placeholder="Serach with Name" style="font-size: medium;width:200px;height: 40px;border:1px solid skyblue;  border-radius: 25px;text-align: center; margin-left: 2%;">' +
        '  <button class="btn btn-primary" id="donationSearchButton" disabled="true" type="button"' +
        '  style="font-size: medium;margin-left: 2%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Search</button>' +
        '<button class="btn btn-primary" id="donationRefreshButton" type="button" style="font-size: medium;margin-left: 1%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Refresh</button>' +
        '<label for="exampleFormControlInput1" id="adminDonationCount" class="form-label" style="margin-left: 2px;font-size: large;"></label>' +
        "</div>" +
        "<div>" +
        '<div style="height: 400px;overflow-y:auto;">' +
        ' <table class="table table-striped" style="font-size:small;height: 400px;">' +
        '     <thead style="position: sticky; top: 0;background-color: white;">' +
        "         <tr >" +
        '             <td><input type="checkbox" id="ckbCheckAll" /></td>' +
        '              <th scope="col" style="text-align:center">Sl.No</th>' +
        '              <th scope="col" style="text-align:center;white-space: nowrap;">Donor Name</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Mobile Number</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Address</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Amount</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Remarks</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Donation Date</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Create User</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Create Date</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Update User</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Update Date</th>' +
        '            <th scope="col" style="text-align:center;white-space: nowrap;">Attachment</th>' +
        "        </tr>" +
        "     </thead>" +
        '     <tbody id="adminDonationTable">' +
        "     </tbody>" +
        "  </table>" +
        "  </div>" +
        "</div>" +
        "</div>";
    $("#adminMainPanel").append(childDiv);

    $("#donationSearchField").on("input", function () {
        var input = this.value;
        if (input.trim().length > 0) {
            $("#donationSearchButton").prop("disabled", false);
        } else {
            $("#donationSearchButton").prop("disabled", true);
        }
    });

    fetchDonationForAdmin();
    //hiding loader
    $('#myModal1').hide();
	$(".loader1").hide();
}

function manageExpenses() {
	 //Calling Loader
	 $('#myModal1').show();
	 $(".loader1").show();
    var childDiv =
        '<div class="report-container" id="test2" style="border: 2px solid tomato;height: 550px;">' +
        '<div class="report-header">' +
        '  <h5 class="recent-Articles">Manage Expenses</h5> ' +
        "</div> " +
        '<div style="margin-top: 5px;">' +
        '  <button class="view" style="background-color:sandybrown;" id="uploadExpenses">Add</button> ' +
        '  <button class="view" style="background-color:navy;" id="editExpenses">Edit</button> ' +
        '  <button class="view" style="background-color:crimson" id="deleteExpenses">Delete</button> ' +
        '   <input type="text" id="expenseSearchField"  placeholder="Serach with Name" style="font-size: medium;width:200px;height: 40px;border:1px solid skyblue;  border-radius: 25px;text-align: center; margin-left: 2%;">' +
        '  <button  id="expenseSearchButton" disabled="true" class="btn btn-primary" type="button"' +
        '  style="font-size: medium;margin-left: 2%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Search</button>' +
        '<button class="btn btn-primary" id="expenseButtonrefresh" type="button" style="font-size: medium;margin-left: 1%;margin-top:0px;width: 100px;height: 35px;border:1px solid skyblue;  border-radius: 25px;">Refresh</button>' +
        '<label for="exampleFormControlInput1" id="expenseLabelId" class="form-label" style="margin-left: 2px;font-size: large;"></label>' +
        "</div>" +
        '<div style="height: 400px;overflow-y:auto;">' +
        ' <table class="table table-striped" style="font-size:medium;height: 400px;">' +
        '     <thead style="position: sticky; top: 0;background-color: white;">' +
        "         <tr>" +
        '             <td><input type="checkbox" id="ckbCheckAll" /></td>' +
        '              <th scope="col" style="text-align:center">Sl.No</th>' +
        '              <th scope="col" style="text-align:center;white-space: nowrap;">Expense Name</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Expense Details</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Amount</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Spent Date</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Create User</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Create Date</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Update User</th>' +
        '             <th scope="col" style="text-align:center;white-space: nowrap;">Update Date</th>' +
        '            <th scope="col" style="text-align:center;white-space: nowrap;">Attachment</th>' +
        "        </tr>" +
        "     </thead>" +
        '     <tbody id="adminExpenseTable">' +
        "     </tbody>" +
        "  </table>" +
        "  </div>" +
        "</div>" +
        "</div>";
    $("#adminMainPanel").append(childDiv);

    $("#expenseSearchField").on("input", function () {
        var input = this.value;
        if (input.trim().length > 0) {
            $("#expenseSearchButton").prop("disabled", false);
        } else {
            $("#expenseSearchButton").prop("disabled", true);
        }
    });

    fetchExpenseForAdmin(null);
    //hiding loader
    $('#myModal1').hide();
	$(".loader1").hide();
}
