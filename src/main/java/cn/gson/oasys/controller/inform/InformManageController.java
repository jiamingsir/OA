package cn.gson.oasys.controller.inform;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cn.gson.oasys.controller.mail.ExchangeClient;
import cn.gson.oasys.model.dao.user.DeptFatherDao;
import cn.gson.oasys.model.entity.user.*;
import cn.gson.oasys.services.system.MenuSysService;
import com.alibaba.fastjson.JSONObject;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.mappers.NoticeMapper;
import cn.gson.oasys.model.dao.informdao.InformDao;
import cn.gson.oasys.model.dao.informdao.InformRelationDao;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.notice.NoticeUserRelation;
import cn.gson.oasys.model.entity.notice.NoticesList;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.services.inform.InformRelationService;
import cn.gson.oasys.services.inform.InformService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")
public class InformManageController {
	/*@Value("${file.upload.path}")
	private String path = "upload/";*/

	Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private StatusDao statusDao;

	@Autowired
	private TypeDao typeDao;

	@Autowired
	private InformDao informDao;

	@Autowired
	private InformService informService;

	@Autowired
	private UserDao uDao;

	@Autowired
	private DeptDao deptDao;

	@Autowired
	private DeptFatherDao deptFatherDao;

	@Autowired
	private InformRelationDao informrelationDao;

	@Autowired
	private InformRelationService informrelationservice;

	@Autowired
	private NoticeMapper nm;
	@Autowired
	private MenuSysService mss;

	private ExchangeClient exchangeClient;

	@Value("${attachment.roopath}")
	private String rootpath;



	/**
	 * 通知管理面板
	 * 
	 * @return
	 */
	@RequestMapping("infrommanage")
	public String inform(HttpSession session,@RequestParam(value = "page", defaultValue = "0") int page,@SessionAttribute("userId") Long userId,Model model) {
		Page<NoticesList> page2 = informService.pageThis(page,userId);
		session.setAttribute("aaa","informmanage");
		List<NoticesList> noticeList=page2.getContent();
		List<Map<String, Object>> list=informService.fengZhuang(noticeList);
		model.addAttribute("list", list);
		model.addAttribute("page", page2);
		//设置变量，需要load的url；
		model.addAttribute("url", "infrommanagepaging");
		return "inform/informmanage";
	}
	
	@RequestMapping("forwardother")
	public String forwardOther(@SessionAttribute("userId")Long userId,@RequestParam(value="noticeId")Long noticeId){
		List<User> users=uDao.findByFatherId(userId);
		NoticesList nl=informDao.findOne(noticeId);
		List<NoticeUserRelation> nurs=new  ArrayList<>();
		for (User user : users) {
			nurs.add(new NoticeUserRelation(nl, user, false));
		}
		informrelationservice.saves(nurs);
		return "redirect:/infromlist";
	}

	// demo
//	@RequestMapping("cccc")
//	public @ResponseBody Page<NoticesList> ddd(@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "size", defaultValue = "10") int size,
//			@RequestParam(value = "baseKey", required = false) String baseKey, @SessionAttribute("userId") Long userId,
//			Model model) {
//		Page<NoticesList> page2 = informService.pageThis(page, size, userId,baseKey,null,null,null);
//		List<NoticesList> noticeList=page2.getContent();
//		Long sum=page2.getTotalElements();
//		int size2=page2.getSize();
//		int pages=page2.getTotalPages();
//		int number=page2.getNumber();
//		model.addAttribute("list", noticeList);
//		model.addAttribute("page", page2);
//		return page2;
		
		// List<NoticesList> noticeList=informDao.findByUserId(userId);
		// List<NoticesList>
		// noticeList=informDao.findByUserIdAndTopOrderByModifyTimeDesc(userId,
		// true);
		// List<NoticesList>
		// noticeList2=informDao.findByUserIdAndTopOrderByModifyTimeDesc(userId,
		// false);
		// noticeList.addAll(noticeList2);
		// List<Map<String, Object>> list=informService.fengZhuang(noticeList);
		// model.addAttribute("list",list);
//	}

