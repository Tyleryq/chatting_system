var uid;
var token;
var msgLists=new Array();
var friendLists;
var teamList;
var subscriptionLists;
var zoneLists;

jQuery(document).ready(function($) {
    uid=$("#uid").val();
    token=getCookie(uid+'_token');//获取uid和token
    $.get('/findUnameByUid', {uid: uid,token:token}, function(json) {
        $("#uname").html(json+",你好");
        //console.log("name:"+json);
    });
	$("#createTeam").attr("href","/createTeamPage?uid="+uid+"&token="+token);
	$("#myQRCode").attr("href","/createUserQRCode?uid="+uid+"&token="+token);
	$("#msgs").click(msgNavClick);
	$("#friends").click(friendsNavClick);
    $("#subscriptions").click(subscriptionsNavClick);
	$("#zones").click(zoneNavClick);
});
setInterval("heart()", 3000);//发送心跳
function heart(){
    $.ajax({        //读取未读消息
    	url: '/receive',
    	dataType: 'json',
    	data: {uid: uid,token:token,receiver_id:uid},
    })
    .done(function(msg) {
//    	console.log("success");
        if(msg!=null){
//            console.log(msg);
            let i = msgLists.push(msg)-1;   //返回数组长度,数组访问从0开始
            if(msg.message.type==0)
                $("#msgLists").append("<li class='list-group-item' onclick='msgClick("+i+")'>"+msg.senderName+"的消息["+msg.message.sendTime+"]</li>");
            else if(msg.message.type==1){
                $("#msgLists").append("<li class='list-group-item' onclick='msgClick("+i+")'>"+msg.senderName+"更新了文章["+msg.message.sendTime+"]</li>");
            } else if(msg.message.type==2)
                $("#msgLists").append("<li class='list-group-item' onclick='msgClick("+i+")'>"+msg.senderName+"发布了朋友圈["+msg.message.sendTime+"]</li>");
            else if(msg.message.type==7)
                $("#msgLists").append("<li class='list-group-item' onclick='msgClick("+i+")'>"+msg.teamName+"的消息["+msg.message.sendTime+"]</li>");
            else{
                $("#msgLists").append("<li class='list-group-item' onclick='msgClick("+i+")'>"+msg.senderName+"的消息["+msg.message.sendTime+"]</li>");
            }
        }
    })
    .fail(function() {
//    	console.log("error");
    })
    .always(function() {
//    	console.log("complete");
    });

    $.get('/updateUserStatus', {uid:uid,token:token});//更新状态
}

function msgClick(i){//消息项点击
    $("#info_title").text(msgLists[i].senderName);
    if(msgLists[i].message.type==0){
        $("#info_title").text(msgLists[i].senderName);
        $("#info_data").html("<li class='list-group-item'><h5><b>"+msgLists[i].senderName+":</b>"+msgLists[i].message.content+"<small>"+msgLists[i].message.sendTime+"</small></h5></li>");
        $("#msgReceiver").val(msgLists[i].message.sender);
        $("#sendBtn").attr("onclick","sendMsg()");
        $("#sendBtn").text("发送好友消息");
    } else if(msgLists[i].message.type==3){
        let hdata="<li class='list-group-item'>"+msgLists[i].senderName+"请求加为好友,是否同意?<br><button type='button' class='btn btn-success' onclick='agreeFriend("+i+")'>同意</button><button type='button' class='btn btn-danger' onclick='disagree("+i+")'>拒绝</button></li>";
        $("#info_data").html(hdata);
    } else if(msgLists[i].message.type==7){
        $("#info_data").html("<li class='list-group-item'><h5><b>"+msgLists[i].senderName+":</b>"+msgLists[i].message.content+"<small>"+msgLists[i].message.sendTime+"</small></h5></li>");
        $("#msgReceiver").val(msgLists[i].message.t_s_Id);
        $("#sendBtn").attr("onclick","sendTeamMsg()");
        $("#sendBtn").text("发送群消息");
    }
     else{
        $("#info_data").html("<li class='list-group-item'><h5><b>"+msgLists[i].senderName+"</b>"+msgLists[i].message.content+"<small>"+msgLists[i].message.sendTime+"</small></h5></li>");
    }

}

function agreeFriend(i){
    $.post('/addFriendReply', {uid: uid,token:token,adder_id:msgLists[i].message.sender,isAgree:'y'});
    alert("已同意");
}

