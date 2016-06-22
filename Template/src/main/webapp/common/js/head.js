//获取用户名
$.ajax({
	   type: "GET",
	   url: baseUrl+"get/userInfo",
	   async: false,
	   cache: false,
	   success: function(data){
		   if(data.status==0){
			   $("#userName").text(data.rows.userName);
		   }else{
			   alert("您还未登录，即将跳转至登录页面!");
			   window.location.href=baseUIUrl+"login.html"
		   }
	   }
});
//修改密码
$("#modifyPassword").click(function () {
		var setting ={
				id:"-1",
				title:"修改密码",
				icon :"fa-pencil",
				url:"../user/password_modify.html",
				close:true
		};
		addTabs(setting);
});

//注销
//用户退出
$("#loginOut").click(function(){
	$.get(baseUrl+"user/loginOut",function(data){
		window.location.href=baseUIUrl+"login.html";
		})
})