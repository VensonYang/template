$(function () {
    if ($('select').length > 0) {
        $.extend($.fn.select2.defaults.defaults, {
            placeholder: '',
            allowClear: true,
            minimumResultsForSearch: Infinity
        });
        $('select').each(function () {
            var $el = $(this);
            $el.select2({
                width: function () {
                    var name = $el.attr('class'),
                        arr;
                    arr = name.match(/w\d+/gi);
                    if (arr.length > 0) {
                        return arr[0].slice(1);
                    } else {
                        return $el.outerWidth();
                    }


                }
            })
        });
    }
    if ($('#saveForm').length > 0) {
        $('#saveForm').find("select").each(function () {
            var $el = $(this);
            $el.select2({
                allowClear: false,
                width: function () {
                    var name = $el.attr('class'),
                        arr;
                    arr = name.match(/w\d+/gi);
                    if (arr.length > 0) {
                        return arr[0].slice(1);
                    } else {
                        return $el.outerWidth();
                    }


                }
            })
        })
    }
    if ($('select').length > 0) {
        $('select').val('').trigger('change');
    }
    //全选按钮的全选功能
    $('.ta-l').prev().click(function () {
        //全选按钮的全选功能
        if ($(this)[0].checked) {
            $('.table').bootstrapTable('checkAll');
        } else {
            $('.table').bootstrapTable('uncheckAll');
        }
    });
    //表单清空按钮的清空功能
    $('.clear-js').click(function () {
        $('.panel-body .form-inline')[0].reset();
        if ($('.panel-body .form-inline select').length > 0) {
            $('.panel-body .form-inline select').val('').trigger('change');
        }
    });
    //获取选中行
    $('.table').on('click-row.bs.table', function (e, row, $element) {
        $('.selecttr', $('.table')).removeClass('selecttr');
        $($element).addClass('selecttr');
    });
    //当表格大小变化时，设置iframe的大小
    $('.table').on('reset-view.bs.table', function () {
        $(window.parent.document).find('.tab-pane.active').height($('body>div').outerHeight());
        parent.$(window.parent.document).trigger('resizeHeight');
        //光标定义到第一行
        $(".fixed-table-body .table tbody tr").first().addClass('selecttr');
    });
})


/*
 * common.js
 * <br>常用功能函数<br>
 * author:venson
 * 
 * */
var Comm = {};
Comm.getUrlParam= function (name,url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r=(url)?url.match(reg):window.location.search.substr(1).match(reg);
    if (r)
        return unescape(r[2]);
    else
        return null; // 返回参数值
}
//后台接口前缀
var baseUrl = window.location.protocol + "//" + window.location.host + "/tkglDI/";
//前台跳转前缀
var baseUIUrl = window.location.protocol + "//" + window.location.host + "/tkglUI/";
//页面权限ID
var priviledgesID = Comm.getUrlParam("id");

/* 确认框 */
Comm.confirm = function (text, handle, messageType) {
    var option = {};
    option.type = "confirm";
    option.onConfirmBtnClick = handle;
    option.messageType = (!messageType) ? "warning" : messageType;
    option.content = text;
    var dialog = new parent.window.CustomDialog(option);
    return dialog;
}

/**提示框*/
Comm.alert = function (text, messageType) {
    var option = {};
    option.type = "alert";
    option.messageType = (!messageType) ? "warning" : messageType;
    option.content = text;
    var dialog = new parent.window.CustomDialog(option);
}

Comm.deleteObject = function (url, table) {
    var $table;
    if (typeof (table) == "object") {
        $table = table;
    } else {
        $table = $(table);
    }
    var row = $table.bootstrapTable('getSelections');
    if (row.length <= 0)
        Comm.alert("请勾选要删除的对象！");
    else {
        this.confirm('是否要删除选择的' + row.length + '个对象', function () {
            $.each(row, function (i, e) {
                Comm.deleteAjax(url, $table, e.id);
            });
        })
    }
}

