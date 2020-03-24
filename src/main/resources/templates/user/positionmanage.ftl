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
			<h1 style="font-size: 24px; margin: 0;" class="">职位管理</h1>
		</div>
		<div class="col-md-10 text-right">
			<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
				disabled="disabled">职位管理</a>
		</div>
	</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box box-primary">
			<!--盒子头-->
			<div class="box-header">
				<h3 class="box-title">
					<a href="positionedit" class="label label-success" style="padding: 5px;">
						<span class="glyphicon glyphicon-plus"></span> 新增
					</a>
				</h3>
				<div class="box-tools">
					<label class="control-label" style="padding-top: 6px"> <span>部门：</span></label>
					<select class="deptselect form-control" name="deptid" id="deptid" style="height:30px;padding:0px 12px;margin:0px 150px 0px 0px;float:right;width:200px;position:relative">
						<#list deptNames as dept>
							<#if dept.deptId == id>
								<option value="${dept.deptId}" selected="selected" }>${dept.deptName}</option>
							<#else>
								<option value="${dept.deptId}" }>${dept.deptName}</option>
							</#if>

						</#list>
					</select>
				</div>



				<div class="box-tools">

					<div class="input-group" style="width: 150px;">
						<input type="text" class="form-control input-sm baseKey"
							   value="${(basekey)!''}"  placeholder="查找职位..." />
						<div class="input-group-btn">
							<a class="btn btn-sm btn-default baseKetsubmit"><span
										class="glyphicon glyphicon-search"></span></a>
						</div>
					</div>
				</div>
			</div>
			<!--盒子身体-->
			<div class="box-body no-padding">
				<div class="table-responsive">
					<table class="table table-hover table-striped">
						<tr>
							<th scope="col">部门</th>
							<th scope="col">名称</th>
							<#--<th scope="col">层级</th>
							<th scope="col">描述</th>-->

							<th scope="col">操作</th>
						</tr>
						<#list positions as position>
							<tr>
								<td>${position.getDept().getDeptName()}</td>
								<td><span>${position.name}</span></td>
								<#--<td><span>${(position.level)!''}</span></td>
								<td><span>${(position.describtion)!''}</span></td>-->

								<td><a  href="positionedit?positionid=${position.id}" class="label xiugai"><span
										class="glyphicon glyphicon-edit"></span> 修改</a>
									<a href="removeposition?positionid=${position.id}" class="label shanchu"><span
												class="glyphicon glyphicon-remove"></span> 删除</a></td>
							</tr>

						</#list>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</div>
<script>


$('.baseKetsubmit').on('click',function(){
	var baseKey=$('.baseKey').val();
	var deptid = $('#deptid option:selected').val();

	$('#body').load('positionmanage',{baseKey:baseKey,deptid:deptid});
});
</script>
