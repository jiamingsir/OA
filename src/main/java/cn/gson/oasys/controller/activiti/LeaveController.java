package cn.gson.oasys.controller.activiti;

import cn.gson.oasys.Utils.ActivitiUtils;
import cn.gson.oasys.Utils.UserUtils;
import cn.gson.oasys.activiti.entity.Leave;
import cn.gson.oasys.controller.ComController;
import cn.gson.oasys.controller.atdRecord.AttendanceController;
import cn.gson.oasys.model.dao.activitidao.LeaveDao;
import cn.gson.oasys.model.dao.atdrecorddao.AtdrecordDao;
import cn.gson.oasys.model.dao.atdrecorddao.VacationDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.HolidaysetDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.user.Holidayset;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.process.ProcessService;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



@Controller
@RequestMapping("/")
public class LeaveController {
    public static LeaveController leaveController;


    @Autowired
    private UserDao userDao;

    @Autowired
    private LeaveDao leaveDao;

    @Autowired
    private TypeDao tydao;

    @Autowired
    private AtdrecordDao atdDao;

    @Autowired
    private VacationDao vacationDao;
    @Autowired
    private HolidaysetDao holidaysetDao;


    @Autowired
    private ProcessService proservice;

    @Autowired
    private ComController comController;

    //@Autowired
     ProcessPushMailController processPushMailController =new ProcessPushMailController();

    @Autowired
     AttendanceController attendanceController;
    /**
     * 初始化当前类
     */
    @PostConstruct
    public void init() {
        leaveController = this;
    }




