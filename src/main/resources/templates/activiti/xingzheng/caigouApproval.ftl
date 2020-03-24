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

			<form action="caigoushenqing" enctype="multipart/form-data" method="post" name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(caigou.senderid)!''}"/>
                    <input type="hidden" name="id" value="${caigou.id}"/>
					<table class="bo table ">
						<tr >
							<th colspan="12" class="title" align="center"><h2>物品采购申请和领用</h2></th>

						</tr>
						<tr >
							<th colspan="12" class="title" align="center"><h3>以下由申请人填写</h3></th>
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
							<td class="title" ><label class="control-label">申请人</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(caigou.sender)!''}" name="sender"/></td>
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control inpu holistart" readonly="readonly" value="${(caigou.senddate)!''}" name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">使用部门</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(caigou.dept)!''}" name="dept"/></td>
							<td class="title" ><label class="control-label">申请设备(物资)</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(caigou.goods)!''}" name="goods"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">类别</label></td>
							<td><input type="radio" value="办公类" name="type"  disabled="disabled" <#if ('${(caigou.type)!}'== '办公类')>checked</#if>/>办公类</td>
							<td><input type="radio" value="工程物资类" name="type"  disabled="disabled" <#if ('${(caigou.type)!}'== '工程物资类')>checked</#if>/>工程物资类</td>
							<td><input type="radio" value="其他类" name="type"  disabled="disabled" <#if ('${(caigou.type)!}'== '其他类')>checked</#if>/>其他类</td>
							<td></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">预算总价格(元)</label></td>
							<td  colspan="5"><input type="number" class="form-control input"  readonly="readonly" value="${(caigou.sendprice)?string('0.00')!0}" name="sendprice"/></td>
							<td></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">申请理由</label></td>
							<td  colspan="5"><input type="text" class="form-control input"  readonly="readonly" value="${(caigou.reason)!''}" name="reason"/></td>
							<td></td>
						</tr>

					</table>

					<table class="bo table" id="addCourse" >
						<tr >
							<th colspan="12" class="title" align="center"><h3>以下由采购人填写</h3>
							<th></th>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">采购人</label><span style="color:red">*</span></td>
							<td  colspan="3"><input type="text" class="form-control input" value="${(caigou.buyuser)!''}" name="buyuser"/></td>
							<td class="title" ><label class="control-label">采购部门</label><span style="color:red">*</span></td>
							<td  colspan="3"><input type="text" class="form-control input" value="${(caigou.buydept)!''}" name="buydept"/></td>
							<td class="title" ><label class="control-label">总价格</label><span style="color:red">*</span></td>
							<td  colspan="3"><input type="text" class="form-control input" value="${(caigou.allprice)!''}"  name="allprice"/></td>
							<td></td>
						</tr>
						<tr >
							<th colspan="12" class="title" align="center"><h4>供货渠道</h4></th>
							<#if show == true>
							<th><a onclick="addCourse()" href="javascript:void(0);" class="label label-success" style="padding: 5px;"><span class="glyphicon glyphicon-plus" ></span> 新增</a></th>
							</#if>
						</tr>

						<#if caigou.caigouSubList??>
							<#list caigou.caigouSubList as sub>
								<tr >
									<td style="display: none"><input type="hidden" value="${sub.id}" name="caigouSubList.caigou[${sub_index}.caigouid"></td>
									<td class="title" ><label class="control-label">供货商</label><span style="color:red">*</span></td>
									<td  colspan="3"><input type="text" class="form-control input" value="${(sub.supplier)!''}" name="caigouSubList[${sub_index}].supplier"/></td>
									<td class="title" ><label class="control-label">规格</label><span style="color:red">*</span></td>
									<td  colspan="3"><input type="text" class="form-control input"  value="${(sub.norms)!''}" name="caigouSubList[${sub_index}].norms"/></td>
									<td class="title" ><label class="control-label">价格</label><span style="color:red">*</span></td>
									<td  colspan="3"><input type="text" class="form-control input" value="${(sub.price)!''}" name="caigouSubList[${sub_index}].price"/></td>
									<td  ><a onclick="deleteCourse(this)" href="javascript:void(0);" class="label label-danger" style="padding: 5px;"><span class="glyphicon glyphicon-minus"></span> 删除</a></td>
								</tr>
							</#list>
						</#if>

					</table>
					<#if show == true>
						<table class="bo table">
							<tr >
								<td class="title" ><label class="control-label">意见</label></td>
								<td colspan="11"><input type="text" class="form-control input" name="option" /></td>
							</tr>
						</table>
					</#if>
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

	var j = $('#addCourse').find("tr").length-4;
	function addCourse(){
		j++;
		var result ="";
		result += '<tr>';
		result += '<td style="display: none"><input type="hidden" value="${caigou.id}" name = "caigouSubList.caigou['+j+'].caigouid"></td>';
		result += '<td class="title" ><label class="control-label">供货商</label><span style="color:red">*</span></td>';
		result += '<td  colspan="3"><input type="text" class="form-control input" name="caigouSubList['+j+'].supplier"/></td>'
		result += '<td class="title" ><label class="control-label">规格</label><span style="color:red">*</span></td>';
		result += '<td  colspan="3"><input type="text" class="form-control input" name="caigouSubList['+j+'].norms"/></td>'
		result += '<td class="title" ><label class="control-label">价格</label><span style="color:red">*</span></td>';
		result += '<td  colspan="3"><input type="text" class="form-control input" name="caigouSubList['+j+'].price"/></td>';
		result += '<td  ><a onclick="deleteCourse(this)" href="javascript:void(0);" class="label label-danger" style="padding: 5px;"><span class="glyphicon glyphicon-minus"></span> 删除</a></td>'
		result += '</tr>';
		$("#addCourse").append(result);

	}
	function deleteCourse(obj){
		var tr=obj.parentNode.parentNode;
		var tbody=tr.parentNode;
		tbody.removeChild(tr);
		j--;
	}


</script>
<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>