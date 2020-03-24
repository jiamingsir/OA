package cn.gson.oasys.Utils;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.*;
import org.activiti.engine.identity.UserQuery;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipInputStream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

/**
 * @author scw
 * @create 2018-01-24 9:51
 * @desc 针对流程管理的工具类
 **/
@Component("activitiUtils")
public class ActivitiUtils {
	@Resource(name = "processEngine")
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();;

	/**
	 * 部署流程
	 * 
	 * @param file
	 *            流程的zip文件
	 * @param processName
	 *            流程的名字
	 * @throws IOException
	 */
	public void deployeProcess(File file, String processName) throws IOException {
		InputStream inputStream = new FileInputStream(file);
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);
		this.processEngine.getRepositoryService().createDeployment().name(processName).addZipInputStream(zipInputStream)
				.deploy();

	}

	/**
	 * 通过字节流来进行部署流程
	 * 
	 * @param io
	 * @param processName
	 */
	public void deplyoProcessByInputSterm(InputStream io, String processName) {
		ZipInputStream zipInputStream = new ZipInputStream(io);
		this.processEngine.getRepositoryService().createDeployment().name(processName).addZipInputStream(zipInputStream)
				.deploy();
	}

	/**
	 * 查询所有的部署流程
	 * 
	 * @return
	 */
	public List<Deployment> getAllDeplyoment() {
		return this.processEngine.getRepositoryService().createDeploymentQuery().orderByDeploymenTime().desc().list();
	}

	@Test
	public void getAllDeplyomentTest() {
		List<Deployment> deploymentList = this.processEngine.getRepositoryService().createDeploymentQuery()
				.orderByDeploymenTime().desc().list();
		for (Deployment deployment : deploymentList) {
			System.out.println(deployment);
		}
	}

	/**
	 * 查询所有的部署定义信息
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getAllProcessInstance() {
		return this.processEngine.getRepositoryService().createProcessDefinitionQuery()
				.orderByProcessDefinitionVersion().desc().list();
	}

	@Test
	public void getAllProcessInstance2() {
		List<ProcessDefinition> processDefinitionList = this.processEngine.getRepositoryService()
				.createProcessDefinitionQuery().orderByProcessDefinitionVersion().desc().list();
		for (ProcessDefinition processDefinition : processDefinitionList) {
			System.out.println(processDefinition);
		}
	}

	/**
	 * 根据部署ID，来删除部署
	 * 
	 * @param deplyomenId
	 */
	public void deleteDeplyomentByPID(String deplyomenId) {
		this.processEngine.getRepositoryService().deleteDeployment(deplyomenId, true);
	}

	/**
	 * 查询某个部署流程的流程图
	 * 
	 * @param pid
	 * @return
	 */
	public InputStream lookProcessPicture(String pid) {
		return this.processEngine.getRepositoryService().getProcessDiagram(pid);
	}

	/**
	 * 开启请假的流程实例
	 * 
	 * @param billId
	 * @param userId
	 */
	public void startProceesInstance(Long billId, String userId) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("userID", userId);
		this.processEngine.getRuntimeService().startProcessInstanceByKey("shenqingtest", "" + billId, variables); // 第一个参数，就是流程中自己定义的名字，这个一定要匹配，否则是找不到的。
	}

	/**
	 * 查询当前登陆人的所有任务
	 * 
	 * @param userId
	 * @return
	 */
	public List<Task> queryCurretUserTaskByAssignerr(String userId) {
		return this.processEngine.getTaskService().createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc()
				.list();
	}

	/**
	 * 查询当前登陆人的所有任务
	 */
	@Test
	public void aa() {
		List<Task> taskList = this.processEngine.getTaskService().createTaskQuery().taskAssignee("费卫峰")
				.orderByTaskCreateTime().desc().list();
		for (Task task : taskList) {
			System.out.println("任务ID:" + task.getId());
			System.out.println("任务名称:" + task.getName());
			System.out.println("任务的创建时间:" + task.getCreateTime());
			System.out.println("任务的办理人:" + task.getAssignee());
			System.out.println("流程实例ID：" + task.getProcessInstanceId());
			System.out.println("执行对象ID:" + task.getExecutionId());
			System.out.println("流程定义ID:" + task.getProcessDefinitionId());
			task.getDelegationState();
			task.getProcessVariables();
			task.getCategory();
			task.getDescription();
			System.out.println("########################################################");
		}

	}

	/**
	 * 根据TaskId，获取到当前的执行节点实例对象
	 * 
	 * @param taskId
	 * @return
	 */
	/*
	 * public ActivityImpl getActivityImplByTaskId(String taskId){ //首先得到任务 Task
	 * task = this.getTaskByTaskId(taskId); //其次，得到流程实例 ProcessInstance
	 * processInstance = this.getProcessInstanceByTask(task); //再次，根据流程实例来获取到流程定义
	 * ProcessDefinitionEntity processDefinitionEntity =
	 * this.getProcessDefinitionEntityByTask(task);
	 * //再根据，流程定义，通过流程实例中来获取到activiti的ID，从而得到acitviImp ActivityImpl activity =
	 * processDefinitionEntity.findActivity(processInstance.getActivityId()); return
	 * activity; }
	 * 
	 * /** 根据taskId，判断对应的流程实例是否结束 如果结束了，那么得到的流程实例就是返回一个null 否则就是返回对应的流程实例对象
	 * 当然也可以选择返回boolean类型的
	 * 
	 * @param taskId 任务ID
	 * 
	 * @return
	 */
	public ProcessInstance isFinishProcessInstancs(String taskId) {
		// 1,先根据taskid，得到任务
		Task task = getTaskByTaskId(taskId);
		// 2:完成当前任务
		finishCurrentTaskByTaskId(taskId);
		// 3:得到当前任务对应得的流程实例对象
		ProcessInstance processInstance = getProcessInstanceByTask(task);
		return processInstance;
	}

	@Test
	public void isFinishProcessInstancs2() {
		String taskId = "32503";
		Task task = getTaskByTaskId(taskId);
		// 2:完成当前任务
		finishCurrentTaskByTaskId(taskId);
		// 3:得到当前任务对应得的流程实例对象
		ProcessInstance processInstance = getProcessInstanceByTask(task);

	}

	/**
	 * 获取当前执行节点的所有出口
	 * 
	 * @param activity
	 * @return
	 */
	/*
	 * public List<PvmTransition> getCurrentActivitiImplPvm(ActivityImpl activity){
	 * List<PvmTransition> outgoingTransitions = activity.getOutgoingTransitions();
	 * return outgoingTransitions; }
	 */

	/**
	 * 根据taskId获取到task
	 * 
	 * @param taskId
	 * @return
	 */
	public Task getTaskByTaskId(String taskId) {
		// 得到当前的任务
		Task task = this.processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		return task;
	}

	/**
	 * 根据Task中的流程实例的ID，来获取对应的流程实例
	 * 
	 * @param task
	 *            流程中的任务
	 * @return
	 */
	public ProcessInstance getProcessInstanceByTask(Task task) {
		// 得到当前任务的流程
		ProcessInstance processInstance = this.processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		return processInstance;
	}

	@Test
	public void getProcessInstanceByTask2() {
		Task task = this.processEngine.getTaskService().createTaskQuery().taskId("172518").singleResult();
		// 得到当前任务的流程
		ProcessInstance processInstance = this.processEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(task.getProcessInstanceId()).singleResult();
		System.out.println(processInstance);
	}

	/**
	 * 根据Task来获取对应的流程定义信息
	 * 
	 * @param task
	 * @return
	 */
	public ProcessDefinitionEntity getProcessDefinitionEntityByTask(Task task) {
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) this.processEngine
				.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
		return processDefinitionEntity;
	}

	@Test
	public void getProcessDefinitionEntityByTask2() {
		Task task = this.processEngine.getTaskService().createTaskQuery().taskId("5003").singleResult();
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) this.processEngine
				.getRepositoryService().getProcessDefinition(task.getProcessDefinitionId());
		System.out.println(processDefinitionEntity);
	}

	/**
	 * 根据taskId获取到businesskey，这个值是管理activiti表和自己流程业务表的关键之处
	 * 
	 * @param taskId
	 *            任务的ID
	 * @return
	 */
	public String getBusinessKeyByTaskId(String taskId) {
		Task task = this.getTaskByTaskId(taskId);
		ProcessInstance processInstance = this.getProcessInstanceByTask(task);
		// 返回值
		return processInstance.getBusinessKey();
	}

	@Test
	public void getBusinessKeyByTaskId2() {
		String taskId = "5003";
		Task task = this.getTaskByTaskId(taskId);
		ProcessInstance processInstance = this.getProcessInstanceByTask(task);
		// 返回值
		System.out.println(processInstance.getBusinessKey());
	}

	/**
	 * 根据taskId，完成任务
	 * 
	 * @param taskId
	 */
	public void finishCurrentTaskByTaskId(String taskId) {
		this.processEngine.getTaskService().complete(taskId);
	}

	/**
	 * 完成任务的同时，进行下一个节点的审批人员的信息的传递
	 * 
	 * @param taskId
	 * @param object
	 */
	public void finishCurrentTaskByTaskId(String taskId, Object object) {
		Map<String, Object> map = new HashMap<>();
		map.put("assigeUser", object);
		this.processEngine.getTaskService().complete(taskId, map);
	}

	/**
	 * 历史活动查询 比如这个流程实例什么时候开始的，什么时候结束的，以及中间具体的执行步骤，这时候，我们需要查询历史流程活动执行表，act_hi_actinst
	 * 也就是此流程的流程图
	 */
	@Test
	public List<HistoricActivityInstance> getHistoryActInstanceList(String processInstanceId) {
		List<HistoricActivityInstance> list = processEngine.getHistoryService() // 历史任务Service
				.createHistoricActivityInstanceQuery() // 创建历史活动实例查询

				.processInstanceId(processInstanceId) // 指定流程实例id
				.finished() // 查询已经完成的任务
				.list();

		return list;

	}

	@Test
	public void historyActInstanceList2() throws Exception {
		List<HistoricActivityInstance> list = processEngine.getHistoryService() // 历史任务Service
				.createHistoricActivityInstanceQuery() // 创建历史活动实例查询

				.processInstanceId("222522") // 指定流程实例id
				.finished() // 查询已经完成的任务
				.list();

		for (HistoricActivityInstance his : list) {
			System.out.println(his.toString());
		}

	}

	/**
	 * 查询流程发起人
	 *
	 */
	@Test
	public void sender() {
		IdentityService identityService = processEngine.getIdentityService();

	}

	@Test
	public void findHistory() {
		List<HistoricActivityInstance> list = processEngine.getHistoryService() // 历史相关Service
				.createHistoricActivityInstanceQuery() // 创建历史活动实例查询
				.processInstanceId("222501") // 执行流程实例id
				.finished().list();
		for (HistoricActivityInstance h : list) {
			System.out.println(h.toString());
		}

	}

	/**
	 * 当前用户所有任务的发起人
	 */
	public HistoricProcessInstance getHistoricProcessInstance(String processInstanceId, ProcessEngine processEngine) {

		HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

		return historicProcessInstance;
	}

	@Test
	public void ewewe() {

		HistoricProcessInstance historicProcessInstance = ProcessEngines.getDefaultProcessEngine().getHistoryService()
				.createHistoricProcessInstanceQuery().processInstanceId("157501").singleResult();

		System.out.println(historicProcessInstance.getStartUserId());
		System.out.println(historicProcessInstance.getEndTime());// 取实例的结束时间
		System.out.println(historicProcessInstance.getStartTime());
		System.out.println(historicProcessInstance.getDescription());
	}

	/**
	 * 当前登陆人的所有流程
	 */
	public List<HistoricProcessInstance> HistoricProcessInstanceQueryStartedBy(String userId,
			ProcessEngine processEngine) {

		HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery();
		HistoricProcessInstanceQuery a = historicProcessInstanceQuery.startedBy(userId)
				.orderByProcessInstanceStartTime().desc();
		List<HistoricProcessInstance> historicProcessInstanceList = a.list();

		return historicProcessInstanceList;

	}

	/**
	 * 根据流程名称查询
	 */
	public List<HistoricProcessInstance> HistoricProcessInstanceQueryDefinitionKey(List<String> keyList,
			ProcessEngine processEngine) {

		HistoricProcessInstanceQuery historicProcessInstanceQuery = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> returnlist = new ArrayList<>();
		HistoricProcessInstanceQuery a = historicProcessInstanceQuery.orderByProcessInstanceStartTime().desc();
		List<HistoricProcessInstance> historicProcessInstanceList = a.processDefinitionKeyIn(keyList).list();
		returnlist.addAll(historicProcessInstanceList);

		return returnlist;

	}

	/**
	 * 查询历史Task
	 */
	public HistoricTaskInstanceQuery HistoricTaskInstanceQueryStartedBy(ProcessEngine processEngine) {
		HistoricTaskInstanceQuery historicTaskInstanceQuery = processEngine.getHistoryService()
				.createHistoricTaskInstanceQuery();

		return historicTaskInstanceQuery;

	}

	public void distoryProcess(String processInstanceId, ProcessEngine processEngine) {
		processEngine.getRuntimeService().deleteProcessInstance(processInstanceId, "ssssss"); // 实例ID ， 参数随便写

	}

	public List<HistoricProcessInstance> getPassedInstanceList(String realName, ProcessEngine processEngine) {
		HistoricActivityInstanceQuery hisact = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.orderByHistoricActivityInstanceStartTime().desc();
		HistoricProcessInstanceQuery proact = processEngine.getHistoryService().createHistoricProcessInstanceQuery();
		List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
		List<String> processInstancesIdList = new ArrayList<>();
		List<HistoricActivityInstance> hisActivityInstanceList = hisact.list();
		List<String> processInstanceIds = new ArrayList<>();
		for (HistoricActivityInstance a : hisActivityInstanceList) {
			if (a.getAssignee() != null && a.getAssignee().equals(realName)) {
				HistoricProcessInstanceQuery query = proact.processInstanceId(a.getProcessInstanceId());

				HistoricProcessInstance instance = query.list().get(0);
				// 判断是否为本人发起
				if (!instance.getStartUserId().equals(realName)) {
					historicProcessInstanceList.add(instance);
					processInstanceIds.add(a.getProcessInstanceId());
				}
			}
		}
		// 去除同一流程实例，多次审核
		List<HistoricProcessInstance> historicProcessInstanceList2 = historicProcessInstanceList.stream().collect(
				collectingAndThen(toCollection(() -> new TreeSet<>(comparing(n -> n.getId()))), ArrayList::new));

		return historicProcessInstanceList2;

	}

	@Test
	public List<HistoricProcessInstance> getPassedInstanceList2(String realName, ProcessEngine processEngine) {
		HistoricActivityInstanceQuery hisact = processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.orderByHistoricActivityInstanceStartTime().desc();
		HistoricProcessInstanceQuery proact = processEngine.getHistoryService().createHistoricProcessInstanceQuery();

		HistoricProcessInstanceQuery proact2 = processEngine.getHistoryService().createHistoricProcessInstanceQuery()
				.startedBy(realName);

		List<HistoricProcessInstance> historicProcessInstanceList = new ArrayList<>();
		List<String> processInstancesIdList = new ArrayList<>();
		List<HistoricActivityInstance> hisActivityInstanceList = hisact.list();
		List<String> processInstanceIds = new ArrayList<>();
		for (HistoricActivityInstance a : hisActivityInstanceList) {
			if (a.getAssignee() != null && a.getAssignee().equals(realName)) {
				HistoricProcessInstanceQuery query = proact.processInstanceId(a.getProcessInstanceId());

				HistoricProcessInstance instance = query.list().get(0);
				// 判断是否为本人发起
				if (!instance.getStartUserId().equals(realName)) {
					historicProcessInstanceList.add(instance);
					processInstanceIds.add(a.getProcessInstanceId());
				}
			}
		}
		// 去除同一流程实例，多次审核
		List<HistoricProcessInstance> historicProcessInstanceList2 = historicProcessInstanceList.stream().collect(
				collectingAndThen(toCollection(() -> new TreeSet<>(comparing(n -> n.getId()))), ArrayList::new));

		return historicProcessInstanceList2;

	}

}