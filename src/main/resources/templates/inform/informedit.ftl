<#include "/common/commoncss.ftl">
<style type="text/css">

a:hover {
	text-decoration: none;
}

.bgc-w {
	background-color: #fff;
}
.red{
	color:#d9534f;
	font-weight:100;
	font-size:1px;
}
</style>
<script charset="utf-8" src="plugins/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="plugins/kindeditor/lang/zh_CN.js"></script>
<#--<script type="text/javascript" src="js/mail/mail.js" ></script>-->
<link rel="stylesheet" href="plugins/kindeditor/themes/default/default.css" />
<link rel="stylesheet" href="css/common/tanchuang.css" />
<#assign idsarr = [2,3] >


<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">通知管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">通知管理</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
	
		<div class="bgc-w box">
			<!--盒子头-->
			<div class="box-header">
				<h3 class="box-title">
					<a href="javascript:history.back();" class="label label-default"
						style="padding: 5px;"> <i
						class="glyphicon glyphicon-chevron-left"></i> <span>返回</span>
					</a>
				</h3>
			</div>
			<form action="informcheck" method="post" id="thisForm" onsubmit="return check();" enctype="multipart/form-data">
				<!--盒子身体-->
				<div class="box-body no-padding">
					<div class="box-body">
						<div class="alert alert-danger alert-dismissable" role="alert"
							style="display: none;">
							错误信息:<button class="close" type="button">&times;</button>
							<span class="error-mess"></span>
						</div>
						<div class="row">
							<div class="col-md-6 form-group">
								<label class="control-label"><span>类型</span></label> 
								<select class="form-control" name="typeId">
								<#if typeName??>
									<option value="${noticeList.typeId}">${typeName}</option>
								</#if>
									<#list typeList as type>
										<option value="${type.typeId}">${type.typeName}</option>
									</#list>
								</select>
							</div>
							<div class="col-md-6 form-group">
								<label class="control-label"><span>状态</span></label> 
								<select class="form-control" name="statusId">
								<#if statusName??>
									<option value="${noticeList.statusId}">${statusName}</option>
								</#if>
									<#list statusList as status>
										<option value="${status.statusId}">${status.statusName}</option>
									</#list>
								</select>
							</div>
							<div class="col-md-6 form-group">
								<label class="control-label"><span>标题</span></label>
								 <input class="form-control" value="${(noticeList.title)!''}" name="title"/>
							</div>
							<div class="col-md-6 form-group">
								<label class="control-label"><span>链接</span></label> 
								 <input	class="form-control" value="${(noticeList.url)!''}" name="url" />
							</div>
							<div class="col-md-12 form-group">
								<label class="control-label"><span>描述</span></label> 
								<#--<textarea class="form-control text" rows="5" cols="20" name="content">${(noticeList.content)!''}</textarea>-->
                                <textarea name="content" id="contentis" class="form-control tent" style="width: 100%; height: 300px; visibility: hidden; font-size: 20px;">${(noticeList.content)!''}</textarea>
							</div>
                            <div class="col-md-3 form-group" ><#--id="deptCheak"-->
                                <#--<label class="control-label"><span>推送部门</span></label>-->
                                <#--<input 	class="form-control" id ="deptids" value="${(noticeList.deptIdS)!''}" name="deptIdS" disabled="disabled"/>-->
                                <#--style="display: none"-->
                                <div style="margin:15px 0">
                                    <input type="checkbox" checked onclick="$('#cc').combotree({cascadeCheck:$(this).is(':checked')})">
                                </div>
                                <#--<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;">-->
                                <#--<div class="easyui-panel" style="width:100%;">-->
                                    <div style="margin-bottom:20px">
                                        <input id="cc" class="easyui-combotree" data-options="url:'informte?deptIdS=${(noticeList.deptIdS)!''}',method:'get',label:'推送部门:',labelPosition:'top',multiple:true,value:[]"  style="width:100%">
									</div>
                                <#--</div>-->

		<#--eazyui引入-->

                            </div>
                            <div class="col-md-9 form-group" style="padding-top: 40px">
                                <div class="form-group">
                                    <div class="col-md-1 form-group">
                                		<label class="control-label"><span >是否置顶</span></label><br>
									</div>
                                    <div class="col-md-11 form-group">
										<#if noticeList??>
											<#if noticeList.top==true>
												<span class="labels"><label><input name="top" type="checkbox" ><i>✓</i></label></span><#--checked-->
											<#else>
												<span class="labels"><label><input name="top" type="checkbox"><i>✓</i></label></span>
											</#if>
										<#else>
												<span class="labels"><label><input name="top" type="checkbox"><i>✓</i></label></span>
										</#if>
									</div>
								</div>
                            </div>

                            <div class="col-md-12 form-group"><#--class="form-group"-->
                                <#--<div class="btn btn-default ">-->
                                <div class="form-group">
                                    <div class="col-md-1 form-group">
										<label class="control-label"><span class="glyphicon glyphicon-paperclip">增加附件</span></label>
									</div>
                                    <#--<img name="image" id="myimage" src="" style="width: 80px;height: 100px"/>-->
                                    <div class="col-md-2 form-group">
										<input type="file" name="file" multiple="multiple" />
									</div>
                                    <#--id="ctl00_cphMain_fuAttachment" onchange="changeImge(this)"-->
                                <#--</div>-->
                                        <div class="col-md-1 form-group">
											<p class="help-block">10MB以内</p>
										</div>
								</div>
                            </div>

                            <#--<input class="easyui-filebox" data-options="prompt:'浏览'" id="contractFile" name="contractFile" style="width:155px">-->

                            <#--<div class="col-md-6 form-group">
								<label class="control-label"><span>置顶</span></label><br>
								<#if noticeList??>
									<#if noticeList.top==true>
										<span class="labels"><label><input name="top" type="checkbox" ><i>✓</i></label></span>&lt;#&ndash;checked&ndash;&gt;
										<#else>
										<span class="labels"><label><input name="top" type="checkbox"><i>✓</i></label></span>
									</#if>
									<#else>
										<span class="labels"><label><input name="top" type="checkbox"><i>✓</i></label></span>
								</#if>
							</div>-->
							<div style="display: none"><input 	class="form-control" id ="deptids" value="${(noticeList.deptIdS)!''}" name="deptIdS"/></div><#--style="display: none"-->
                            <div style="display: none"><input 	class="form-control" id ="srcs" value="${(noticeList.src)!''}" name="src"/></div>
                        </div>
				</div>
				</div>

				<!--盒子尾-->
				<div class="box-footer">
					<input class="btn btn-primary" id="save" type="submit" value="保存" />
					<input class="btn btn-default" id="cancel" type="button" value="取消"
						onclick="window.history.back();" />
				</div>
			</form>
		</div>

	</div>
