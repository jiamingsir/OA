package cn.gson.oasys.controller.task;

import java.io.*;
import java.text.ParseException;


import java.text.SimpleDateFormat;
import java.util.*;


import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cn.gson.oasys.controller.ComController;
import cn.gson.oasys.model.dao.filedao.FileListdao;
import cn.gson.oasys.model.dao.filedao.FilePathdao;
import cn.gson.oasys.model.dao.plandao.WorkTableDao;
import cn.gson.oasys.model.entity.file.FileList;
import cn.gson.oasys.model.entity.file.FilePath;
import cn.gson.oasys.model.entity.file.FileTreeApi;
import cn.gson.oasys.model.entity.plan.AoaWorkLable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.util.StringUtil;

import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.taskdao.TaskDao;
import cn.gson.oasys.model.dao.taskdao.TaskloggerDao;
import cn.gson.oasys.model.dao.taskdao.TaskuserDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.PositionDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.task.Tasklist;
import cn.gson.oasys.model.entity.task.Tasklogger;
import cn.gson.oasys.model.entity.task.Taskuser;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.Position;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.task.TaskService;

@Controller
@RequestMapping("/")
public class TaskController {

	@Autowired
	private TaskDao tdao;
	@Autowired
	private StatusDao sdao;
	@Autowired
	private TypeDao tydao;
	@Autowired
	private UserDao udao;
	@Autowired
	private DeptDao ddao;
	@Autowired
	private TaskuserDao tudao;
	@Autowired
	private TaskService tservice;
	@Autowired
	private TaskloggerDao tldao;
	@Autowired
	private PositionDao pdao;
	@Autowired
	WorkTableDao workTableDao;
	@Autowired
	private FilePathdao filePathdao;
	@Autowired
	private FileListdao fileListdao;
	@Autowired
	private ComController comController;
	@Value("${file.root.path}")
	private String rootpath;
	/**
	 * 任务管理表格
	 * 
	 * @return
	 */
	@RequestMapping("taskmanage")
	public String index(Model model,
			@SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {

		// 通过发布人id找用户
		User tu = udao.findOne(userId);
		// 根据发布人id查询任务
		Page<Tasklist> tasklist=tservice.index(page, size, null, tu);
		List<Map<String, Object>> list=tservice.index2(tasklist, tu);
	
		model.addAttribute("tasklist", list);
		model.addAttribute("page", tasklist);
		model.addAttribute("url", "paixu");
		return "task/taskmanage";
	}
	
	/**
	 * 各种排序
	 */
	@RequestMapping("paixu")
	public String paixu(HttpServletRequest request, 
			@SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
	
		// 通过发布人id找用户
		User tu = udao.findOne(userId);
		String val=null;
		if(!StringUtil.isEmpty(request.getParameter("val"))){
			val = request.getParameter("val").trim();
			model.addAttribute("sort", "&val="+val);
		}
		
		Page<Tasklist> tasklist=tservice.index(page, size, val, tu);
		List<Map<String, Object>> list=tservice.index2(tasklist, tu);
		model.addAttribute("tasklist", list);
		model.addAttribute("page", tasklist);
		model.addAttribute("url", "paixu");
		
		return "task/managetable";

	}


	/**
	 * 点击新增任务
	 */
	@RequestMapping("addtask")
	public ModelAndView index2(HttpServletRequest req,@SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pa=new PageRequest(page, size);
		ModelAndView mav = new ModelAndView("task/addtask");
		//查询员工所在的部门
		User user = udao.findByUserId(userId).get(0);
		// 查询类型表
		Iterable<SystemTypeList> typelist = tydao.findAll();
		// 查询状态表
		Iterable<SystemStatusList> statuslist = sdao.findAll();
		// 查询部门下面的员工
		Page<User> pagelist = udao.findByFatherId(userId,pa);
		List<User> emplist=pagelist.getContent();
		// 查询部门表
		Iterable<Dept> deptlist = ddao.findAll();
		// 查职位表
		Iterable<Position> poslist = pdao.findAll();
		//查看工作标签表
		List<AoaWorkLable> lable = (List<AoaWorkLable>) workTableDao.findByDeptId(user.getDept().getDeptId());
		//生成任务编号
        String name = user.getUserName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        List<Tasklist> tasklists = tdao.findByUserid(user);
        String num = String.valueOf(tasklists.size()+1);
        String number = name+date+num;

		//部门树对象
		List<FileTreeApi> fileTrees = new ArrayList<>();

		if (StringUtils.isEmpty(req.getParameter("id"))) {

			//查询父级部门
			/*List<DeptFather> deptFatherList = deptFatherDao.findALLList();
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
			}*/

			/*
			*
			* */

			List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
			for (int f = 0; f < fileFatherList.size(); f++) {
				FileTreeApi filetree = new FileTreeApi();
				filetree.setId(String.valueOf(f));
				filetree.setText(fileFatherList.get(f).getPathName());
				filetree.setChecked(false);

				//根据父级id查子级部门
				List<FilePath> fileListf = filePathdao.findByParentId(fileFatherList.get(f).getParentId());
				List<FileTreeApi> filetreezList = new ArrayList<>();
				for (int z = 0; z < fileListf.size(); z++) {
					FileTreeApi filetreez = new FileTreeApi();
					filetreez.setId(String.valueOf(f) + String.valueOf(z));
					filetreez.setText(fileListf.get(z).getPathName());
					filetreez.setChecked(false);
					//depttreez.setChildren(null);
					//depttreez.setState("closed");
					filetreezList.add(filetreez);
				}

				filetree.setChildren(filetreezList);
				filetree.setState("open");
				fileTrees.add(filetree);
			}

		}
		String attafile="";
		mav.addObject("userid", userId);
		mav.addObject("attafile", attafile);
		mav.addObject("fileTrees", fileTrees);
        mav.addObject("number", number);
		mav.addObject("lablelist", lable);
		mav.addObject("typelist", typelist);
		mav.addObject("statuslist", statuslist);
		mav.addObject("emplist", emplist);
		mav.addObject("deptlist", deptlist);
		mav.addObject("poslist", poslist);
		mav.addObject("page", pagelist);
		mav.addObject("url", "names");
		mav.addObject("qufen", "任务");
		return mav;
	}

	/**
	 * 新增任务保存
	 */
	@RequestMapping("addtasks")
	public String addtask(@SessionAttribute("userId") Long userId,
						  HttpServletRequest request ,@Valid Tasklist list ,BindingResult br) {
		//@RequestParam("filePath")MultipartFile[] filePath,
		User userlist = udao.findOne(userId);
		//Tasklist list = (Tasklist) request.getAttribute("tasklist");
		request.getAttribute("success");
		list.setUsersId(userlist);
		list.setPublishTime(new Date());
		list.setModifyTime(new Date());

		String fileidstr = list.getFileFile();
		String fileStr ="";
		String[] fileArr = fileidstr.split(",");
		for (int i = 0; i < fileArr.length; i++) {
			if (fileArr[i].contains("w")){
				System.out.println("包含文件夹");
			}else {
				fileStr = fileStr + fileArr[i] +",";
			}
		}
		list.setFileFile(fileStr);

		//文件上传
		/*for (int i=0;i<filePath.length;i++){
			String filename = filePath[i].getOriginalFilename();
			if (!filename.equals("")) {
				try {
					//bu.setFileRadio(0);
					byte[] fileb = filePath[i].getBytes();
					//String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
					String cp11111 = "fileTaskPath/";
					Date d = new Date();
					Date d1 = list.getPublishTime();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
					String dateNowStr = sdf.format(d1);
					String datetimeNowStr = sdftime.format(d1);
					String pathstr = cp11111 + dateNowStr;


					String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//格式
					String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
					String picName =  fname + "_" + datetimeNowStr + "." + type;//图片名称

					comController.uploadFilecom(fileb, pathstr, picName);
					list.setFileFile( pathstr + "/" + picName + ";");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
				//if (fileRadio.equals("选中")){
				if (false){
					//bu.setFileRadio(1);

				}else {
					return "common/procefile";
				}
			}
		}*/
		tdao.save(list);
		// 分割任务接收人
		/*StringTokenizer st = new StringTokenizer(list.getReciverlist(), ";");
		while (st.hasMoreElements()) {
			User reciver = udao.findid(st.nextToken());
			Taskuser task = new Taskuser();
			task.setTaskId(list);
			task.setUserId(reciver);
			task.setStatusId(list.getStatusId());
			// 存任务中间表
			tudao.save(task);

		}*/

		return "redirect:/taskmanage";
	}

	/**
	 * 修改任务
	 */
	@RequestMapping("edittasks")
	public ModelAndView index3(HttpServletRequest req, @SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pa=new PageRequest(page, size);
		ModelAndView mav = new ModelAndView("task/edittask");
		// 得到链接中的任务id
		String taskid = req.getParameter("id");
		Long ltaskid = Long.parseLong(taskid);
		// 通过任务id得到相应的任务
		Tasklist task = tdao.findOne(ltaskid);
		// 得到状态id
		Long statusid = task.getStatusId().longValue();
		// 得到类型id
		Long typeid = task.getTypeId();
		// 查看状态表
		SystemStatusList status = sdao.findOne(statusid);
		// 查询类型表
		SystemTypeList type = tydao.findOne(typeid);

		// 查询部门下面的员工
		Page<User> pagelist = udao.findByFatherId(userId,pa);
		List<User> emplist=pagelist.getContent();

		// 查询部门表
		Iterable<Dept> deptlist = ddao.findAll();
		// 查职位表
		Iterable<Position> poslist = pdao.findAll();

		String attafile = task.getFileFile();
		mav.addObject("attafile", attafile);
		mav.addObject("type", type);
		mav.addObject("status", status);
		mav.addObject("emplist", emplist);
		mav.addObject("deptlist", deptlist);
		mav.addObject("poslist", poslist);
		mav.addObject("task", task);
		mav.addObject("page", pagelist);
		mav.addObject("url", "names");
		mav.addObject("qufen", "任务");
		return mav;
	}

	/**
	 * 修改任务确定
	 */
	@RequestMapping("update")
	public String update(Tasklist task, HttpSession session) {
		String userId = session.getAttribute("userId").toString();
		//String userId = ((String) session.getAttribute("userId")).trim();
		Long userid = Long.parseLong(userId);
		User userlist = udao.findOne(userid);
		task.setUsersId(userlist);
		task.setPublishTime(new Date());
		task.setModifyTime(new Date());
		String fileidstr = task.getFileFile();
		String fileStr ="";
		String[] fileArr = fileidstr.split(",");
		for (int i = 0; i < fileArr.length; i++) {
			if (fileArr[i].contains("w")){
				System.out.println("包含文件夹");
			}else {
				fileStr = fileStr + fileArr[i] +",";
			}
		}
		task.setFileFile(fileStr);
		tservice.save(task);

		// 分割任务接收人 还要查找联系人的主键  //初始版本
		/*StringTokenizer st = new StringTokenizer(task.getReciverlist(), ";");
		while (st.hasMoreElements()) {
			User reciver = udao.findid(st.nextToken());
			Long pkid = udao.findpkId(task.getTaskId(), reciver.getUserId());
			Taskuser tasku = new Taskuser();
			tasku.setPkId(pkid);
			tasku.setTaskId(task);
			tasku.setUserId(reciver);
			tasku.setStatusId(task.getStatusId());
			// 存任务中间表
			tudao.save(tasku);

		}*/

		return "redirect:/taskmanage";

	}





	@RequestMapping("fileste")
	@ResponseBody
	public List<FileTreeApi> deptTreeLists (HttpServletRequest req, String fileIdS) {
		HttpSession session = req.getSession();
		Long userId = Long.parseLong(session.getAttribute("userId") + "");
		User user = udao.findOne(userId);
		//[{"id":3,"text":"系统管理","children":[{"id":5,"text":"用户管理","children":[{"id":6,"text":"用户新增","children":null},{"id":7,"text":"用户查询","children":null},{"id":10,"text":"用户删除","children":null},{"id":11,"text":"用户修改","children":null}]},{"id":12,"text":"机构管理","children":[{"id":13,"text":"机构新增","children":null},{"id":14,"text":"机构查询","children":null},{"id":16,"text":"机构删除","children":null},{"id":17,"text":"机构修改","children":null}]}]
		/*这些都完成之后，可以根据
		var pids = $('#rolePer').combotree('getValues');
		来获取选择的值*/
		List<FileTreeApi> fileTrees = new ArrayList<>();
		if (fileIdS.equals("")) {
			//查询父级部门
			FilePath filePath = filePathdao.findByPathName(user.getUserName());
			List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
			for (int f = 0; f < fileFatherList.size(); f++) {
				FileTreeApi filetree = new FileTreeApi();
				filetree.setId(String.valueOf(f));
				filetree.setText(fileFatherList.get(f).getPathName());
				filetree.setChecked(false);
				List<FilePath> fileListfs = filePathdao.findByParentId(fileFatherList.get(f).getId());
				if (fileListfs.size()>0){

				}
				//根据父级id查子级部门
				List<FilePath> fileListf = filePathdao.findByParentId(fileFatherList.get(f).getId());
               /* List<DeptTreeSonApi> depttreezList = new ArrayList<DeptTreeSonApi>();
                for (int z = 0; z < deptListf.size(); z++) {
                    DeptTreeSonApi depttreez = new DeptTreeSonApi();
                    depttreez.setId(String.valueOf(f) + String.valueOf(z));
                    depttreez.setText(deptListf.get(z).getDeptName());
                    depttreez.setChecked(false);
                    //depttreez.setChildren(null);
                    //depttreez.setState("closed");
                    depttreezList.add(depttreez);
                }*/
				List<FileTreeApi> filetreezList = new ArrayList<>();
				for (int z = 0; z < fileListf.size(); z++) {
					FileTreeApi filetreez = new FileTreeApi();
					filetreez.setId(String.valueOf(f) + String.valueOf(z));
					filetreez.setText(fileListf.get(z).getPathName());
					filetreez.setChecked(false);
					List<FilePath> fileListff = filePathdao.findByParentId(fileListf.get(z).getId());
					//depttreez.setChildren(null);
					//depttreez.setState("closed");
					filetreezList.add(filetreez);
				}

				filetree.setChildren(filetreezList);
				filetree.setState("open");
				fileTrees.add(filetree);
			}
		}else {
			//带默认选中
			String[] fileidsArr = fileIdS.split(",");

			//查询父级部门
			//List<DeptFather> deptFatherList = deptFatherDao.findALLList();
			List<FilePath> fileFatherList = filePathdao.findByPathNames(user.getUserName());
			for (int f = 0; f < fileFatherList.size(); f++) {
				FileTreeApi filetree = new FileTreeApi();
				filetree.setId(String.valueOf(f));
				filetree.setText(fileFatherList.get(f).getPathName());
				filetree.setChecked(false);

				//根据父级id查子级部门
				List<FilePath> fileListf = filePathdao.findByParentId(fileFatherList.get(f).getId());
				List<FileTreeApi> flietreezList = new ArrayList<>();
				for (int z = 0; z < fileListf.size(); z++) {
					int falgx = 0;
					for (int x =0;x <fileidsArr.length;x++){
						long fileidl = Long.parseLong(fileidsArr[x]);
						if (fileListf.get(z).getId() == fileidl){
							falgx = 1;
						}
					}
					FileTreeApi filetreez = new FileTreeApi();
					if (falgx == 0){
						filetreez.setId(String.valueOf(f) + String.valueOf(z));
						filetreez.setText(fileListf.get(z).getPathName());
						filetreez.setChecked(false);
						filetreez.setChildren(null);
						filetreez.setState("closed");
					}else {
						filetreez.setId(String.valueOf(f) + String.valueOf(z));
						filetreez.setText(fileListf.get(z).getPathName());
						filetreez.setChecked(true);
						filetreez.setChildren(null);
						filetreez.setState("closed");
					}
					flietreezList.add(filetreez);
				}
				filetree.setChildren(flietreezList);
				filetree.setState("open");
				fileTrees.add(filetree);
			}

		}
		return fileTrees;

	}
	/**
	 * 查看任务
	 */
	@RequestMapping("seetasks")
	public ModelAndView index4(HttpServletRequest req) {
		ModelAndView mav = new ModelAndView("task/seetask");
		// 得到任务的 id
		String taskid = req.getParameter("id");
		Long ltaskid = Long.parseLong(taskid);
		// 通过任务id得到相应的任务
		Tasklist task = tdao.findOne(ltaskid);
		Long statusid = task.getStatusId().longValue();

		// 根据状态id查看状态表
		SystemStatusList status = sdao.findOne(statusid);
		// 查看状态表
		Iterable<SystemStatusList> statuslist = sdao.findAll();
		// 查看发布人
		User user = udao.findOne(task.getUsersId().getUserId());
		// 查看任务日志表
		List<Tasklogger> logger = tldao.findByTaskId(ltaskid);

		//文件路径
		String fileFiles = "";
		String fileNames = "";
		String fileFileids = task.getFileFile();
		String[] fileFileArr = fileFileids.split(",");
		for (int i = 0; i < fileFileArr.length; i++) {
			String fileidstr = fileFileArr[i];
			String idstr = fileidstr.substring(1,fileidstr.length());
			Long pathid = Long.parseLong(idstr);
			FileList fileList =fileListdao.findOne(pathid);
			fileFiles = fileFiles + fileList.getFilePath()+";";
			fileNames = fileNames + fileList.getFileName()+";";

		}

		mav.addObject("task", task);
		mav.addObject("user", user);
		mav.addObject("status", status);
		mav.addObject("loggerlist", logger);
		mav.addObject("statuslist", statuslist);
		mav.addObject("fileFiles", fileFiles);
		mav.addObject("fileNames", fileNames);

		return mav;
	}

	/**
	 * 存反馈日志
	 * 
	 * @return
	 */
	@RequestMapping("tasklogger")
	public String tasklogger(Tasklogger logger, @SessionAttribute("userId") Long userId) {
		User userlist = udao.findOne(userId);
		logger.setCreateTime(new Date());
		logger.setUsername(userlist.getUserName());
		// 存日志
		tldao.save(logger);
		// 修改任务状态
		tservice.updateStatusid(logger.getTaskId().getTaskId(), logger.getLoggerStatusid());
		// 修改任务中间表状态
		tservice.updateUStatusid(logger.getTaskId().getTaskId(), logger.getLoggerStatusid());

		return "redirect:/taskmanage";

	}

	/**
	 * 我的任务
	 */
	@RequestMapping("mytask")
	public String index5(@SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) {
		Pageable pa=new PageRequest(page, size);
		Page<Tasklist> tasklist= tservice.index3(userId, null, page, size);
		
		Page<Tasklist> tasklist2=tdao.findByTickingIsNotNull(pa);
		if(tasklist!=null){
			List<Map<String, Object>> list=tservice.index4(tasklist, userId);
			model.addAttribute("page", tasklist);
			model.addAttribute("tasklist", list);
		}else{
			List<Map<String, Object>> list2=tservice.index4(tasklist2, userId);
			model.addAttribute("page", tasklist2);
			model.addAttribute("tasklist", list2);
		}
		model.addAttribute("url", "mychaxun");
		return "task/mytask";

	}
	
	/**
	 * 在我的任务里面进行查询
	 * 
	 * @throws ParseException
	 */
	@RequestMapping("mychaxun")
	public String select(HttpServletRequest request, @SessionAttribute("userId") Long userId, Model model,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "10") int size) throws ParseException {
	
		String title =null;
		if(!StringUtil.isEmpty(request.getParameter("title"))){
			 title = request.getParameter("title").trim();
		}
		Page<Tasklist> tasklist= tservice.index3(userId, title, page, size);
		List<Map<String, Object>> list=tservice.index4(tasklist, userId);
		model.addAttribute("tasklist", list);
		model.addAttribute("page", tasklist);
		model.addAttribute("url", "mychaxun");
		model.addAttribute("sort", "&title="+title);
		return "task/mytasklist";
	}