    ActivitiUtils aUtils = new ActivitiUtils();
    //流程引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    //请假申请
    @RequestMapping("leave")
    public String leave(Model model, @SessionAttribute("userId") Long userId, HttpServletRequest request,
                          @RequestParam(value = "page", defaultValue = "0") int page,
                          @RequestParam(value = "size", defaultValue = "10") int size){
        //查找类型
        List<SystemTypeList> overtypes=tydao.findByTypeModel("请假");
        User user = userDao.getOne(userId);
         String dept = user.getDept().getDeptName();
         User usersjj = userDao.findByUserName("shaojingjing");
         String deptsjj = "";
         if (usersjj.getUserId()!=0){
             deptsjj = usersjj.getDept().getDeptName();
         }
        boolean flag=false;
        if (dept.equals("总裁室") || dept.equals("CMO") || (dept.equals("财务部") && deptsjj.equals("财务部"))){
            flag = true;
        }
        List<SystemTypeList> overtype=new ArrayList<>();
        for (int i=0;i<overtypes.size();i++){
           String typename = overtypes.get(i).getTypeName();
            if (flag){
                if (typename.contains("总裁")){
                    overtype.add(overtypes.get(i));
                }
            }else {
                if (!typename.contains("总裁")){
                    overtype.add(overtypes.get(i));
                }
            }
        }
        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = userDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        proservice.index6(model, userId, page, size);
        model.addAttribute("overtype", overtype);
        model.addAttribute("dept",dept);
        model.addAttribute("flag",flag);
        model.addAttribute("depter",leaderName);
        return "activiti/Leave/leavetable";
    }
    /**
     * 请假申请接收
     * @param model
     * @param session
     * @param //request
     * @param //page
     * @param //size
     * @return
     */
    @RequestMapping("saveLeave")
    public String saveLeave(HttpSession session ,  HttpServletRequest req,
                            @RequestParam(value = "option",defaultValue="") String option ,
                            @RequestParam("filePath")MultipartFile[] filePath,
                            @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                            @Valid Leave eve, BindingResult br,
                            @RequestParam(value = "taskid")String taskid,
                            Model model) throws IllegalStateException,
            IOException, ParseException {
        //@RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
        //@RequestParam("filePath")MultipartFile filePath,

        User lu = UserUtils.getUser(session);

        User user = UserUtils.getUser(session);

        String processname = eve.getType();
        //String taskid = req.getParameter("taskid");
        Map<String, Object> variables = new HashMap<String, Object>();
        Timestamp createTime = new Timestamp(new Date().getTime());
        eve.setApplydate(createTime);
        //是否推送邮件
        boolean flagpush = processPushMailController.ispush();

        if (taskid == null || taskid.equals("")) {
            int leavedays = 0;
            String[] dayd = eve.getLeaveDays().split("\\.");
            if(dayd.length > 1){
                if (eve.getType().contains("事假") || eve.getType().contains("总裁办事假")){

                }else {
                    model.addAttribute("error", "除事假外，其它类假期天数单位为 1 天,请重新选择请假时间！");
                    return "common/proce";
                }
            }else {
                double leavedaysdou = Double.valueOf(eve.getLeaveDays());
                leavedays = (int)Math.ceil(leavedaysdou);
            }


            List<Holidayset> holidaysetlist = holidaysetDao.findByUserid(user.getUserId());
            if (holidaysetlist.size()>0 || eve.getType().contains("事假") || eve.getType().contains("总裁办事假")) {
                if (holidaysetlist.size()>0) {
                    Holidayset holidayset = holidaysetlist.get(0);

                    if (eve.getType().contains("年假") || eve.getType().contains("总裁办年假")) {
                        //processname = "年假申请";
                        if (leavedays > holidayset.getAyudays()) {
                            model.addAttribute("error", "假期天数不够，您还有" + holidayset.getAyudays() + "天可用！");
                            return "common/proce";
                        }
                    } else if (eve.getType().contains("特别假") || eve.getType().contains("总裁办特别假")) {
                        //processname = "年假申请";
                        if (leavedays > holidayset.getByudays()) {
                            model.addAttribute("error", "假期天数不够，您还有" + holidayset.getByudays() + "天可用！");
                            return "common/proce";
                        }
                    }
                }
            }else {
                model.addAttribute("error", "请联系人事部，确认假期情况！");
                return "common/proce";
            }
            //文件上传
            if (true) {
                String path = "";
                for (int i = 0; i < filePath.length; i++) {
                    String filename = filePath[i].getOriginalFilename();
                    if (!filename.equals("")) {
                        try {
                            byte[] fileb = filePath[i].getBytes();
                            //String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
                            String cp11111 = "fileLeavePath/";
                            Date d = new Date();
                            Date d1 = eve.getApplydate();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
                            String dateNowStr = sdf.format(d1);
                            String datetimeNowStr = sdftime.format(d1);
                            String pathstr = cp11111 + dateNowStr;


                            String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//格式
                            String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
                            String picName = fname + "_" + datetimeNowStr + "." + type;//图片名称

                            comController.uploadFilecom(fileb, pathstr, picName);
                            path = path + pathstr + "/" + picName + ";";
                          /*  eve.setFileFile();*/
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //if (fileRadio.equals("选中")){
                        if (true) {
                            //bu.setFileRadio(1);

                        } else {
                            return "common/procefile";
                        }
                    }
                }
                eve.setFileFile(path);


            }
            //隶属部门
            String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
            String deptName = user.getDept().getDeptName();
            if (processname.equals("总裁办请假") || processname.equals("总裁室事假") || processname.equals("总裁室年假") || processname.equals("总裁室病假") || processname.equals("总裁室特别假")){
                /*if (deptName.equals("总裁室")){

                }else {
                    model.addAttribute("error","请选择正确的请假类型！");
                    return "common/proce";
                }*/

            }

            //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
            setVariables(session, variables, processname, eve);        //session 获取当前登陆人信息  processName 流程名称
            String okso =variables.get("oks").toString();
            int oks =Integer.parseInt(okso);
            if (oks != 1) {
                variables.remove("oks");
                if (true) {
                    if (eve.getType().equals("事假")) {
                        processname = "事假申请";
                    } else if (eve.getType().equals("年假")) {
                        processname = "年假申请";
                    }else if (eve.getType().equals("病假")) {
                        processname = "病假申请";
                    }else if (eve.getType().equals("特别假")) {
                        processname = "特别假申请";
                    }else if (eve.getType().equals("总裁室事假")) {
                        processname = "总裁室事假申请";
                    } else if (eve.getType().equals("总裁室年假")) {
                        processname = "总裁室年假申请";
                    }else if (eve.getType().equals("总裁室病假")) {
                        processname = "总裁室病假申请";
                    }else if (eve.getType().equals("总裁室特别假")) {
                        processname = "总裁室特别假申请";
                    }/*else if (eve.getType().equals("总裁办请假")) {
                        processname = "总裁办请假";
                    }*/

                    //2、开始任务实例
                    IdentityService identityService = processEngine.getIdentityService();
                    identityService.setAuthenticatedUserId(user.getRealName());
                    ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                            .startProcessInstanceByKey(processname, variables);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

                    //3、执行流程第一步~
                    TaskService taskService = processEngine.getTaskService();
                    Task task = this.processEngine.getTaskService().createTaskQuery().processInstanceId(pi.getId()).active().singleResult();
                    taskService.complete(task.getId(), variables);

                    //4、将流程信息添加到实体类中
                    eve.setProcessid(Long.parseLong(pi.getId()));
                    eve.setSenderId(user.getUserId());
                    eve.setSenderName(user.getRealName());
                    eve.setIsend(0);
                    //eve.setApplydate(new Timestamp(new Date().getTime()));

                    leaveDao.save(eve);

                    //推送
                    String sendname1 = eve.getDept() +eve.getSenderName() + processname;
                    processPushMailController.yespush(flagpush,task,processInstanceId,lu,sendname1,createTime);
                }

            } else {
                return "common/proceleave";
            }

        }else {
            Holidayset holidayset = null;
            int ayuday = 0;
            int byuday = 0;
            if (eve.getType().contains("年假") || eve.getType().contains("特别假")) {
                List<Holidayset> holidaysetListbefore = leaveController.holidaysetDao.findByUserid(eve.getSenderId());
                Holidayset holidaysetend = new Holidayset();
                if (holidaysetListbefore.size()>0) {
                     holidayset = holidaysetListbefore.get(0);
                    if (eve.getType().contains("年假")) {
                        int ayudayold = holidayset.getAyudays();
                        int qing = Integer.parseInt(eve.getLeaveDays());
                         ayuday = ayudayold - qing;
                        if (ayuday>=0) {
                            //holidayset.setAyudays(ayuday);
                        }else {
                            model.addAttribute("error","请假天数超额");
                            return "common/proce";
                        }
                    } else if (eve.getType().contains("特别假")) {
                        int byudayold = holidayset.getByudays();
                        int qing = Integer.parseInt(eve.getLeaveDays());
                         byuday = byudayold - qing;
                        if (byuday>=0) {
                            //holidayset.setByudays(byuday);
                        }else {
                            model.addAttribute("error","请假天数超额");
                            return "common/proce";
                        }
                    }
                    holidaysetend = holidayset;

                }else {
                    model.addAttribute("error", "请联系人事部，确认假期情况！");
                    return "common/proce";
                }
            }
            //if (true) {
                Task task = aUtils.getTaskByTaskId(taskid);
                 //processInstanceId = aUtils.getProcessInstanceByTask(task).getProcessInstanceId();
                processEngine.getTaskService().addComment(taskid, processInstanceId, option);
                processEngine.getTaskService()//与正在执行的任务管理相关的Service
                        .complete(taskid, variables);

            //推送流程信息到邮箱
            String sendname1 = eve.getDept()+eve.getSenderName() + processname;
            processPushMailController.yespush(flagpush,task,processInstanceId,lu,sendname1,createTime);


                //如果有End，则判断是结束，写业务逻辑
                List<HistoricActivityInstance> historicActivityInstanceList = aUtils.getHistoryActInstanceList(processInstanceId);
                HistoricActivityInstance historicActivityInstance = historicActivityInstanceList.get(historicActivityInstanceList.size() - 1);
            for (int i = 0; i < historicActivityInstanceList.size(); i++) {
                String assignee = historicActivityInstanceList.get(i).getAssignee();
                System.out.println("assignee:"+ assignee);
            }
            /*    HistoricActivityInstance historicActivityInstancedang = historicActivityInstanceList.get(historicActivityInstanceList.size() - 2);
            if (historicActivityInstance.getAssignee().equals(historicActivityInstancedang.getAssignee())){
                //下一审批人相同时
                System.out.println("相同");

            }*/
                //}
            int isEndz = 0;
            for (int i = 0; i < historicActivityInstanceList.size(); i++) {
                String activityName = historicActivityInstanceList.get(i).getActivityName();
                if (activityName.equals("End")){
                    isEndz+=1;
                }
            }
            //if(historicActivityInstance.getActivityName().equals("End")) {      //已完成
            if(isEndz > 0) {      //已完成
            //if (true) {
                if (true) {
                    if (eve.getType().equals("事假")) {
                        processname = "事假申请";
                    } else if (eve.getType().equals("年假")) {
                        processname = "年假申请";
                    } else if (eve.getType().equals("病假")) {
                        processname = "病假申请";
                    }  else if (eve.getType().equals("特别假")) {
                        processname = "特别假申请";
                    } else if (eve.getType().equals("总裁室事假")) {
                        processname = "总裁室事假申请";
                    } else if (eve.getType().equals("总裁室年假")) {
                        processname = "总裁室年假申请";
                    } else if (eve.getType().equals("总裁室病假")) {
                        processname = "总裁室病假申请";
                    }  else if (eve.getType().equals("特别假")) {
                        processname = "总裁室特别假申请";
                    }
                    /*if (eve.getType().equals("总裁办请假")) {
                        processname = "总裁办请假";
                    }*/

                    //List<String> timeArr = isInDate(eve.getBeginDate(), eve.getEndDate());
                    List<String> timeArr = leaveController.quholiday(eve.getBeginDate(), eve.getEndDate());


                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat sdf2 = new SimpleDateFormat("HH:ss");
                    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    List<Atdrecord> atdrecords = new ArrayList<Atdrecord>();
                    for (int i = 0; i < timeArr.size(); i++) {
                        Atdrecord atdrecord = new Atdrecord();
                        if (processname.equals("事假申请")) {
                            atdrecord.setFlag(2);           //设置  1:补卡申请
                            atdrecord.setRemark("事假申请");         //设置备注  补卡申请
                        } else if (processname.equals("年假申请")) {
                            atdrecord.setFlag(3);
                            atdrecord.setRemark("年假申请");
                        } else if (processname.equals("特别假申请")) {
                            atdrecord.setFlag(4);
                            atdrecord.setRemark("特别假申请");
                        } else if (processname.equals("总裁室事假申请")) {
                            atdrecord.setFlag(5);
                            atdrecord.setRemark("总裁室事假申请");
                        } else if (processname.equals("病假申请")) {
                            atdrecord.setFlag(6);
                            atdrecord.setRemark("病假申请");
                        }else if (processname.equals("总裁室病假申请")) {
                            atdrecord.setFlag(7);
                            atdrecord.setRemark("总裁室病假申请");
                        }else if (processname.equals("总裁室年假申请")) {
                            atdrecord.setFlag(8);
                            atdrecord.setRemark("总裁室年假申请");
                        }else if (processname.equals("总裁室特别假申请")) {
                            atdrecord.setFlag(9);
                            atdrecord.setRemark("总裁室特别假申请");
                        }
                        String recDate = "";
                        String recTime = "";
                        Date time = sdf3.parse(timeArr.get(i));
                        recDate = sdf1.format(time);
                        recTime = sdf2.format(time);


                        //String recDate = DateFormat("yyyy-MM-dd" , buka.getNodate());
                        atdrecord.setRecdate(recDate);                               //设置补卡日期
                        //String recTime = DateFormat("HH:ss" , buka.getNodate());
                        atdrecord.setRectime(recTime);                               //设置申请时间
                        //设置操作日期
                        Date nowDate = eve.getApplydate();
                        if (nowDate != null) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            atdrecord.setOperdate(sdf.format(nowDate));
                        }
                        //设置胸卡卡号
                        Long senderid = eve.getSenderId();
                        User sender = leaveController.userDao.findByUserId(senderid).get(0);
                        String[] ids = sender.getCardId().split(",");
                        atdrecord.setCardno(ids[ids.length - 1]);          //插入胸卡ID
                        atdrecord.setChecker(UserUtils.getUser(session).getRealName());               //设置操作人
                        atdrecords.add(atdrecord);

                    }
                    //List<Atdrecord> atdrecords2 =atdrecords;
                    leaveController.atdDao.save(atdrecords);
                }
                //改变基础信息表字段
                eve.setIsend(1);
                if (eve.getFileFile() == null){
                    eve.setFileFile(null);
                }else if (eve.getFileFile().equals("")){
                    eve.setFileFile(null);
                }

                eve.setProcessid(Long.parseLong(processInstanceId));
                leaveController.leaveDao.save(eve);

                if (holidayset != null){
                    if (eve.getType().contains("年假")){
                        holidayset.setAyudays(ayuday);
                    }else if (eve.getType().contains("特别假")){
                        holidayset.setByudays(byuday);
                    }
                    leaveController.holidaysetDao.save(holidayset);
                }



            }
        }

        //return "myactiviti";
        return "activitiHistory";
    }
@RequestMapping("quholidaysum")
@ResponseBody
     public double quholidaysum(String begin, String end){


    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date begindata = null;
    Date enddata = null;
    try {
        begindata = sdf.parse(begin);
        enddata = sdf.parse(end);
    } catch (ParseException e) {
        e.printStackTrace();
    }

    List<String> dateList = quholiday( begindata, enddata);
    double result = (double) dateList.size()/(double)2;
        return result;
     }




