jQuery(document).ready(function($) {
    $("#verifyCode").click(function(event) {
    var src = "verifyCode?t="+new Date().getTime(); //加时间戳，防止浏览器利用缓存
        $('#verifyCode').attr("src",src);
    });
    $("#register").click(function(){
        let data=$("form").serialize();
        $.ajax({
            url: '/SubscriptionRegister',
            type: 'POST',
            dataType: 'json',
            data: data,
        })
        .done(function(response) {
            console.log("success");
            if(response.success==true){
                alert(response.msg);
                window.location.href="/login";//重定向
            } else{
                $("#msgBox").addClass("in");
                $("#msg").text(response.msg);
             }
        })
        .fail(function() {
            console.log("error");
            $("#msgBox").addClass("in");
            $("#msg").text("请求服务器失败");
        }).always(function() {
            console.log("complete");
        });
    });
});