	@RequestMapping("myseetasks")
	public ModelAndView myseetask(HttpServletRequest req, @SessionAttribute("userId") Long userId) {

		ModelAndView mav = new ModelAndView("task/myseetask");
		// 得到任务的 id
		String taskid = req.getParameter("id");

		Long ltaskid = Long.parseLong(taskid);
		// 通过任务id得到相应的任务
		Tasklist task = tdao.findOne(ltaskid);

		// 查看状态表
		Iterable<SystemStatusList> statuslist = sdao.findAll();
		// 查询接收人的任务状态
		//Long ustatus = tudao.findByuserIdAndTaskId(userId, ltaskid);
		Long ustatus = (long)tdao.findOne(ltaskid).getStatusId();

		SystemStatusList status = sdao.findOne(ustatus);
		/*System.out.println(status);*/

		// 查看发布人
		User user = udao.findOne(task.getUsersId().getUserId());
		if (user.getUserId() == userId){
			mav.addObject("flag", "yes");
		}else {
			mav.addObject("flag", "no");
		}
		// 查看任务日志表
		List<Tasklogger> logger = tldao.findByTaskId(ltaskid);


		//文件路径
		String fileFiles = "";
		String fileNames = "";
		String fileFileids = task.getFileFile();
		String[] fileFileArr = fileFileids.split(",");
		for (int i = 0; i < fileFileArr.length; i++) {
			String fileidstr = fileFileArr[i];
			String idstr = fileidstr.substring(1,fileidstr.length());
			Long pathid = Long.parseLong(idstr);
			FileList fileList =fileListdao.findOne(pathid);
			fileFiles = fileFiles + fileList.getFilePath()+";";
			fileNames = fileNames + fileList.getFileName()+";";

		}


		mav.addObject("task", task);
		mav.addObject("user", user);
		mav.addObject("status", status);
		mav.addObject("statuslist", statuslist);
		mav.addObject("loggerlist", logger);
		mav.addObject("fileFiles", fileFiles);
		mav.addObject("fileNames", fileNames);
		return mav;

	}

