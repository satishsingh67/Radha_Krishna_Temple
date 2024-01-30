//Add Donation
$(document).on("click", "#uploadDonation", function () {
    $("#donationHeader").text("Add Donation");
    $("#uploadDonationForm").show();
});

//Edit Donation
$(document).on("click", "#editDonation", function () {
    $("#donationHeader").text("Edit Donation");

    var donorName;
    var donorMobileNumber;
    var donorAddress;
    var donationAmount;
    var remarks;
    var donationDate;

    $(".row-select input:checked").each(function () {
        donorName = $(this).closest("tr").find("td").eq(3).text();
        donorMobileNumber = $(this).closest("tr").find("td").eq(4).text();
        donorAddress = $(this).closest("tr").find("td").eq(5).text();
        donationAmount = $(this).closest("tr").find("td").eq(6).text();
        remarks = $(this).closest("tr").find("td").eq(7).text();
        donationDate = $(this).closest("tr").find("td").eq(8).text();

        donationDate = new Date(donationDate)
            .toISOString("en-US", {
                year: "numeric",
                month: "2-digit",
                day: "2-digit",
            })
            .split("T")[0];
    });

    $("#donorName").val(donorName);
    $("#donorMobileNumber").val(donorMobileNumber);
    $("#donorAddress").val(donorAddress);
    $("#donationAmount").val(donationAmount);
    $("#remarksd").val(remarks);
    $("#donationDate").val(donationDate);

    $("#uploadDonationForm").show();
});

// Close Donation Form
$("#closeDonation").click(function () {
    $("#uploadDonationForm").hide();
});

//Refresh Button
$(document).on("click", "#donationRefreshButton", function () {
	 $('#myModal1').show();
     $(".loader1").show();
    var searchName = $("#donationSearchField").val();

    fetchDonationForAdmin(searchName);
    
    $('#myModal1').hide();
    $(".loader1").hide();
});

// Search Button

$(document).on("click", "#donationSearchButton", function () {
	 $('#myModal1').show();
     $(".loader1").show();
    var searchName = $("#donationSearchField").val();

    fetchDonationForAdmin(searchName);
    $('#myModal1').hide();
    $(".loader1").hide();
});

// Delete Button

$(document).on("click", "#deleteDonation", function () {
	$('#myModal1').show();
    $(".loader1").show();
    var deleteId = new Array();
    $(".row-select input:checked").each(function () {
        var id;
        id = $(this).closest("tr").find("td").eq(1).text();
        deleteId.push(id);
    });
    deleteDonor(deleteId);
});

function fetchDonationForAdmin(searchTitle) {
   
    var searchTitle = $("#donationSearchField").val();

    if (searchTitle == undefined || searchTitle == null) {
        searchTitle = "";
    }

    $.ajax({
        type: "GET",
        url: "/Temple_Website/donation?action=getDonation&isFromAdmin=yes&donnerName=" + searchTitle,
        success: function (data, textStatus, jqXHR) {
            $("#adminDonationTable").empty();
            if (data === "No Records") {
                $("#adminDonationCount").text("");
                $("#adminDonationCount").text(" Total Records: 0");
                $("#adminDonationTable").html('<tr class="no-records"><td colspan="13" style="text-align:center;text-color:black">Sorry,No record found.</td></tr>');
            } else {
                var JsonData = jQuery.parseJSON(data);
                $("#adminDonationCount").text("");
                $("#adminDonationCount").text(" Total Records: " + JsonData.count);
                var i = 1;
                $(JsonData.rows).each(function (index, item) {
                    $("#adminDonationTable").append(
                        '<tr class="row-select" style="height:20px">' +
                            '<td><input type="checkbox" class="noticeCheckbox"/>    &nbsp;   </td>' +
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
                            '<td style="text-align: center;"><button class="btn btn-primary" type="button" onclick=downloadDonation(' +
                            item.pkDonnerId +
                            ')  style="font-size: medium;border-radius: 20px;">View</button></td>' +
                            "</tr>"
                    );
                    i++;
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#adminDonationTable").html('<tr class="no-records"><td colspan="13" style="text-align:center;text-color:black">Sorry, Something went wrong,Please try again.</td></tr>');
        },
    });
}

function deleteDonor(donnerId) {
    var form_data = new FormData();
    form_data.append("action", "delete");
    form_data.append("donnerId", donnerId);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/Temple_Website/donation",
        data: form_data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                fetchDonationForAdmin();
            } else {
                swal("Error", data, "error");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            swal("Error", data, "error");
        },
    });
}

$("#submitDonationForm").click(function (event) {
    $("#uploadDonationForm").hide();

	$('#myModal1').show();
    $(".loader1").show();
    event.preventDefault();

    $("#submitDonationForm").prop("disabled", true);
    var form = $("#donorFormId")[0];
    // Create an FormData object
    var data = new FormData(form);

    var donorId;
    $(".row-select input:checked").each(function () {
        donorId = $(this).closest("tr").find("td").eq(1).text();
        data.append("donnerId", donorId);
    });

    var action = "update";

    if (donorId == null || donorId == undefined || donorId === "") {
        action = "add";
    }

    data.append("action", action);

    // disabled the submit button
    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/Temple_Website/donation",
        data: data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            $("#uploadDonationForm").show();
            $("#submitDonationForm").prop("disabled", false);
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                $("#donorFormId")[0].reset();
                fetchDonationForAdmin();
                $("#uploadDonationForm").hide();
            } else {
                swal("Error", data, "error");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            $("#uploadDonationForm").show();
            $("#submitDonationForm").prop("disabled", false);
        },
    });
});

function downloadDonation(id) {
    window.open("/Temple_Website/donation?action=download&donnerId=" + id, "_blank");
}
