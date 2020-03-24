<style>
 .table-r{
 overflow: scroll;
 height:245px;
 }
</style>
<!-- 接收人弹窗-->
				<div class="modal fade " id="myModal" tabindex="-1">

					<!--第二步，窗口声明-->
					<div class="modal-dialog modal-lg ">
						<!--第三步、内容区的声明-->
						<div class="modal-content" style="background: #F9F9F9;">
							<div class="modal-1">

								<div class="modal-2" style="height: 650px;">

									<div class="modal-header" style="margin:10px;margin-top: -20px;">
										<button class="close" data-dismiss="modal" style="display: block;margin: -52px -78px 0 0;;border-radius: 60%;">
											<span class="glyphicon glyphicon-remove-circle"style="color:white;font-size: 30px;" ></span>
										</button>
										<div class="row">
											<div class="col-xs-12" style="height:30px;margin:10px 0px;">

												<h4 style="float:left;">
                      			<a class="btn btn-success glyphicon glyphicon-plus btn-sm"  href="##" onclick="addvalue()"> 新增文件</a>
                      			<a class="btn btn-success glyphicon glyphicon-plus btn-sm"  href="##" onclick="addvalue2()"> 
                      				追加文件</a>
                  			</h4>

												<div class="input-group">
													<input type="text" class="form-control input-sm pull-right cha" placeholder="查找..." />
													<div class="input-group-btn chazhao" style="top:-1px;">
														<a class="btn btn-sm btn-default glyphicon glyphicon-search" style="height: 30px;" href="##"></a>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="modal-body thistable" style="margin:10px;">

									<#--<#include "/common/reciversf.ftl">-->

                                        <div class="table-r" style="height: 520px;">
                                            <table class="table  table-hover  container-fluid ">
                                                <thead>
                                                <tr class="row">
                                                    <th class=" col-xs-1">
                                                        <span class="labels"  style="display:none;"><label><input id="checkedAll" type="checkbox"><i>✓</i></label></span>
                                                    </th>
                                                    <th class=" col-xs-1">
                                                        <span class="labels"  style="display:block;"></span>
                                                    </th>
                                                    <th class=" col-xs-2 b">部门</th>
                                                    <th class=" col-xs-2 b">真实姓名</th>
                                                    <th class="col-xs-2 b">用户名</th>
                                                    <th class="col-xs-2 b">职位</th>
                                                    <th class=" col-xs-2">电话</th>
                                                </tr>
                                                </thead>
                                                <tbody>
											<#list emplist as emp>
                                            <tr class="row">
                                                <td class=" col-xs-1">
                                                    <span class="labels"><label><input name="id" type="checkbox"><i>✓</i></label></span>
                                                </td>
                                                <td class=" col-xs-1">
													<span class="imgs center-block">
														<#if emp.imgPath?? && emp.imgPath!=''  >
														<img style="width: 30px;height: 30px;"
                                                             class="profile-user-img img-responsive img-circle"
                                                             src="/image/${emp.imgPath}" />
														<#else>
														<img style="width: 30px;height: 30px;"
                                                             class="profile-user-img img-responsive img-circle"
                                                             src="images/timg.jpg" alt="images"/>

														</#if>
                                                    </span>
                                                </td>
												<#list deptlist as dept>
													<#if emp.dept.deptId==dept.deptId>
												<td class="col-xs-2">${dept.deptName}</td>
													</#if>
												</#list>
                                                <td class=" col-xs-2 n">${emp.realName}</td>
                                                <td class="col-xs-2 na">${emp.userName}</td>
												<#list poslist as pos>
													<#if emp.position.id==pos.id>
												<td class=" col-xs-2">${pos.name}</td>
													</#if>
												</#list>
                                                <td class=" col-xs-2">${(emp.userTel)!""}</td>

                                            </tr>
											</#list>
                                                </tbody>
                                            </table>
                                        </div>

										<#include "/common/paging.ftl">



									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
				<h2 class="mi" style="display:none;">${(qufen)!''}</h2>
<script>
$(function(){
	
	/* 分页插件按钮的点击事件 */
	
	$('.baseKetsubmit').on('click',function(){
		var baseKey=$('.baseKey').val();
		$('#myModal .thistable').load('namesa?baseKey=baseKey');
	});
	$(".chazhao").click(function() {
		var $qufen=$(".mi").text();
		var con = $(".cha").val();
		$("#myModal .thistable").load("namesa",{title:con,qufen:$qufen});
	});
});
function addvalue(){
	
	var id_array=new Array();
	$('input[name="id"]:checked').each(function(){
	
		var $name=$(this).parents(".col-xs-1").siblings(".n").text();
		console.log($name);
		id_array.push($name);//向数组中添加元素
		var idstr=id_array.join(';');//将数组元素连接起来以构建一个字符串
		$("#recive_list").val(idstr);
		$(".recive_list").val(idstr);
		$(".recive_list").change();
	})
	
	$(".fade").css("display","none");
	
}
/*追加到联系人*/
function addvalue2(){
	
	var id_array=new Array();
	
	var  idstr=null;

	$('input[name="id"]:checked').each(function(){
		var $name=$(this).parents(".col-xs-1").siblings(".n").text();
		id_array.push($name);
		idstr=id_array.join(';');
	})
	
	var org=$("#recive_list").val();
	$("#recive_list").val(org+';'+idstr);	
	$(".fade").css("display","none");
	
}
</script>