Comm.modifyObject = function (url, table) {
    var obj = Comm.getSelectedRow($(table));
    if (obj)
        location.href = url + "?objId=" + obj.id + "&id=" + priviledgesID;
    else
        Comm.alert("请点击要修改的行");


}

Comm.getSelectedRow = function ($table) {
    var index = $table.find('tr.selecttr').data('index');
    return $table.bootstrapTable('getData')[index];
}

/**
 * 封装删除ajax函数
 * <br>param: url 接口地址
 * <br>param: table bootstrap表格
 * <br>param: ids 删除的id
 * */
Comm.deleteAjax = function (url, table, ids) {
        $.ajax({
            type: "POST",
            url: baseUrl + url,
            data: {
                id: ids,
                "priviledgesID": priviledgesID
            },
            dataType: "json",
            success: function (data) {
                if (data.status === 0) {
                    if (table) {
                        table.bootstrapTable('remove', {
                            field: 'id',
                            values: [ids]
                        });
                    }
                } else
                    Comm.alert("错误提示：" + data.message);

            },
            error: function (data) {
                Comm.alert("连接服务器出错，请检查！");
            }
        });
    }
    /**
     * 伪多态保存函数
     * 
     * */
Comm.saveAjax = function (url, param, handle) {
        if (typeof (arguments[0]) == "object") {
            Comm.saveDataByObject(arguments[0]);
        } else {
            var l = arguments.length;
            var option = {};
            if (l == 2) {
                option.url = arguments[0];
                option.params = arguments[1];
                Comm.saveDataByObject(option);
            } else if (l == 3) {
                option.url = arguments[0];
                option.params = arguments[1];
                option.handle = arguments[2];
                Comm.saveDataByObject(option)
            }

        }
    }
    /**
     * 快速保存表单数据
     * <br>param:url 接口地址
     * <br>param:handle 结果处理
     * 
     * */
Comm.fastSaveAjax = function (url, handle,id) {
        handle = (handle) ? handle : "back";
        if (!Comm.checkError(id)) {
            Comm.saveAjax(url, Comm.getParameters(id), handle);
        }
    }
    /**
     * 快速修改表单数据
     * <br>param:url 接口地址
     * <br>param:objId 修改对象Id
     * <br>param:handle 结果处理
     * 
     * */
Comm.fastModifyAjax = function (url, objId, handle) {
        handle = (handle) ? handle : "back";
        if (!Comm.checkError()) {
            var params = Comm.getParameters();
            params.id = objId;
            Comm.saveAjax(url, params, handle);
        }
    }
    /**
     * 封装保存ajax函数
     * <br>param: option 参数格式{"url":"","async":false,"tip","#tag","params":{},"handle"} 
     * */
Comm.saveDataByObject = function (option) {
        option.async = (!option.async) ? option.async : true;
        option.type = (!option.type) ? "POST" : option.type;
        option.tip = (!option.tip) ? '#saveId' : option.tip;
        if (typeof (option.params) == "object") {
            option.params.priviledgesID = priviledgesID;
        }
        if (!option.success) {
            option.success = function (response) {
                if (response.status == 0) {
                    if (option.handle) {
                        if (option.handle == "current") {
                            location.href = location.href;
                        } else if (option.handle == "back") {
                            location.href = document.referrer;
                        } else if (option.handle == "returnValue") {
                            window.primaryKey = response.rows;
                        } else if (option.handle == "refresh") {
                            $('.modal').modal('hide');
                            $('.table').bootstrapTable('refresh');
                        }
                    }
                } else {
                    if (response.message)
                        $(option.tip).tips({
                            msg: response.message
                        });
                    else
                        $(option.tip).tips({
                            msg: "保存失败，请联系管理员！"
                        });
                }
            }
        }
        if (!option.error) {
            option.error = function () {
                $(option.tip).tips({
                    msg: "网络出错，请检查网络！"
                });
            }
        }
        $.ajax({
            type: option.type,
            url: baseUrl + option.url,
            data: option.params,
            async: option.async,
            dataType: "json",
            success: option.success,
            error: option.error
        });
    }
    /*根据页面定义的样式自动校验参数*/
