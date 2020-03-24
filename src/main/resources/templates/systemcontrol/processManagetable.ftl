<div class="table-responsive">
					<table class="table table-hover">
						<tr>
							<th scope="col">序号</th>
							<th scope="col">流程编号</th>
							<th scope="col">流程名称</th>
							<th scope="col">节点编号</th>
							<th scope="col">节点名称</th>
							<th scope="col">下一节点</th>
							<th scope="col">节点状态</th>
							<th scope="col">执行角色</th>
							<th scope="col">备注</th>
							<th scope="col">操作</th>
						</tr>
						<#if processManageList??>
							<#list processManageList as list>
								<tr>
								<td><span>${(list.id)!''}</span></td>
								<td><span>${(list.processnum)!''}</span></td>
								<td><span>${(list.processname)!''}</span></td>
								<td><span>${(list.nodenum)!''}</span></td>
								<td><span>${(list.nodename)!''}</span></td>
								<td><span>${(list.nodenamenext)!''}</span></td>
								<td><span>${(list.nodetype)!''}</span></td>
								<td><span>${(list.executorrole)!''}</span></td>
								<td><span>${(list.remark)!''}</span></td>
								<td><a  href="processListEdit?id=${list.id}" class="label xiugai"><span
												class="glyphicon glyphicon-edit"></span> 修改</a> <a
											onclick="{return confirm('删除该记录将不能恢复，确定删除吗？');};"
											href="deleteProcessList?userid=${list.id}" class="label shanchu"><span
												class="glyphicon glyphicon-remove"></span> 删除</a></td>
								</tr>
							</#list>
						</#if>
					</table>
				</div>