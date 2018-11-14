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
@Table(name = "PAYMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payment.findAll", query = "SELECT p FROM Payment p")
    , @NamedQuery(name = "Payment.findByCardnumber", query = "SELECT p FROM Payment p WHERE p.cardnumber = :cardnumber")
    , @NamedQuery(name = "Payment.findByCardholder", query = "SELECT p FROM Payment p WHERE p.cardholder = :cardholder")
    , @NamedQuery(name = "Payment.findByCvv", query = "SELECT p FROM Payment p WHERE p.cvv = :cvv")
    , @NamedQuery(name = "Payment.findByExpireMonth", query = "SELECT p FROM Payment p WHERE p.expireMonth = :expireMonth")
    , @NamedQuery(name = "Payment.findByExpireYear", query = "SELECT p FROM Payment p WHERE p.expireYear = :expireYear")
    , @NamedQuery(name = "Payment.findByPaymentdate", query = "SELECT p FROM Payment p WHERE p.paymentdate = :paymentdate")
    , @NamedQuery(name = "Payment.findByBalance", query = "SELECT p FROM Payment p WHERE p.balance = :balance")})
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CARDNUMBER")
    private String cardnumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CARDHOLDER")
    private String cardholder;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "CVV")
    private String cvv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "EXPIRE_MONTH")
    private String expireMonth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "EXPIRE_YEAR")
    private String expireYear;
    @Column(name = "PAYMENTDATE")
    @Temporal(TemporalType.DATE)
    private Date paymentdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BALANCE")
    private int balance;
    @JoinColumn(name = "CUSTOMERNUMBER", referencedColumnName = "CUSTOMERNUMBER")
    @ManyToOne(optional = false)
    private Customer customernumber;

    public Payment() {
    }

    public Payment(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Payment(String cardnumber, String cardholder, String cvv, String expireMonth, String expireYear, int balance) {
        this.cardnumber = cardnumber;
        this.cardholder = cardholder;
        this.cvv = cvv;
        this.expireMonth = expireMonth;
        this.expireYear = expireYear;
        this.balance = balance;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getExpireMonth() {
        return expireMonth;
    }

    public void setExpireMonth(String expireMonth) {
        this.expireMonth = expireMonth;
    }

    public String getExpireYear() {
        return expireYear;
    }

    public void setExpireYear(String expireYear) {
        this.expireYear = expireYear;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Customer getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(Customer customernumber) {
        this.customernumber = customernumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cardnumber != null ? cardnumber.hashCode() : 0);
        return hash;
    }
    
    public boolean payMent(int pay){
        if(pay > 0 && this.balance >= pay){
            this.balance -= pay;
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.cardnumber == null && other.cardnumber != null) || (this.cardnumber != null && !this.cardnumber.equals(other.cardnumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Payment[ cardnumber=" + cardnumber + " ]";
    }
    
}