	/**
	 * 通知管理删除
	 */
	@RequestMapping("infromdelete")
	public String infromDelete(HttpSession session, HttpServletRequest req) {
		Long noticeId = Long.parseLong(req.getParameter("id"));
		Long userId = Long.parseLong(session.getAttribute("userId") + "");
		NoticesList notice = informDao.findOne(noticeId);
		if (userId != notice.getUserId()) {
			System.out.println("权限不匹配，不能删除");
			return "redirect:/notlimit";
		}
		System.out.println(noticeId);
		informService.deleteOne(noticeId);
		return "redirect:/infrommanage";

	}

	/**
	 * 通知列表删除
	 */
	@RequestMapping("informlistdelete")
	public String informListDelete(HttpServletRequest req, HttpSession session) {
		Long userId = Long.parseLong(session.getAttribute("userId") + "");
		Long noticeId = Long.parseLong(req.getParameter("id"));
		NoticeUserRelation relation = informrelationDao.findByUserIdAndNoticeId(uDao.findOne(userId),
				informDao.findOne(noticeId));
		if (Objects.isNull(relation)) {
			System.out.println("权限不匹配，不能删除");
			return "redirect:/notlimit";
		}
		informrelationservice.deleteOne(relation);
		return "forward:/infromlist";
	}

