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
<script>
    function check(){
        return true;
    }

    function aaa() {
        console.log(aaa);
        document.rrrr.action="reject";
        document.rrrr.submit();
    }
    function reject(){

    }


</script>


<div class="row" style="padding-top: 10px;">
    <div class="col-md-2">
        <h1 style="font-size: 24px; margin: 0;" class="">流程管理</h1>
    </div>
    <div class="col-md-10 text-right">
        <a href="##"><span class="glyphicon glyphicon-home"></span> 首页</a> > <a
            disabled="disabled">补卡申请</a>
    </div>
</div>
<div class="row" style="padding-top: 15px;">
    <div class="col-md-12">
        <!--id="container"-->
        <div class="bgc-w box">
            <!--盒子头-->

            <form action="bukashenqing" enctype="multipart/form-data" method="post" name="rrrr" onsubmit="return check();" >
                <div class="box-header">
                    <input type="hidden" value="${(processname)!""}" name = "processname"/>
                    <input type="hidden" value="${(taskid)!""}" name = "taskid"/>
                    <input type="hidden" value="${(processInstanceId)!""}" name = "processInstanceId"/>
                    <input type="hidden" name="senderid" value="${(buka.senderid)!''}"/>

                    <table class="bo table ">

                        <tr >
                            <td colspan="14" class="title"><h2>补卡申请单</h2></td>

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
                            <td class="title" ><label class="control-label">申请人</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input"
                                                    readonly="readonly" value="${(buka.sendername)!""}" name="name"/></td>
                            <td class="title" ><label class="control-label">填表时间</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly"  name="nowdate" value="${(buka.nowdate)!""}"/></td>
                        </tr>


                        <tr >
                            <#-- <td class="title" style="display: none"><input type="hidden" style="display: none" name="senderid" value="${(buka.senderid)!''}"/></td>
                             <td class="title" style="display: none"><input type="hidden" style="display: none" name="id" value="${(buka.id)!''}"/></td>
                             <td class="title" style="display: none"><input type="hidden" style="display: none" name="senderid" value="${(buka.senderid)!''}"/></td>
                             <td class="title" style="display: none"><input type="hidden" style="display: none" name="processid" value="${(buka.processid)!''}"/></td>-->



                            <td class="title" ><label class="control-label">部门</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" readonly="readonly" value="${(buka.dept)!""}" name="dept"/></td>
                            <td class="title" ><label class="control-label">公司</label><span style="color:red">*</span></td>
                            <td  colspan="6">
                                <input type = "radio" name = "company" value="新云传媒" disabled="disabled" <#if ('${(buka.company)!}'=='新云传媒')>checked</#if>>新云传媒
                                <input type = "radio" name = "company" value="新云文化" disabled="disabled" <#if ('${(buka.company)!}'=='新云文化')>checked</#if>>新云文化
                                <input type = "radio" name = "company" value="新云投资" disabled="disabled" <#if ('${(buka.company)!}'=='新云投资')>checked</#if>>新云投资
                            </td>
                        </tr>

                        <td class="title" ><label class="control-label">未打卡开始时间</label><span style="color:red">*</span></td>
                        <td  colspan="6"><input type="text" class="form-control input "  name="nodatebegin" value="${(buka.nodatebegin)!""}" readonly="readonly" /></td>
                            <td class="title" ><label class="control-label">未打卡结束时间</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input "  name="nodateend" value="${(buka.nodateend)!""}" readonly="readonly" /></td>
                        </tr>

                        <tr >
                        <tr >
                            <td class="title" ><label class="control-label">未打卡原因</label><span style="color:red">*</span></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(buka.reason)!""}" readonly="readonly"  name="reason"/></td>

                            <td class="title" ><label class="control-label">备注</label></td>
                            <td  colspan="6"><input type="text" class="form-control input" value="${(buka.remark)!""}"  name="remark" readonly="readonly"/></td>
                        </tr>
                        <tr >


                            <td class="title" ><label class="control-label">下载附件</label></td>
                            <#if buka.fileFile??>
                                <td class="title" style="display: none"><label class="control-label">附件下载</label><span style="color:red">*</span></td>
                                <td  colspan="6" style="display: none"><input type="text"  id ="per" class="form-control input" value="${(buka.fileFile)!""}" readonly="readonly"  name="fileFile"/></td>
                                <td  colspan="6" id="tdas"></td>


                            </#if>


                        </tr>
                        <#if show == true>
                            <tr >
                                <td class="title" ><label class="control-label">意见</label></td>
                                <td ><input type="text" class="form-control input" name="option" /></td>
                            </tr>
                        </#if>
                    </table>

                </div>
                <div class="box-footer">
                    <#if show == true>
                        <input class="btn btn-primary" id="save" type="submit" value="同意" onclick="{return confirm('确定要保存吗？');};"  />
                        <input class="btn btn-default" id="reject" type="button" value="不同意" onclick="aaa()"/>
                    </#if>
                    <input class="btn btn-default" id="cancel" type="button" value="返回"
                           onclick="window.history.back();" />
                </div>
                <#include "/activiti/processInfo.ftl"/>
                <#include "/activiti/processImage.ftl"/>

        </div>
    </div>
</div>

<#include "/common/modalTip.ftl"/>
<script type="text/javascript" src="js/common/data.js"></script>
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

</script>