package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Gongzhangwaijie;
import cn.gson.oasys.activiti.entity.Jiu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JiuDao extends JpaRepository<Jiu, Long> {

    @Query("from Jiu b where b.processinstanceid = ?1")
    Jiu findbyProcessid(Long processid);

}
