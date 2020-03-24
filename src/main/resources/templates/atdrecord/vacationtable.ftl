<div class="bgc-w box box-primary">
	<!--盒子头-->
	<div class="box-header">
		<h3 class="box-title">
			<a class="label label-success" href="vacationedit"><span
						class="glyphicon glyphicon-plus"></span> 新增</a>
		</h3>

	</div>
	<!--盒子身体-->
	<div class="box-body no-padding">
		<div class="table-responsive">
			<table class="table table-hover">
				<tr>
					<th scope="col"><span class="paixu thistype"> 类型
						</span></th>
					<th scope="col"><span class="paixu thistype"> 名称
						</span></th>
					<th scope="col"><span class="paixu thistime">调休开始时间
						</span></th>
					<th scope="col"><span class="paixu thistime">调休结束时间
						</span></th>
					<th scope="col"><span class="paixu thisvisit">描述

						</span></th>
					<th scope="col">操作</th>
				</tr>
				<#list vacationList as v>
				<tr>
					<td>${((v.type == '0')) ?string("放假","补假")}</td>
					<td>${(v.name)!''}</td>
					<td>${v.begindate?string('yyyy-MM-dd')!""}</td>
					<td>${(v.enddate)?string('yyyy-MM-dd')!""}</td>
					<td>${(v.descript)!''}</td>
					<td>
						<a href="vacationedit?id=${v.id}" class="label xiugai"> <span
							class="glyphicon glyphicon-search"></span>修改</a>
						<a href="deletevacation?id=${v.id}" class="label shanchu" >
							<span class="glyphicon glyphicon-remove"></span> 删除</a></td>
					</td>
				</tr>
				</#list>
			</table>
		</div>
	</div>
	<!--盒子尾-->
	<#include "/common/paging.ftl"/>
</div>
<script>
/* 显示表格的删除 */
	$('.thistable').on('click','.deletethis',function(){
		console.log($(this).attr('discussId'));
		console.log('${name}');
		var name='${name}';
		var discussId=$(this).attr('discussId');
		if(confirm("确定删除吗？ 不能恢复哟~")){
		window.location.href="/deletediscuss?discussId="+discussId+"&name="+name+"&page="+${page.number};
		}
	});
</script>