package cn.gson.oasys.model.dao.activitidao;


import cn.gson.oasys.activiti.entity.Regulara;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegularaDao extends JpaRepository<Regulara,Long> {
    @Query("select b from Regulara b where b.processid = ?1")
    List<Regulara> findbyProcessid(Long processid);
}
