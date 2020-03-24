<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
    td {

    }
</style>
<body>
<table class="table table-striped table-hover table-bordered table-responsive" style="table-layout: fixed;">
					<tr>
						<th>部门</th>
						<th>成员</th>
						<th>计划</th>
                        <th>时间</th>
						<th>状态</th>
						<th>总结</th>
						<th>操作</th>
						
					</tr>
					
					<#if uMap??>
						<#list uMap?keys as userName>
						<#--<#if ulist??>-->
							<#list ulist as user>
<#--<#if user.userName??>-->
						  			<#if userName==user.userName>
										<#if uMap["${userName}"]??>
										<#assign ulist1 = uMap["${userName}"]>

										<#list ulist1 as umap>
										<tr>
						    			<td>${user.dept.deptName}</td>
										<td>${user.realName}</td>
				     	   					<td style="width:500px;white-space:nowrap;overflow:hidden;text-overflow: ellipsis;">
												<#if umap??>
				     	   					<div>【${umap.label}】${umap.title}</div>
				     	   					<div>${umap.planContent}
				     	   					<#if umap.attachId??>
												<a style="color:#337ab7;"href=""><#--down?paid=${umap.attachId}-->
												<span class="glyphicon glyphicon-paperclip"></span>
															</#if></div>
				     	   					
				     	   					</#if>
				     	   					</td>
                                            <td><#if umap??>${umap.startTime}&nbsp;/&nbsp;${umap.endTime}</#if></td>
												<td><#if umap??>
													<#list status as s>
												   		<#if umap.statusId==s.statusId>
												   			<span class="label ${s.statusColor}">
												   			${s.statusName}</span>
												   		</#if>
													</#list>
												</#if></td>
												
											<td style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;"><#if umap??>${umap.planSummary}</#if></td>
                                            <td>
                                                <a href="planshow?pid=<#if umap??>${umap.planId}</#if>"
                                                   class="label xiugai lab"><span class="glyphicon glyphicon-search"></span>
                                                    查看</a>
											</td>
											<#--<td>
											<#if uMap["${userName}"]??>-->
											<!-- 模态框按钮 -->
											<#--<a class="btn thisa" id="${uMap["${userName}"].planId}">
											<span class="label label-success ">
											<i class="glyphicon glyphicon-commenting">
											评论</i>
											</span>
											</a>-->
											<#--<div>【${user.userName}】
											<#if uMap["${userName}"].planComment??>${(uMap["${userName}"].planComment)!''}</#if>
											</div>
											</#if></td>-->
                                        </tr>
											</#list>
										</#if>
									</#if>
<#--</#if>-->

							</#list>
<#--						</#else>
	<tr>
		<td>ulist is null</td>
	</tr>
						</#if>-->
					</#list>
				</#if>
				</table>
<#include "/common/paging.ftl">
<#include "/common/comment.ftl">

<script>
$('.thisa').on('click',function(){
	$("#myModal").modal("toggle");
	$("#commentid").val($(this).attr("id"))
});

var start=$("#start").text();
var end=$("#end").text();

//评论提交
$("#commentsave").click(function(){
	 var $comment=$("#comment").val();
	 var $commentid=$("#commentid").val();
	
	 $.ajax({
		 type:"get",
		 url:'${url}',
		 data:{
			 pid:$commentid,
			 comment:$comment,
			 starttime:'${(starttime)!''}',
			 endtime:'${(endtime)!''}',
			 choose:'${(choose)!''}'
		 },
		 success:function(dates){
			 $(".fade").hide();
			 $(".close").click();
			 $("#refresh").html(dates);
		},
		 error:function(){
		}
	 })
})


</script>
</body>
</html>