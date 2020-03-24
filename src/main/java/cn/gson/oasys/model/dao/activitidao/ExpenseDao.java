package cn.gson.oasys.model.dao.activitidao;

import cn.gson.oasys.activiti.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExpenseDao  extends JpaRepository<Expense,Long> {

    @Query("select b from Expense b where b.processid = ?1")
    List<Expense> findbyProcessid(Long processid);

}
