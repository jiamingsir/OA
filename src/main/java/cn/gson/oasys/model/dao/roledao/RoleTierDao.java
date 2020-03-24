package cn.gson.oasys.model.dao.roledao;


import cn.gson.oasys.model.entity.role.RoleProcess;
import cn.gson.oasys.model.entity.role.RoleTier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleTierDao extends JpaRepository<RoleTier, Long> {

   /* @Query("select ro from RoleTier as ro where ro.roleprofatherid like %?1%")
    Page<RoleTier> findbyids(Long val, Pageable pa);*/
    @Query("select r from RoleTier as r where r.roleprofatherid =?1")
    List<RoleTier> findbyRoleFatherid(Long roleid);
    /*@Query("select r from RoleTier as r where r.Id =?1")
    RoleTier findByroleid(Long roleid);*/

}
