package cn.gson.oasys;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWorkDeploy {
    //流程引擎
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    //部署流程
    @Test
    public void deployProcess(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        DeploymentBuilder builder = repositoryService.createDeployment();
        builder.addClasspathResource("processes/特别假申请.bpmn").name("特别假申请");//bpmn文件的名称
        builder.deploy();
    }
    //删除已经部署的流数据
    /*@Test
    public void tes*/


    //启动流程
    @Test
    public void  startProcess(){
        Map<String,Object> map1= new HashMap<String,Object>();
        map1.put("depter","大赵");
        map1.put("deptmanager","大赵打");
        map1.put("boss","大老赵");
        map1.put("day",2);


        RuntimeService runtimeService =processEngine.getRuntimeService();
        IdentityService identityService = processEngine.getIdentityService();
        identityService.setAuthenticatedUserId("87504");
        ProcessInstance leave_process = runtimeService.startProcessInstanceByKey("leave_process2", map1);

        /*ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processname ,variables);*/


    }
@Test
public  void leave(){


}
    @Test
    public void pro(){
        //hi_pro
        //据实例取得该实例信息的个例
        String processInstanceId = "87501";//流程实例ID
        HistoricProcessInstance processInstance= ProcessEngines.getDefaultProcessEngine().
                getHistoryService().createHistoricProcessInstanceQuery().
                processInstanceId(processInstanceId).singleResult();
        //通过 processInstance.getXXX 的方式取得其他的属性信息
        //获取实例的发起人
        String startUserId = processInstance.getStartUserId();
        System.out.println("获取实例的发起人:"+startUserId);
        String startActivityId = processInstance.getStartActivityId();
        System.out.println("获取实例的开始节点:"+startActivityId);


        TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量
        Task task = taskService.createTaskQuery().taskId("87509").singleResult();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String processDefinitionId=task.getProcessDefinitionId(); // 获取流程定义id
        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery() // 创建流程定义查询
                .processDefinitionId(processDefinitionId) // 根据流程定义id查询
                .singleResult();
        System.out.println("部署id:"+processDefinition.getDeploymentId());

        ProcessDefinitionEntity processDefinition1 = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processInstanceId1 = task.getProcessInstanceId();// 获取流程实例id
        List<ProcessInstance> pi=runtimeService.createProcessInstanceQuery() // 根据流程实例id获取流程实例
                .processInstanceId(processInstanceId1)
                .list();

        System.out.println("交易key:"+pi.get(0).getId());
        System.out.println("流程实例id:"+processInstanceId1);
        //ActivityImpl  activityImpl=processDefinition1.findActivity(pi.getActivityId()); // 根据活动id获取活动实例




    }
    /**设置流程变量*/
    @Test
    public void setProcessVariables(){
        String processInstanceId = "87501";//流程实例ID
        String assignee = "赵负责";//任务办理人
        TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量

        //查询当前办理人的任务ID
        Task task = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)//使用流程实例ID
                .taskAssignee(assignee)//任务办理人
                .singleResult();

        //设置流程变量【基本类型】
        taskService.setVariable(task.getId(), "boss", assignee);

        taskService.setVariable(task.getId(), "day",4 );
        //taskService.setVariableLocal(task.getId(), "day",3);
        //taskService.setVariable(task.getId(), "请假日期", new Date());


    }

    //查看流程
    @Test
    public void queryTask(){
        TaskService taskService = processEngine.getTaskService();
        //根据assignee(节点接收人)查看任务
        String assignee ="张三";
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        int size = tasks.size();
        for (int i = 0; i < size; i++) {
            Task task = tasks.get(i);

        }
        //首次运行的时候这个没有输出，因为第一次运行的时候扫描act_ru_task的表里面是空的，但第一次运行完成之后里面会添加一条记录，之后每次运行里面都会添加一条记录
        for (Task task : tasks) {
            System.out.println("taskId=" +"流程任务节点信息ID："+ task.getId() +
                    ",taskName:" +"流程任务节点名称ID：" +task.getName() +
                    ",assignee:" + "流程任务节点接受人："+task.getAssignee() +
                    ",createTime:" +"流程任务节点创建时间："+ task.getCreateTime());
        }


    }

    //查询流程定义明细
    @Test
    public void queryProcdef(){
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //创建查询对象
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
        //添加查询条件
        query.processDefinitionKey("myProcess_1");//通过key获取
        // .processDefinitionName("My process")//通过name获取
        // .orderByProcessDefinitionId()//根据ID排序
        //执行查询获取流程定义明细
        List<ProcessDefinition> pds = query.list();
        for (ProcessDefinition pd : pds) {
            System.out.println("ID:"+pd.getId()+",NAME:"+pd.getName()+",KEY:"+pd.getKey()+",VERSION:"+pd.getVersion()+",RESOURCE_NAME:"+pd.getResourceName()+",DGRM_RESOURCE_NAME:"+pd.getDiagramResourceName());
        }
    }

    //审核过程
    @Test
    public void startProcessApproval(){

        TaskService taskService = processEngine.getTaskService();
        //taskId 就是查询任务中的 ID
        String taskId = "70003";
        //完成请假申请任务
        taskService.complete(taskId);
    }




}
