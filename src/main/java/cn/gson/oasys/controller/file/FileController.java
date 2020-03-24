package cn.gson.oasys.controller.file;

import cn.gson.oasys.Utils.UserUtils;
import cn.gson.oasys.controller.login.LoginsController;
import cn.gson.oasys.model.dao.filedao.FileListdao;
import cn.gson.oasys.model.dao.filedao.FilePathdao;
import cn.gson.oasys.model.dao.filedao.PcmaclogDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.file.FileList;
import cn.gson.oasys.model.entity.file.FilePath;
import cn.gson.oasys.model.entity.file.JcifsFile;
import cn.gson.oasys.model.entity.file.Pcmaclog;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.file.FileServices;
import jcifs.UniAddress;
import jcifs.smb.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
public class FileController {
	@Autowired
	private FileServices fs;
	@Autowired
	private FilePathdao fpdao;
	@Autowired
	private FileListdao fldao;
	@Autowired
	private UserDao udao;
	@Autowired
	private PcmaclogDao pDao;

	/**
	 * 第一次进入
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping("filemanage")
	public String usermanage(@SessionAttribute("userId")Long userid,Model model) {
		System.out.println(userid);
		User user = udao.findOne(userid);

		FilePath filepath = fpdao.findByPathName(user.getUserName());

		System.out.println(filepath);

		if(filepath == null){
			FilePath filepath1 = new FilePath();
			filepath1.setParentId(1L);
			filepath1.setPathName(user.getUserName());
			filepath1.setPathUserId(user.getUserId());
			filepath = fpdao.save(filepath1);
		}

		model.addAttribute("nowpath", filepath);
		model.addAttribute("paths", fs.findpathByparent(filepath.getId()));
		model.addAttribute("files", fs.findfileBypath(filepath));

		model.addAttribute("userrootpath",filepath);
		model.addAttribute("mcpaths",fs.findpathByparent(filepath.getId()));
		return "file/filemanage";
	}


	/**
	 * 得到SubFile文件
	 * @param username
	 * @param password
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private SmbFile getSubFile( String username, String password,String url) throws Exception{
		System.setProperty("jcifs.smb.client.dfs.disabled", "true");
		UniAddress dc = UniAddress.getByName("192.168.0.254");
		//NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("flairmedia.com.cn", username, password);
		NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication("flairmedia.com.cn", username, password);
		SmbSession.logon(dc, authentication);
		SmbFile remoteFile = new SmbFile(url,  authentication);
		remoteFile.connect(); // 尝试连接

		return remoteFile;
	}


	/**
	 * 得到所有文件
	 * @param url
	 * @return
	 * @throws Exception
	 */
	private List<JcifsFile> getFiles(String url) throws Exception{

		String username = LoginsController.pcmancUsername;
		String password = LoginsController.pcmacPassword;
		SmbFile remoteFile = getSubFile( username , password , url);
		List<JcifsFile> filepathList = new ArrayList<>();
		List<String> paths = new ArrayList<>();
		if(remoteFile.exists()) {
			SmbFile[] files = remoteFile.listFiles();
			Long a = 1L;
			for (SmbFile f : files) {
				JcifsFile jf = new JcifsFile();
				jf.setId(a++);
				jf.setFileName(f.getName());
				jf.setFilePath(f.getPath());
				jf.setSize(f.getContentLengthLong());
				//jf.setSize(f.getContentLength());
				jf.setUploadTime(f.getLastModified());
				String name2 = f.getName();
				String[] name = f.getName().split("\\.");
				String fileShuffix = name[name.length-1];
				jf.setFileShuffix(fileShuffix);
				if(fileShuffix.substring(fileShuffix.length() -1).equals("/"))
				{
					jf.setFileShuffix("/");
				}
				jf.setFileIsshare(0L);
				jf.setFileIstrash(0L);
				paths.add(f.getName());
				filepathList.add(jf);
			}
		}
		return filepathList;
	}



	/**
	 * 第一次进入
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("filemanage2")
	public String usermanage2(@SessionAttribute("userId")Long userid,Model model) throws Exception{

		String url = "smb://192.168.0.254";

		List<JcifsFile> filepathList = getFiles(url);

		model.addAttribute("files", filepathList);			//文件列表

		model.addAttribute("userrootpath",filepathList);
		model.addAttribute("mcpaths",filepathList);


		String filePath = url.substring(6,url.length());

		model.addAttribute("filePath",filePath);				//当前路径
		String[] filePaths = filePath.split("/");
		model.addAttribute("filePaths",filePaths);			//文件当前每级目录
		/*model.addAttribute("istop",false);				//是否可以返回 上层*/


