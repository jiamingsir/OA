package cn.gson.oasys.controller.plan;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import cn.gson.oasys.model.dao.plandao.WorkTableDao;
import cn.gson.oasys.model.dao.roledao.RoleDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.entity.plan.AoaWorkLable;
import cn.gson.oasys.model.entity.role.Role;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.services.task.TaskService;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import cn.gson.oasys.common.StringtoDate;
import cn.gson.oasys.common.formValid.BindingResultVOUtil;
import cn.gson.oasys.common.formValid.MapToList;
import cn.gson.oasys.common.formValid.ResultEnum;
import cn.gson.oasys.common.formValid.ResultVO;
import cn.gson.oasys.controller.attendce.AttendceController;
import cn.gson.oasys.model.dao.notedao.AttachmentDao;
import cn.gson.oasys.model.dao.plandao.PlanDao;
import cn.gson.oasys.model.dao.plandao.Planservice;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.dao.user.UserService;
import cn.gson.oasys.model.entity.note.Attachment;
import cn.gson.oasys.model.entity.plan.Plan;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.file.FileServices;

@Controller
@RequestMapping("/")
public class PlanController {

	@Autowired
	PlanDao planDao;
	@Autowired
	Planservice planservice;
	@Autowired
	TypeDao typeDao;
	@Autowired
	StatusDao statusDao;
	@Autowired
	FileServices fServices;
	@Autowired
	UserDao userDao;
	@Autowired
	UserService userService;
	@Autowired
	RoleDao roleDao;
	@Autowired
	DeptDao deptDao;
	@Autowired
	AttachmentDao attachmentDao;
	@Autowired
	WorkTableDao workTableDao;
	@Autowired
	TaskService taskService;
	@Autowired
	private AttachmentDao AttDao;

	List<Plan> pList;
	List<User> uList;
	Date startDate,endDate;
	String choose2;
	Logger log = LoggerFactory.getLogger(getClass());
	// 格式转化导入
	DefaultConversionService service = new DefaultConversionService();

	@RequestMapping("plandelete")
	public String DSAGec(HttpServletRequest request, HttpSession session) {
		long realuserid = Long.valueOf(session.getAttribute("userId") + "");
		long pid = Long.valueOf(request.getParameter("pid"));
		long userid = planDao.findOne(pid).getUser().getUserId();
		if (userid == realuserid) {
			planservice.delete(pid);
			return "redirect:/planview";
		} else {
			System.out.println("没有权限");
			return "redirect:/notlimit";
		}

	}

