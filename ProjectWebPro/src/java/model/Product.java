/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kao-tu
 */
@Entity
@Table(name = "PRODUCT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
    , @NamedQuery(name = "Product.findByProductcode", query = "SELECT p FROM Product p WHERE p.productcode = :productcode")
    , @NamedQuery(name = "Product.findByProductname", query = "SELECT p FROM Product p WHERE p.productname = :productname")
    , @NamedQuery(name = "Product.findByProductcolor", query = "SELECT p FROM Product p WHERE p.productcolor = :productcolor")
    , @NamedQuery(name = "Product.findByProducttype", query = "SELECT p FROM Product p WHERE p.producttype = :producttype")
    , @NamedQuery(name = "Product.findBySex", query = "SELECT p FROM Product p WHERE p.sex = :sex")
    , @NamedQuery(name = "Product.findByWarrenty", query = "SELECT p FROM Product p WHERE p.warrenty = :warrenty")
    , @NamedQuery(name = "Product.findBySize", query = "SELECT p FROM Product p WHERE p.size = :size")
    , @NamedQuery(name = "Product.findByWeight", query = "SELECT p FROM Product p WHERE p.weight = :weight")
    , @NamedQuery(name = "Product.findByQuantityinstock", query = "SELECT p FROM Product p WHERE p.quantityinstock = :quantityinstock")
    , @NamedQuery(name = "Product.findByBuyprice", query = "SELECT p FROM Product p WHERE p.buyprice = :buyprice")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "PRODUCTCODE")
    private String productcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "PRODUCTNAME")
    private String productname;
    @Size(max = 15)
    @Column(name = "PRODUCTCOLOR")
    private String productcolor;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "PRODUCTDESCRIPTION")
    private String productdescription;
    @Size(max = 20)
    @Column(name = "PRODUCTTYPE")
    private String producttype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "SEX")
    private String sex;
    @Size(max = 20)
    @Column(name = "WARRENTY")
    private String warrenty;
    @Size(max = 30)
    @Column(name = "SIZE")
    private String size;
    @Size(max = 15)
    @Column(name = "WEIGHT")
    private String weight;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QUANTITYINSTOCK")
    private short quantityinstock;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "BUYPRICE")
    private BigDecimal buyprice;

    public Product() {
    }

    public Product(String productcode) {
        this.productcode = productcode;
    }

    public Product(String productcode, String productname, String productdescription, String sex, short quantityinstock, BigDecimal buyprice) {
        this.productcode = productcode;
        this.productname = productname;
        this.productdescription = productdescription;
        this.sex = sex;
        this.quantityinstock = quantityinstock;
        this.buyprice = buyprice;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductcolor() {
        return productcolor;
    }

    public void setProductcolor(String productcolor) {
        this.productcolor = productcolor;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWarrenty() {
        return warrenty;
    }

    public void setWarrenty(String warrenty) {
        this.warrenty = warrenty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public short getQuantityinstock() {
        return quantityinstock;
    }

    public void setQuantityinstock(short quantityinstock) {
        this.quantityinstock = quantityinstock;
    }

    public BigDecimal getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(BigDecimal buyprice) {
        this.buyprice = buyprice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productcode != null ? productcode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.productcode == null && other.productcode != null) || (this.productcode != null && !this.productcode.equals(other.productcode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Product[ productcode=" + productcode + " ]";
    }
    
}