Comm.checkError = function (id) {
        id = (id) ? id : "saveForm";
        var hasError = false;
        $("#" + id + " :input").not(":button").not(":file").each(function (i, e) {
            var $label = $(this).prev("label");
            if ($(this).data('require')) {
                if ($.trim(e.value).length == 0) {
                    if (e.lang) {
                        $(this).tips({
                            msg: e.lang + "不能为空！"
                        });
                    } else {
                        var text = $.trim($label.text());
                        if (text.length > 0) {
                            text = text.substring(0, text.length - 1);
                            $(this).tips({
                                msg: text + "不能为空！"
                            });
                        } else {
                            $(this).tips({
                                msg: "该值不能为空！"
                            });
                        }

                    }
                    hasError = true;
                }
            }
        })
        return hasError;
    }
    /*根据页面存在的表单自动获取参数*/
Comm.getParameters = function (id) {
    id = (id) ? id : "saveForm";
    var params = {};
    $("#" + id + " :input").not(":button").not(":file").each(function (i, e) {
        if ($(this).is("input[type='checkbox']")) {
            if (this.checked) {
                params[e.name] = e.value;
            }
        } else {
            if (e.value) {
                params[e.name] = e.value
            } else {
                params[e.name] = "";
            }
        }

    })
    return params;
}

Comm.resetForm = function (id) {
    id = (id) ? id : "saveForm";
    $("#" + id + " :input").not(":button").not("input[type='hidden']").each(function (i, e) {
        if ($(this).is("input[type='checkbox']")) {
            this.checked = false;
        } else if ($(this).is("select")) {
            $(this).val('').trigger('change');
        }  else {
            if (!$(this).data('filter')) {
                $(this).val('');
            }
        }
    })
}

Comm.setSelectByOption = function (option) {
        option.params = (option.params) ? option.params : {};
        var data = this.getData(option.url, option.params);
        $(option.tag).html("<option></option>");
        var rows = [];
        if (option.handle) {
            $.each(data, function (i, e) {
                rows.push({
                    id: e.text,
                    text: e.text
                })
            })
        } else {
            rows = data
        }
        $(option.tag).select2({
            data: rows
        });
    }
    /**
     * ajax设置下拉框
     * <br>param: url 接口地址
     * <br>param: tag 目标标签
     * */
Comm.setSelect = function (url, tag, handle) {
    var option = {
        "url": url,
        "tag": tag,
        "handle": handle
    };
    this.setSelectByOption(option);
}

Comm.setText = function (url, tag, params) {
        params.priviledgesID = priviledgesID;
        var data = this.getData(url, params);
        $(tag).text(data);
    }
    /**
     * ajax设置编辑数据
     * <br>param: url 接口地址
     * <br>param: objId 对象Id
     * <br>param: filter 过滤的参数
     * */
Comm.setData = function (url, objId, id) {
    var data = Comm.getData(url, {
        "id": objId,
        "priviledgesID": priviledgesID
    });
    Comm.setFormData(data, id);

}

Comm.setFormData = function (data, id) {
        id = (id) ? id : "saveForm";
        $("#" + id + " :input").not(":button").each(function (i, e) {
            if ($(this).is("input[type='text']") || $(this).is("textarea")) {
                $(this).val(data[e.name])
            } else if ($(this).is("select")) {
                $(this).val(data[e.name]).trigger('change');
            }else if($(this).is("input[type='radio']")){
            	if($(this).val()==data[e.name]){
            		this.checked=true;
            	}else{
            		this.checked=false;
            	}
            	
            }
        })
    }
    /**
     * ajax设置checkbox
     * <br>param: url 接口地址
     * <br>param: objId 对象Id
     * <br>param: tag 设置目标
     * */
