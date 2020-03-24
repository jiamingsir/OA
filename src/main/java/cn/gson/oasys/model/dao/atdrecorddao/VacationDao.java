package cn.gson.oasys.model.dao.atdrecorddao;

import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.atdrecord.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VacationDao extends JpaRepository<Vacation, Long> {


    Page<Vacation> findAll(Pageable page);


    Vacation findById(Integer idd);

    @Query(nativeQuery=true,value = "select * from aoa_vacation where begindate <= ?1 and enddate >= ?2")
    List<Vacation> findByTimes(String startTime, String endTime);

    @Query(nativeQuery=true,value = "select * from aoa_vacation where begindate <= ?1 and enddate >= ?1")
    List<Vacation> findByDate(String date);

    @Query(nativeQuery=true,value = "select * from aoa_vacation where ((begindate >=?1  and begindate <=?2) or (begindate<=?1 and enddate >=?2) or (enddate >=?1 and enddate <=?2 )) and type =?3")
    List<Vacation> findBytypeTimes(String startTime, String endTime,int type);

    @Query(nativeQuery=true,value = "select * from aoa_vacation where (begindate >=?1  and begindate <=?2) or (begindate<=?1 and enddate >=?2) or (enddate >=?1 and enddate <=?2 ) order by beginDate asc ")
    List<Vacation> findByCTimes(String startTime, String endTime);

    @Query(nativeQuery=true,value = "select * from aoa_vacation where (begindate <= ?1 and enddate >= ?2) and type =?3")
    List<Vacation> findByCtypeTime(String startTime, String endTime,int type);




}
