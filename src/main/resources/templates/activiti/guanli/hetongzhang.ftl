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
				disabled="disabled">合同会签单</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="hetongzhangshenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
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
							<td  colspan="5"><input type="text" class="form-control input" value="${ user.realName}" readonly="readonly" name="sender"/></td>
							<td class="title" ><label class="control-label">申请时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control inpu holistart" readonly="readonly"  name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">合同类型</label><span style="color:red">*</span></td>
							<td><input type="radio" value="阵地合同" name="contracttype"/>阵地合同</td>
							<td><input type="radio" value="销售合同" name="contracttype"/>销售合同</td>
							<td><input type="radio" value="制作合同" name="contracttype"/>制作合同</td>
							<td><input type="radio" value="其他" name="contracttype"/>其他</td>
						</tr>
						<tr>
							<td class="title" ><label class="control-label">注明</label></td>
							<td  colspan="5"><input type="text" class="form-control input" placeholder="合同类型为其他时填写" name="contractremark" id="contractremark"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同版本</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input"  name="contractedition" id="contractedition"/></td>
							<td class="title" ><label class="control-label">合同份数</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="number" class="form-control input" name="contractnum" id="contractnum"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">签回(份)</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="number" class="form-control input" name="contractbacknum" id="contractbacknum"/></td>
							<td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="dept" id="dept"/></td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">合同执行人</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="contractexecutor" id="contractexecutor"/></td>
							<td class="title" ><label class="control-label">合同编号</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="contractnumber" id="contractnumber"/></td>
						</tr >
						<tr >
							<td class="title" ><label class="control-label">甲方名称</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="firstpayname" id="firstpayname" /></td>
							<td class="title" ><label class="control-label">甲方联系人</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="firstpaycontacts" id="firstpaycontacts"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">甲方联系手机</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="firstpayphone" id="firstpayphone"/></td>
							<td class="title" ><label class="control-label">甲方公司电话</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="firstpaycompanyphone" id="firstpaycompanyphone"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">甲方地址</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="firstpayaddress" id="firstpayaddress"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">乙方名称</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" value="上海新云传媒股份有限公司" readonly="readonly" name="secondpayname"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同开始时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="contractbegindate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="contractbegindate"/></td>
							<td class="title" ><label class="control-label">合同结束时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="contractenddate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="contractenddate"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同年限</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="year" id="year"/></td>
							<td class="title" ><label class="control-label">合同总价</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="money" id="money"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">付款方式</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="paytype" id="paytype"/></td>
							<td class="title" ><label class="control-label">发布内容</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="content" id="content"/></td>

						</tr>
						<tr>
							<td class="title" ><label class="control-label">发布媒体(位置\形式\数量)</label><span style="color:red">*</span></td>
							<td  colspan="11"><input type="text" class="form-control input" name="media" id="media"/></td>
						</tr>
					</table>






				</div>
				<div class="box-footer">
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
		var dept = $("#dept").val();
		var contractexecutor =$("#contractexecutor").val();
		var contractnumber = $("#contractnumber").val();
		var contractedition = $("#contractedition").val();
		var contractnum =$("#contractnum").val();
		var contractbacknum = $("#contractbacknum").val();
		var firstpayname =$("#firstpayname").val();
		var firstpaycontacts =$("#firstpaycontacts").val();
		var firstpayphone =$("#firstpayphone").val();
		var firstpaycompanyphone =$("#firstpaycompanyphone").val();
		var firstpayaddress =$("#firstpayaddress").val();
		var contractbegindate =$("#contractbegindate").val();
		var contractenddate =$("#contractenddate").val();
		var year =$("#year").val();
		var money =$("#money").val();
		var paytype =$("#paytype").val();
		var content =$("#content").val();
		var media =$("#media").val();
		var contracttype = $('input:radio[name="contracttype"]:checked').val();

		if(dept=== 'undefined' || dept === null || dept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("部门不能为空");
			return false;
		}
		if(contractexecutor=== 'undefined' || contractexecutor === null || contractexecutor === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同执行人不能为空");
			return false;
		}
		if(contractnumber=== 'undefined' || contractnumber === null || contractnumber === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同编号不能为空");
			return false;
		}

		if(contractedition=== 'undefined' || contractedition === null || contractedition === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同版本不能为空");
			return false;
		}
		if(contractbacknum=== 'undefined' || contractbacknum === null || contractbacknum === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("签回份数不能为空");
			return false;
		}
		if(contractnum=== 'undefined' || contractnum === null || contractnum === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同份数不能为空");
			return false;
		}
		if(firstpayname=== 'undefined' || firstpayname === null || firstpayname === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("甲方名称不能为空");
			return false;
		}
		if(firstpaycontacts=== 'undefined' || firstpaycontacts === null || firstpaycontacts === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("甲方联系人不能为空");
			return false;
		}
		if(firstpaycompanyphone=== 'undefined' || firstpaycompanyphone === null || firstpaycompanyphone === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("甲方公司电话不能为空");
			return false;
		}
		if(firstpayaddress=== 'undefined' || firstpayaddress === null || firstpayaddress === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("甲方地址不能为空");
			return false;
		}

		if(firstpayphone=== 'undefined' || firstpayphone === null || firstpayphone === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("甲方联系手机不能为空");
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
		if(year=== 'undefined' || year === null || year === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同年限不能为空");
			return false;
		}
		if(money=== 'undefined' || money === null || money === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同总价不能为空");
			return false;
		}
		if(paytype=== 'undefined' || paytype === null || paytype === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("付款方式不能为空");
			return false;
		}
		if(content=== 'undefined' || content === null || content === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("发布内容不能为空");
			return false;
		}
		if(media=== 'undefined' || media === null || media === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("发布媒体不能为空");
			return false;
		}
		if(contracttype == 'undefined'  || contracttype == null || contracttype === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同类型不能为空");
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