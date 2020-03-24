package cn.gson.oasys.model.dao.activitidao;


import cn.gson.oasys.activiti.entity.Regularreply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegularreplyDao extends JpaRepository<Regularreply,Long> {
    @Query("select b from Regularreply b where b.processid = ?1")
    List<Regularreply> findbyProcessid(Long processid);
}
