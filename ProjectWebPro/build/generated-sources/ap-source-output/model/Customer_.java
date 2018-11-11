package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-10T23:05:42")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Integer> customernumber;
    public static volatile SingularAttribute<Customer, String> fname;
    public static volatile SingularAttribute<Customer, String> country;
    public static volatile SingularAttribute<Customer, Account> accountId;
    public static volatile SingularAttribute<Customer, String> lname;
    public static volatile SingularAttribute<Customer, String> city;
    public static volatile SingularAttribute<Customer, String> contry;
    public static volatile SingularAttribute<Customer, String> postalcode;
    public static volatile SingularAttribute<Customer, String> sex;
    public static volatile SingularAttribute<Customer, String> addressline1;
    public static volatile SingularAttribute<Customer, String> telno;

}