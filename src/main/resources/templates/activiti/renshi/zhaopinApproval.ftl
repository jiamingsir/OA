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

			<form action="zhaopinshenqing" enctype="multipart/form-data" method="post" name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(recruit.senderid)!''}"/>
                    <input type="hidden" name="id" value="${recruit.id}"/>
					<table class="bo table ">

						<tr >
							<th colspan="12" class="title" align="center"><h2>招聘需求</h2></th>
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
							<td  colspan="4"><input type="text" class="form-control input"  readonly="readonly"  value="${(recruit.sender)!''}" name="sender"/></td>
							<td class="title" ><label class="control-label">申请时间</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control inpu holistart" readonly="readonly"  value="${(recruit.sender)!''}" name="senddate"/></td>
							<td></td>
						</tr>
						<th colspan="10"><h4>职位信息</h4></th>
						<tr >
							<td class="title" ><label class="control-label">所属中心</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.deptfather)!''}" name="deptfather"/></td>
							<td class="title" ><label class="control-label">所属部门</label><span style="color:red">*</span></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.dept)!''}" name="dept"/></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">职务名称</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.post)!''}" name="post"/></td>
							<td class="title" ><label class="control-label">要求入职日期</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.joindate)?string('yyyy-MM-dd')!''}" name="joindate"/></td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">直属上级主管</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.leader)!''}" name="leader" /></td>
							<td class="title" ><label class="control-label">性质</label></td>
							<td><input type="radio" value="劳动合同" name="nature" disabled="disabled" <#if ('${(recruit.nature)!}'=='劳动合同')>checked</#if>/>劳动合同</td>
							<td><input type="radio" value="劳务协议" name="nature" disabled="disabled" <#if ('${(recruit.nature)!}'=='劳务协议')>checked</#if>/>劳务协议</td>
							<td><input type="radio" value="兼职" name="nature" disabled="disabled" <#if ('${(recruit.nature)!}'=='兼职')>checked</#if>/>兼职</td>
							<td><input type="radio" value="实习" name="nature" disabled="disabled" <#if ('${(recruit.nature)!}'=='实习')>checked</#if>/>实习</td>
						</tr>
						<th colspan="10"><h4>招聘要求</h4></th>
						<tr >
							<td class="title" ><label class="control-label">年龄</label></td>
							<td  colspan="4"><input type="number" class="form-control input" readonly="readonly"  value="${(recruit.age)!''}" name="age" /></td>
							<td class="title" ><label class="control-label">性别</label></td>
							<td><input type="radio" value="男" name="sex" disabled="disabled" <#if ('${(recruit.sex)!}'=='男')>checked</#if>/>男</td>
							<td><input type="radio" value="女" name="sex" disabled="disabled" <#if ('${(recruit.sex)!}'=='女')>checked</#if>/>女</td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">学历</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.education)!''}" name="education" /></td>
							<td class="title" ><label class="control-label">计算机水平</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.computer)!''}" name="computer" /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">英语水平</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.english)!''}" name="english" /></td>
							<td class="title" ><label class="control-label">工作经验</label></td>
							<td  colspan="4"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.workexperience)!''}" name="workexperience" /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">薪资及绩效(建议)</label></td>
							<td  colspan="4"><input type="number" class="form-control input" readonly="readonly"  value="${(recruit.salary)!''}" name="salary" /></td>
							<td class="title" ><label class="control-label">所需人数</label></td>
							<td  colspan="4"><input type="number" class="form-control input" readonly="readonly"  value="${(recruit.num)!''}" name="num" /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">其他</label></td>
							<td  colspan="8"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.other)!''}" name="other" /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">工作职责</label></td>
							<td  colspan="8"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.job)!''}" name="job" /></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">技能要求</label></td>
							<td  colspan="8"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.skill)!''}" name="skill" /></td>
						</tr>
						<th colspan="10"><h4>招聘原因</h4></th>
						<tr >
							<td class="title" ><label class="control-label">技能要求</label></td>
							<td><input type="radio" value="新增" name="result" disabled="disabled" <#if ('${(recruit.result)!}'=='新增')>checked</#if>/>新增</td>
							<td><input type="radio" value="接任" name="result" disabled="disabled" <#if ('${(recruit.result)!}'=='接任')>checked</#if>/>接任</td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">原因/替换人</label></td>
							<td  colspan="8"><input type="text" class="form-control input" readonly="readonly"  value="${(recruit.resultremark)!''}" name="resultremark" /></td>
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