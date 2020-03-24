package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Buka;
import cn.gson.oasys.activiti.entity.Gongzhangshenqing;
import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GongzhangshenqingDao extends JpaRepository<Gongzhangshenqing, Long> {

    @Query("from Gongzhangshenqing b where b.processid = ?1")
    Gongzhangshenqing findbyProcessid(Long processid);

}
