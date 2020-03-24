package cn.gson.oasys.model.dao.roledao;

import cn.gson.oasys.model.entity.role.RoleProcess;
import cn.gson.oasys.model.entity.role.RoleUserProcess;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleUserProcessDao extends JpaRepository<RoleUserProcess, Long> {

    @Query("select roup from RoleUserProcess as roup ")
    Page<RoleUserProcess> findbyrolename(String val, Pageable pa);

    @Query("select roup from RoleUserProcess as roup  where roup.userName =?1")
    List<RoleUserProcess> findbyroleuserproname(String val);

    @Query("select roup from RoleUserProcess as roup  where roup.userName =?1 and roup.roleProcess.id =?2 ")
    List<RoleUserProcess> findByUserNameAndRoleId(String val ,Long id);
    @Query("select roup from RoleUserProcess as roup  where  roup.roleProcess.id =?1 ")
    List<RoleUserProcess> findByRoleupId(Long id);

    List<RoleUserProcess> findByUserName(String name);




}
