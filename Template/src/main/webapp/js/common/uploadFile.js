
		//上传文件个数
    	var count=0;
	    //保存文件参数
	  	var params=[];
	  	//关闭模态框触发清空
	    $('#upload').on('hidden.bs.modal', function (e) {
	    	$("#uploadTable").find("tbody").html("");
	    	$("#uploadForm input:hidden").not($("#uploadForm input").last()).remove();
	    	$("#progress-bar").hide();
	    	count=0;
	    })
	    function addFile(obj){
		    	if(count<5){
					if(!$(obj).fileTypeJudge("doc"))
						return;
					//保存文件名，以便删除
					var fileName=GetFileNameNoExt($(obj).val());
					//插入新的input file
					$(obj).after('<input onchange="addFile(this)" type="file" name="attachment" class="attachment" accept="application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document,text/plain">');
					//隐藏当前input
					$(obj).hide();
					var className="fileName"+count;
					//增加文件名属性方便删除
					$(obj).addClass(className);
					//显示进度条
					$("#progress").refreshProcessBar();
					$("#progress-bar").show();
					var html=[];
					html.push('<tr class='+className+'>');
					html.push('<td><p style="text-align:left;padding-left:10px;"><span class="file-txt">'+fileName+'</span></p></td>');
					html.push('<td><span class="delete TRemove"></span></td>');
					html.push('</tr>');
	    			$("#uploadTable tbody").append(html.join(''));
	    			//document.getElementById("fileName"+count).appendChild($(this).get(0));
					$($(".TRemove").get(count)).bind("click",function(){
						$('.'+className).each(function(i){
								$(this).remove();
						});
						count--;
						if(count==0){
							//隐藏进度条
							$("#progress-bar").hide();
						}
					})
					count++;
			}else{
				alert("上传文件个数不能大于5个");
			}
	    }
    	function upload(){
    		var flag=true;
    		$.each($("#uploadForm input[type='file']"),function(i,e){
    			if($(this).val()){
    				flag=false;
    			}
    		})
    		if(!flag){
	  			$("#uploadForm").ajaxSubmit(options);
	  			//轮询读取进度
	  			window.setTimeout("getProcess()", 10);
    		}else{
    			alert("请先添加要上传的文件！");
    		}
    	}
    	var options={
			    success : showResponse,    // 提交后的回调函数
			    url : baseUrl+"attachment/uploadAttachment?floder=netdisk",    //默认是form的action，如果申明，则会覆盖
			    type : "POST",    // 默认值是form的method("GET" or "POST")，如果声明，则会覆盖
			    dataType : "json",   // html（默认）、xml、script、json接受服务器端返回的类型
			    //clearForm : true    // 成功提交后，清除所有表单元素的值
			    //resetForm : true   // 成功提交后，重置所有表单元素的值
			}
    	function showResponse(data,statusText){
	  		if(data.status==0){
	  			params.push(data.rows);
	  			params.push("&privilegesID="+privilegesID);
	  			params.push("&floder=netdisk");
	  			$.ajax({
					type:"post",
					url:baseUrl+"netdisk/save",
					data:params.join(""),
					//async:false,
					success:function(response){
						if(response.status==0){
							//$("#uploadTable").find("tbody").html("");
							//$("#progress-bar").hide();
							params=[];
							$("#progress").refreshProcessBar(100);
							alert("上传成功");
							$('#upload').modal('hide');
							$("#table").bootstrapTable('refresh');
						
						}else{
							alert(response.message);
						}
						
					}
				})
	  		}else{
    			alert(data.message);
	  		}
	  	}
    	//获取进度
    	function getProcess() {
			  $.ajax({
			    url:baseUrl+'attachment/getUploadProcess',
			    data:{'timestamp': new Date().getTime(),"privilegesID":privilegesID},
			    type:'get',
			    success:function(data){
			    	if(data.status==0){
			    		$("#progress").refreshProcessBar(data.rows);
			    	}else{
			    		alert(data.message);
			    	}
			      
			    }
			  });
			
		}
    	
    	
    	
    	 /** 
    	    **author:sharp 
    	    *文件类型判断
    	    *使用方法
    	         $(":file").bind("change",function(){  
    	                $(this).fileTypeJudge("package");  
    	    })  
    	    */  
    	   var flag=true;   
    	    (function($) {  
    	        $.fn.extend({  
    	            fileTypeJudge : function(str) {  
    	                 this.each(function() {  
    	                    var rightFileType;  
    	                    var pojo;  
    	                    if (str == "photo") {  
    	                        rightFileType = new Array("jpg", "png","jpeg");  
    	                        pojo = "图片";  
    	                    } else if (str == "doc") {  
    	                        rightFileType = new Array("docx", "doc", "txt");  
    	                        pojo = "文档";  
    	                    }else if (str == "excel") {  
    	                        rightFileType = new Array("xls", "xlsx");  
    	                        pojo = "excel";  
    	                    }else {  
    	                        return;  
    	                    }  
    	                    var fileType = $(this).val().substring($(this).val().lastIndexOf(".") + 1);  
    	                    if (!in_array(fileType,rightFileType)) {  
    	                        flag=false;
    	                        $(this).tips({msg : "请上传"+pojo+"格式文件！"});
    	                        $(this).val("");
    	                    }  
    	                    
    	                })  
    	                return flag;
    	            }  
    	        })  
    	    })(jQuery)  
    	      
    	    function in_array(needle, haystack) {  
    	        // 得到needle的类型  
    	        var type = typeof needle;  
    	        if(type == 'string' || type =='number') {  
    	            for(var i in haystack) {  
    	                if(haystack[i] == needle) {  
    	                	flag=true;
    	                	return true; 
    	                }  
    	            }  
    	        }  
    	        return false;  
    	    }  
    	    
    	    
    	    //字符串逆转
            function strturn(str) {
                if (str != "") {
                var str1 = "";
                for (var i = str.length - 1; i >= 0; i--) {
                    str1 += str.charAt(i);
                }
                return (str1);
                }
            }
    	  //取文件后缀名
            function GetFileExt(filepath) {
                if (filepath != "") {
                    var pos = "." + filepath.replace(/.+\./, "");
                    return pos;
                }
            }
          //取文件全名名称
            function GetFileName(filepath) {
                if (filepath != "") {
                    var names = filepath.split("\\");
                    return names[names.length - 1];
                }
            }
    	    //取文件名不带后缀
            function GetFileNameNoExt(filepath) {
                var pos = strturn(GetFileExt(filepath));
                var file = strturn(filepath);
                var pos1 =strturn( file.replace(pos, ""));
                var pos2 = GetFileName(pos1);
                return pos2;

            }
            
            /* ========================================================================
             * refreshProcessBar: 1.0
             * author:Venson Yang
             * ======================================================================== */
            +function ($) {
            	  'use strict';
            	// ====================
            	var initProcessSetting={totalLength:"0kb",percent:"0",velocity:"0kb/s",totalTime:"0s",timeLeft:"0s",length:"0kb"}
            	$.fn.refreshProcessBar = function(option){
            		if(option!=null){
            			if(typeof(option)== "number" ){
            				this.attr("aria-valuenow",option);
            				this.css("width",option+"%");
            				this.parent("div").next("span").text(option+"%");
            			}
            			if(typeof(option)== "object" ){
            				this.attr("aria-valuenow",option.percent);
            				this.css("width",option.percent+"%");
            				var html='文件大小:'+option.totalLength+' 上传速度:'+option.velocity+' 估计上传时间:'+option.totalTime+' 估计剩余时间:'+option.timeLeft;
            				this.parent("div").next("span").text(html);
            			}
            		}else{
            			this.attr("aria-valuenow",0);
            			this.css("width","0%");
            			this.parent("div").next("span").text("0%");
            		}
            		
            	}
            }(jQuery);