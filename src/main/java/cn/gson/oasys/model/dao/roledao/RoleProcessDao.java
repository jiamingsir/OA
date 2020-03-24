package cn.gson.oasys.model.dao.roledao;


import cn.gson.oasys.model.entity.role.RoleProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoleProcessDao extends JpaRepository<RoleProcess, Long> {

    @Query("select ro from RoleProcess as ro where ro.proRoleName like %?1%")
    Page<RoleProcess> findbyrolename(String val, Pageable pa);
    @Query("select r from RoleProcess as r where r.proRoleName =?1")
    List<RoleProcess> findByrolename(String rolename);
    @Query("select r from RoleProcess as r where r.Id =?1")
    RoleProcess findByroleid(Long roleid);

}
