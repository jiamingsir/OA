<#include "/common/commoncss.ftl"/>
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
.paixu:HOVER {
	cursor: pointer;
	color: #337ab7;
}
</style>

<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">节假日管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="index"><span class="glyphicon glyphicon-home"></span> 首页</a>
		> <a disabled="disabled">节假日管理</a>
	</div>
</div>
<#--<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<div class="box">
			<div class="box-header">
				<h3 class="box-title">
					<a class="label label-success" href="vacationedit"><span
								class="glyphicon glyphicon-plus"></span> 新增</a>
				</h3>
			</div>
			<div class="box-body">
				<div id="calendar"></div>
			</div>
		</div>
	</div>
</div>-->

<div class="row" style="padding-top: 15px;">
	<div class="col-md-12 thistable">
		<!--id="container"-->
		<#include "vacationtable.ftl"/>
	</div>
</div>