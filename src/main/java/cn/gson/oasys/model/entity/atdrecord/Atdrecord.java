package cn.gson.oasys.model.entity.atdrecord;

import javax.persistence.*;

@Entity
@Table(name="Atdrecord")
public class Atdrecord {

    @Id
    @Column(name="serialid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serialid;
    @Column(name="empliD")
    private String empliD;
    @Column(name="cardno")
    private String cardno;
    @Column(name="recdate")
    private String recdate;
    @Column(name="rectime")
    private String rectime;
    @Column(name="isauto")
    private String isauto;
    @Column(name="verifymode")
    private Integer verifymode;
    @Column(name="equno")
    private String equno;
    @Column(name="inouttype")
    private Integer inouttype;
    @Column(name="operid")
    private String operid;
    @Column(name="operdate")
    private String operdate;
    @Column(name="remark")
    private String remark;
    @Column(name="checker")
    private String checker;
    @Column(name="checkdate")
    private String checkdate;
    @Column(name="personalrec")
    private Integer personalrec;


    @Column(name="morning")
    private String morning;

    @Column(name="evening")
    private String evening;

    @Column(name="flag")
    private Integer flag;



    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getEvening() {
        return evening;
    }

    public void setEvening(String evening) {
        this.evening = evening;
    }


    public Integer getSerialid() {
        return serialid;
    }

    public void setSerialid(Integer serialid) {
        this.serialid = serialid;
    }

    public String getEmpliD() {
        return empliD;
    }

    public void setEmpliD(String empliD) {
        this.empliD = empliD;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getRecdate() {
        return recdate;
    }

    public void setRecdate(String recdate) {
        this.recdate = recdate;
    }

    public String getRectime() {
        return rectime;
    }

    public void setRectime(String rectime) {
        this.rectime = rectime;
    }

    public String getIsauto() {
        return isauto;
    }

    public void setIsauto(String isauto) {
        this.isauto = isauto;
    }

    public Integer getVerifymode() {
        return verifymode;
    }

    public void setVerifymode(Integer verifymode) {
        this.verifymode = verifymode;
    }

    public String getEquno() {
        return equno;
    }

    public void setEquno(String equno) {
        this.equno = equno;
    }

    public Integer getInouttype() {
        return inouttype;
    }

    public void setInouttype(Integer inOuttype) {
        this.inouttype = inOuttype;
    }

    public String getOperid() {
        return operid;
    }

    public void setOperid(String operid) {
        this.operid = operid;
    }

    public String getOperdate() {
        return operdate;
    }

    public void setOperdate(String operdate) {
        this.operdate = operdate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(String checkdate) {
        this.checkdate = checkdate;
    }

    public Integer getPersonalrec() {
        return personalrec;
    }

    public void setPersonalrec(Integer personalrec) {
        this.personalrec = personalrec;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
