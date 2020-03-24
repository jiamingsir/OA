package cn.gson.oasys.model.entity.role;

import javax.persistence.*;

//员工流程角色关系表
@Entity
@Table(name="aoa_user_role_process")
public class RoleUserProcess {

        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        @Column(name="id")
        private Long Id;//id

        @Column(name="user_name")
        private String userName;//员工名

        /*@Column(name="user_role_process_id")
        private Integer  userRoleProcessId;//员工关联流程角色*/

        @ManyToOne()
        @JoinColumn(name = "user_role_process_id")
        private RoleProcess roleProcess;		//员工关联流程角色	//外键关联

        @Column(name="remake")
        private String Remake;//备注

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /*public Integer getUserRoleProcessId() {
        return userRoleProcessId;
    }

    public void setUserRoleProcessId(Integer userRoleProcessId) {
        this.userRoleProcessId = userRoleProcessId;
    }*/

    public RoleProcess getRoleProcess() {
        return roleProcess;
    }

    public void setRoleProcess(RoleProcess roleProcess) {
        this.roleProcess = roleProcess;
    }

    public String getRemake() {
        return Remake;
    }

    public void setRemake(String remake) {
        Remake = remake;
    }

    @Override
    public String toString() {
        return "RoleUserProcess{" +
                "Id=" + Id +
                ", userName='" + userName + '\'' +
                ", roleProcess=" + roleProcess +
                ", Remake='" + Remake + '\'' +
                '}';
    }
}
