package cn.gson.oasys.activiti;

import cn.gson.oasys.Utils.UserUtils;
import cn.gson.oasys.model.dao.user.UserDao;
import cn.gson.oasys.model.entity.user.User;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskListenerImpl implements TaskListener {

    private String assUser;

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    private HttpSession session;

    @Autowired
    private UserDao uDao;

    @Autowired
    public void setUDao(UserDao uDao){
        this.uDao = uDao;
    }
    /**用来指定任务的办理人*/
    @Override
    public void notify(DelegateTask delegateTask) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();;
        //指定个人任务的办理人，也可以指定组任务的办理人
       /* User user = UserUtils.getUser(sess);
        Map<String, Object> variables = new HashMap<String, Object>();*/


    }

    public String getAssUser() {
        return assUser;
    }

    public void setAssUser(String assUser) {
        this.assUser = assUser;
    }



}


