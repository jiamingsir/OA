package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Changeposition;
import cn.gson.oasys.activiti.entity.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangepositionDao extends JpaRepository<Changeposition, Long> {

    @Query("from Changeposition b where b.processinstanceid = ?1")
    Changeposition findbyProcessid(Long processid);

}
