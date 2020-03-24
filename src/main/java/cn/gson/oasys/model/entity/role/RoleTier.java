package cn.gson.oasys.model.entity.role;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "aoa_role_tier", schema = "xyoa", catalog = "")
public class RoleTier {
    private long id;
    private Long roleprofatherid;
    private Long roleproid;
    private String remark;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "roleprofatherid")
    public Long getRoleprofatherid() {
        return roleprofatherid;
    }

    public void setRoleprofatherid(Long roleprofatherid) {
        this.roleprofatherid = roleprofatherid;
    }

    @Basic
    @Column(name = "roleproid")
    public Long getRoleproid() {
        return roleproid;
    }

    public void setRoleproid(Long roleproid) {
        this.roleproid = roleproid;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleTier roleTier = (RoleTier) o;
        return id == roleTier.id &&
                Objects.equals(roleprofatherid, roleTier.roleprofatherid) &&
                Objects.equals(roleproid, roleTier.roleproid) &&
                Objects.equals(remark, roleTier.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, roleprofatherid, roleproid, remark);
    }
}
