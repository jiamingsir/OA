<#include "/common/commoncss.ftl">

<style type="text/css">
.imgs {
	display: block;
	width: 100px;
	height: 100px;
}

.list-group li {
	margin-bottom: 10px;
	list-style-type: none;
}

a {
	text-decoration: none !important;
	color:black;
}
a:focus {
    outline:0px auto -webkit-focus-ring-color; 
    color:black;
    
}


.pa {
	margin-top: 12px;
	margin-bottom: 10px;
}

.nav-tabs-custom {
	background-color: #fff;
}

h1, h3 {
	font-family: 'Source Sans Pro', sans-serif;
}

.bo {
	margin-bottom: 12px;
}

.nav-tabs-custom>.nav-tabs>li:first-of-type {
	margin-left: 0;
}

.nav-tabs-custom>.nav-tabs>li.active {
	border-top-color: #3c8dbc;
}

.nav-tabs-custom>.nav-tabs>li {
	border-top: 3px solid transparent;
	margin-bottom: -2px;
	margin-right: 5px;
}

.nav-tabs-custom>.nav-tabs>li:first-of-type.active>a {
	border-left-color: transparent;
}

.nav-tabs-custom>.nav-tabs>li>a, .nav-tabs-custom>.nav-tabs>li>a:hover {
	background: transparent;
	margin: 0;
	color:
}

.nav-tabs-custom>.nav-tabs>li>a:hover {
	color: #999;
}

.nav-tabs-custom>.nav-tabs>li.active>a, .nav-tabs-custom>.nav-tabs>li.active:hover>a
	{
	background-color: #fff;
	color: #444;
}

.nav-tabs-custom>.nav-tabs>li>a {
	color: #444;
	border-radius: 0;
}

.nav-tabs>li>a {
	margin-right: 2px;
	line-height: 1.42857143;
	border: 0px;
	border-radius: 4px 4px 0 0;
}
.list-inline li{
	display:block;
}
.me{
    margin-right: 5px;
}
</style>

<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">用户面板</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">用户面板</a>
	</div>
</div>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-3">

		<div class="bgc-w box box-solid " style="border-top: 3px solid blue;">
			<div class="box-header ">
				<span class="imgs center-block">
				<#if user.imgPath?? && user.imgPath!=''  >
				<img style="width: 110px;height: 110px;"
					class="profile-user-img img-responsive img-circle"
					src="/image/${user.imgPath}" />
				<#else>
				<img style="width: 110px;height: 110px;"
					class="profile-user-img img-responsive img-circle"
					src="images/timg.jpg" alt="images"/>
				</#if>	
					</span>
				<h3 class="profile-username text-center">
					<span id="ctl00_cphMain_lblFullName">${user.realName}</span>
				</h3>
				<p class="text-muted text-center">
					<span id="ctl00_cphMain_lblPosition">${positionname}</span>（<span
						id="ctl00_cphMain_lblDepartment">${deptname}</span>）
				</p>
				<hr class="pa" />

				<ul class="list-group ">
					<li class=""><b>我的消息</b> <a href="##" class="pull-right me" ><span
							id="ctl00_cphMain_lblMsgCount">${noticelist}</span></a></li>
					<hr class="pa" />
					<li class=""><b>我的邮件</b> <a href="mail" class="pull-right me"><span
							id="ctl00_cphMain_lblMailCount">${maillist}</span></a></li>
					<hr class="pa" />
				</ul>
				<a href="##" class="btn btn-primary btn-block wri" data-toggle="modal" data-target="#notepaper"><b><i
						class="glyphicon glyphicon-pushpin"></i> 写便签</b></a>
			</div>

		</div>

		<div class="bgc-w box box-solid" style="border-top: 3px solid blue;">
			<div class="box-header">
				<h3 class="box-title">基本资料</h3>
			</div>
			<div class="box-body">


				<strong><i class="glyphicon glyphicon-earphone"></i> 手机</strong>
				<p class="text-muted">
					<span id="ctl00_cphMain_lblPhone">${(user.userTel)!''}</span>
				</p>

				<hr />

				<strong><i class="glyphicon glyphicon-map-marker"></i> 地址</strong>
				<p class="text-muted">
					<span id="ctl00_cphMain_lblAddress">${(user.address)!''}</span>
				</p>

				<hr />

				<strong><i class="glyphicon glyphicon-pencil"></i> 邮箱昵称</strong>
				<p class="text-muted">
					<span id="ctl00_cphMain_lblNote">${(user.userSign)!''}</span>
				</p>
			</div>
		</div>
	</div>
	<div class="col-md-9" style="margin-bottom: 60px;">

		<div class="tab-content">
			<div class=" nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li class="pwd"><a href="#pwd" data-toggle="tab" >修改密码</a></li>
				</ul>

				<input type="hidden" value="${(user.password)!""}" name="password" id="passwordnow">
				<form action="changepassword" method="post" onsubmit="return changePwdCheck();">
					<div class="active tab-pane thistable" id="pwd">
						<!--錯誤信息提示  -->
						<div class="alert alert-danger alert-dismissable" style="display:none;" role="alert">
							错误信息:<button class="thisclose close" type="button">&times;</button>
							<span class="error-emp"></span>
						</div>
						<div class="row">
							<hr />
							<div class="col-md-6 form-group">
								<label class="control-label"><span id="ctl00_cphMain_Label11">请输入原密码</span></label> <input
										type="password"
										id="oldPassword" class="form-control" />
								<span id="checkpwd"></span>
							</div>

						</div>
						<div class="row">
							<hr />

							<div class="col-md-6 form-group">
								<label class="control-label"><span id="ctl00_cphMain_Label11">新的密码</span></label> <input
										 type="password"
										id="newPassword" class="form-control" />
							</div>

							<div class="col-md-6 form-group">
								<label class="control-label"><span id="ctl00_cphMain_Label12">确认密码</span></label> <input
										name="password" type="password"
										id="newPassword2" class="form-control" />
							</div>
						</div>
						<div class="box-footer" style="position: relative; overflow: hidden;">
							<input type="submit" name="ctl00$cphMain$btnSave" value="保存"
								   id="ctl00_cphMain_btnSave" class="btn btn-primary" />
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>


<#include "/common/modalTip.ftl"> 
<script>

	function changePwdCheck(){

		var oldPassword = $("#oldPassword").val();
		var newPassword = $("#newPassword").val();
		var newPassword2 = $("#newPassword2").val();
		var flag=true;

		if(newPassword.length < 6 || newPassword.length > 19){
			alertCheck("密码长度必须在6到19之间!");
			flag=false;
		}
		if(newPassword2.trim() != newPassword.trim()){
			alertCheck("两次密码必须一致");
			flag=false;
		}
		if(oldPassword != $("#passwordnow").val()){
			alertCheck("原始密码输入错误");
			flag=false;
		}
		return flag;
	}
	function alertCheck(errorMess){

		$('.alert-danger').css('display', 'block');
		// 提示框的错误信息显示
		$('.error-emp').text(errorMess);

	}



</script>
<#include "editpaper.ftl">