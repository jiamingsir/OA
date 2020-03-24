package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Leave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LeaveDao extends JpaRepository<Leave,Long> {


    @Query("select b from Leave b where b.processid = ?1")
    List<Leave> findbyProcessid(Long processid);

}
