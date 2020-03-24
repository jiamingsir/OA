package cn.gson.oasys.model.dao.system;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;


import cn.gson.oasys.model.entity.system.SystemMenu;

import java.util.List;


public interface SystemMenuDao extends PagingAndSortingRepository<SystemMenu, Long>{
    @Query(" SELECT asm FROM SystemMenu AS asm WHERE asm.menuId = ?1")
    public SystemMenu  findByMenuId(Long menuId);

    @Query(" SELECT asm FROM SystemMenu AS asm WHERE asm.parentId = 0")
    public List<SystemMenu> findAllMenuParent();
}
