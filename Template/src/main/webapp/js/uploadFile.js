
    	//获取进度
    	function getProcess() {
			  $.ajax({
			    url:baseUrl+'attachment/getUploadProcess',
			    data:{'timestamp': new Date().getTime(),"priviledgesID":priviledgesID},
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
    	                    }else if (str == "word") {  
    	                        rightFileType = new Array("docx", "doc");  
    	                        pojo = "word文档";  
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