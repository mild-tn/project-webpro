package model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.OrderdetailPK;
import model.Orderscustomer;
import model.Product;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-11-15T20:22:17")
@StaticMetamodel(Orderdetail.class)
public class Orderdetail_ { 

    public static volatile SingularAttribute<Orderdetail, Product> product;
    public static volatile SingularAttribute<Orderdetail, OrderdetailPK> orderdetailPK;
    public static volatile SingularAttribute<Orderdetail, Orderscustomer> orderscustomer;
    public static volatile SingularAttribute<Orderdetail, BigDecimal> priceeach;
    public static volatile SingularAttribute<Orderdetail, Short> orderlinenumber;
    public static volatile SingularAttribute<Orderdetail, Integer> quantityordered;

}