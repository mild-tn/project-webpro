/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.ProductJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Cart;
import model.Orderdetail;
import model.Product;

/**
 *
 * @author Mild-TN
 */
public class AddToCartServlet extends HttpServlet {

    @PersistenceUnit(unitName = "FinalProjectWebProPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       HttpSession session = request.getSession();
        if (session.getAttribute("shoppingCart") == null) {
            session.setAttribute("shoppingCart", new Cart());
        }
        String item = request.getParameter("addProductCode");
        Cart cart = (Cart) session.getAttribute("shoppingCart");
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        Product product = productJpaCtrl.findProduct(item);
        session.setAttribute("productDetail", product);
        Orderdetail orderDetail = new Orderdetail(1, item);
        orderDetail.setProduct(product);
        orderDetail.setQuantityordered(1);
        cart.add(product);
        getServletContext().getRequestDispatcher("/ProductDetail.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
