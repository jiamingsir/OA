<div class="bgc-w box box-primary">
	<div class="box-header" style="padding-bottom: 20px">



		<input class="form-control" id="beginDate" name="beginDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${(startmonth)!''}"  >
		<input class="form-control" id="endDate" name="endDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${(endmonth)!''}"/>
		<input type="button" onclick="syn()" value="同步打卡数据">



	</div>
	<#--<div class="box-body">
		<div class="table" style="padding-top: 15px;">
			<div class="">
				<table class="table table-hover table-striped">
					<tr class="table-header">
						<th scope="col">用户名</th>
						<th scope="col" class="paixu thistype" style="color:blue;">类型
										<#if type?? && icon??>
										<span class="glyphicon ${icon}"></span>
										</#if>
									</th>
						<th scope="col"><span class="paixu thistime" style="color:blue;">时间
						<#if time?? && icon??>
							<span class="glyphicon ${icon}"></span>
						</#if>
						</span></th>
						<th scope="col">ip</th>
						<th scope="col ">备注</th>
						<th scope="col"><span class="paixu thisstatus" style="color:blue;">状态
						<#if status?? && icon??>
							<span class="glyphicon ${icon}"></span>
						</#if>
						</span></th>
						<th scope="col">操作</th>
					</tr>
					<#if alist??>
									<#list alist as att>
									<tr>
									<td ><span>
									<#if att.user.userName??>
									${att.user.userName}</#if>
									</span></td>
									<td><span>
									<#if att.typeId??>
									
									<#list typelist as t>
											<#if att.typeId==t.typeId>${t.typeName}</#if>
										</#list>
									</#if>
									</span></td>
									
									<td ><span>
									<#if att.attendsTime??>
									${att.attendsTime}</#if>
									</span></td>
									<td><span><#if att.attendsIp??>
									${att.attendsIp}</#if>
									</span></td>
									<td><#if att.attendsRemark??>
									${att.attendsRemark}</#if>
									</td>
									<td>
									<#if att.statusId??>
										<#list statuslist as s>
												<#if att.statusId==s.statusId><span class="label ${s.statusColor}">${s.statusName}</span></#if>
										</#list>
									</#if>
									</td>
									<td><a  href="attendceedit?aid=${att.attendsId}" class="label xiugai"><span
											class="glyphicon glyphicon-edit"></span> 修改</a> <a
										onclick="{return confirm('删除该记录将不能恢复，确定删除吗？');};" 
										href="attdelete?aid=${att.attendsId}" class="label shanchu"><span
											class="glyphicon glyphicon-remove"></span> 删除</a></td>
										</tr>
									</#list>
									</#if>
							</table>
			</div>
		</div>
	</div>-->


	<!--盒子尾-->
	<#--<#include "/common/paging.ftl">-->
</div>
<script>
	/*$(".form_datetime").datetimepicker({
		autoclose: true,   //选择后自动关闭当前时间控件
		//isRTL: Metronic.isRTL(), //RTL：right to left（从右向左显示），默认为false，即：从左向右显示
		format: "yyyy-mm-dd", //时间格式
		pickerPosition: "bottom-left"//控件显示位置
	});
*/


	function syn(){
		var beginDate = $("#beginDate").val();
		var endDate = $("#endDate").val();
		if($("#beginDate").val()>$("#endDate").val()){
			var errorMess = "[开始日期不能大于结束日期]";
			// 对齐设置错误信息提醒；红色边框
			//开始和结束框提示错误
			$("#beginDate").parent().addClass("has-error has-feedback");
			$("#endDate").parent().addClass("has-error has-feedback");
			$('.alert-danger').css('display', 'block');
			// 提示框的错误信息显示
			$('.error-mess').text(errorMess);
			// 模态框的错误信息显示
			$('.modal-error-mess').text(errorMess);
			isRight = 0;
			return false;
		}

		$.ajax({
			url:"syn",
			data:{beginDate : beginDate , endDate :endDate},
			success:function(data){
				console.log(data);
			}
		})

	}

</script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script><title>Insert title here</title>
<script type="text/javascript" src="js/common/data.js"></script>