Comm.setCheckbox = function (url, objId, tag) {
        data = this.getData(url, {
            "priviledgesID": priviledgesID,
            "id": objId
        });
        var inputs = $(tag);
        $.each(data, function (i, v) {
            $.each(inputs, function (i1, v1) {
                if (v.id == $(this).val()) {
                    this.checked = true;
                }

            });
        });
    }
    /**
     * 获取已勾选的checkbox参数
     * <br>param: tag 获取目标
     * <br>param: paramName 参数名
     * */
Comm.getCheckedData = function (tag, paramName) {
    var o = Comm.getCheckedObject(tag);
    return Comm.checkDataToParam(o, paramName);
}

Comm.checkDataToParam = function (o, paramName) {
    var ids = [];
    ids.push("priviledgesID=");
    ids.push(priviledgesID);
    for (var k in o) {
        ids.push("&" + paramName + "=");
        ids.push(k);
    }
    if (ids.length > 2) {
        return ids.join('');
    } else {
        return "";
    }
}

Comm.checkDataToName = function (o) {
    var name = [];
    for (var k in o) {
        name.push(o[k] + ",");
    }
    if (name.length > 0) {
        if (name.length > 3) {
            return name[0] + name[1] + name[2].substring(0, name[2].length - 1) + "...";
        } else {
            var n = name.join('');
            return n.substring(0, n.length - 1);
        }
    } else {
        return "";
    }
}

Comm.getCheckedObject = function (tag) {
    var o = {};
    $($(tag)).each(function (i, v) {
        o[$(this).val()] = $(this).next("span").text();
    })
    return o;
}

