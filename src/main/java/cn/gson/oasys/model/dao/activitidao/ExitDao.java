package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Exit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExitDao extends JpaRepository<Exit,Long> {
    @Query("select b from Exit b where b.processid = ?1")
    List<Exit> findbyProcessid(Long processid);
}
