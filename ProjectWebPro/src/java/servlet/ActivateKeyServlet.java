/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.AccountJpaController;
import controller.RegisterJpaController;
import controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Account;
import model.Register;

/**
 *
 * @author kao-tu
 */
public class ActivateKeyServlet extends HttpServlet {
@Resource
    UserTransaction utx;
    @PersistenceUnit (unitName = "ProjectWebProPU")
    EntityManagerFactory emf;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Register emailSession = (Register) session.getAttribute("email");
        String activateKey = request.getParameter("activatekey");
        boolean isActivated = false;
        if (emailSession != null && activateKey != null && activateKey.length() > 0) {
            RegisterJpaController regJpaCtrl = new RegisterJpaController(utx, emf);
            AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
            Register register = regJpaCtrl.findRegister(emailSession.getRegisterId());
            System.out.println("regisssssss : "+register.getEmail());
            if (activateKey.equals(register.getActivatekey())) {
                register.setActivatedate(new Date());
                Account account = new Account(register.getRegisterId(),register.getEmail(),register.getPassword());
                System.out.println("Accounttttttttttttttttttttt : "+account);
                try {
                    regJpaCtrl.edit(register);
                    accountJpaCtrl.create(account);
                    isActivated = true;
                    request.setAttribute("isActivated", isActivated);
                    getServletContext().getRequestDispatcher("/HomePage.jsp").forward(request, response);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, "jpa", ex);
                } catch (Exception ex) {
                    Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, "jpa", ex);
                }
            }else{
                //Alert BY JS
                request.setAttribute("messageActivate", "Wrong!!!!! Try Again");
            }
        }
                getServletContext().getRequestDispatcher("/ActivateAccount.jsp").forward(request, response);
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
