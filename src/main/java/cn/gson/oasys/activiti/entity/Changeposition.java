package cn.gson.oasys.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "process_changeposition")
public class Changeposition {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "sender")
    private String sender;
    @Column(name = "senddate")
    private Date senddate;
    @Column(name = "name")
    private String name;
    @Column(name = "dept")
    private String dept;
    @Column(name = "position")
    private String position;
    @Column(name = "joindate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date joindate;
    @Column(name = "effectivedate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date effectivedate;
    @Column(name = "contractbegindate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date contractbegindate;
    @Column(name = "contractenddate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date contractenddate;
    @Column(name = "effectiveresult")
    private String effectiveresult;
    @Column(name = "effectivebeforedept")
    private String effectivebeforedept;
    @Column(name = "effectiveafterdept")
    private String effectiveafterdept;
    @Column(name = "effectivebeforeposition")
    private String effectivebeforeposition;
    @Column(name = "effectiveafterposition")
    private String effectiveafterposition;
    @Column(name = "salarybefore")
    private Integer salarybefore;
    @Column(name = "salaryafter")
    private Integer salaryafter;
    @Column(name = "salaryproportion")
    private String salaryproportion;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public Date getEffectivedate() {
        return effectivedate;
    }

    public void setEffectivedate(Date effectivedate) {
        this.effectivedate = effectivedate;
    }

    public Date getContractbegindate() {
        return contractbegindate;
    }

    public void setContractbegindate(Date contractbegindate) {
        this.contractbegindate = contractbegindate;
    }

    public Date getContractenddate() {
        return contractenddate;
    }

    public void setContractenddate(Date contractenddate) {
        this.contractenddate = contractenddate;
    }

    public String getEffectiveresult() {
        return effectiveresult;
    }

    public void setEffectiveresult(String effectiveresult) {
        this.effectiveresult = effectiveresult;
    }

    public String getEffectivebeforedept() {
        return effectivebeforedept;
    }

    public void setEffectivebeforedept(String effectivebeforedept) {
        this.effectivebeforedept = effectivebeforedept;
    }

    public String getEffectiveafterdept() {
        return effectiveafterdept;
    }

    public void setEffectiveafterdept(String effectiveafterdept) {
        this.effectiveafterdept = effectiveafterdept;
    }

    public String getEffectivebeforeposition() {
        return effectivebeforeposition;
    }

    public void setEffectivebeforeposition(String effectivebeforeposition) {
        this.effectivebeforeposition = effectivebeforeposition;
    }

    public String getEffectiveafterposition() {
        return effectiveafterposition;
    }

    public void setEffectiveafterposition(String effectiveafterposition) {
        this.effectiveafterposition = effectiveafterposition;
    }

    public Integer getSalarybefore() {
        return salarybefore;
    }

    public void setSalarybefore(Integer salarybefore) {
        this.salarybefore = salarybefore;
    }

    public Integer getSalaryafter() {
        return salaryafter;
    }

    public void setSalaryafter(Integer salaryafter) {
        this.salaryafter = salaryafter;
    }

    public String getSalaryproportion() {
        return salaryproportion;
    }

    public void setSalaryproportion(String salaryproportion) {
        this.salaryproportion = salaryproportion;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }

    @Column(name = "processinstanceid")
    private Long processinstanceid;



}
