package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_contractxq", schema = "xyoa")
public class Contractxq {
    private String processname;
    private Long senderId;
    private String senderName;
    private Timestamp newDate;
    private String dept;
    private String contractNumber;
    private String firstParty;
    private String secondParty;
    private String phone;
    private Timestamp beginDate;
    private Timestamp endDate;
    private String remark;
    private Long processid;
    private String fileFile;
    private String type;
    private String secondphone;
    private String orContractid;
    private long id;
    private String types;

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
    @Column(name = "contract_Number")
    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    @Basic
    @Column(name = "first_Party")
    public String getFirstParty() {
        return firstParty;
    }

    public void setFirstParty(String firstParty) {
        this.firstParty = firstParty;
    }

    @Basic
    @Column(name = "second_Party")
    public String getSecondParty() {
        return secondParty;
    }

    public void setSecondParty(String secondParty) {
        this.secondParty = secondParty;
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

    @Basic
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "secondphone")
    public String getSecondphone() {
        return secondphone;
    }

    public void setSecondphone(String secondphone) {
        this.secondphone = secondphone;
    }

    @Basic
    @Column(name = "or_Contractid")
    public String getOrContractid() {
        return orContractid;
    }

    public void setOrContractid(String orContractid) {
        this.orContractid = orContractid;
    }

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "types")
    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contractxq that = (Contractxq) o;
        return id == that.id &&
                Objects.equals(processname, that.processname) &&
                Objects.equals(senderId, that.senderId) &&
                Objects.equals(senderName, that.senderName) &&
                Objects.equals(newDate, that.newDate) &&
                Objects.equals(dept, that.dept) &&
                Objects.equals(contractNumber, that.contractNumber) &&
                Objects.equals(firstParty, that.firstParty) &&
                Objects.equals(secondParty, that.secondParty) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(beginDate, that.beginDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(processid, that.processid) &&
                Objects.equals(fileFile, that.fileFile) &&
                Objects.equals(type, that.type) &&
                Objects.equals(secondphone, that.secondphone) &&
                Objects.equals(orContractid, that.orContractid) &&
                Objects.equals(types, that.types);
    }

    @Override
    public int hashCode() {

        return Objects.hash(processname, senderId, senderName, newDate, dept, contractNumber, firstParty, secondParty, phone, beginDate, endDate, remark, processid, fileFile, type, secondphone, orContractid, id, types);
    }
}