	/**
	 * 通知列表
	 * 
	 * @return
	 */
	@RequestMapping("infromlist")
	public String infromList(HttpSession session, HttpServletRequest req, Model model,
			@RequestParam(value="pageNum",defaultValue="1") int page) {
		Long userId = Long.parseLong(session.getAttribute("userId") + "");
		PageHelper.startPage(page, 10);
		List<Map<String, Object>> list = nm.findMyNotice(userId);
		PageInfo<Map<String, Object>> pageinfo=new PageInfo<Map<String, Object>>(list);
		List<Map<String, Object>> list2=informrelationservice.setList(list);
		for (Map<String, Object> map : list2) {
			System.out.println(map);
		}
		model.addAttribute("url", "informlistpaging");
		model.addAttribute("list", list2);
		model.addAttribute("page", pageinfo);
		System.out.println(pageinfo);
		return "inform/informlist";
	}
	@RequestMapping("informjsonstr")
	public String jsonStr() {
		String json = "{'name': 'helloworlda','array':[{'a':'111','b':'222','c':'333'},{'a':'999'}],'address':'111','people':{'name':'happ','sex':'girl'}}";
		return json;
	}
	/**
	 * 编辑通知界面
	 */
	@RequestMapping("informedit")
	public String infromEdit(HttpServletRequest req, HttpSession session, Model model) {
		session.removeAttribute("noticeId");
		//mss.findById();
		String aaa = (String)session.getAttribute("aaa");
		List<SystemTypeList> typeList = typeDao.findByTypeModel("公告管理");
		List<SystemStatusList> statusList = statusDao.findByStatusModel("公告管理");//inform
		List<Dept> deptList1 = deptDao.findALLInName();
		//部门树对象
		List<DeptTree> deptTrees = new ArrayList<DeptTree>();

		if (StringUtils.isEmpty(req.getParameter("id"))) {

		//查询父级部门
		List<DeptFather> deptFatherList = deptFatherDao.findALLList();
		for (int f = 0; f<deptFatherList.size();f++){
			DeptTree depttree =new DeptTree();
			depttree.setDeptTreeId(String.valueOf(f));
			depttree.setDeptTreeNmae(deptFatherList.get(f).getDeptFatherName());
			depttree.setDeptTreeRemake("1");
			deptTrees.add(depttree);
			//根据父级id查子级部门
			List<Dept> deptListf = deptDao.findALLByFather(deptFatherList.get(f).getDeptFatherId());
			for (int z = 0; z<deptListf.size();z++){
				DeptTree depttreez =new DeptTree();
				depttreez.setDeptTreeId(String.valueOf(f)+String.valueOf(z));
				depttreez.setDeptTreeNmae(deptListf.get(z).getDeptName());
				depttreez.setDeptTreeRemake("0");
				deptTrees.add(depttreez);
			}
		}
		}

		List<Long> deid = new ArrayList<>();
		List<String> dename = new ArrayList<>();


		if (!StringUtils.isEmpty(req.getAttribute("errormess"))) {
			req.setAttribute("errormess", req.getAttribute("errormess"));
		}
		if (!StringUtils.isEmpty(req.getAttribute("success"))) {
			req.setAttribute("success", "数据保存成功");
		}
		req.setAttribute("typeList", typeList);
		req.setAttribute("statusList", statusList);
		req.setAttribute("deptList", deptList1);
		req.setAttribute("deptId", deid);
		req.setAttribute("deName", dename);

		if (!StringUtils.isEmpty(req.getParameter("id"))) {
			Long noticeId = Long.parseLong(req.getParameter("id"));
			NoticesList noticeList = informDao.findOne(noticeId);
			model.addAttribute("noticeList", noticeList);
			model.addAttribute("typeName", typeDao.findOne(noticeList.getTypeId()).getTypeName());
			model.addAttribute("statusName", statusDao.findOne(noticeList.getStatusId()).getStatusName());

			String deptids = noticeList.getDeptIdS();
			String[] deptidArr =deptids.split(",");

			//查询父级部门
			List<DeptFather> deptFatherList = deptFatherDao.findALLList();
			for (int f = 0; f<deptFatherList.size();f++){
				DeptTree depttree =new DeptTree();
				depttree.setDeptTreeId(String.valueOf(f));
				depttree.setDeptTreeNmae(deptFatherList.get(f).getDeptFatherName());
				depttree.setDeptTreeRemake("1");
				depttree.setDeptSelect(false);
				deptTrees.add(depttree);
				//根据父级id查子级部门
				List<Dept> deptListf = deptDao.findALLByFather(deptFatherList.get(f).getDeptFatherId());
				for (int z = 0; z<deptListf.size();z++){
					int flag = 0;
					if (deptidArr.length != 0){
						for (int s = 0; s<deptidArr.length;s++){
							long deptIdSelect = Long.parseLong(deptidArr[s]) ;
							if (deptListf.get(z).getDeptId() == deptIdSelect){
								flag = 1;
							}
						}
					}
					if (flag == 1){
					DeptTree depttreez =new DeptTree();
					depttreez.setDeptTreeId(String.valueOf(f)+String.valueOf(z));
					depttreez.setDeptTreeNmae(deptListf.get(z).getDeptName());
					depttreez.setDeptTreeRemake("0");
					depttree.setDeptSelect(true);
					deptTrees.add(depttreez);
					}else {
						DeptTree depttreez =new DeptTree();
						depttreez.setDeptTreeId(String.valueOf(f)+String.valueOf(z));
						depttreez.setDeptTreeNmae(deptListf.get(z).getDeptName());
						depttreez.setDeptTreeRemake("0");
						depttree.setDeptSelect(false);
						deptTrees.add(depttreez);
					}
				}
			}

			deid.clear();
			for (int i = 0;i < deptList1.size();i++) {
				for (int j = 0;j < deptidArr.length;j++) {
					Long did = Long.parseLong(deptidArr[j]);
					/*String deptname = deptDao.findname(did);
					map1.put(i+"1",deptname);
					deid.add(did);*/
					if (deptList1.get(i).getDeptId() == did){
						deid.add(did);
					}else {
						long KONG =0;
						deid.add(KONG);
					}

				}
			}
			Map<String,String> map1=new HashMap<String ,String>();
			for (int i=0;i<deptidArr.length;i++) {
				Long did = Long.parseLong(deptidArr[i]);
				String deptname = deptDao.findname(did);
				map1.put(i+"1",deptname);
				deid.add(did);

			}
			model.addAttribute("deptId", deid);

			session.setAttribute("noticeId", noticeId);
		}
		req.setAttribute("deptTrees", deptTrees);

		return "inform/informedit";
	}

