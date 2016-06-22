var baseUrl=window.location.protocol+"//"+window.location.host+"/Template/";
//前台跳转前缀
var baseUIUrl=window.location.protocol+"//"+window.location.host+"/Template/";

var addTabs = function (obj) {
    id = "tab_" + obj.id;
    var parent=window.parent.document;
    var $active,$navtab,$tabContent,$navtab,$tabContent,$tabId,$ID
    if($(".nav-tabs").get(0)!=null){
    	$active=$(".active");
    	$navtab=$(".nav-tabs");
    	$tabContent=$(".tab-content");
    }else{
    	$active=$(parent).find(".active");
    	$navtab=$(parent).find(".nav-tabs");
    	$tabContent=$(parent).find(".tab-content");
    }
    $active.removeClass("active");
  
    //如果TAB不存在，创建一个新的TAB
    if (!$("#" + id)[0]&&!$(parent).find("#" + id)[0]) {
        //创建新TAB的title
        title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab"><i class="color-blue fa '+obj.icon+'"></i> <span class="color-black">' + obj.title+"</span>";
        //是否允许关闭
        if (obj.close) {
            title += ' <i class="color-red fa fa-close bigger-120" tabclose="' + id + '"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (obj.content) {
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + obj.content + '</div>';
        } else {//没有内容，使用IFRAME打开链接
        	//console.log( window.top.innerHeight);
        	var height=window.top.innerHeight-100;
            content = '<div role="tabpanel" style="height:'+height+'px;" class="tab-pane" id="' + id + '"><iframe   src="' +obj.url + (obj.url.indexOf("?")!=-1?"&":"?") +'id='+obj.id+'" id="iframepage_'+obj.id+'" style="overflow:hidden;" width="100%" height="100%"  frameborder="no" border="0" marginwidth="0" marginheight="0" allowtransparency="yes"></iframe></div>';
        }
        //加入TABS
        $navtab.append(title);
        $tabContent.append(content);
        
    }
    if($(".nav-tabs").get(0)!=null){
    	$tabId=$("#tab_" + id);
    	$ID=$("#" + id);
    }else{
    	$tabId=$(parent).find("#tab_" + id);
    	$ID=$(parent).find("#" + id);
    }
    //激活TAB
    $tabId.addClass('active');
    $ID.addClass("active");
   
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

function getUrlParam(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg); // 匹配目标参数
	if (r != null)
		return unescape(r[2]);
	return null; // 返回参数值
}
var priviledgesID=getUrlParam("id");
function reSizeFun(obj){
	if(document.body.scrollHeight<300){
		$(window.parent.document).find("#iframepage_"+obj).height(document.body.scrollHeight+400);
	}else{
		$(window.parent.document).find("#iframepage_"+obj).height(document.body.scrollHeight);
	}
	
}
/*获取用户页面操作权限
$('#table').on('load-success.bs.table', function () {
	$.ajax({
		   type: "get",
		   url: baseUrl+"user/getUOP",
		   data:{"priviledgesID":priviledgesID},
		   cache: false,
		   success: function(data){
			   if(data.status==0){
				   if(data.rows.iscreate==false){
					   $(".add").hide();
				   }
				   if(data.rows.isdelete==false){
					   $(".delete").hide();
				   }
				   if(data.rows.ismodify==false){
					   $(".modify").each(function(i){
						   $(this).hide();
						});
				   }
			   }else{
				   console.log(data.message);
			   }
		   }
	});
});
*/
function setRole(){
	$.ajax({
		   type: "POST",
		   url: baseUrl+"role/showRole",
		   data:{"priviledgesID":priviledgesID},
		   cache: false,
		   success: function(data){
			   var htmlCode='<option value="">角色设置</option>';
		    	$.each(data.rows, function(k, v) {
		    		htmlCode+='<option value='+v.id+'>'+v.name+'</option>';
		        });
		    	$("#roleId").html(htmlCode);
		   }
			
	});
}
function deleteObject(url,table){
	var $table=$(table);
	var row=$table.bootstrapTable('getSelections');
	if(row.length<=0){
		alert("请选择要删除的对象！");
	}else{
		for(var i=0;i<row.length;i++){
			deleteAjax(url,$table,row[i].id);
		}	
	}
}

function deleteAjax(url,table,ids){
		$.ajax({
		   type: "POST",
		   url: baseUrl+url,
		   data:{id:ids,"priviledgesID":priviledgesID},
		   //async:false,
		   success: function(response){
			  if(response.status==0){
				  table.bootstrapTable('remove', {field: 'id',values: [ids]});
			  }else{
				  alert("错误提示："+response.message);
			  }
		   },error:function(response){
			   	 alert("连接服务器出错，请检查！");
		   }
		});
	}

function saveAjax(url,params,tip){
	params.priviledgesID=priviledgesID;
	$.ajax({
		   type: "POST",		
		   url: baseUrl+url,
		   data:params,
		   success: function(response){
			  if(response.status==0){
				  $("#table").bootstrapTable('refresh');
				  $(".modal").modal('hide');
			  }else{
				  if(response.message!=null){
					  tip.tips({msg : response.message});
				  }else{
					  tip.tips({msg : "保存失败，请联系管理员！"});
				  }
			  }
		   },error:function(){
			   tip.tips({msg : "网络出错，请检查网络！"});
		   }
		});
}