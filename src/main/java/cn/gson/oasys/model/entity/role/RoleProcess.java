package cn.gson.oasys.model.entity.role;

import javax.persistence.*;

@Entity
@Table(name="aoa_role_process")
public class RoleProcess {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;//角色id

    @Column(name="pro_role_name")
    private String proRoleName;//角色名

    @Column(name="pro_role_value")
    private Integer  proRoleValue;//角色权限值

    @Column(name="pro_role_remake")
    private String proRoleRemake;//角色备注

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getProRoleName() {
        return proRoleName;
    }

    public void setProRoleName(String proRoleName) {
        this.proRoleName = proRoleName;
    }

    public Integer getProRoleValue() {
        return proRoleValue;
    }

    public void setProRoleValue(Integer proRoleValue) {
        this.proRoleValue = proRoleValue;
    }

    public String getProRoleRemake() {
        return proRoleRemake;
    }

    public void setProRoleRemake(String proRoleRemake) {
        this.proRoleRemake = proRoleRemake;
    }

    @Override
    public String toString() {
        return "RoleProcess{" +
                "Id=" + Id +
                ", proRoleName='" + proRoleName + '\'' +
                ", proRoleValue=" + proRoleValue +
                ", proRoleRemake='" + proRoleRemake + '\'' +
                '}';
    }
}
