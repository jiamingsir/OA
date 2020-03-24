package cn.gson.oasys.model.dao.user;

import cn.gson.oasys.model.entity.user.Holidayset;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HolidaysetDao extends JpaRepository<Holidayset,Integer> {

    Holidayset findById(Integer idd);

    List<Holidayset> findByUserid(Long userid);

    @Query("from Holidayset d where d.username like %?1% ")
    Page<Holidayset> findnamelike(String namesearch, Pageable pa);
}