	/**
	 * 从我的任务查看里面修改状态和日志
	 */
	@RequestMapping("uplogger")
	public String updatelo(Tasklogger logger, @SessionAttribute("userId") Long userId) {
		System.out.println(logger.getLoggerStatusid());
		// 获取用户id
		
		// 查找用户
		User user = udao.findOne(userId);
		// 查任务
		Tasklist task = tdao.findOne(logger.getTaskId().getTaskId());
		logger.setCreateTime(new Date());
		logger.setUsername(user.getUserName());
		// 存日志
		tldao.save(logger);

		// 修改任务中间表状态
		Long pkid = udao.findpkId(logger.getTaskId().getTaskId(), userId);
		Taskuser tasku = new Taskuser();
		tasku.setPkId(pkid);
		tasku.setTaskId(task);
		tasku.setUserId(user);
		if (!Objects.isNull(logger.getLoggerStatusid())) {

			tasku.setStatusId(logger.getLoggerStatusid());
		}
		// 存任务中间表
		tudao.save(tasku);
		
		// 修改任务状态
		// 通过任务id查看总状态
		
		List<Integer> statu = tudao.findByTaskId(logger.getTaskId().getTaskId());
		System.out.println(statu);
		// 选出最小的状态id 修改任务表里面的状态
		Integer min = statu.get(0);
		for (Integer integer : statu) {
			if (integer.intValue() < min) {
				min = integer;
			}
		}

		int up = tservice.updateStatusid(logger.getTaskId().getTaskId(), min);
		/*System.out.println(logger.getTaskId().getTaskId() + "aaaa");
		System.out.println(min + "wwww");
		System.out.println(up + "pppppp");*/
		if (up > 0) {
			System.out.println("任务状态修改成功!");
		}

		return "redirect:/mytask";

	}