	// 计划管理
	@RequestMapping(value="planview", method = RequestMethod.GET)
	public String test(Model model, HttpSession session, @SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "baseKey", required = false) String baseKey,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "time", required = false) String time,
			@RequestParam(value = "icon", required = false) String icon) {
		System.out.println("11"+baseKey);
		User user = userDao.getOne(userId);
		Long deptid =user.getDept().getDeptId();
		sortpaging(model, session, page, baseKey, type, status, time, icon,deptid);
		return "plan/planview";
	}

	

	@RequestMapping(value="planviewtable", method = RequestMethod.GET)
	public String testdd(Model model, HttpSession session, @SessionAttribute("userId") Long userId,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "baseKey", required = false) String baseKey,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "time", required = false) String time,
			@RequestParam(value = "icon", required = false) String icon) {
		System.out.println("222"+baseKey);
		User user = userDao.getOne(userId);
		Long deptid =user.getDept().getDeptId();
		sortpaging(model, session, page, baseKey, type, status, time, icon,deptid);
		return "plan/planviewtable";
	}

	// 计划报表
	@RequestMapping("myplan")
	public String test2(HttpServletRequest request, Model model, HttpSession session, 
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "baseKey", required = false) String baseKey) {
		plantablepaging(request, model, session, page, baseKey);
		return "plan/plantable";
	}

	// 真正的报表
	@RequestMapping("realplantable")
	public String test23(HttpServletRequest request, Model model, HttpSession session, 
			@RequestParam(value="pid",required=false) String pid,
			@RequestParam(value="comment",required=false) String comment,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "baseKey", required = false) String baseKey) {
		System.out.println("pid的"+pid);
		plantablepaging(request, model, session, page, baseKey);
		if(!StringUtils.isEmpty(pid)){
		Plan plan = planDao.findOne(Long.valueOf(pid));
		if(plan.getPlanComment()==null)
			plan.setPlanComment(comment);
		else
		plan.setPlanComment(plan.getPlanComment() + comment);
		planDao.save(plan);}
		return "plan/realplantable";
	}

	//查看详情
	/**
	 * 详细通知显示
	 */
	@RequestMapping("planshow")
	public String informShow(HttpServletRequest req, Model model) {
		Long pid = Long.parseLong(req.getParameter("pid"));
		Plan plan = planDao.findOne(pid);
		String typename = typeDao.findOne(plan.getTypeId()).getTypeName();
		String statusname = statusDao.findOne(plan.getStatusId()).getStatusName();

		/*String src1 = plan.getSrc();
		model.addAttribute("noticesrctype", 0);
		if (src1 != null && !"".equals(src1) && src1.length()!=0){
			String[] src2 = src1.split("\\.");
			String src3 =src2[src2.length-1];
			model.addAttribute("noticesrctype", src3);
		}*/
		//User user = userDao.findOne(notice.getUserId());
		model.addAttribute("plan", plan);
		if(StringUtil.isNotEmpty(plan.getAttachId())){
			List<Attachment> filepath = new ArrayList<>();
			String[] idstr = plan.getAttachId().split(",");
			for (int i = 0; i < idstr.length; i++) {
				if (StringUtil.isNotEmpty(idstr[i])){
					Long attrid = Long.parseLong(idstr[i]);
					Attachment attachment = AttDao.findOne(attrid);
					filepath.add(attachment);
				}
			}
			model.addAttribute("fileatta", filepath);
		}else {
			model.addAttribute("fileatta", null);
		}
		model.addAttribute("typename", typename);
		model.addAttribute("statusname", statusname);
		return "plan/planshow";
	}

	// 我的编辑
	@RequestMapping("planedit")
	public String test3(HttpServletRequest request, @SessionAttribute("userId") Long userId, Model model) {
		User user = userDao.getOne(userId);
		Long deptid =user.getDept().getDeptId();
		long pid = Long.valueOf(request.getParameter("pid"));
		if (!StringUtils.isEmpty(request.getAttribute("errormess"))) {
			request.setAttribute("errormess", request.getAttribute("errormess"));
			request.setAttribute("plan", request.getAttribute("plan2"));
		} else if (!StringUtils.isEmpty(request.getAttribute("success"))) {
			request.setAttribute("success", request.getAttribute("success"));
			request.setAttribute("plan", request.getAttribute("plan2"));
		}
		// 新建
		if (pid == -1) {
			model.addAttribute("plan", null);
			model.addAttribute("pid", pid);

		} else if (pid > 0) {
			Plan plan = planDao.findOne(pid);
			List<String> times = getTimeInterval(new Date());
			String starttime = times.get(0);
			String endtime = times.get(1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date benzhou = null;
			try {
				benzhou = sdf.parse(starttime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (plan.getCreateTime().getTime()<benzhou.getTime() ){
				model.addAttribute("error","非本周计划，不可修改！");
				return "common/proce";
			}
			model.addAttribute("plan", plan);
			model.addAttribute("pid", pid);
		}

		typestatus(model,deptid);
		return "plan/planedit";
	}


	@RequestMapping(value = "plansave", method = RequestMethod.GET)
	public void Datagr() {
	}

	@RequestMapping(value = "plansave", method = RequestMethod.POST)
	public String testMess(@RequestParam("file") MultipartFile[] file, HttpServletRequest req, @Valid Plan plan2,
			BindingResult br) throws IllegalStateException, IOException {
		service.addConverter(new StringtoDate());
		// 格式化开始日期和结束日期
		Date start = service.convert(plan2.getStartTime(), Date.class);
		Date end = service.convert(plan2.getEndTime(), Date.class);
		Attachment att = null;
		Long attid = null;
		Plan plan = null;

		HttpSession session = req.getSession();
		long userid = Long.valueOf(session.getAttribute("userId") + "");
		User user = userDao.findOne(userid);

		// 获取到类型和状态id
		String type = req.getParameter("type");
		String status = req.getParameter("status");
		String lable = plan2.getLabel();
		long typeid = typeDao.findByTypeModelAndTypeName("工作计划", type).getTypeId();
		long statusid = statusDao.findByStatusModelAndStatusName("工作计划", status).getStatusId();
		long lableid = workTableDao.findByLabel(plan2.getLabel()).get(0).getId();
		//long lableid = typeDao.findByTypeModelAndTypeName("工作标签", lable).getTypeId();
		long pid = Long.valueOf(req.getParameter("pid") + "");

		// 这里返回ResultVO对象，如果校验通过，ResultEnum.SUCCESS.getCode()返回的值为200；否则就是没有通过；
		ResultVO res = BindingResultVOUtil.hasErrors(br);
		if (!ResultEnum.SUCCESS.getCode().equals(res.getCode())) {
			List<Object> list = new MapToList<>().mapToList(res.getData());
			req.setAttribute("errormess", list.get(0).toString());
		}
		// 校验通过，下面写自己的逻辑业务
		else {
			if (!StringUtils.isEmpty(session.getAttribute("getId"))) {
				System.out.println("验证通过，进入狗太了");
			}
			// 新建
			if (pid == -1) {
				List<Long> attaids=new ArrayList<>();
				//原单附件
				/*if (!file.isEmpty()) {
					att = (Attachment) fServices.savefile(file, user, null, false);
					attid = att.getAttachmentId();
				} else if (file.isEmpty()) {
					attid = null;
				}*/
				for (int i = 0; i < file.length; i++) {
					Attachment attaid=null;
					MultipartFile filez = file[i];
					if(!StringUtil.isEmpty(filez.getOriginalFilename())){
						attaid = (Attachment) fServices.savefile(filez, user, null, false);
						//attaid=mservice.upload(file, tu);
						//attaid.setModel("plan");
						//AttDao.save(attaid);
						//存id
						attaids.add(attaid.getAttachmentId());
					}

				}
				String attaidstr ="";
				if (attaids.size()>0){
					for (int i = 0; i < attaids.size(); i++) {
						Long atid = attaids.get(i);
						String atidstr = atid.toString();
						attaidstr = attaidstr + atidstr +",";
					}
				}
				/*if (StringUtil.isNotEmpty(attaidstr)){
					plan.setAttachId(attaidstr);
				}*/
				plan = new Plan(typeid, statusid, attaidstr, start, end, new Date(), plan2.getTitle(), plan2.getLabel(),
						plan2.getPlanContent(), plan2.getPlanSummary(), null, user,lableid);
				planDao.save(plan);
			}
			if (pid > 0) {
				List<Long> attaids=new ArrayList<>();
				plan = planDao.findOne(pid);
				if (plan.getAttachId() == null) {
					/*if (!file.isEmpty()) {
						att = (Attachment) fServices.savefile(file, user, null, false);
						attid = att.getAttachmentId();
						plan.setAttachId(attid);
						planDao.save(plan);
					}*/
					for (int i = 0; i < file.length; i++) {
						Attachment attaid=null;
						MultipartFile filez = file[i];
						if(!StringUtil.isEmpty(filez.getOriginalFilename())){
							attaid = (Attachment) fServices.savefile(filez, user, null, false);
							attaids.add(attaid.getAttachmentId());
						}

					}
					String attaidstr ="";
					if (attaids.size()>0){
						for (int i = 0; i < attaids.size(); i++) {
							Long atid = attaids.get(i);
							String atidstr = atid.toString();
							attaidstr = attaidstr + atidstr +",";
						}
					}
				if (StringUtil.isNotEmpty(attaidstr)){
					plan.setAttachId(attaidstr);
				}

					planDao.save(plan);

				}
				if (plan.getAttachId() != null)
					/*for (int i = 0; i < file.length; i++) {
						Attachment attaid=null;
						MultipartFile filez = file[i];
						if(!StringUtil.isEmpty(filez.getOriginalFilename())){
							attaid = (Attachment) fServices.savefile(filez, user, null, false);
							fServices.updateatt(filez, user, null, plan.getAttachId());
							attaids.add(attaid.getAttachmentId());
						}

					}*/
					//fServices.updateatt(filez, user, null, plan.getAttachId());
				planservice.updateplan(typeid, statusid, start, end, plan2.getTitle(), plan2.getLabel(),
						plan2.getPlanContent(), plan2.getPlanSummary(), pid);

			}
			req.setAttribute("success", "后台验证成功");
		}
		req.setAttribute("plan2", plan2);
		return "forward:/planedit";
	}


	private void typestatus(Model model,Long deptid) {
		List<SystemTypeList> type = (List<SystemTypeList>) typeDao.findByTypeModel("工作计划");
		List<SystemStatusList> status = (List<SystemStatusList>) statusDao.findByStatusModel("工作计划");
		//
		List<AoaWorkLable> lable = (List<AoaWorkLable>) workTableDao.findByDeptId(deptid);
		model.addAttribute("typelist", type);
		model.addAttribute("statuslist", status);
		model.addAttribute("lablelist", lable);
	}
	private void sortpaging(Model model, HttpSession session, int page, String baseKey, String type, String status,
			String time, String icon,Long deptid) {
		new AttendceController().setSomething(baseKey, type, status, time, icon, model);
		Long userid = Long.valueOf(session.getAttribute("userId") + "");
		User user = userDao.findOne(userid);
		Page<Plan> page2 = planservice.paging(page, baseKey, userid, type, status, time);
		typestatus(model,deptid);
		model.addAttribute("plist", page2.getContent());
		model.addAttribute("page", page2);
		model.addAttribute("url", "planviewtable");
	}
/*
* 根据当前日期获得所在周的日期区间
* */
	public List<String> getTimeInterval(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<String> times = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = sdf.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = sdf.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		times.add(imptimeBegin+" 00:00:00");
		times.add(imptimeEnd+" 23:59:59");
		return times;
	}

	//计划报表
	private void plantablepaging(HttpServletRequest request, Model model, HttpSession session, int page,
			String baseKey) {
		List<SystemTypeList> type = (List<SystemTypeList>) typeDao.findByTypeModel("工作计划");
		List<SystemStatusList> status = (List<SystemStatusList>) statusDao.findByStatusModel("工作计划");
		List<Plan> plans = new ArrayList<>();
		// 利用set过滤掉重复的plan_user_id 因为set不能重复
		Set<Long> number = new HashSet();
		List<Plan> plan2;
		long typeid = 14;Long choose;
		service.addConverter(new StringtoDate());
		String starttime = request.getParameter("starttime");
		String endtime = request.getParameter("endtime");

		/*
		*
		* */
		/*if(date.getDay()==0)
			dayDistance=6;
		else
			dayDistance=date.getDay()-1;
		monday=changeSomeday(getday,-dayDistance,0)

		//首先初始化周计划日期
		startday=changeSomeday(monday,0,0);
		endday=changeSomeday(monday,7,1);*/

		if (StringUtil.isEmpty(starttime) || StringUtil.isEmpty(endtime)){
			List<String> times = getTimeInterval(new Date());
			starttime = times.get(0);
			endtime = times.get(1);

		}

		System.out.println(starttime+";"+endtime);
		Date start = service.convert(starttime, Date.class);
		Date end = service.convert(endtime, Date.class);
		String choose1=request.getParameter("choose");
		//分页的时候记住
		if(start==null&&end==null&&choose1==null)
			{start=startDate;end=endDate;choose1=choose2;}
		if(start!=null&&end!=null&&choose1!=null)
		{startDate=start;endDate=end;choose2=choose1;}
		// 1是日计划2是周计划3是月计划
		if(choose1==null||choose1.length()==0)
			choose=2l;//choose=1l;
		else
		 choose = Long.valueOf(choose1);
		if (choose == 1) {
			typeid = 13l;
		}
		if (choose == 2) {
			typeid = 14l;
		}
		if (choose == 3) {
			typeid = 15l;
		}
		pList = (List<Plan>) planDao.findAll();
		Long userid=Long.valueOf(session.getAttribute("userId")+"");
		//查看下级人员
		Map<Long,User> userMap = taskService.chakanroot(userid);

		//1.当前用户角色
		Long userroleid = userDao.findByUserId(userid).get(0).getRole().getRoleId();
		//
		Role roles = roleDao.findByroleid(userroleid);
		List<User> uList =new ArrayList<User>();



		//Page<User> uListpage =userService.findmyemployuser2(page, baseKey, userid);
		List<User> uListpage =userService.findmyemployuser3( baseKey, userMap);
		for (Plan plan : pList) {
			number.add(plan.getUser().getUserId());
		}
		System.out.println("有周报的id:"+number);
		// 找到相对应的计划记录
		for (Long num : number) { 
			plan2 = planDao.findlatest(start, end, num, typeid);
			/*if (plan2 != null)
				plans.add(plan2);*/ //原单条记录
			if (plan2.size()>0){
				for (int i = 0; i < plan2.size(); i++) {
					plans.add(plan2.get(i));
				}
			}


		}
		System.out.println("有没有plan"+plans);
		// 将用户名和list绑定在一起
		Map<String,List<Plan>> uMap1 = new HashMap<>();
		List<Plan> umap = new ArrayList<>();
		//for (User userw : uListpage) {
		List<Long> planidlist = new ArrayList<>();
			for (User user : uListpage) {
				if (false) {
					if (plans.size() == 0) {
						umap.add(null);
						uMap1.put(user.getUserName(), umap);
					}
					for (Plan plan : plans) {
						if (user.getUserId() == plan.getUser().getUserId()) {
							umap.add(plan);
							uMap1.put(user.getUserName(), umap);
							//break;
						} else {
							umap.add(null);
							uMap1.put(user.getUserName(), umap);
						}
					}
					System.out.println("list<plan>:" + umap);
				}
				//新写法
				List<Plan> planList = planDao.findlatest(start, end, user.getUserId(), typeid);
				if (planList.size()>0){
					uMap1.put(user.getUserName(),planList);
					for (int i = 0; i < planList.size(); i++) {
						planidlist.add(planList.get(i).getPlanId());
					}
				}else {
					//uMap1.put(user.getUserName(),null);
				}

			}
			if (planidlist.size()<1){
				planidlist.add((long)0);
			}
		Pageable pa=new PageRequest(page, 10);

				Page<Plan> planListpage = planDao.findByIds(planidlist, pa);

			System.out.println("Map:"+uMap1);
		//}

        System.out.println(planListpage.getContent());
    	
        //记住开始时间和结束时间以及选择
        model.addAttribute("starttime",starttime);
        model.addAttribute("endtime", endtime);
    	model.addAttribute("choose", choose1);
        
		model.addAttribute("uMap", uMap1);
		model.addAttribute("type", type);
		model.addAttribute("status", status);
		model.addAttribute("plans", plans);
		model.addAttribute("plist", pList);
		model.addAttribute("ulist", uListpage);
		model.addAttribute("page", planListpage);
		model.addAttribute("url", "realplantable");
	}
	
}
