package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Changeposition;
import cn.gson.oasys.activiti.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewDao extends JpaRepository<Interview, Long> {

    @Query("from Interview b where b.processinstanceid = ?1")
    Interview findbyProcessid(Long processid);

}
