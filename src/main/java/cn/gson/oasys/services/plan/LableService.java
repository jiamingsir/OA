package cn.gson.oasys.services.plan;

import cn.gson.oasys.model.dao.plandao.WorkTableDao;
import cn.gson.oasys.model.dao.system.TypeDao;
import cn.gson.oasys.model.entity.plan.AoaWorkLable;
import cn.gson.oasys.model.entity.system.SystemTypeList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LableService {
	
	@Autowired
	private WorkTableDao workTableDao;
	
	/**
	 * 新增和更新
	 * @param list
	 * @return
	 */
	public AoaWorkLable save(AoaWorkLable list){
		return workTableDao.save(list);
	}
	
	/**
	 * 删除方法
	 */
	public void deleteLable(Long lableId){
		workTableDao.delete(lableId);
	}
	

}
