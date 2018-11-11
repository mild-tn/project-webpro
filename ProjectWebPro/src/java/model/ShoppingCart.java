/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.rmi.server.ObjID;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Product;

/**
 *
 * @author INT303
 */
public class ShoppingCart implements Serializable {

    private Map<String, LineItem> cart;//เขียน map เพื่อให้เข้าถึงข้อมูลได้ง่าย //ทำงานกับ map ต้องมี key ตลอดนะจ๊ะ
    
    public ShoppingCart() {
        cart = new HashMap();
    }
    
    public void add(Product p) {
        LineItem line = cart.get(p.getProductcode());
        if (line == null) {
            cart.put(p.getProductcode(), new LineItem(p)); // check ว่ามี สินค้าทมีใน ตะกร้าไหม ( มี ใน Map มั้ย ?)
        } else {
            line.setQuantity(line.getQuantity() + 1);//มีอยู่แล้ว จะ ไปขอของเดิมมาแล้วบวก 1 
        }
    }
    
    public void remove(Product p) {
        this.remove(p.getProductcode()); //รับ ProductCode ที่เป็น String เข้ามา แล้วส่งไป method ล่าง เพื่อทำการลบ
    }
    
    public void remove(String productCode) {
        cart.remove(productCode); //ลบด้วย String ที่ method remove อันแรกส่งมา
    }
    
    public double getTotalPrice() {
        double sum = 0;
        Collection<LineItem> lineItems = cart.values();
        for (LineItem lineItem : lineItems) {
            sum += lineItem.getTotalPrice();
        }
        return sum;
    }
    
    public int getTotalQuantity(){
        int sum = 0;
        Collection<LineItem> totalQuantity = cart.values(); // เป็น super class ของ List
        for (LineItem lineItem : totalQuantity) {
            sum += lineItem.getQuantity();
        }
        return sum;
    }
    
    public List<LineItem> getLineItems(){
        return new ArrayList(cart.values());//ยัด cart.values เข้าไปใน ArrayList
    }
   
}
