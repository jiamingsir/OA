package cn.gson.oasys.model.entity.user;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Entity
@Table(name = "aoa_dept_father")
public class DeptFather {
    @Id
    @Column(name = "dept_father_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long deptFatherId;	//部门id

    @Column(name = "dept_father_name")
    @NotEmpty(message="父级部门名称不为空")
    private String deptFatherName;	//部门名字  非空 唯一

    @Column(name = "dept_father_remake")
    private String deptFatherRemake;		//部门备注

    public Long getDeptFatherId() {
        return deptFatherId;
    }

    public void setDeptFatherId(Long deptFatherId) {
        this.deptFatherId = deptFatherId;
    }

    public String getDeptFatherName() {
        return deptFatherName;
    }

    public void setDeptFatherName(String deptFatherName) {
        this.deptFatherName = deptFatherName;
    }

    public String getDeptFatherRemake() {
        return deptFatherRemake;
    }

    public void setDeptFatherRemake(String deptFatherRemake) {
        this.deptFatherRemake = deptFatherRemake;
    }

    @Override
    public String toString() {
        return "DeptFather{" +
                "deptFatherId=" + deptFatherId +
                ", deptFatherName='" + deptFatherName + '\'' +
                ", deptFatherRemake='" + deptFatherRemake + '\'' +
                '}';
    }
}