</div>

<#include "/common/modalTip.ftl"> 
<script type="text/javascript">

    function changeImge(obj) {
        var f = obj.files[0];
        var type = f.type;//image/jpeg
        var size = f.size;
        var tp = type.split("/")[0];

        $('#srcs').attr("value",0);
        if ( size > 10*1024*1024){
            alert("请选择图片-10MB内！");
            return false;
        }
        if (filePath != null && filePath != "") {
            //$('#srcs').attr("value",filePath);
        }
        }



   $('#btnyes').on('click', function () {
       var chk_value = [];//定义一个数组
       var chk = "";
       var len = $('input[name="deptId"]:checked').length;
       $('input[name="deptId"]:checked').each(function () {//遍历每一个名字为interest的复选框，其中选中的执行函数
           //chk_value.push($(this).val());//将选中的值添加到数组chk_value中
           chk += $(this).val() + ",";
       });
       chk_value = chk.split(',');
       for (var i =0;i<chk_value.length;i++){

       }
       var chks = chk.substring(0, chk.length - 1);
       //$('#deptids').val(chks);


       //alert("部门选择完成！");
       $('#modal-container-209949').modal('hide');
   })

var ids = $('#deptids').val();
if (ids != ""){
    var idsArr=ids.split(",");
}


$('.successToUrl').on('click',function(){
	window.location.href='/infrommanage';
});

