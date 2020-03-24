package cn.gson.oasys.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "process_contractcharter")
public class Contractcharter {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "sender")
    private String sender;
    @Column(name = "senddate")
    private Timestamp senddate;
    @Column(name = "contracttype")
    private String contracttype;
    @Column(name = "contractremark")
    private String contractremark;
    @Column(name = "contractedition")
    private String contractedition;
    @Column(name = "contractnum")
    private Integer contractnum;
    @Column(name = "contractbacknum")
    private Integer contractbacknum;
    @Column(name = "dept")
    private String dept;
    @Column(name = "contractexecutor")
    private String contractexecutor;
    @Column(name = "contractnumber")
    private String contractnumber;
    @Column(name = "firstpayname")
    private String firstpayname;
    @Column(name = "firstpayaddress")
    private String firstpayaddress;
    @Column(name = "firstpaycontacts")
    private String firstpaycontacts;
    @Column(name = "firstpayphone")
    private Integer firstpayphone;
    @Column(name = "firstpaycompanyphone")
    private Integer firstpaycompanyphone;
    @Column(name = "secondpayname")
    private String secondpayname;
    @Column(name = "contractbegindate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date contractbegindate;
    @Column(name = "contractenddate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date contractenddate;
    @Column(name = "year")
    private Integer year;
    @Column(name = "money")
    private BigInteger money;
    @Column(name = "paytype")
    private String paytype;
    @Column(name = "content")
    private String content;
    @Column(name = "media")
    private String media;
    @Column(name = "processinstanceid")
    private Long processinstanceid;


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

    public Timestamp getSenddate() {
        return senddate;
    }

    public void setSenddate(Timestamp senddate) {
        this.senddate = senddate;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getContractremark() {
        return contractremark;
    }

    public void setContractremark(String contractremark) {
        this.contractremark = contractremark;
    }

    public String getContractedition() {
        return contractedition;
    }

    public void setContractedition(String contractedition) {
        this.contractedition = contractedition;
    }

    public Integer getContractnum() {
        return contractnum;
    }

    public void setContractnum(Integer contractnum) {
        this.contractnum = contractnum;
    }

    public Integer getContractbacknum() {
        return contractbacknum;
    }

    public void setContractbacknum(Integer contractbacknum) {
        this.contractbacknum = contractbacknum;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getContractexecutor() {
        return contractexecutor;
    }

    public void setContractexecutor(String contractexecutor) {
        this.contractexecutor = contractexecutor;
    }

    public String getContractnumber() {
        return contractnumber;
    }

    public void setContractnumber(String contractnumber) {
        this.contractnumber = contractnumber;
    }

    public String getFirstpayname() {
        return firstpayname;
    }

    public void setFirstpayname(String firstpayname) {
        this.firstpayname = firstpayname;
    }

    public String getFirstpayaddress() {
        return firstpayaddress;
    }

    public void setFirstpayaddress(String firstpayaddress) {
        this.firstpayaddress = firstpayaddress;
    }

    public String getFirstpaycontacts() {
        return firstpaycontacts;
    }

    public void setFirstpaycontacts(String firstpaycontacts) {
        this.firstpaycontacts = firstpaycontacts;
    }

    public Integer getFirstpayphone() {
        return firstpayphone;
    }

    public void setFirstpayphone(Integer firstpayphone) {
        this.firstpayphone = firstpayphone;
    }

    public Integer getFirstpaycompanyphone() {
        return firstpaycompanyphone;
    }

    public void setFirstpaycompanyphone(Integer firstpaycompanyphone) {
        this.firstpaycompanyphone = firstpaycompanyphone;
    }

    public String getSecondpayname() {
        return secondpayname;
    }

    public void setSecondpayname(String secondpayname) {
        this.secondpayname = secondpayname;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigInteger getMoney() {
        return money;
    }

    public void setMoney(BigInteger money) {
        this.money = money;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }
}
