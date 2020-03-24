package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_leave", schema = "xyoa", catalog = "")
public class Leave {
    private long id;
    private Long senderId;
    private String senderName;
    private String dept;
    private String company;
    private Timestamp beginDate;
    private Timestamp endDate;
    private String type;
    private Long processid;
    private String replacement;
    private String remark;
    private String cause;
    private String leaveDays;
    private Timestamp applydate;
    private String processname;
    private String fileFile;
    private Integer isend;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    @Column(name = "dept")
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Basic
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "replacement")
    public String getReplacement() {
        return replacement;
    }

    public void setReplacement(String replacement) {
        this.replacement = replacement;
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
    @Column(name = "cause")
    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    @Basic
    @Column(name = "leave_Days")
    public String getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    @Basic
    @Column(name = "applydate")
    public Timestamp getApplydate() {
        return applydate;
    }

    public void setApplydate(Timestamp applydate) {
        this.applydate = applydate;
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
        Leave leave = (Leave) o;
        return id == leave.id &&
                Objects.equals(senderId, leave.senderId) &&
                Objects.equals(senderName, leave.senderName) &&
                Objects.equals(dept, leave.dept) &&
                Objects.equals(company, leave.company) &&
                Objects.equals(beginDate, leave.beginDate) &&
                Objects.equals(endDate, leave.endDate) &&
                Objects.equals(type, leave.type) &&
                Objects.equals(processid, leave.processid) &&
                Objects.equals(replacement, leave.replacement) &&
                Objects.equals(remark, leave.remark) &&
                Objects.equals(cause, leave.cause) &&
                Objects.equals(leaveDays, leave.leaveDays) &&
                Objects.equals(applydate, leave.applydate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, senderId, senderName, dept, company, beginDate, endDate, type, processid, replacement, remark, cause, leaveDays, applydate);
    }

    @Basic
    @Column(name = "isend")
    public Integer getIsend() {
        return isend;
    }

    public void setIsend(Integer isend) {
        this.isend = isend;
    }
}
