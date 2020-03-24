package cn.gson.oasys.activiti.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "process_recruit")
public class Recruit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(name = "sender")
    private String sender;
    @Column(name = "senderid")
    private Long senderid;
    @Column(name = "senddate")
    private Timestamp senddate;
    @Column(name = "dept")
    private String dept;
    @Column(name = "deptfather")
    private String deptfather;
    @Column(name = "post")
    private String post;
    @Column(name = "joindate")
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date joindate;
    @Column(name = "leader")
    private String leader;
    @Column(name = "nature")
    private String nature;
    @Column(name = "age")
    private Integer age;
    @Column(name = "sex")
    private String sex;
    @Column(name = "education")
    private String education;
    @Column(name = "computer")
    private String computer;
    @Column(name = "english")
    private String english;
    @Column(name = "workexperience")
    private String workexperience;
    @Column(name = "salary")
    private BigDecimal salary;
    @Column(name = "num")
    private Integer num;
    @Column(name = "other")
    private String other;
    @Column(name = "job")
    private String job;
    @Column(name = "skill")
    private String skill;
    @Column(name = "result")
    private String result;
    @Column(name = "resultremark")
    private String resultremark;
    @Column(name = "processinstanceid")
    private Long processinstanceid;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Long getSenderid() {
        return senderid;
    }

    public void setSenderid(Long senderid) {
        this.senderid = senderid;
    }

    public Timestamp getSenddate() {
        return senddate;
    }

    public void setSenddate(Timestamp senddate) {
        this.senddate = senddate;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getDeptfather() {
        return deptfather;
    }

    public void setDeptfather(String deptfather) {
        this.deptfather = deptfather;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getJoindate() {
        return joindate;
    }

    public void setJoindate(Date joindate) {
        this.joindate = joindate;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getWorkexperience() {
        return workexperience;
    }

    public void setWorkexperience(String workexperience) {
        this.workexperience = workexperience;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getResultremark() {
        return resultremark;
    }

    public void setResultremark(String resultremark) {
        this.resultremark = resultremark;
    }

    public Long getProcessinstanceid() {
        return processinstanceid;
    }

    public void setProcessinstanceid(Long processinstanceid) {
        this.processinstanceid = processinstanceid;
    }
}
