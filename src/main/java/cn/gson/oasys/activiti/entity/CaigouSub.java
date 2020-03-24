package cn.gson.oasys.activiti.entity;

import cn.gson.oasys.model.entity.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "process_caigou_sub")
public class CaigouSub {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "supplier")
    private String supplier;
    @Column(name = "norms")
    private String norms;
    @Column(name = "price")
    private BigDecimal price;



    @ManyToOne
    @JoinColumn(name="caigouid")
    private Caigou caigou;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }


    public String getNorms() {
        return norms;
    }

    public void setNorms(String norms) {
        this.norms = norms;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Caigou getCaigou() {
        return caigou;
    }

    public void setCaigou(Caigou caigou) {
        this.caigou = caigou;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaigouSub caigouSub = (CaigouSub) o;
        return id == caigouSub.id &&
                Objects.equals(supplier, caigouSub.supplier) &&
                Objects.equals(norms, caigouSub.norms) &&
                Objects.equals(price, caigouSub.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supplier, norms, price);
    }
}
