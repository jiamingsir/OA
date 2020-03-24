package cn.gson.oasys.model.dao.user;

import java.util.List;

import cn.gson.oasys.model.entity.user.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.gson.oasys.model.entity.user.Dept;
import cn.gson.oasys.model.entity.user.User;

public interface UserDao extends JpaRepository<User, Long>{

	List<User>  findByUserId(Long id);
	
	List<User>  findByFatherId(Long parentid);
	
	Page<User> findByFatherId(Long parentid,Pageable pa);

	//List<User>  findByupFatherId(Long parentid);
@Query("SELECT u FROM User u where u.upfatherId = ?1 or u.upfatherId in (SELECT us.userId FROM User us where us.upfatherId = ?1) or u.userId =?1")
	Page<User> findByupfatherId(Long parentid,Pageable pa);

	@Query("SELECT u FROM User u where u.upfatherId = ?1 or u.upfatherId in (SELECT us.userId FROM User us where us.upfatherId = ?1) or u.userId =?1")
	List<User> findByupfatherIdList(Long parentid);
	
	//名字模糊查找
	@Query("select u from User u where  (u.userName like %?1% or u.realName like %?1%) and u.fatherId=?2 ")
	Page<User> findbyFatherId(String name,Long parentid,Pageable pa);
	//名字模糊查找
	@Query("select u from User u where  (u.userName like %?1% or u.realName like %?1%)  ")
	Page<User> findbyupfatherId(String name,Long parentid,Pageable pa);

	//名字模糊查找
	@Query("select u from User u where  u.userId in( ?1 )  order by u.dept.deptId")
	List<User> findbylonguserid(List<Long> parentid);
	//名字模糊查找
	@Query("select u from User u where  u.userId in( ?2 ) and (u.userName like %?1% or u.realName like %?1%) order by u.dept.deptId")
	List<User> findbykeylonguserid(String name,List<Long> parentid);
	
	@Query("select u from User u where u.realName=:name and u.isLock=0")
	User findid(@Param("name")String name);
	
	@Query("select tu.pkId from Taskuser tu where tu.taskId.taskId=:taskid and tu.userId.userId=:userid")
	Long findpkId(@Param("taskid")Long taskid,@Param("userid")Long userid);
	
	//根据名字找用户
	User findByUserName(String title);

	//根据姓名找用户
	User findByRealName(String title);
	//根据中文名字找用户
	@Query("from User u where u.realName =?1")
	List<User> findByUserNameZ(String title);

	
	//根据用户名模糊查找
	@Query("from User u where (u.userName like %:name% or u.realName like %:name%) and u.isLock = 0")
	Page<User> findbyUserNameLike(@Param("name")String name,Pageable pa);
	//根据真实姓名模糊查找
	Page<User> findByrealNameLike(String title,Pageable pa);
	
	//根据英文名首拼模糊查找，并分页
	Page<User> findByPinyinLike(String pinyin,Pageable pa);
	//根据姓名首拼模糊查找，并分页
	Page<User> findByUserNameLike(String pinyin,Pageable pa);
	
	//根据姓名首拼+查找关键字查找(部门、姓名、电话号码)，并分页
	@Query("from User u where (u.userName like ?1 or u.realName like ?1 or u.dept.deptName like ?1 or u.userTel like ?1 or u.position.name like ?1) and u.pinyin like ?2")
	Page<User> findSelectUsers(String baseKey,String pinyinm,Pageable pa);
	
	//根据姓名首拼+查找关键字查找(部门、姓名、电话号码)，并分页
	@Query("from User u where u.userName like ?1 or u.realName like ?1 or u.dept.deptName like ?1 or u.userTel like ?1 or u.position.name like ?1 or u.pinyin like ?2")
	Page<User> findUsers(String baseKey,String baseKey2,Pageable pa);
	/**
	 * 用户管理查询可用用户
	 * @param isLock
	 * @param pa
	 * @return
	 */


	Page<User> findByIsLock(Integer isLock,Pageable pa);

	@Query("from User u where u.isLock = ?1")
	List<User> findByIsLock2(Integer isLock);

	
	@Query("from User u where u.dept.deptName like %?1% or u.userName like %?1% or u.realName like %?1% or u.userTel like %?1% or u.role.roleName like %?1%")
	Page<User> findnamelike(String name,Pageable pa);
	
	List<User> findByDept(Dept dept);

	@Query("select u from User u where u.dept.deptId in (?1) ")
	List<User> findByDeptIdS(String deptids);

	@Query("select u from User u where u.dept.deptId =?1 ")
	List<User> findByDeptId (Long did);

	List<User> findByPosition(Position position);

	@Query("select u from User u where u.role.roleId=?1")
	List<User> findrole(Long lid); 
	
	/*通过（用户名或者电话号码）+密码查找用户*/
	@Query("from User u where (u.userName = ?1 or u.userTel = ?1) and u.password =?2")
	User findOneUser(String userName,String password);


	@Query("from User u where u.dept = ?1 and u.position = ?2")
	List<User> findByDeptNameAndPositionName(String Dept ,String Position);

	@Query("from User u  where u.isLock = ?1 and (u.userName like %?2% or u.realName like %?2%)")
	Page<User> findByIsLockAndSearch(int isOnJob, String usersearch ,Pageable pa);

	@Query("from User u  where u.isLock = 0 ")
	Page<User> findByNotIsLock(Pageable pa);

	@Query("from User u where u.userId = ?1 ")
	User findOneByUserId(Long userId);

	@Query( "select u.cardId from User u where u.userId = ?1")
    String findCardIdByUserId(Long userId);

	@Query("select u from User u where u.cardId like %?1%")
    User findycardId(String cardNo);

	@Query(nativeQuery=true,value = "SELECT u.* FROM aoa_user u join aoa_user_role_process r on r.user_name = u.user_name join aoa_role_process p on p.id = r.user_role_process_id where p.pro_role_name = ?1")
    List<User> findUsersByRoleName(String s);

	@Query("select u from User u where u.dept.deptName = ?1")
	List<User> findByDeptName(String deptName);



}
