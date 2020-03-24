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

			<form action="gongzhangshenqing" enctype="multipart/form-data" method="post" name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(buka.senderid)!''}"/>
					<table class="bo table ">

						<tr >
							<td colspan="14" class="title"><h2>公章申请单</h2></td>

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
											readonly="readonly" value="${(gz.sendername)!''}" name="sendername"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" value="${(gz.applydate)!''}" class="form-control inpu holistart" readonly="readonly"  name="applydate"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  value="${(gz.company)!''}" name="company" readonly="readonly"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">部门</label></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(gz.dept)!''}"  name="dept" readonly="readonly"/></td>
							</td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">所需份数</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="number" class="form-control input" value="${(gz.num)!''}"  name="num" readonly="readonly"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">文件类型</label><span style="color:red">*</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="证明" disabled="disabled"><i>✓</i></label></span><span>证明</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="申请/申报" disabled="disabled"><i>✓</i></label></span><span>申请/申报</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="催款信函" disabled="disabled"><i>✓</i></label></span><span>催款信函</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="报价单" disabled="disabled"><i>✓</i></label></span><span>报价单</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="明细单" disabled="disabled"><i>✓</i></label></span><span>明细单</span></td>

						</tr>
						<tr >
							<td class="title" ></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="营业执照复印件"  disabled="disabled"><i>✓</i></label></span><span>营业执照复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="组织代码复印件"  disabled="disabled"><i>✓</i></label></span><span>组织代码复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="税务登记证复印件" disabled="disabled"><i>✓</i></label></span><span>税务登记证复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="其他" disabled="disabled"><i>✓</i></label></span><span>其他</span></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">内容概述</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" value="${(gz.typeremark)!''}"  name="typeremark" readonly="readonly"/></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同</label></td>

							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="劳动/劳务合同" disabled="disabled"><i>✓</i></label></span><span>劳动/劳务合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="制作/阵地合同" disabled="disabled"><i>✓</i></label></span><span>制作/阵地合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="销售合同" disabled="disabled"><i>✓</i></label></span><span>销售合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="租赁/工程合同" disabled="disabled"><i>✓</i></label></span><span>租赁/工程合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="其他协议/合同" disabled="disabled"><i>✓</i></label></span><span>其他协议/合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="财务相关" disabled="disabled"><i>✓</i></label></span><span>财务相关</span></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">具体情况说明</label></td>
							<td  colspan="6"><input type="text" class="form-control input" value="${(gz.contractremark)!''}"  name="contractremark" readonly="readonly"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">是否备份</label></td>
							<td><input type="radio" value="1" name="isbak" disabled="disabled" <#if ('${(gz.isbak)!}'== '1')>checked</#if>/>是</td>
							<td><input type="radio" value="0" name="isbak" disabled="disabled" <#if ('${(gz.isbak)!}'== '0')>checked</#if>/>否</td>
						</tr>
						<#if show == true>
							<tr >
								<td class="title" ><label class="control-label">意见</label></td>
								<td colspan="6"><input type="text" class="form-control input" name="option" /></td>
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
	function check(){
		return true;
	}

	function aaa() {
		return true;
	}


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