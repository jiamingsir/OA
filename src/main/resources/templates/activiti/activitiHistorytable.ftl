	<div class="bgc-w box box-primary" id="body">
		<!--盒子头-->
		<div class="box-header">
			<h3 class="box-title">
				<a href="" class="label label-success" style="padding: 5px;">
					<span class="glyphicon glyphicon-refresh"></span> 刷新
				</a>
			</h3>
				<div>
                    <div class="row clearfix">
                        <div class="col-md-2 column">
                            <label class="control-label" style="padding-top: 6px"> <span>时间区间查询：</span></label>
                            <input name="datebegin" id="datebegin" class="form-control yyyymmdd" value="${(datebegin)!''}"/>
                        </div>
                        <div class="col-md-2 column">
                            <label class="control-label" style="padding-top: 6px"> <span>-</span></label>
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
							<div class="col-md-2 column">
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
							<div class="col-md-2 column">
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
		</div>
		<div class="box-body no-padding">
			<div class="table-responsive">
				<table class="table table-hover">
				<table class="table table-hover">
					<tr>
						<#if url == "activitiPassedHistory">
							<th scope="col">发起人</th>
						</#if>
						<th scope="col">流程名称</th>
						<th scope="col">申请时间</th>
						<th scope="col">状态</th>
						<th scope="col">操作</th>
					</tr>
					<#list prolist as pro>
					<tr>
						<#if url == "activitiPassedHistory">
							<td scope="col">${(pro.shenuser)!''}</td>
						</#if>
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
	<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
	<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
	<script type="text/javascript" src="js/common/data2.js"></script>
<script>

		/* 分页插件按钮的点击事件 */
		$('.baseKetsubmit').on('click',function(){
			var baseKey=$('.baseKey').val();
			var datebegin = $('#datebegin').val();
			var dateend = $('#dateend').val();
			var processSelect = $('#process option:selected').val();
			var sender = $('#sender').val();
			/*var deptSelect = $("#dept option:selected").val();*/
			$('#body').load("${urltable}",{datebegin:datebegin,dateend:dateend,processSelect:processSelect,sender:sender/*,deptSelect:deptSelect*/});


		});
		
</script>