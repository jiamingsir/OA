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

	.modal-open {
		overflow:auto;
	}
	.box-header{
		text-align: center;
		border-bottom: 0px solid #f4f4f4;
	}
	.title{
		text-align: center;
	}

	.inpu{
		margin-top: -6px;

	}

	.control-label{
		display:inline-block;
		font-weight: 400;
	}
	.bo{
		margin: 0px auto;
		width: 80%;
	}


	.table th,.chebox,.table>tbody>tr>td{
		font-weight: 400;
		text-align: center;
	}
	.inside{
		width: 100%;
	}
	.inside thead{
		background-color: rgba(76, 175, 95, 0.06);
	}
	.inside>tbody>tr>td{
		border-top: 0px solid #ddd;
	}
	.inside>tbody>tr>td{
		border-bottom: 1px solid #ddd;
		border-left: 1px solid #ddd;
	}
	.tdrig{
		border-right: 1px solid #ddd;
	}
	.bo>tbody>tr>td,.inside>thead>tr>th {
		border-top: 0px solid #ddd;
		border-bottom: 0px solid #ddd;
		border-left: 0px solid #ddd;
	}
	.food{
		padding-left:10px
	}
	.zeng,.jian{
		cursor: pointer;
	}
	.reciver{
		position: relative;
		float: right;
		margin-top: -28px;
		right: 5px;
		cursor: pointer;
	}
	.sub{
		position: relative;
		float: right;
		margin-top: -24px;
		right: 9px;
		cursor: pointer;
	}
	.text{
		min-height:100px;
	}
</style>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">流程管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
				disabled="disabled">公章、营业执照等外借申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="gongzhangwaijieshenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<table class="bo table ">

						<tr >
							<td colspan="14" class="title"><h2>公章、营业执照等外借申请</h2></td>

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
											readonly="readonly" value="${user.realName}" name="sender"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control inpu holistart" readonly="readonly"  name="applydate"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${user.dept.deptName}"  name="dept"/></td>
							</td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">拜访单位/拜访部门</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="targetdept" id="targetdept"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">公章/营业执照等名称</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="officialseal" id="officialseal"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">办理事项</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="something" id="something"/></td>
						</tr>

						<td class="title" ><label class="control-label">归还时间</label><span style="color:red">*</span></td>
						<td  colspan="6"><input type="text" class="form-control input "  name="backdate" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})" id="backdate"/></td>


					</table>
				</div>
				<div class="box-footer" style="margin:0 15%">
					<input class="btn btn-primary" id="save" type="submit" value="保存" onclick="{return confirm('确定要保存吗？');};"  />
					<input class="btn btn-default" id="cancel" type="button" value="取消"
						   onclick="window.history.back();" />
				</div>

		</div>
	</div>
</div>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>
<script>
	function check(){
		$('.alert-danger').css('display', 'none');
		var targetdept =$("#targetdept").val();
		var officialseal =$("#officialseal").val();
		var something =$("#something").val();
		var backdate =$("#backdate").val();

		if(targetdept=== 'undefined' || targetdept === null || targetdept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("拜访单位/拜访部门不能为空");
			return false;
		}
		if(officialseal=== 'undefined' || officialseal === null || officialseal === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("公章/营业执照不能为空");
			return false;
		}
		if(something=== 'undefined' || something === null || something === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("办理事项不能为空");
			return false;
		}
		if(backdate=== 'undefined' || backdate === null || backdate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("归还时间不能为空");
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
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>