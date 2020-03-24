<#include "/common/commoncss.ftl">
<#include "/common/modalTip.ftl"/>

<style type="text/css">
	a {
		color: black;
	}

	a:hover {
		text-decoration: none;
	}

	.bgc-w {
		background-color: #fff;
	}
	#tb{
		text-align: center;

	}
	#tb .a√{

	}
	#tb .a{
		color:red;
	}

	#tb th,#tb td,#tb tr{
		text-align:center;/** 设置水平方向居中 */
		vertical-align:middle;/** 设置垂直方向居中 */
		border:1px solid #0f0f0f; !important;
	}

</style>
<div  id="body" style=" OVERFLOW-X: scroll; scrollbar-face-color:#B3DDF7;scrollbar-shadow-color:#B3DDF7;scrollbar-highlight-color:#B3DDF7;scrollbar-3dlight-color:#EBEBE4;scrollbar-darkshadow-color:#EBEBE4;scrollbar-track-color:#F4F4F0;scrollbar-arrow-color:#000000;">
	<div class="row" style="padding-top: 10px;">
		<div class="col-md-2">
			<h1 style="font-size: 24px; margin: 0;" class="">考勤统计</h1>
		</div>
		<div class="col-md-10 text-right">
			<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
					disabled="disabled">考勤统计</a>
		</div>
	</div>
	<div class="row" style="padding-top: 15px;">
		<div class="col-md-12">
			<!--id="container"-->
			<div class="bgc-w box box-primary">

				<table class="bo table ">

					<tr >
						<td><span>开始时间：</span></td>
						<td><input class="form-control" id="beginDate" name="beginDate"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${(startmonth)!''}"  ></td>
						<td><span>结束时间：</span></td>
						<td><input class="form-control" id="endDate" name="endDate" onclick="WdatePicker()" value="${(endmonth)!''}"/></td>
						<td><input type="button" onclick="syn()" value="同步打卡数据"></td>

						<td><span>预览月份：</span></td>
						<td><input class="form-control" id="choosemonth" name="choosemonth" onclick="WdatePicker({dateFmt:'yyyy-MM'})" value="${(choosemonth)!''}"/></td>
						<td><input type="button" onclick="synAttendance()" value="生成预览图"></td>




					</tr>
					<tr>
						<td><a href="/exportAttendance"><input type="button" value="导出Excel" ></a></td>
					</tr>


				<!--盒子身体-->
				<div class="box-body no-padding" >
					<div class="table-responsive">
						<!--如果有数据，则显示table页 -->
						<#if attendanceList??>
							<table class="table table-hover table-striped" id="tb" style="border: #0f0f0f">
								<tr>
									<th colspan="#{colspan}">上海新云传媒股份有限公司${year}年${month}月员工日常考勤汇总</th>
								</tr>
								<tr>
									<th rowspan="2">序号</th>
									<th rowspan="2">姓名</th>
									<th rowspan="2">部门</th>
									<#list monthday as md>
										<#if md.state = 0>
										<th colspan="2">${md.day}</th>
										<#elseif md.state = 1>
										<td colspan="2"<#-- style="color:red"-->>${md.day}</td>
										</#if>
									</#list>

									<#--<#list monthdayList as list>
										<th scope="col">${list}</th>
									</#list>-->
									<th colspan="8">合计</th>
								</tr>
								<tr>
								<#list monthday as md>
									<#--<#if md.state = 0>
										<td>am</td>
										<td>pm</td>
										<#elseif md.state = 1>
										<td style="color:red">am</td>
										<td style="color:red">pm</td>
									</#if>-->
									<td>am</td>
									<td>pm</td>
								</#list>
									<#--<#list monthdayList as list>
										<th scope="col">${list}</th>
									</#list>-->
									<td>迟到次数</td>
									<td>迟到(分)</td>
									<#--<td>致歉信</td>
									<td>旷工</td>-->
									<td>事假</td>
									<#--<td>病假</td>-->
									<td>年假</td>
									<td>特别假</td>
								</tr>


								<#list attendanceList as a>
									<tr>
										<td>${a.id}</td>
										<td>${a.name}</td>
										<td>${a.dept}</td>
										<td class="${"a"+a.day1am}">${a.day1am}</td>
										<td class="${"a"+a.day1pm}">${a.day1pm}</td>
										<td class="${"a"+a.day2am}">${a.day2am}</td>
										<td class="${"a"+a.day2pm}">${a.day2pm}</td>
										<td>${a.day3am}</td>
										<td>${a.day3pm}</td>
										<td>${a.day4am}</td>
										<td>${a.day4pm}</td>
										<td>${a.day5am}</td>
										<td>${a.day5pm}</td>
										<td>${a.day6am}</td>
										<td>${a.day6pm}</td>
										<td>${a.day7am}</td>
										<td>${a.day7pm}</td>
										<td>${a.day8am}</td>
										<td>${a.day8pm}</td>
										<td>${a.day9am}</td>
										<td>${a.day9pm}</td>
										<td>${a.day10am}</td>
										<td>${a.day10pm}</td>
										<td>${a.day11am}</td>
										<td>${a.day11pm}</td>
										<td>${a.day12am}</td>
										<td>${a.day12pm}</td>
										<td>${a.day13am}</td>
										<td>${a.day13pm}</td>
										<td>${a.day14am}</td>
										<td>${a.day14pm}</td>
										<td>${a.day15am}</td>
										<td>${a.day15pm}</td>
										<td>${a.day16am}</td>
										<td>${a.day16pm}</td>
										<td>${a.day17am}</td>
										<td>${a.day17pm}</td>
										<td>${a.day18am}</td>
										<td>${a.day18pm}</td>
										<td>${a.day19am}</td>
										<td>${a.day19pm}</td>
										<td>${a.day20am}</td>
										<td>${a.day20pm}</td>
										<td>${a.day21am}</td>
										<td>${a.day21pm}</td>
										<td>${a.day22am}</td>
										<td>${a.day22pm}</td>
										<td>${a.day23am}</td>
										<td>${a.day23pm}</td>
										<td>${a.day24am}</td>
										<td>${a.day24pm}</td>
										<td>${a.day25am}</td>
										<td>${a.day25pm}</td>
										<td>${a.day26am}</td>
										<td>${a.day26pm}</td>
										<td>${a.day27am}</td>
										<td>${a.day27pm}</td>
										<td>${a.day28am}</td>
										<td>${a.day28pm}</td>
										<#if a.day29am == "-1">
											<td style="display: none">${a.day29am}</td>
											<td style="display: none">${a.day29pm}</td>
										<#else >
											<td>${a.day29am}</td>
											<td>${a.day29pm}</td>
										</#if>

										<#if a.day30am == "-1">
											<td style="display: none">${a.day30am}</td>
											<td style="display: none">${a.day30pm}</td>
											<#else >
												<td>${a.day30am}</td>
												<td>${a.day30pm}</td>
										</#if>
										<#if a.day31am == "-1">
											<td style="display: none">${a.day31am}</td>
											<td style="display: none">${a.day31pm}</td>
										<#else >
											<td>${a.day31am}</td>
											<td>${a.day31pm}</td>
										</#if>
										<td>${a.latetimes}</td>
										<td>${a.latetime}</td>
										<#--<td>0</td>
										<td>${a.absenteeism?string('0.0')}</td>-->
										<td>${a.trouble?string('0.0')}</td>
										<td>${a.year?string('0.0')}</td>
										<td>${(a.special?string('0.0'))}</td>

									</tr>
								</#list>
							</table>
						</#if>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	<script>

		function synAttendance(){
			var chooseDate = $("#choosemonth").val();
			$.ajax({
				url:"synchronizeAttendance",
				data:{chooseDate:chooseDate},
				success:function(data){
				}
			})
		}

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