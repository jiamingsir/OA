package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Contractbg;
import cn.gson.oasys.activiti.entity.Contractxq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractxqDao extends JpaRepository<Contractxq,Long> {
    @Query("select b from Contractxq b where b.processid = ?1")
    List<Contractxq> findbyProcessid(Long processid);
}
