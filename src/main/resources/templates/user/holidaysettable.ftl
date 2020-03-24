<div class="bgc-w box box-primary">
	<!--盒子头-->
	<div class="box-header">
		<h3 class="box-title">
		<#if manage??>
			<a href="writechat" class="label label-success" style="padding: 5px;">
				<span class="glyphicon glyphicon-plus"></span> 新增
			</a>
		</#if>
			<a href="" class="label label-success" style="padding: 5px;margin-left:5px;">
				<span class="glyphicon glyphicon-refresh"></span> 刷新
			</a>
		</h3>
		<div class="box-tools">
			<div class="input-group" style="width: 150px;">
				<input type="text" class="form-control input-sm baseKey"  value="${(basekey)!''}" placeholder="按员工名搜索..." />
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
			<table class="table table-hover">
				<tr>
					<th scope="col"><span class="paixu thistype"> 员工
						</span></th>
					<th scope="col"><span class="paixu thistype"> 年假天数
						</span></th>
					<th scope="col"><span class="paixu thistime"> 年假剩余
						</span></th>
					<th scope="col"><span class="paixu thistime">特别假天数
						</span></th>
					<th scope="col"><span class="paixu thisvisit">特别假剩余
						</span></th>
                    <th scope="col"><span class="paixu thisvisit">备注
						</span></th>
					<th scope="col">操作</th>
				</tr>
				<#list holidaysetList as v>
				<tr>
					<td>${((v.username )!'')}</td>
					<td>${(v.acomdays)!''}</td>
					<td>${v.ayudays!''}</td>
					<td>${(v.bcomdays)!''}</td>
					<td>${(v.byudays)!''}</td>
                    <td>${(v.remark)!''}</td>
					<td>
						<a href="holidaysetedit?id=${v.id}" class="label xiugai"> <span
							class="glyphicon glyphicon-search"></span>修改</a>
						<a href="deleteholidayset?id=${v.id}" class="label shanchu" >
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
$('.baseKetsubmit').on('click',function(){
    var baseKey=$('.baseKey').val();
    $('#body').load('${returnUrl}?baseKey='+baseKey);
});

</script>