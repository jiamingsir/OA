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
        <h1 style="font-size: 24px; margin: 0;" class="">报销管理</h1>
    </div>
    <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
            disabled="disabled">报销管理</a>
    </div>
</div>
<div class="row" style="padding-top: 15px;">
    <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box">
            <!--盒子头-->

            <form action="expense" enctype="multipart/form-data" method="post" name="rrrr" onsubmit="return check();" >
                <div class="box-header">
                    <input type="hidden" value="${(processname)!""}" name = "processname"/>
                    <input type="hidden" value="${(taskid)!""}" name = "taskid"/>
                    <input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
                    <table class="bo table ">

                        <tr >
                            <td colspan="14" class="title"><h2>费用报销单</h2></td>

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
                            <td class="title" ><label class="control-label">标题</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input"
                                                    readonly="readonly" value="${(expense.feeName)!""}" name="feeName"/></td>

                            <td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
                            <td  colspan="6">
                                <input type = "radio" name = "company" value="新云传媒" disabled="disabled" <#if ('${(expense.company)!}'=='新云传媒')>checked</#if>>新云传媒
                                <input type = "radio" name = "company" value="新云文化" disabled="disabled" <#if ('${(expense.company)!}'=='新云文化')>checked</#if>>新云文化
                                <input type = "radio" name = "company" value="新云投资" disabled="disabled" <#if ('${(expense.company)!}'=='新云投资')>checked</#if>>新云投资
                            </td>

                        </tr>

                        <tr >
                            <td class="title" ><label class="control-label">提单人员</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(expense.senderName)!""}"
                                                    name="senderName" readonly="readonly"/></td>
                            <td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(expense.dept)!""}" name="dept"/></td>

                        </tr>
                        <tr >
                            <td class="title" ><label class="control-label">支付内容</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(expense.feeContent)!""}" readonly="readonly"  name="feeContent"/></td>

                            <td class="title" ><label class="control-label">支付金额</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(expense.feeFee)!""}"  name="feeFee" readonly="readonly"/></td>
                        </tr>
                        <tr >

                            <td class="title" ><label class="control-label">支付方式</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly"  name="feePaytype" value="${(expense.feePaytype)!""}"/></td>

                            <td class="title" ><label class="control-label">费用日期</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly"  name="feeDate" value="${(expense.feeDate)!""}"/></td>
                        </tr>


                        <tr >
                            <td class="title" ><label class="control-label">合同编号</label></td>
                            <td  colspan="6"><input type="text" class="form-control input "  name="contractId" value="${(expense.contractId)!""}" readonly="readonly" /></td>

                            <td class="title" ><label class="control-label">合同金额</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(expense.contractFee)!""}"  name="contractFee" readonly="readonly"/></td>
                        </tr>
                        <tr >
                            <td class="title" ><label class="control-label">本次支付金额</label></td>
                            <td  colspan="6"><input type="text" class="form-control input "  name="contractPaynew" value="${(expense.contractPaynew)!""}" readonly="readonly" /></td>

                            <td class="title" ><label class="control-label">累计已支付金额</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(expense.contractPaysum)!""}"  name="contractPaysum" readonly="readonly"/></td>
                        </tr>
                        <tr >

                            <td class="title" ><label class="control-label">备注</label></td>
                            <td  colspan="14"><input type="text" class="form-control input" value="${(expense.remark)!""}"  name="remark" readonly="readonly"/></td>
                        </tr>
                        <tr >


                            <td class="title" ><label class="control-label">下载附件</label></td>
                        <#--<td  colspan="6"><input type="text" class="form-control input" value="${(leave.replacement)!""}"  name="replacement" readonly="readonly"/></td>-->
                            <#if expense.feeFile??>

                                <td  colspan="14" id="tdas"></td>
                            <#else>
                            <#-- <td  colspan="14"><input type="text" class="form-control input "  name=""
                                                     value="无附件" readonly="readonly" /></td>-->
                            <td  colspan="14"><kbd><a  href="" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"> 附件候补
                                </i></a></kbd></td>
                            </#if>
                            <td class="title" style="display: none"><label class="control-label">票据</label><span style="color:red">*</span></td>
                            <td  colspan="6" style="display: none"><input type="text"  id ="per" class="form-control input" value="${(expense.feeFile)!""}" readonly="readonly"  name="feeFile"/></td>
                        </tr>

                    <#if show == true>
                                <tr >
                                    <td class="title" ><label class="control-label">意见</label></td>
                                    <td  colspan="6"><input type="text" class="form-control input" name="option" /></td>
                                </tr>
                    </#if>
                    </table>
                </div>
                <div class="box-footer">
                    <#if show == true>
                    <input   type="text" value="费用报销" id="rad" name="fileRadio" value="${(expense.fileRadio)!""}" hidden="hidden"/>
                        <input class="btn btn-primary" id="save" type="submit" value="同意" onclick="{return confirm('确定要保存吗？');};"  />
                        <input class="btn btn-default" id="reject" type="button" value="不同意"
                               onclick="aaa()" />
                    </#if>
                    <input class="btn btn-default" id="cancel" type="button" value="返回"
                           onclick="window.history.back();" />
                </div>
                <#include "/activiti/processInfo.ftl"/>
                <#include "/activiti/processImage.ftl"/>

        </div>
    </div>
</div>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>
<script>
    $(function(){
        var hrefs = $('#per').val();
        if (hrefs!=''||hrefs!=null) {
            var hrefArr = hrefs.split(';');

            var a1 = '<kbd><a  href="informdownfile?Src=';
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


    function check(){
        return true;
    }

    function aaa() {
        console.log(aaa);
        document.rrrr.action="reject";
        document.rrrr.submit();
    }


</script>
<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
<script type="text/javascript" src="plugins/My97DatePicker/WdatePicker.js"></script>