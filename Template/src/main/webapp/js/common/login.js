document.getElementById("verify").src = baseUrl+"get/createVerifyCode";
    var isClick = 0;
    function changeImg() {
    	if (isClick < 5) {
    		document.getElementById("verify").src = baseUrl+"get/createVerifyCode?timestamp="
    				+ new Date().getTime();
    		isClick++;
    	} else {
    		alert("请勿频繁刷新！");
    	}

    }
    //背景图片
    jQuery(function(){
		jQuery('#camera_wrap_4').camera({
			height : 'auto',
				loader : 'bar',
				pagination : false,
				thumbnails : false,
				hover : false,
				playPause : false,
				navigation : false,
				opacityOnGrid : true
		});

	});
  	//取消保存密码
    function savePassword() {
    		var obj=$("#saveid").get(0);
    		if (!obj.checked) {
    			$("#userAccount").val('');
    			$("#password").val('');
    		}
    	}
     //查看cookie中是否存放用户信息
       $.ajax({
      		type:"post",
      		url:baseUrl+"get/getUserAccount",
      		//async:false,
      		success:function(response){
      			if(response.status==0){
      				var loginname = response.rows
      	  		   var password = "sdfdsffsd";
      	  		   if (typeof(loginname) != "undefined") {
      		  		   	$("#userAccount").val(loginname);
      		  		   	$("#password").val(password);
      		  		   	$("#saveid").attr("checked", true);
      	  		   }
      			}
      		}
    })
    $("#saveid").bind("click", function() {
    	savePassword();
    });
    $("#login").bind("click", function() {
    	if(check()){
    		var vuserAccount = $("#userAccount").val();
    		var vpassword = $("#password").val();
    		var visSavePassword=$("#saveid").get(0).checked;
    		var vverifyCode=$("#verifyCode").val();
    					$.ajax({
    						type: "POST",
    						url: baseUrl+'user/login',
    				    	data: {userAccount:vuserAccount,password:vpassword,savePassword:visSavePassword,verifyCode:vverifyCode},
    						cache: false,
    						success: function(data){
    							if(0 == data.status){
    									window.location.href=baseUIUrl+"index.html";
    							}else {
    								var msg  = data.message;
    								$("#login").tips({
    									side : 1,
    									msg : msg,
    									bg : '#FF5080',
    								});
    							}
    						},error:function(){
    							$("#login").tips({
    								side : 1,
    								msg : "网络出错，请检查！",
    								bg : '#FF5080',
    							});
    						}
    					});
    	}
    });

    function check() {
    	if ($("#userAccount").val() == "") {
    		$("#userAccount").tips({
    			side : 2,
    			msg : '用户名不得为空！',
    			bg : '#AE81FF',
    			time : 3
    		});

    		$("#userAccount").focus();
    		return false;
    	} else {
    		$("#userAccount").val(jQuery.trim($('#userAccount').val()));
    	}

    	if ($("#password").val() == "") {

    		$("#password").tips({
    			side : 2,
    			msg : '密码不得为空！',
    			bg : '#AE81FF',
    			time : 3
    		});

    		$("#password").focus();
    		return false;
    	}
    	if ($("#verifyCode").val() == "") {

    		$("#verifyCode").tips({
    			side : 2,
    			msg : '验证码不得为空！',
    			bg : '#AE81FF',
    			time : 3
    		});

    		$("#verifyCode").focus();
    		return false;
    	}

    	$("#login-box").tips({
    		side : 1,
    		msg : '正在登录 , 请稍后 ...',
    		bg : '#68B500',
    			time : 5
    		});

    		return true;
    }
    	
    	
    $(document).keyup(function(event) {
    	if (event.keyCode == 13) {
    		$("#login").trigger("click");
    	}
    });