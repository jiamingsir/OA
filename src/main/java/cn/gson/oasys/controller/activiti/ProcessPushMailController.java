package cn.gson.oasys.controller.activiti;

import cn.gson.oasys.Utils.ActivitiUtils;
import cn.gson.oasys.controller.mail.ExchangeClient;
import cn.gson.oasys.model.dao.maildao.InMailDao;
import cn.gson.oasys.model.dao.maildao.MailnumberDao;
import cn.gson.oasys.model.dao.maildao.MailreciverDao;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.mail.Inmaillist;
import cn.gson.oasys.model.entity.mail.Mailnumber;
import cn.gson.oasys.model.entity.mail.Mailreciver;
import cn.gson.oasys.model.entity.user.User;
import cn.gson.oasys.services.mail.MailServices;
import cn.gson.oasys.services.process.ProcessService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProcessPushMailController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailnumberDao mndao;
    @Autowired
    private InMailDao imdao;
    @Autowired
    private MailreciverDao mrdao;
    @Autowired
    private MailServices mservice;

    @Autowired
    private ProcessService proservice;

    private ExchangeClient exchangeClient;

    ActivitiUtils aUtils = new ActivitiUtils();
    //流程引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    public boolean ispush(){
        boolean ispush = false;
        String push="不推送";
        if (push.equals("推送")){
            ispush =true;
        }

        return ispush;
    }


    public void yespush(boolean flagpush, Task task, String processInstanceId, User lu, String sendname1, Timestamp createTime){
        if (flagpush) {
            ProcessInstance processInstance = aUtils.getProcessInstanceByTask(task);
            processInstanceId = processInstance.getProcessInstanceId();
            processpushmail(processInstanceId, lu, sendname1, createTime);
        }
    }
/**
* @processInstanceId 流程实例id
 * lu当前登录用户
 * bu设置邮件的title
 * createTime当前时间
*
* */
@RequestMapping("propushmails")
    public void processpushmail(String processInstanceId, User lu, String bu, Timestamp createTime){

        //流程当前人
        //Mailnumber number1 = mndao.findByUserIds(lu).get(0);
        //推送管理员
        User useradmin = userDao.findByUserName("admin");
        List<Mailnumber> numbers = mndao.findByUserIds(useradmin);
        if (numbers.size()>0){
            Mailnumber number =numbers.get(0);

        Inmaillist mail =new Inmaillist();
        mail.setMailTitle("流程推送："+bu);
        mail.setContent("您好，您在OA系统流程管理中有一条代办任务需要处理，请注意办理。谢谢！");
        mail.setMailUserid(useradmin);
        mail.setInReceiver("");//收件人
        mail.setMailCreateTime(createTime);
        Mailnumber mailnumber =mndao.findByUserIds(useradmin).get(0);
        mail.setMailNumberid(mailnumber);
        mail.setMailType((long)16);
        mail.setMailStatusid((long)20);
        mail.setPush(true);
        Inmaillist imail=imdao.save(mail);

        //显示历史完成信息
        //String processInstanceId = processInstance.getProcessInstanceId();
        List<HistoricActivityInstance> historyInfo = processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        String assignee = historyInfo.get(historyInfo.size()-1).getAssignee();
        mail.setInReceiver(assignee);//收件人
        User user2 = userDao.findByUserNameZ(assignee).get(0);
        //收件人的邮箱账号
        List<Mailnumber> accouts = mndao.findByUserIds(user2);
            String accout ="";
        if (accouts.size()>0){
             accout = accouts.get(0).getMailAccount();
            Mailreciver mreciver=new Mailreciver();
            mreciver.setMailId(imail);
            mreciver.setReciverId(user2);
            mrdao.save(mreciver);

        //邮件业务层
       /* mservice.pushmail(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
                mail.getContent(),null, null);*/
    mservice.pushmailexchange(number.getMailAccount(), number.getPassword(), accout, number.getMailUserName(), mail.getMailTitle(),
            mail.getContent(),null, null);
        }
        }
    }
}
