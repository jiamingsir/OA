package cn.gson.oasys.model.dao.activitidao;



import cn.gson.oasys.activiti.entity.Gongzhangwaijie;
import cn.gson.oasys.activiti.entity.Lipin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GongzhangwaijieDao extends JpaRepository<Gongzhangwaijie, Long> {

    @Query("from Gongzhangwaijie b where b.processinstanceid = ?1")
    Gongzhangwaijie findbyProcessid(Long processid);

}
