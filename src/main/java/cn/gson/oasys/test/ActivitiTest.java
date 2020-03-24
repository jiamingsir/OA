package cn.gson.oasys.test;

import org.activiti.engine.*;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class ActivitiTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private RuntimeService runtimeService;

    @Test
    public void testCreateTableWithXml(){
        // 引擎配置
        ProcessEngineConfiguration pec=ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        // 获取流程引擎对象
        ProcessEngine processEngine=pec.buildProcessEngine();
    }


    //6.2发布流程
    @Test
    public void deploy() throws Exception{


        processEngine = ProcessEngines.getDefaultProcessEngine();
        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("补卡申请")
                .addClasspathResource("processes/补卡申请.bpmn")
                .addClasspathResource("processes/补卡申请.png").deploy();

        System.out.println(deployment.getId()+"~~~~"+deployment.getName());



    }


    //6.3启动流程实例
    @Test
    public void startProcess() throws Exception{

        //获取流程引擎对象

        //启动流程
        //使用流程定义的key启动流程实例，默认会按照最新版本启动浏览器

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("inputUser", "哈哈哈啊");
        variables.put("message","营销中心");
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("补卡申请",variables);
        System.out.println("pid:"+pi.getId() + ",activitiId:"+pi.getActivityId());

    }

    //6.4查看我的个人任务
    @Test
    public void queryMyTask() throws Exception{
        //指定任务办理者
        String assignee = "赵彬";

        //获取流程对象引擎
         //查询任务列表
        List<Task> tasks  = processEngine.getTaskService().createTaskQuery()    //创建任务查询对象
                .taskAssignee(assignee)         //指定个人任务办理
                .list();
        for(Task task : tasks){
            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务的创建时间:"+task.getCreateTime());
            System.out.println("任务的办理人:"+task.getAssignee());
            System.out.println("流程实例ID："+task.getProcessInstanceId());
            System.out.println("执行对象ID:"+task.getExecutionId());
            System.out.println("流程定义ID:"+task.getProcessDefinitionId());
            System.out.println("########################################################");
        }

    }

    //6.5办理任务
    @Test
    public void complementTask() throws Exception{

        String taskId = "10002";
        processEngine = ProcessEngines.getDefaultProcessEngine();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("inputUser", "邱雯婷");
        variables.put("message","营销中心");
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId,variables);

        //完成任务
        //processEngine.getTaskService().complete(taskId);
        System.out.println("完成任务");

    }




 //查看已经完成的任务和当前在执行的任务


    @Test
    public void findHistoryTask() {
        ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();
        //如果只想获取到已经执行完成的，那么就要加入completed这个过滤条件
        List<HistoricTaskInstance> historicTaskInstances1 = defaultProcessEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .taskDeleteReason("completed")
                .list();
        //如果只想获取到已经执行完成的，那么就要加入completed这个过滤条件
        List<HistoricTaskInstance> historicTaskInstances2 = defaultProcessEngine.getHistoryService()
                .createHistoricTaskInstanceQuery()
                .list();
        System.out.println("执行完成的任务：" + historicTaskInstances1.size());
        System.out.println("所有的总任务数（执行完和当前未执行完）：" + historicTaskInstances2.size());


    }




















    //查询流程定义
    @Test
    public void findProcessDefinition() {
        processEngine = ProcessEngines.getDefaultProcessEngine();

        List<ProcessDefinition> list = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()// 创建一个流程定义的查询

/** 指定查询条件,where条件 */


// .deploymentId(deploymentId)//使用部署对象ID查询

// .processDefinitionId(processDefinitionId)//使用流程定义ID查询

// .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询

// .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

/*
* 排序


                .orderByProcessDefinitionVersion().asc()// 按照版本的升序排列

// .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列


* 返回的结果集 */


                .list();// 返回一个集合列表，封装流程定义

// .singleResult();//返回惟一结果集

// .count();//返回结果集数量

// .listPage(firstResult, maxResults);//分页查询

        if (list != null && list.size() > 0) {

            for (ProcessDefinition pd : list) {

                System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数

                System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值

                System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值

                System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1

                System.out.println("资源名称bpmn文件:" + pd.getResourceName());

                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());

                System.out.println("部署对象ID：" + pd.getDeploymentId());

                System.out

                        .println("#########################################################");

            }

        }

    }


    @Test
    //7.5删除流程
    public void deleteDeploment() throws Exception{
        processEngine = ProcessEngines.getDefaultProcessEngine();
        String deploymentId = "1342501";
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment(deploymentId , true);

    }


    @Test
    //7.6获取流程定义资源文档（流程图）
    public void ViewImage() throws Exception{
        processEngine = ProcessEngines.getDefaultProcessEngine();
        String deploymentId = "1";
        List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        String imageName = null;
        for(String name : names){
            System.out.println("name" + name);
            if(name.indexOf(".png") >= 0){
                imageName = name;
            }
        }
        System.out.println("imageName:" + imageName );
        if(imageName != null){
            File f = new File("e:/" + imageName);
            InputStream is = processEngine.getRepositoryService().getResourceAsStream(deploymentId,imageName);
            FileUtils.copyInputStreamToFile(is,f);
        }



    }

    //7.7附加功能：查询最新版本的流程定义
    @Test
    public void findLastVersionProcessDefinition(){
        processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> list = processEngine.getRepositoryService()//

                .createProcessDefinitionQuery()//

                .orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排列

                .list();

/**

 * Map<String,ProcessDefinition>

 map集合的key：流程定义的key

 map集合的value：流程定义的对象

 map集合的特点：当map集合key值相同的情况下，后一次的值将替换前一次的值*/




        Map<String, ProcessDefinition> map = new LinkedHashMap<String, ProcessDefinition>();

        if(list!=null && list.size()>0){

            for(ProcessDefinition pd:list){

                map.put(pd.getKey(), pd);

            }

        }

        List<ProcessDefinition> pdList = new ArrayList<ProcessDefinition>(map.values());

        if(pdList!=null && pdList.size()>0){

            for(ProcessDefinition pd:pdList){

                System.out.println("流程定义ID:"+pd.getId());//流程定义的key+版本+随机生成数

                System.out.println("流程定义的名称:"+pd.getName());//对应helloworld.bpmn文件中的name属性值

                System.out.println("流程定义的key:"+pd.getKey());//对应helloworld.bpmn文件中的id属性值

                System.out.println("流程定义的版本:"+pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1

                System.out.println("资源名称bpmn文件:"+pd.getResourceName());

                System.out.println("资源名称png文件:"+pd.getDiagramResourceName());

                System.out.println("部署对象ID："+pd.getDeploymentId());

                System.out.println("#########################################################");

            }

        }

    }
    //7.8附加功能：删除流程定义（删除key相同的所有不同版本的流程定义）

    @Test

    public void deleteProcessDefinitionByKey() {

// 流程定义的key

        String processDefinitionKey = "补卡申请";

// 先使用流程定义的key查询流程定义，查询出所有的版本

        List<ProcessDefinition> list = processEngine.getRepositoryService()//

                .createProcessDefinitionQuery()//

                .processDefinitionKey(processDefinitionKey).list();//

// 遍历，获取每个流程定义的部署ID

        if (list != null && list.size() > 0) {

            for(ProcessDefinition pd:list){

//获取部署ID

                String deploymentId = pd.getDeploymentId();

                processEngine.getRepositoryService()//

                        .deleteDeployment(deploymentId, true);

            }

        }

    }



   /* @Test*/
    //8.4：查询我的个人任务
    /*public void findMyPersonalTask() {

        String assignee = "孙博";

        List<Task> list = processEngine.getTaskService()//与正在执行的任务管理相关的Service

                .createTaskQuery() //创建任务查询对象*/

/**查询条件（where部分）


 .taskAssignee(assignee)//指定个人任务查询，指定办理人*/

// .taskCandidateUser(candidateUser)//组任务的办理人查询

// .processDefinitionId(processDefinitionId)//使用流程定义ID查询

// .processInstanceId(processInstanceId)//使用流程实例ID查询

// .executionId(executionId)//使用执行对象ID查询
/*
*排序


                .orderByTaskCreateTime().asc()//使用创建时间的升序排列

*返回结果集*/


// .singleResult()//返回惟一结果集

// .count()//返回结果集的数量

// .listPage(firstResult, maxResults);//分页查询

 /*               .list();//返回列表

        if(list!=null && list.size()>0){

            for(Task task:list){

                System.out.println("任务ID:"+task.getId());

                System.out.println("任务名称:"+task.getName());

                System.out.println("任务的创建时间:"+task.getCreateTime());

                System.out.println("任务的办理人:"+task.getAssignee());

                System.out.println("流程实例ID："+task.getProcessInstanceId());

                System.out.println("执行对象ID:"+task.getExecutionId());

                System.out.println("流程定义ID:"+task.getProcessDefinitionId());

                System.out.println("########################################################");

            }

        }

    }*/


/**
 * 当前用户-->当前用户正在执行的任务--->当前正在执行的任务的piid-->该任务所在的流程实例
 * @param assignee
 * @return
 * */


   /* @Test
        public
List<ProcessInstance> void getPIByUser() {
        List<ProcessInstance> pis = new ArrayList<ProcessInstance>();
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
*//**
         * 该用户正在执行的任务*//*


        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("赵彬")
                .list();
        for (Task task : tasks) {
*//**
         * 根据task-->piid-->pi*//*


            String piid = task.getProcessInstanceId();
            ProcessInstance pi = processEngine.getRuntimeService()
                    .createProcessInstanceQuery()
                    .processInstanceId(piid)
                    .singleResult();
            pis.add(pi);
            System.out.println(pi);
 System.out.print(pi.getStartUserId());
            }*/
            /* return pis;


        }
    }


    @Test
    public void completeMyPersonalTask(){
        //任务ID
        String taskId = "3103";
        //完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，对应sequenceFlow.bpmn文件中${message=='不重要'}
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("message", "重要");
        processEngine.getTaskService()//与正在执行的任务管理相关的Service
                .complete(taskId,variables);
        System.out.println("完成任务：任务ID："+taskId);
    }


*
     * 当前用户所有任务的发起人


    @Test
    public void ewewe() {

        HistoricProcessInstance historicProcessInstance= ProcessEngines.getDefaultProcessEngine().
                getHistoryService().createHistoricProcessInstanceQuery().
                processInstanceId("157501").singleResult();

        System.out.println(historicProcessInstance.getStartUserId());
        System.out.println(historicProcessInstance.getEndTime());//取实例的结束时间
        System.out.println(historicProcessInstance.getStartTime());
        System.out.println(historicProcessInstance.getDescription());
    }

    @Test
    public void activitiHistory() {

        HistoricProcessInstanceQuery historicProcessInstanceQuery = ProcessEngines.getDefaultProcessEngine().
                getHistoryService().createHistoricProcessInstanceQuery();
        HistoricProcessInstanceQuery a = historicProcessInstanceQuery.startedBy("孙博");
        List<HistoricProcessInstance> historicProcessInstance = a.list() ;

        for(HistoricProcessInstance h : historicProcessInstance){
            System.out.println(h.getStartUserId());
            System.out.println(h.getEndTime());//取实例的结束时间
            System.out.println(h.getStartTime());
            System.out.println(h.getDescription());
            System.out.println(h.getDeleteReason());
            System.out.println(h.getProcessDefinitionId());
            System.out.println(h.getProcessVariables());

        }




    }

    @Test
    public void distoryProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService().deleteProcessInstance("165001","");       //实例ID ， 参数随便写



    }

    //取回流程是什么鬼
    @Test
    public void ertyeryery(){

        TaskService taskService = processEngine.getTaskService();
        Task task = taskService.createTaskQuery().processInstanceBusinessKey("140004").singleResult();

            System.out.println("任务ID:"+task.getId());
            System.out.println("任务名称:"+task.getName());
            System.out.println("任务的创建时间:"+task.getCreateTime());
            System.out.println("任务的办理人:"+task.getAssignee());
            System.out.println("流程实例ID："+task.getProcessInstanceId());
            System.out.println("执行对象ID:"+task.getExecutionId());
            System.out.println("流程定义ID:"+task.getProcessDefinitionId());
            System.out.println("########################################################");





    }


}
*/



    @Test
    public void comment(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        ProcessInstance pi =ProcessEngines.getDefaultProcessEngine().getRuntimeService().createProcessInstanceQuery().processInstanceId("907501").singleResult();
        List<Comment> historyCommnets = new ArrayList<>();
        TaskService taskService = processEngine.getTaskService();
//       2）通过流程实例查询所有的(用户任务类型)历史活动
        List<HistoricActivityInstance> hais = processEngine.getHistoryService().createHistoricActivityInstanceQuery().processInstanceId(pi.getId()).activityType("userTask").list();
//       3）查询每个历史任务的批注
        for (HistoricActivityInstance hai : hais) {
            String historytaskId = hai.getTaskId();
            List<Comment> comments = taskService.getTaskComments(historytaskId);
            // 4）如果当前任务有批注信息，添加到集合中
            if(comments!=null && comments.size()>0){
                historyCommnets.addAll(comments);
            }
        }
    }

    @Test
    public void findHisVariablesList(){
        String processInstanceId = "00";
        List<HistoricVariableInstance> list = processEngine.getHistoryService()
                .createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();
        if(list != null && list.size()>0){
            for(HistoricVariableInstance hvi:list){
                System.out.println(hvi.getId()+"    "+hvi.getVariableName()+"	"+hvi.getValue());
            }
        }
    }


    @Test
    public void ghffghfgh() {
        List<HistoricProcessInstance> proact = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();

        List<HistoricProcessInstance> proact2 = processEngine.getHistoryService().createHistoricProcessInstanceQuery().startedBy("孙博").list();

        System.out.println(proact.size());
        System.out.println(proact2.size());

        for(HistoricProcessInstance a : proact){


            System.out.println(a.getSuperProcessInstanceId());
            System.out.println(a.getProcessDefinitionId());


        }

        for(HistoricProcessInstance a : proact2){


            System.out.println(a.getSuperProcessInstanceId());
            System.out.println(a.getProcessDefinitionId());


        }


/*


        List<HistoricActivityInstance> hisActivityInstanceList2 =   ((HistoricActivityInstanceQuery)processEngine.getHistoryService()
                .createHistoricActivityInstanceQuery()
                .processInstanceId("360001").activityType("userTask")
                .finished().orderByHistoricActivityInstanceEndTime().desc())
                .list();

        for(HistoricActivityInstance a : hisActivityInstanceList2){


                System.out.println(a.getProcessInstanceId());
                System.out.println(a.getActivityType());
                System.out.println(a.getProcessDefinitionId());


        }*/



    }

    @Test
    public void  aaaasds(){
        HistoricProcessInstanceQuery query = processEngine.getHistoryService().createHistoricProcessInstanceQuery().startedBy("赵彬");
      /*  for(HistoricProcessInstance a : query.list()){
            System.out.println(a.getId());
        }
        System.out.println(query.list().size());*/
    }



/*
    @Test
    public void getApproveHistory(String businessEventId) {


        HistoricProcessInstance hisProcessInstance = (HistoricProcessInstance) processEngine.getHistoryService()
                .createHistoricProcessInstanceQuery()
                .processInstanceBusinessKey(businessEventId).singleResult();
        // 该流程实例的所有节点审批记录
        List<HistoricActivityInstanceQuery> hisActInstList = getHisUserTaskActivityInstanceList(hisProcessInstance
                .getId());
        for (Iterator iterator = hisActInstList.iterator(); iterator.hasNext();) {
            // 需要转换成HistoricActivityInstance
            HistoricActivityInstance activityInstance = (HistoricActivityInstance) iterator
                    .next();
            //如果还没结束则不放里面
            if ("".equals(taskBo.getEndTime()) || taskBo.getEndTime() == null) {
                continue;
            }
            TaskBo taskBo = new TaskBo();
            taskBo.setTaskName(activityInstance.getActivityName());
            // 获得审批人名称 Assignee存放的是审批用户id
            if (activityInstance.getAssignee() != null) {
                taskBo.setApproveUserName(getUserName(activityInstance
                        .getAssignee()));
            } else {
                taskBo.setApproveUserName("");
            }
            // 获取流程节点开始时间
            taskBo.setStartTime(activityInstance.getStartTime() != null ? DateTimeUtil
                    .getFormatDate(activityInstance.getStartTime(),
                            WorkflowConstants.DATEFORMATSTRING) : "");
            // 获取流程节点结束时间
            if (activityInstance.getEndTime() == null) {
                taskBo.setEndTime("");
            } else {
                taskBo.setEndTime(DateTimeUtil.getFormatDate(
                        activityInstance.getEndTime(),
                        WorkflowConstants.DATEFORMATSTRING));
            }
            taskBoList.add(taskBo);
        }

    }*/




}