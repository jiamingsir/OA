<#include "/common/commoncss.ftl">
<link rel="stylesheet" href="css/common/tanchuang.css" />
<style>
.modal-open {
    overflow:auto;
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
.food{
	padding-left:10px
}
.zeng,.jian{
cursor: pointer;
}
.reciver{
	position: relative;
    float: right;
    margin-top: -28px;
    right: 5px;
    cursor: pointer;
}
.sub{
	position: relative;
    float: right;
    margin-top: -24px;
    right: 9px;
    cursor: pointer;
}
.text{
	min-height:100px;
}
</style>
<div class="row" style="padding-top: 10px;">
	<div class="col-md-2">
		<h1 style="font-size: 24px; margin: 0;" class="">开票申请</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">开票申请</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		
		<div class="bgc-w box">
			<form action="savekaipiao"  enctype="multipart/form-data" method="post" onsubmit="return check();" >
			<div class="box-header">
				<table class="bo table ">
			
				<tr >
					<td colspan="14" class="title"><h2>开票申请单</h2></td>
			
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
					<td class="title"><label class="control-label">标题</label></td>
					<td  colspan="6"><input type="text" class="form-control inpu" name="dept"/></td>
                    <td class="title" ><label class="control-label">公司</label></td>
                    <td  colspan="6">
                        <input type = "radio" name = "company" value="新云传媒"  >新云传媒
                        <input type = "radio" name = "company" value="新云文化"  >新云文化
                        <input type = "radio" name = "company" value="新云投资"  >新云投资
                    </td>

					<#--<td class="title"><span >紧急程度</span></td>
					<td colspan="6">
						<select class="form-control inpu" name="proId.deeply">
							<#list harrylist as harry>
							<option value="${harry.typeId}">${harry.typeName}</option>
							</#list>
						</select>
					</td>-->
					
				</tr>
				<tr >
                    <td class="title" ><label class="control-label">申请人</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu"
                                            readonly="readonly" style="background-color:#fff;" name="senderName" value="${username}"/></td>

					<td class="title" ><label class="control-label">开票日期</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu"
						name="kpDate" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})" /> </td>
				</tr>
                    <tr >
                        <td class="title"><label class="control-label">合同编号</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="contractId"/></td>
                        <td class="title"><label class="control-label">客户名称</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="clientName"/></td>
                    </tr>
                    <tr>
                        <td class="title"><label class="control-label">合同金额</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="contractFee"/></td>
                        <td class="title"><label class="control-label">开票期数</label></td>
						<td  colspan="1">第</td><td  colspan="1"><input type="text" class="form-control inpu" name="kpQishu"/></td><td colspan="1">期/共</td>
						<td  colspan="2"><input type="text" class="form-control inpu" name="kpSumqishu"/></td><td colspan="1">期</td>
                    </tr>
                    <tr >
                        <td class="title"><label class="control-label">本次开票金额</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="kpThisfee"/></td>
                        <td class="title"><label class="control-label">累计开票金额</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="kpSumfee"/></td>
                    </tr>
				
				<tr >
                    <td class="title"><label class="control-label">发票形式</label></td>
                   <#-- <td  colspan="6"><input type="text" class="form-control inpu" name="fapiaoType"/></td>-->
                    <td colspan="6">
                        <select class="form-control inpu" name="fapiaoType">
                        	<option value="形式发票">形式发票</option>
                            <option value="商业发票">商业发票</option>
                            <option value="增值税发票">增值税发票</option>
                        </select>
                    </td>

				<#--<td class="title" ><label class="control-label">相关客户</label></td>
                <td  colspan="6"><input type="text" class="form-control inpu" name="name"/></td>-->
                    <td class="title"><label class="control-label">开票内容</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu" name="kpContent"/></td>
                </tr>
                    <tr >
                        <td class="title"><label class="control-label">发票号码</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="fapiaoNumber"/></td>


                        <td class="title"><label class="control-label">开票人</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="kper"/>
                            <div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
                                >通讯录</span>
                            </div>
						</td>
                    </tr>
                    <tr >
                        <td class="title"><label class="control-label">审批人</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="shepier"/>
                            <div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
                                >通讯录</span>
                            </div>
						</td>


                        <td class="title"><label class="control-label">签收人</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="qianshouer"/>
							<div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
                                >通讯录</span>
                            </div>
						</td>
                    </tr>
                    <tr >
                        <td class="title"><label class="control-label">签收日期</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="qianshouDate" onclick="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss'})"/></td>


                        <td class="title"><label class="control-label">备注</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="remark"/></td>
                    </tr>

                    <td class="title" ><label class="control-label">相关票据</label></td>
                    <td  colspan="6">
                        <div class="btn btn-default"style="position: relative; overflow: hidden;width: 100%;margin-top: -6px;">
                            <i class="glyphicon glyphicon-open"></i> 上传票据
                            <input type="file" name="filePath" style="opacity: 0; position: absolute;
								 top: 12px; right: 0; " class='inpu' id="files" multiple="multiple">
                        </div>
                    </td>
				</tr>



				<tr >


					<td colspan="14" style="text-align: right;" >
						<input   type="text" value="费用报销" name="val" hidden="hidden"/>
						<input class="btn btn-primary" id="save" type="submit" value="保存"  />
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
<input type="text" class="recive_list" style="display:none;">
<input type="text" class="ject" style="display:none;">
<#include "/common/modalTip.ftl"> 
<script>
	function fileup(){
		var filearr = $('#files').files;
        var formdata = new FormData();//创建一个表单

        for(var i = 0; i < filrarr.length; i++){

            formdata.append("file", filrarr[i]);

        }
        $('#files').value();
        $('#feefiles').value();
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
			if($(this).hasClass("cha")){
				return true;
			}
			// 排除哪些字段是可以为空的，在这里排除
			 if (index == 6||index == 7||index == 8||index == 9||index == 10||index == 11) {
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

<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>
<#include "/common/reciver.ftl">
<#include "/process/subject.ftl">