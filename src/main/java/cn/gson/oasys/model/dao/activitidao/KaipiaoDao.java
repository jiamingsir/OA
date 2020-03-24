package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Kaipiao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KaipiaoDao  extends JpaRepository<Kaipiao,Long> {

    @Query("select b from Kaipiao b where b.processid = ?1")
    List<Kaipiao> findbyProcessid(Long processid);
}
