var articleList;
var sid,token;
jQuery(document).ready(function($) {
    sid=$("#sid").val();
    token=getCookie(sid+"_stoken");
    $.get('/findSnameBySid', {uid:sid,token:token},function(data) {
    	$("#sname").text(data+",你好");
    });
    nav_articles_click();
    $("#nav_articles").click(nav_articles_click);

    $("#nav_write").click(function(){
//        $("#navBox>li").removeClass('active');
//        $(this).addClass('active');
//        $(".list_dataBox").addClass("hidden");
//        $("#writeArticle").removeClass("hidden");
        window.location.href="/writeArticlePage?uid="+sid+"&token="+token;
    });

});

function nav_articles_click(){
    $("#navBox>li").removeClass('active');
    $(this).addClass('active');
    $(".list_dataBox").addClass("hidden");
    $("#articleLists").removeClass("hidden");
    getArticleList();
}

function getArticleList(){//获取文章
    $.ajax({
    		url: '/getAllArticlesBySid',
    		dataType: 'json',
    		data: {uid: sid,token:token,sid:sid},
    	})
    	.done(function(list) {
    		console.log("success");
//    		console.log(list);
    		articleList=list;
    	})
    	.fail(function() {
    		console.log("error");
    	})
    	.always(function() {//显示文章s
            if(articleList.length==0) {
                $("#articleLists").html("你还没有文章");
                return;
            }
            $("#articleLists").html("");
            for(var i=0;i<articleList.length;i++){
                let a=articleList[i];
                $("#articleLists").append("<li class='list-group-item' onclick='articleClick("+i+")'>"+a.title+"</li>");
            }
    		console.log("complete");
    	});
}

function articleClick(i){//文章项点击
    $("#info_title").text(articleList[i].title);
    let hdata="<div id='publisherBox'><span>发布者:"+articleList[i].name+"</span><span class='small_info'>关键词:"+articleList[i].keyword+"</span><span class='small_info'>发布日期:"+articleList[i].p_date+"</span></div>"+articleList[i].content;
    $("#info_data").html(hdata);
}

function getCookie(key){//获取cookie
    var arr1=document.cookie.split("; ");//由于cookie是通过一个分号+空格的形式串联起来的，所以这里需要先按分号空格截断,变成[name=Jack,pwd=123456,age=22]数组类型；
    for(var i=0;i<arr1.length;i++){
        var arr2=arr1[i].split("=");//通过=截断，把name=Jack截断成[name,Jack]数组；
        if(arr2[0]==key){
            return decodeURI(arr2[1]);
        }
    }
}