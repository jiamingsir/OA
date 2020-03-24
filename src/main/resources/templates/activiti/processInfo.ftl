
<div>
	<table style="width:50%;margin:0 20%">
		<tr>
			<th>流程节点</th>
			<th>执行人</th>
			<th>执行时间</th>
			<th>意见</th>
		</tr>
		<#list historyInfo as info>
			<tr>
				<td>${(info.activityName)!""}</td>
				<td>${(info.assignee)!""}</td>
				<td>${((info.endTime)?string('yyyy-MM-dd HH:mm:ss'))!""}</td>
				<#if historyCommnets?? && (historyCommnets?size > 0) >
					<#list historyCommnets as h >
						<#if info.taskId?? && h.taskId == info.taskId>
							<td>${(h.fullMessage)!""}</td>
						</#if>
					</#list>
					<#else >
				</#if>
			</tr>
	</#list>
	</table>
</div>