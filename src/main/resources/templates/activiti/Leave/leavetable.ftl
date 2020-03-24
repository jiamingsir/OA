<#include "/common/commoncss.ftl">
<link rel="stylesheet" href="css/common/tanchuang.css" />
<style>
.modal-open {
    overflow: auto;
}
.box-header{
  text-align: center;
  border-bottom: 0px solid #f4f4f4;
}
.title{
	text-align: center;
}

.inpu{
 margin-top: -6px;

}

.control-label{
	display:inline-block;
	font-weight: 400;
}
.bo{
	margin: 0px auto;
	width: 80%;
}


.table th,.chebox,.table>tbody>tr>td{
font-weight: 400;
 text-align: center;
}
.inside{
width: 100%;
}
.inside thead{
background-color: rgba(76, 175, 95, 0.06);
}
.inside>tbody>tr>td{
 border-top: 0px solid #ddd;
}
.inside>tbody>tr>td{
border-bottom: 1px solid #ddd;
border-left: 1px solid #ddd;
}
.tdrig{
border-right: 1px solid #ddd;
}
.bo>tbody>tr>td,.inside>thead>tr>th {
    border-top: 0px solid #ddd;
    border-bottom: 0px solid #ddd;
    border-left: 0px solid #ddd;
}
.text {
	min-height: 100px;
}
.shuoming{
min-height: 120px;
}
.reciver{
	position: relative;
    float: right;
    margin-top: -28px;
    right: 5px;
    cursor: pointer;
}
</style>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">请假申请</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">请假申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		
		<div class="bgc-w box">
			<form action="saveLeave" enctype="multipart/form-data" method="post" onsubmit="return check();" >
                <input type="hidden" name="taskid" >
			<div class="box-header">
				<table class="bo table ">
			
				<tr >
					<td colspan="14" class="title"><h2>请假申请单</h2></td>
			
				</tr>
				<tr style="opacity: 0;">
					<td colspan="14">11</td>
				</tr>
				<tr >
					<td colspan="14" style="text-align: left;">
						<!--錯誤信息提示  -->
					<div class="alert alert-danger alert-dismissable" style="display:none;" role="alert">
						错误信息:<button class="thisclose close" type="button">&times;</button>
						<span class="error-mess"></span>
					</div>
					</td>
				</tr>
					<tr >
					<td class="title"><label class="control-label">请假说明</label></td>
					<td  colspan="13">
						<textarea class="form-control shuoming" readonly="readonly" style="background-color:#fff;">
员工因临时或突发情况无法到岗，未能提前请病假或事假的，则应用电话通知直接上级批准后，短信通知人事行政部，说明请假事由，并在上班后第一时间补办请假手续，如在2个工作日内未提交请假流程，超过2个工作日补流程无效，均视为旷工，扣除当日双倍工资。  
<#list overtype as out>
${(out.typeColor)!''}
</#list>
						</textarea>
					</td>
				</tr>

				<tr >
                    <td class="title" ><label class="control-label">申请人</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu"
                                            readonly="readonly" style="background-color:#fff;" value="${username}"/></td>
                    <td class="title" ><label class="control-label">请假类型</label></td>
                    <td colspan="6">
                        <select class="form-control inpu" name="type">
							<#list overtype as out>
                                <option value="${out.typeName}">${out.typeName}</option>
							</#list>
                        </select>
                    </td>
                </tr>

                    <tr >
                        <td class="title" ><label class="control-label">部门</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu " name="dept" value="${dept}"
                                                readonly="readonly" style="background-color:#fff;"/></td>


                        <td class="title" ><label class="control-label">公司</label></td>
                        <#--<td  colspan="6">
                            <input type = "radio" name = "company" value="新云传媒"  >新云传媒
                            <input type = "radio" name = "company" value="新云文化"  >新云文化
                            <input type = "radio" name = "company" value="新云投资"  >新云投资
                        </td>-->
                        <td colspan="6">
                            <select class="form-control inpu" name="company">
                                <option value="新云传媒">新云传媒</option>
                                <option value="新云文化">新云文化</option>
                                <option value="新云投资">新云投资</option>
                            </select>
                        </td>
                    </tr>
				
				<tr >
					<td class="title" ><label class="control-label">开始日期</label></td>
					<td  colspan="6"><input type="text" class="form-control shijian"  id="holistart" name="beginDate"
											/></td><#--holistart-->
					<td class="title" ><label class="control-label">结束日期</label></td>
					<td  colspan="6"><input type="text" class="form-control shijian" id="holiend" name="endDate"
                                            /></td>
                    <#--onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})" inpu-->
				</tr>
					<tr >
					<td class="title" ><label class="control-label">请假天数</label></td>
					<td  colspan="6"><input type="text" class="form-control inpu days" name="leaveDays"
					readonly="readonly" style="background-color:#fff;"/></td>
                        <td class="title" ><label class="control-label">临时替代人</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu " id="recive_list" name="replacement"
                                                style="background-color:#fff;"/>
                            <div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
                                      data-toggle="modal" data-target="#myModal">通讯录</span>
                            </div>
						</td>


				</tr>
				
				<tr >
                    <td class="title" ><label class="control-label">请假原因</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu " name="cause"
                                            style="background-color:#fff;"/></td>
                        <td class="title" ><label class="control-label">审批人</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu " name="depter" value="${depter}"
                                                style="background-color:#fff;" readonly="readonly"/></td>
				</tr>
				<tr>
					<td class="title" ><label class="control-label">备注</label></td>
					<td  colspan="6"><textarea class="form-control text "  name="remark"></textarea></td>
				</tr>
                    <tr >

                        <td class="title" ><label class="control-label">相关材料</label></td>
                        <td  colspan="6">
                            <div class="btn btn-default"style="position: relative; overflow: hidden;width: 100%;margin-top: -6px;">
                                <i class="glyphicon glyphicon-open"></i> 上传材料
                                <input type="file" name="filePath" style="opacity: 0; position: absolute;
								 top: 12px; right: 0; " class='inpu' id="files" multiple="multiple">
                            </div>
                        </td>

                    </tr>
				<tr >

					<td colspan="14" style="text-align: right;" >
					<#--<input   type="text" class="day" name="proId.procseedays" hidden="hidden"/>-->
					<#--<input   type="text" value="请假申请" name="val" hidden="hidden"/>-->
						<input class="btn btn-primary" id="save" type="submit" value="保存" />
						<input class="btn btn-default" id="cancel" type="button" value="取消"
						onclick="window.history.back();" />
					</td>
					
				</tr>
				</table>
			</div>
			</form>
		</div>
	</div>
