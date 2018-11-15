package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Account;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T20:22:17")
@StaticMetamodel(Register.class)
public class Register_ { 

    public static volatile SingularAttribute<Register, Date> activatedate;
    public static volatile SingularAttribute<Register, String> password;
    public static volatile SingularAttribute<Register, Integer> registerId;
    public static volatile ListAttribute<Register, Account> accountList;
    public static volatile SingularAttribute<Register, Date> regdate;
    public static volatile SingularAttribute<Register, String> email;
    public static volatile SingularAttribute<Register, String> activatekey;

}