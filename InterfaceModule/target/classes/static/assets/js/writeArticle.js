var sid,token;
jQuery(document).ready(function($) {
    sid=$("#sid").val();
    token=getCookie(sid+"_stoken");

});

function publish(){
    let title=$("#title").val();
    let content=$("#htmlContent").val();
    let keyword=$("#keyword").val();
//    console.log(sid);
    $.post('/publishArticle', {uid: sid,token:token,title:title,content:content,keyword:keyword},function(){
        console.log("success");
        alert("发布成功!");
    });
}

function cancel(){
    window.opener = null;
    window.open('', '_self');
    window.close();
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