package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_exit", schema = "xyoa")
public class Exit {
    private String processname;
    private Long senderId;
    private String senderName;
    private Timestamp newDate;
    private String number;
    private Long empId;
    private String empName;
    private String dept;
    private String position;
    private String phone;
    private String type;
    private Timestamp joinDate;
    private String remark;
    private Long processid;
    private String fileFile;
    private long id;

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
    @Column(name = "number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "emp_Id")
    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
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
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    @Column(name = "file_File")
    public String getFileFile() {
        return fileFile;
    }

    public void setFileFile(String fileFile) {
        this.fileFile = fileFile;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exit exit = (Exit) o;
        return id == exit.id &&
                Objects.equals(processname, exit.processname) &&
                Objects.equals(senderId, exit.senderId) &&
                Objects.equals(senderName, exit.senderName) &&
                Objects.equals(newDate, exit.newDate) &&
                Objects.equals(number, exit.number) &&
                Objects.equals(empId, exit.empId) &&
                Objects.equals(empName, exit.empName) &&
                Objects.equals(dept, exit.dept) &&
                Objects.equals(position, exit.position) &&
                Objects.equals(phone, exit.phone) &&
                Objects.equals(type, exit.type) &&
                Objects.equals(joinDate, exit.joinDate) &&
                Objects.equals(remark, exit.remark) &&
                Objects.equals(processid, exit.processid) &&
                Objects.equals(fileFile, exit.fileFile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(processname, senderId, senderName, newDate, number, empId, empName, dept, position, phone, type, joinDate, remark, processid, fileFile, id);
    }
}
