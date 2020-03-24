package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Changeposition;
import cn.gson.oasys.activiti.entity.Contractcharter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractcharterDao extends JpaRepository<Contractcharter, Long> {

    @Query("from Contractcharter b where b.processinstanceid = ?1")
    Contractcharter findbyProcessid(Long processid);

}
