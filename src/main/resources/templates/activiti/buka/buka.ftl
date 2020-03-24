<#include "/common/commoncss.ftl">
<style type="text/css">
	a {
		color: black;
	}

	a:hover {
		text-decoration: none;
	}

	.bgc-w {
		background-color: #fff;
	}
</style>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">流程管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
				disabled="disabled">补卡申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="bukashenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="" name = "taskid"/>
					<table class="bo table ">

						<tr >
							<td colspan="14" class="title"><h2>补卡申请单</h2></td>

						</tr>
						<tr style="opacity: 0;">
							<td colspan="14">11</td>
						</tr>
						<tr >
							<td colspan="14" style="text-align: left;">
								<!--錯誤信息提示  -->
								<div class="alert alert-danger alert-dismissable" style="display:none;" role="alert">
									错误信息:<button class="thisclose close" type="button">&times;</button>
									<span class="error-mess"></span>
								</div>
							</td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">申请人</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"
													readonly="readonly" value="${user.realName}" name="name"/></td>

							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control inpu holistart" readonly="readonly"  name="nowdate" /></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${user.dept.deptName}" name="dept"/></td>
							<td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type = "radio" name = "company" value="新云传媒" >新云传媒
								<input type = "radio" name = "company" value="新云文化" >新云文化
								<input type = "radio" name = "company" value="新云投资" >新云投资
							</td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">未打卡开始时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control shijian "  name="nodatebegin" id="nodatebegin" /></td>

							<td class="title" ><label class="control-label">未打卡结束时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control shijian "  name="nodateend" id="nodateend"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">未打卡原因</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="reason" id="reason"/></td>
							<td class="title" ><label class="control-label">备注</label></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="remark"/></td>
						</tr>
                        <tr >

                            <td class="title" ><label class="control-label">相关材料</label></td>
                            <td  colspan="6">
                                <div class="btn btn-default"style="position: relative; overflow: hidden;width: 100%;margin-top: -6px;">
                                    <i class="glyphicon glyphicon-open"></i> 上传材料
                                    <input type="file" name="filePath" style="opacity: 0; position: absolute;
								 top: 12px; right: 0; " class='inpu' id="files" multiple="multiple">
                                </div>
                            </td>

                        </tr>


					</table>
				</div>
				<div class="box-footer">
					<input class="btn btn-primary" id="save" type="submit" value="保存" onclick="check();"  />
					<input class="btn btn-default" id="cancel" type="button" value="取消"
						   onclick="window.history.back();" />
				</div>

		</div>
	</div>
</div>
<script>
	function check(){
		$('.alert-danger').css('display', 'none');
		var reason = $("#reason").val();
		var nodatebegin = $("#nodatebegin").val();
		var nodateend = $("#nodateend").val();
		var company = $('input:radio[name="company"]:checked').val();

		if(reason=== 'undefined' || reason === null || reason === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("未打卡原因不能为空");
			return false;
		}
		if(nodatebegin=== 'undefined' || nodatebegin === null || nodatebegin === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("未打卡开始时间不能为空");
			return false;
		}
		if(nodateend=== 'undefined' || nodateend === null || nodateend === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("未打卡结束时间不能为空");
			return false;
		}

		if(company == 'undefined'  || company == null || company === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("公司不能为空");
			return false;
		}
		if(nodatebegin > nodateend){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("结束时间不能小于开始时间");
			return false;
		}


	}

	function alertCheck(errorMess){
		$('.alert-danger').css('display', 'block');
		// 提示框的错误信息显示
		$('.error-mess').text(errorMess);

	}

</script>
<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
<script type="text/javascript" src="js/common/data2.js"></script>