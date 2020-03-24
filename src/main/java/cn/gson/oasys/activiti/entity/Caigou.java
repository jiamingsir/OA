package cn.gson.oasys.activiti.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "process_caigou")
public class Caigou {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "senddate")
    private Date senddate;
    @Column(name = "sender")
    private String sender;
    @Column(name = "dept")
    private String dept;
    @Column(name = "goods")
    private String goods;
    @Column(name = "type")
    private String type;
    @Column(name = "reason")
    private String reason;
    @Column(name = "processinstanceid")
    private Long processinstanceid;
    @Column(name = "buyuser")
    private String buyuser;
    @Column(name = "buydept")
    private String buydept;
    @Column(name = "fixed")
    private Integer fixed;
    @Column(name = "allprice")
    private Double allprice;


    @Column(name="sendprice")
    private Double sendprice;



    @OneToMany(cascade=CascadeType.ALL,mappedBy="caigou",orphanRemoval = true)
    private List<CaigouSub> caigouSubList;

    public List<CaigouSub> getCaigouSubList() {
        return caigouSubList;
    }

    public void setCaigouSubList(List<CaigouSub> caigouSubList) {
        this.caigouSubList = caigouSubList;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Long getSenderid() {
        return senderid;
    }

    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }


    public Date getSenddate() {
        return senddate;
    }

    public void setSenddate(Date senddate) {
        this.senddate = senddate;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }


    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


    public Long getProcessInstanceId() {
        return processinstanceid;
    }

    public void setProcessInstanceId(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }


    public String getBuyuser() {
        return buyuser;
    }

    public void setBuyuser(String buyuser) {
        this.buyuser = buyuser;
    }


    public String getBuydept() {
        return buydept;
    }

    public void setBuydept(String buydept) {
        this.buydept = buydept;
    }

    public Double getAllprice() {
        return allprice;
    }

    public void setAllprice(Double allprice) {
        this.allprice = allprice;
    }
    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }
    public Double getSendprice() {
        return sendprice;
    }

    public void setSendprice(Double sendprice) {
        this.sendprice = sendprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caigou caigou = (Caigou) o;
        return id == caigou.id &&
                Objects.equals(senderid, caigou.senderid) &&
                Objects.equals(senddate, caigou.senddate) &&
                Objects.equals(sender, caigou.sender) &&
                Objects.equals(dept, caigou.dept) &&
                Objects.equals(goods, caigou.goods) &&
                Objects.equals(type, caigou.type) &&
                Objects.equals(reason, caigou.reason) &&
                Objects.equals(processinstanceid, caigou.processinstanceid) &&
                Objects.equals(buyuser, caigou.buyuser) &&
                Objects.equals(buydept, caigou.buydept);
    }


}
