package cn.gson.oasys.controller.activiti;

import cn.gson.oasys.Utils.ActivitiUtils;
import cn.gson.oasys.Utils.UserUtils;
import cn.gson.oasys.activiti.entity.Expense;
import cn.gson.oasys.activiti.entity.Leave;
import cn.gson.oasys.controller.ComController;
import cn.gson.oasys.controller.inform.InformManageController;
import cn.gson.oasys.model.dao.activitidao.ExpenseDao;
import cn.gson.oasys.model.dao.processdao.SubjectDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.process.Bursement;
import cn.gson.oasys.model.entity.process.DetailsBurse;
import cn.gson.oasys.model.entity.process.ProcessList;
import cn.gson.oasys.model.entity.process.Subject;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.process.ProcessService;

import com.alibaba.fastjson.JSONObject;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ExpenseController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TypeDao tydao;

    @Autowired
    private ExpenseDao expenseDao;

    @Autowired
    private SubjectDao sudao;

    @Autowired
    private ProcessService proservice;

    @Autowired
    private ComController comController;
    @Autowired
    private ProcessPushMailController processPushMailController;


    ActivitiUtils aUtils = new ActivitiUtils();
    //流程引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


    //报销申请
    @RequestMapping("baoxiao")
    public String leave(Model model, @SessionAttribute("userId") Long userId, HttpServletRequest request,
                        @RequestParam(value = "page", defaultValue = "0") int page,
                        @RequestParam(value = "size", defaultValue = "10") int size){
        //查找类型
        //List<SystemTypeList> overtype=tydao.findByTypeModel("请假");
        User user = userDao.getOne(userId);
        String dept = user.getDept().getDeptName();
        String leaderName = "";
        if(user.getFatherId() != null && user.getFatherId() != 0) {
            User leader = userDao.findByUserId(user.getFatherId()).get(0);
            leaderName = leader.getRealName();
        }
        //proservice.index6(model, userId, page, size);
        //model.addAttribute("overtype", overtype);
        model.addAttribute("dept",dept);
        model.addAttribute("depter",leaderName);


        //
        //查找类型
        List<SystemTypeList> uplist=tydao.findByTypeModel("支付方式");
        //查找费用科目生成树
        List<Subject> second=sudao.findByParentId(1L);
        List<Subject> sublist=sudao.findByParentIdNot(1L);
        proservice.index6(model, userId, page, size);

        model.addAttribute("second", second);
        model.addAttribute("sublist", sublist);
        model.addAttribute("uplist", uplist);


        return "activiti/Expense/bursement";
    }

    /**
     * 费用表单接收
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @RequestMapping("expense")
    public String expense(HttpSession session , HttpServletRequest req, @RequestParam("filePath")MultipartFile[] filePath,
                          @RequestParam(value = "option",defaultValue="") String option ,
                          @RequestParam(value = "processInstanceId",defaultValue="") String processInstanceId ,
                          @RequestParam("fileRadio")String fileRadio,
                          @Valid Expense bu, BindingResult br,
                        @SessionAttribute("userId") Long userId) throws IllegalStateException, IOException{
        //
        //,@RequestParam("fileRadios")String fileRadio
        User lu=userDao.findOne(userId);//申请人

        Long roleid=lu.getRole().getRoleId();//申请人角色id
        Long fatherid=lu.getFatherId();//申请人父id
        //Long userid=reuser.getUserId();//审核人userid
        User user = UserUtils.getUser(session);

        String processname = "费用报销";
        String taskid = req.getParameter("taskid");
        Map<String, Object> variables = new HashMap<String, Object>();
        //setVariables(session,variables,processname,bu);
        //传入时间
        Timestamp createTime = new Timestamp(new Date().getTime());
        bu.setFeeDate(createTime);
//是否推送邮件
        boolean flagpush = processPushMailController.ispush();
        if(taskid == null){
            //文件上传
            for (int i=0;i<filePath.length;i++){
                    String filename = filePath[i].getOriginalFilename();
                    if (true){
                if (!filename.equals("")) {
                    try {
                        bu.setFileRadio(0);
                        byte[] fileb = filePath[i].getBytes();
                        //String cp11111=req.getSession().getServletContext().getRealPath("/fileSoft/");
                        String cp11111 = "filefeePath/";
                        Date d = new Date();
                        Date d1 = bu.getFeeDate();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat sdftime = new SimpleDateFormat("HHmmss");
                        String dateNowStr = sdf.format(d1);
                        String datetimeNowStr = sdftime.format(d1);
                        String pathstr = cp11111 + dateNowStr;


                        String type = filename.substring(filename.lastIndexOf(".") + 1, filename.length());//格式
                        String fname = filename.substring(0, filename.lastIndexOf("."));//文件名
                        String picName = bu.getFeeName() + "_" + fname + "_" + datetimeNowStr + "." + type;//图片名称

                        comController.uploadFilecom(fileb, pathstr, picName);
                        bu.setFeeFile( pathstr + "/" + picName + ";");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    if (fileRadio.equals("选中")){
                      //if(false){
                    bu.setFileRadio(1);

                    }else {
                        return "common/procefile";
                    }
                }
                    }
            }

                //1、设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
                setVariables(session, variables, processname, bu);        //session 获取当前登陆人信息  processName 流程名称
            if (true) {

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
                bu.setProcessid(Long.parseLong(pi.getId()));
                bu.setSenderId(user.getUserId());
                bu.setSenderName(user.getRealName());

                expenseDao.save(bu);

                //推送
                String sendname1 = bu.getDept() +bu.getSenderName() + processname;
                processPushMailController.yespush(flagpush,task,processInstanceId,lu,sendname1,createTime);
            }

        }else{
            Task task = aUtils.getTaskByTaskId(taskid);
            processEngine.getTaskService().addComment(taskid,processInstanceId,option);
            processEngine.getTaskService()//与正在执行的任务管理相关的Service
                    .complete(taskid,variables);

            //推送流程信息到邮箱
            String sendname1 = bu.getDept()+bu.getSenderName() + processname;
            processPushMailController.yespush(flagpush,task,processInstanceId,lu,sendname1,createTime);
            //return "common/proce";
            return "myactiviti";
        }
        return "activitiHistory";
    }

    /*
     * 设置流程参数信息
     *
     * */
    private void setVariables(HttpSession session , Map<String, Object> variables, String processname,Expense eve){

        User user = UserUtils.getUser(session);
        User leader = UserUtils.getLeader(session);

        //设置对应角色（发起者 sender 、部门领导 deptleader 、中心总经理 deptmanager、管理部 guanlibu），设置分支（是否为营销中心 message）
        String deptFatherName = user.getDept().getDeptFatherId().getDeptFatherName();

        List<User> managerList = userDao.findUsersByRoleName(deptFatherName+"_中心总经理");
        String deptmanager = managerList.get(0).getRealName();
        BigDecimal fee = eve.getFeeFee();
        BigDecimal fee1 = new BigDecimal(Integer.toString(5000));
        int feeflag =0;
        int ff = fee.compareTo(fee1);
        if (ff >= 0){
            feeflag = 1;
        }
            if(processname.equals("费用报销")) {
                /*申请人-
                        财务部审核-
                        部门负责人审核
                中心总经理审核-
                        副总裁审核-
                        总裁审核（>5000元）-
                        出纳付款*/
                String caiwubu = userDao.findUsersByRoleName("财务部报销审核人").get(0).getRealName();
                String boss = userDao.findUsersByRoleName("副总裁").get(0).getRealName();
                String bigboss = userDao.findUsersByRoleName("总裁").get(0).getRealName();
                String outer = userDao.findUsersByRoleName("出纳").get(0).getRealName();

                variables.put("sender", user.getRealName());
                variables.put("depter", leader.getRealName());
                variables.put("deptmanager", deptmanager);
                variables.put("caiwubuer", caiwubu);
                variables.put("boss", boss);
                variables.put("bigboss", bigboss);
                variables.put("feeflag", feeflag);
                variables.put("outer", outer);


            }

    }


}
