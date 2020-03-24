package cn.gson.oasys.model.dao.system;

import cn.gson.oasys.model.entity.system.ProcessManage;
import cn.gson.oasys.model.entity.system.SystemStatusList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessManageDao extends PagingAndSortingRepository<ProcessManage, Long>{


    ProcessManage findById(Integer id);
}
