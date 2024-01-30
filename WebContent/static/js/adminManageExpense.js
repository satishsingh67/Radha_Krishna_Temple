//Add Expense
$(document).on("click", "#uploadExpenses", function () {
    $("#expanseHeader").text("Add Expense");
    $("#uploadExpanseForm").show();
});

//Edit Expense
$(document).on("click", "#editExpenses", function () {
    $("#expanseHeader").text("Edit Expense");

    var expenseName;
    var expenseDescription;
    var expenseAmount;
    var expenseDate;

    $(".row-select input:checked").each(function () {
        expenseName = $(this).closest("tr").find("td").eq(3).text();
        expenseDescription = $(this).closest("tr").find("td").eq(4).text();
        expenseAmount = $(this).closest("tr").find("td").eq(5).text();
        expenseDate = $(this).closest("tr").find("td").eq(6).text();

        expenseDate = new Date(expenseDate)
            .toISOString("en-US", {
                year: "numeric",
                month: "2-digit",
                day: "2-digit",
            })
            .split("T")[0];
    });

    $("#expenseName").val(expenseName);
    $("#expenseDetails").val(expenseDescription);
    $("#expenseAmount").val(expenseAmount);
    $("#expenseDate").val(expenseDate);

    $("#uploadExpanseForm").show();
});

// Close Expense Form
$("#closeExpense").click(function () {
    $("#uploadExpanseForm").hide();
});

//Refresh Button
$(document).on("click", "#expenseButtonrefresh", function () {
	$('#myModal1').show();
    $(".loader1").show();
    var searchName = $("#expenseSearchField").val();

    fetchExpenseForAdmin(searchName);
    $('#myModal1').hide();
    $(".loader1").hide();
});

// Search Button

$(document).on("click", "#expenseSearchButton", function () {
	$('#myModal1').show();
    $(".loader1").show();
    var searchName = $("#expenseSearchField").val();

    fetchExpenseForAdmin(searchName);
    $('#myModal1').hide();
    $(".loader1").hide();
});

// Delete Button

$(document).on("click", "#deleteExpenses", function () {
	$('#myModal1').hide();
    $(".loader1").hide();
    var deleteId = new Array();
    $(".row-select input:checked").each(function () {
        var id;
        id = $(this).closest("tr").find("td").eq(1).text();
        deleteId.push(id);
    });
    deleteExpense(deleteId);
});

function fetchExpenseForAdmin(searchTitle) {
    var searchTitle = $("#expenseSearchField").val();

    if (searchTitle == undefined || searchTitle == null) {
        searchTitle = "";
    }

    $.ajax({
        type: "GET",
        url: "/Temple_Website/expense?action=getExpense&isFromAdmin=yes&expenseName=" + searchTitle,
        success: function (data, textStatus, jqXHR) {
            $("#adminExpenseTable").empty();
            if (data === "No Records") {
                $("#expenseLabelId").text("");
                $("#expenseLabelId").text(" Total Records: 0");
                $("#adminExpenseTable").html('<tr class="no-records"><td colspan="11" style="text-align:center;text-color:black">Sorry,No record found.</td></tr>');
            } else {
                var JsonData = jQuery.parseJSON(data);
                $("#expenseLabelId").text("");
                $("#expenseLabelId").text(" Total Records: " + JsonData.count);
                var i = 1;
                $(JsonData.rows).each(function (index, item) {
                    $("#adminExpenseTable").append(
                        '<tr class="row-select" style="height:20px">' +
                            '<td><input type="checkbox" class="noticeCheckbox"/>    &nbsp;   </td>' +
                            '<td style="text-align: center;font-size: 14px;display:none">' +
                            item.pkExpenseId +
                            "</td>" +
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
                            '<td style="text-align: center;"><button class="btn btn-primary" type="button" onclick=downloadExpense(' +
                            item.pkExpenseId +
                            ')  style="font-size: medium;border-radius: 20px;">View</button></td>' +
                            "</tr>"
                    );
                    i++;
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            $("#adminExpenseTable").html('<tr class="no-records"><td colspan="11" style="text-align:center;text-color:black">Sorry, Something went wrong,Please try again.</td></tr>');
        },
    });
}

function deleteExpense(expenseId) {
    var form_data = new FormData();
    form_data.append("action", "delete");
    form_data.append("pkExpenseId", expenseId);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/Temple_Website/expense",
        data: form_data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                fetchExpenseForAdmin();
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

$("#submitExpense").click(function (event) {
    $("#uploadExpanseForm").hide();

	$('#myModal1').show();
    $(".loader1").show();
    event.preventDefault();

    $("#submitExpense").prop("disabled", true);
    var form = $("#expenseFormId")[0];
    // Create an FormData object
    var data = new FormData(form);

    var noticeId;
    $(".row-select input:checked").each(function () {
        noticeId = $(this).closest("tr").find("td").eq(1).text();
        data.append("pkExpenseId", noticeId);
    });

    var action = "update";

    if (noticeId == null || noticeId == undefined || noticeId === "") {
        action = "add";
    }

    data.append("action", action);

    // disabled the submit button
    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: "/Temple_Website/expense",
        data: data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
            $("#uploadExpanseForm").show();
        	$('#myModal1').hide();
            $(".loader1").hide();
            $("#submitExpense").prop("disabled", false);
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                $("#expenseFormId")[0].reset();
                fetchExpenseForAdmin();
                $("#uploadExpanseForm").hide();
            } else {
                swal("Error", data, "error");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	$('#myModal1').hide();
            $(".loader1").hide();
            $("#uploadExpanseForm").show();
            $("#submitExpense").prop("disabled", false);
        },
    });
});

function downloadExpense(id) {
    window.open("/Temple_Website/expense?action=download&expenseId=" + id, "_blank");
}
