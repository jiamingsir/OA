package cn.gson.oasys;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

import java.util.*;

public class MyWorkTable {
    @Test
    public void createTable(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
    }

    @Test
    public void sds(){
        List<Integer> aa = new ArrayList();
        List<Integer> bb = new ArrayList();
        aa.add(1);
        aa.add(2);
        aa.add(3);
        bb.add(1);
        bb.add(2);
        bb.add(4);

        Collection<Integer> defferentId = getDifferent(aa,bb);
        for(Integer i  : defferentId){
            System.out.println(i);
        }

    }


    private  Collection<Integer> getDifferent(List<Integer> atdIdList, List<Integer> atdId2List) {

        Collection realA = new ArrayList<Integer>(atdIdList);
        Collection realB = new ArrayList<Integer>(atdId2List);
// 求交集
        realA.retainAll(realB);
        System.out.println("交集结果：" + realA);
        Set result = new HashSet();
// 求全集
        result.addAll(atdIdList);
        result.addAll(atdId2List);
        System.out.println("全集结果：" + result);
// 求差集：结果
        Collection aa = new ArrayList(realA);
        Collection bb = new ArrayList(result);
        bb.removeAll(aa);
        System.out.println("最终结果：" + bb);

        return bb;

    }
}
