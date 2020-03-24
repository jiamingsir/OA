package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_regularreply", schema = "xyoa", catalog = "")
public class Regularreply {
    private long id;
    private Long processid;
    private String empName;
    private String empeval;
    private String deptName;
    private String depteval;
    private String remark;
    private String processname;
    private Timestamp empDate;
    private Timestamp deptDate;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Processid")
    public Long getProcessid() {
        return processid;
    }

    public void setProcessid(Long processid) {
        this.processid = processid;
    }

    @Basic
    @Column(name = "emp_Name")
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Basic
    @Column(name = "empeval")
    public String getEmpeval() {
        return empeval;
    }

    public void setEmpeval(String empeval) {
        this.empeval = empeval;
    }

    @Basic
    @Column(name = "dept_Name")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "depteval")
    public String getDepteval() {
        return depteval;
    }

    public void setDepteval(String depteval) {
        this.depteval = depteval;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "processname")
    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    @Basic
    @Column(name = "emp_Date")
    public Timestamp getEmpDate() {
        return empDate;
    }

    public void setEmpDate(Timestamp empDate) {
        this.empDate = empDate;
    }

    @Basic
    @Column(name = "dept_Date")
    public Timestamp getDeptDate() {
        return deptDate;
    }

    public void setDeptDate(Timestamp deptDate) {
        this.deptDate = deptDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regularreply that = (Regularreply) o;
        return id == that.id &&
                Objects.equals(processid, that.processid) &&
                Objects.equals(empName, that.empName) &&
                Objects.equals(empeval, that.empeval) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(depteval, that.depteval) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(processname, that.processname) &&
                Objects.equals(empDate, that.empDate) &&
                Objects.equals(deptDate, that.deptDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, processid, empName, empeval, deptName, depteval, remark, processname, empDate, deptDate);
    }
}
