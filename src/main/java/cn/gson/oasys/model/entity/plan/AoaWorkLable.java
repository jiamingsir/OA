package cn.gson.oasys.model.entity.plan;

import cn.gson.oasys.model.entity.user.Dept;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "aoa_work_lable", schema = "xyoa", catalog = "")
public class AoaWorkLable {
    private long id;
    private Dept deptId;
    private String workLabel;
    private String remark;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    @ManyToOne
    @JoinColumn(name="dept_Id")
    public Dept getDeptId() {
        return deptId;
    }

    public void setDeptId(Dept deptId) {
        this.deptId = deptId;
    }

    @Basic
    @Column(name = "work_Label")
    public String getWorkLabel() {
        return workLabel;
    }

    public void setWorkLabel(String workLabel) {
        this.workLabel = workLabel;
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
        AoaWorkLable that = (AoaWorkLable) o;
        return id == that.id &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(workLabel, that.workLabel) &&
                Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, deptId, workLabel, remark);
    }
}
