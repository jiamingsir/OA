	<div class="bgc-w box box-primary" id="body">
				<!--盒子头-->
				<div class="box-header">

					<div>
                        <div class="row clearfix">
                        <div class="col-md-3 column">
                            <label class="control-label" style="padding-top: 6px"> <span>时间区间查询：</span></label>
                            <#--<input name="datebegin" id="datebegin" class="form-control yyyymmdd" value="${(datebegin)!''}"/>-->
                            <input name="datebegin" id="datebegin" class="form-control yyyymmdd" value="${(datebegin)!''}"/>

						</div>
                        <div class="col-md-3 column">
                            <label class="control-label" style="padding-top: 6px"> <span>-</span></label>
                            <#--<input name="dateend" id="dateend" class="form-control yyyymmdd" value="${(dateend)!''}"/>-->
                            <input name="dateend" id="dateend" class="form-control yyyymmdd" value="${(dateend)!''}"/>
                        </div>
                        <div class="col-md-2 column">
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
						<#if url =="activitiPassedHistory">
							<div class="col-md-3 column">
                                <div>
                                    <label class="control-label" style="padding-top: 6px"> <span>发起人查询：</span></label>
                                    <input name="sender" id="sender" class="form-control" value="${(sender)!''}"/>
                                </div>
                            </div>
						<div class="col-md-1 column">
                            <div >

                                <button type="button" style="position: relative; left: 0px;top: 30px;" class="btn btn-default btn-success baseKetsubmit">查询</button>

                            </div>

                        </div>
						<#else >
							<div class="col-md-3 column">
                                <div >

                                    <button type="button" style="position: relative; left: 0px;top: 30px;" class="btn btn-default btn-success baseKetsubmit">查询</button>
                                </div>
							<#--<div class="input-group-btn">
                                <label class="control-label" style="padding-top: 6px"> <span>查询：</span></label>
                                <a class="btn btn-sm btn-default baseKetsubmit"><span
                                        class="glyphicon glyphicon-search"></span></a>
                            </div>-->

                            </div>
						</#if>
						<#-------------------------------------------------------------------------------->
						<#--<div>
							<label class="control-label" style="padding-top: 6px"> <span>时间区间查询：</span></label>
							<input name="datebegin" id="datebegin" class="form-control yyyymmdd" value="${(datebegin)!''}"/>
						</div>
						<div>
							<label class="control-label" style="padding-top: 6px"> <span>-</span></label>
							<input name="dateend" id="dateend" class="form-control yyyymmdd" value="${(dateend)!''}"/>
						</div>

						<div>
							<label class="control-label" style="padding-top: 6px"> <span>查询流程：</span></label>
							<select class="deptselect form-control" name="process" id="process" >
								<#list processList as process>
									<#if processSelect?? && process == processSelect>
										<option value="${(process)!''}" selected="selected" }>${(process)!''}</option>
									<#else>
										<option value="${(process)!''}" }>${(process)!''}</option>
									</#if>

								</#list>
							</select>
						</div>-->
						<div class="box-tools">

							<#--<div class="input-group" style="width: 150px;">
                                <input type="text" class="form-control input-sm baseKey"
                                       value="${(basekey)!''}"  placeholder="查找职位..." />-->
							<#--<div class="input-group-btn">
								<a class="btn btn-sm btn-default baseKetsubmit"><span
											class="glyphicon glyphicon-search"></span></a>
							</div>-->
							<#--</div>-->
						</div>
						</div>
					</div>
					<div>
						<h3 class="box-title">
						<#--<a class="passall" style="padding: 5px;">
                            <span class="glyphicon glyphicon-hand-up "></span> 一键同意

							</a>-->
                            <button type="button" style="position: relative; left: 0px;top: 20px;" class="btn btn-default btn-success passall">一键同意</button>
						</h3>
				</div>
				<div class="box-body no-padding" style="position: relative; left: 0px;top: 25px;">
					<div class="table-responsive">
						<table class="table table-hover">
						<tr>

							<#--<th scope="col"><span class="labels"><label><input type="checkbox" name="taskids"  class="val" id="checkall" &lt;#&ndash;onclick="checkall()"&ndash;&gt; ><i>✓</i></label></span></th>-->
							<th><a class="btn btn-sm btn-default bac chec" href="##" title="全选/反选"><span
											class="glyphicon glyphicon-unchecked"></span></a></th>
							<th scope="col">发起人</th>
							<th scope="col">流程名称</th>
							<th scope="col">申请时间</th>
							<th scope="col">当前节点</th>
							<#--<th scope="col">状态</th>-->
							<th scope="col">操作</th>
						</tr>
						<#list taskList as task>
						<tr>
							<#--<td> <span class="labels"><label><input type="checkbox" name="taskid"  value="${(task.id)!''}" class="val"  ><i>✓</i></label></span></td>-->
							<#--<td style="display: none">${(task.id)!''}</td>-->
							<#--<td><span><label><input type="checkbox"  value="${(task.id)!''}" name="taskids" class="val" checked><i>✓</i></label></span></td>-->
							<#--<td><input type="checkbox" value="${(task.id)!''}"/></td>-->
							<td>
								<span class="labels"><label><input value="${(task.id)!''}" name="items" type="checkbox"><i>✓</i></label></span>
							</td>
							<td><span>${(task.assignee)!''}</span></td>
							<td><span>${(task.category)!''}</span></td>
							<td><span>${(task.createTime)?string("yyyy-MM-dd HH:mm:ss")!''}</span></td>
							<td><span>${(task.name)!''}</span></td>
							<#--<td><span>未完成（寫死）</span></td>-->
							<#--<#list statusname as status>
								<#if status.statusId==pro.statusId>
									<td><span class="label ${status.statusColor}">${status.statusName}</span></td>
								</#if>
							</#list>-->
							<td>
							<a href="passProcess?taskId=${task.id!""}" class="label xiugai"><span
											class="	glyphicon glyphicon-ok-sign"></span> 同意</a>
							<a href="noPassProcess?taskId=${task.id!""}" class="label xiugai"><span
											class="glyphicon glyphicon-remove-sign"></span> 不同意</a>
							 <a href="ProcessHistory?processname=${(task.processDefinitionId)!''}&taskid=${task.id!""}" class="label xiugai"><span
									class="glyphicon glyphicon-search"></span> 审核</a>
							</td>
						</tr>
						</#list>
					</table>
					</div>
				
				<!--盒子尾-->
				<#--<#include "/common/paging.ftl">-->
				</div>
			</div>
        <link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
        <script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
        <script type="text/javascript" src="js/common/data2.js"></script>
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
			//$(".thistable").load("passAllProcess",{taskIds:values});//原
			//页面嵌套样式
            $.ajax({
                type: "post",
                url: "passAllProcess",
                data: {taskIds:values},
                success: function(data){

                }
            });

		});


	})

	$('.baseKetsubmit').on('click',function(){
		var baseKey=$('.baseKey').val();
		var datebegin = $('#datebegin').val();
		var dateend = $('#dateend').val();
		var processSelect = $('#process option:selected').val();


		$('#body').load("${url}",{datebegin:datebegin,dateend:dateend,processSelect:processSelect ,deptSelect:deptSelectm,sender:sender});
	});

</script>