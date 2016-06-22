
/*
 * 
 * 模块收缩
 * */
!function ($) {
	    $(document).on("click","ul.nav li.parent > a", function(){          
	        $(this).find('em:first').toggleClass("fa-minus");      
	    }); 
	    $(".sidebar span.icon").find('em:first').addClass("fa-plus");
	}(window.jQuery);
/*
 * 菜单栏自适应
 * */
	
$(window).on('resize', function () {
	  if ($(window).width() > 768) $('#sidebar-collapse').collapse('show')
	})
	$(window).on('resize', function () {
	  if ($(window).width() <= 767) $('#sidebar-collapse').collapse('hide')
	})
	//设置首页

function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }

$(function () {
//    mainHeight = $(document.body).height() - 45;
//    $('.main-left,.main-right').height(mainHeight);
//    $("[addtabs]").click(function () {
//        addTabs({id: $(this).attr("id"), title: $(this).attr('title'), close: true});
//    });
 
    $(".nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        closeTab(id);
    });
});

function mainFrameResize(){
	var navHeight = $("#navbar").height();
	//var mainHeight=$("iframe").contentDocument.body.scrollHeight;
	var mainHeight = $(window).height()-navHeight-105;
	$("iframe").height(mainHeight);
}

jQuery(function($) {
	$.ajax({
		type: "POST",
		   url: baseUrl+"get/userInfo",
		   async: false,
		   success: function(data){
			   $.ajax({
				   type: "POST",
				   url: baseUrl+"user/getMenuByUserId",
				   data:{"id":data.rows.userId},
				   async: false,
				   success: function(response){
							     if(response.status==0){
							    	// 菜单
					 				initMenu(response.rows.childNode);
					 				// 菜单点击事件
					 				initClick();
					        		var home_url="home.html";
						    		var setting ={
						    				id:"home",
						    				title:"首页",
						    				icon :"fa-home",
						    				url:home_url,
						    				close:false
						    		};
						    		addTabs(setting);
					    	 	} else {
					    	 		if(console)
					    	 		console.log("加载错误，请联系系统管理员！");
					    	 	}
				  }
			});
		   }
	})
	
});

// 初始化菜单
function initMenu(data){
	$.each(data,function(i,menu){
		var menu_li = getMenu(menu);
		$("#menu").append(menu_li);
	});
}

function getMenu(menu){
	var menu_li = "";
	var url=(menu.url.lastIndexOf("#")!=-1||menu.url.lastIndexOf("http")!=-1)?menu.url:baseUIUrl+menu.url;
	if(menu.childNode.length>0){
		menu_li+="<li id='"+menu.id+"' class='parent'>";
		menu_li +=" <a lang='"+url+"' href='#subMenu"+menu.childNode[0].id+"' data-toggle='collapse'><i class='fa "+menu.icon+"' icon='"+menu.icon+"' ></i> <span>"+menu.name+"</span> <i class='icon pull-right'><em class='fa fa-s fa-plus'></em></i> </a>";
//		menu_li +=" <b class='arrow'></b> ";
		menu_li +=" <ul class='children collapse' id='subMenu"+menu.childNode[0].id+"'> ";
		$.each(menu.childNode,function(j,cmenu){
			menu_li += getMenu(cmenu);
		});
		menu_li +=" </ul> ";
	}else{
		menu_li+="<li id='"+menu.id+"' >";
		menu_li +=" <a href='javascript:void(0)' lang='"+url+"' ><i class='fa "+menu.icon+"' icon='"+menu.icon+"' ></i> <span>"+menu.name+"</span> </a>";
	}
	menu_li +='</li>';
	return menu_li;
}

//初始化菜单事件 a
function initClick(){
	/* $(document).on('click', '.nav a[href !="#"]', function(e) {*/
	 $(document).on('click', '.sidebar .nav a[href]', function(e) {
		e.preventDefault();
		// a 标签的父 li
		var childrenId = $(this).parent().attr("id");
		
		var title = $(this).children('span').text();
		var icon = $(this).children('i').attr("icon");
		var url = $(this).attr('lang');
		if(url != '#'){
			var close = childrenId == 'home'? false:true;
			var setting ={
					id:childrenId,
					title:title,
					url : url,
					icon : icon,
					close:close
			};
			addTabs(setting);
		} 
	 });
}