</div>


<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap-datetimepicker.zh-CN.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-datetimepicker.css"/>
<script type="text/javascript" src="js/common/data2.js"></script>

<#include "/common/reciver.ftl">
<#include "/common/modalTip.ftl"/>
<script>

function sunday(){
    //后台返回天数
    var begin = $("#holistart").val();
    var end = $("#holiend").val();
    if (begin!="" && end!=""){
    $.ajax({
        url:"quholidaysum",
        type:"post",
        data:{
            "begin":begin,
            "end":end
        },
        dataType:"json",
        success : function(dates) {
            //alert("data:"+dates)
            $('.days').val(dates);
        }/*,
        error:function(){
            alert("失败了")
        }*/
    })
    }



    var $star = new Date($("#holistart").val());
    var mon = Number($star.getMonth()) + 1;
    var ymd = $star.getFullYear() + "-" + mon + "-" + $star.getDate();
    var hours1 = $star.getHours();
    var $star2 = null;
    if (hours1 >= 12) {
        $star2 = new Date(ymd + ' 12:00:00');
    } else {
        $star2 = new Date(ymd + ' 00:00:00');
    }
    var $end = new Date($("#holiend").val());
    var mon2 = Number($end.getMonth()) + 1;
    var ymd2 = $end.getFullYear() + "-" + mon2 + "-" + $end.getDate();
    var hours2 = $end.getHours();
    var $end2 = null;
    if (hours2 > 12) {
        $end2 = new Date(ymd2 + ' 24:00:00');
    } else {
        $end2 = new Date(ymd2 + ' 12:00:00');
    }
    st = $end2.getTime() - $star2.getTime();
    tt = $end.getTime() - $star.getTime();
    //$(".days").val(Math.ceil(tt/ (24*60*60*1000)));
    var day1 = Math.ceil(st / (12 * 60 * 60 * 1000));

    //$(".days").val(day1 / 2);
}
	$(function(){
		//$(".days").focus(function(){

        $("#holistart").blur(function(){
            var star = $("#holistart").val();
            if (star == "") {

                var errorMess = "请选择开始日期!";
                // 对齐设置错误信息提醒；红色边框
                $('.alert-danger').css('display', 'block');
                // 提示框的错误信息显示
                $('.error-mess').text(errorMess);
                return false;
            }else {
                $('.alert-danger').css('display', 'none');
            }
            sunday();

        });
        $("#holiend").blur(function(){
            var star = $("#holistart").val();
            if (star == "") {

                var errorMess = "请选择开始日期!";
                // 对齐设置错误信息提醒；红色边框
                $('.alert-danger').css('display', 'block');
                // 提示框的错误信息显示
                $('.error-mess').text(errorMess);
                // alertCheck("请选择开始日期！");
                return false;
            }else {
                $('.alert-danger').css('display', 'none');
            }
                sunday();

		});
        $(".days").focus(function(){
            sunday();

        });
        $("#save").mouseover(function(){
            sunday();

        });
	})

//表单提交前执行的onsubmit()方法；返回false时，执行相应的提示信息；返回true就提交表单到后台校验与执行
function check() {
	console.log("开始进入了");
	//提示框可能在提交之前是block状态，所以在这之前要设置成none
	$('.alert-danger').css('display', 'none');
	var isRight = 1;
    var $star=new Date($("#holistart").val());
    var $end=new Date($("#holiend").val());
    if ($end.getTime()<=$star.getTime()){

        var errorMess = "结束日期应大于开始日期!";
        // 对齐设置错误信息提醒；红色边框
        $('.alert-danger').css('display', 'block');
        // 提示框的错误信息显示
        $('.error-mess').text(errorMess);
        isRight = 0;
        return false;
    }
	$('.form-control').each(function(index) {
		// 如果在这些input框中，判断是否能够为空
		if (index<11){
		    var vqal=$(this).val();

            if ($(this).val() == "") {
			if($(this).hasClass("rem")){
				return true;
			}
			// 排除哪些字段是可以为空的，在这里排除
			 if (index == 10) {
				return true;
			}

			
			// 获取到input框的兄弟的文本信息，并对应提醒；
			console.log(index);
			var errorMess = "红色提示框不能为空!";
			// 对齐设置错误信息提醒；红色边框
			$(this).parent().addClass("has-error has-feedback");
			$('.alert-danger').css('display', 'block');
			// 提示框的错误信息显示
			$('.error-mess').text(errorMess);
			
			isRight = 0;
			return false;
			
		} else {

			return true;
		}
            if (index ==5 &&$(this).val() != ""){
                sunday();
            }
        }else {
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

</script>
