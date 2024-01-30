function fetchExpenseForFundManagementPage() {
    /*	 $('#myModal').show();
	  	//Calling Loader
	  	$(".loader1").show();
*/

    $.ajax({
        type: "GET",
        url: "/Temple_Website/expense?action=getExpense",
        success: function (data, textStatus, jqXHR) {
            $("#fundManagementExpenseTable").empty();
            if (data === "No Records") {
                $("#expensSpanId").text("");
                $("#expensSpanId").text(" Total Records: 0");
                $("#adminExpenseTable").html('<tr class="no-records"><td colspan="11" style="text-align:center;text-color:black">Sorry,No record found.</td></tr>');
            } else {
                var JsonData = jQuery.parseJSON(data);
                $("#expensSpanId").text("");
                $("#expensSpanId").text(" Total Records: " + JsonData.count);
                var i = 1;
                $(JsonData.rows).each(function (index, item) {
                    $("#fundManagementExpenseTable").append(
                        '<tr class="row-select" style="height:20px">' +
                            '<td style="text-align: center;font-size: 14px;">' +
                            i +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.expenseName +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.expenseDeatails +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.expenseAmount +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.expenseDate +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.createUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.createDate +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.updateUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.updateDate +
                            "</td>" +
                            '<td style="text-align: center;"><button class="btn btn-primary" type="button" onclick=downloadExpenseFormFundmanagement(' +
                            item.pkExpenseId +
                            ')  style="font-size: medium;border-radius: 20px;">View</button></td>' +
                            "</tr>"
                    );
                    i++;
                });
            }
            /*	 $('#myModal').hide();
		    	//Calling Loader
		    	$(".loader1").hide();
				$('#myModalQuestionPaper').show();
*/
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#fundManagementExpenseTable").html('<tr class="no-records"><td colspan="11" style="text-align:center;text-color:black">Sorry, Something went wrong,Please try again.</td></tr>');
            $("#myModal").hide();
            //Calling Loader
            $(".loader1").hide();

            $("#myModalQuestionPaper").show();
        },
    });
}

function downloadExpenseFormFundmanagement(id) {
    window.open("/Temple_Website/expense?action=download&expenseId=" + id, "_blank");
}

$("#donationSearchFund").on("input", function () {
    var input = this.value;
    if (input.trim().length > 0) {
        $("#donationSearchButtonFund").prop("disabled", false);
    } else {
        $("#donationSearchButtonFund").prop("disabled", true);
    }
});

//Refresh Button
$(document).on("click", "#donationRefreshButtonFund", function () {
    var searchName = $("#donationSearchFund").val();

    fetchDonationForFundManagement(searchName);
});

// Search Button

$(document).on("click", "#donationSearchButtonFund", function () {
    var searchName = $("#donationSearchFund").val();

    fetchDonationForFundManagement(searchName);
});

function fetchDonationForFundManagement(searchTitle) {
    /*	 $('#myModal').show();
		  	//Calling Loader
		  	$(".loader1").show();
	*/
    var searchTitle = $("#donationSearchFund").val();

    if (searchTitle == undefined || searchTitle == null) {
        searchTitle = "";
    }

    $.ajax({
        type: "GET",
        url: "/Temple_Website/donation?action=getDonation&donnerName=" + searchTitle,
        success: function (data, textStatus, jqXHR) {
            $("#fundManagementDonationTable").empty();
            if (data === "No Records") {
                $("#DonationSpanId").text("");
                $("#DonationSpanId").text(" Total Records: 0");
                $("#fundManagementDonationTable").html('<tr class="no-records"><td colspan="13" style="text-align:center;text-color:black">Sorry,No record found.</td></tr>');
            } else {
                var JsonData = jQuery.parseJSON(data);
                $("#DonationSpanId").text("");
                $("#DonationSpanId").text(" Total Records: " + JsonData.count);
                var i = 1;
                $(JsonData.rows).each(function (index, item) {
                    $("#fundManagementDonationTable").append(
                        '<tr class="row-select" style="height:20px">' +
                            '<td style="text-align: center;font-size: 14px;display:none">' +
                            item.pkDonnerId +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            i +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.fullName +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.mobileNumber +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.donnerAddress +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.amount +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.remarks +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.receivedDate +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.createUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.createDate +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.updateUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 14px;">' +
                            item.updateDate +
                            "</td>" +
                            '<td style="text-align: center;"><button class="btn btn-primary" type="button" onclick=downloadDonationFund(' +
                            item.pkDonnerId +
                            ')  style="font-size: medium;border-radius: 20px;">View</button></td>' +
                            "</tr>"
                    );
                    i++;
                });
            }
            /*	 $('#myModal').hide();
			    	//Calling Loader
			    	$(".loader1").hide();
					$('#myModalQuestionPaper').show();
	*/
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#fundManagementDonationTable").html('<tr class="no-records"><td colspan="11" style="text-align:center;text-color:black">Sorry, Something went wrong,Please try again.</td></tr>');
            $("#myModal").hide();
            //Calling Loader
            $(".loader1").hide();

            $("#myModalQuestionPaper").show();
        },
    });
}

function downloadDonationFund(id) {
    window.open("/Temple_Website/donation?action=download&donnerId=" + id, "_blank");
}

function getFundDetails() {
    $.ajax({
        type: "GET",
        url: "/Temple_Website/donation?action=getFundDetails",
        success: function (data, textStatus, jqXHR) {
            var JsonData = jQuery.parseJSON(data);
            $("#fundRecivedId").text("");
            $("#totalPeople").text("");
            $("#fundSpent").text("");
            $("#fundLeft").text("");

            $("#fundRecivedId").text(JsonData.totalFund);
            $("#totalPeople").text(JsonData.TotalPeople);
            $("#fundSpent").text(JsonData.totalSpent);
            $("#fundLeft").text(JsonData.fundLeft);
        },
        error: function (jqXHR, textStatus, errorThrown) {},
    });
}
