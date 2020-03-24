package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Contractcq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractcqDao extends JpaRepository<Contractcq,Long> {
    @Query("select b from Contractcq b where b.processid = ?1")
    List<Contractcq> findbyProcessid(Long processid);
}
