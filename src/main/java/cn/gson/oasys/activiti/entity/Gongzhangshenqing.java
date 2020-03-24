package cn.gson.oasys.activiti.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "process_gongzhang")
public class Gongzhangshenqing {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sendername")
    private String sendername;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "company")
    private String company;
    @Column(name = "dept")
    private String dept;
    @Column(name="applydate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date applydate;
    @Column(name = "num")
    private Integer num;
    @Column(name = "type")
    private String type;
    @Column(name = "typeremark")
    private String typeremark;
    @Column(name = "contract")
    private String contract;
    @Column(name = "contractremark")
    private String contractremark;
    @Column(name = "isbak")
    private Integer isbak;
    @Column(name = "processid")
    private Long processid;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public Long getSenderid() {
        return senderid;
    }

    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Date getApplydate() {
        return applydate;
    }

    public void setApplydate(Date applydate) {
        this.applydate = applydate;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTyperemark() {
        return typeremark;
    }

    public void setTyperemark(String typeremark) {
        this.typeremark = typeremark;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getContractremark() {
        return contractremark;
    }

    public void setContractremark(String contractremark) {
        this.contractremark = contractremark;
    }

    public Integer getIsbak() {
        return isbak;
    }

    public void setIsbak(Integer isbak) {
        this.isbak = isbak;
    }

    public Long getProcessid() {
        return processid;
    }

    public void setProcessid(Long processid) {
        this.processid = processid;
    }
}