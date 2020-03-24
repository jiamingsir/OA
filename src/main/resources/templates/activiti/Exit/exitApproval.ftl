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
        <h1 style="font-size: 24px; margin: 0;" class="">离职流程</h1>
    </div>
    <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
            disabled="disabled">离职流程</a>
    </div>
</div>
<div class="row" style="padding-top: 15px;">
    <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box">
            <!--盒子头-->

            <form action="saveExit" enctype="multipart/form-data" method="post" name="rrrr" onsubmit="return check();" >
                <div class="box-header">
                    <input type="hidden" value="${(processname)!""}" name = "processname"/>
                    <input type="hidden" value="${(taskid)!""}" name = "taskid"/>
                    <input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
                    <table class="bo table ">

                        <tr >
                            <td colspan="14" class="title"><h2>离职单</h2></td>

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
                                                    readonly="readonly" value="${(exit.processname)!""}" name="processname"/></td>


                            <td class="title" ><label class="control-label">编号</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" readonly="readonly" class="form-control inpu" value="${(exit.id)!""}" name="id"/></td>

                        </tr>

                        <#--<tr >
                            <td class="title" ><label class="control-label">起草人</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(exit.senderName)!""}"
                                                    name="senderName" readonly="readonly"/></td>
                            <td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(exit.dept)!""}" name="dept"/></td>

                        </tr>-->
                        <tr >
                            <td class="title" ><label class="control-label">离职人员</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(exit.senderName)!""}" readonly="readonly"  name="senderName"/></td>

                            <td class="title" ><label class="control-label">接受人员</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(exit.empName)!""}"  name="empName" readonly="readonly"/></td>
                        </tr>
                        <tr >

                            <td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly"  name="secondParty" value="${(exit.dept)!""}"/></td>

                            <td class="title" ><label class="control-label">职位</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly"  name="position" value="${(exit.position)!""}"/></td>
                        </tr>


                    <#--                            <td class="title" ><label class="control-label">合同编号</label><span style="color:red">*</span></td>
                                                <td  colspan="6"><input type="text" class="form-control input "  name="contractId" value="${(expense.contractId)!""}" readonly="readonly" /></td>

                                                <td class="title" ><label class="control-label">合同金额</label></td>
                                                <td  colspan="6"><input type="text" class="form-control input" value="${(expense.contractFee)!""}"  name="contractFee" readonly="readonly"/></td>
                                            </tr>
                                            <tr >
                                                <td class="title" ><label class="control-label">本次支付金额</label><span style="color:red">*</span></td>
                                                <td  colspan="6"><input type="text" class="form-control input "  name="contractPayNew" value="${(expense.contractPayNew)!""}" readonly="readonly" /></td>

                                                <td class="title" ><label class="control-label">累计已支付金额</label></td>
                                                <td  colspan="6"><input type="text" class="form-control input" value="${(expense.contractPaySum)!""}"  name="contractPaySum" readonly="readonly"/></td>
                                            </tr>-->
                        <tr >

                            <td class="title" ><label class="control-label">入职时间</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(exit.joinDate)!""}"  name="joinDate" readonly="readonly"/></td>
                            <td class="title" ><label class="control-label">联系方式</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(exit.phone)!""}"  name="phone" readonly="readonly"/></td>

                        </tr>
                        <tr>
                            <td class="title"><label class="control-label">备注</label></td>
                            <td  colspan="6"><input type="text" class="form-control inpu" value="${(exit.remark)!""}" readonly="readonly" name="remark"/></td>
                        </tr>
                        <tr >


                            <td class="title" ><label class="control-label">下载附件</label></td>
                            <#if exit.fileFile??>

                                <td  colspan="14" id="tdas"></td>
                            <#else>
                            <#-- <td  colspan="14"><input type="text" class="form-control input "  name=""
                                                     value="无附件" readonly="readonly" /></td>-->
                            <td  colspan="14"><kbd><a  href="" style="color: #fff;font-size:12px;"><i class="glyphicon glyphicon-link"> 附件候补
                                </i></a></kbd></td>
                            </#if>

                            <td class="title" style="display: none"><label class="control-label">票据</label><span style="color:red">*</span></td>
                            <td  colspan="6" style="display: none"><input type="text"  id ="per" class="form-control input" value="${(exit.fileFile)!""}" readonly="readonly"  name="fileFile"/></td>
                        </tr>

                    <#--<#if show == true>
                        <tr >
                            <td class="title" ><label class="control-label">审核人</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="中心老总" name="leader" /></td>
                        </tr>
                    </#if>-->
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

            var a1 = '<kbd><a  href="downfilecom?Src=';
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