	/**
	 * 详细通知显示
	 */
	@RequestMapping("informshow")
	public String informShow(HttpServletRequest req, Model model) {
		Long noticeId = Long.parseLong(req.getParameter("id"));
		if (!StringUtils.isEmpty(req.getParameter("read"))) {
			if (("0").equals(req.getParameter("read"))) {
				Long relationId = Long.parseLong(req.getParameter("relationid"));
				NoticeUserRelation relation = informrelationDao.findOne(relationId);
				relation.setRead(true);
				informrelationservice.save(relation);
			}
		}
		NoticesList notice = informDao.findOne(noticeId);
		String src1 = notice.getSrc();
		model.addAttribute("noticesrctype", 0);
		if (src1 != null && !"".equals(src1) && src1.length()!=0){
			String[] src2 = src1.split("\\.");
			String src3 =src2[src2.length-1];
			model.addAttribute("noticesrctype", src3);
		}
		User user = uDao.findOne(notice.getUserId());
		model.addAttribute("notice", notice);
		model.addAttribute("deptName", user.getDept().getDeptName());
		model.addAttribute("realName", user.getRealName());
		model.addAttribute("userName", user.getUserName());
		return "inform/informshow";
	}

	public boolean uploadFile(byte[] file,String filePath,String fileName){
	//默认文件上传成功
		boolean flag = true;
		File targetFile = new File(rootpath,filePath);
	//new一个文件对象实例
		//File targetFile = new File(filePath);
	//如果当前文件目录不存在就自动创建该文件或者目录
		if(!targetFile.exists()){
			targetFile.mkdirs();
		}

		try{
	  //通过文件流实现文件上传
			//FileOutputStream fileOutputStream = new FileOutputStream( filePath+"/"+ fileName);
			FileOutputStream fileOutputStream = new FileOutputStream( rootpath+"/"+filePath+"/"+ fileName);
			fileOutputStream.write(file);
			fileOutputStream.flush();
			fileOutputStream.close();
		}catch(FileNotFoundException e){
			System.out.println("文件不存在异常");
			flag = false;
		}catch(IOException ioException){
			System.out.println("javaIO流异常");
			flag = false;
		}
	return flag;
}

	@RequestMapping("informdownfile")
	//@ResponseBody
	public  boolean downfile (String filepath,@RequestParam(value = "Src")String src,
                                        HttpServletResponse response, HttpServletRequest request)  {
		//默认文件上传成功
		boolean flag = true;
       /* if (src!=null && !src.equals("")) {
            String[] split = src.split(";");
            for (int i = 0; i < split.length; i++) {
                String src1 =split[i];
                HttpServletResponse response =responses;*/

		try {
			filepath =src;
			//new一个文件对象实例
		File targetFile = new File(rootpath,filepath);
		//取文件名
		String filename = targetFile.getName();
		int falg = 0;
            if(!targetFile.exists()){
                System.out.println("获取序列文件出错，请检查！");
            }
		else{
                System.out.println("没有问题");
            }

            if (falg==0){
//yuan

		// 清空response
		response.reset();

		String userAgent = request.getHeader("User-agent");
			/*if(agent.indexOf("Firefox")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName==?UTF-8?B?"+new String(Base64.encodeBase64(filename.getBytes("utf-8")))+"?=");
			}else if(agent.indexOf("Edge")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName="+URLEncoder.encode(filename, "utf-8"));
			}*/

			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				filename = java.net.URLEncoder.encode(filename, "UTF-8");
			} else {
				// 非IE浏览器的处理：
				filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
			}
		// 设置response的Header
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
		response.addHeader("Content-Length", "" + targetFile.length());
		//xian
// 以流的形式下载文件。
                FileInputStream fileInputStream = new FileInputStream(rootpath+filepath);
                InputStream fis = new BufferedInputStream(fileInputStream);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                fis.close();

		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		response.setContentType("application/octet-stream");
		toClient.write(buffer);
		toClient.flush();
		toClient.close();
			flag = true;
            }
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
          /*  }
        }*/

		return  flag;
	}