function disagree(i){
    $.post('/addFriendReply', {uid: uid,token:token,adder_id:msgLists[i].message.sender,isAgree:'n'});
    alert("已拒绝");
}

function msgNavClick(){//消息导航点击
    $("#navBox>li").removeClass('active');
    $(this).addClass('active');
    $("#info").css("height","80%");//改变样式
    $(".list_dataBox").addClass("hidden");
    $("#msgLists").removeClass("hidden");
    $("#inputBox").removeClass("hidden");//显示编辑框
    $("#info_title").html("");
    $("#info_data").html("");//清除面板数据
    $("#sendBtn").text("发送");
}

function friendsNavClick(){ //好友导航点击
    $("#navBox>li").removeClass('active');
    $(this).addClass('active');
    $("#info").css("height","100%");//改变样式
    $(".list_dataBox").addClass("hidden");
    $("#friendLists").removeClass("hidden");
    $("#inputBox").addClass("hidden");
    $("#info_title").html("");
    $("#info_data").html("");//清除面板数据
    getFriendAndTeamList(uid,token);//请求好友和群列表并显示
}

function subscriptionsNavClick(){//公众号导航点击
    $("#navBox>li").removeClass('active');
    $(this).addClass('active');
    $("#info").css("height","100%");//改变样式
    $(".list_dataBox").addClass("hidden");
    $("#subscriptionLists").removeClass("hidden");
    $("#subscriptionLists").html("");
    $("#inputBox").addClass("hidden");
    $("#info_title").html("");
    $("#info_data").html("");//清除面板数据
    getSubscriptionList(uid,token);//请求公众号列表并显示
}

function zoneNavClick(){//朋友圈导航点击
    $("#navBox>li").removeClass('active');
    $(this).addClass('active');
    $("#info").css("height","80%");//改变样式
    $(".list_dataBox").addClass("hidden");
    $("#zoneLists").removeClass("hidden");
    $("#inputBox").removeClass("hidden");
    $("#sendBtn").attr("onclick","publishZone()");
    $("#sendBtn").text("发布朋友圈");
    $("#info_title").html("好友动态");
    $("#info_data").html("");//清除面板数据
    getZoneList(uid,token);
}

function search(){
    let id=$("#id_input").val();
    $("#id_input").val("");
    $.ajax({
    	url: '/search',
    	dataType: 'json',
    	data: {uid: uid,token:token,id:id},
    })
    .done(function(result) {
    	console.log("success");
    	$("#info_title").text("搜索结果");
        let userData="<li class='list-group-item'>未找到用户</li>";
        let teamData="<li class='list-group-item'>未找到群聊</li>";
        let subscriptionData="<li class='list-group-item'>未找到公众号</li>";

        if(result.user!=null){
            let sex;
            if(result.user.sex=='m') sex='男';else sex='女';
    	    userData="<li class='list-group-item'><span class='text-center col-sm-12'><b>用户</b></span><br><br>账号:"+result.user.uid+"<br>用户名:"+result.user.uname+"<br>性别:"+sex+"<br>注册日期:"+result.user.register_date+"<br>邮箱:"+result.user.mail+"<br><br><br><button type='button' class='btn btn-success' onclick='addFriendRequest("+result.user.uid+")'>添加好友</button><button type='button' class='btn btn btn-success'>查看空间</button></li>";
        }
        if(result.team!=null)
            teamData="<li class='list-group-item'><span class='text-center col-sm-12'><b>群聊</b></span><br><br>群号:"+result.team.team_id+"<br>群名称:"+result.team.t_name+"<br>群主:"+result.teamCreatorName+"<br>简介:"+result.team.descript+"<br>创建日期:"+result.team.create_time+"<br><br><br><button type='button' class='btn btn-success' onclick='joinTeam("+result.team.team_id+")'>加入群聊</button></li>";
        if(result.subscription!=null)
            subscriptionData="<li class='list-group-item'><span class='text-center col-sm-12'><b>公众号</b></span><br><br>账号:"+result.subscription.s_id+"<br>公众号名称:"+result.subscription.s_name+"<br>公司:"+result.subscription.company+"<br>工商注册码:"+result.subscription.company_code+"<br>简介:"+result.subscription.description+"<br>注册日期:"+result.subscription.r_date+"<br><br><br><button type='button' class='btn btn-success' onclick='subscribe("+result.subscription.s_id+")'>添加关注</button><button type='button' class='btn btn btn-success'>查看文章</button></li>";
        $("#info_data").html(userData+teamData+subscriptionData);
    })
    .fail(function() {
    	console.log("error");
    })
    .always(function() {
    	console.log("complete");
    });
}