    public List<String> quholiday(Date beginDate,Date endDate){
        List<String> deteListqu= new ArrayList<>();
        //全部天数没去假期
        List<String> dateList = isInDate( beginDate, endDate);
        //周末
        List<String> dateListzm = new ArrayList<>();//dateList;
        for (int i = 0; i < dateList.size(); i++) {

        }
        //假期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdfnyr = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfsfm = new SimpleDateFormat("HH:mm:ss");
        String begin = sdf.format(beginDate);
        String end = sdf.format(endDate);
        //List<Vacation> vacationList = vacationDao.findByTimes(begin,end);

        for (int i = 0; i < dateList.size(); i++) {
            String dt1 = dateList.get(i);
            Date dtdate =null;
            try {
                dtdate = sdf.parse(dt1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar caldt1 = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            caldt1.setTime(dtdate);



            String time = sdfsfm.format(dtdate);
            int isWeekends = isWeekend(dtdate,time);
            if (isWeekends == 0){
                deteListqu.add(dt1);
            }

        }

        return deteListqu;
    }

    //判断是否为节假日
    public int isWeekend(Date date ,String time)  {

        boolean isWeekend = false;
        boolean isVacation = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断周末
        isWeekend = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        //判断调休
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date) + " " + time;
        List<Vacation> vacationList = vacationDao.findByDate(dateString);
        if(vacationList.size() > 0){
            Vacation vacation = vacationList.get(0);
            //类型为1则为假期
            if(vacation.getType().equals("1")){
                isWeekend = false;
            }
            //类型为0则为补班（上班）
            if(vacation.getType().equals("0")){
                isWeekend = true;
            }

        }

        return isWeekend ?1:0;
    }

    public List<String> isInDate(Date beginDate,Date endDate){
        List<String> timearr = new ArrayList<>();
        Map map = new HashMap();
        //开始时间
        //Date beginDate = new Date();
        Date begin =null;
        //结束时间
        //Date endDate = new Date();
        Date end = null;
        //未打卡时间
        SimpleDateFormat formatday = new SimpleDateFormat("dd");
        SimpleDateFormat formath = new SimpleDateFormat("H");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        SimpleDateFormat sdfymd = new SimpleDateFormat("yyyy-MM-dd");

        /*List<String> lDate = new ArrayList<String>();
        lDate.add("2019-12-05 14:21:44");
        lDate.add("2019-12-06 14:21:44");
        lDate.add("2019-12-07 11:21:44");*/
        List<String> lDate = findDates(beginDate,endDate);


        Date aa =null;
        try {
            aa = sdf.parse(lDate.get(0));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String a = sdfymd.format(aa);

        Date firsta = null;
        Date lasta =null;
        Date centa =null;
        String monhms=" 09:00:00";
        String evehms=" 17:00:00";
        for (int i = 0;i<lDate.size();i++){
            try {

                String ymd = "";
                if (i==0 ) {
                    firsta = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(firsta);
                    String day = formatday.format(firsta);
                    String house = formath.format(firsta);

                    if (Integer.parseInt(house) <=12) {

                        String monymdhms = ymd + monhms;
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, monymdhms);
                        map.put(day + 2, eveymdhms);
                        timearr.add(monymdhms);
                        timearr.add(eveymdhms);
                    } else {
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, eveymdhms);

                        timearr.add(eveymdhms);
                    }
                }else if(i==lDate.size()-1){
                    lasta = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(lasta);
                    String day = formatday.format(lasta);
                    String house = formath.format(lasta);
                    if (Integer.parseInt(house) > 12) {
                        String monymdhms = ymd + monhms;
                        String eveymdhms = ymd + evehms;
                        map.put(day + 1, monymdhms);
                        map.put(day + 2, eveymdhms);
                        timearr.add(monymdhms);
                        timearr.add(eveymdhms);
                    } else {
                        String monymdhms = ymd + monhms;
                        map.put(day + 1, monymdhms);
                        timearr.add(monymdhms);

                    }
                } else {
                    centa = sdf.parse(lDate.get(i));
                    ymd = sdfymd.format(centa);
                    String day = formatday.format(centa);
                    String house = formath.format(centa);
                    String monymdhms = ymd + monhms;
                    String eveymdhms = ymd + evehms;
                    map.put(day + 1, monymdhms);
                    map.put(day + 2, eveymdhms);
                    timearr.add(monymdhms);
                    timearr.add(eveymdhms);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(Object m:map.values()){
            System.out.println("xunhuan:"+m);
        }

        return lDate;


    }

    public List<String> findDates(Date dBegin, Date dEnd){

        List<String> lDate = new ArrayList<String>();
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");


        Calendar calBegin = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        String isdangtian1 = sd1.format(dBegin);
        String isdangtian2 = sd1.format(dEnd);
        if(isdangtian1.equals(isdangtian2)){
            String sb = sd1.format(calBegin.getTime());
            String savaz = sb + " 12:00:00";
            String sava1w = sb + " 17:00:00";
            Date begind = null;
            Date begindw = null;
            try {
                begind = sd.parse(savaz);
                begindw = sd.parse(sava1w);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar eBegin = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            eBegin.setTime(begind);//12点
            Calendar eBeginw = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            eBeginw.setTime(begindw);//17点

            if (dBegin.before(eBegin.getTime())) {

                if(dEnd.before(eBegin.getTime())){
                    lDate.add(sd.format(dEnd));
                }else {
                    lDate.add(sd.format(dBegin));
                    lDate.add(sd.format(dEnd));
                }
                /*if (isdangtian1.equals(isdangtian2)) {
                    lDate.add(sd.format(dBegin));
                } else {
                    lDate.add(sd.format(dBegin));
                    lDate.add(sd.format(eBeginw.getTime()));
                }*/

            } else {
                //lDate.add(sd.format(dBegin));//开始时间
                lDate.add(sd.format(dEnd));//开始时间
            }
        }else {
            //开始一天
            String sb = sd1.format(calBegin.getTime());
            String savaz = sb + " 12:00:00";
            String sava1w = sb + " 17:00:00";
            Date begind = null;
            Date begindw = null;
            try {
                begind = sd.parse(savaz);
                begindw = sd.parse(sava1w);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar eBegin = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            eBegin.setTime(begind);
            Calendar eBeginw = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            eBeginw.setTime(begindw);

            if (dBegin.before(eBegin.getTime())) {
                //lDate.add(sd.format(calBegin.getTime()));


                if (isdangtian1.equals(isdangtian2)) {
                    lDate.add(sd.format(dBegin));
                } else {
                    lDate.add(sd.format(dBegin));
                    lDate.add(sd.format(eBeginw.getTime()));
                }

            } else {
                lDate.add(sd.format(dBegin));
            }

// 测试此日期是否在指定日期之后
            String endb = sd1.format(dEnd);
            String endbj = endb + " 00:00:00";
            Date endM = null;
            try {
                endM = sd.parse(endbj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String beginb = sd1.format(dBegin);
            String beginbj = beginb + " 24:00:00";
            Date beginM = null;
            try {
                beginM = sd.parse(beginbj);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar beginMm = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            beginMm.setTime(beginM);
            //while (endM.after(calBegin.getTime()))
            while (endM.after(beginMm.getTime())) {
// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                // calBegin.add(Calendar.DAY_OF_MONTH, 1);
                Date datemn = beginMm.getTime();
                String bm = sd1.format(datemn);
                String bmm = bm + " 09:00:00";
                String bma = bm + " 17:00:00";

                lDate.add(bmm);
                lDate.add(bma);
                beginMm.add(Calendar.DAY_OF_MONTH, 1);
            /*if (dEnd.after(calBegin.getTime())){
                calBegin.add(Calendar.DAY_OF_MONTH, -1);
                String s = sd1.format(calBegin.getTime());
                String sava1 = s+" 17:00:00";
                lDate.add(sava1);
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(sd.format(calBegin.getTime()));
                //

            }*/

            }
//最后一天
            String se = sd1.format(calEnd.getTime());
            String savaZ = se + " 12:00:00";
            String savaM = se + " 09:00:00";
            Date endd = null;
            try {
                endd = sd.parse(savaZ);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar eEnd = Calendar.getInstance();
// 使用给定的 Date 设置此 Calendar 的时间
            eEnd.setTime(endd);
            if (dEnd.before(eEnd.getTime())) {
                lDate.add(sd.format(calEnd.getTime()));

            } else {
                if (isdangtian1.equals(isdangtian2)) {
                    lDate.add(sd.format(calEnd.getTime()));
                } else {
                    lDate.add(savaM);
                    lDate.add(sd.format(calEnd.getTime()));
                }
            }
        }
        System.out.println("时间list："+lDate);
        return lDate;
    }




    /*
    * 设置流程参数信息
    *
    * */
    private void setVariables(HttpSession session , Map<String, Object> variables, String processname,Leave eve){

        User user = UserUtils.getUser(session);
        User leader = UserUtils.getLeader(session);
        if (eve.getDept().contains("无部门")){
            Long userId = (Long) session.getAttribute("userId");
            List<User> userList = userDao.findByUserId(userId);
            User user1 = userList.get(0);
            if (eve.getDept().contains("无部门1")){
                //zhangsisi
                //User user2 = userDao.findByUserName("shenzhiping");
                leader = userDao.findByUserName("shenzhiping");

            }else if (eve.getDept().contains("无部门2")){
                //gusongwei
                leader = userDao.findByUserName("huangyiming");

            }
        }
        String leaveDays = eve.getLeaveDays();
        //设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
        //String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();
        String deptName = user.getDept().getDeptName();

        List<User> managerList = userDao.findUsersByRoleName(deptName+"_中心总经理");
        String deptmanager = managerList.get(0).getRealName();
        boolean zongcai = deptName.equals("总裁室") || deptName.equals("CMO") || (deptName.equals("财务部") && leader.getUserName().equals("shaojingjing"));
        //天数判断
        double days = Double.valueOf(leaveDays);
        int day = daysyn(deptName, days, zongcai);

        //假期提前申请判断
         int oks = 0;
        //if (deptName.equals("总裁室") || deptName.equals("CMO") || (deptName.equals("财务部") && leader.getUserName().equals("shaojingjing"))){
        if (zongcai){
            Date begindate = eve.getBeginDate();
            Date datenew = new Date();
            Long dn = datenew.getTime();
            begindate.getTime();

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(begindate);
            if (day == 0) {
                /*int daycha = -3;
                calendar.add(Calendar.DATE, daycha);
                Date s = calendar.getTime();
                long dt = s.getTime();
                if (dn >= dt) {
                    oks = 1;
                }*/
            }else if (day == 1){
                if (!processname.equals("总裁室病假")) {
                    int daycha = -14;
                    calendar.add(Calendar.DATE, daycha);
                    Date s = calendar.getTime();
                    long dt = s.getTime();
                    if (dn >= dt) {
                        oks = 1;
                    }
                }
            }


        }



        if(processname.equals("事假")) {

            variables.put("sender", user.getRealName());
            variables.put("depter", leader.getRealName());
            if (user.getRealName().equals(leader.getRealName())){
                day = 1;
            }
            variables.put("deptmanager", deptmanager);
            variables.put("day", day);
            variables.put("oks", oks);
        }else if (processname.equals("年假")||processname.equals("特别假")||processname.equals("病假")){
            String renshibuer = userDao.findUsersByRoleName("人力资源部_副总监").get(0).getRealName();

            variables.put("sender", user.getRealName());
            variables.put("depter", leader.getRealName());
            if (user.getRealName().equals(leader.getRealName())){
                day = 1;
            }
            variables.put("deptmanager", deptmanager);
            variables.put("renshibuer", renshibuer);
            variables.put("day", day);
            variables.put("oks", oks);
        }else if (processname.equals("总裁办请假")){
            String boss = userDao.findUsersByRoleName("总裁").get(0).getRealName();
            String bigboss = userDao.findUsersByRoleName("董事长").get(0).getRealName();
            variables.put("sender", user.getRealName());
            variables.put("boss", boss);
            variables.put("bigboss", bigboss);
            variables.put("day", day);
            variables.put("oks", oks);
        }else if (processname.equals("总裁室事假")){
            String boss = userDao.findUsersByRoleName("总裁").get(0).getRealName();
            String bigboss = userDao.findUsersByRoleName("董事长").get(0).getRealName();
            variables.put("sender", user.getRealName());
            variables.put("boss", boss);
            variables.put("bigboss", bigboss);
            variables.put("day", day);
            variables.put("oks", oks);
        }else if (processname.equals("总裁室年假") || processname.equals("总裁室病假") || processname.equals("总裁室特别假") ){
            String boss = userDao.findUsersByRoleName("总裁").get(0).getRealName();
            String bigboss = userDao.findUsersByRoleName("董事长").get(0).getRealName();
            String renshibuer = userDao.findUsersByRoleName("人力资源部_副总监").get(0).getRealName();
            variables.put("sender", user.getRealName());
            variables.put("boss", boss);
            variables.put("bigboss", bigboss);
            variables.put("renshibuer", renshibuer);
            variables.put("day", day);
            variables.put("oks", oks);
        }
    }

    public int daysyn(String deptFatherName,double days,boolean zongcai){
        int day=0;

        //if (deptFatherName.equals("总裁室")|| deptFatherName.equals("CMO") || (deptFatherName.equals("财务部") && leader.getUserName().equals("shaojingjing"))){
        if (zongcai){
            if (days >=3 ){
                day = 1;
            }
        }else if (days > 1 ){
            day = 1;
        }
        return day;
    }


}
