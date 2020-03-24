package cn.gson.oasys.model.dao.filedao;

import cn.gson.oasys.model.entity.file.FileList;
import cn.gson.oasys.model.entity.file.FilePath;
import cn.gson.oasys.model.entity.file.Pcmaclog;
import cn.gson.oasys.model.entity.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PcmaclogDao extends PagingAndSortingRepository<Pcmaclog, Long>{




}
