<#include "/common/commoncss.ftl">
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
</style>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">员工假期管理</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">员工假期管理</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		<!--id="container"-->
		<div class="bgc-w box">
			<!--盒子头-->
			<form action="holidaysetedit" method="post" onsubmit="return check();">
				<div class="box-header">
					<h3 class="box-title">
						<a href="holidaysetList" class="label label-default" style="padding: 5px;"><#--javascript:history.back();-->
							<i class="glyphicon glyphicon-chevron-left"></i> <span>返回</span>
						</a>
					</h3>
				</div>
				<!--盒子身体-->
				<div class="box-body no-padding">
					<div class="box-body">
						<div class="alert alert-danger alert-dismissable" role="alert"
							style="display: none;">
							错误信息:<button class="close thisclose" type="button">&times;</button>
							<span class="error-mess"></span>
						</div>
						<div class="row">
                            <table class="bo table ">
							<#--<div class="col-md-6 form-group">-->
								<tr>
                                <td class="title"><label class="control-label"><span>员工</span></label></td>
                                <td  colspan="4"><input type="text" class="form-control inpu " id="recive_list" readonly ="readonly" name="username" value="${(holidayset.username)!''}"
														style="background-color:#fff;"/></td>
                                    <td  colspan="2">
								<#if insert == true>
                                    <div class="reciver">
										<span class="label label-success glyphicon glyphicon-plus"
                                			>通讯录</span>
                                    </div>
								</#if>
                                </td>

							<#--</div>-->
                           <#-- <div class="col-md-6 form-group">-->

                                <td class="title"><label class="control-label"><span>备注</span></label> </td>
                                <td  colspan="6"><input
                                    name="remark" class="form-control" value="${(holidayset.remark)!''}"/></td>
                            <#--</div>-->
                                </tr>
							<#--<div class="col-md-6 form-group">-->
								<tr>
                                    <td class="title"><label class="control-label"><span>年假天数</span></label></td>
                                    <td  colspan="6"><input
									name="acomdays" class="form-control" value="${(holidayset.acomdays)!''}"/></td>
							<#--</div>-->
                            <#--<div class="col-md-6 form-group">-->
                                    <td class="title"><label class="control-label"><span>年假剩余</span></label> </td>
                                    <td  colspan="6"><input
                                    name="ayudays" class="form-control" value="${(holidayset.ayudays)!''}"/></td>
								</tr>
                            <#--</div>-->
							<#--<div class="col-md-6 form-group">-->
								<tr>
                                    <td class="title"><label class="control-label"><span>特别假天数</span></label> </td>
                                    <td  colspan="6"><input
									name="bcomdays"  class="form-control" value="${(holidayset.bcomdays)!''}"/></td>
							<#--</div>
							<div class="col-md-6 form-group">-->
                                    <td class="title"><label class="control-label"><span>特别假剩余</span></label> </td>
                                    <td  colspan="6"><input
									name="byudays"  class="form-control " value="${(holidayset.byudays)!''}"/></td>
							<#--</div>-->
								</tr>

							</table>
							<input type="hidden" name="id" value="${(holidayset.id)!''}"/>
						</div>
					</div>
				</div>
				<!--盒子尾-->
				<div class="box-footer">
					<input class="btn btn-primary" id="save" type="submit" value="保存" onclick="{return confirm('确定要保存吗？');};"  />
					<input class="btn btn-default" id="cancel" type="button" value="取消"
						onclick="window.history.back();" />
				</div>
			</form>
		</div>
	</div>
</div>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
<script type="text/javascript" src="js/common/data2.js"></script>
<script type="text/javascript" src="js/common/data.js"></script>
<#include "/common/modalTip.ftl"/>
<#include "/common/reciver.ftl">
<script type="text/javascript">
$(".usernameonliy").on("blur",function(){
	console.log("改变了！！~~");
	$.post("useronlyname",{"username":$(this).val()},function(data){
		console.log(data);
		$(".usernameonliyvalue").val(data);
	});
}); 
$(".usernameonliy").focus(function(){
	$(this).parent().removeClass("has-error has-feedback");
	$('.alert-danger').css('display', 'none');
});





$("#deptid").on("change",function(){
	//alert("部门选择变化");
	var selectdeptid = $(this).val();
	
	$.post("selectdept",{selectdeptid:selectdeptid},function(data){
		$(".positionselect").empty();
		
		//console.log(data);
		$.each(data,function(i,item){
			var potion = $("<option value="+item.id+">"+item.name+"</option>");
			$(".positionselect").append(potion);
		});
	});
	
});



