package cn.gson.oasys.activiti.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "process_buka")
public class Buka {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name="sendername")
    private String sendername;
    @Column(name = "dept")
    private String dept;
    @Column(name = "nowdate")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nowdate;
    @Column(name = "company")
    private String company;
    @Column(name = "reason")
    private String reason;
    @Column(name = "nodatebegin")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nodatebegin;
    @Column(name = "nodateend")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date nodateend;
    @Column(name = "remark")
    private String remark;
    @Column(name = "processid")
    private Long processid;
    @Column(name = "file_file")
    private String fileFile;


    public Buka() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderid() {
        return senderid;
    }

    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Date getNowdate() {
        return nowdate;
    }

    public void setNowdate(Date nowdate) {
        this.nowdate = nowdate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getNodatebegin() {
        return nodatebegin;
    }

    public void setNodatebegin(Date nodatebegin) {
        this.nodatebegin = nodatebegin;
    }

    public Date getNodateend() {
        return nodateend;
    }

    public void setNodateend(Date nodateend) {
        this.nodateend = nodateend;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProcessid() {
        return processid;
    }

    public void setProcessid(Long processid) {
        this.processid = processid;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }


    public String getFileFile() {
        return fileFile;
    }

    public void setFileFile(String fileFile) {
        this.fileFile = fileFile;
    }
}
