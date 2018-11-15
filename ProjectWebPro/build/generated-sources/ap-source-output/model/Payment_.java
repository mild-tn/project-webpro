package model;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Customer;
import model.PaymentPK;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T20:22:17")
@StaticMetamodel(Payment.class)
public class Payment_ { 

    public static volatile SingularAttribute<Payment, Date> paymentdate;
    public static volatile SingularAttribute<Payment, BigDecimal> amount;
    public static volatile SingularAttribute<Payment, PaymentPK> paymentPK;
    public static volatile SingularAttribute<Payment, Customer> customer;

}