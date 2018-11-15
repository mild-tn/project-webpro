package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Account;
import model.Orderscustomer;
import model.Payment;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T20:22:17")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Integer> customernumber;
    public static volatile SingularAttribute<Customer, String> fname;
    public static volatile SingularAttribute<Customer, String> country;
    public static volatile SingularAttribute<Customer, String> city;
    public static volatile SingularAttribute<Customer, String> contry;
    public static volatile SingularAttribute<Customer, String> sex;
    public static volatile SingularAttribute<Customer, String> telno;
    public static volatile ListAttribute<Customer, Payment> paymentList;
    public static volatile SingularAttribute<Customer, Account> accountId;
    public static volatile SingularAttribute<Customer, String> lname;
    public static volatile SingularAttribute<Customer, String> postalcode;
    public static volatile ListAttribute<Customer, Orderscustomer> orderscustomerList;
    public static volatile SingularAttribute<Customer, String> addressline1;

}