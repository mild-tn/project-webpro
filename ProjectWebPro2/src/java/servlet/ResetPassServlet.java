/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.AccountJpaController;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.IOException;
import java.io.PrintWriter;
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
import model.Account;
import static model.Account_.email;

/**
 *
 * @author maysmiler
 */
public class ResetPassServlet extends HttpServlet {
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
      HttpSession session = request.getSession(false);
      String emailre = request.getParameter("emailre");
      String passre = request.getParameter("passre");
      String newpass = request.getParameter("newpass");
      String newpasscf = request.getParameter("newpasscf");
    AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
            Account ac = accountJpaCtrl.findByEmail(emailre);
        if (ac!=null) {
             if (emailre!=null&&emailre.equals(ac.getEmail())) {
                 if (passre!=null&&passre.equals(ac.getPassword())) {
                     if (newpass!=null&&newpass.equals(newpasscf)) {
                         Account account = new Account(Integer.BYTES,emailre,ac.getPassword());
                         try {
                             accountJpaCtrl.edit(ac);
                             session.setAttribute("account", account);
                             response.sendRedirect("ResetPassword.jsp");
                             return;
                             
                         } catch (NonexistentEntityException ex) {
                             Logger.getLogger(ResetPassServlet.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (RollbackFailureException ex) {
                             Logger.getLogger(ResetPassServlet.class.getName()).log(Level.SEVERE, null, ex);
                         } catch (Exception ex) {
                             Logger.getLogger(ResetPassServlet.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                     else{
                         session.setAttribute("message","Confirmpassword Invalid");
                     }
                 }else{
                     session.setAttribute("message","Password Invalid");
                 }
            }else{
                 session.setAttribute("message","Email Invalid");
             }
  
        }
        getServletContext().getRequestDispatcher("/Login.jsp").forward(request, response);
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
