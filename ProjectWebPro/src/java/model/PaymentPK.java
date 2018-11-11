/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Mild-TN
 */
@Embeddable
public class PaymentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "CUSTOMERNUMBER")
    private int customernumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "CARDNUMBER")
    private String cardnumber;

    public PaymentPK() {
    }

    public PaymentPK(int customernumber, String cardnumber) {
        this.customernumber = customernumber;
        this.cardnumber = cardnumber;
    }

    public int getCustomernumber() {
        return customernumber;
    }

    public void setCustomernumber(int customernumber) {
        this.customernumber = customernumber;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customernumber;
        hash += (cardnumber != null ? cardnumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentPK)) {
            return false;
        }
        PaymentPK other = (PaymentPK) object;
        if (this.customernumber != other.customernumber) {
            return false;
        }
        if ((this.cardnumber == null && other.cardnumber != null) || (this.cardnumber != null && !this.cardnumber.equals(other.cardnumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PaymentPK[ customernumber=" + customernumber + ", cardnumber=" + cardnumber + " ]";
    }
    
}
