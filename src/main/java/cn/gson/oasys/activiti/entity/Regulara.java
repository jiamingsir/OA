package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_regular", schema = "xyoa", catalog = "")
public class Regulara {
    private long id;
    private String processname;
    private Long senderId;
    private String senderName;
    private Timestamp newDate;
    private String dept;
    private String position;
    private Timestamp joinDate;
    private Timestamp beginDate;
    private Timestamp endDate;
    private String supemp;
    private String depter;
    private String supempPosition;
    private String depterPosition;
    private String remark;
    private String fileFile;
    private Long processid;
    private String empName;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "sender_Id")
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Basic
    @Column(name = "sender_Name")
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    @Basic
    @Column(name = "new_Date")
    public Timestamp getNewDate() {
        return newDate;
    }

    public void setNewDate(Timestamp newDate) {
        this.newDate = newDate;
    }

    @Basic
    @Column(name = "dept")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Basic
    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "join_Date")
    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    @Basic
    @Column(name = "begin_Date")
    public Timestamp getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Timestamp beginDate) {
        this.beginDate = beginDate;
    }

    @Basic
    @Column(name = "end_Date")
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "supemp")
    public String getSupemp() {
        return supemp;
    }

    public void setSupemp(String supemp) {
        this.supemp = supemp;
    }

    @Basic
    @Column(name = "depter")
    public String getDepter() {
        return depter;
    }

    public void setDepter(String depter) {
        this.depter = depter;
    }

    @Basic
    @Column(name = "supemp_Position")
    public String getSupempPosition() {
        return supempPosition;
    }

    public void setSupempPosition(String supempPosition) {
        this.supempPosition = supempPosition;
    }

    @Basic
    @Column(name = "depter_Position")
    public String getDepterPosition() {
        return depterPosition;
    }

    public void setDepterPosition(String depterPosition) {
        this.depterPosition = depterPosition;
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
    @Column(name = "file_File")
    public String getFileFile() {
        return fileFile;
    }

    public void setFileFile(String fileFile) {
        this.fileFile = fileFile;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Regulara regulara = (Regulara) o;
        return id == regulara.id &&
                Objects.equals(processname, regulara.processname) &&
                Objects.equals(senderId, regulara.senderId) &&
                Objects.equals(senderName, regulara.senderName) &&
                Objects.equals(newDate, regulara.newDate) &&
                Objects.equals(dept, regulara.dept) &&
                Objects.equals(position, regulara.position) &&
                Objects.equals(joinDate, regulara.joinDate) &&
                Objects.equals(beginDate, regulara.beginDate) &&
                Objects.equals(endDate, regulara.endDate) &&
                Objects.equals(supemp, regulara.supemp) &&
                Objects.equals(depter, regulara.depter) &&
                Objects.equals(supempPosition, regulara.supempPosition) &&
                Objects.equals(depterPosition, regulara.depterPosition) &&
                Objects.equals(remark, regulara.remark) &&
                Objects.equals(fileFile, regulara.fileFile) &&
                Objects.equals(processid, regulara.processid) &&
                Objects.equals(empName, regulara.empName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, processname, senderId, senderName, newDate, dept, position, joinDate, beginDate, endDate, supemp, depter, supempPosition, depterPosition, remark, fileFile, processid, empName);
    }
}
