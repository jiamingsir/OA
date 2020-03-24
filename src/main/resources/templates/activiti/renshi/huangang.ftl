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
				disabled="disabled">人事异动</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="huangangshenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<table class="bo table ">

						<tr >
							<th colspan="12" class="title" align="center"><h2>人事异动</h2></th>
						</tr>
						<tr >
							<td colspan="12" style="text-align: left;">
								<!--錯誤信息提示  -->
								<div class="alert alert-danger alert-dismissable" style="display:none;" role="alert">
									错误信息:<button class="thisclose close" type="button">&times;</button>
									<span class="error-mess"></span>
								</div>
							</td>
						</tr>
						<tr >
							<th colspan="10"><h4>基础信息</h4></th>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">申请人</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" value="${ user.realName}" readonly="readonly" name="sender"/></td>
							<td class="title" ><label class="control-label">申请时间</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control inpu holistart" readonly="readonly"  name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">姓名</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input"  name="name" id="name"/></td>
							<td class="title" ><label class="control-label">部门/中心</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="dept" id="dept"/></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">职位</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="position" id="position"/></td>
						</tr >
						<tr >

							<td class="title" ><label class="control-label">到职日</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="joindate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="joindate"/></td>
							<td class="title" ><label class="control-label">生效日</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="effectivedate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  id="effectivedate"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同开始时间</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="contractbegindate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="contractbegindate"/></td>
							<td class="title" ><label class="control-label">合同结束时间</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="contractenddate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="contractenddate"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动原因</label><span style="color:red">*</span></td>
							<td><input type="radio" value="转正" name="effectiveresult"/>转正</td>
							<td><input type="radio" value="升职" name="effectiveresult"/>升职</td>
							<td><input type="radio" value="工资调整" name="effectiveresult"/>工资调整</td>
							<td><input type="radio" value="降职" name="effectiveresult"/>降职</td>
							<td><input type="radio" value="中心/部门调整" name="effectiveresult"/>中心/部门调整</td>
							<td><input type="radio" value="职位调整" name="effectiveresult"/>职位调整</td>
							<td><input type="radio" value="中止雇佣" name="effectiveresult"/>中止雇佣</td>
						</tr>

						<tr >
							<th class="title" colspan="16" >异动信息</th>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动前中心/部门</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="effectivebeforedept" id="effectivebeforedept"/></td>
							<td class="title" ><label class="control-label">异动前职位</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="effectivebeforeposition" id="effectivebeforeposition"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动后中心/部门</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="effectiveafterdept" id="effectiveafterdept"/></td>
							<td class="title" ><label class="control-label">异动后职位</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="effectiveafterposition" id="effectiveafterposition"/></td>
						</tr>

						<tr >
							<td class="title" colspan="10">薪资信息</td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">目前月薪</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="number" class="form-control input" name="salarybefore" id="salarybefore"/></td>
							<td class="title" ><label class="control-label">调整后月薪</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="number" class="form-control input" name="salaryafter" id="salaryafter"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">调整比例</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" name="salaryproportion" id="salaryproportion"/></td>
						</tr>
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
<script>
	function check(){
		$('.alert-danger').css('display', 'none');
		var name =$("#name").val();
		var dept =$("#dept").val();
		var position =$("#position").val();
		var joindate =$("#joindate").val();
		var effectivedate =$("#effectivedate").val();
		var contractbegindate =$("#contractbegindate").val();
		var contractenddate =$("#contractenddate").val();
		var effectivebeforedept =$("#effectivebeforedept").val();
		var effectivebeforeposition =$("#effectivebeforeposition").val();
		var effectiveafterdept =$("#effectiveafterdept").val();
		var effectiveafterposition =$("#effectiveafterposition").val();
		var salarybefore =$("#salaryafter").val();
		var salaryafter =$("#name").val();
		var salaryproportion =$("#salaryproportion").val();
		var effectiveresult = $('input:radio[name="effectiveresult"]:checked').val();


		if(name=== 'undefined' || name === null || name === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("姓名不能为空");
			return false;
		}
		if(dept=== 'undefined' || dept === null || dept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("部门/中心不能为空");
			return false;
		}
		if(position=== 'undefined' || position === null || position === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("职位不能为空");
			return false;
		}
		if(joindate=== 'undefined' || joindate === null || joindate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("到职日不能为空");
			return false;
		}
		if(effectivedate=== 'undefined' || effectivedate === null || effectivedate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("生效日不能为空");
			return false;
		}
		if(contractbegindate=== 'undefined' || contractbegindate === null || contractbegindate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同开始时间不能为空");
			return false;
		}
		if(contractenddate=== 'undefined' || contractenddate === null || contractenddate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同结束时间不能为空");
			return false;
		}
		if(effectivebeforedept=== 'undefined' || effectivebeforedept === null || effectivebeforedept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("异动前中心/部门不能为空");
			return false;
		}
		if(effectivebeforeposition=== 'undefined' || effectivebeforeposition === null || effectivebeforeposition === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("异动前职位不能为空");
			return false;
		}
		if(effectiveafterdept=== 'undefined' || effectiveafterdept === null || effectiveafterdept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("异动后中心/部门不能为空");
			return false;
		}
		if(effectiveafterposition=== 'undefined' || effectiveafterposition === null || effectiveafterposition === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("异动后职位不能为空");
			return false;
		}
		if(salarybefore=== 'undefined' || salarybefore === null || salarybefore === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("奴前月薪不能为空");
			return false;
		}
		if(salaryafter=== 'undefined' || salaryafter === null || salaryafter === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("调整后月薪不能为空");
			return false;
		}
		if(salaryproportion=== 'undefined' || salaryproportion === null || salaryproportion === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("调整比例不能为空");
			return false;
		}
		if(effectiveresult == 'undefined'  || effectiveresult == null || effectiveresult === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("异动原因不能为空");
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