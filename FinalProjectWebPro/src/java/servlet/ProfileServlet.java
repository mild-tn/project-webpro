package servlet;

import controller.AccountJpaController;
import controller.CustomerJpaController;
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
import model.Account;
import model.Customer;

/**
 *
 * @author Mild-TN
 */
public class ProfileServlet extends HttpServlet {

    @PersistenceUnit(unitName = "FinalProjectWebProPU")
    EntityManagerFactory emf;
    @Resource
    UserTransaction utx;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            AccountJpaController accountJpaController = new AccountJpaController(utx, emf);
            Account account = (Account) session.getAttribute("account");
            Account accountId = accountJpaController.findAccount(account.getAccountId());
            if (accountId != null) {
                CustomerJpaController customerJpaController = new CustomerJpaController(utx, emf);
                Customer customer = customerJpaController.findCustomer(accountId.getAccountId());
                session.setAttribute("customer", customer);
                getServletContext().getRequestDispatcher("/Profile.jsp").forward(request, response);
            } else {
            }
        } else {
            response.sendRedirect("Login.jsp");
            return;
        }
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