	@RequestMapping("informdownfiles")
	public boolean downfiles (String filepath, @RequestParam(value = "Src")String src,
										  HttpServletResponse response, HttpServletRequest request)  {
		//默认文件上传成功
		boolean flag = true;
       /* if (src!=null && !src.equals("")) {
            String[] split = src.split(";");
            for (int i = 0; i < split.length; i++) {
                String src1 =split[i];
                HttpServletResponse response =responses;*/

		try {
			filepath =src;
			//new一个文件对象实例
			File targetFile = new File(rootpath,filepath);
			//取文件名
			String filename = targetFile.getName();
			int falg = 0;
			if(!targetFile.exists()){
				System.out.println("获取序列文件出错，请检查！");
			}
			else{
				System.out.println("没有问题");
			}

			if (falg==0){
//yuan

				// 清空response
				response.reset();

				String userAgent = request.getHeader("User-agent");
			/*if(agent.indexOf("Firefox")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName==?UTF-8?B?"+new String(Base64.encodeBase64(filename.getBytes("utf-8")))+"?=");
			}else if(agent.indexOf("Edge")!=-1) {
				response.addHeader("content-Disposition", "attachment;fileName="+URLEncoder.encode(filename, "utf-8"));
			}*/

				// 针对IE或者以IE为内核的浏览器：
				if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
					filename = java.net.URLEncoder.encode(filename, "UTF-8");
				} else {
					// 非IE浏览器的处理：
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
				}
				// 设置response的Header
				response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
				response.addHeader("Content-Length", "" + targetFile.length());
				//xian
// 以流的形式下载文件。
				FileInputStream fileInputStream = new FileInputStream(rootpath+"/"+filepath);
				InputStream fis = new BufferedInputStream(fileInputStream);
				byte[] buffer = new byte[fis.available()];
				fis.read(buffer);
				fis.close();

				OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
				response.setContentType("application/octet-stream");
				toClient.write(buffer);
				toClient.flush();
				toClient.close();
				flag = true;
				fileInputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
		}
          /*  }
        }*/

		return  flag;
	}


