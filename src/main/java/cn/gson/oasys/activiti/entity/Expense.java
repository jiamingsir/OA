package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_expense", schema = "xyoa")
public class Expense {
    private long id;
    private String feeName;
    private Long senderId;
    private String senderName;
    private String dept;
    private String company;
    private String feeContent;
    private String feeFile;
    private BigDecimal feeFee;
    private String feeType;
    private String feePaytype;
    private String contractId;
    private String contractFee;
    private BigDecimal contractPaynew;
    private BigDecimal contractPaysum;
    private String remark;
    private Long processid;
    private Timestamp feeDate;
    private Integer fileRadio;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "fee_Name")
    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
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
    @Column(name = "fee_Content")
    public String getFeeContent() {
        return feeContent;
    }

    public void setFeeContent(String feeContent) {
        this.feeContent = feeContent;
    }

    @Basic
    @Column(name = "fee_File")
    public String getFeeFile() {
        return feeFile;
    }

    public void setFeeFile(String feeFile) {
        this.feeFile = feeFile;
    }

    @Basic
    @Column(name = "fee_Fee")
    public BigDecimal getFeeFee() {
        return feeFee;
    }

    public void setFeeFee(BigDecimal feeFee) {
        this.feeFee = feeFee;
    }

    @Basic
    @Column(name = "fee_Type")
    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    @Basic
    @Column(name = "fee_Paytype")
    public String getFeePaytype() {
        return feePaytype;
    }

    public void setFeePaytype(String feePaytype) {
        this.feePaytype = feePaytype;
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
    @Column(name = "contract_Fee")
    public String getContractFee() {
        return contractFee;
    }

    public void setContractFee(String contractFee) {
        this.contractFee = contractFee;
    }

    @Basic
    @Column(name = "contract_Paynew")
    public BigDecimal getContractPaynew() {
        return contractPaynew;
    }

    public void setContractPaynew(BigDecimal contractPaynew) {
        this.contractPaynew = contractPaynew;
    }

    @Basic
    @Column(name = "contract_Paysum")
    public BigDecimal getContractPaysum() {
        return contractPaysum;
    }

    public void setContractPaysum(BigDecimal contractPaysum) {
        this.contractPaysum = contractPaysum;
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
    @Column(name = "fee_Date")
    public Timestamp getFeeDate() {
        return feeDate;
    }

    public void setFeeDate(Timestamp feeDate) {
        this.feeDate = feeDate;
    }

    @Basic
    @Column(name = "file_Radio")
    public Integer getFileRadio() {
        return fileRadio;
    }

    public void setFileRadio(Integer fileRadio) {
        this.fileRadio = fileRadio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return id == expense.id &&
                Objects.equals(feeName, expense.feeName) &&
                Objects.equals(senderId, expense.senderId) &&
                Objects.equals(senderName, expense.senderName) &&
                Objects.equals(dept, expense.dept) &&
                Objects.equals(company, expense.company) &&
                Objects.equals(feeContent, expense.feeContent) &&
                Objects.equals(feeFile, expense.feeFile) &&
                Objects.equals(feeFee, expense.feeFee) &&
                Objects.equals(feeType, expense.feeType) &&
                Objects.equals(feePaytype, expense.feePaytype) &&
                Objects.equals(contractId, expense.contractId) &&
                Objects.equals(contractFee, expense.contractFee) &&
                Objects.equals(contractPaynew, expense.contractPaynew) &&
                Objects.equals(contractPaysum, expense.contractPaysum) &&
                Objects.equals(remark, expense.remark) &&
                Objects.equals(processid, expense.processid) &&
                Objects.equals(feeDate, expense.feeDate) &&
                Objects.equals(fileRadio, expense.fileRadio);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, feeName, senderId, senderName, dept, company, feeContent, feeFile, feeFee, feeType, feePaytype, contractId, contractFee, contractPaynew, contractPaysum, remark, processid, feeDate, fileRadio);
    }
}
