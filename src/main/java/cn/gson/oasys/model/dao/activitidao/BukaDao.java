package cn.gson.oasys.model.dao.activitidao;


import cn.gson.oasys.activiti.entity.Buka;
import cn.gson.oasys.model.entity.note.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BukaDao extends JpaRepository<Buka, Long> {

    @Query("from Buka b where b.processid = ?1")
    Buka findbyProcessid(Long processid);
}