	/**
	 * 系统管理表单验证
	 * informcheck
	 * @param req
	 * @param menu
	 * @param br
	 *            后台校验表单数据，不通过则回填数据，显示错误信息；通过则直接执行业务，例如新增、编辑等；
	 * @return
	 */
	@RequestMapping("informcheck")
	public String testMess(HttpServletRequest req, @Valid NoticesList menu, @RequestParam(value="file")MultipartFile[] file, BindingResult br) {// @Valid List<Integer> deptId ,, @RequestParam(value="deptId")List<String> deptIdS
		HttpSession session = req.getSession();
		String path ="";
		for (int i = 0; i < file.length; i++) {
			String filename = file[i].getOriginalFilename();
			if (!filename.equals("")) {
				//String filename = file.getOriginalFilename();
				try {
					byte[] fileb = file[i].getBytes();
					//String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
					String cp11111 = "filePath/";
					Date d = new Date();
					Date d1 = menu.getModifyTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
					String dateNowStr = sdf.format(d1);
					String datetimeNowStr = sdftime.format(d1);
					String pathstr = cp11111 + dateNowStr;

					String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
					String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//图片格式
					String picName = menu.getTitle() + "_" + fname + "_" + datetimeNowStr + "." + type;//图片名称

					uploadFile(fileb, pathstr, picName);
					path = path + pathstr + "/" + picName+";";

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		menu.setSrc(path);



		List<String> deptIdS =new ArrayList<String>();
		Long menuId = null;
		req.setAttribute("menuObj", menu);
		//req.setAttribute("deptObj", deptId);
		Long userId = Long.parseLong(session.getAttribute("userId") + "");
		menu.setUserId(userId);

		// 这里返回ResultVO对象，如果校验通过，ResultEnum.SUCCESS.getCode()返回的值为200；否则就是没有通过；
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		// 校验失败
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			req.setAttribute("errormess", list.get(0).toString());
			// 代码调试阶段，下面是错误的相关信息；
			System.out.println("list错误的实体类信息：" + menu);
			System.out.println("list错误详情:" + list);
			System.out.println("list错误第一条:" + list.get(0));
			System.out.println("啊啊啊错误的信息——：" + list.get(0).toString());
			// 下面的info信息是打印出详细的信息
			log.info("getData:{}", res.getData());
			log.info("getCode:{}", res.getCode());
			log.info("getMsg:{}", res.getMsg());
		}
		// 校验通过，下面写自己的逻辑业务
		else {
			// 判断是否从编辑界面进来的，前面有"session.setAttribute("getId",getId);",在这里获取，并remove掉；
			if (!StringUtils.isEmpty(session.getAttribute("noticeId"))) {
				//修改时
				menuId = (Long) session.getAttribute("noticeId"); // 获取进入编辑界面的menuID值

				NoticesList inform = informDao.findOne(menuId);
				//原推送部门
				String deptIdstr =inform.getDeptIdS();

				menu.setNoticeTime(inform.getNoticeTime());
				menu.setNoticeId(menuId);
				//
				List<DeptTree> deptTree =deptTreeList();
				String deptIds = menu.getDeptIdS();
				String deptIdsStr = "";
				String[] deptidsArr =deptIds.split(",");
//可公共部分
				List<String> deptlistexit =new ArrayList<String>();
				for (int c =0;c<deptidsArr.length;c++){
					String deptid = deptidsArr[c];
					if (deptlistexit.size()!=0){
						int falg =0;
						for (int e =0;e<deptlistexit.size();e++){
							falg =0;
							String deptidd = deptlistexit.get(e);
							if(!deptid.substring(0,deptid.length()-1).equals(deptidd)){
								falg=1;
							}
						}
						if (falg ==1){
							deptlistexit.add(deptid);
						}
					}else {
						deptlistexit.add(deptid);
					}

				}
				for (int i=0;i<deptlistexit.size();i++){
					String deptid = deptlistexit.get(i);
					for (int j = 0; j<deptTree.size();j++){
						if (deptid.equals(deptTree.get(j).getDeptTreeId())){
							String deptname =deptTree.get(j).getDeptTreeNmae();
							String deptfather =deptTree.get(j).getDeptTreeRemake();
							if (deptfather.equals("1")){
								//获取父部门id 根据id得到相关的子部门
								DeptFather deptFather = deptFatherDao.findByDeptFatherName(deptname).get(0);
								Long deptfatherid = deptFather.getDeptFatherId();
								List<Dept> depts = deptDao.findALLByFather(deptfatherid);
								for (int z=0;z<depts.size();z++){
									Long deptid1 =depts.get(z).getDeptId();
									String deptidStr =String.valueOf(deptid1);
									deptIdsStr += deptidStr + ",";
								}
							}else {
								//获取子部门的id
								Long deptid1 =deptDao.findByDeptNames(deptname).get(0).getDeptId();
								String deptidStr =String.valueOf(deptid1);
								deptIdsStr += deptidStr + ",";
							}
						}
					}
				}
				String deptStr = deptIdsStr.substring(0,deptIdsStr.length()-1);
				menu.setDeptIdS(deptStr);
				//
				//
				session.removeAttribute("noticeId");
				//informService.save(menu);
				NoticesList noticeList = informService.save(menu);

				String[] deptiddel = deptIdstr.split(",");


				String deptids= menu.getDeptIdS();
				if (deptids !="" && deptids != null){
					String[] deptidArr =deptids.split(",");
					for (int i=0;i<deptidArr.length;i++){
						int falg = 0;
						for (int j =0;j<deptiddel.length;j++){
							if (deptidArr[i] == deptiddel[j]){
								falg = 1;
							}
						}
						if (falg == 0){
							Long did=Long.parseLong(deptidArr[i]);
							//List<User> userList = uDao.findByDeptIdS(did);
							List<User> userList = uDao.findByDeptId(did);
							for (User user : userList) {
								informrelationservice.save(new NoticeUserRelation(noticeList, user, false));
							}
						}
					}
				}

			} else {
				//新建时
				List<DeptTree> deptTree =deptTreeList();
				String deptIds = menu.getDeptIdS();
				String deptIdsStr = "";
				String[] deptidsArr =deptIds.split(",");
				//List<String> deptidsArr =deptIdS;
				List<String> deptlistexit =new ArrayList<String>();
				for (int c =0;c<deptidsArr.length;c++){
					String deptid = deptidsArr[c];
					if (deptlistexit.size()!=0){
						int falg =0;
						for (int e =0;e<deptlistexit.size();e++){
							falg =0;
							String deptidd = deptlistexit.get(e);
							if(!deptid.substring(0,deptid.length()-1).equals(deptidd)){
								falg=1;
							}
						}
						if (falg ==1){
							deptlistexit.add(deptid);
						}
					}else {
						deptlistexit.add(deptid);
					}


				}
				for (int i=0;i<deptlistexit.size();i++){
					String deptid = deptlistexit.get(i);
					for (int j = 0; j<deptTree.size();j++){
						if (deptid.equals(deptTree.get(j).getDeptTreeId())){
							String deptname =deptTree.get(j).getDeptTreeNmae();
							String deptfather =deptTree.get(j).getDeptTreeRemake();
							if (deptfather.equals("1")){
								//获取父部门id 根据id得到相关的子部门
								DeptFather deptFather = deptFatherDao.findByDeptFatherName(deptname).get(0);
								Long deptfatherid = deptFather.getDeptFatherId();
								List<Dept> depts = deptDao.findALLByFather(deptfatherid);
								for (int z=0;z<depts.size();z++){
									Long deptid1 =depts.get(z).getDeptId();
									String deptidStr =String.valueOf(deptid1);
									deptIdsStr += deptidStr + ",";
								}
							}else {
								//获取子部门的id
								Long deptid1 =deptDao.findByDeptNames(deptname).get(0).getDeptId();
								String deptidStr =String.valueOf(deptid1);
								deptIdsStr += deptidStr + ",";
							}
						}
					}
				}
				String deptStr = deptIdsStr.substring(0,deptIdsStr.length()-1);
				menu.setDeptIdS(deptStr);
				menu.setNoticeTime(new Date());
				menu.setUserId(userId);
				NoticesList noticeList = informService.save(menu);
				//List<User> userList = uDao.findByFatherId(userId);

				String deptids= menu.getDeptIdS();
				if (deptids !="" && deptids != null){
				String[] deptidArr =deptids.split(",");
				for (int i=0;i<deptidArr.length;i++){
					Long did=Long.parseLong(deptidArr[i]);
					//List<User> userList = uDao.findByDeptIdS(did);
					List<User> userList = uDao.findByDeptId(did);
					for (User user : userList) {
						informrelationservice.save(new NoticeUserRelation(noticeList, user, false));
					}
				}
				}

			}
			// 执行业务代码
			System.out.println("此操作是正确的");
			req.setAttribute("success", "后台验证成功");
		}
		System.out.println("是否进入最后的实体类信息：" + menu);
		return "forward:/informedit";
	}



	@RequestMapping("informte")
	@ResponseBody
	public 	List<DeptTreeApi> deptTreeLists (String deptIdS) {
		//[{"id":3,"text":"系统管理","children":[{"id":5,"text":"用户管理","children":[{"id":6,"text":"用户新增","children":null},{"id":7,"text":"用户查询","children":null},{"id":10,"text":"用户删除","children":null},{"id":11,"text":"用户修改","children":null}]},{"id":12,"text":"机构管理","children":[{"id":13,"text":"机构新增","children":null},{"id":14,"text":"机构查询","children":null},{"id":16,"text":"机构删除","children":null},{"id":17,"text":"机构修改","children":null}]}]
		/*这些都完成之后，可以根据
		var pids = $('#rolePer').combotree('getValues');
		来获取选择的值*/
		List<DeptTreeApi> deptTrees = new ArrayList<DeptTreeApi>();
		if (deptIdS.equals("")) {
			//查询父级部门
			List<DeptFather> deptFatherList = deptFatherDao.findALLList();
			for (int f = 0; f < deptFatherList.size(); f++) {
				DeptTreeApi depttree = new DeptTreeApi();
				depttree.setId(String.valueOf(f));
				depttree.setText(deptFatherList.get(f).getDeptFatherName());
				depttree.setChecked(false);

				//根据父级id查子级部门
				List<Dept> deptListf = deptDao.findALLByFather(deptFatherList.get(f).getDeptFatherId());
				List<DeptTreeSonApi> depttreezList = new ArrayList<DeptTreeSonApi>();
				for (int z = 0; z < deptListf.size(); z++) {
					DeptTreeSonApi depttreez = new DeptTreeSonApi();
					depttreez.setId(String.valueOf(f) + String.valueOf(z));
					depttreez.setText(deptListf.get(z).getDeptName());
					depttreez.setChecked(false);
					//depttreez.setChildren(null);
					//depttreez.setState("closed");
					depttreezList.add(depttreez);
				}

				depttree.setChildren(depttreezList);
				depttree.setState("open");
				deptTrees.add(depttree);
			}
		}else {
			//带默认选中
			String[] deptidsArr = deptIdS.split(",");

			//查询父级部门
			List<DeptFather> deptFatherList = deptFatherDao.findALLList();
			for (int f = 0; f < deptFatherList.size(); f++) {
				DeptTreeApi depttree = new DeptTreeApi();
				depttree.setId(String.valueOf(f));
				depttree.setText(deptFatherList.get(f).getDeptFatherName());
				depttree.setChecked(false);

				//根据父级id查子级部门
				List<Dept> deptListf = deptDao.findALLByFather(deptFatherList.get(f).getDeptFatherId());
				List<DeptTreeSonApi> depttreezList = new ArrayList<DeptTreeSonApi>();
				for (int z = 0; z < deptListf.size(); z++) {
					int falgx = 0;
					for (int x =0;x <deptidsArr.length;x++){
						long deptidl = Long.parseLong(deptidsArr[x]);
						if (deptListf.get(z).getDeptId() == deptidl){
							falgx = 1;
						}
					}
					DeptTreeSonApi depttreez = new DeptTreeSonApi();
					if (falgx == 0){
						depttreez.setId(String.valueOf(f) + String.valueOf(z));
						depttreez.setText(deptListf.get(z).getDeptName());
						depttreez.setChecked(false);
						//depttreez.setChildren(null);
						//depttreez.setState("closed");
					}else {
						depttreez.setId(String.valueOf(f) + String.valueOf(z));
						depttreez.setText(deptListf.get(z).getDeptName());
						depttreez.setChecked(true);
						//depttreez.setChildren(null);
						//depttreez.setState("closed");
					}
					depttreezList.add(depttreez);
				}
				depttree.setChildren(depttreezList);
				depttree.setState("open");
				deptTrees.add(depttree);
			}

		}
		return deptTrees;

	}

	public 	List<DeptTree> deptTreeList () {
		List<DeptTree> deptTrees = new ArrayList<DeptTree>();
		//查询父级部门
		List<DeptFather> deptFatherList = deptFatherDao.findALLList();
		for (int f = 0; f < deptFatherList.size(); f++) {
			DeptTree depttree = new DeptTree();
			depttree.setDeptTreeId(String.valueOf(f));
			depttree.setDeptTreeNmae(deptFatherList.get(f).getDeptFatherName());
			depttree.setDeptTreeRemake("1");
			deptTrees.add(depttree);
			//根据父级id查子级部门
			List<Dept> deptListf = deptDao.findALLByFather(deptFatherList.get(f).getDeptFatherId());
			for (int z = 0; z < deptListf.size(); z++) {
				DeptTree depttreez = new DeptTree();
				depttreez.setDeptTreeId(String.valueOf(f) + String.valueOf(z));
				depttreez.setDeptTreeNmae(deptListf.get(z).getDeptName());
				depttreez.setDeptTreeRemake("0");
				deptTrees.add(depttreez);
			}
		}
		return deptTrees;

	}

}
