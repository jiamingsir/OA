package cn.gson.oasys.model.entity.user;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "aoa_holidayset", schema = "xyoa", catalog = "")
public class Holidayset {
    private int id;
    private Integer acomdays;
    private Integer ayudays;
    private Integer bcomdays;
    private Integer byudays;
    private String remark;
    private Long userid;
    private String username;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "acomdays")
    public Integer getAcomdays() {
        return acomdays;
    }

    public void setAcomdays(Integer acomdays) {
        this.acomdays = acomdays;
    }

    @Basic
    @Column(name = "ayudays")
    public Integer getAyudays() {
        return ayudays;
    }

    public void setAyudays(Integer ayudays) {
        this.ayudays = ayudays;
    }

    @Basic
    @Column(name = "bcomdays")
    public Integer getBcomdays() {
        return bcomdays;
    }

    public void setBcomdays(Integer bcomdays) {
        this.bcomdays = bcomdays;
    }

    @Basic
    @Column(name = "byudays")
    public Integer getByudays() {
        return byudays;
    }

    public void setByudays(Integer byudays) {
        this.byudays = byudays;
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
    @Column(name = "userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Holidayset that = (Holidayset) o;
        return id == that.id &&
                Objects.equals(acomdays, that.acomdays) &&
                Objects.equals(ayudays, that.ayudays) &&
                Objects.equals(bcomdays, that.bcomdays) &&
                Objects.equals(byudays, that.byudays) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(userid, that.userid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, acomdays, ayudays, bcomdays, byudays, remark, userid);
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
