<#include "/common/commoncss.ftl"/>
<style type="text/css">
a {
	color: black;
	
}
ul li a{
	font-size:13px;
}
a:hover {
	text-decoration: none;
}

.bgc-w {
	background-color: #fff;
}
/*下面是主题回复*/
.chat-box .chat-title {
	padding: 10px;
	border-bottom: 1px solid #ddd;
}

.chat-box .big-img {
	border-radius: 50%;
	border: 2px solid #ddd;
	float: left;
	width: 40px;
	height: 40px;
}

.chat-box .username {
	display: block;
	margin-left: 50px;
}

.chat-box .right-time {
	margin-left: 50px;
	display: block;
}

.chat-box .comment-td {
	padding: 0;
	width: 40px;
}

.chat-box .raply-name {
	color: #54a0ea;
}
</style>

<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;">计划查看</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="index"><span class="glyphicon glyphicon-home"></span> 首页</a>
		> <a disabled="disabled">计划查看</a>
	</div>
</div>

<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box box-primary"
			style="border-top: 3px solid #d2d6de; color: #5f5f5f;height: 100%">
			<!--盒子头-->
			<div class="box-header">
				<h3 class="box-title">
					<a href="javascript:history.back();" class="label label-default" style="padding: 6px;">
						<span class="glyphicon glyphicon-chevron-left">返回</span>
					</a> 
				</h3>

			</div>
			<!--盒子身体-->
			<div class="box-body no-padding chat-box" style="height: 100%">
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="control-label">
                            <span>员工</span>
                        </label>
                        <input class="form-control" name="name" readonly="readonly" value="<#if plan??>${plan.user.realName}</#if>"/>

                    </div>
                    <div class="col-md-6 form-group">
                        <label class="control-label">部门</label>
                        <input class="form-control" name="dept" readonly="readonly" value="<#if plan??>${plan.user.dept.deptName}</#if>"/>

                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="control-label">
                            <span>类型</span>
                        </label>
                        <input class="form-control" name="type" readonly="readonly" value="<#if plan.typeId??>${typename}</#if>"/>

					<#--<select class="form-control plantype" onchange="dochange(); " name="type">
                            <#list typelist as t>
                                <option ${(plan??&&plan.typeId==t.typeId)?string('selected','')}>${t.typeName}</option>
                            </#list>

                    </select>-->
                    </div>
                    <div class="col-md-6 form-group">
                        <label class="control-label">状态</label>
                        <input class="form-control" name="status" readonly="readonly" value="<#if plan.statusId??>${statusname}</#if>"/>

                        <#--<select class="form-control" name="status">
						    <#list statuslist as s>
                                <option ${(plan??&&plan.statusId==s.statusId)?string('selected','')} >${s.statusName}</option>
							</#list>
                        </select>-->
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
						<span>
						<label class="control-label" >开始时间</label>
						</span>
                        <input class="form-control shijian" id="starTime" readonly="readonly" name="startTime" value="<#if plan??>${plan.startTime}</#if>"/>
                    </div>
                    <div class="col-md-6 form-group">
                        <label class="control-label">结束时间</label>
                        <input class="form-control shijian" id="endTime" readonly="readonly" name="endTime" value="<#if plan??>${plan.endTime}</#if>"/>
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="control-label">标题</label>
                        <input class="form-control" name="title" readonly="readonly" value="<#if plan??>${plan.title}</#if>"/>
                    </div>
                    <div class="col-md-6 form-group" style="position: relative;">
                        <label class="control-label">标签</label>
					<#--<input class="form-control" name="label" value="<#if plan??>${plan.label}</#if>"/ >-->
                        <input class="form-control" name="title" readonly="readonly" value="<#if plan??>${plan.label}</#if>"/>

                        <#--<select class="form-control" name="label">
						    <#list lablelist as l>
                                <option ${(plan??&&plan.lableid==l.id)?string('selected','')} >${l.workLabel}</option>
							</#list>
                        </select>-->
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 form-group">
                        <label class="control-label">计划</label>
                        <textarea style="overflow-y:hidden; height:120px;" onpropertychange="this.style.height=this.scrollHeight + 'px'" oninput="this.style.height=this.scrollHeight + 'px'"
                                class="form-control text"  readonly="readonly" name="planContent" ><#if plan??>${plan.planContent}</#if></textarea>
                    </div>
                    <div class="col-md-6 form-group">
                        <label class="control-label">总结</label>
                        <textarea style="overflow-y:hidden; height:120px;" onpropertychange="this.style.height=this.scrollHeight + 'px'" oninput="this.style.height=this.scrollHeight + 'px'"
                                class="form-control text"  readonly="readonly" name="planSummary" ><#if plan??>${plan.planSummary}</#if></textarea>
                    </div>
                </div>

                <div class="row">
                    <#--<span id="ctl00_cphMain_lblDescription">附件信息：</span>-->
                    <span id="ctl00_cphMain_lblFeedback">
						<div style="padding-left: 64px;">
							<p><#--<#if plan.attachId??>-->
							<#--<#if filetype=="img">
							<a href="javacript:void(0);" class="label xiugai yulan" title="图片预览">
							<span class="glyphicon glyphicon-search"></span> 预览</a>
							</#if>-->
                                <#if fileatta??>
                                    <#list fileatta as file>
                                    <#--<a href="file?fileid=${(mail.mailFileid.attachmentId)!''}" class="label xiugai">-->
                                        <a href="file?fileid=${(file.attachmentId)!''}" class="label xiugai">
                                        <span class="glyphicon glyphicon-download-alt"></span> ${(file.attachmentName)!''}下载</a>
                                    </#list>
                               <#-- </#if>-->
                                <#else >
                                <div ><kbd><a  href="" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"> 无附件
                            </i></a></kbd></div>
							</#if></p>
						</div>
					</span>
                </div>
                <#--<div  style="display: none"><input type="text"  id ="per" class="form-control input" value="${(notice.src)!""}" readonly="readonly"  name="src"/></div>-->
                <#--style="display: none"-->
			</div>
			<!--盒子尾-->
		</div>
	</div>
