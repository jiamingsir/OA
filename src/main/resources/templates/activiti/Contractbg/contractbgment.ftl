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
		<h1 style="font-size: 24px; margin: 0;" class="">合同变更</h1>
	</div>
	<div class="col-md-10 text-right">
		<a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
			disabled="disabled">合同变更</a>
	</div>
</div>
<div class="row" style="padding-top: 15px;">
	<div class="col-md-12">
		
		<div class="bgc-w box">
			<form action="saveContractbg"  enctype="multipart/form-data" method="post" onsubmit="return check();" >
			<div class="box-header">
				<table class="bo table ">
			
				<tr >
					<td colspan="14" class="title"><h2>合同变更单</h2></td>
			
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
					<td  colspan="6"><input type="text" class="form-control inpu" name="processname"/></td>
                    <td class="title" ><label class="control-label">编号</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu" name="contractNumber"/></td>

				</tr>
				<#--<tr >
                    <td class="title" ><label class="control-label">起草人</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu"
                             readonly="readonly" style="background-color:#fff;" name="senderName" value="${username}"/></td>

					<td class="title" ><label class="control-label">部门</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu"
						name="dept" value="${dept}" readonly="readonly"/></td>
				</tr>-->
				
				<tr >
                    <td class="title"><label class="control-label">甲方</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu" name="firstParty"/></td>


                    <td class="title"><label class="control-label">联系方式</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu" name="phone"/></td>
				</tr>
                    <tr >
                        <td class="title"><label class="control-label">乙方</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="secondParty"/>
                            <div class="reciver">
								<span class="label label-success glyphicon glyphicon-plus"
                                >通讯录</span>
                            </div>
						</td>


                        <td class="title"><label class="control-label">联系方式</label></td>
                        <td  colspan="6"><input type="text" class="form-control inpu" name="secondphone"/></td>
                    </tr>
				<tr >

                    <td class="title" ><label class="control-label">相关合同</label></td>
                    <td  colspan="6">
                        <div class="btn btn-default"style="position: relative; overflow: hidden;width: 100%;margin-top: -6px;">
                            <i class="glyphicon glyphicon-open"></i> 上传合同
                            <input type="file" name="filePath" style="opacity: 0; position: absolute;
								 top: 12px; right: 0; " class='inpu' id="files" multiple="multiple">
                        </div>
                    </td>
                    <td class="title"><label class="control-label">原合同编号</label></td>
                    <td  colspan="6"><input type="text" class="form-control inpu" name="orContractid"/></td>
                    <#--<td  colspan="3">
                        <div class="btn btn-default"style="position: relative; overflow: hidden;width: 100%;margin-top: -6px;">

                            <input type="radio" name="fileRadio"   id="filesxuan" value="选中"><span>合同候补</span></input>
                        </div>
                    </td>-->
				</tr>

                    <tr >
                        <td class="title"><label class="control-label">备注</label></td>
                        <td  colspan="14"><textarea class="form-control text" name="remark"></textarea></td>
                    </tr>

				<tr >


					<td colspan="14" style="text-align: right;" >
						<input   type="text" value="合同单" name="val" hidden="hidden"/>
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
<#include "/common/reciver.ftl">
<#include "/process/subject.ftl">
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>