Comm.getData = function (url, params, cache) {
    params = (params) ? params : {};
    cache = (cache) ? cache : false;
    var name = this.builderName(url, params);
    params.priviledgesID = priviledgesID;
    var data;
    if (cache) {
        //    if (!parent.window.cache[name]) {
        if (!this.get(name)) {
            $.ajax({
                type: "POST",
                url: baseUrl + url,
                data: params,
                async: false,
                dataType: "json",
                success: function (d) {
                    if (d.status == 0) {
                        data = d.rows;
                        //                      parent.window.cache[name] = data;
                        Comm.set(name, data);
                    } else {
                        Comm.alert(d.message, "error");
                    }
                },
                error: function () {
                    Comm.alert("网络出错，请联系管理员！", "error");
                }
            });
        } else {
            //            data = parent.window.cache[name];
            data = this.get(name);
        }
    } else {
        $.ajax({
            type: "POST",
            url: baseUrl + url,
            data: params,
            async: false,
            dataType: "json",
            success: function (d) {
                if (d.status == 0) {
                    data = d.rows;
                } else {
                    Comm.alert(d.message, "error");
                }
            },
            error: function () {
                Comm.alert("网络出错，请联系管理员！", "error");
            }
        });
    }
    return data;
}
Comm.builderName = function (url, params) {
        var name = url.replace(/\//g, "$");
        if (typeof (params) == 'object') {
            var n = [];
            for (var i in params) {
                n.push(params[i]);
            }
            return name + n.join('');
        } else {
            return name + params.replace(/\&/g, "$");
        }
    }
    /**
     * 设置权限菜单
     * */
Comm.setPriviledgeMenu = function () {
    data = this.getData("priviledges/getMenu", {}, true);
    $.each(data.childNode, function (i, v) {
        $("#content").append(getMenu(v));
    });
    $.each($(".priviledgesGroup"), function (i, e) {
        $(this).click(function () {
            var checkAll = this.checked;
            $.each($(this).parent("label").parent("div .row-check-all").next("div .row-check-item").find(":checkbox"), function (i1, o) {
                this.checked = checkAll;
            })
        })
    })

    function getMenu(menu) {
        var menu_li = '';
        if (menu.childNode.length > 0) {
            menu_li += '<div class="row-check"><div class="row-check-all">';
            menu_li += '<label><input name="priviledgesID" class="priviledgesGroup" value=' + menu.id + ' type="checkbox"> <span>' + menu.name + ' </span></label></div>';
            menu_li += '<div class="row-check-item">';
            $.each(menu.childNode, function (i, v) {
                menu_li += getMenu(v);
            })
            menu_li += "</div></div>"
        } else {
            menu_li += '<label><input name="priviledgesID" value=' + menu.id + ' type="checkbox"> <span>' + menu.name + ' </span></label>';
        }
        return menu_li;
    };
}

/**
 * 下载excel模板
 * <br>param: templateName 模板名称
 * <br>param: downloadName 下载文件名称
 * */
Comm.downloadTemplate = function (templateName, downloadName) {
        window.location.href = baseUrl + "attachment/excelDownload?fileName=/template/" + templateName + "&downName=" + downloadName;
    }
    /**
     * 下载excel模板
     * <br>param: fileName 下载文件名称
     * */
Comm.exportExcel = function (fileName) {
    var data = $('.table').bootstrapTable('getData', true);
    this.postByForm('excel/exportExcelByCreate', {
        "json": JSON.stringify(data),
        "cloums": JSON.stringify(pageColumns),
        "fileName": fileName
    })
}


Comm.postByForm = function (URL, PARAMS) {
    var temp = document.createElement("form");
    temp.action = baseUrl + URL;
    PARAMS.priviledgesID = priviledgesID;
    temp.method = "post";
    temp.target = "_blank";
    temp.style.display = "none";
    for (var x in PARAMS) {
        var opt = document.createElement("textarea");
        opt.name = x;
        opt.value = PARAMS[x];
        // alert(opt.name)        
        temp.appendChild(opt);
    }
    document.body.appendChild(temp);
    temp.submit();
    temp.parentNode.removeChild(temp);
}

Comm.post = function (url, response, params) {
    url = baseUrl + url;
    if (typeof (params) == "object") {
        params = params ? params : {};
        params.priviledgesID = priviledgesID;
    } else {
        params = params ? params : "";
        params = "priviledgesID=" + priviledgesID + params;
    }
    $.post(url, params, response, "json");
}

/**
 * 自动获取查询参数并发送查询请求
 * */
var queryParam = {};
queryParam.priviledgesID = priviledgesID;
Comm.queryData = function (id) {
	id = (id) ? id : "searchForm";
    queryParam = {};
    queryParam.priviledgesID = priviledgesID;
    $("#"+id+" :input").not(":button").each(function (i, e) {
        if ($(this).is("input[type='checkbox']")) {
            if (this.checked) {
                queryParam[e.name] = $(this).val();
            }
        } else {
            if ($.trim($(this).val())) {
                queryParam[e.name] = $(this).val();
            }
        }
    });
    $(".table").bootstrapTable('refresh', {
        query: queryParam
    });
}

var pageColumns = [];
Comm.bootstrapTableParams = function (option) {
    pageColumns = option.columns;
    var params = {
        url: baseUrl + option.url,
        method: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        dataField: 'rows',
        cache: false,
        sidePagination: 'server',

        pagination: true,
        pageNumeber: 1,
        pageSize: 10,
        pageList: [10, 30, 50, 100],
        checkboxHeader: false,
        paginationDetailHAlign: 'right',
        columns: option.columns,
        queryParams: function (params) {
            $.each(queryParam, function (i, e) {
                params[i] = e;
            })
            if (option.queryParam) {
                $.each(option.queryParam, function (i, e) {
                    params[i] = e;
                })
            }
            return params;
        }
    }
    return params;
}

Comm.HighChartPie=function(option){
	var param={
	        chart: {
	            type: 'column'
	        },
	        title: {
	            text: ' '
	        },
	        tooltip: {
	        	 pointFormat: '{series.name}: <b>{point.y:1f} 题</b>'
	        },
	        credits: {
		        enabled: false
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [option.data]
	    }
	return param;
}

Comm.HighChartBar=function(option){
	var param={ 
			chart: { type: 'column' }, 
			credits: {
		        enabled: false
	        },
			title: { text: '' }, 
			xAxis: { categories: [ '' ] }, 
			yAxis: { min: 0, title: { text: '' } }, 
			tooltip: { 
				headerFormat: '<span style="font-size:10px">{point.key}</span><table>', 
				pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>' + '<td style="padding:0"><b>{point.y:1f} 题</b></td></tr>', 
				footerFormat: '</table>',  useHTML: true }, 
			plotOptions: { column: { pointPadding: 0.2, borderWidth: 0 } }, 
			series: option.data }
	return param;
}

Comm.tree = function (option) {
    var params = {
        url: baseUrl + option.url,
        method: 'POST',
        lines: true
    }
    if (opiton.onContextMenu) {
        params.onContextMenu = opiton.onContextMenu;
    }
    return params;
}

Comm.refreshTree = function (tag, url, param) {
    var data = [Comm.getData(url, param)];
    $(tag).tree('loadData', data);
}

Comm.uploadFile = function (type, handle, tag) {
    if (!$(":file").fileTypeJudge(type))
        return;
    var url = "";
    if (tag) {
        $(tag).text($(":file").val())
    }
    if (type == "photo") {
        url = "attachment/uploadImg?timestamp="+ new Date().getTime();
    } else if (type == "excel") {
        url = "attachment/excelUpload?timestamp="+ new Date().getTime();
    } else if (type == "word") {
        url = "attachment/wordUpload?timestamp="+ new Date().getTime();
    } else {
        url = "attachment/uploadAttachment?timestamp="+ new Date().getTime();
    }
    var options = {
        success: handle, // 提交后的回调函数
        url: baseUrl + url, //默认是form的action，如果申明，则会覆盖
        type: "POST", // 默认值是form的method("GET" or "POST")，如果声明，则会覆盖
        dataType: "json", // html（默认）、xml、script、json接受服务器端返回的类型
    }
    $("#uploadForm").ajaxSubmit(options);
}

Comm.uploadImage = function (handle) {
    var options = {
        success: handle, // 提交后的回调函数
        url: baseUrl + "attachment/uploadImg", //默认是form的action，如果申明，则会覆盖
        type: "POST", // 默认值是form的method("GET" or "POST")，如果声明，则会覆盖
        dataType: "json", // html（默认）、xml、script、json接受服务器端返回的类型
    }
    $("#uploadForm").ajaxSubmit(options);
}

Comm.paramToString = function (obj) {
    var params = [];
    for (var key in obj) {
        params.push("&" + key + "=");
        params.push(obj[key]);
    }
    return params.join('');
}

Comm.uniqueArr = function (arr) {
    arr.sort();
    var re = [arr[0]];
    for (var i = 1; i < arr.length; i++) {
        if (arr[i]&&arr[i] !== re[re.length - 1]) {
            re.push(arr[i]);
        }
    }
    return re;
}

Comm.set = function (key, value) {
    	if( typeof(value)=="object" ){
    		window.sessionStorage.setItem(key, JSON.stringify(value));
    	}else{
    		window.sessionStorage.setItem(key, value);
    	}
}
Comm.get = function (key) {
		var data;
    	var obj=window.sessionStorage.getItem(key);
    	try{
    		data=JSON.parse(obj);
    	}catch (e) {
    		data=obj;
    	}
    	return data;
}
Comm.indexOfArr = function (arr, val) {
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] == val)
            return i;
    }
    return -1;
};
Comm.removeArr = function (arr, val) {
    var index = this.indexOfArr(arr, val);
    if (index > -1) {
        arr.splice(index, 1);
    }
    return arr;
};
var Init = {};
/**
 * 根据页面需求进行数据依赖注入,并将数据缓存到父窗口中。
 * */
