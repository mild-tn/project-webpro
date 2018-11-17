/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import model.Product;

/**
 *
 * @author INT303
 */
public class LineItem implements Serializable {

    private Product product;
    private double salePrice;
    private int quantity;

    public LineItem() {

    }

    public LineItem(Product product) {
        this(product, 1) ;
    }

    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getTotalPrice() {
        double quantity = (double)this.quantity * product.getBuyprice().doubleValue();
        return quantity ;
    }
}