function sendMsg(){
    let receiver=$("#msgReceiver").val();
    let content=$("#textMsg").val();
    $("#textMsg").val("");
    $.ajax({
        url: '/sendFriendMessage',
        data: {uid: uid,token:token,sender:uid,receiver:receiver,content:content},
    })
    .done(function() {
        console.log("success");
        let now=new Date;
        let str_now=now.getFullYear()+"-"+now.getMonth()+1+"-"+now.getDate()+"-"+now.getHours()+":"+now.getMinutes();
        $("#info_data").append("<li class='list-group-item'><h5><b>我:</b>"+content+"<small>"+str_now+"</small></h5></li>");
    })
    .fail(function() {
        console.log("error");
    })
    .always(function() {
        console.log("complete");
    });
}

function sendTeamMsg(){//发送群消息
    let tid=$("#msgReceiver").val();
    let content=$("#textMsg").val();
    $("#textMsg").val("");
    $.post('/sendTeamMessage', {uid: uid,token:token,sender:uid,tid:tid,content:content},function(){
    let now=new Date;
        let str_now=now.getFullYear()+"-"+now.getMonth()+1+"-"+now.getDate()+"-"+now.getHours()+":"+now.getMinutes();
        $("#info_data").append("<li class='list-group-item'><h5><b>我:</b>"+content+"<small>"+str_now+"</small></h5></li>");
    });
}

function addFriendRequest(fid){
//    console.log(fid);
    $.post('/addFriendSend', {uid: uid,token:token,friend_id:fid},function(data){
        alert(data.msg);
    });

}

function joinTeam(tid){
    $.post('/joinTeam', {uid: uid,token:token,t_id:tid},function(){
        alert("操作成功!");
    });
}

function subscribe(sid){
    $.post('/subscribe', {uid: uid, token:token, sid:sid});
    alert("操作成功");
}

function getFriendAndTeamList(puid,ptoken) {
	// console.log("fun");
	$.ajax({
		url: '/getFriendList',
		type: 'POST',
		dataType: 'json',
		async:false,
		data: {uid: puid,token:ptoken},
	})
	.done(function(list) {
		console.log("success");
//		console.log(list);
		friendLists=list;
	})
	.fail(function() {
		console.log("error");
		alert("请求失败!");
	})
	.always(function() {//显示好友
//        console.log(friendLists);
        if(friendLists.length==0) {
            $("#friendLists").html("你还没有好友");
            return;
        }
        $("#friendLists").html("<li class='list-group-item'  style='height: 20px;padding-top: 0;background-color: gray;'>我的好友:</li>");
        for(var i=0;i<friendLists.length;i++){
            let f=friendLists[i];
            let isOnlineStr;
            if(f.online)  isOnlineStr="在线";   else    isOnlineStr="离线";//console.log(f);
            $("#friendLists").append("<li class='list-group-item' onclick='friendClick("+i+");'>"+f.uname+"["+isOnlineStr+"]<span></span></li>");
        }
		console.log("complete");
	});

	$.ajax({
    	url: '/getTeamList',
    	type: 'GET',
    	dataType: 'json',
    	async:false,
    	data: {uid:uid,token:token},
    })
    .done(function(teams) {
        teamList=teams;
//        console.log(uid);
//        console.log(teams);
    	console.log("success");
    	$("#friendLists").append("<li class='list-group-item' style='height: 20px;padding-top: 0;background-color: gray;'>我的群聊:</li>");
    	for(var i=0;i<teams.length;i++){
    	    $("#friendLists").append("<li class='list-group-item' onclick='teamClick("+i+")'>"+teams[i].team.t_name+"</li>");
    	}
    })
    .fail(function() {
    	console.log("error");
    	alert("请求失败!");
    });
}

