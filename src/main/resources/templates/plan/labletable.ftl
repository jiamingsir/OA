<div class="table-responsive">
	<table class="table table-hover">
		<tr>
			<th scope="col">部门</th>
			<th scope="col">标签</th>
			<th scope="col">备注</th>
			<!-- <th scope="col">颜色</th> -->
			<th scope="col">操作</th>
		</tr>
		<#list lableList as lable>
		<tr>
			<td><span>${(lable.deptId.deptName)!''}</span></td>
			<td><span>${(lable.workLabel)!''}</span></td>
			<td><span>${(lable.remark)!''}</span></td>
			<td><a href="lableedit?lableid=${lable.id}"
				class="label xiugai"><span class="glyphicon glyphicon-edit"></span>
					修改</a>  <a
				onclick="{return confirm('删除该记录将不能恢复，确定删除吗？');};"
				href="/deletelable?id=${lable.id}" class="label shanchu"><span
					class="glyphicon glyphicon-remove"></span> 删除</a></td>
		</tr>
		</#list>
	</table>
</div>