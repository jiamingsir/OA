package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "peocess_lipin")
public class Lipin {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "money")
    private BigInteger money;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "sender")
    private String sender;
    @Column(name = "senddate")
    private Timestamp senddate;
    @Column(name = "company")
    private String company;
    @Column(name = "dept")
    private String dept;
    @Column(name = "collectname")
    private String collectname;
    @Column(name = "collectdept")
    private String collectdept;
    @Column(name = "gift")
    private String gift;
    @Column(name = "num")
    private Double num;
    @Column(name = "remark")
    private String remark;
    @Column(name = "processinstanceid")
    private Long processinstanceid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getMoney() {
        return money;
    }

    public void setMoney(BigInteger money) {
        this.money = money;
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

    public Timestamp getSenddate() {
        return senddate;
    }

    public void setSenddate(Timestamp senddate) {
        this.senddate = senddate;
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

    public String getCollectname() {
        return collectname;
    }

    public void setCollectname(String collectname) {
        this.collectname = collectname;
    }

    public String getCollectdept() {
        return collectdept;
    }

    public void setCollectdept(String collectdept) {
        this.collectdept = collectdept;
    }

    public String getGift() {
        return gift;
    }

    public void setGift(String gift) {
        this.gift = gift;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }




}