function friendClick(i){//好友项点击
//    console.log("click"+i);
    $("#info_title").text(friendLists[i].uname);
    let sex;
    if(friendLists[i].sex=='m') sex='男';else sex='女';
    let hdata1="账号:"+friendLists[i].uid+"<br>用户名:"+friendLists[i].uname+"<br>性别:"+sex+"<br>注册日期:"+friendLists[i].register_date+"<br>邮箱:"+friendLists[i].mail+"<br>分组:<input type='text' name='friendGroup' id='"+i+"_group' value='"+friendLists[i].group+"'><button onclick='changeGroup("+i+")'>修改</button><br>";
    let hdata2="朋友圈权限:<input type='radio' name='zonePermission' id='"+i+"_EnZone' onclick='enableZonePermission("+i+")'>允许查看<input type='radio' name='zonePermission' id='"+i+"_DisZone' onclick='disableZonePermission("+i+")'>不允许查看<br><br><br><br><br><br>";
    let hdata3="<button type='button' class='btn btn-success col-sm-3' onclick='toSendMsg("+i+")'>发消息</button><button type='button' class='btn btn-success col-sm-3'><a target='_blank' href='/zoneListPage?uid="+uid+"&token="+token+"&fid="+friendLists[i].uid+"'>查看空间</a></button><button type='button' class='btn btn btn-danger col-sm-3' onclick='delFriend("+i+")'>删除</button><button type='button' class='btn btn btn-danger col-sm-3' onclick='addBlock("+i+")'>加入黑名单</button>";
    $("#info_data").html(hdata1+hdata2+hdata3);
    if(friendLists[i].zone_permission==true)    $("#"+i+"_EnZone").attr('checked', 'checked');
    else    $("#"+i+"_DisZone").attr('checked', 'checked');
}

function teamClick(i){//群聊项点击
    $("#info_title").text(teamList[i].team.t_name);
    let hdata1="群号:"+teamList[i].team.team_id+"<br>群名称:"+teamList[i].team.t_name+"<br>群主:"+teamList[i].creator_name+"<br>群人数:"+teamList[i].team_num+"<br>创建时间:"+teamList[i].team.create_time+"<br>简介:"+teamList[i].team.descript+"<br><br><br><br><br><br><br>";
    let hdata2="<button type='button' class='btn btn-success col-sm-6' onclick='toSendTeamMsg("+i+")'>发消息</button><button type='button' class='btn btn btn-danger col-sm-6' onclick='quitTeam("+teamList[i].team.team_id+")'>退出该群</button>";
    $("#info_data").html(hdata1+hdata2);
}

function toSendMsg(i){
    $("#msgReceiver").val(friendLists[i].uid);
    $("#sendBtn").attr("onclick","sendMsg()");
    msgNavClick();
    $("#info_title").html(friendLists[i].uname);
}

function toSendTeamMsg(i){
    $("#msgReceiver").val(teamList[i].team.team_id);
    $("#sendBtn").attr("onclick","sendTeamMsg()");
    msgNavClick();
    $("#info_title").text(teamList[i].team.t_name);
}

function changeGroup(i){
    let group=$("#"+i+"_group").val();
    let fid=friendLists[i].uid;
    $.post('/setFriendGroup', {uid: uid,token:token,fid:fid,group:group});
}

function enableZonePermission(i){
    let fid=friendLists[i].uid;
    $.post('/changeZonePermission', {uid: uid,token:token,fid:fid,permission:true},function(){
        alert("操作成功");
    });
}

function disableZonePermission(i){
    let fid=friendLists[i].uid;
    $.post('/changeZonePermission', {uid: uid,token:token,fid:fid,permission:false},function(){
        alert("操作成功");
    });
}

function delFriend(i){
    let fid=friendLists[i].uid;
        $.post('/deleteFriend', {uid: uid,token:token,fid:fid},function(){
        friendsNavClick();//刷新页面
    });
}

function addBlock(i){
    $.post('/addBlock', {uid: uid, token:token, b_id:friendLists[i].uid},function(){
        alert("操作成功!");
        friendsNavClick();
    });
}

function quitTeam(tid){
    $.post('/quitTeam', {uid: uid,token:token,t_id:tid},function(){
        alert("操作成功!");
        msgNavClick();
    });
}

function getSubscriptionList(puid,ptoken){//获取已关注公众号列表
    $.ajax({
        url: '/getSubscriptionsByUid',
        dataType: 'json',
        data: {uid: puid,token:ptoken},
    })
    .done(function(list) {
        console.log("success");
        subscriptionLists=list;
    })
    .fail(function() {
        console.log("error");
    })
    .always(function() {
        console.log("complete");
        if(subscriptionLists.length==0)
            $("#subscriptionLists").text("你还没有关注公众号");
        for(var i=0;i<subscriptionLists.length;i++){
            let s=subscriptionLists[i];
            $("#subscriptionLists").append("<li class='list-group-item' onclick='subscriptionClick("+i+");'>"+s.s_name+"</li>");
        }
    });

}

