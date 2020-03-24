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
<script>
	function check(){
		return true;
	}

	function aaa() {
		console.log(aaa);
		document.rrrr.action="reject";
		document.rrrr.submit();
	}
	function reject(){

	}


</script>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">流程管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
				disabled="disabled">物品采购申请和领用</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="huangangshenqing" enctype="multipart/form-data" method="post" name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(changeposition.senderid)!''}"/>
                    <input type="hidden" name="id" value="${changeposition.id}"/>
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
							<td  colspan="4"><input type="text" class="form-control input" value="${ (changeposition.sender)!''}" readonly="readonly" name="sender"/></td>
							<td class="title" ><label class="control-label">申请时间</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.senddate)!''}"   name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">姓名</label></td>
							<td  colspan="4"><input type="text" class="form-control input"  readonly="readonly"  value="${ (changeposition.name)!''}"  name="name"/></td>
							<td class="title" ><label class="control-label">部门/中心</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input"  readonly="readonly" value="${ (changeposition.dept)!''}"  name="dept"/></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">职位</label></td>
							<td  colspan="4"><input type="text" class="form-control input" name="position" readonly="readonly"  value="${ (changeposition.position)!''}"/></td>
						</tr >
						<tr >

							<td class="title" ><label class="control-label">到职日</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.joindate)!''}" name="joindate"  /></td>
							<td class="title" ><label class="control-label">生效日</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.effectivedate)!''}" name="effectivedate"   /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同开始时间</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.contractbegindate)!''}" name="contractbegindate"  /></td>
							<td class="title" ><label class="control-label">合同结束时间</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.contractenddate)!''}" name="contractenddate"  /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动原因</label></td>
							<td><input type="radio" value="转正" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='转正')>checked</#if>/>转正</td>
							<td><input type="radio" value="升职" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='升职')>checked</#if>/>升职</td>
							<td><input type="radio" value="工资调整" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='工资调整')>checked</#if>/>工资调整</td>
							<td><input type="radio" value="降职" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='降职')>checked</#if>/>降职</td>
							<td><input type="radio" value="中心/部门调整" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='中心/部门调整')>checked</#if>/>中心/部门调整</td>
							<td><input type="radio" value="职位调整" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='职位调整')>checked</#if>/>职位调整</td>
							<td><input type="radio" value="中止雇佣" name="effectiveresult"  disabled="disabled" <#if ('${(changeposition.effectiveresult)!}'=='中止雇佣')>checked</#if>/>中止雇佣</td>
						</tr>

						<tr >
							<th class="title" colspan="16" >异动信息</th>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动前中心/部门</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.effectivebeforedept)!''}" name="effectivebeforedept"/></td>
							<td class="title" ><label class="control-label">异动前职位</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.effectivebeforeposition)!''}" name="effectivebeforeposition"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">异动后中心/部门</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.effectiveafterdept)!''}" name="effectiveafterdept"/></td>
							<td class="title" ><label class="control-label">异动后职位</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.effectiveafterposition)!''}" name="effectiveafterposition"/></td>
						</tr>

						<tr >
							<td class="title" colspan="10">薪资信息</td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">目前月薪</label></td>
							<td  colspan="4"><input type="number" class="form-control input" readonly="readonly"  value="${ (changeposition.salarybefore)!''}" name="salarybefore"/></td>
							<td class="title" ><label class="control-label">调整后月薪</label></td>
							<td  colspan="4"><input type="number" class="form-control input" readonly="readonly"  value="${ (changeposition.salaryafter)!''}" name="salaryafter"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">调整比例</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${ (changeposition.salaryproportion)!''}" name="salaryproportion"/></td>
						</tr>
					</table>


				</div>
				<div class="box-footer">
					<#if show == true>
						<input class="btn btn-primary" id="save" type="submit" value="同意" onclick="{return confirm('确定要保存吗？');};"  />
						<input class="btn btn-default" id="reject" type="button" value="不同意" onclick="aaa()"/>
					</#if>
					<input class="btn btn-default" id="cancel" type="button" value="返回"
						   onclick="window.history.back();" />
				</div>

		</div>
	</div>
</div>
<#include "/activiti/processInfo.ftl"/>
<#include "/activiti/processImage.ftl"/>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>

<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>