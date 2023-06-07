$(".welcome").hide();

$('#videoPlayer').click(function() {
    this.paused ? this.play() : this.pause();
});

$(function() {
    $.ajax({
        type: "POST",
        url: "/check_alive",
        dataType:"json",
        contentType: "application/json",
        data: "",
        beforeSend: function(){

        },
        success: function(response){
            let objResp = response;
            // console.log(objResp);
            if(objResp.code == "1") {
                if(objResp.data.msisdn != '' && objResp.data.msisdn != null) {
                    $('.btn-action').hide();

                    let msisdn = objResp.data.msisdn;
                    // console.log(msisdn);
                    $('.welcome .welcome-text').html("Hi! 0" + msisdn);
                    $('.mwelcome .welcome-text').html("Hi! 0" + msisdn);
                    $('.mcode').html(objResp.data.code);
                    $('.welcome').show();
                    $('.mwelcome').show();
                }
            }
        }
    });
});
// (function() {
//     console.log("XXXXX");
//
// })();

$('#frmLogin').on('submit', function(e) {
    e.preventDefault();
    let frmObj = {
        username : $("#frmLogin #username").val(),
        password : $("#frmLogin #password").val()
    }
    let link = $("#frmLogin").attr('action');
    $.ajax({
        type: "POST",
        url: link,
        dataType:"json",
        contentType: "application/json",
        data: JSON.stringify(frmObj),
        beforeSend: function(){
            $('.alert').alert('close');
            $.blockUI({
                baseZ: 2000,
                message: '<div class="spinner-border text-white" role="status"></div>',
                timeout: 10000,
                css: {
                    backgroundColor: 'transparent',
                    border: '0'
                },
                overlayCSS: {
                    opacity: 0.5
                }
            });
        },
        success: function(response){
            $.unblockUI();
            let objResp = response;
            // console.log(objResp);
            if(objResp.code == "1") {
                if(objResp.data.msisdn != '' && objResp.data.msisdn != null) {
                    $('#loginModal').modal("toggle");
                    $('.btn-action').hide();
                    // let msisdn = [[${session.userSession.msisdn}]];
                    let msisdn = objResp.data.msisdn;
                    // console.log(msisdn);
                    $('.welcome .welcome-text').html("Hi! 0" + msisdn);
                    $('.mwelcome .welcome-text').html("Hi! 0" + msisdn);
                    $('.mcode').html(objResp.data.code);
                    $('.welcome').show();
                    $('.mwelcome').show();
                }
            } else {
                $("#message").html("<div class=\"alert alert-warning alert-dismissible fade show\" role=\"alert\">\n" +
                    "                           " + objResp.message + " \n" +
                    "                            <button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "                                <span aria-hidden=\"true\">&times;</span>\n" +
                    "                            </button>\n" +
                    "                        </div>");
            }
        }
    });
});

//BEGIN: Check alive and show msisdn
$(function () {
    $.get('http://vnpsub.mytalk.vn/header', {},
        function (resp_data) {
            $.ajax({
                type: "POST",
                url: "/check_alive",
                dataType:"json",
                contentType: "application/json",
                data: resp_data,
                beforeSend: function(){

                },
                success: function(response){
                    let objResp = response;
                    if(objResp.code == "1") {
                        // alert("1");
                        if(objResp.data.msisdn != '' && objResp.data.msisdn != null) {
                            $('.btn-action').hide();
                            let msisdn = objResp.data.msisdn;
                            // console.log(msisdn);
                            $('.welcome .welcome-text').html("Hi! 0" + msisdn);
                            $('.mwelcome .welcome-text').html("Hi! 0" + msisdn);
                            $('.mcode').html(objResp.data.code);
                            $('.welcome').show();
                            $('.mwelcome').show();
                        }
                    } else {
                        // alert("0");
                        $.get('http://vmssub.mytalk.vn/header', {},
                            function (resp_data2) {
                                $.ajax({
                                    type: "POST",
                                    url: "/check_alive",
                                    dataType:"json",
                                    contentType: "application/json",
                                    data: resp_data2,
                                    beforeSend: function(){

                                    },
                                    success: function(response2){
                                        let objResp2 = response2;
                                        // alert(response.code);
                                        if(objResp2.code == "1") {
                                            if(objResp2.data.msisdn != '' && objResp2.data.msisdn != null) {
                                                $('.btn-action').hide();
                                                let msisdn = objResp2.data.msisdn;
                                                // console.log(msisdn);
                                                $('.welcome .welcome-text').html("Hi! 0" + msisdn);
                                                $('.mwelcome .welcome-text').html("Hi! 0" + msisdn);
                                                $('.mcode').html(objResp2.data.code);
                                                $('.welcome').show();
                                                $('.mwelcome').show();
                                            }
                                        } else {

                                        }
                                    }
                                });
                            }, 'text');
                    }
                }
            });
        }, 'text');
});

function mlogout(){
    $.ajax({
        type: "POST",
        url: "/mlogout",
        dataType:"json",
        contentType: "application/json",
        data: null,
        beforeSend: function(){
            $.blockUI({
                baseZ: 2000,
                message: '<div class="spinner-border text-white" role="status"></div>',
                timeout: 10000,
                css: {
                    backgroundColor: 'transparent',
                    border: '0'
                },
                overlayCSS: {
                    opacity: 0.5
                }
            });
        },
        success: function(response){
            $.unblockUI();
            let objResp = response;
            // console.log(objResp);
            if(objResp.code == "1") {
                $('.welcome').hide();
                $('.welcome').attr("style", "display:none !important");
                $('.mwelcome').hide();
                $('.mwelcome').attr("style", "display:none !important");
                $('.mcode').html("");
                $('.btn-action').show();
            }
        }
    });
}
$(".toggle-icon").click(function() {
    $(this).toggleClass("fa-eye fa-eye-slash");
    var input = $('#password');
    if (input.attr("type") == "password") {
        input.attr("type", "text");
    } else {
        input.attr("type", "password");
    }
});

$('#loginModal').on('hidden.bs.modal', function () {
    $("#frmLogin #username").val('');
    $("#frmLogin #password").val('');
})