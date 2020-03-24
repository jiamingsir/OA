
<#include "/common/commoncss.ftl">
<link rel="stylesheet" type="text/css" href="css/common/checkbox.css" />
<link rel="stylesheet" href="css/common/tanchuang.css" />
<script type="text/javascript" src="js/task/task.js" ></script>

<script charset="utf-8" src="plugins/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="plugins/kindeditor/lang/zh_CN.js"></script>
<link rel="stylesheet" href="plugins/kindeditor/themes/default/default.css" />

<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
<script type="text/javascript" src="js/common/data2.js"></script>
<style type="text/css">
a {
	color: black;
}

a:hover {
	text-decoration: none;
}



.text {
	min-height: 114px;
}
.reciver{
	position: relative;
    top: -27px;
    float: right;
    right: 4px;
    cursor: pointer;
}
</style>
<script>
	$(function(){
	
	})

</script>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">任务管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">任务管理</a>
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
						style="padding: 5px;">
						 <i class="glyphicon glyphicon-chevron-left"></i> <span>返回</span>
					</a>
				</h3>
			</div>
			<!--盒子身体-->
			<form action="update" method="post" onsubmit="return check();">
			<div class="box-body no-padding">
				<div class="box-body">
                    <!--錯誤信息提示  -->
                    <div class="alert alert-danger alert-dismissable" role="alert"style="display: none;">
                        错误信息:<button class="thisclose close" type="button">&times;</button>
                        <span class="error-mess"></span>
                    </div>
					<div class="row">
                        <div class="col-md-6 form-group">
                            <label class="control-label"> <span>任务编号</span></label>
                            <input class="form-control" name="number" value="${task.number}" readonly="readonly"/>
                        </div>
                        <div class="col-md-6 form-group">
                            <label class="control-label">任务名称</label>
                            <input name="title"
                                   type="text" id="title_Name" value="${task.title}"class="form-control" readonly="readonly"/>
                        </div>
						<div class="col-md-6 form-group">
							<label class="control-label"> <span>类型</span></label>
							 <select class="form-control" name="typeId">
								<option value="${type.typeId}">${type.typeName}</option>
								<#if type.typeId ==3>
								<#else>
								<option value="3">公事</option>
								</#if>
								<#if type.typeId ==4>
								<#else>
								<option value="4">私事</option>
								</#if>
							</select>
						</div>
						<div class="col-md-6 form-group">
							<label class="control-label">状态</label> 
							<select class="form-control" name="statusId">
								<option value="${status.statusId}">${status.statusName}</option>
								<#if status.statusId ==5>
								<#else>
								<option value="5">进行中</option>
								</#if>
								<#if status.statusId ==7>
								<#else>
								<option value="7">已完成</option>
								</#if>
							</select>
						</div>
						<div class="col-md-6 form-group">
							<label class="control-label">开始日期</label> <input id="starTime" name="starTime"
								class="form-control shijian"  value="${task.starTime}"/>
						</div>
						<div class="col-md-6 form-group">
							<label class="control-label">结束日期</label> <input id="endTime" name="endTime"
								class="form-control shijian" value="${task.endTime}"/>
						</div>
						<div class="col-md-6 form-group">

							<label class="control-label">相关项目</label> <input name="linkobj"
								type="text" id="linkobj" class="form-control" value="${task.linkobj}"/>
						</div>
                        <div class="col-md-6 form-group">

                            <label class="control-label">协助部门</label> <input name="linker"
                                                                             type="text" id="linker" class="form-control" value="${task.linker}"/>
                        </div>
						<#--<div class="col-md-6 form-group" style="position: relative;">
							<label class="control-label" data-toggle="modal" data-target="#myModal">接收人</label>
							 <input name="reciverlist" type="text" id="recive_list" value="${(task.reciverlist)!''}"
								class="form-control " readonly="readonly"  style="background-color:#fff;"/>
							<div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
									data-toggle="modal" data-target="#myModal">通讯录</span>
							</div>
						</div>-->
						<div class="col-md-12 form-group">
							<label class="control-label">描述</label>
							<#--<textarea class="form-control text" name="taskDescribe" >${(task.taskDescribe)!''}</textarea>-->
                            <textarea class="form-control tent" name="taskDescribe" style="width: 100%; height: 300px; visibility: hidden; font-size: 20px;">${(task.taskDescribe)!''}</textarea>

						</div>

						<#--<div class="col-md-6 form-group">
							<label class="control-label">文件连接</label>
							<input class="form-control" name="fileFile" value="${(task.fileFile)!''}"/>
						</div>-->
                        <#--<div class="row">
                            <div class="col-md-6 form-group">
                                <div class="btn btn-default ">
                                    <span class="glyphicon glyphicon-paperclip">增加附件</span> <input
                                        type="file" name="filePath"
                                        id="ctl00_cphMain_fuAttachment" multiple="multiple"/>
                                </div>
                                <p class="help-block">5MB以内</p>
                            </div>
                        </div>-->
						<#--<div class="col-md-6 form-group ">
							<label class="control-label">置顶</label> <br /> 
								<#if task.top==true>
									<span class="labels">
									<label>
									   <input type="checkbox" name="top" class="val" checked><i>✓</i>
									 </label></span>
								<#else>
									 <span class="labels">
										<label>
										   <input type="checkbox" name="top" class="val"><i>✓</i>
											 </label></span>
								 </#if>
						</div>

						<div class="col-md-6  form-group"> 
							<label class="control-label">取消</label> <br />
							<#if task.cancel==true>
									<span class="labels">
									<label>
									   <input type="checkbox" name="cancel" class="val" checked><i>✓</i>
									 </label></span>
								<#else>
									 <span class="labels">
										<label>
										   <input type="checkbox" name="cancel" class="val"><i>✓</i>
											 </label></span>
								 </#if>
						</div>-->
                        <div class="col-md-3 form-group" >
                        <#--<label class="control-label"><span>推送部门</span></label>-->
                        <#--<input 	class="form-control" id ="deptids" value="${(noticeList.deptIdS)!''}" name="deptIdS" disabled="disabled"/>-->
                        <#--style="display: none"-->
                            <div style="margin:15px 0">
                                <input type="checkbox" checked onclick="$('#cc').combotree({cascadeCheck:$(this).is(':checked')})">
                            </div>
                        <#--<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;">-->
                        <#--<div class="easyui-panel" style="width:100%;">-->
                            <div style="margin-bottom:20px">
                                <input id="cc" class="easyui-combotree" data-options="url:'fileste1?fileIdS=${attafile}',method:'get',label:'文件选择:',labelPosition:'top',multiple:true,value:[]"  style="width:100%">
                            </div>
                        <#--</div>-->

                        <#--eazyui引入-->

                        </div>
                        <div style="display: none"><input 	class="form-control" id ="fileids" value="" name="fileFile"/></div><#--style="display: none"-->
                        <input name="taskId" type="text" style="display:none;"value="${task.taskId}">
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

<script type="text/javascript">

    //富文本
    var editor;
    KindEditor.ready(function(K) {
        editor = K.create('textarea[name="taskDescribe"]', {
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
        $('#fileids').attr("value",str)


        console.log("开始进入了");
        //提示框可能在提交之前是block状态，所以在这之前要设置成none
        $('.alert-danger').css('display', 'none');
        var isRight = 1;
        $('.form-control').each(function(index) {
            // 如果在这些input框中，判断是否能够为空
            if ($(this).val() == "") {
                // 排除哪些字段是可以为空的，在这里排除
                if ( index == 6 ||index == 7 ||index == 9 || index == 10) {
                    return true;
                }
                if(index == 8){
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
                if (index==8){
                     errorMess = "[" + "描述,输入框信息不能为空]";
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

<!-- 接收人弹窗-->
	<#--<#include "/common/reciver.ftl">-->
			
<#--<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>-->

