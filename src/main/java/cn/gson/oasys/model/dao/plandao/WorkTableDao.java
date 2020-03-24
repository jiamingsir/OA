package cn.gson.oasys.model.dao.plandao;

import cn.gson.oasys.model.entity.plan.AoaWorkLable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTableDao extends JpaRepository<AoaWorkLable,Long> {

    //List<AoaWorkLable> findByLableNameLikeOrLableModelLike(String name, String name2);
@Query("select a from AoaWorkLable a where a.deptId.deptName like ?1")
    List<AoaWorkLable> findByDeptNameLike(String name);

    @Query("select distinct slable from AoaWorkLable slable group by slable.deptId")
    List<AoaWorkLable> findAlls();
    @Query("select distinct slable from AoaWorkLable slable where slable.deptId.deptId =?1 ")
    List<AoaWorkLable> findByDeptId(Long id);
@Query("select  l from AoaWorkLable l where l.workLabel =?1")
    List<AoaWorkLable> findByLabel(String leble);


}
