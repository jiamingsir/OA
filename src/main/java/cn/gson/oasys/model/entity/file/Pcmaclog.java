package cn.gson.oasys.model.entity.file;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import java.util.Date;

@Entity
@Table(name = "aoa_pcmaclog")
public class Pcmaclog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;	//文件id

    @Column(name = "user")
    private String user;

    @Column(name = "downdate")
    private Date downdate;

    @Column(name = "downpath")
    private String downpath;

    @Column(name = "downfile")
    private String downfile;

    @Column(name = "ip")
    private String ip;

    @Column(name = "type")
    private Integer type;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDowndate() {
        return downdate;
    }

    public void setDowndate(Date downdate) {
        this.downdate = downdate;
    }

    public String getDownpath() {
        return downpath;
    }

    public void setDownpath(String downpath) {
        this.downpath = downpath;
    }

    public String getDownfile() {
        return downfile;
    }

    public void setDownfile(String downfile) {
        this.downfile = downfile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
