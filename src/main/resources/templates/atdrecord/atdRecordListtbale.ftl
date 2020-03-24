<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
</head>
<body>
<table class="table table-striped table-hover table-bordered table-responsive">
	<tr>
		<th>姓名</th>
		<th>打卡日期</th>
		<th>打卡时间</th>
	</tr>
		<#list atdList as atd>
			<tr class="tr">
				<td>${atd.cardno}</td>
				<td>${atd.recdate}</td>
				<td>${atd.rectime}</td>
			</tr>
		</#list>

</table>
<#include "/common/paging.ftl">
</body>
</html>