		return "file/filemanage2";
	}

	/**
	 * 进入指定文件夹 的controller方法
	 * 
	 * @param pathid
	 * @param model
	 * @return
	 */
	@RequestMapping("filetest")

	public String text(@SessionAttribute("userId")Long userid,@RequestParam("pathid") Long pathid, Model model) {
		User user = udao.findOne(userid);
		FilePath userrootpath = fpdao.findByPathName(user.getUserName());

		// 查询当前目录
		FilePath filepath = fpdao.findOne(pathid);

		// 查询当前目录的所有父级目录
		List<FilePath> allparentpaths = new ArrayList<>();
		fs.findAllParent(filepath, allparentpaths);
		Collections.reverse(allparentpaths);

		model.addAttribute("allparentpaths", allparentpaths);
		model.addAttribute("nowpath", filepath);
		model.addAttribute("paths", fs.findpathByparent(filepath.getId()));
		model.addAttribute("files", fs.findfileBypath(filepath));
		//复制移动显示 目录
		model.addAttribute("userrootpath",userrootpath);
		model.addAttribute("mcpaths",fs.findpathByparent(userrootpath.getId()));
		return "file/filemanage";
	}

	/**
	 * 进入指定文件夹 的controller方法
	 *
	 * @param fileName
	 * @param model
	 * @return
	 */
	@RequestMapping("filetest2")
	public String text2(@SessionAttribute("userId")Long userid,@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath, Model model) throws Exception{

		List<JcifsFile> fileList = new ArrayList<>();
		String url = "";
		try {
			//返回上一层  fileName一个参数
			if(fileName.equals("") && !filePath.equals("")){
				url = "smb://" + filePath.substring(0,filePath.lastIndexOf("/")) + "/";
			}
			//选择某个上级文件跳转  filePath 包含 fileName
			else if(filePath.indexOf(fileName) != -1) {
				url = "smb://" + filePath.substring(0, filePath.indexOf(fileName)) + fileName + "/";
				//进入下一层
			}else if (!fileName.endsWith("/")){
				url = "smb://" + filePath + "/" + fileName + "/";
			}else {
				url = "smb://" + filePath + "/" + fileName;
			}
				fileList = getFiles(url);

			//如果有异常，则当前页面
		}catch (SmbAuthException e){
			url = "smb://" + filePath + "/";
			fileList = getFiles(url);
			model.addAttribute("errormess", "访问被拒绝");
		} finally {
			/*if(filePath.contains("/")){
				model.addAttribute("istop",true);
			}else{
				model.addAttribute("istop",false);
			}*/
			String filePath2 = url.substring(6,url.length());
			String[] filePaths = filePath2.split("/");
			model.addAttribute("filePaths",filePaths);			//文件当前每级目录

			model.addAttribute("files", fileList);
			model.addAttribute("userrootpath",fileList);
			model.addAttribute("mcpaths",fileList);
			filePath = url.substring(6,url.length()-1);
			model.addAttribute("filePath",filePath);

		}

		return "file/filemanage2";
	}

	/**
	 * 文件上传 controller方法
	 * 
	 * @param file
	 * @param pathid
	 * @param session
	 * @param model
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@RequestMapping("fileupload")
	public String uploadfile(@RequestParam("file") MultipartFile file, @RequestParam("pathid") Long pathid,
			HttpSession session, Model model) throws IllegalStateException, IOException {
		Long userid = Long.parseLong(session.getAttribute("userId") + "");
		User user = udao.findOne(userid);
		FilePath nowpath = fpdao.findOne(pathid);
		// true 表示从文件使用上传
		FileList uploadfile = (FileList) fs.savefile(file, user, nowpath, true);
		System.out.println(uploadfile);
		
		model.addAttribute("pathid", pathid);
		return "forward:/filetest";
	}
	
	/**
	 * 文件分享
	 * @param pathid
	 * @param checkfileids
	 * @param model
	 * @return
	 */
	@RequestMapping("doshare")
	public String doshare(@RequestParam("pathid") Long pathid,
			@RequestParam("checkfileids") List<Long> checkfileids, 
			Model model
			){
		if (!checkfileids.isEmpty()) {
			System.out.println(checkfileids);
			fs.doshare(checkfileids);
		}
		model.addAttribute("pathid", pathid);
		model.addAttribute("message","分享成功");
		return "forward:/filetest";
	}
	
	/**
	 * 删除前台选择的文件以及文件夹
	 * 
	 * @param pathid
	 * @param checkpathids
	 * @param checkfileids
	 * @param model
	 * @return
	 */
	@RequestMapping("deletefile")
	public String deletefile(@SessionAttribute("userId") Long userid,
			@RequestParam("pathid") Long pathid,
			@RequestParam("checkpathids") List<Long> checkpathids,
			@RequestParam("checkfileids") List<Long> checkfileids, Model model) {
		System.out.println(checkfileids);
		System.out.println(checkpathids);

		if (!checkfileids.isEmpty()) {
			// 删除文件
			//fs.deleteFile(checkfileids);
			//文件放入回收战
			fs.trashfile(checkfileids, 1L,userid);
		}
		if (!checkpathids.isEmpty()) {
			// 删除文件夹
			//fs.deletePath(checkpathids);
			fs.trashpath(checkpathids, 1L, true);
			//fs.trashPath(checkpathids);
		}

		model.addAttribute("pathid", pathid);
		return "forward:/filetest";
	}
	
	/**
	 * 重命名
	 * @param name
	 * @param renamefp
	 * @param pathid
	 * @param model
	 * @return
	 */
	
	@RequestMapping("rename")
	public String rename(@RequestParam("name") String name,
			@RequestParam("renamefp") Long renamefp,
			@RequestParam("pathid") Long pathid,
			@RequestParam("isfile") boolean isfile,
			Model model){
		
		//这里调用重命名方法
		fs.rename(name, renamefp, pathid, isfile);
		
		model.addAttribute("pathid", pathid);
		return "forward:/filetest";
		
	}

	/**
	 *
	 * @param fileName
	 * @param filePath
	 * @param fileRename
	 * @param isfile
	 * @param model
	 * @return
	 */

	@RequestMapping("rename2")
	public String rename2(@RequestParam("fileName") String fileName,
						 @RequestParam("filePath") String filePath,
						 @RequestParam("fileRename") String fileRename,
						 @RequestParam("isfile") boolean isfile,
						 Model model){
		try {
		String url = "smb://" + filePath + "/" + fileName;

		String username = LoginsController.pcmancUsername;
		String password = LoginsController.pcmacPassword;

		SmbFile oldFile = getSubFile( username , password , url+fileName);
		SmbFile newFile = getSubFile( username , password , url+fileRename);


		oldFile.renameTo(newFile);





		} catch (Exception e) {
			e.printStackTrace();
		}

		return "forward:/filetest2";

	}




	/**
	 * 移动和复制
	 * @param mctoid
	 * @param model
	 * @return
	 */
	@RequestMapping("mcto")
	public String mcto(@SessionAttribute("userId") Long userid,
			@RequestParam("morc") boolean morc,
			@RequestParam("mctoid") Long mctoid,
			@RequestParam("pathid") Long pathid,
			@RequestParam("mcfileids")List<Long> mcfileids,
			@RequestParam("mcpathids")List<Long> mcpathids,
			Model model){
		System.out.println("--------------------");
		System.out.println("mcfileids"+mcfileids);
		System.out.println("mcpathids"+mcpathids);
	
		if(morc){
			System.out.println("这里是移动！~~");
			fs.moveAndcopy(mcfileids,mcpathids,mctoid,true,userid);
		}else{
			System.out.println("这里是复制！~~");
			fs.moveAndcopy(mcfileids,mcpathids,mctoid,false,userid);
		}
		
		model.addAttribute("pathid", pathid);
		return "forward:/filetest";
	}

	/**
	 * 新建文件夹
	 * 
	 * @param pathid
	 * @param pathname
	 * @param model
	 * @return
	 */
	@RequestMapping("createpath")
	public String createpath(@SessionAttribute("userId") Long userid, @RequestParam("pathid") Long pathid, @RequestParam("pathname") String pathname,
			Model model) {
		System.out.println(pathid + "aaaaaa" + pathname);
		FilePath filepath = fpdao.findOne(pathid);
		String newname = fs.onlyname(pathname, filepath, null, 1, false);

		FilePath newfilepath = new FilePath(pathid, newname);
		newfilepath.setPathUserId(userid);
		
		System.out.println(newname);
		System.out.println(newfilepath);
		fpdao.save(newfilepath);

		model.addAttribute("pathid", pathid);
		return "forward:/filetest";
	}

	/**
	 * 新建文件夹
	 *
	 * @param fileName
	 * @param filePath
	 * @param model
	 * @return
	 */
	@RequestMapping("createpath2")
	public String createpath2(@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath, Model model){
		try{
			//注意使用jcifs-1.3.15.jar的时候 操作远程计算机的时候所有类前面须要增加Smb
			//创建一个远程文件对象
			String url = "smb://" + filePath + "/";
			fileName = fileName + "/";
			String username = LoginsController.pcmancUsername;
			String password = LoginsController.pcmacPassword;
			SmbFile remoteFile = getSubFile( username , password , url+fileName);
			if(!remoteFile.exists()){
				//创建远程文件夹
				remoteFile.mkdir();
			}


		}catch(Exception e){
			e.printStackTrace();
		}


		return "forward:/filetest2";
	}
	
	/**
	 * 图片预览
	 * @param response
	 * @param fileid
	 */
	@RequestMapping("imgshow")
	public void imgshow(HttpServletResponse response, @RequestParam("fileid") Long fileid) {
		FileList filelist = fldao.findOne(fileid);
		File file = fs.getFile(filelist.getFilePath());
		writefile(response, file);
	}

	@RequestMapping("imgshow2")
	public void imgshow2(HttpServletResponse response, @RequestParam("filePath") String filePath , @RequestParam("fileName") String fileName ) {

		try{
			String url = "smb://" + filePath + "/";
			String username = LoginsController.pcmancUsername;
			String password = LoginsController.pcmacPassword;
			SmbFile remoteFile = getSubFile( username , password , url+fileName);
			writefile2(response, remoteFile);

		}catch (Exception e){
			System.out.println(e);
		}




	}


	
	/**
	 * 下载文件
	 * @param response
	 * @param fileid
	 */
	@RequestMapping("downfile")
	public void downFile(HttpServletResponse response, @RequestParam("fileid") Long fileid) {
		try {
			FileList filelist = fldao.findOne(fileid);
			File file = fs.getFile(filelist.getFilePath());
			response.setContentLength(filelist.getSize().intValue());
			response.setContentType(filelist.getContentType());
			response.setHeader("Content-Disposition","attachment;filename=" + new String(filelist.getFileName().getBytes("UTF-8"), "ISO8859-1"));
			writefile(response, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 下载文件！
	 * @param fileName
	 * @param filePath
	 */
	@ResponseBody
	@RequestMapping("downfile2")
	public String downFile2(HttpServletRequest request,HttpSession session ,Model model,@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath ,HttpServletResponse response) {
		try {
			String url = "smb://" + filePath + "/";
			String[] filePaths = filePath.split("/");
			model.addAttribute("filePaths",filePaths);			//文件当前每级目录
			List<JcifsFile> fileList = getFiles(url);
			model.addAttribute("files", fileList);
			model.addAttribute("userrootpath",fileList);
			model.addAttribute("mcpaths",fileList);
			model.addAttribute("filePath",filePath);
			String username = LoginsController.pcmancUsername;
			String password = LoginsController.pcmacPassword;
			SmbFile remoteFile = getSubFile( username , password , url+fileName);

/*

			FileList filelist = fldao.findOne(fileid);
			File file = fs.getFile(filelist.getFilePath());
*/


			response.setContentLength(remoteFile.getContentLength());
			response.setContentType(remoteFile.getContentType());
			response.setHeader("Content-Disposition","attachment;filename=" + new String(remoteFile.getName().getBytes("UTF-8"), "ISO8859-1"));
			writefile2(response, remoteFile);
			Pcmaclog p = new Pcmaclog();
			User user = UserUtils.getUser(session);
			p.setUser(user.getRealName());
			p.setDowndate(new Date());
			p.setDownfile(fileName);
			p.setDownpath(filePath);
			String ip = null;
			ip = request.getHeader("x-forwarded-for");
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
			p.setIp(ip);
			p.setType(1);
			pDao.save(p);

			/*String url = "smb://" + filePath + "/";
			String[] filePaths = filePath.split("/");
			model.addAttribute("filePaths",filePaths);			//文件当前每级目录
			List<JcifsFile> fileList = getFiles(url);
			model.addAttribute("files", fileList);
			model.addAttribute("userrootpath",fileList);
			model.addAttribute("mcpaths",fileList);
			model.addAttribute("filePath",filePath);
			String username = LoginsController.pcmancUsername;
			String password = LoginsController.pcmacPassword;
			SmbFile remoteFile = getSubFile( username , password , url+fileName);
			String localDir = "D:/pcmac";
			File localFile = new File(localDir+File.separator+fileName);
			in = new BufferedInputStream(new SmbFileInputStream(remoteFile));
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			byte []buffer = new byte[1024];
			while((in.read(buffer)) != -1){
				out.write(buffer);
				buffer = new byte[1024];
			}*/
			model.addAttribute("success", "下載成功");
		} catch (Exception e) {
			model.addAttribute("errormess", "下載失敗");
			e.printStackTrace();
		}finally{

		}
		return "file/filemanage2";
	}

	/**
	 * 写文件 方法
	 *
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public String writefile(HttpServletResponse response, File file) {
		ServletOutputStream sos = null;
		FileInputStream aa = null;
		try {
			aa = new FileInputStream(file);
			sos = response.getOutputStream();
			// 读取文件问字节码
			byte[] data = new byte[(int) file.length()];
			IOUtils.readFully(aa, data);
			// 将文件流输出到浏览器
			IOUtils.write(data, sos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sos.close();
				aa.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "filetest2";
	}


	/**
	 * 写文件 方法
	 *
	 * @param response
	 * @param file
	 * @throws IOException
	 */
	public String writefile2(HttpServletResponse response, SmbFile file) {
		ServletOutputStream sos = null;
		SmbFileInputStream aa = null;
		try {
			aa = new SmbFileInputStream(file);
			sos = response.getOutputStream();
			// 读取文件问字节码
			byte[] data = new byte[(int) file.length()];
			IOUtils.readFully(aa, data);
			// 将文件流输出到浏览器
			IOUtils.write(data, sos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				sos.close();
				aa.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return "filetest2";
	}
	

	public String smbGet(String remoteUrl,String localDir,Model model){
		InputStream in = null;
		OutputStream out = null;
		try {

			SmbFile smbFile = new SmbFile(remoteUrl);
			String fileName = smbFile.getName();
			File localFile = new File(localDir+File.separator+fileName);
			in = new BufferedInputStream(new SmbFileInputStream(smbFile));
			out = new BufferedOutputStream(new FileOutputStream(localFile));
			byte []buffer = new byte[1024];
			while((in.read(buffer)) != -1){
				out.write(buffer);
				buffer = new byte[1024];
			}
			model.addAttribute("success", "下載成功");
		} catch (Exception e) {
			model.addAttribute("errormess", "下載失敗");
			e.printStackTrace();
		}finally{
		/*	model.addAttribute("filePaths",filePaths);			//文件当前每级目录*/
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping("smbPut")
	public String smbPut(Model model,@RequestParam("filePath") String filePath, @RequestParam(value="file") MultipartFile multipartFile)throws Exception{
		InputStream in = null;
		OutputStream out = null;
		System.err.println(filePath);
		System.err.println(multipartFile);

		// 获取文件名
		String fileName = multipartFile.getOriginalFilename();
		// 获取文件后缀
		String prefix=fileName.substring(fileName.lastIndexOf("."));
		// 用uuid作为文件名，防止生成的临时文件重复
		File excelFile = File.createTempFile("sunbo", prefix);
		// MultipartFile to File
		multipartFile.transferTo(excelFile);

		String url = "smb://" + filePath + "/";
		String[] filePaths = filePath.split("/");
		model.addAttribute("filePaths",filePaths);			//文件当前每级目录
		List<JcifsFile> fileList = getFiles(url);
		model.addAttribute("files", fileList);
		model.addAttribute("userrootpath",fileList);
		model.addAttribute("mcpaths",fileList);
		model.addAttribute("filePath",filePath);

		try {
			String username = LoginsController.pcmancUsername;
			String password = LoginsController.pcmacPassword;
			/*String url = "smb://" + filePath + "/";*/
			SmbFile remoteFile = getSubFile( username , password , url+fileName);
			String localFilePath = "D:/";

			in = new BufferedInputStream(new FileInputStream(excelFile));
			out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));
			byte []buffer = new byte[1024];
			while((in.read(buffer)) != -1){
				out.write(buffer);
				buffer = new byte[1024];
			}
			model.addAttribute("success", "上传成功");
		} catch (Exception e) {
			model.addAttribute("errormess", "上传失败");
			e.printStackTrace();
		}finally{
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "file/filemanage2";
	}

}
