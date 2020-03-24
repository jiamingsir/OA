package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_entry", schema = "xyoa")
public class Entry {
    private long id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return id == entry.id &&
                Objects.equals(processname, entry.processname) &&
                Objects.equals(senderId, entry.senderId) &&
                Objects.equals(senderName, entry.senderName) &&
                Objects.equals(newDate, entry.newDate) &&
                Objects.equals(number, entry.number) &&
                Objects.equals(empId, entry.empId) &&
                Objects.equals(empName, entry.empName) &&
                Objects.equals(dept, entry.dept) &&
                Objects.equals(position, entry.position) &&
                Objects.equals(phone, entry.phone) &&
                Objects.equals(type, entry.type) &&
                Objects.equals(joinDate, entry.joinDate) &&
                Objects.equals(remark, entry.remark) &&
                Objects.equals(processid, entry.processid) &&
                Objects.equals(fileFile, entry.fileFile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, processname, senderId, senderName, newDate, number, empId, empName, dept, position, phone, type, joinDate, remark, processid, fileFile);
    }
}
