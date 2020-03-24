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
		$('.alert-danger').css('display', 'none');
		var reason = $("#reason").val();
		var nodate = $("#nodate").val();
		if(reason=== 'undefined' || reason === null || reason === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("未打卡原因不能为空");
			return false;
		}
		if(nodate=== 'undefined' || nodate === null || nodate === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("未打卡时间不能为空");
			return false;
		}

	}

	function alertCheck(errorMess){
		$('.alert-danger').css('display', 'block');
		// 提示框的错误信息显示
		$('.error-mess').text(errorMess);

	}

	function aaa() {
		console.log(aaa);
		document.rrrr.action="reject";
		document.rrrr.submit();
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

			<form action="hetongzhangshenqing" enctype="multipart/form-data" method="post"  name="rrrr"  onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
					<input type="hidden" value="${(taskid)!""}" name = "taskid"/>
					<input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
					<input type="hidden" name="senderid" value="${(contractcharter.senderid)!''}"/>
                    <input type="hidden" name="id" value="${contractcharter.id}"/>
					<table class="bo table ">

						<tr >
							<th colspan="12" class="title" align="center"><h2>合同会签单</h2></th>
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
							<td  colspan="5"><input type="text" class="form-control input"  readonly="readonly" value="${(contractcharter.sender)!''}" name="sender"/></td>
							<td class="title" ><label class="control-label">申请时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control inpu holistart" readonly="readonly" value="${(contractcharter.sender)!''}" name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">合同类型</label></td>
							<td><input type="radio" value="阵地合同" name="contracttype"  disabled <#if ('${(contractcharter.contracttype)!}'=='阵地合同')>checked</#if>/>阵地合同</td>
							<td><input type="radio" value="销售合同" name="contracttype"  disabled <#if ('${(contractcharter.contracttype)!}'=='销售合同')>checked</#if>/>销售合同</td>
							<td><input type="radio" value="制作合同" name="contracttype"  disabled <#if ('${(contractcharter.contracttype)!}'=='制作合同')>checked</#if>/>制作合同</td>
							<td><input type="radio" value="其他" name="contracttype"  disabled <#if ('${(contractcharter.contracttype)!}'=='其他')>checked</#if>/>其他</td>
						</tr>
						<tr>
							<td class="title" ><label class="control-label">注明</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractremark)!''}"  name="contractremark"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同版本</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractedition)!''}" name="contractedition"/></td>
							<td class="title" ><label class="control-label">合同份数</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="number" class="form-control input" readonly="readonly" value="${(contractcharter.contractnum)!''}" name="contractnum"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">签回(份)</label></td>
							<td  colspan="5"><input type="number" class="form-control input" readonly="readonly" value="${(contractcharter.contractbacknum)!''}" name="contractbacknum"/></td>
							<td class="title" ><label class="control-label">部门</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.dept)!''}" name="dept"/></td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">合同执行人</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractexecutor)!''}" name="contractexecutor"/></td>
							<td class="title" ><label class="control-label">合同编号</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractnumber)!''}" name="contractnumber"/></td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">甲方名称</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.firstpayname)!''}" name="firstpayname" /></td>
							<td class="title" ><label class="control-label">甲方联系人</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.firstpaycontacts)!''}" name="firstpaycontacts"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">甲方联系手机</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.firstpayphone)!''}" name="firstpayphone"/></td>
							<td class="title" ><label class="control-label">甲方公司电话</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.firstpaycompanyphone)!''}" name="firstpaycompanyphone"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">甲方地址</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.firstpayaddress)!''}" name="firstpayaddress"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">乙方名称</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.secondpayname)!''}" name="secondpayname"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同开始时间</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractbegindate)?string('yyyy-MM-dd')!''}" name="contractbegindate"/></td>
							<td class="title" ><label class="control-label">合同结束时间</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.contractenddate)?string('yyyy-MM-dd')!''}" name="contractenddate"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同年限</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.year)!''}" name="year"/></td>
							<td class="title" ><label class="control-label">合同总价</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.money)!''}" name="money"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">付款方式</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.paytype)!''}" name="paytype"/></td>
							<td class="title" ><label class="control-label">发布内容</label></td>
							<td  colspan="5"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.content)!''}" name="content"/></td>

						</tr>
						<tr>
							<td class="title" ><label class="control-label">发布媒体(位置\形式\数量)</label></td>
							<td  colspan="11"><input type="text" class="form-control input" readonly="readonly" value="${(contractcharter.media)!''}" name="media"/></td>
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
<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>