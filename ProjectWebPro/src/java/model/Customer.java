/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "CUSTOMER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c")
    , @NamedQuery(name = "Customer.findByCustomernumber", query = "SELECT c FROM Customer c WHERE c.customernumber = :customernumber")
    , @NamedQuery(name = "Customer.findByFname", query = "SELECT c FROM Customer c WHERE c.fname = :fname")
    , @NamedQuery(name = "Customer.findByLname", query = "SELECT c FROM Customer c WHERE c.lname = :lname")
    , @NamedQuery(name = "Customer.findByTelno", query = "SELECT c FROM Customer c WHERE c.telno = :telno")
    , @NamedQuery(name = "Customer.findByAddressline1", query = "SELECT c FROM Customer c WHERE c.addressline1 = :addressline1")
    , @NamedQuery(name = "Customer.findByCity", query = "SELECT c FROM Customer c WHERE c.city = :city")
    , @NamedQuery(name = "Customer.findByContry", query = "SELECT c FROM Customer c WHERE c.contry = :contry")
    , @NamedQuery(name = "Customer.findByPostalcode", query = "SELECT c FROM Customer c WHERE c.postalcode = :postalcode")
    , @NamedQuery(name = "Customer.findByCountry", query = "SELECT c FROM Customer c WHERE c.country = :country")
    , @NamedQuery(name = "Customer.findBySex", query = "SELECT c FROM Customer c WHERE c.sex = :sex")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMERNUMBER")
    private Integer customernumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FNAME")
    private String fname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "LNAME")
    private String lname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TELNO")
    private String telno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ADDRESSLINE1")
    private String addressline1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CITY")
    private String city;
    @Size(max = 50)
    @Column(name = "CONTRY")
    private String contry;
    @Size(max = 15)
    @Column(name = "POSTALCODE")
    private String postalcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "COUNTRY")
    private String country;
    @Size(max = 6)
    @Column(name = "SEX")
    private String sex;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountId;

    public Customer() {
    }

    public Customer(Integer customernumber) {
        this.customernumber = customernumber;
    }

    public Customer(Integer customernumber, String fname, String lname, String telno, String addressline1, String city, String country) {
        this.customernumber = customernumber;
        this.fname = fname;
        this.lname = lname;
        this.telno = telno;
        this.addressline1 = addressline1;
        this.city = city;
        this.country = country;
    }

    public Integer getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(Integer customernumber) {
        this.customernumber = customernumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getAddressline1() {
        return addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContry() {
        return contry;
    }

    public void setContry(String contry) {
        this.contry = contry;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customernumber != null ? customernumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customernumber == null && other.customernumber != null) || (this.customernumber != null && !this.customernumber.equals(other.customernumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Customer[ customernumber=" + customernumber + " ]";
    }
    
}
