package cn.gson.oasys.model.dao.user;

import java.util.List;

import cn.gson.oasys.model.entity.user.Dept;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.gson.oasys.model.entity.user.Position;

public interface PositionDao extends PagingAndSortingRepository<Position, Long>{

	@Query("select po.name from Position po where po.id=:id")
	String findById(@Param("id")Long id);

	@Query("select p from Position p")
	List<Position> findByAll();
	
	List<Position> findByDeptidAndNameNotLike(Long deptid,String name);
	
	List<Position> findByDeptidAndNameLike(Long deptid,String name);

	//List<Position> findByDeptidAndNameIsNotLike(Long deletedeptid,String name);

	@Query("select p from Position p where p.name = ?1 and p.deptid = ?2")
	List<Position> findByPosition(String Name,Long deptid);

	@Query("select p from Position p where p.name like ?1")
	List<Position> findByLikeName(String Name);


	List<Position> findByNameNotLike(String name);

	List<Position> findByDeptid(Long deptid);
}
