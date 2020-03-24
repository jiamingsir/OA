package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_kaipiao", schema = "xyoa", catalog = "")
public class Kaipiao {
    private long id;
    private Long senderId;
    private String senderName;
    private String company;
    private String dept;
    private Timestamp kpDate;
    private String contractId;
    private String clientName;
    private BigDecimal contractFee;
    private Integer kpQishu;
    private Integer kpSumqishu;
    private String kpThisfee;
    private String kpSumfee;
    private String fapiaoType;
    private String kpContent;
    private String fapiaoNumber;
    private String kper;
    private String shepier;
    private String qianshouer;
    private Timestamp qianshouDate;
    private String remark;
    private Long processid;
    private String kpFile;

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
    @Column(name = "company")
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
    @Column(name = "kp_Date")
    public Timestamp getKpDate() {
        return kpDate;
    }

    public void setKpDate(Timestamp kpDate) {
        this.kpDate = kpDate;
    }

    @Basic
    @Column(name = "contract_Id")
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "client_Name")
    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Basic
    @Column(name = "contract_Fee")
    public BigDecimal getContractFee() {
        return contractFee;
    }

    public void setContractFee(BigDecimal contractFee) {
        this.contractFee = contractFee;
    }

    @Basic
    @Column(name = "kp_Qishu")
    public Integer getKpQishu() {
        return kpQishu;
    }

    public void setKpQishu(Integer kpQishu) {
        this.kpQishu = kpQishu;
    }

    @Basic
    @Column(name = "kp_Sumqishu")
    public Integer getKpSumqishu() {
        return kpSumqishu;
    }

    public void setKpSumqishu(Integer kpSumqishu) {
        this.kpSumqishu = kpSumqishu;
    }

    @Basic
    @Column(name = "kp_Thisfee")
    public String getKpThisfee() {
        return kpThisfee;
    }

    public void setKpThisfee(String kpThisfee) {
        this.kpThisfee = kpThisfee;
    }

    @Basic
    @Column(name = "kp_Sumfee")
    public String getKpSumfee() {
        return kpSumfee;
    }

    public void setKpSumfee(String kpSumfee) {
        this.kpSumfee = kpSumfee;
    }

    @Basic
    @Column(name = "fapiao_Type")
    public String getFapiaoType() {
        return fapiaoType;
    }

    public void setFapiaoType(String fapiaoType) {
        this.fapiaoType = fapiaoType;
    }

    @Basic
    @Column(name = "kp_Content")
    public String getKpContent() {
        return kpContent;
    }

    public void setKpContent(String kpContent) {
        this.kpContent = kpContent;
    }

    @Basic
    @Column(name = "fapiao_Number")
    public String getFapiaoNumber() {
        return fapiaoNumber;
    }

    public void setFapiaoNumber(String fapiaoNumber) {
        this.fapiaoNumber = fapiaoNumber;
    }

    @Basic
    @Column(name = "kper")
    public String getKper() {
        return kper;
    }

    public void setKper(String kper) {
        this.kper = kper;
    }

    @Basic
    @Column(name = "shepier")
    public String getShepier() {
        return shepier;
    }

    public void setShepier(String shepier) {
        this.shepier = shepier;
    }

    @Basic
    @Column(name = "qianshouer")
    public String getQianshouer() {
        return qianshouer;
    }

    public void setQianshouer(String qianshouer) {
        this.qianshouer = qianshouer;
    }

    @Basic
    @Column(name = "qianshou_Date")
    public Timestamp getQianshouDate() {
        return qianshouDate;
    }

    public void setQianshouDate(Timestamp qianshouDate) {
        this.qianshouDate = qianshouDate;
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
    @Column(name = "kp_File")
    public String getKpFile() {
        return kpFile;
    }

    public void setKpFile(String kpFile) {
        this.kpFile = kpFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kaipiao kaipiao = (Kaipiao) o;
        return id == kaipiao.id &&
                Objects.equals(senderId, kaipiao.senderId) &&
                Objects.equals(senderName, kaipiao.senderName) &&
                Objects.equals(company, kaipiao.company) &&
                Objects.equals(dept, kaipiao.dept) &&
                Objects.equals(kpDate, kaipiao.kpDate) &&
                Objects.equals(contractId, kaipiao.contractId) &&
                Objects.equals(clientName, kaipiao.clientName) &&
                Objects.equals(contractFee, kaipiao.contractFee) &&
                Objects.equals(kpQishu, kaipiao.kpQishu) &&
                Objects.equals(kpSumqishu, kaipiao.kpSumqishu) &&
                Objects.equals(kpThisfee, kaipiao.kpThisfee) &&
                Objects.equals(kpSumfee, kaipiao.kpSumfee) &&
                Objects.equals(fapiaoType, kaipiao.fapiaoType) &&
                Objects.equals(kpContent, kaipiao.kpContent) &&
                Objects.equals(fapiaoNumber, kaipiao.fapiaoNumber) &&
                Objects.equals(kper, kaipiao.kper) &&
                Objects.equals(shepier, kaipiao.shepier) &&
                Objects.equals(qianshouer, kaipiao.qianshouer) &&
                Objects.equals(qianshouDate, kaipiao.qianshouDate) &&
                Objects.equals(remark, kaipiao.remark) &&
                Objects.equals(processid, kaipiao.processid) &&
                Objects.equals(kpFile, kaipiao.kpFile);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, senderId, senderName, company, dept, kpDate, contractId, clientName, contractFee, kpQishu, kpSumqishu, kpThisfee, kpSumfee, fapiaoType, kpContent, fapiaoNumber, kper, shepier, qianshouer, qianshouDate, remark, processid, kpFile);
    }
}
