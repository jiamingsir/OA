package cn.gson.oasys.controller.activiti;

import cn.gson.oasys.Utils.ActivitiUtils;
import cn.gson.oasys.Utils.DateUtils;
import cn.gson.oasys.Utils.SpringUtils;
import cn.gson.oasys.Utils.UserUtils;
import cn.gson.oasys.activiti.entity.*;
import cn.gson.oasys.controller.ComController;
import cn.gson.oasys.model.dao.activitidao.*;
import cn.gson.oasys.activiti.entity.Buka;
import cn.gson.oasys.activiti.entity.Expense;
import cn.gson.oasys.activiti.entity.Kaipiao;
import cn.gson.oasys.activiti.entity.Leave;
import cn.gson.oasys.model.dao.activitidao.BukaDao;
import cn.gson.oasys.model.dao.activitidao.ExpenseDao;
import cn.gson.oasys.model.dao.activitidao.KaipiaoDao;
import cn.gson.oasys.model.dao.atdrecorddao.AtdrecordDao;
import cn.gson.oasys.model.dao.system.StatusDao;
import cn.gson.oasys.model.dao.user.DeptDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.process.ProcessList;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.User;
import org.activiti.engine.*;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.activiti.engine.task.TaskInfoQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/")
public class ActivitiController {

    public static ActivitiController activitiController;
    /**
     * 初始化当前类
     */
    @PostConstruct
    public void init() {
        activitiController = this;
    }

    @Autowired
    UserDao uDao;

    @Autowired
    BukaDao bukaDao;

    @Autowired
    LeaveDao leaveDao;

    @Autowired
    CaigouDao caigouDao;
	@Autowired
    ExpenseDao expenseDao;
    @Autowired
    KaipiaoDao kaipiaoDao;
    @Autowired
    ContractcqDao contractcqDao;
    @Autowired
    ContractbgDao contractbgDao;
    @Autowired
    ContractxqDao contractxqDao;
    @Autowired
    EntryDao entryDao;
    @Autowired
    ExitDao exitDao;
    @Autowired
    RegularaDao regularaDao;
    @Autowired
    RegularreplyDao regularreplyDao;


    @Autowired
    private StatusDao sdao;

    @Autowired
    private AtdrecordDao atdDao;

    @Autowired
    private GongzhangshenqingDao gzDao;
    @Autowired
    private JiuDao jiuDao;
    @Autowired
    private LipinDao lipinDao;
    @Autowired
    private GongzhangwaijieDao gongzhangwaijieDao;
    @Autowired
    private InterviewDao interviewDao;
    @Autowired
    private ChangepositionDao changepositionDao;
    @Autowired
    private ContractcharterDao contractcharterDao;
    @Autowired
    private RecruitDao recruitDao;


    @Autowired
    private ComController comController;

    @Autowired
    private DeptDao dDao;




    ActivitiUtils aUtils = new ActivitiUtils();
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @RequestMapping("buka")
    public String buka(@SessionAttribute("userId") Long userid,
                       HttpServletResponse response,
                       Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        Buka buka = new Buka();
        String processname = "补卡申请";
        model.addAttribute("buka",buka);
        model.addAttribute("user",user);
        model.addAttribute("leaderName" , leaderName);
        model.addAttribute("processname",processname);

        return "activiti/buka/buka";
    }


