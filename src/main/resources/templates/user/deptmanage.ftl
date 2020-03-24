<#include "/common/commoncss.ftl">
<#include "/common/modalTip.ftl"/>

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
</style>
<div  id="body">
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">部门管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">部门管理</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box box-primary">
			<!--盒子头-->
			<div class="box-header">
				<h3 class="box-title">
					<a href="deptedit" class="label label-success" style="padding: 5px;">
						<span class="glyphicon glyphicon-plus"></span> 新增
					</a>

				</h3>
				<div class="box-tools">

					<div class="input-group" style="width: 150px;">
						<input type="text" class="form-control input-sm baseKey"
							   value="${(basekey)!''}"  placeholder="查找..." />
						<div class="input-group-btn">
						<a class="btn btn-sm btn-default baseKetsubmit"><span
									class="glyphicon glyphicon-search"></span></a>
						</div>
					</div>
				</div>
			</div>


			<!--盒子身体-->
			<div class="box-body no-padding" >
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<th scope="col">名称</th>
							<#--<th scope="col">电话</th>
							<th scope="col">邮箱</th>
							<th scope="col">地址</th>-->
							<th scope="col">操作</th>
						</tr>
						<#list depts as dept>
							<tr>
								<td><span>${dept.deptName}</span></td>
								<#--<td><span>${dept.deptTel}</span></td>
								<td><span>${dept.email}</span></td>
								<td><span>${dept.deptAddr}</span></td>-->
								<td>
									<a href="deptedit?dept=${dept.deptId}" class="label xiugai">
									<span class="glyphicon glyphicon-edit"></span> 修改</a> 
									<a href="readdept?deptid=${dept.deptId}" class="label xiugai">
										<span class="glyphicon glyphicon-search"></span> 人事调动
									</a>
									<a href="deletdept?deletedeptid=${dept.deptId}" class="label shanchu" >
										<span class="glyphicon glyphicon-remove"></span> 删除</a></td>
									<#--<a onclick="checkUser(${dept.deptId})" class="label shanchu" >
									<span class="glyphicon glyphicon-remove"></span> 删除</a></td>-->
							</tr>
						</#list>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	/*var deletedeptid  = 0;
	function checkUser(id) {
		deletedeptid = id;
		console.log('checkUser:' + id)
		var execute = 0;
		$.ajax({
			url: '/checkUserAndPosition?positionid=' + id,
			async: true,
			success: function (result) {
				if (result.code == 1) {
					deletedept(id);
				} else {
					alert(result.message);
					console.log(result.message + "1");
					execute = 0;
				}

			}
		})
	}
	function deletedept(deletedeptid) {

			$.ajax({
				url: '/deletdept?deletedeptid='+deletedeptid,
				success:function(){
					window.location = '/deptmanage';
				}

		})

	}*/

	$('.baseKetsubmit').on('click',function(){
		var baseKey=$('.baseKey').val();
		$('#body').load('${url}?baseKey='+baseKey);
	});
</script>
