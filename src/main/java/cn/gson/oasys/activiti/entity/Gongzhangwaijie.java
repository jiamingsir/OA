package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_gongzhangwaijie")
public class Gongzhangwaijie {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "sender")
    private String sender;
    @Column(name = "dept")
    private String dept;
    @Column(name = "targetdept")
    private String targetdept;
    @Column(name = "something")
    private String something;
    @Column(name = "officialseal")
    private String officialseal;
    @Column(name = "backdate")
    private Timestamp backdate;
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getTargetdept() {
        return targetdept;
    }

    public void setTargetdept(String targetdept) {
        this.targetdept = targetdept;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    public String getOfficialseal() {
        return officialseal;
    }

    public void setOfficialseal(String officialseal) {
        this.officialseal = officialseal;
    }

    public Timestamp getBackdate() {
        return backdate;
    }

    public void setBackdate(Timestamp backdate) {
        this.backdate = backdate;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }

}
