package cn.gson.oasys.model.dao.activitidao;


import cn.gson.oasys.activiti.entity.Buka;
import cn.gson.oasys.activiti.entity.Caigou;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CaigouDao extends JpaRepository<Caigou, Long> {

    @Query("from Caigou b where b.processinstanceid = ?1")
    Caigou findbyProcessid(Long processid);
}
