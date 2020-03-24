package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EntryDao extends JpaRepository<Entry,Long> {
    @Query("select b from Entry b where b.processid = ?1")
    List<Entry> findbyProcessid(Long processid);
}
