package cn.gson.oasys.activiti.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "process_interview")
public class Interview {
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
    @Column(name = "name")
    @NotNull(message = "姓名不能为空")
    private String name;
    @Column(name = "dept")
    private String dept;
    @Column(name = "joindate")
    private Timestamp joindate;
    @Column(name = "interviewdate")
    private Timestamp interviewdate;
    @Column(name = "processinstanceid")
    private Long processinstanceid;
    @Column(name = "file")
    private String file;
    @Column(name = "type")
    @NotNull(message = "面谈类型不能为空")
    private String type;


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

    public Timestamp getJoindate() {
        return joindate;
    }

    public void setJoindate(Timestamp joindate) {
        this.joindate = joindate;
    }

    public Timestamp getInterviewdate() {
        return interviewdate;
    }

    public void setInterviewdate(Timestamp interviewdate) {
        this.interviewdate = interviewdate;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
