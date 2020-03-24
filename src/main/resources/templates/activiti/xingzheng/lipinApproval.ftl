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
				disabled="disabled">公章申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="lipinshenling" enctype="multipart/form-data" method="post" name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${(processname)!""}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(lipin.senderid)!''}"/>
					<table class="bo table ">

						<tr >
							<td colspan="14" class="title"><h2>礼品申领</h2></td>

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
													readonly="readonly" value="${(lipin.sender)!''}"name="sender"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control inpu holistart" readonly="readonly" value="${(lipin.senddate)!''}" name="senddate"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
							<td  colspan="6">
								<input type = "radio" name = "company" value="资产管理" disabled="disabled" <#if ('${(lipin.company)!}'=='资产管理')>checked</#if>>资产管理
								<input type = "radio" name = "company" value="新云传媒" disabled="disabled" <#if ('${(lipin.company)!}'=='新云传媒')>checked</#if>>新云传媒
								<input type = "radio" name = "company" value="新云文化" disabled="disabled" <#if ('${(lipin.company)!}'=='新云文化')>checked</#if>>新云文化
								<input type = "radio" name = "company" value="新云投资" disabled="disabled" <#if ('${(lipin.company)!}'=='新云投资')>checked</#if>>新云投资
								<input type = "radio" name = "company" value="其他公司" disabled="disabled" <#if ('${(lipin.company)!}'=='其他公司')>checked</#if>>其他公司
							</td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">申请部门</label></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(lipin.dept)!''}"  name="dept"/></td>
							</td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">受款单位</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(lipin.collectdept)!''}"   name="collectdept"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">受款个人</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(lipin.collectname)!''}"   name="collectname"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">礼品</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(lipin.gift)!''}"  name="gift"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">数量</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  readonly="readonly" value="${(lipin.num)!''}"  name="num"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">备注</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(lipin.remark)!''}"  name="remark"/></td>
						</tr>
						<#if show == true>
							<tr >
								<td class="title" ><label class="control-label">意见</label></td>
								<td ><input type="text" class="form-control input" name="option" /></td>
							</tr>
						</#if>

					</table>
				</div>
				<div class="box-footer" style="margin:0 15%">
					<#if show == true>
						<input class="btn btn-primary" id="save" type="submit" value="同意" onclick="{return confirm('确定要保存吗？');};"  />
						<input class="btn btn-default" id="reject" type="button" value="不同意" onclick="aaa()"/>
					</#if>
					<input class="btn btn-default" id="cancel" type="button" value="返回"
						   onclick="window.history.back();" />
				</div>
				<#include "/activiti/processInfo.ftl"/>
				<#include "/activiti/processImage.ftl"/>
		</div>
	</div>
</div>

<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>
<script>


	var s = '${gz.type}';//这里改你数据库读出的
	s=','+s+',';//前后加限定符
	var typelist = document.getElementsByName('type');//这里改你checkbox的name值
	for (var i = 0; i < typelist.length; i++)
		if (s.indexOf(',' + typelist[i].value + ',') != -1) typelist[i].checked = true;

	var b = '${gz.contract}';//这里改你数据库读出的
	b=','+b+',';//前后加限定符
	var contractlist = document.getElementsByName('contract');//这里改你checkbox的name值
	for (var i = 0; i < contractlist.length; i++)
		if (b.indexOf(',' + contractlist[i].value + ',') != -1) contractlist[i].checked = true;





</script>
<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>