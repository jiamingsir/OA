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
		<h1 style="font-size: 24px; margin: 0;">通知查看</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="index"><span class="glyphicon glyphicon-home"></span> 首页</a>
		> <a disabled="disabled">通知查看</a>
	</div>
</div>

<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box box-primary"
			style="border-top: 3px solid #d2d6de; color: #5f5f5f;">
			<!--盒子头-->
			<div class="box-header">
				<h3 class="box-title">
					<a href="javascript:history.back();" class="label label-default" style="padding: 6px;">
						<span class="glyphicon glyphicon-chevron-left">返回</span>
					</a> 
				</h3>

			</div>
			<!--盒子身体-->
			<div class="box-body no-padding chat-box">
				<div class="chat-title">
					<h4>
						<span>标题：${(notice.title)!''}</span>
					</h4>
					<h5>
						<small> <span>发布部门：${(deptName)!''}</span>;&#12288;<span>发布人：${(realName)!''}</span> <span class="pull-right">${(notice.noticeTime)!''}</span>
						</small>
					</h5>
				</div>
				<div class="chat-content" style="padding: 10px 40px 12px 16px;">
					<p>${(notice.content)!''}
					</p>
					<#if notice.url!=''>
						<kbd><a href="${(notice.url)!''}" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"></i>打开连接</a></kbd>
					<#else>

					</#if>
					<#--<#if notice.src?default("")?trim?length gt 1>

						<kbd><a id ="srchref" href="informdownfile?Src=${(notice.src)!''}"  style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"></i>下载附件</a></kbd>
					<#else>
						<kbd><a href="" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"></i>无附件</a></kbd>
					</#if>-->
					<#if notice.src!=''>

                                <div  id="tdas"></div>
					<#else>
					<#-- <td  colspan="14"><input type="text" class="form-control input "  name=""
                                             value="无附件" readonly="readonly" /></td>-->
                            <div ><kbd><a  href="" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"> 无附件
                            </i></a></kbd></div>
					</#if>
				</div>
                <div  style="display: none"><input type="text"  id ="per" class="form-control input" value="${(notice.src)!""}" readonly="readonly"  name="src"/></div>
                <#--style="display: none"-->
			</div>
			<!--盒子尾-->
		</div>
	</div>
</div>
<script>
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


    $(function(){
        var hrefs = $('#per').val();
        if (hrefs!=''||hrefs!=null) {
            var hrefArr = hrefs.split(';');

            /*var a1 = '<kbd><a  href="downfilecom?Src=';*/
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
    })

    //下载附件
    $('#srchref').on('click',function () {
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
    })
</script>
