package cn.gson.oasys.model.dao.user;


import cn.gson.oasys.model.entity.user.DeptFather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DeptFatherDao extends PagingAndSortingRepository<DeptFather, Long> {

    @Query("select de from DeptFather de ")
    List<DeptFather> findALLList();

    @Query("select de from DeptFather de where de.deptFatherName = ?1 ")
    List<DeptFather> findByDeptFatherName(String FName);
}
