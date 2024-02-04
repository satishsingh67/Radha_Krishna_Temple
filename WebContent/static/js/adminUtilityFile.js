var baseUrl="https://srktsahlaur.com";
function fetchNoticeForAdmin(searchTitle) {
	
    if (searchTitle == undefined || searchTitle == null) {
        searchTitle = "";
    }

    $.ajax({
        type: "GET",
        url: baseUrl+"/notice?action=getNotice&isFromAdmin=yes&noticeTitle=" + searchTitle,
        success: function (data, textStatus, jqXHR) {
            $("#adminNotceTable").empty();
            if (data === "No Records") {
                $("#noticeCount").text("");
                $("#noticeCount").text(" Total Records: 0");
                $("#adminNotceTable").html('<tr class="no-records"><td colspan="9" style="text-align:center;text-color:black">Sorry,No record found.</td></tr>');
            } else {
                var JsonData = jQuery.parseJSON(data);
                $("#noticeCount").text("");
                $("#noticeCount").text(" Total Records: " + JsonData.count);
                var i = 1;
                $(JsonData.rows).each(function (index, item) {
                    $("#adminNotceTable").append(
                        '<tr class="row-select" style="height:20px">' +
                            '<td><input type="checkbox" class="noticeCheckbox"/>    &nbsp;   </td>' +
                            '<td style="text-align: center;font-size: 15px;display:none">' +
                            item.pkNoticeId +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            i +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            item.noticeTitle +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;"><textarea rows="2" cols="15" readonly>' +
                            item.noticeDescription +
                            "</textarea></td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            item.createUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            item.createTime +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            item.updateUser +
                            "</td>" +
                            '<td style="text-align: center;font-size: 15px;">' +
                            item.updateTime +
                            "</td>" +
                            '<td style="text-align: center;"><button class="btn btn-primary" type="button" onclick=myFunction(' +
                            item.pkNoticeId +
                            ')  style="font-size: medium;border-radius: 20px;">View</button></td>' +
                            "</tr>"
                    );
                    i++;
                });
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            $("#Table1").html('<tr class="no-records"><td colspan="9" style="text-align:center;text-color:black">Sorry, Something went wrong,Please try again.</td></tr>');
        },
    });
}

function deleteNotice(noticeIds) {
    var form_data = new FormData();
    form_data.append("action", "delete");
    form_data.append("noticeId", noticeIds);

    $.ajax({
        type: "POST",
        enctype: "multipart/form-data",
        url: baseUrl+"/notice",
        data: form_data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
        	//hiding loader
            $('#myModal1').hide();
            $(".loader1").hide();
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                fetchNoticeForAdmin();
            } else {
                swal("Error", data, "error");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	//hiding loader
            $('#myModal1').hide();
            $(".loader1").hide();
            swal("Error", data, "error");
        },
    });
}

$("#submitNotice").click(function (event) {
	
	
    $("#uploadNoticeForm").hide();

	//calling loader
    $('#myModal1').show();
	$(".loader1").show();
	
    event.preventDefault();
    
    $("#submitNotice").prop("disabled", true);
    var form = $("#uploadNoticeFormPanel")[0];
    // Create an FormData object
    var data = new FormData(form);

    var noticeId;
    $(".row-select input:checked").each(function () {
        noticeId = $(this).closest("tr").find("td").eq(1).text();
        data.append("noticeId", noticeId);
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
        url: baseUrl+"/notice",
        data: data,
        processData: false,
        contentType: false,
        success: function (data, textStatus, jqXHR) {
            $("#uploadNoticeForm").show();
        	//hiding loader
            $('#myModal1').hide();
        	$(".loader1").hide();
            $("#submitNotice").prop("disabled", false);
            if (data.trim().includes("Successfully")) {
                swal("Done", data, "success");
                $("#uploadNoticeFormPanel")[0].reset();
                fetchNoticeForAdmin();
                $("#uploadNoticeForm").hide();
            } else {
                swal("Error", data, "error");
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	//hiding loader
            $('#myModal1').hide();
        	$(".loader1").hide();
            $("#uploadNoticeForm").show();
            $("#submitNotice").prop("disabled", false);
        },
    });
});

function myFunction(id) {
    window.open("/notice?action=download&noticeId=" + id, "_blank");
}
