package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-10T23:05:42")
@StaticMetamodel(Register.class)
public class Register_ { 

    public static volatile SingularAttribute<Register, Account> accountId;
    public static volatile SingularAttribute<Register, Integer> password;
    public static volatile SingularAttribute<Register, Integer> registerId;
    public static volatile SingularAttribute<Register, Date> regdate;
    public static volatile SingularAttribute<Register, String> email;
    public static volatile SingularAttribute<Register, String> activedate;
    public static volatile SingularAttribute<Register, String> activatekey;

}