	/**
	 * 根据发布人这边删除任务和相关联系
	 * @param req
	 * @param
	 * @return
	 */
	@RequestMapping("shanchu")
	public String delete(HttpServletRequest req, @SessionAttribute("userId") Long userId) {
		// 得到任务的 id
		String taskid = req.getParameter("id");
		Long ltaskid = Long.parseLong(taskid);
		
		// 根据任务id找出这条任务
		Tasklist task = tdao.findOne(ltaskid);
		if(task.getUsersId().getUserId().equals(userId)){
			// 删除日志表
			int i=tservice.detelelogger(ltaskid);
			System.out.println(i+"mmmmmmmmmmmm");
			// 分割任务接收人 还要查找联系人的主键并删除接收人中间表
			StringTokenizer st = new StringTokenizer(task.getReciverlist(), ";");
			while (st.hasMoreElements()) {
				User reciver = udao.findid(st.nextToken());
				Long pkid = udao.findpkId(task.getTaskId(), reciver.getUserId());
				int m=tservice.delete(pkid);
				System.out.println(m+"sssssssssss");
				
			}
			// 删除这条任务
			tservice.deteletask(task);
		}else{
			System.out.println("权限不匹配，不能删除");
			return "redirect:/notlimit";

		}
		return "redirect:/taskmanage";

	}

	/**
	 * 接收人这边删除
	 */
	@RequestMapping("myshanchu")
	public String mydelete(HttpServletRequest req, @SessionAttribute("userId") Long userId) {
		// 用户id
		// 得到任务的 id
		String taskid = req.getParameter("id");
		Long ltaskid = Long.parseLong(taskid);
		Long pkid = udao.findpkId(ltaskid, userId);
		tservice.delete(pkid);

		return "redirect:/mytask";

	}

	@RequestMapping("taskdownfiles")
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



}
