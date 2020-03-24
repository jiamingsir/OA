package cn.gson.oasys.model.dao.atdrecorddao;



import cn.gson.oasys.model.entity.atdrecord.Atdrecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface AtdrecordDao extends JpaRepository<Atdrecord, Long> {


    @Query(nativeQuery=true,value = "select * from atdRecord p where p.recDate >= ?1 and p.recDate <= ?2 ")
    List<Atdrecord> findByRecdate(String beginDate , String endDate);

    @Query(nativeQuery=true,value = "select SerialId from atdRecord p where p.recDate >= ?1 and p.recDate <= ?2")
    List<Integer> findSerialIdByRecdate(String beginDate , String endDate);


    @Query(nativeQuery=true,value = "select distinct OperDate from atdRecord p where p.recDate >= ?1 and p.recDate <= ?2 and p.flag = ?3")
    List<String> findOperDateByRecdate(String beginDate , String endDate , Integer type);

    @Query(value="from Atdrecord  r where r.cardno in (?1) and r.recdate >= ?2 and r.recdate <= ?3  order by r.serialid desc" )
    Page<Atdrecord> findByCardno (List<String> CardId, String beginDate,String endDate,Pageable pa);


    @Query(value="from Atdrecord  r where r.cardno in (?1)  order by r.serialid desc" )
    List<Atdrecord> findByCardid(List<String> CardId);


    /*@Query(nativeQuery=true,value = "select serialid,cardno,recdate,Max(rectime) as evening,min(rectime) as morning from atdrecord where recDate >= ?1 and recDate <= ?2 and cardno = ?3 and cardno is not null  group by cardno, recdate order by cardno ")*/
    @Query(nativeQuery=true,value = "select * from atdrecord where recDate >= ?1 and recDate <= ?2 and cardno = ?3 and cardno is not null  group by cardno, recdate,rectime order by cardno,recDate ")
    List<Atdrecord> findByRecdateAndCardno(String beginDate , String endDate ,String cardno);

    @Query(nativeQuery=true,value = "select distinct cardno from atdRecord p where p.recDate >= ?1 and p.recDate <= ?2 and p.cardno is not null order by p.CardNo")
    List<String> findCardnoByRecdate(String beginDate,String endDate);

    @Query(nativeQuery=true,value = "select  SerialId,EmplID,cardno,recdate,RecTime,IsAuto,VerifyMode,EquNo,InOutType,OperId,OperDate,Remark,Checker,checkdate,PersonalRec,min(rectime) as morning,Max(rectime) as evening ,flag from atdrecord where recDate >= ?1 and recDate <= ?2 and cardno = ?3 and cardno is not null  group by cardno, recdate order by cardno,recDate ")
    List<Atdrecord> findByRecdateAndCardnoGroupbyDay(String beginDate , String endDate ,String cardno);

    /*
    @Query(nativeQuery=true,value = "select  * from atdrecord where recDate >= ?1 and recDate <= ?2 and flag != 0 and cardno = ?3 and cardno is not null  ")
    List<Atdrecord> findAtdrecordByFlagIsNotZero(String beginDate , String endDate,String cardno);
*/

    @Query(nativeQuery=true,value = "select  SerialId,EmplID,cardno,recdate,RecTime,IsAuto,VerifyMode,EquNo,InOutType,OperId,OperDate,Remark,Checker,checkdate,PersonalRec,min(rectime) as morning,Max(rectime) as evening ,flag from atdrecord where recDate >= ?1 and recDate <= ?2 and flag != 0 and cardno = ?3 and cardno is not null  group by recdate  order by recdate,cardno ")
    List<Atdrecord> findAtdrecordByFlagIsNotZero(String beginDate , String endDate,String cardno);

    @Query(nativeQuery=true,value = "select  SerialId,EmplID,cardno,recdate,RecTime,IsAuto,VerifyMode,EquNo,InOutType,OperId,OperDate,Remark,Checker,checkdate,PersonalRec,min(rectime) as morning,Max(rectime) as evening ,flag from atdrecord where recDate >= ?1 and recDate <= ?2 and flag != 0 and flag != 10 and cardno = ?3 and cardno is not null  group by recdate  order by recdate,cardno ")
    List<Atdrecord> findAtdrecordByFlagIsNotZeroTen(String beginDate , String endDate,String cardno);

    @Query(nativeQuery=true,value = "select  count(*) from atdrecord where recDate = ?1 and ?2 < '12:00' and flag != 0 and cardno = ?3 and flag != ?4 and cardno is not null  ")
    int findNumByFlagAndRecdateAndRectime(String recdate , String rectime , String cardno , int flag);

    @Query(nativeQuery=true,value = "select  SerialId,EmplID,cardno,recdate,RecTime,IsAuto,VerifyMode,EquNo,InOutType,OperId,OperDate,Remark,Checker,checkdate,PersonalRec,flag from atdrecord where cardno = ?1 and recDate = ?2 and rectime >= ?3 and rectime <= ?4 and flag = 10 and cardno is not null  group by recdate  order by recdate")
    List<Atdrecord> findByCardnoAndRecdateAndRectime(String cardno, String recDate ,String beginTime , String endTime);

}
