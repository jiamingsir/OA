<div class="bgc-w box box-primary">
	<!--盒子头-->
	<div class="box-header">
		<h3 class="box-title">
		<#--	<a onclick="javascript:window.print();" class="label label-primary" style="padding: 5px;">
				<i class="glyphicon glyphicon-print"></i> <span>打印</span>
			</a>-->
			<a href="" class="label label-success" style="padding: 5px;margin-left:5px;">
				<span class="glyphicon glyphicon-refresh"></span> 刷新
			</a>
		</h3>
		<div class="box-tools">
			<div class="input-group" style="width: 150px;">
				<input type="text" class="form-control input-sm baseKey"
					   value="${(baseKey)!''}"  placeholder="查找..." />
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

					<th scope="col">用户</th>
					<th scope="col">下载时间</th>
					<th scope="col">下载路径</th>
					<th scope="col">下载文件</th>
					<th scope="col">IP</th>
					<th scope="col">上传/下载</th>
				</tr>

				<#list logList as list>
					<tr>
						<td><span>${(list.user)!''}</span></td>
						<td><span>${(list.downdate)!''}</span></td>
						<td><span>${(list.downpath)!''}</span></td>
						<td><span>${(list.downfile)!''}</span></td>
						<td><span>${(list.ip)!''}</span></td>
						<#if list.type = 1>
							<td><span>下载</span></td>
						<#else>
							<td><span>上传</span></td>
						</#if>
					</tr>
				</#list>
			</table>
		</div>
	</div>

	<#include "/common/paging.ftl">
</div>
		