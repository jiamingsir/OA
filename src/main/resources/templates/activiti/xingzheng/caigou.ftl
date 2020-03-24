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
				disabled="disabled">物品采购申请和领用</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->

			<form action="caigoushenqing" enctype="multipart/form-data" method="post" onsubmit="return check();" >
				<div class="box-header">
					<input type="hidden" value="物品采购申请和领用" name = "processname"/>
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
							<td  colspan="5"><input type="text" class="form-control input" value="${user.realName}" readonly="readonly" name="sender"/></td>
							<td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control inpu holistart" readonly  name="senddate"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">使用部门</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" value="${user.dept.deptName}" readonly="readonly" name="dept" id="dept"/></td>
							<td class="title" ><label class="control-label">申请设备(物资)</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="goods" id="goods"/></td>
							<td></td>
						</tr>

						<tr >
							<td class="title" ><label class="control-label">类别</label><span style="color:red">*</span></td>
							<td><input type="radio" value="办公类" name="type"/>办公类</td>
							<td><input type="radio" value="工程物资类" name="type"/>工程物资类</td>
							<td><input type="radio" value="其他类" name="type"/>其他类</td>
							<td></td>

						</tr>
						<tr >
							<td class="title" ><label class="control-label">预算总价格(元)</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="number" class="form-control input" name="sendprice" id="sendprice"/></td>
							<td></td>
						</tr>
						<tr >
							<td class="title" ><label class="control-label">申请理由</label><span style="color:red">*</span></td>
							<td  colspan="5"><input type="text" class="form-control input" name="reason" id="reason"/></td>
							<td></td>
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


		var goods =$("#goods").val();
		var dept =$("#dept").val();
		var sendprice =$("#sendprice").val();
		var reason =$("#reason").val();
		var type = $('input:radio[name="type"]:checked').val();




		if(goods=== 'undefined' || goods === null || goods === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("申请设备(物资)不能为空");
			return false;
		}
		if(dept=== 'undefined' || dept === null || dept === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("部门不能为空");
			return false;
		}
		if(sendprice=== 'undefined' || sendprice === null || sendprice === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("预算总价格不能为空");
			return false;
		}
		if(reason=== 'undefined' || reason === null || reason === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("申请理由不能为空");
			return false;
		}
		if(type== 'undefined'  || type == null || type === ""){
			$(this).parent().addClass("has-error has-feedback");
			alertCheck("类别不能为空");
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