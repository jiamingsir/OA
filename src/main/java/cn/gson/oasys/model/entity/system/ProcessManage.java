package cn.gson.oasys.model.entity.system;

import javax.persistence.*;


@Entity
@Table(name = "aoa_process_manage")
public class ProcessManage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "processnum")
    private Integer processnum;
    @Column(name = "processname")
    private String processname;
    @Column(name = "nodenum")
    private Integer nodenum ;
    @Column(name = "nodename")
    private String nodename;
    @Column(name = "nodenamenext")
    private String nodenamenext;
    @Column(name = "nodetype")
    private String nodetype;
    @Column(name = "executorrole")
    private String executorrole;
    @Column(name = "remark")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProcessnum() {
        return processnum;
    }

    public void setProcessnum(Integer processnum) {
        this.processnum = processnum;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public Integer getNodenum() {
        return nodenum;
    }

    public void setNodenum(Integer nodenum) {
        this.nodenum = nodenum;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public String getNodenamenext() {
        return nodenamenext;
    }

    public void setNodenamenext(String nodenamenext) {
        this.nodenamenext = nodenamenext;
    }

    public String getNodetype() {
        return nodetype;
    }

    public void setNodetype(String nodetype) {
        this.nodetype = nodetype;
    }

    public String getExecutorrole() {
        return executorrole;
    }

    public void setExecutorrole(String executorrole) {
        this.executorrole = executorrole;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
