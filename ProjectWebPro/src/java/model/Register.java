/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kao-tu
 */
@Entity
@Table(name = "REGISTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Register.findAll", query = "SELECT r FROM Register r")
    , @NamedQuery(name = "Register.findByRegisterId", query = "SELECT r FROM Register r WHERE r.registerId = :registerId")
    , @NamedQuery(name = "Register.findByEmail", query = "SELECT r FROM Register r WHERE r.email = :email")
    , @NamedQuery(name = "Register.findByActivedate", query = "SELECT r FROM Register r WHERE r.activedate = :activedate")
    , @NamedQuery(name = "Register.findByActivatekey", query = "SELECT r FROM Register r WHERE r.activatekey = :activatekey")
    , @NamedQuery(name = "Register.findByPassword", query = "SELECT r FROM Register r WHERE r.password = :password")
    , @NamedQuery(name = "Register.findByRegdate", query = "SELECT r FROM Register r WHERE r.regdate = :regdate")})
public class Register implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "REGISTER_ID")
    private Integer registerId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACTIVEDATE")
    private String activedate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ACTIVATEKEY")
    private String activatekey;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PASSWORD")
    private int password;
    @Column(name = "REGDATE")
    @Temporal(TemporalType.DATE)
    private Date regdate;
    @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ACCOUNT_ID")
    @ManyToOne(optional = false)
    private Account accountId;

    public Register() {
    }

    public Register(Integer registerId) {
        this.registerId = registerId;
    }

    public Register(Integer registerId, String email, String activedate, String activatekey, int password) {
        this.registerId = registerId;
        this.email = email;
        this.activedate = activedate;
        this.activatekey = activatekey;
        this.password = password;
    }

    public Register(String email, String password) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getRegisterId() {
        return registerId;
    }

    public void setRegisterId(Integer registerId) {
        this.registerId = registerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActivedate() {
        return activedate;
    }

    public void setActivedate(String activedate) {
        this.activedate = activedate;
    }

    public String getActivatekey() {
        return activatekey;
    }

    public void setActivatekey(String activatekey) {
        this.activatekey = activatekey;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
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
        hash += (registerId != null ? registerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Register)) {
            return false;
        }
        Register other = (Register) object;
        if ((this.registerId == null && other.registerId != null) || (this.registerId != null && !this.registerId.equals(other.registerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Register[ registerId=" + registerId + " ]";
    }
    
}