</div>
<script>
    $("textarea").autoTextarea({
        maxHeight:220,//文本框是否自动撑高，默认：null，不自动撑高；如果自动撑高必须输入数值，该值作为文本框自动撑高的最大高度
    })
	/*$('#srchref').on('click',function () {
        //console.log(this.href);
        //var data1 =event.preventDefault();
        var data2 =this.href;
        $.ajax({
            url:data2,
            type:'get',
            data:data2,
            success:function(){

            }
        })
    })*/


   /* $(function(){
        var hrefs = $('#per').val();
        if (hrefs!=''||hrefs!=null) {
            var hrefArr = hrefs.split(';');

            /!*var a1 = '<kbd><a  href="downfilecom?Src=';*!/
            var a1 = '<kbd><a  href="informdownfiles?Src=';
            var a2 = '" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"> 下载&nbsp;';
            var a3 = '</i></a></kbd>&nbsp;&nbsp;';


            for (var i = 0; i < hrefArr.length - 1; i++) {
                var nameArr = hrefArr[i].split('/');
                var name = nameArr[nameArr.length - 1];
                $("#tdas").append(a1 + hrefArr[i] + a2 + name + a3);
            }
        }

        //$(this).attr("href",Ahref);
    })*/

    //下载附件
   /* $('#srchref').on('click',function () {
        //console.log(this.href);
        //var data1 =event.preventDefault();
        var data2 =this.href;
        var per =$('#per').val();
        var data1 ="informdownfile?Src="+per;
        $.ajax({
            url:data1,
            type:'get',
            data:data1,
            success:function(){

            }
        })
    })*/
</script>
