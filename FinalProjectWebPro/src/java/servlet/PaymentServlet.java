package servlet;

import controller.HistoryJpaController;
import controller.OrderscustomerJpaController;
import controller.PaymentJpaController;
import controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.History;
import model.Orderscustomer;
import model.Payment;

/**
 *
 * @author Mild-TN
 */
public class PaymentServlet extends HttpServlet {

    @PersistenceUnit(unitName = "FinalProjectWebProPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Orderscustomer orderCusSession = (Orderscustomer) session.getAttribute("orderCus");
        OrderscustomerJpaController orderCusJpaCtrl = new OrderscustomerJpaController(utx, emf);
        Orderscustomer orderCus = orderCusJpaCtrl.findOrderscustomer(1);
        String cardHolder = request.getParameter("cardholder");
        String cardNo = request.getParameter("cardno");
        String exp = request.getParameter("exp");
        String cvv = request.getParameter("cvv");
        if (cardHolder != null && cardHolder.length() > 0) {
            if (cardNo != null && cardNo.length() > 0) {
                if (exp != null) {
                    if (cvv != null && cvv.length() > 0) {
                        PaymentJpaController paymentJpaCtrl = new PaymentJpaController(utx, emf);
                        HistoryJpaController historyJpaCtrl = new HistoryJpaController(utx, emf);
                        Payment payment = paymentJpaCtrl.findPayment(cardNo);
                        boolean checkPay = payment.payMent(orderCus.getTotalprice());
                        if (checkPay) {
                            History history = new History(orderCus.getTotalprice(), new Date(), payment.getBalance(), orderCus.getCustomernumber());
                            try {
                                paymentJpaCtrl.edit(payment);
                                session.setAttribute("payment", payment);
                                historyJpaCtrl.create(history);
                                response.sendRedirect("SucessfulPay.jsp");
                                return;
                            } catch (RollbackFailureException ex) {
                                Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (Exception ex) {
                                Logger.getLogger(PaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    } else {
                        session.setAttribute("message", "CVV Wrong!!");
                    }
                } else {
                    session.setAttribute("message", "EXP Wrong!!");
                }
            } else {
                session.setAttribute("message", "Card Number Wrong!!");
            }
        } else {
            session.setAttribute("message", "Card Holder Wrong!!");
        }
        getServletContext().getRequestDispatcher("/HomePage.jsp").forward(request, response);
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
