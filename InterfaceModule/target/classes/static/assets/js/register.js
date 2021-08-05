jQuery(document).ready(function($) {
    $("#verifyCode").click(function(event) {
        var src = "verifyCode?t="+new Date().getTime(); //加时间戳，防止浏览器利用缓存
        $('#verifyCode').attr("src",src);
    });
    $("#register").click(function(){
            var data = $("form").serialize();
            $.ajax({
                url: '/userRegister',
                type: 'POST',
                dataType: 'json',
                data: data,
            })
            .done(function(response) {
                if(response.success==true){
                    alert(response.msg);
                    window.location.href="/login";//重定向
                } else{
                    $("#msgBox").addClass("in");
                    $("#msg").text(response.msg);
                 }
                console.log("success");
            })
            .fail(function() {
                $("#msgBox").addClass("in");
                $("#msg").text("请求服务器失败！");
                console.log("error");
            })
            .always(function() {
                console.log("complete");
            });
    });
});

function checkName(){//检测用户输入的用户名是否存在
    let iName = $("input[name='uname']").val();
//    console.log(iName);
    if(iName==""){
        $("#msgBox").addClass("in");
        $("#msg").text("用户名为空");
    }else {
        $.get('/checkNameExist', {name:iName},function(data){
//            console.log(data);
            if(!data.success){
                $("#msgBox").addClass("in");
                $("#msg").text(data.msg);
            }else{
                $("#msgBox").removeClass("in");
            }
        });
    }
}

function sendMailCode(){
    let mail=$("input[name='mail']").val();
    $.get('/sendMailCode', {mail:mail},function(){
        alert("已向你的邮箱发送验证码");
    });
}