<#include "/common/modalTip.ftl"/>
<div class = "menu">
	<ul class="nav nav-pills nav-stacked" style="padding:5px 0 5px 0;">

			<#--<li><a class="open">打开</a></li>-->
			<li><a class="downloadfile2">下载</a></li>
			<#--<li><a class="doshare" href="doshare?pathid=${nowpath.id}&">分享</a></li>-->
			<#--<li><a class="movefile">移动到</a></li>
			<li><a class="copyfile">复制到</a></li>
			<li><a class="menurename">重命名</a></li>
			<li><a onclick="{return confirm('确定删除吗？');};" class="delete">删除</a></li>-->


	</ul>
</div>
<div class="bgc-w box box-primary" style="height: 695px; width: 135%">
	<!--盒子头-->
	<div class="box-header">

			<#--<#if type=="picture">
				<h3 class="box-title" style="font-size: 12px;">图片</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="document">
				<h3 class="box-title" style="font-size: 12px;">文档</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="music">
				<h3 class="box-title" style="font-size: 12px;">音乐</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="video">
				<h3 class="box-title" style="font-size: 12px;">视频</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="yasuo">
				<h3 class="box-title" style="font-size: 12px;">压缩包</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="trash">
				<h3 class="box-title" style="font-size: 12px;">回收站</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="share">
				<h3 class="box-title" style="font-size: 12px;">分享文件</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			<#elseif type=="all">
				<h3 class="box-title" style="font-size: 12px;">全部文件查找</h3>
				<input class="loadfilestype" name="type" type="hidden" value="${type}"/>
			</#if>-->

			<#--<#if nowpath.parentId == 1>
				<h3 class="box-title" style="font-size: 12px;">${nowpath.pathName}</h3>
			<#else>-->
				<h3 class="box-title" style="font-size: 12px;">
				<#--	<#if istop == true>
					<a style="font-size: 12px;" href="filetest2?filePath=${filePath}&fileName=">返回上一层 </a>
					>
						<#else >
						返回上一层>
					</#if>-->
					<#--${filePath}-->
					<#list filePaths as f>
					<a style="font-size: 12px;" href="filetest2?filePath=${filePath}&fileName=${f}">${f}</a>
					>
					</#list>

				</h3>


		<div class="box-tools">
			<div class="input-group" style="width: 150px;">
				<input type="text" class="form-control input-sm findfileandpath"
					placeholder="查找..." />
				<div class="input-group-btn">
					<a class="btn btn-sm btn-default btn-change findfileandpathgo"><span
						class="glyphicon glyphicon-search"></span></a>
				</div>
			</div>
		</div>
	</div>
	<!--盒子身体-->
	<div class="box-body no-padding">
	
		<div style="padding: 5px;">
			<#--<a class="btn btn-sm btn-default allcheck"
				href="javascript:void(0);" title="全选/反选"><span
				class="iconfont icon-xuanze1"></span></a>
			<div class="btn-group">
				<#if isload??>
					<#if istrash??>
						<a onclick="{return confirm('文件将无法复原，确定删除吗？');};" class="btn btn-sm btn-default loaddelete" title="删除">
							<span class="iconfont icon-lajitong"></span>
						</a>
					<#elseif isshare??>
					
					<#else>
						<a onclick="{return confirm('文件将放入回收站，确定删除吗？');};" class="btn btn-sm btn-default loadtrash" title="删除">
							<span class="iconfont icon-lajitong"></span>
						</a> 
					</#if>
				<#else>
					<a onclick="{return confirm('文件将放入回收站，确定删除吗？');};"  class="btn btn-sm btn-default topdelete" &lt;#&ndash;href="deletefile?pathid=${nowpath.id}&checkpathids=&checkfileids="&ndash;&gt; title="删除">
						<span class="iconfont icon-lajitong"></span>
					</a>
					<a class="btn btn-sm btn-default topcreatepath" href="javascript:void(0);" title="新建文件夹">
						<span class="iconfont icon-xinzengwenjian"></span>
					</a>
				</#if>
			</div>-->
			<a class="btn btn-sm btn-default" href="filemanage2" title="刷新"><span
				class="iconfont icon-shuaxin"></span></a>
		</div>
		
		
		<div class="file-box" style="overflow-y: auto;">
			<div class = "boxcontain" style="height: auto;">
			

					<!--新建文件夹操作显示部分  -->
					<div class="file-one creatpath pathtextarea diplaynone" style="width: 144px;">
						<div class="file-img">
							<img src="images/fileimg/Folder.png" />
						</div>
						<div class="file-name" style="text-align: left;">
							<form action="createpath2">
								<input class="creatpathinput" type="text" name="fileName" value="新建文件夹"/>
								<input type="hidden" name="filePath" value="${filePath}"/>
								<button class="btn btn-default">
									<em class="glyphicon glyphicon-ok" style="font-size: 12px;"></em>
								</button>
								<span class="btn btn-default">
									<em class="glyphicon glyphicon-remove cansalcreate" style="font-size: 12px;"></em>
								</span>
							</form>
						</div>
						<span class="file-check"> 
							<span class = "iconfont icon-xuanze" style="height:1.5em;width:1.5em"></span>
						</span>
					</div>

				
				<#if filepathList??>
					<#list filepathList as list>
						<div class="file-one">
							<div class="file-img path">
									<a href="filetest2?fileName=${file.fileName}&filePath=${filePath}">
									<a>
										<img src="images/fileimg/Folder.png" />
									</a>
							</div>
							<div class="file-name path">
							

									<div class="filename">
										<#--<a style="font-size: 12px;">${path.pathName}</a>-->
										<a style="font-size: 12px;">${list.fileName}</a>
									</div>

								
								<div class="pathtextarea rename diplaynone" style="position: absolute;top: 97px;left: -5px;z-index:100;">

										<form action="rename2">
											<#--<input class="creatpathinput" type="text" name="name" value="${path.pathName}"/>-->
											<input class="creatpathinput fileRename" type="text" name="fileRename" value="${list.fileName}"/>
											<input type="hidden" name="filePath" value="${filePath}"/>
											<input type="hidden" name="fileName" value="${list.fileName}"/>
											<input type="hidden" name="isfile" value="false"/>
											<button class="btn btn-default">
												<em class="glyphicon glyphicon-ok" style="font-size: 12px;"></em>
											</button>
											<span class="btn btn-default">
												<em class="glyphicon glyphicon-remove cansalcreate" style="font-size: 12px;"></em>
											</span>
										</form>
								</div>
							</div>
						<#--	<input type="hidden" class = "pathmessage" value="${path.id}">-->
							<span class="file-check"> 
								<span class = "iconfont icon-xuanze" style="height:1.5em;width:1.5em"></span>
							</span>
						</div>
					</#list>
				</#if>
				
				<#list files as file>
					<div class="file-one">
						<#if file.fileShuffix == "zip" || file.fileShuffix =="rar" || file.fileShuffix =="7-Zip">
								<div class="file-img">
									<img src="images/fileimg/ZIP.png" />
								</div>
							<#elseif file.fileShuffix == "mp4" || file.fileShuffix == "rmvb" || file.fileShuffix == "avi">
								<div class="file-img">
									<img src="images/fileimg/Video.png" />
								</div>
							<#elseif file.fileShuffix == "pdf">
								<div class="file-img">
									<img src="images/fileimg/PDF.png" />
								</div>
							<#elseif file.fileShuffix == "mp3" || file.fileShuffix =="aiff" >
								<div class="file-img">
									<img src="images/fileimg/Music.png" />
								</div>
							<#elseif file.fileShuffix == "jpeg" || file.fileShuffix == "png" || file.fileShuffix == "gif" || file.fileShuffix == "jpg" >
								<div class="file-img">
									<img src="imgshow2?filePath=${filePath}&fileName=${file.fileName}" style="height:71px;width:56px;"/>
								</div>
						<#elseif file.fileShuffix == "xlsx" ||file.fileShuffix == "csv" ||file.fileShuffix == "xls"  >
							<div class="file-img">
								<img src="images/fileimg/excel.png" />
							</div>
						<#elseif file.fileShuffix == "ppt" ||file.fileShuffix == "pptx"  >
							<div class="file-img">
								<img src="images/fileimg/ppt.png" />
							</div>
						<#elseif file.fileShuffix == "doc" ||file.fileShuffix == "docx"  >
							<div class="file-img">
								<img src="images/fileimg/word.png" />
							</div>
						<#elseif file.fileShuffix == "txt">
							<div class="file-img">
								<img src="images/fileimg/txt.png" />
							</div>
						<#elseif file.fileShuffix == "/" >
							<div class="file-img">
								<a href="filetest2?fileName=${file.fileName}&filePath=${filePath}">
									<img src="images/fileimg/Folder.png" style="height:71px;width:56px;"/>
								</a>
							</div>
							<#else>
								<div class="file-img">

									<img src="images/fileimg/other.png" />
								</div>
						</#if>                                   
						<div class="file-name">
							<div class="filename">
								<#if file.fileShuffix == '/'>
								<a href = "filetest2?fileName=${file.fileName}&filePath=${filePath}" >${file.fileName}</a>
									<#else>
									${file.fileName}
								</#if>
								<input type="hidden" class="nowuserid" id="${(userid)!''}"/>
							</div>
							<div class="pathtextarea rename diplaynone" style="position: absolute;top: 97px;left: -5px;z-index:100;">
								<#--<#if isload??>-->
									<input class="creatpathinput" type="text" name="name" value="${file.fileName}"/>
									<#--<input class="renamefp" type="hidden" name="renamefp" value="${file.fileId}"/>-->
									<input class="pathid" type="hidden" name="pathid" value=""/>
									<input class="isfile" type="hidden" name="isfile" value="true"/>
									<button class="btn btn-default okfilerename2">
										<em class="glyphicon glyphicon-ok" style="font-size: 12px;"></em>
									</button>
									<span class="btn btn-default">
										<em class="glyphicon glyphicon-remove cansalcreate" style="font-size: 12px;"></em>
									</span>
							</div>
						</div>
						<input type="hidden" class = "fileName" value="${file.fileName}">
						<input type="hidden" class = "filePath" value="${filePath}">
						<span class="file-check"> 
							<span class = "iconfont icon-xuanze" style="height:1.5em;width:1.5em"></span>
						</span>
					</div>
				</#list>
			</div>
		</div>
	</div>

</div>