//富文本
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('textarea[name="content"]', {
            allowFileManager: true
        });
        K('input[name=getHtml]').click(function(e) {
            alert(editor.html());
        });
        K('input[name=isEmpty]').click(function(e) {
            alert(editor.isEmpty());
        });
        K('input[name=getText]').click(function(e) {
            alert(editor.text());
        });
        K('input[name=selectedHtml]').click(function(e) {
            alert(editor.selectedHtml());
        });
        K('input[name=setHtml]').click(function(e) {
            editor.html('<h3>Hello KindEditor</h3>');
        });
        K('input[name=setText]').click(function(e) {
            editor.text('<h3>Hello KindEditor</h3>');
        });
        K('input[name=insertHtml]').click(function(e) {
            editor.insertHtml('<strong>插入HTML</strong>');
        });
        K('input[name=appendHtml]').click(function(e) {
            editor.appendHtml('<strong>添加HTML</strong>');
        });
        K('input[name=clear]').click(function(e) {
            editor.html('');
        });


    });



//表单提交前执行的onsubmit()方法；返回false时，执行相应的提示信息；返回true就提交表单到后台校验与执行
function check() {
    var chk = "";
    var str = $('#cc').val();
    //var t = $('#cc').combotree('tree');
    //$(str).Array();
    for (var i =0;i<str.length;i++){
        chk += str[i] + ",";
	}
    var chks = chk.substring(0, chk.length - 1);
    //$('#deptids').val(chks);
    //$('#deptids').val(str);
    $('#deptids').attr("value",str)





    console.log("开始进入了");

	//提示框可能在提交之前是block状态，所以在这之前要设置成none
	$('.alert-danger').css('display', 'none');
	var isRight = 1;
	$('.form-control').each(function(index) {
		// 如果在这些input框中，判断是否能够为空
		if ($(this).val() == "") {
			// 排除哪些字段是可以为空的，在这里排除
			if (index == 3 || index == 6) {
				return true;
			}
			if(index == 4){
			    var evals = editor.html();
                $(this).val(evals);
                //var ue = UE.getEditor('content');
                //var content = CKEDITOR.instances.contentis.getDate();
                var content = $(this).val();
                if(evals == "" || evals == null || evals == undefined){ // "",null,undefined

                }else {
                    return true;
				}
			}

			// 获取到input框的兄弟的文本信息，并对应提醒；
			 var brother = $(this).siblings('.control-label').text();
			var errorMess = "[" + brother + "输入框信息不能为空]";
            if(index == 5){
                errorMess = "[" + brother + "推送部门不能为空]";
            }
			// 对齐设置错误信息提醒；红色边框
			$(this).parent().addClass("has-error has-feedback");
			$('.alert-danger').css('display', 'block');
			// 提示框的错误信息显示
			$('.error-mess').text(errorMess);
			// 模态框的错误信息显示
			$('.modal-error-mess').text(errorMess);
			isRight = 0;
			return false;
		} else {
			// 在这个里面进行其他的判断；不为空的错误信息提醒

            /*var chk_value =[];//定义一个数组
			var chk = "";
			var len =$('input[name="deptId"]:checked').length;
            $('input[name="deptId"]:checked').each(function(){//遍历每一个名字为interest的复选框，其中选中的执行函数
                chk_value.push($(this).val());//将选中的值添加到数组chk_value中
				chk += $(this).val()+",";
            });
            var chks=chk.substring(0,chk.length-1);
            $('#deptids').val(chks);*/

            return true;
		}
	});
	if (isRight == 0) {
		//modalShow(0);
		 return false;
	} else if (isRight == 1) {
		//modalShow(1);
		 return true;
	}
//	return false;
}
</script>
