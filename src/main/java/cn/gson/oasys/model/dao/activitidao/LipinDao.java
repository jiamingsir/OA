package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Gongzhangshenqing;
import cn.gson.oasys.activiti.entity.Lipin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LipinDao extends JpaRepository<Lipin, Long> {

    @Query("from Lipin b where b.processinstanceid = ?1")
    Lipin findbyProcessid(Long processid);

}
