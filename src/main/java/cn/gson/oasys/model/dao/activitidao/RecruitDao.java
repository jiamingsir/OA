package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Lipin;
import cn.gson.oasys.activiti.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitDao extends JpaRepository<Recruit, Long> {

    @Query("from Recruit b where b.processinstanceid = ?1")
    Recruit findbyProcessid(Long processid);

}