Init.IOC = function () {
    //定义需要注入的对象
    var ioc_user_role = {
        "name": "ioc_user_role",
        "url": "role/showRole"
    };
    var ioc_question = {
        "name": "ioc_question",
        "url": "courseAttribute/getAttrByType",
        "params": { id: 0 }


    };
    var ioc_difficulty = {
        "name": "ioc_difficulty",
        "url": "courseAttribute/getAttrByType",
        "params": { id: 1 }
    };
    var ioc_level = {
        "name": "ioc_level",
        "url": "courseAttribute/getAttrByType",
        "params": { id: 4 }
    };
    var ioc_paper_type = {
            "name": "ioc_paper_type",
            "url": "courseAttribute/getAttrByType",
            "params": { id: 5 }
        };
    var ioc_grade = {
        "name": "ioc_grade",
        "url": "enumbank/getSelectByTypeId",
        "params": { id: "0002" }
    };
    var ioc_subject = {
        "name": "ioc_subject",
        "url": "enumbank/getSelectByTypeId",
        "params": { id: "0001" }
    };
    var ioc_department = {
    		"name": "ioc_department",
    		"url": "department/getAllDepartment"
    };
    
    var iocArray = [ioc_user_role, ioc_question, ioc_difficulty, ioc_level, ioc_grade, ioc_subject,ioc_paper_type,ioc_department];
    //查找doc中是否需要注入
    for (var i in iocArray) {
        if ($('.' + iocArray[i].name).length > 0) {
            this.setData(iocArray[i]);
        }
    }
}
Init.setData = function (option) {
    option.params = (option.params) ? option.params : {};
    var data = Comm.getData(option.url, option.params, true)
        //判断是否需要进行数据转换
    var rows = [];
    if (option.handle && isMoralModule) {
        for (var i in data) {
            var e = data[i];
            rows.push({
                id: e.text,
                text: e.text
            })
        }
    } else {
        rows = data;
    }
    //设置数据
    $('.' + option.name).html("<option></option>");
    $('.' + option.name).select2({
        data: rows
    });
}
Init.IOC();
var Tree = {};
Tree.init = function(text, tree) {
    var o = {};
    o.text = text;
    o.children = [];
    o.origin={};
    o.add = function (tree) {
        this.children.push(tree);
    }
    o.setOrigin=function(origin){
    	this.origin=origin;
    }
    o.setText = function (text) {
        this.text = text;
    }
    return o;
}
Tree.dataToTree=function(data,text){
	var tree=Tree.init(text);
	$.each(data,function(i,e){
		var leaf=Tree.init(e.text);
		leaf.setOrigin(e);
		tree.add(leaf);
	})
	return [tree];
}
Tree.reset=function(id){
	var checkNode= $(id).tree('getChecked');
	if(checkNode.length>0){
		function removeData(children){
	       	$.each(children,function(i,e){
	       		if(e.children.length>0){
	       			removeData(e.children);
	           	}
	       		$(id).tree('uncheck',e.target);
	        })
		}
		removeData(checkNode);
	}
}
Tree.getCheckData=function(id,key){
	var checkNode= $(id).tree('getChecked');
	var data=[];
	if(checkNode.length>0){
		function getData(children){
	       	$.each(children,function(i,e){
	       		if(e.children.length>0){
	       			getData(e.children);
	           	}else{
  	           		data.push(e.origin[key]);
  	           	}
	        })
		}
		getData(checkNode);
	}
	if(data.length>0){
		data=Comm.uniqueArr(data);
	}
	return data;
}
Tree.build = function getTree(paper) {
    var t1 = new Tree(paper.name);
    $.each(paper.questions, function (i, e) {
        if (e.name != "***答案区***") {
            var t2 = new Tree(e.name);
            if (e.type == 0) {
                $.each(e.items, function (k, o) {
                    var title = o.title;
                    title = title.length > 20 ? title.substring(0, 20) + "..." : title;
                    //			var t3=new Tree(title);
                    o.text = title;
                    t2.add(o);
                })
            } else {
                $.each(e.items, function (k, o) {
                    var title = o.title;
                    title = title.length > 10 ? title.substring(0, 10) + "..." : title;
                    var t3 = new Tree(title);
                    $.each(o.items, function (j, t) {
                        var title = t.title;
                        title = title.length > 20 ? title.substring(0, 20) + "..." : title;
                        t.text = title;
                        t3.add(t);
                    })
                    t2.add(t3);
                })
            }
            t1.add(t2);
        }
    })
    return t1;
}