    /**
     * 补卡申请流程
     * @param session
     * @param buka
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("bukashenqing")
    public String bukashenqing( HttpSession session,
                                Buka buka,
                                @RequestParam(value = "processname") String processname ,
                                @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                @RequestParam(value = "option",defaultValue="") String option ,
                                @RequestParam("filePath")MultipartFile[] filePath,
                                @RequestParam(value = "taskid")String taskid,
                                HttpServletRequest req) {
        //taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //上传附件
        if(filePath != null ){
            for (int i=0;i<filePath.length;i++) {
                String filename = filePath[i].getOriginalFilename();
                if (!filename.equals("")) {
                    try {
                        //bu.setFileRadio(0);
                        byte[] fileb = filePath[i].getBytes();
                        //String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
                        String cp11111 = "bukaPath/";
                        Date d = new Date();
                        Date d1 = buka.getNowdate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
                        String dateNowStr = sdf.format(d1);
                        String datetimeNowStr = sdftime.format(d1);
                        String pathstr = cp11111 + dateNowStr;


                        String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//格式
                        String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
                        String picName = fname + "_" + datetimeNowStr + "." + type;//图片名称

                        comController.uploadFilecom(fileb, pathstr, picName);
                        buka.setFileFile(pathstr + "/" + picName + ";");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //首次申请，taskid为null
        if(taskid == null || taskid.equals("")) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            buka.setProcessid(Long.parseLong(pi.getId()));
            buka.setSenderid(user.getUserId());
            buka.setSendername(user.getRealName());
            bukaDao.save(buka);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            try {

                processEngine.getTaskService().addComment(taskid,processInstanceId,option);
                //执行通过流程
                processEngine.getTaskService()//与正在执行的任务管理相关的Service
                        .complete(taskid,variables);


                //如果有End，则判断是结束，写业务逻辑
                List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
                HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);
                boolean passflag = false;
                for(HistoricActivityInstance hai : historicActivityInstanceList ){
                    if(hai.getActivityName().equals("End")){
                        passflag = true;
                        break;
                    }
                }

                if(passflag == true) {      //已完成

                    if(buka == null){
                        buka = activitiController.bukaDao.findbyProcessid(Long.parseLong(processInstanceId));
                    }

                    Date beginDate = buka.getNodatebegin();
                    Date endDate = buka.getNodateend();
                    Long senderid = buka.getSenderid();
                     User sender = activitiController.uDao.findByUserId(senderid).get(0);
                    String[] ids = sender.getCardId().split(",");




                    //将时间参数转换为指定格式的时间参数  上午指定时间9:00 下午指定时间17:00
                    int day = Integer.parseInt(DateUtils.getTwoDay(DateUtils.formatDate(endDate,null),DateUtils.formatDate(beginDate,null)));
                    String beginDateStr = DateUtils.formatDate(beginDate,"yyyy-MM-dd");
                    String endDateStr = DateUtils.formatDate(endDate,"yyyy-MM-dd");
                    String beginTimeStr = DateUtils.formatDate(beginDate,"HH:mm:ss");
                    String endTimeStr = DateUtils.formatDate(endDate,"HH:mm:ss");
                    if(DateUtils.compareTime(beginTimeStr,"00:00", "12:00")){
                        beginTimeStr = "09:00";
                    }else{
                        beginTimeStr = "17:00";
                    }
                    beginDate = DateUtils.parseDate(beginDateStr + " " + beginTimeStr);
                    if(DateUtils.compareTime(endTimeStr,"00:00", "12:00")){
                        endTimeStr = "09:00";
                    }else{
                        endTimeStr = "17:00";
                    }
                    endDate = DateUtils.parseDate(endDateStr + " " + endTimeStr);


                    int times = 0;
                    //同一天，单次
                    if(day == 0 && endTimeStr.equals(beginTimeStr)){
                        times = 1 ;
                    //同一天，两次
                    }else if(day == 0 && !endTimeStr.equals(beginTimeStr)){
                        times = 2;
                    //多天+1次
                    }else if(day > 0 && endTimeStr.equals(beginTimeStr)){
                        times = day * 2 + 1;
                    //多天 + 2次
                    }else if(day > 0 && !endTimeStr.equals(beginTimeStr)){
                        times = day*2 + 2;
                    }
                    String[] timeArr = {"09:00","17:00"};
                    int addDay = 0;
                    int addDay2 = 1;
                    for(int i = 0 ; i < times ; i++){
                        //设置考勤中间表对象
                        Atdrecord atdrecord = new Atdrecord();
                        atdrecord.setFlag(1);           //设置  1:补卡申请
                        atdrecord.setRemark("补卡申请");         //设置备注  补卡申请
                        atdrecord.setCardno(ids[ids.length-1]);          //插入胸卡ID
                        atdrecord.setChecker(UserUtils.getUser(session).getRealName());               //设置操作人
                        if(beginTimeStr.equals(timeArr[0])){
                            Date recdate = DateUtils.addDays(beginDate,i/2);
                            String recdate1 = DateUtils.formatDate(recdate,"yyyy-MM-dd");
                            atdrecord.setRecdate(recdate1);
                            int c = i%2;
                            atdrecord.setRectime(timeArr[i%2]);
                         /*   addDay++;
                            if(addDay >= 2){
                                addDay = 0;
                            }*/

                        }
                        if(beginTimeStr.equals(timeArr[1])){

                          /*  atdrecord.setRecdate(DateUtils.formatDate(DateUtils.addDays(beginDate,(i+1)/2),"yyyy-MM-dd"));
                            atdrecord.setRectime(timeArr[(i+1)%2]);*/
                            Date recdate = DateUtils.addDays(beginDate,(i+1)/2);
                            String recdate1 = DateUtils.formatDate(recdate,"yyyy-MM-dd");
                            atdrecord.setRecdate(recdate1);
                            int c = i%2;
                            atdrecord.setRectime(timeArr[(i+1)%2]);
                            /*addDay++;
                            if(addDay2 >= 2){
                                addDay2 = 0;
                            }*/

                        }

                        activitiController.atdDao.save(atdrecord);
                    }



                  /*  String recDate = DateFormat("yyyy-MM-dd" , buka.getNodate());
                    atdrecord.setRecdate(recDate);                               //设置补卡日期
                    String recTime = DateFormat("HH:ss" , buka.getNodate());
                    atdrecord.setRectime(recTime);                               //设置申请时间
                    //设置操作日期
                    Date nowDate = buka.getNowdate();
                    atdrecord.setOperdate(DateFormat("yyyy-MM-dd HH:mm:ss",nowDate));
                    //设置胸卡卡号
                    Long senderid = buka.getSenderid();
                    User sender = uDao.findByUserId(senderid).get(0);
                    String[] ids = sender.getCardId().split(",");
                    atdrecord.setCardno(ids[ids.length-1]);          //插入胸卡ID
                    atdrecord.setChecker(UserUtils.getUser(session).getRealName());               //设置操作人
                    atdDao.save(atdrecord);
                    */
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return "activitiHistory";
    }

    /**
     * 公章申请页面
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("gongzhang")
    public String gongzhang (@SessionAttribute("userId") Long userid,
                       HttpServletResponse response,
                       Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        Gongzhangshenqing gz = new Gongzhangshenqing();
        String processname = "公章申请";
        model.addAttribute("gongzhangshenqing",gz);
        model.addAttribute("user",user);
        model.addAttribute("leaderName" , leaderName);
        model.addAttribute("processname",processname);

        return "activiti/xingzheng/gongzhang";
    }


    /**
     * 公章申请流程
     * @param session
     * @param gongzhangshenqing
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("gongzhangshenqing")
    public String gongzhangshenqing(HttpSession session,
                                    Gongzhangshenqing gongzhangshenqing, BindingResult bindingResult,
                                    @RequestParam(value = "processname") String processname ,
                                    @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                    @RequestParam(value = "option",defaultValue="") String option ,
                                    HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            gongzhangshenqing.setProcessid(Long.parseLong(pi.getId()));
            gongzhangshenqing.setSenderid(user.getUserId());
            gongzhangshenqing.setSendername(user.getRealName());
            gzDao.save(gongzhangshenqing);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }


    /**
     * 礼品申领
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("lipin")
    public String lipin (@SessionAttribute("userId") Long userid,
                             HttpServletResponse response,
                             Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);
        String processname = "礼品申领";
        model.addAttribute("user",user);
        model.addAttribute("processname",processname);

        return "activiti/xingzheng/lipin";
    }


    /**
     * 礼品申领流程
     * @param session
     * @param lipin
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("lipinshenling")
    public String lipinshenling(HttpSession session,
                                    Lipin lipin, BindingResult bindingResult,
                                    @RequestParam(value = "processname") String processname ,
                                    @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                    @RequestParam(value = "option",defaultValue="") String option ,
                                    HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            lipin.setProcessinstanceid(Long.parseLong(pi.getId()));
            lipin.setSenderid(user.getUserId());
            lipin.setSender(user.getRealName());
            lipinDao.save(lipin);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{

            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }


    /**
     * 用酒申领
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("yongjiu")
    public String yongjiu (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        Jiu jiu = new Jiu();
        String processname = "用酒申领";
        model.addAttribute("jiu",jiu);
        model.addAttribute("user",user);
        model.addAttribute("leaderName" , leaderName);
        model.addAttribute("processname",processname);

        return "activiti/xingzheng/jiu";
    }

    /**
     * 用酒申领流程
     * @param session
     * @param jiu
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("yongjiushenling")
    public String yongjiushenling(HttpSession session,
                                Jiu jiu, BindingResult bindingResult,
                                  @RequestParam(value = "processname",defaultValue="") String processname ,
                                  @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                @RequestParam(value = "option",defaultValue="") String option ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            jiu.setProcessinstanceid(Long.parseLong(pi.getId()));
            jiu.setSenderid(user.getUserId());
            jiu.setSender(user.getRealName());
            jiuDao.save(jiu);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            //执行通过流程
            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }




    /**
     * 公章外借
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("gongzhangwaijie")
    public String gongzhangwaijie (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        Gongzhangwaijie gz = new Gongzhangwaijie();
        String processname = "公章外借";
        model.addAttribute("gz",gz);
        model.addAttribute("user",user);
        model.addAttribute("leaderName" , leaderName);
        model.addAttribute("processname",processname);

        return "activiti/xingzheng/gongzhangwaijie";
    }

    /**
     * 公章外借申请流程
     * @param session
     * @param gz
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("gongzhangwaijieshenqing")
    public String gongzhangwaijieshenqing(HttpSession session,
                                Gongzhangwaijie gz, BindingResult bindingResult,
                                @RequestParam(value = "processname") String processname ,
                                  @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                  @RequestParam(value = "option",defaultValue="") String option ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            gz.setProcessinstanceid(Long.parseLong(pi.getId()));
            gz.setSenderid(user.getUserId());
            gz.setSender(user.getRealName());
            gongzhangwaijieDao.save(gz);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }


    /**
     * 采购申请
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("caigou")
    public String caigou (@SessionAttribute("userId") Long userid,
                             HttpServletResponse response,
                             Model model,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        Gongzhangshenqing gz = new Gongzhangshenqing();
        String processname = "物品采购申请和领用";
        //model.addAttribute("caigou",caigou);
        model.addAttribute("user",user);
        model.addAttribute("leaderName" , leaderName);
        model.addAttribute("processname",processname);

        return "activiti/xingzheng/caigou";
    }
;
    /**
     * 采购申请流程
     * @param session
     * @param caigou
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("caigoushenqing")
    public String caigoushenqing(HttpSession session,
                                 Caigou caigou, BindingResult bindingResult,
                                 @RequestParam(value = "processname") String processname ,
                                 @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                                 @RequestParam(value = "option",defaultValue="") String option ,
                                 HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {
             variables.put("money",caigou.getSendprice());
            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            caigou.setProcessInstanceId(Long.parseLong(pi.getId()));
            caigou.setSenderid(user.getUserId());
            caigou.setSender(user.getRealName());

            caigouDao.save(caigou);
            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);

        }

        if(caigou.getCaigouSubList() != null && caigou.getCaigouSubList().size()>0) {
            List<CaigouSub> caigouSubList = caigou.getCaigouSubList();
            for (CaigouSub sub : caigouSubList) {
                sub.setCaigou(caigou);
            }
            caigouDao.save(caigou);
        }

        return "activitiHistory";
    }

    /**
     * 换岗申请
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("huangang")
    public String huangang (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);

        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = uDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        String processname = "换岗";
        model.addAttribute("user",user);
        model.addAttribute("processname",processname);

        return "activiti/renshi/huangang";
    }


    /**
     * 换岗申领流程
     * @param session
     * @param changeposition
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("huangangshenqing")
    public String huangangshenqing(HttpSession session,
                                Changeposition changeposition, BindingResult bindingResult,
                                @RequestParam(value = "processname") String processname ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {
            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User user1 = uDao.findByRealName(changeposition.getName());
            String deptFatherName = user1.getDept().getDeptFatherId().getDeptFatherName();
            variables.put("流程发起人", user.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("财务部_总监", findByRole("财务部_总监"));
            variables.put("员工", user1.getRealName());

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            changeposition.setProcessinstanceid(Long.parseLong(pi.getId()));
            changeposition.setSenderid(user.getUserId());
            changeposition.setSender(user.getRealName());
            changepositionDao.save(changeposition);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }

    /**
     * 招聘需求申请
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("zhaopin")
    public String zhaopin (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {

        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);
        String processname = "招聘需求";
        model.addAttribute("user",user);
        model.addAttribute("processname",processname);

        return "activiti/renshi/zhaopin";
    }


    /**
     * 招聘需求申领流程
     * @param session
     * @param recruit
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("zhaopinshenqing")
    public String zhaopinshenqing(HttpSession session,
                                  Recruit recruit, BindingResult bindingResult,
                                @RequestParam(value = "processname") String processname ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            recruit.setProcessinstanceid(Long.parseLong(pi.getId()));
            recruit.setSenderid(user.getUserId());
            recruit.setSender(user.getRealName());
            recruitDao.save(recruit);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }

    /**
     * 合同章申请
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("hetongzhang")
    public String hetongzhang (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);
        String processname = "合同章申请";
        model.addAttribute("user",user);
        model.addAttribute("processname",processname);

        return "activiti/guanli/hetongzhang";
    }


    /**
     * 合同章申请流程
     * @param session
     * @param contractcharter
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("hetongzhangshenqing")
    public String hetongzhangshenqing(HttpSession session,
                                Contractcharter contractcharter, BindingResult bindingResult,
                                @RequestParam(value = "processname") String processname ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();
        //首次申请，taskid为null
        if(taskid == null) {
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            contractcharter.setProcessinstanceid(Long.parseLong(pi.getId()));
            contractcharter.setSenderid(user.getUserId());
            contractcharter.setSender(user.getRealName());
            contractcharterDao.save(contractcharter);

            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }

    /**
     * 面谈
     * @param userid
     * @param response
     * @param model
     * @param page
     * @param size
     * @return
     * @throws IOException
     */
    @RequestMapping("miantan")
    public String miantan (@SessionAttribute("userId") Long userid,
                         HttpServletResponse response,
                         Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size) throws IOException {


        List<User> userList = uDao.findByUserId(userid);
        User user = userList.get(0);
        String processname = "员工面谈";
        model.addAttribute("user",user);
        model.addAttribute("processname",processname);

        return "activiti/renshi/miantan";
    }


    /**
     * 面谈流程
     * @param session
     * @param interview
     * @param bindingResult
     * @param processname
     * @param req
     * @return
     */
    @RequestMapping("miantanshenqing")
    public String miantanshenqing(HttpSession session,
                                Interview interview, BindingResult bindingResult, @RequestParam("filePath") MultipartFile[] filePath,
                                @RequestParam(value = "processname") String processname ,
                                HttpServletRequest req) {
        String taskid = req.getParameter("taskid");
        User user = UserUtils.getUser(session);
        Map<String, Object> variables = new HashMap<String, Object>();

        //传入时间
        Timestamp createTime = new Timestamp(new Date().getTime());

        //首次申请，taskid为null
        if(taskid == null) {
            for (int i=0;i<filePath.length;i++){
                String filename = filePath[i].getOriginalFilename();
                if (!filename.equals("")) {
                    try {
                        byte[] fileb = filePath[i].getBytes();
                        //String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
                        String cp11111 = "filefeePath/";

                        Date d1 = createTime;//bu.getFeeDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
                        String dateNowStr = sdf.format(d1);
                        String datetimeNowStr = sdftime.format(d1);
                        String pathstr = cp11111 + dateNowStr;


                        String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//格式
                        String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
                        String picName =   fname + "_" + datetimeNowStr + "." + type;//图片名称

                        comController.uploadFilecom(fileb, pathstr, picName);
                        interview.setFile( pathstr + "/" + picName + ";");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    return "common/procefile";
                }
            }




            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session,variables,processname);        //session 获取当前登陆人信息  processName 流程名称
            User leader = UserUtils.getLeader(session);

            //2、开始任务实例
            IdentityService identityService = processEngine.getIdentityService();
            identityService.setAuthenticatedUserId(user.getRealName());
            ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                    .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

            //3、执行流程第一步~
            TaskService taskService = processEngine.getTaskService();
            Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
            taskService.complete(task.getId(),variables);

            //4、将流程信息添加到具体流程信息实体类中
            try {
                interview.setProcessinstanceid(Long.parseLong(pi.getId()));
                interview.setSenderid(user.getUserId());
                interview.setSender(user.getRealName());
                interviewDao.save(interview);
            }catch(Exception e){
                req.setAttribute("error", e);
                return "common/proce";

            }
            System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
            System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
        }else{
            //执行通过流程
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);
        }
        return "activitiHistory";
    }



    /**
     * 我的待办任务
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value="myactiviti")
    public String myactiviti(Model model, HttpSession session,
                             @RequestParam(value="processSelect",required=false)String processSelect,
                             @RequestParam(value="datebegin",required=false)String datebegin,
                             @RequestParam(value="dateend",required=false)String dateend) throws Exception{
        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");

        User user = UserUtils.getUser(session);
        HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.
                getHistoryService().createHistoricProcessInstanceQuery().startedBy(user.getRealName());
        for(HistoricProcessInstance q : historicProcessInstanceQuery.list()){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表ll,.lllll
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只选择在给定日期之后开始的历史进程实例。
        TaskInfoQuery query = processEngine.getTaskService().createTaskQuery().taskAssignee(user.getRealName()).orderByTaskCreateTime().desc();
        List<Task> taskNames = query.list();
        for(TaskInfo task :taskNames){
            processList.add(task.getProcessDefinitionId().split(":")[0]);
        }

        if(datebegin != null && datebegin != ""){
            query = query.taskCreatedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            query = query.taskCreatedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            query = query.processDefinitionKey(processSelect);
        }


        HistoricProcessInstance historicProcessInstance = null;
        Long userId = (Long) session.getAttribute("userId");
        List<Task> taskList = query.list();


        //List<Task> taskList = aUtils.queryCurretUserTaskByAssignerr(user.getRealName());
        for(Task t : taskList){
            ProcessInstance processInstance = aUtils.getProcessInstanceByTask(t);       //得到ProcessInstance引擎
            String processInstanceId = processInstance.getProcessInstanceId();          //得到processInsta


            historicProcessInstance = aUtils.getHistoricProcessInstance(processInstanceId,processEngine);
            t.setAssignee(historicProcessInstance.getStartUserId());        //临时设置项目发起人
            t.setCategory(t.getProcessDefinitionId().split(":")[0]);      //设置流程名称


        }
        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }

        model.addAttribute("taskList",taskList);

        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);

        model.addAttribute("url","myactiviti");
        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);


        return "activiti/myactiviti";
    }


    /**
     * 显示我的申请历史记录。
     * @param model
     * @return
     */
    @RequestMapping("activitiHistory")
    public String activitiHistory(Model model,HttpSession session,
                                  @RequestParam(value="processSelect",required=false)String processSelect,
                                   @RequestParam(value="datebegin",required=false)String datebegin,
                                  @RequestParam(value="dateend",required=false)String dateend
                                ) throws Exception{


        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");

        User user = UserUtils.getUser(session);
        HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.
                getHistoryService().createHistoricProcessInstanceQuery().startedBy(user.getRealName());
        for(HistoricProcessInstance q : historicProcessInstanceQuery.list()){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只选择在给定日期之后开始的历史进程实例。

        if(datebegin != null && datebegin != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            historicProcessInstanceQuery = historicProcessInstanceQuery.processDefinitionKey(processSelect);
        }

        HistoricProcessInstanceQuery a = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> historicProcessInstanceList = a.list() ;
        Iterable<SystemStatusList>  statusname = sdao.findByStatusModel("aoa_task_list1");
        List<ProcessList> prolist = new ArrayList<ProcessList>();
        for(HistoricProcessInstance h : historicProcessInstanceList){
           String processInstanceId = h.getId();
           List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
            HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);

            ProcessList p  = new ProcessList();
            p.setActivitiprocessid(processInstanceId);               //临时用来存流程ID
            p.setTypeNmae(h.getProcessDefinitionId().split(":")[0]);                                //设置任务名称

            p.setApplyTime(h.getStartTime());                         //设置开始时间
            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
            ProcessInstanceQuery processInstance = createProcessInstanceQuery.processInstanceId(processInstanceId);
            ProcessInstance singleResult = processInstance.singleResult();
            boolean passflag = false;
            for(HistoricActivityInstance hai : historicActivityInstanceList ){
                if(hai.getActivityName().equals("End")){
                    passflag = true;
                    break;
                }
            }
            if(singleResult==null){
                if(passflag == true){
                    p.setStatusId(36L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }
            //设置状态
            /*if(h.getEndTime() != null){
                ;p.setStatusId(7L)
                if(historicActivityInstance.getActivityName().equals("End")){
                    p.setStatusId(7L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }*/
            prolist.add(p);
        }

        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }

        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);


        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);
       model.addAttribute("statusname",statusname);
       model.addAttribute("prolist",prolist);
       model.addAttribute("url","activitiHistory");
        model.addAttribute("urltable","activitiHistorytable");

        return "activiti/activitiHistory";
    }


    @RequestMapping("activitiHistorytable")
    public String activitiHistorytable(Model model,HttpSession session,
                                  @RequestParam(value="processSelect",required=false)String processSelect,
                                  @RequestParam(value="datebegin",required=false)String datebegin,
                                  @RequestParam(value="dateend",required=false)String dateend
    ) throws Exception{


        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");

        User user = UserUtils.getUser(session);
        HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.
                getHistoryService().createHistoricProcessInstanceQuery().startedBy(user.getRealName());
        for(HistoricProcessInstance q : historicProcessInstanceQuery.list()){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只选择在给定日期之后开始的历史进程实例。

        if(datebegin != null && datebegin != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            historicProcessInstanceQuery = historicProcessInstanceQuery.processDefinitionKey(processSelect);
        }

        HistoricProcessInstanceQuery a = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
        List<HistoricProcessInstance> historicProcessInstanceList = a.list() ;
        Iterable<SystemStatusList>  statusname = sdao.findByStatusModel("aoa_task_list1");
        List<ProcessList> prolist = new ArrayList<ProcessList>();
        for(HistoricProcessInstance h : historicProcessInstanceList){
            String processInstanceId = h.getId();
            List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
            HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);

            ProcessList p  = new ProcessList();
            p.setActivitiprocessid(processInstanceId);               //临时用来存流程ID
            p.setTypeNmae(h.getProcessDefinitionId().split(":")[0]);                                //设置任务名称

            p.setApplyTime(h.getStartTime());                         //设置开始时间
            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
            ProcessInstanceQuery processInstance = createProcessInstanceQuery.processInstanceId(processInstanceId);
            ProcessInstance singleResult = processInstance.singleResult();
            boolean passflag = false;
            for(HistoricActivityInstance hai : historicActivityInstanceList ){
                if(hai.getActivityName().equals("End")){
                    passflag = true;
                    break;
                }
            }
            if(singleResult==null){
                if(passflag == true){
                    p.setStatusId(36L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }
            //设置状态
            /*if(h.getEndTime() != null){
                ;p.setStatusId(7L)
                if(historicActivityInstance.getActivityName().equals("End")){
                    p.setStatusId(7L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }*/
            prolist.add(p);
        }

        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }

        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);


        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);
        model.addAttribute("statusname",statusname);
        model.addAttribute("prolist",prolist);
        model.addAttribute("url","activitiHistory");
        model.addAttribute("urltable","activitiHistorytable");


        return "activiti/activitiHistorytable";
    }



    /**
     * 查看办理过的任务。
     * @param model
     * @return
     */
    @RequestMapping("activitiPassedHistory")
    public String activitiPassedHistory(Model model,HttpSession session ,
                                        @RequestParam(value="processSelect",required=false)String processSelect,
                                        @RequestParam(value="datebegin",required=false)String datebegin,
                                        @RequestParam(value="dateend",required=false)String dateend,
                                        @RequestParam(value="deptSelect",required=false)String deptSelect,
                                        @RequestParam(value="sender",required=false)String sender) throws Exception{

        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");
        User user = UserUtils.getUser(session);
        //historicProcessInstanceList  历史此人所有记录
        HistoricActivityInstanceQuery hisact = processEngine.getHistoryService().createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceStartTime().desc();
        HistoricProcessInstanceQuery proact = processEngine.getHistoryService().createHistoricProcessInstanceQuery();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只选择在给定日期之后开始的历史进程实例。

        List<String> deptList = dDao.findnames();
        deptList.add(0,"-请选择-");

        if(datebegin != null && datebegin != ""){
            proact = proact.startedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            proact = proact.startedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            proact = proact.processDefinitionKey(processSelect);
        }
        //只选择具有给发起人流程定义定义的历史流程实例。
        if(sender != null  && !("").equals(sender)){
            proact = proact.startedBy(sender);
        }
      /*  //只选择具有给部门的流程定义定义的历史流程实例。
        if(deptSelect != null  && !("-请选择-").equals(deptSelect)){
        }*/

        List<HistoricProcessInstance> sdsd = proact.list();
        for(HistoricProcessInstance q : proact.list()){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表
        }

        List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
        List<String> processInstancesIdList = new ArrayList<>();
        List<HistoricActivityInstance> hisActivityInstanceList = hisact.list();
        List<String> processInstanceIds = new ArrayList<>();
        for (HistoricActivityInstance a : hisActivityInstanceList) {
            if (a.getAssignee() != null && a.getAssignee().equals(user.getRealName())) {
                HistoricProcessInstanceQuery query = proact.processInstanceId(a.getProcessInstanceId());
                List<HistoricProcessInstance> list =  query.list();
                if(list.size() != 0) {
                    HistoricProcessInstance instance = list.get(0);
                    //判断是否为本人发起
                    if (!instance.getStartUserId().equals(user.getRealName())) {
                        historicProcessInstanceList.add(instance);
                        processInstanceIds.add(a.getProcessInstanceId());
                    }
                }
            }
        }


        Iterable<SystemStatusList>  statusname = sdao.findByStatusModel("aoa_task_list1");
        List<ProcessList> prolist = new ArrayList<ProcessList>();
        for(HistoricProcessInstance h : historicProcessInstanceList){
            String processInstanceId = h.getId();
            //historicActivityInstanceList  历史节点信息
            List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
            HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);

            ProcessList p  = new ProcessList();
            p.setActivitiprocessid(processInstanceId);               //临时用来存流程ID
            p.setTypeNmae(h.getProcessDefinitionId().split(":")[0]);                                //设置任务名称
            p.setApplyTime(h.getStartTime());                         //设置开始时间
            p.setShenuser(h.getStartUserId());

            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
            ProcessInstanceQuery processInstance = createProcessInstanceQuery.processInstanceId(processInstanceId);
            ProcessInstance singleResult = processInstance.singleResult();
            boolean passfalg = false;
            for(HistoricActivityInstance hai : historicActivityInstanceList ){
                if(hai.getActivityName().equals("End")){
                    passfalg = true;
                    break;
                }
            }
            if(singleResult==null){
                if(passfalg == true){
                    p.setStatusId(36L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                System.out.println("正在执行");
                p.setStatusId(5L);                                  //进行中
            }
            prolist.add(p);
        }


        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }


        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);
        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);
        model.addAttribute("url","activitiPassedHistory");
        model.addAttribute("urltable","activitiPassedHistorytable");
        model.addAttribute("statusname",statusname);
        model.addAttribute("prolist",prolist);
        model.addAttribute("sender",sender);
        /*model.addAttribute("deptSelect",deptSelect);
        model.addAttribute("deptList",deptList);*/



        return "activiti/activitiPassedHistory";
    }

    @RequestMapping("activitiPassedHistorytable")
    public String activitiPassedHistorytable(Model model,HttpSession session ,
                                        @RequestParam(value="processSelect",required=false)String processSelect,
                                        @RequestParam(value="datebegin",required=false)String datebegin,
                                        @RequestParam(value="dateend",required=false)String dateend,
                                        @RequestParam(value="deptSelect",required=false)String deptSelect,
                                        @RequestParam(value="sender",required=false)String sender) throws Exception{

        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");
        User user = UserUtils.getUser(session);
        //historicProcessInstanceList  历史此人所有记录
        HistoricActivityInstanceQuery hisact = processEngine.getHistoryService().createHistoricActivityInstanceQuery().orderByHistoricActivityInstanceStartTime().desc();
        HistoricProcessInstanceQuery proact = processEngine.getHistoryService().createHistoricProcessInstanceQuery();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //只选择在给定日期之后开始的历史进程实例。

        List<String> deptList = dDao.findnames();
        deptList.add(0,"-请选择-");

        if(datebegin != null && datebegin != ""){
            proact = proact.startedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            proact = proact.startedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            proact = proact.processDefinitionKey(processSelect);
        }
        //只选择具有给发起人流程定义定义的历史流程实例。
        if(sender != null  && !("").equals(sender)){
            proact = proact.startedBy(sender);
        }
      /*  //只选择具有给部门的流程定义定义的历史流程实例。
        if(deptSelect != null  && !("-请选择-").equals(deptSelect)){
        }*/

        List<HistoricProcessInstance> sdsd = proact.list();
        for(HistoricProcessInstance q : proact.list()){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表
        }

        List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
        List<String> processInstancesIdList = new ArrayList<>();
        List<HistoricActivityInstance> hisActivityInstanceList = hisact.list();
        List<String> processInstanceIds = new ArrayList<>();
        for (HistoricActivityInstance a : hisActivityInstanceList) {
            if (a.getAssignee() != null && a.getAssignee().equals(user.getRealName())) {
                HistoricProcessInstanceQuery query = proact.processInstanceId(a.getProcessInstanceId());
                List<HistoricProcessInstance> list =  query.list();
                if(list.size() != 0) {
                    HistoricProcessInstance instance = list.get(0);
                    //判断是否为本人发起
                    if (!instance.getStartUserId().equals(user.getRealName())) {
                        historicProcessInstanceList.add(instance);
                        processInstanceIds.add(a.getProcessInstanceId());
                    }
                }
            }
        }


        Iterable<SystemStatusList>  statusname = sdao.findByStatusModel("aoa_task_list1");
        List<ProcessList> prolist = new ArrayList<ProcessList>();
        for(HistoricProcessInstance h : historicProcessInstanceList){
            String processInstanceId = h.getId();
            //historicActivityInstanceList  历史节点信息
            List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
            HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);

            ProcessList p  = new ProcessList();
            p.setActivitiprocessid(processInstanceId);               //临时用来存流程ID
            p.setTypeNmae(h.getProcessDefinitionId().split(":")[0]);                                //设置任务名称
            p.setApplyTime(h.getStartTime());                         //设置开始时间
            p.setShenuser(h.getStartUserId());

            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
            ProcessInstanceQuery processInstance = createProcessInstanceQuery.processInstanceId(processInstanceId);
            ProcessInstance singleResult = processInstance.singleResult();
            boolean passfalg = false;
            for(HistoricActivityInstance hai : historicActivityInstanceList ){
                if(hai.getActivityName().equals("End")){
                    passfalg = true;
                    break;
                }
            }
            if(singleResult==null){
                if(passfalg == true){
                    p.setStatusId(36L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                System.out.println("正在执行");
                p.setStatusId(5L);                                  //进行中
            }
            prolist.add(p);
        }


        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }


        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);
        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);
        model.addAttribute("url","activitiPassedHistory");
        model.addAttribute("urltable","activitiPassedHistorytable");
        model.addAttribute("statusname",statusname);
        model.addAttribute("prolist",prolist);
        model.addAttribute("sender",sender);
        /*model.addAttribute("deptSelect",deptSelect);
        model.addAttribute("deptList",deptList);*/


        return "activiti/activitiHistorytable";


    }


    /**
     * 查看我的申请
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("ProcessHistory")
    public String ProcessHistory(Model model,HttpServletRequest req){

        //获取TaskId
        String processInstanceId = req.getParameter("processInstanceId");
        String taskid = "";
        //是否显示审核 或 不同意
        boolean showflag = false;
        //获取流程名称

        //审核  有ProcessInstanceId ， 查看历史 无ProcessInstanceId
        if(processInstanceId == null || processInstanceId.equals("")){
            taskid= req.getParameter("taskid");
            Task task = aUtils.getTaskByTaskId(taskid);
            processInstanceId = aUtils.getProcessInstanceByTask(task).getProcessInstanceId();
            showflag = true;
        }

        String processname = req.getParameter("processname");
        String ClassName = "";  //返回可配置页面-
        String imageName = "";  //返回可配置流程/图片

        List<HistoricActivityInstance> historyInfo = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        //添加批注
        List<Comment> historyCommnets = new ArrayList<>();
        for(HistoricActivityInstance his : historyInfo){
            String historytaskId = his.getTaskId();
            List<Comment> comments = processEngine.getTaskService().getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                historyCommnets.addAll(comments);
            }
        }
        model.addAttribute("historyCommnets",historyCommnets);

        /*historyInfo.remove(0);*/
        model.addAttribute("historyInfo",historyInfo);

        boolean showempevle = false;
        boolean showdeptevle = false;
        String activitiname ="";
        if(historyInfo != null && historyInfo.size()>0){
            for(HistoricActivityInstance hai : historyInfo){
                System.out.println(hai.getId()+"  "+hai.getActivityName() + "   " +hai.getAssignee() + "    " + hai.getEndTime());
            }
            String aname =historyInfo.get(historyInfo.size()-1).getActivityName();
            if (historyInfo.get(historyInfo.size()-1).getActivityName().equals("员工填写自评")){
                showempevle =true;
                 activitiname ="员工填写自评";
            }else  if (historyInfo.get(historyInfo.size()-1).getActivityName().equals("部门负责人评定及建议")){
                showdeptevle =true;
                activitiname ="部门负责人评定及建议";
            }
        }


        if(processname != null && processname != "") {
            imageName = processname.split(":")[0];
            if (imageName.equals("补卡申请")) {
                //获取流程编号
                Buka buka = bukaDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("buka", buka);
                ClassName = "activiti/buka/BukaApproval";
            } else if (imageName.equals("事假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {
                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);
                    model.addAttribute("processname", processname);
                }
                ClassName = "activiti/Leave/leaveApproval";
            } else if (imageName.equals("公章申请")) {
                //获取流程编号
                Gongzhangshenqing gz = gzDao.findbyProcessid((Long.parseLong(processInstanceId)));
                String[] types = gz.getType().split(",");
                String[] contracts = gz.getContract().split(",");
                List<String> typeList = new ArrayList<>();
                List<String> contractList = new ArrayList<>();
                for (String s : types) {
                    typeList.add(s);
                }
                for (String s : contracts) {
                    contractList.add(s);
                }
                model.addAttribute("typeList", typeList);
                model.addAttribute("contractList", contractList);
                model.addAttribute("gz", gz);
                model.addAttribute("taskid", taskid);
                model.addAttribute("processname", processname);
                ClassName = "activiti/xingzheng/gongzhangshenqingApproval";
            } else if (imageName.equals("物品采购申请和领用")) {
                //获取流程编号
                Caigou caigou = caigouDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("caigou", caigou);
                ClassName = "activiti/xingzheng/caigouApproval";
            } else if (imageName.equals("年假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if (imageName.equals("病假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            } else if (imageName.equals("总裁室事假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            } else if (imageName.equals("总裁室年假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }  else if (imageName.equals("总裁室病假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }  else if (imageName.equals("总裁室特别假申请")) {
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }  else if (imageName.equals("费用报销")) {
                //获取流程编号
                List<Expense> leavelist = expenseDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("expense", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Expense/ExpenseApproval";
            } else if (imageName.equals("开票申请")) {
                //获取流程编号
                List<Kaipiao> leavelist = kaipiaoDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size() > 0) {

                    model.addAttribute("kaipiao", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Kaipiao/kaipiaoApproval";
            } else if (imageName.equals("用酒申领")) {
                //获取流程编号
                Jiu jiu = jiuDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("jiu", jiu);
                ClassName = "activiti/xingzheng/jiuApproval";

            } else if (imageName.equals("礼品申领")) {
                //获取流程编号
                Lipin lipin = lipinDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("lipin", lipin);
                ClassName = "activiti/xingzheng/lipinApproval";

            } else if (imageName.equals("公章外借")) {
                //获取流程编号
                Gongzhangwaijie gz = gongzhangwaijieDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("gz", gz);
                ClassName = "activiti/xingzheng/gongzhangwaijieApproval";

            } else if (imageName.equals("换岗")) {
                //获取流程编号
                Changeposition changeposition = changepositionDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("changeposition", changeposition);
                ClassName = "activiti/renshi/huangangApproval";
            }else if(imageName.equals("合同初签")){
                //获取流程编号
                List<Contractcq> leavelist = contractcqDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size()>0) {

                    model.addAttribute("contractcq", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Contractcq/contractcqApproval";
            }else if(imageName.equals("合同变更")){
                //获取流程编号
                List<Contractbg> leavelist = contractbgDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size()>0) {

                    model.addAttribute("contractbg", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Contractbg/contractbgApproval";
            }else if(imageName.equals("合同续签部门")||imageName.equals("合同续签员工")){
                //获取流程编号
                List<Contractxq> leavelist = contractxqDao.findbyProcessid((Long.parseLong(processInstanceId)));
                //List<Leave> leavelist2 = leaveDao.findAll();
                if (leavelist.size()>0) {

                    model.addAttribute("contractxq", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Contractxq/contractxqApproval";
            }else if(imageName.equals("入职流程")){
                //获取流程编号
                List<Entry> leavelist = entryDao.findbyProcessid((Long.parseLong(processInstanceId)));
                if (leavelist.size()>0) {

                    model.addAttribute("entry", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Entry/entryApproval";
            }else if(imageName.equals("离职流程")){
                //获取流程编号
                List<Exit> leavelist = exitDao.findbyProcessid((Long.parseLong(processInstanceId)));
                if (leavelist.size()>0) {

                    model.addAttribute("exit", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                }
                ClassName = "activiti/Exit/exitApproval";
            }else if(imageName.equals("转正流程")){
                //获取流程编号
                List<Regulara> leavelist = regularaDao.findbyProcessid((Long.parseLong(processInstanceId)));
                List<Regularreply> eval = regularreplyDao.findbyProcessid((Long.parseLong(processInstanceId)));
                String emple=eval.get(0).getEmpeval();
                if (leavelist.size()>0) {

                    model.addAttribute("regulara", leavelist.get(0));
                    model.addAttribute("taskid", processInstanceId);
                    model.addAttribute("empeval", eval.get(0).getEmpeval());
                    model.addAttribute("depteval", eval.get(0).getDepteval());
                    model.addAttribute("processname", processname);
                    model.addAttribute("show", false);
                    model.addAttribute("showemp", showempevle);
                    model.addAttribute("showdept", showdeptevle);
                    model.addAttribute("activitiname", activitiname);
                }
                ClassName = "activiti/Regular/regularApproval";
            } else if (imageName.equals("合同章申请")) {
                //获取流程编号
                Contractcharter contractcharter = contractcharterDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("contractcharter", contractcharter);
                ClassName = "activiti/guanli/hetongzhangApproval";

            } else if (imageName.equals("员工面谈")) {
                //获取流程编号
                Interview interview = interviewDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("interview", interview);
                ClassName = "activiti/renshi/miantanApproval";

            } else if (imageName.equals("招聘需求")) {
                //获取流程编号
                Recruit recruit = recruitDao.findbyProcessid((Long.parseLong(processInstanceId)));
                model.addAttribute("recruit", recruit);
                ClassName = "activiti/renshi/zhaopinApproval";

            }

        }

        model.addAttribute("show", showflag);
        model.addAttribute("taskid",taskid);
        model.addAttribute("imageName",imageName);
        model.addAttribute("processInstanceId",processInstanceId);
        model.addAttribute("processname",processname);
        return ClassName;

    }


    /**
     * 查看我的待办任务
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("viewProcess")
    public String viewProcess(Model model
                                , HttpServletRequest req){

        //获取TaskId
        String taskid = req.getParameter("taskid");
        //获取流程名称
        String processname = req.getParameter("processname");
        String ClassName = "";  //返回可配置页面
        String imageName = "";  //返回可配置流程/图片
        Task task = aUtils.getTaskByTaskId(taskid);
        ProcessInstance processInstance = aUtils.getProcessInstanceByTask(task);


        //显示历史完成信息
        String processInstanceId = processInstance.getProcessInstanceId();
        List<HistoricActivityInstance> historyInfo = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        model.addAttribute("historyInfo",historyInfo);

        boolean showempevle = false;
        boolean showdeptevle = false;
        String activitiname ="";
        if(historyInfo != null && historyInfo.size()>0){
            for(HistoricActivityInstance hai : historyInfo){
                System.out.println(hai.getId()+"  "+hai.getActivityName() + "   " +hai.getAssignee() + "    " + hai.getEndTime());
            }
            if (historyInfo.get(historyInfo.size()-1).getActivityName().equals("员工填写自聘") && historyInfo.get(historyInfo.size()-1).getEndTime()==null){
                showempevle =true;
                activitiname =historyInfo.get(historyInfo.size()-1).getActivityName();
            }else  if (historyInfo.get(historyInfo.size()-1).equals("部门负责人评定及建议")&& historyInfo.get(historyInfo.size()-1).getEndTime()==null){
                showdeptevle =true;
                activitiname =historyInfo.get(historyInfo.size()-1).getActivityName();
            }
        }

        if(processname != null && processname != ""){
            imageName = processname.split(":")[0];
            if(imageName.equals("补卡申请")){
                //获取流程编号  通过taskid 找 processInstanceId
                Buka buka = bukaDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                model.addAttribute("buka",buka);
                model.addAttribute("taskid",taskid);
                ClassName = "activiti/buka/BukaApproval";
                model.addAttribute("show",true);
                model.addAttribute("processname",processname);
            }else if(imageName.equals("事假申请")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("年假申请")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("病假申请")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("总裁室事假")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("总裁室年假")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("总裁室病假")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("总裁室特别假")){
                //获取流程编号
                List<Leave> leavelist = leaveDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("leave", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Leave/leaveApproval";
            }else if(imageName.equals("费用报销")){
                //获取流程编号
                List<Expense> leavelist = expenseDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("expense", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Expense/expenseApproval";
            }else if(imageName.equals("开票申请")){
                //获取流程编号
                List<Kaipiao> leavelist = kaipiaoDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("kaipiao", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Kaipiao/kaipiaoApproval";
            }else if(imageName.equals("合同初签")){
                //获取流程编号
                List<Contractcq> leavelist = contractcqDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("contractcq", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Contractcq/contractcqApproval";
            }else if(imageName.equals("合同变更")){
                //获取流程编号
                List<Contractbg> leavelist = contractbgDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("contractbg", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Contractbg/contractbgApproval";
            }else if(imageName.equals("合同续签员工") || imageName.equals("合同续签部门")){
                //获取流程编号
                List<Contractxq> leavelist = contractxqDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("contractxq", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Contractxq/contractxqApproval";
            }else if(imageName.equals("入职流程")){
                //获取流程编号
                List<Entry> leavelist = entryDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("entry", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Entry/entryApproval";
            }else if(imageName.equals("离职流程")){
                //获取流程编号
                List<Exit> leavelist = exitDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("exit", leavelist.get(0));
                    model.addAttribute("taskid", taskid);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                }
                ClassName = "activiti/Exit/exitApproval";
            }else if(imageName.equals("转正流程")){
                //获取流程编号
                List<Regulara> leavelist = regularaDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                List<Regularreply> eval = regularreplyDao.findbyProcessid((Long.parseLong(processInstance.getId())));
                if (leavelist.size()>0) {

                    model.addAttribute("regulara", leavelist.get(0));
                    model.addAttribute("eval", eval.get(0));

                    model.addAttribute("taskid", taskid);
                    model.addAttribute("activitiname", activitiname);

                    model.addAttribute("processname", processname);
                    model.addAttribute("show", true);
                    model.addAttribute("showemp", showempevle);
                    model.addAttribute("showdept", showdeptevle);

                }
                ClassName = "activiti/Regulara/regularApproval";
            }


        }



        model.addAttribute("taskId");
        model.addAttribute("imageName",imageName);
        return ClassName;

    }

    /**
     * 中止任务
     * @param processname
     */
    @RequestMapping(value="reject")
    public String reject(
                         @RequestParam(value = "processname") String processname ,
                         @RequestParam(value = "option",defaultValue="") String option ,
                         HttpServletRequest req){
        String taskid = req.getParameter("taskid");
        Task task = aUtils.getTaskByTaskId(taskid);
        String processInstanceId = aUtils.getProcessInstanceByTask(task).getProcessInstanceId();
        processEngine.getTaskService().addComment(taskid,processInstanceId,option);
        aUtils.distoryProcess(processInstanceId,processEngine);

        return "myactiviti";

    }


    /**
     * 设置流程所需变量名
     * @param session
     * @param variables
     * @param processname
     */
    private void setVariables(HttpSession session , Map<String, Object> variables,String processname) {
            if (processname.equals("补卡申请")) {
                User user = UserUtils.getUser(session);
                User leader = UserUtils.getLeader(session);

                String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
                List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");
                variables.put("流程发起人", user.getRealName());
                variables.put("部门负责人", leader.getRealName());
                variables.put("中心总经理",  findByRole(user.getDept().getDeptName() + "_中心总经理"));
                variables.put("leader" , user.getUserId() == user.getFatherId());

            }
            if (processname.equals("公章申请")) {
                User user = UserUtils.getUser(session);
                User leader = UserUtils.getLeader(session);

                String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
                List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");
                String deptmanager = managerList.get(0).getRealName();
                String guanlibu = uDao.findUsersByRoleName("营销中心_管理部_经理").get(0).getRealName();
                variables.put("项目发起人", user.getRealName());
                variables.put("部门领导", leader.getRealName());
                variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
                variables.put("副总裁", findByRole("副总裁"));
                variables.put("行政部_高级经理", findByRole("行政部_高级经理"));
            }

        if (processname.equals("物品采购申请和领用")) {
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);

            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");
            String deptmanager = managerList.get(0).getRealName();
            String guanlibu = uDao.findUsersByRoleName("营销中心_管理部_经理").get(0).getRealName();
            variables.put("流程发起人", user.getRealName());
            variables.put("部门领导", leader.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("副总裁", findByRole("副总裁"));
            variables.put("行政部_高级经理", findByRole("行政部_高级经理"));
            variables.put("总裁", findByRole("总裁"));
            variables.put("行政部采购", findByRole("行政部_采购"));
        }

        if (processname.equals("用酒申领")) {
            User user = UserUtils.getUser(session);
            variables.put("流程发起人", user.getRealName());
            variables.put("总裁", findByRole("总裁"));
            variables.put("行政部采购", findByRole("行政部_采购"));
            variables.put("行政部礼品发放人", findByRole("行政部礼品发放人"));
        }


        if (processname.equals("礼品申领")) {
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);

            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            variables.put("流程发起人", user.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("副总裁", findByRole("副总裁"));
            variables.put("行政部礼品发放人", findByRole("行政部礼品发放人"));
        }


        if (processname.equals("公章外借")) {
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);

            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            variables.put("流程发起人", user.getRealName());
            variables.put("部门领导", leader.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("总裁", findByRole("总裁"));
            variables.put("行政部礼品发放人", findByRole("行政部礼品发放人"));
        }

        if (processname.equals("物品采购申请和领用")) {
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);

            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");
            String deptmanager = managerList.get(0).getRealName();
            String guanlibu = uDao.findUsersByRoleName("营销中心_管理部_经理").get(0).getRealName();
            variables.put("流程发起人", user.getRealName());
            variables.put("部门领导", leader.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("总裁", findByRole("总裁"));
            variables.put("行政部礼品发放人", findByRole("行政部礼品发放人"));
        }

        if(processname.equals("招聘需求")){
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);
            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");

            variables.put("流程发起人", user.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("副总裁", findByRole("副总裁"));
            variables.put("人力资源部_副总监", findByRole("人力资源部_副总监"));
        }

        if(processname.equals("合同章申请")){
            User user = UserUtils.getUser(session);
            User leader = UserUtils.getLeader(session);
            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            List<User> managerList = uDao.findUsersByRoleName(deptFatherName + "_中心总经理");

            variables.put("流程发起人", user.getRealName());
            variables.put("部门负责人", leader.getRealName());
            variables.put("中心总经理", findByRole(deptFatherName + "_中心总经理"));
            variables.put("营销中心_管理部_经理", findByRole("营销中心_管理部_经理"));
        }
        if(processname.equals("员工面谈")){
            User user = UserUtils.getUser(session);
            variables.put("流程发起人", user.getRealName());
            variables.put("总裁", findByRole("总裁"));
        }

    }


    @RequestMapping("passProcess")
    public String passPrcess(HttpSession session,Model model, @RequestParam(value = "taskId", defaultValue = "0") String taskId,HttpServletRequest req){
        try {
            Task task = aUtils.getTaskByTaskId(taskId);
            ProcessInstance processInstance = aUtils.getProcessInstanceByTask(task);       //得到ProcessInstance引擎
            String processInstanceId = processInstance.getProcessInstanceId();          //得到processInsta
            String processName = task.getProcessDefinitionId().split(":")[0];      //流程名称
            if(processName.equals("补卡申请")){
                Class clazz = Class.forName("cn.gson.oasys.controller.activiti.ActivitiController");
                Method method = clazz.getMethod("bukashenqing", HttpSession.class, Buka.class, String.class, String.class, String.class, MultipartFile[].class, String.class, HttpServletRequest.class);
                 method.invoke(clazz.newInstance(),session,null,processInstanceId,processInstanceId,"列表同意",null,taskId,req);
            }else if(processName.equals("请假")){
                Class clazz = Class.forName("cn.gson.oasys.controller.activiti.LeaveController");
                Method method = clazz.getMethod("bukashenqing", HttpSession.class, Buka.class, String.class, String.class, String.class, String.class, HttpServletRequest.class);
                Object obj = method.invoke(clazz.newInstance(),session,null,"",null,"列表同意",taskId,req);
                System.out.println(obj);
            }else if(processName.equals("事假申请") || processName.equals("年假申请") || processName.equals("病假申请") || processName.equals("特别假申请")
                    || processName.equals("总裁室事假申请") || processName.equals("总裁室特别假申请") || processName.equals("总裁室年假申请") || processName.equals("总裁室病假申请") ){
                Class clazz = Class.forName("cn.gson.oasys.controller.activiti.LeaveController");
                Leave leave = leaveDao.findbyProcessid(Long.parseLong(processInstanceId)).get(0);
                Method method = clazz.getMethod("saveLeave", HttpSession.class, HttpServletRequest.class, String.class, MultipartFile[].class, String.class, Leave.class, BindingResult.class, String.class, Model.class);
                Object obj = method.invoke(clazz.newInstance(),session,req,"列表同意",null,processInstanceId,leave,null,taskId,model);
                System.out.println(obj);
            }else {
                processEngine.getTaskService().addComment(taskId, processInstanceId, "列表同意");
                //执行通过流程
                processEngine.getTaskService()//与正在执行的任务管理相关的Service
                        .complete(taskId);
            }
            model.addAttribute("success","操作成功");
        }catch (Exception e){
            model.addAttribute("error", "发生异常，请联系管理员");
        }



        return "/myactiviti";
    }

    @RequestMapping("passAllProcess")
    public String passAllPrcess(Model model, @RequestParam(value = "taskIds", defaultValue = "0") String[] taskIds, HttpServletRequest req,HttpSession session){
        try {
            for(int i = 0 ; i < taskIds.length ; i++) {
                Task task = aUtils.getTaskByTaskId(taskIds[i]);
                String processInstanceId = aUtils.getProcessInstanceByTask(task).getProcessInstanceId();
                String processName = task.getProcessDefinitionId().split(":")[0];      //流程名称
                if(processName.equals("补卡申请")){
                    Class clazz = Class.forName("cn.gson.oasys.controller.activiti.ActivitiController");
                    Method method = clazz.getMethod("bukashenqing", HttpSession.class, Buka.class, String.class, String.class, String.class, MultipartFile[].class, String.class, HttpServletRequest.class);
                    method.invoke(clazz.newInstance(),session,null,null,processInstanceId,"一键同意",null,task.getId(),req);

                }else if(processName.equals("事假申请") || processName.equals("年假申请") || processName.equals("病假申请") || processName.equals("特别假申请")
                        || processName.equals("总裁室事假申请") || processName.equals("总裁室特别假申请") || processName.equals("总裁室年假申请") || processName.equals("总裁室病假申请") ){
                    Class clazz = Class.forName("cn.gson.oasys.controller.activiti.LeaveController");
                    /*Method method = clazz.getMethod("saveLeave", HttpSession.class, Buka.class, String.class, String.class, String.class, MultipartFile[].class, String        .class, HttpServletRequest.class);
                    method.invoke(clazz.newInstance(),session,null,"",null,"列表同意",null,task.getId(),req);*/
                    Leave leave = leaveDao.findbyProcessid(Long.parseLong(processInstanceId)).get(0);
                    Method method = clazz.getMethod("saveLeave", HttpSession.class, HttpServletRequest.class, String.class, MultipartFile[].class, String.class, Leave.class, BindingResult.class, String.class, Model.class);
                    Object obj = method.invoke(clazz.newInstance(),session,req,"一键同意",null,processInstanceId,leave,null,task.getId(),model);
                    //System.out.println(obj);

                }else {
                    processEngine.getTaskService().addComment(taskIds[i], processInstanceId, "一键同意");
                    //执行通过流程
                    processEngine.getTaskService()//与正在执行的任务管理相关的Service
                            .complete(taskIds[i]);
                }
            }
            model.addAttribute("success","操作成功");
        }catch (Exception e){
            model.addAttribute("error", "发生异常，请联系管理员");
        }



        return "/myactiviti";
    }



    @RequestMapping("noPassProcess")
    public String noPassProcess(Model model, @RequestParam(value = "taskId", defaultValue = "0") String taskId){

        try{
            Task task = aUtils.getTaskByTaskId(taskId);
            String processInstanceId = aUtils.getProcessInstanceByTask(task).getProcessInstanceId();
            processEngine.getTaskService().addComment(taskId,processInstanceId,"列表拒绝");
            aUtils.distoryProcess(processInstanceId,processEngine);
            model.addAttribute("success","操作成功");
        }catch (Exception e){
            model.addAttribute("error", "发生异常，请联系管理员");
        }

        return "myactiviti";
    }


    /**
     * 显示我的申请历史记录。
     * @param model
     * @return
     */
    @RequestMapping("kaoqinHistory")
    public String kaoqinHistory(Model model,HttpSession session,
                                @RequestParam(value="processSelect",required=false)String processSelect,
                                @RequestParam(value="datebegin",required=false)String datebegin,
                                @RequestParam(value="dateend",required=false)String dateend,
                                @RequestParam(value="deptSelect",required=false)String deptSelect,
                                @RequestParam(value="sender",required=false)String sender) throws ParseException {

        List<String> processList = new ArrayList<>();
        processList.add("-请选择-");
        List<String> deptList = dDao.findnames();
        deptList.add(0,"-请选择-");


        User user = UserUtils.getUser(session);
        List<String> keyList = new ArrayList<>();keyList.add("补卡申请");keyList.add("事假申请");keyList.add("特别假申请");keyList.add("年假申请");keyList.add("病假申请");

        HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.
                getHistoryService().createHistoricProcessInstanceQuery();

        historicProcessInstanceQuery = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc().processDefinitionKeyIn(keyList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(datebegin != null && datebegin != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedAfter(sdf.parse(datebegin));
        }
        //只选择在给定日期之前启动的历史进程实例。
        if(dateend != null && dateend != ""){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedBefore(sdf.parse(dateend));
        }
        //只选择具有给定键的流程定义定义的历史流程实例。
        if(processSelect != null  && !("-请选择-").equals(processSelect)){
            historicProcessInstanceQuery = historicProcessInstanceQuery.processDefinitionKey(processSelect);
        }
        //只选择具有给发起人流程定义定义的历史流程实例。
        if(sender != null  && !("").equals(sender)){
            historicProcessInstanceQuery = historicProcessInstanceQuery.startedBy(sender);
        }
 /*  //只选择具有给部门的流程定义定义的历史流程实例。
        if(deptSelect != null  && !("-请选择-").equals(deptSelect)){
        }*/

       List<HistoricProcessInstance> historicProcessInstanceList = historicProcessInstanceQuery.list();
        for(HistoricProcessInstance q : historicProcessInstanceList){
            processList.add(q.getProcessDefinitionId().split(":")[0]);          //流程名称下拉列表
        }


        Iterable<SystemStatusList>  statusname = sdao.findByStatusModel("aoa_task_list1");
        List<ProcessList> prolist = new ArrayList<ProcessList>();
        for(HistoricProcessInstance h : historicProcessInstanceList){
            String processInstanceId = h.getId();
            List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
            HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size()-1);

            ProcessList p  = new ProcessList();
            p.setActivitiprocessid(processInstanceId);               //临时用来存流程ID
            p.setTypeNmae(h.getProcessDefinitionId().split(":")[0]);                                //设置任务名称
            p.setApplyTime(h.getStartTime());                         //设置开始时间
            p.setShenuser(h.getStartUserId());
            RuntimeService runtimeService = processEngine.getRuntimeService();
            ProcessInstanceQuery createProcessInstanceQuery = runtimeService.createProcessInstanceQuery();
            ProcessInstanceQuery processInstance = createProcessInstanceQuery.processInstanceId(processInstanceId);
            ProcessInstance singleResult = processInstance.singleResult();
            boolean passfalg = false;
            for(HistoricActivityInstance hai : historicActivityInstanceList ){
                if(hai.getActivityName().equals("End")){
                    passfalg = true;
                    break;
                }
            }
            if(singleResult==null){
                if(passfalg == true){
                    p.setStatusId(36L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }
            //设置状态
            /*if(h.getEndTime() != null){
                ;p.setStatusId(7L)
                if(historicActivityInstance.getActivityName().equals("End")){
                    p.setStatusId(7L);                              //已完成
                }else{
                    p.setStatusId(30L);                             //已取消
                }
            }else{
                p.setStatusId(5L);                                  //进行中
            }*/
            prolist.add(p);


        }
        List<String> listTemp = new ArrayList<String>();
        for(int i=0;i<processList.size();i++){
            if(!listTemp.contains(processList.get(i))){
                listTemp.add(processList.get(i));
            }
        }
        model.addAttribute("datebegin",datebegin);
        model.addAttribute("dateend",dateend);
        model.addAttribute("processSelect",processSelect);
        model.addAttribute("processList",listTemp);
        model.addAttribute("url","kaoqinHistory");
        model.addAttribute("statusname",statusname);
        model.addAttribute("prolist",prolist);
        model.addAttribute("sender",sender);
        /*model.addAttribute("deptSelect",deptSelect);
        model.addAttribute("deptList",deptList);*/
        model.addAttribute("title","补卡请假查看");
        return "activiti/kaoqinHistory";
    }






    /**
     * 格式化时间
     * @param geshi
     * @param date
     * @return
     */
    private String DateFormat(String geshi , Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(geshi);
        return sdf.format(date);
    }

    /**
     * 根据角色查找第一个人
     */
    private String findByRole(String roleName){

        String name = uDao.findUsersByRoleName(roleName).get(0).getRealName();
        return name;
    }


    /**
     * 自动通过
     */
    private void AutoComplete(String processId,User user,String processInstanceId){
        List<Task> taskList = aUtils.queryCurretUserTaskByAssignerr(user.getRealName());
        boolean flag = true;
        for(Task task : taskList){
            String taskId = task.getId();
            if(flag){

            }


        }



    }

}