function alertCheck(errorMess){
	
	$('.alert-danger').css('display', 'block');
	// 提示框的错误信息显示
	$('.error-mess').text(errorMess);
	
}
//表单提交前执行的onsubmit()方法；返回false时，执行相应的提示信息；返回true就提交表单到后台校验与执行
function check() {
	console.log("开始进入了");
	//提示框可能在提交之前是block状态，所以在这之前要设置成none
	$('.alert-danger').css('display', 'none');
	var isRight = 1;
	$('.form-control').each(function(index) {
		// 如果在这些input框中，判断是否能够为空
		if ($(this).val() == "") {
			// 排除哪些字段是可以为空的，在这里排除
			if (index == 1 ) {
				return true;
			}
			// 获取到input框的兄弟的文本信息，并对应提醒；
			var brother = $(this).siblings('.control-label').text();
			var errorMess = "[" + brother + "输入框信息不能为空]";
			// 对齐设置错误信息提醒；红色边框
			$(this).parent().addClass("has-error has-feedback");
			$('.alert-danger').css('display', 'block');
			// 提示框的错误信息显示
			$('.error-mess').text(errorMess);
			// 模态框的错误信息显示
			$('.modal-error-mess').text(errorMess);
			isRight = 0;
			return false;
		} else {
			if(index == 0){
				
				var aaa= $(".usernameonliyvalue").val();
				if(aaa=="false"){
					console.log("进来了0");
					$(this).parent().addClass("has-error has-feedback");
 					alertCheck("用户名已存在");
 					isRight = 0;
 		 			return false;
				}
				
			}
			if(index == 2 || index == 3 || index == 4 || index == 5){
                var its = $(this).val();
                
				if (isNaN(its)){
                    var brother = $(this).siblings('.control-label').text();
                    var errorMess = "[" + brother + "输入框信息应为数字]";
                    // 对齐设置错误信息提醒；红色边框
                    $(this).parent().addClass("has-error has-feedback");
                    $('.alert-danger').css('display', 'block');
                    // 提示框的错误信息显示
                    $('.error-mess').text(errorMess);
                    // 模态框的错误信息显示
                    $('.modal-error-mess').text(errorMess);
                    isRight = 0;
                    return false;
				}else {

				}
			}
			
			
			/*if(index == 1){
				var $tel = $(this).val();
				
				if(isPhoneNo($tel) == false){
					$(this).parent().addClass("has-error has-feedback");
 					alertCheck("手机格式错误");
 					isRight = 0;
 		 			return false;
				}
			}*/
			if(index == 3){
				var $email = $(this).val();
				
				if(isMailNo($email) == false){
					$(this).parent().addClass("has-error has-feedback");
 					alertCheck("邮箱格式错误");
 					isRight = 0;
 		 			return false;
				}
			}
			/*if(index == 7){
				var $idcard = $(this).val();
				
				if(isCardNo($idcard) == false){
					$(this).parent().addClass("has-error has-feedback");
 					alertCheck("错误身份证");
 					isRight = 0;
 		 			return false;
				}
			}*/
			if(index == 8){
				var $bank = $(this).val();
				
 				if(CheckBankNo($bank) == false){
 					$(this).parent().addClass("has-error has-feedback");
 					isRight = 0;
 		 			return false;
 				}
			}
			// 在这个里面进行其他的判断；不为空的错误信息提醒
			return true;
		}
	});
	if (isRight == 0) {
		//modalShow(0);
		 return false;
	} else if (isRight == 1) {
		//modalShow(1);
		 return true;
	}
//	return false;
}

/*$("#begindate").datetimepicker({
	format: 'yyyy-mm-dd hh:ii:ss',//显示格式
	todayHighlight: 1,//今天高亮
	minView: 1,
	startView: 2,
	forceParse: 0,
	language: 'zh-CN',
	autoclose: 1,//选择后自动关闭
	todayBtn:true
})

$("#enddate").datetimepicker({
	format: 'yyyy-mm-dd hh:ii:ss',//显示格式
	todayHighlight: 1,//今天高亮
	minView: 1,//设置只显示到月份
	startView: 2,
	forceParse: 0,
	language: 'zh-CN',
	autoclose: 1,//选择后自动关闭
	todayBtn:true
})*/

$(function(){
    $('.reciver').on('click',function(){
        $('#myModal').modal("toggle");
        $(this).siblings("input").val("");
        $('.reciver').removeClass("qu");
        $(this).addClass("qu");
    });
    $(".recive_list").change(function(){
        var	$val=$(this).val();
        $(".qu").siblings("input").val($val);

    });

    $(".tbody").on("click",".sub",function(){
        $('#subject').modal("toggle");
        $(this).siblings("input").val("");
        $('.sub').removeClass("je");
        $(this).addClass("je");
    });
    $(".ject").change(function(){
        var	$val=$(this).val();
        $(".je").siblings("input").val($val);
        console.log("jinlai");

    });
    var i=1;
    //增加一行
    $(".zeng").click(function(){
        var date=new Date();
        var nowDate=date.Format('yyyy-MM-dd hh:mm:ss');
        var star=addDate(nowDate,0);
        var td1 = $('<td class="chebox" colspan="2"></td>').append($('<span class="labels"></span>').append($('<label></label>').append($('<input type="checkbox" name="items"  class="val" >')).append($('<i></i>').text('✓'))));
        var td2 = $('<td  colspan="2"></td>').append($('<input type="text" class="form-control inpu incar" name="details['+i+'].produceTime"/>').val(star));
        var td3 = $('<td colspan="2"></td>').append($('<input type="text" class="form-control inpu" name="details['+i+'].subject" readonly="readonly" style="background-color:#fff;"/>'))
                .append($('<div class="sub"></div>').append($('<i class="glyphicon glyphicon-search"></i>')));
        var td4 = $('<td colspan="2"></td>').append($('<input type="text" class="form-control inpu" name="details['+i+'].descript"/>'));
        var td5 = $('<td colspan="2"></td>').append($('<input type="text" class="form-control inpu" name="details['+i+'].invoices"/>'));
        var td6 = $('<td colspan="2" class="tdrig"></td>').append($('<input type="text" class="form-control inpu" name="details['+i+'].detailmoney"/>'));
        var tr = $('<tr class="tr"></tr>').append(td1).append(td2).append(td3).append(td4).append(td5).append(td6);
        $('.tbody').append(tr);
        i=i+1;
    });



    //把tr置空
    $(".jian").click(function(){

        $("[name=items]:checkbox").each(function(){
            if(this.checked){
                //获取被选中了的行
                var $tr=$(this).parents(".tr");
                $tr.html("");

            }
        })
    });



});


</script>
