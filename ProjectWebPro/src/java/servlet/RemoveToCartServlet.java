/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.ProductJpaController;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
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
import model.OrderdetailPK;
import model.Product;

/**
 *
 * @author Mild-TN
 */
public class RemoveToCartServlet extends HttpServlet {

    @PersistenceUnit(unitName = "ProjectWebProPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String deleteItems = request.getParameter("removeProductCode");
        ProductJpaController productJpaController = new ProductJpaController(utx, emf);
        Product product = productJpaController.findByProductcode(deleteItems);
        System.out.println("-------------"+product);
        if (session != null && session.getAttribute("shoppingCart") != null) {
            Cart cart = (Cart) session.getAttribute("shoppingCart");
            if (product != null) {
                cart.remove(deleteItems);
                session.setAttribute("shoppingCart", cart);
                response.sendRedirect("ProductDetail.jsp");
                return;
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
