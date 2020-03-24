package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Contractbg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractbgDao extends JpaRepository<Contractbg,Long> {
    @Query("select b from Contractbg b where b.processid = ?1")
    List<Contractbg> findbyProcessid(Long processid);
}
