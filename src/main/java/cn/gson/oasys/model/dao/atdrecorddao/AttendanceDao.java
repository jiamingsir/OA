package cn.gson.oasys.model.dao.atdrecorddao;



import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import cn.gson.oasys.model.entity.atdrecord.Attendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AttendanceDao extends JpaRepository<Attendance, Long> {




    @Query(nativeQuery=true,value = "?1")
    @Transactional
    @Modifying
    void aaa(String sql);





/*   @Modifying
   @Transactional
   @Query(nativeQuery=true,value = "insert into aoa_attendance(id,name,dept,day1am,day1pm,day2am,day2pm,day3am,day3pm,day4am,day4pm,day5am,day5pm,day6am,day6pm,day7am,day7pm,day8am,day8pm,day9am,day9pm,day10am,day10pm,day11am,day11pm,day12am,day12pm,day13am,day13pm,day14am,day14pm,day15am,day15pm,day16am,day16pm,day17am,day17pm,day18am,day18pm,day19am,day19pm,day20am,day20pm,day21am,day21pm,day22am,day22pm,day23am,day23pm,day24am,day24pm,day25am,day25pm,day26am,day26pm,day27am,day27pm,day28am,day28pm,day29am,day29pm,day30am,day30pm,day31am,day31pm) values ( ?1)")
   Integer aaa(String sql);*/


}