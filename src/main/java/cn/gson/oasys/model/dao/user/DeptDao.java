package cn.gson.oasys.model.dao.user;

import java.util.List;

import cn.gson.oasys.model.entity.user.DeptFather;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.gson.oasys.model.entity.user.Dept;

public interface DeptDao extends PagingAndSortingRepository<Dept, Long>{

	Dept findByDeptId(Long id);

	@Query("select de from Dept de ")
	List<Dept> findALLInName();

	@Query("select de from Dept de where de.deptFatherId.deptFatherId =?1 ")
	List<Dept> findALLByFather(Long fatherid);

	@Query("select def from DeptFather def ")
	List<DeptFather> findALLFatherName();

	@Query("select def from DeptFather def WHERE def.deptFatherId =?1 ")
	DeptFather findByFatherId(Long id);


	@Query("select de from Dept de WHERE de.deptName =?1 ")
	List<Dept> findByDeptNames(String DName);

	@Query("select de.deptName from Dept de where de.deptId=:id")
	String findname(@Param("id")Long id);

	@Query("from Dept d where d.deptName like %?1% ")
	List<Dept> findnamelike(String deptsearch, Pageable pa);


	Dept findByDeptName(String DeptName);

	@Query("select d.deptmanager from Dept d where d.deptId = ?1")
	Long findDeptManager(Long deptId);

	@Query("select distinct de.deptName from Dept de")
	List<String> findnames();



}
