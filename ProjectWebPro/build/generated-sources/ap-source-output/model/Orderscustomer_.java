package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Customer;
import model.Orderdetail;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T20:22:17")
@StaticMetamodel(Orderscustomer.class)
public class Orderscustomer_ { 

    public static volatile SingularAttribute<Orderscustomer, Customer> customernumber;
    public static volatile ListAttribute<Orderscustomer, Orderdetail> orderdetailList;
    public static volatile SingularAttribute<Orderscustomer, Integer> ordernumber;
    public static volatile SingularAttribute<Orderscustomer, Date> shippeddate;
    public static volatile SingularAttribute<Orderscustomer, Date> orderdate;
    public static volatile SingularAttribute<Orderscustomer, Date> requireddate;
    public static volatile SingularAttribute<Orderscustomer, String> status;

}