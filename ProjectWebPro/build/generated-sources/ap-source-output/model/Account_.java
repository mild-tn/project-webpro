package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Customer;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-10T23:05:42")
@StaticMetamodel(Account.class)
public class Account_ { 

    public static volatile SingularAttribute<Account, Integer> accountId;
    public static volatile SingularAttribute<Account, String> password;
    public static volatile ListAttribute<Account, Customer> customerList;
    public static volatile SingularAttribute<Account, String> email;
    public static volatile SingularAttribute<Account, String> telno;

}