	<div class="bgc-w box box-primary" id="body">
		<!--盒子头-->
		<div class="box-header">
			<h3 class="box-title">
				<a href="" class="label label-success" style="padding: 5px;">
					<span class="glyphicon glyphicon-refresh"></span> 刷新
				</a>
			</h3>
			<div>
				<div>
					<label class="control-label" style="padding-top: 6px"> <span>时间区间查询：</span></label>
					<input name="datebegin" id="datebegin" class="form-control yyyymmdd" value="${(datebegin)!''}"/>
				</div>
				<div>
					<label class="control-label" style="padding-top: 6px"> <span>-</span></label>
					<input name="dateend" id="dateend" class="form-control yyyymmdd" value="${(dateend)!''}"/>
				</div>
				<div>
					<label class="control-label" style="padding-top: 6px"> <span>流程查询：</span></label>
					<select class="deptselect form-control" name="process" id="process" >
						<#list processList as process>
							<#if processSelect?? && process == processSelect>
								<option value="${(process)!''}" selected="selected" }>${(process)!''}</option>
							<#else>
								<option value="${(process)!''}" }>${(process)!''}</option>
							</#if>

						</#list>
					</select>
				</div>

				<#--<div>
                    <label class="control-label" style="padding-top: 6px"> <span>部门查询：</span></label>
                    <select class="deptselect form-control" name="dept" id="dept" >
                        <#list deptList as dept>
                            <#if deptSelect?? && dept == deptSelect>
                                <option value="${(dept)!''}" selected="selected" }>${(dept)!''}</option>
                            <#else>
                                <option value="${(dept)!''}" }>${(dept)!''}</option>
                            </#if>

                        </#list>
                    </select>
                </div>-->
					<div>
						<label class="control-label" style="padding-top: 6px"> <span>发起人查询：</span></label>
						<input name="sender" id="sender" class="form-control" value="${(sender)!''}"/>
					</div>



				<div class="box-tools">

					<#--<div class="input-group" style="width: 150px;">
                        <input type="text" class="form-control input-sm baseKey"
                               value="${(basekey)!''}"  placeholder="查找职位..." />-->
					<div class="input-group-btn">
						<a class="btn btn-sm btn-default baseKetsubmit"><span
									class="glyphicon glyphicon-search"></span></a>
					</div>
					<#--</div>-->
				</div>
			</div>
		</div>
		<div class="box-body no-padding">
			<div class="table-responsive">
				<table class="table table-hover">
					<table class="table table-hover">
						<tr>
							<th scope="col">发起人</th>
							<th scope="col">流程名称</th>
							<th scope="col">申请时间</th>
							<th scope="col">状态</th>
							<th scope="col">操作</th>
						</tr>
						<#list prolist as pro>
							<tr>
								<td><span>${(pro.shenuser)!''}</span></td>
								<td style="display: none">${(pro.activitiprocessid)!''}</td>
								<td >${(pro.typeNmae)!''}</td>
								<td><span>${(pro.applyTime)?string("yyyy-MM-dd HH:mm:ss")!''}</span></td>
								<#list statusname as status>
									<#if status.statusId==pro.statusId>
										<td><span class="label ${status.statusColor}">${status.statusName}</span></td>
									</#if>
								</#list>


								<td> <a href="ProcessHistory?processInstanceId=${pro.activitiprocessid}&processname=${pro.typeNmae}" class="label xiugai"><span
												class="glyphicon glyphicon-search"></span> 查看</a>
								</td>
							</tr>
						</#list>
					</table>
				</div>

		<!--盒子尾-->
		<#--<#include "/common/paging.ftl">-->
		</div>
	</div>
<script>
	$(function() {
		$("#checkall").click(function () {
			var ischeck = $(this).is(':checked')
			if (ischeck) {
				console.log("checked");
				$("input[name='taskid']").attr("checked", "true");
			} else {
				console.log("undefinned");
				$("input[name='taskid']").removeAttr("checked");
			}
		});

		$("[name=items]:checkbox").click(function () {
			var flag = true;

			$("[name=items]:checkbox").each(function () {
				if (!this.checked) {
					flag = false;
				}
			});
			if (flag) {
				$(".chec span").removeClass("glyphicon-unchecked").addClass("glyphicon-stop");
			} else {
				$(".chec span").removeClass("glyphicon-stop").addClass("glyphicon-unchecked");
			}
			if ($(this).prop('checked')) {
				$(this).attr("checked", "checked");
			} else {
				$(this).removeAttr("checked");
			}

		})

		$(".chec").click(function (e) {
			e.preventDefault();
			var $this = $(".chec span");
			if ($this.hasClass("glyphicon-unchecked")) {
				$(".chec span").removeClass("glyphicon-unchecked").addClass("glyphicon-stop");
			} else {
				$(".chec span").removeClass("glyphicon-stop").addClass("glyphicon-unchecked");
			}
			$("[name=items]:checkbox").each(function () {

				if ($this.hasClass("glyphicon-stop")) {
					/*$(this).prop("checked","checked");*/
					$(this).prop("checked", !$(this).attr("checked"));
				} else {
					$(this).removeAttr("checked");
				}

			})
		})
		$(".passall").click(function(){

			var  arry=new Array();
			$("[name=items]:checkbox").each(function(){
				if(this.checked){
					//获取被选中了的邮件id
					var taskIds = $(this).val();
					arry.push(taskIds);

				}
			})
			if(arry.length==0){
				return;
			}
			var values=arry.toString();
			$(".thistable").load("passAllProcess",{taskIds:values});

		});


	})
	$('.baseKetsubmit').on('click',function(){
		var baseKey=$('.baseKey').val();
		var datebegin = $('#datebegin').val();
		var dateend = $('#dateend').val();
		var processSelect = $('#process option:selected').val();
		var sender = $('#sender').val();
		/*var deptSelect = $("#dept option:selected").val();*/
		$('#body').load("${url}",{datebegin:datebegin,dateend:dateend,processSelect:processSelect,sender:sender/*,deptSelect:deptSelect*/});

	});
</script>
		<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
		<script type="text/javascript" src="js/common/data2.js"></script>