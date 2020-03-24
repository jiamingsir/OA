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
				disabled="disabled">公章申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="gongzhangshenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="${processname}" name = "processname"/>
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
											readonly="readonly" value="${user.realName}" name="sendername"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control inpu holistart" readonly="readonly"  name="applydate"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" name="company" id="company"/></td>
						</tr>


						<tr >
							<td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${user.dept.deptName}"  name="dept"  id="dept"/></td>
							</td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">所需份数</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="number" class="form-control input"  name="num"  id="num"/></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">文件类型</label><span style="color:red">*</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="证明"><i>✓</i></label></span><span>证明</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="申请/申报"><i>✓</i></label></span><span>申请/申报</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="催款信函"><i>✓</i></label></span><span>催款信函</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="报价单"><i>✓</i></label></span><span>报价单</span></td>
							<td><span class="labels"><label><input name="type" type="checkbox" value="明细单"><i>✓</i></label></span><span>明细单</span></td>


						</tr>
						<tr >
							<td class="title" ></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="营业执照复印件"><i>✓</i></label></span><span>营业执照复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="组织代码复印件"><i>✓</i></label></span><span>组织代码复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="税务登记证复印件"><i>✓</i></label></span><span>税务登记证复印件</span></td>
							<td><span class="labels"><label><input type="checkbox" name="type" value="其他"><i>✓</i></label></span><span>其他</span></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">内容概述</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="typeremark"  id="typeremark"/></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">合同</label><span style="color:red">*</span></td>

							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="劳动/劳务合同"><i>✓</i></label></span><span>劳动/劳务合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="制作/阵地合同"><i>✓</i></label></span><span>制作/阵地合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="销售合同"><i>✓</i></label></span><span>销售合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="租赁/工程合同"><i>✓</i></label></span><span>租赁/工程合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="其他协议/合同"><i>✓</i></label></span><span>其他协议/合同</span></td>
							<td ><span class="labels"><label><input type="checkbox" name="contract"  value="财务相关"><i>✓</i></label></span><span>财务相关</span></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">具体情况说明</label><span style="color:red">*</span></td>
							<td  colspan="6"><input type="text" class="form-control input"  name="contractremark"  id="contractremark"/></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">是否备份</label><span style="color:red">*</span></td>
							<td><input type="radio" value="1" name="isbak"/>是</td>
							<td><input type="radio" value="0" name="isbak"/>否</td>
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
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>
<script>
	function check(){
		$('.alert-danger').css('display', 'none');


		var company =$("#company").val();
		var dept =$("#dept").val();
		var num =$("#num").val();
		var typeremark =$("#typeremark").val();
		var contractremark =$("#contractremark").val();
		var type = $('input:checkbox[name="type"]:checked').val();
		var contract = $('input:checkbox[name="contract"]:checked').val();
		var isbak = $('input:radio[name="isbak"]:checked').val();




		if(company=== 'undefined' || company === null || company === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("公司不能为空");
			return false;
		}
		if(dept=== 'undefined' || dept === null || dept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("部门不能为空");
			return false;
		}
		if(num=== 'undefined' || num === null || num === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("所需份数不能为空");
			return false;
		}
		if(typeremark=== 'undefined' || typeremark === null || typeremark === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("内容概述不能为空");
			return false;
		}
		if(contractremark=== 'undefined' || contractremark === null || contractremark === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("具体情况说明不能为空");
			return false;
		}
		if(dept=== 'undefined' || dept === null || dept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("部门不能为空");
			return false;
		}
		if(type== 'undefined'  || type == null || type === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("文件类型不能为空");
			return false;
		}
		if(contract== 'undefined'  || contract == null || contract === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("合同不能为空");
			return false;
		}
		if(isbak== 'undefined'  || isbak == null || isbak === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("是否备份不能为空");
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