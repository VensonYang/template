
function resizeIframe(obj) {
    obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
  }

var addTabs = function (obj) {
    id = "tab_" + obj.id;
 
    $(".active").removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]) {
        //固定TAB中IFRAME高度
        mainHeight = $(document.body).height() - 95;
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab"><i class="blue ace-icon fa '+obj.icon+' bigger-120"></i>' + obj.title;
        //是否允许关闭
        if (obj.close) {
            title += ' <i class="red ace-icon fa fa-close bigger-120" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (obj.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe  src="' + obj.url + '?id='+obj.id+'" id="iframepage_'+obj.id+'" width="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe></div>';
        }
        //加入TABS
        $(".nav-tabs").append(title);
        $(".tab-content").append(content);
    }
     
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
};
 
var closeTab = function (id) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
    }
    //关闭TAB
    $("#tab_" + id).remove();
    $("#" + id).remove();
};
 
$(function () {
    mainHeight = $(document.body).height() - 45;
    $('.main-left,.main-right').height(mainHeight);
    $("[addtabs]").click(function () {
    	alert("我活了");
        addTabs({id: $(this).attr("id"), title: $(this).attr('title'), close: true});
    });
 
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
	var baseUrl = location.protocol  +"//"+location.host;
	$.ajax({
		   type: "POST",
		   url: "/student/getUserInfo",
		   async: false,
		   success: function(response){
		     if(response.status==0){
		    	 	var userId = response.data.userId;
		    	 	if(userId!=null&&userId!=""){
		        		$.post("/student/priviledges/getByUserId", { id: userId },function(menu) {
			    			if(menu.status==-1) {
			    				window.location.href=baseUrl+"/portalUI/login.html"
			    			} else {
			    				// 菜单
			    				initMenu(menu.data.children);
			    				// 菜单点击事件
			    				initClick();
			    			}
			    		});

//			    		tabAdd("home",'首页',"pageAction?url="+home,false,"fa-home");
		        		var home_url="home.html";
			    		var setting ={
			    				id:"home",
			    				title:"首页",
			    				icon :"fa-home",
			    				url:home_url,
			    				close:false
			    		};
			    		addTabs(setting);
//			    		mainFrameResize();
			    		setTimeout(function(){
			    			$("#userName").html(response.data.userName); 
			    		}, 1000)
		    	 	} else {
		    	 		alert("加载错误，请联系系统管理员！");
		    	 	}
		     } else { //获取用户信息失败，返回首页
	    		window.location.href=baseUrl+"/portalUI/login.html"
		     }
		  }
	});
});

// 初始化菜单
function initMenu(data){
	$.each(data,function(i,menu){
		var menu_li = getMenu(menu);
		$("#menu").append(menu_li);
	});
}

function getMenu(menu){
	var menu_li = "<li id='"+menu.id+"' >";
	if(menu.children.length>0){
		menu_li +=" <a href='"+baseUIUrl+menu.url+"' class='dropdown-toggle' ><i class='menu-icon fa "+menu.icon+"' icon='"+menu.icon+"' ></i><span class='menu-text'>"+menu.name+"</span> <b class='arrow fa fa-angle-down'></b> </a>";
		menu_li +=" <b class='arrow'></b> ";
		menu_li +=" <ul class='submenu'> ";
		$.each(menu.children,function(j,cmenu){
			menu_li += getMenu(cmenu);
		});
		menu_li +=" </ul> ";
	}else{
		menu_li +=" <a href='"+baseUIUrl+menu.url+"' ><i class='menu-icon fa "+menu.icon+"' icon='"+menu.icon+"' ></i><span class='menu-text'>"+menu.name+"</span> <b class='arrow'></b> </a>";
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
		var parentId="";
		var parent = $(this).parents("ul .submenu"); 
		if(parent){
			parentId = parent.parent().attr("id");
		}else{
			parentId = childrenId;
		}
		var title = $(this).children('span.menu-text').text();
		var icon = $(this).children('i.menu-icon').attr("icon");
		var url = $(this).attr('href');
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


