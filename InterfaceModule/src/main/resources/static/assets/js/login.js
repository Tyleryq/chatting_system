jQuery(document).ready(function($) {
	$("#login").click(function(event) {
	    console.log('login click');
		var data = $("form").serialize();
		$.ajax({
			url: '/loginCheck',
			type: 'POST',
			dataType: 'json',
			data: data,
		})
		.done(function(response) {
		    if(response.success==true){
		        var date=new Date();
		        date.setTime(date.getTime()+7*24*60*60*1000);
		        var uid = $("#uid").val();
		        let account_type=$("input[name='account_type']:checked").val();
//		        alert(account_type);
		        if(account_type=="person"){
                    document.cookie=uid+"_token="+response.msg+";expires="+date.toGMTString();//设置cookie
                    window.location.href="/main?uid="+uid+"&token="+response.msg;//重定向
		        }else{
                    document.cookie=uid+"_stoken="+response.msg+";expires="+date.toGMTString();//设置cookie
                    window.location.href="/subscriptionMain?uid="+uid+"&token="+response.msg;//重定向
                }
		    }
            else{
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