function subscriptionClick(i){//公众号选项点击
    $("#info_title").text(subscriptionLists[i].s_name);
    let hdata="账号:"+subscriptionLists[i].s_id+"<br>公众号名称:"+subscriptionLists[i].s_name+"<br>公司:"+subscriptionLists[i].company+"<br>工商注册码:"+subscriptionLists[i].company_code+"<br>简介:"+subscriptionLists[i].description+"<br>注册日期:"+subscriptionLists[i].r_date+"<br><br><br><br><br><br><br><button type='button' class='btn btn-success col-sm-6'><a href='/articleListPage?uid="+uid+"&token="+token+"&sid="+subscriptionLists[i].s_id+"'>查看文章</a></button><button type='button' class='btn btn btn-danger col-sm-6' onclick='offSubscribe("+subscriptionLists[i].s_id+")'>取消关注</button>";
    $("#info_data").html(hdata);
}

function offSubscribe(sid){
    $.post('/offSubscribe', {uid: uid, token:token, sid:sid},function(){
        alert("操作成功");
        subscriptionsNavClick();
    });

}

function getZoneList(puid,ptoken){//获取好友朋友圈
    $.ajax({
        url: '/getFriendsZones',
        dataType: 'json',
        data: {uid: puid,token:ptoken},
    })
    .done(function(list) {
        console.log("success");
        zoneLists=list;
//        console.log(list);
    })
    .fail(function() {
        console.log("error");
    })
    .always(function() {
        console.log("complete");
        if(zoneLists.length==0) $("#info_data").html("没有动态");
        for(var i=0;i<zoneLists.length;i++){
            let likes=zoneLists[i].likes;
            let likesString="";
            for(var j=0;j<likes.length;j++){//加载点赞人姓名
                likesString+=likes[j]+",";
            }
            let comments=zoneLists[i].zoneComments;
            let commentsString='';
            for(var j=0;j<comments.length;j++){//加载评论
                commentsString+=comments[j].uname+':'+comments[j].content+'<br>';
            }
            let hdata="<li class='list-group-item'><span class='zone_uname'>"+zoneLists[i].uname+"</span><br>发表于"+zoneLists[i].zone.p_date+"<br>"+zoneLists[i].zone.content+"<br><br><span class='glyphicon glyphicon-thumbs-up' onclick='likeZone("+i+")'/>&nbsp;&nbsp;"+likesString+"<span id='"+i+"_likeNum'>"+zoneLists[i].likeNum+"</span>人觉得很赞<br>"+commentsString+"<div class='input-group'><input type='text' id='"+i+"_zone' class='form-control' placeholder='输入评论' /><span class='input-group-btn'><button class='btn btn-default' type='button' onclick='addComment("+i+")'>评论</button></span></div></li>";
            $("#info_data").append(hdata);
        }
    });
}

function likeZone(i){
    $("#"+i+"_likeNum").text(zoneLists[i].likeNum+1);
    $.post('/likeZone', {uid: uid, token:token,zid:zoneLists[i].zone.zone_id});
}

function addComment(i){
    let content=$("#"+i+"_zone").val();
    $("#"+i+"_zone").val("");
    $.post('/addComment', {uid: uid,token:token,zid:zoneLists[i].zone.zone_id,content:content},function(){
        zoneNavClick();//刷新页面
    });

}

function publishZone(){
    let content=$("#textMsg").val();
    $("#textMsg").val("");
    $.post('/publishZone', {uid: uid,token:token,content:content},function(){
        zoneNavClick();
    });
}

function seeBlocks(){
    $.get('/getBlocks', {uid:uid,token:token},function(data){
        let blocks="";
        if(data.length==0)  blocks="未添加";
        for(var i=0;i<data.length;i++){
            blocks+=data[i]+" ";
        }
//        console.log(blocks);
        alert("黑名单:\r\n"+blocks);
    });
}

function deleteAccount(){
    $.get('/deleteAcount', {uid:uid,token:token},function(){
        alert("操作成功");
        window.location.href="/login";
    });
}

function QRCodeUpClick(){
	$("#QRCodeUp").click();
}

function upQRCode(){
	console.log('up QR');
	var form = document.getElementById('QRCodeForm'),
        formData = new FormData(form);
	$.ajax({
    	url: '/decodeQRCode',
    	type: 'POST',
        processData:false,
        contentType:false,
    	data: formData,
    })
    .done(function() {
    	console.log("success");
    	alert("成功发送加好友请求");
    })
    .fail(function() {
    	console.log("error");
    });
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