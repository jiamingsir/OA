package cn.gson.oasys.model.dao.user;

import javax.transaction.Transactional;

import org.aspectj.weaver.reflect.ReflectionBasedReferenceTypeDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import cn.gson.oasys.model.entity.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserService {

	@Autowired
	UserDao userDao;
	
	//找到该管理员下面的所有用户并且分页
	public Page<User> findmyemployuser(int page, String baseKey,long parentid) {
		Pageable pa=new  PageRequest(page, 10);
		if (!StringUtils.isEmpty(baseKey)) {
			// 模糊查询
			return userDao.findbyFatherId(baseKey, parentid, pa);
		}
		else{
			return userDao.findByFatherId(parentid, pa);
		}

		/*if (!StringUtils.isEmpty(baseKey)) {
			// 模糊查询
			return userDao.findbyupfatherId(baseKey, parentid, pa);
		}
		else{
			return userDao.findByupfatherId(parentid, pa);
		}*/

		
	}
	//找到该管理员下面的所有用户并且分页
	public Page<User> findmyemployuser2(int page, String baseKey,long parentid) {
		Pageable pa=new  PageRequest(page, 10);

		if (!StringUtils.isEmpty(baseKey)) {
			// 模糊查询
			return userDao.findbyupfatherId(baseKey, parentid, pa);
		}
		else{
			return userDao.findByupfatherId(parentid, pa);
		}


	}
	//找到该管理员下面的所有用户并且分页
	public List<User> findmyemployuser3( String baseKey,Map<Long,User> userids) {
		//Pageable pa=new  PageRequest(page, 10);
		String usersid ="";
		List<Long> usersids =new ArrayList<>();
		for (Long uid : userids.keySet()) {
			usersid = usersid + uid.toString()+",";
			usersids.add(uid);

		}
		String user1 = usersid.substring(0,usersid.length()-1);
		if (!StringUtils.isEmpty(baseKey)) {
			// 模糊查询
			return userDao.findbykeylonguserid(baseKey, usersids);
		}
		else{
			return userDao.findbylonguserid(usersids);
		}


	}
}
