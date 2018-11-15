/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Account;
import model.Payment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Customer;
import model.Orderscustomer;
import model.History;

/**
 *
 * @author Mild-TN
 */
public class CustomerJpaController implements Serializable {

    public CustomerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (customer.getPaymentList() == null) {
            customer.setPaymentList(new ArrayList<Payment>());
        }
        if (customer.getOrderscustomerList() == null) {
            customer.setOrderscustomerList(new ArrayList<Orderscustomer>());
        }
        if (customer.getHistoryList() == null) {
            customer.setHistoryList(new ArrayList<History>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account accountId = customer.getAccountId();
            if (accountId != null) {
                accountId = em.getReference(accountId.getClass(), accountId.getAccountId());
                customer.setAccountId(accountId);
            }
            List<Payment> attachedPaymentList = new ArrayList<Payment>();
            for (Payment paymentListPaymentToAttach : customer.getPaymentList()) {
                paymentListPaymentToAttach = em.getReference(paymentListPaymentToAttach.getClass(), paymentListPaymentToAttach.getCardnumber());
                attachedPaymentList.add(paymentListPaymentToAttach);
            }
            customer.setPaymentList(attachedPaymentList);
            List<Orderscustomer> attachedOrderscustomerList = new ArrayList<Orderscustomer>();
            for (Orderscustomer orderscustomerListOrderscustomerToAttach : customer.getOrderscustomerList()) {
                orderscustomerListOrderscustomerToAttach = em.getReference(orderscustomerListOrderscustomerToAttach.getClass(), orderscustomerListOrderscustomerToAttach.getOrdernumber());
                attachedOrderscustomerList.add(orderscustomerListOrderscustomerToAttach);
            }
            customer.setOrderscustomerList(attachedOrderscustomerList);
            List<History> attachedHistoryList = new ArrayList<History>();
            for (History historyListHistoryToAttach : customer.getHistoryList()) {
                historyListHistoryToAttach = em.getReference(historyListHistoryToAttach.getClass(), historyListHistoryToAttach.getHistoryid());
                attachedHistoryList.add(historyListHistoryToAttach);
            }
            customer.setHistoryList(attachedHistoryList);
            em.persist(customer);
            if (accountId != null) {
                accountId.getCustomerList().add(customer);
                accountId = em.merge(accountId);
            }
            for (Payment paymentListPayment : customer.getPaymentList()) {
                Customer oldCustomernumberOfPaymentListPayment = paymentListPayment.getCustomernumber();
                paymentListPayment.setCustomernumber(customer);
                paymentListPayment = em.merge(paymentListPayment);
                if (oldCustomernumberOfPaymentListPayment != null) {
                    oldCustomernumberOfPaymentListPayment.getPaymentList().remove(paymentListPayment);
                    oldCustomernumberOfPaymentListPayment = em.merge(oldCustomernumberOfPaymentListPayment);
                }
            }
            for (Orderscustomer orderscustomerListOrderscustomer : customer.getOrderscustomerList()) {
                Customer oldCustomernumberOfOrderscustomerListOrderscustomer = orderscustomerListOrderscustomer.getCustomernumber();
                orderscustomerListOrderscustomer.setCustomernumber(customer);
                orderscustomerListOrderscustomer = em.merge(orderscustomerListOrderscustomer);
                if (oldCustomernumberOfOrderscustomerListOrderscustomer != null) {
                    oldCustomernumberOfOrderscustomerListOrderscustomer.getOrderscustomerList().remove(orderscustomerListOrderscustomer);
                    oldCustomernumberOfOrderscustomerListOrderscustomer = em.merge(oldCustomernumberOfOrderscustomerListOrderscustomer);
                }
            }
            for (History historyListHistory : customer.getHistoryList()) {
                Customer oldCustomernumberOfHistoryListHistory = historyListHistory.getCustomernumber();
                historyListHistory.setCustomernumber(customer);
                historyListHistory = em.merge(historyListHistory);
                if (oldCustomernumberOfHistoryListHistory != null) {
                    oldCustomernumberOfHistoryListHistory.getHistoryList().remove(historyListHistory);
                    oldCustomernumberOfHistoryListHistory = em.merge(oldCustomernumberOfHistoryListHistory);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCustomer(customer.getCustomernumber()) != null) {
                throw new PreexistingEntityException("Customer " + customer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomernumber());
            Account accountIdOld = persistentCustomer.getAccountId();
            Account accountIdNew = customer.getAccountId();
            List<Payment> paymentListOld = persistentCustomer.getPaymentList();
            List<Payment> paymentListNew = customer.getPaymentList();
            List<Orderscustomer> orderscustomerListOld = persistentCustomer.getOrderscustomerList();
            List<Orderscustomer> orderscustomerListNew = customer.getOrderscustomerList();
            List<History> historyListOld = persistentCustomer.getHistoryList();
            List<History> historyListNew = customer.getHistoryList();
            List<String> illegalOrphanMessages = null;
            for (Payment paymentListOldPayment : paymentListOld) {
                if (!paymentListNew.contains(paymentListOldPayment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Payment " + paymentListOldPayment + " since its customernumber field is not nullable.");
                }
            }
            for (Orderscustomer orderscustomerListOldOrderscustomer : orderscustomerListOld) {
                if (!orderscustomerListNew.contains(orderscustomerListOldOrderscustomer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderscustomer " + orderscustomerListOldOrderscustomer + " since its customernumber field is not nullable.");
                }
            }
            for (History historyListOldHistory : historyListOld) {
                if (!historyListNew.contains(historyListOldHistory)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain History " + historyListOldHistory + " since its customernumber field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (accountIdNew != null) {
                accountIdNew = em.getReference(accountIdNew.getClass(), accountIdNew.getAccountId());
                customer.setAccountId(accountIdNew);
            }
            List<Payment> attachedPaymentListNew = new ArrayList<Payment>();
            for (Payment paymentListNewPaymentToAttach : paymentListNew) {
                paymentListNewPaymentToAttach = em.getReference(paymentListNewPaymentToAttach.getClass(), paymentListNewPaymentToAttach.getCardnumber());
                attachedPaymentListNew.add(paymentListNewPaymentToAttach);
            }
            paymentListNew = attachedPaymentListNew;
            customer.setPaymentList(paymentListNew);
            List<Orderscustomer> attachedOrderscustomerListNew = new ArrayList<Orderscustomer>();
            for (Orderscustomer orderscustomerListNewOrderscustomerToAttach : orderscustomerListNew) {
                orderscustomerListNewOrderscustomerToAttach = em.getReference(orderscustomerListNewOrderscustomerToAttach.getClass(), orderscustomerListNewOrderscustomerToAttach.getOrdernumber());
                attachedOrderscustomerListNew.add(orderscustomerListNewOrderscustomerToAttach);
            }
            orderscustomerListNew = attachedOrderscustomerListNew;
            customer.setOrderscustomerList(orderscustomerListNew);
            List<History> attachedHistoryListNew = new ArrayList<History>();
            for (History historyListNewHistoryToAttach : historyListNew) {
                historyListNewHistoryToAttach = em.getReference(historyListNewHistoryToAttach.getClass(), historyListNewHistoryToAttach.getHistoryid());
                attachedHistoryListNew.add(historyListNewHistoryToAttach);
            }
            historyListNew = attachedHistoryListNew;
            customer.setHistoryList(historyListNew);
            customer = em.merge(customer);
            if (accountIdOld != null && !accountIdOld.equals(accountIdNew)) {
                accountIdOld.getCustomerList().remove(customer);
                accountIdOld = em.merge(accountIdOld);
            }
            if (accountIdNew != null && !accountIdNew.equals(accountIdOld)) {
                accountIdNew.getCustomerList().add(customer);
                accountIdNew = em.merge(accountIdNew);
            }
            for (Payment paymentListNewPayment : paymentListNew) {
                if (!paymentListOld.contains(paymentListNewPayment)) {
                    Customer oldCustomernumberOfPaymentListNewPayment = paymentListNewPayment.getCustomernumber();
                    paymentListNewPayment.setCustomernumber(customer);
                    paymentListNewPayment = em.merge(paymentListNewPayment);
                    if (oldCustomernumberOfPaymentListNewPayment != null && !oldCustomernumberOfPaymentListNewPayment.equals(customer)) {
                        oldCustomernumberOfPaymentListNewPayment.getPaymentList().remove(paymentListNewPayment);
                        oldCustomernumberOfPaymentListNewPayment = em.merge(oldCustomernumberOfPaymentListNewPayment);
                    }
                }
            }
            for (Orderscustomer orderscustomerListNewOrderscustomer : orderscustomerListNew) {
                if (!orderscustomerListOld.contains(orderscustomerListNewOrderscustomer)) {
                    Customer oldCustomernumberOfOrderscustomerListNewOrderscustomer = orderscustomerListNewOrderscustomer.getCustomernumber();
                    orderscustomerListNewOrderscustomer.setCustomernumber(customer);
                    orderscustomerListNewOrderscustomer = em.merge(orderscustomerListNewOrderscustomer);
                    if (oldCustomernumberOfOrderscustomerListNewOrderscustomer != null && !oldCustomernumberOfOrderscustomerListNewOrderscustomer.equals(customer)) {
                        oldCustomernumberOfOrderscustomerListNewOrderscustomer.getOrderscustomerList().remove(orderscustomerListNewOrderscustomer);
                        oldCustomernumberOfOrderscustomerListNewOrderscustomer = em.merge(oldCustomernumberOfOrderscustomerListNewOrderscustomer);
                    }
                }
            }
            for (History historyListNewHistory : historyListNew) {
                if (!historyListOld.contains(historyListNewHistory)) {
                    Customer oldCustomernumberOfHistoryListNewHistory = historyListNewHistory.getCustomernumber();
                    historyListNewHistory.setCustomernumber(customer);
                    historyListNewHistory = em.merge(historyListNewHistory);
                    if (oldCustomernumberOfHistoryListNewHistory != null && !oldCustomernumberOfHistoryListNewHistory.equals(customer)) {
                        oldCustomernumberOfHistoryListNewHistory.getHistoryList().remove(historyListNewHistory);
                        oldCustomernumberOfHistoryListNewHistory = em.merge(oldCustomernumberOfHistoryListNewHistory);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customer.getCustomernumber();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomernumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Payment> paymentListOrphanCheck = customer.getPaymentList();
            for (Payment paymentListOrphanCheckPayment : paymentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Payment " + paymentListOrphanCheckPayment + " in its paymentList field has a non-nullable customernumber field.");
            }
            List<Orderscustomer> orderscustomerListOrphanCheck = customer.getOrderscustomerList();
            for (Orderscustomer orderscustomerListOrphanCheckOrderscustomer : orderscustomerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the Orderscustomer " + orderscustomerListOrphanCheckOrderscustomer + " in its orderscustomerList field has a non-nullable customernumber field.");
            }
            List<History> historyListOrphanCheck = customer.getHistoryList();
            for (History historyListOrphanCheckHistory : historyListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the History " + historyListOrphanCheckHistory + " in its historyList field has a non-nullable customernumber field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Account accountId = customer.getAccountId();
            if (accountId != null) {
                accountId.getCustomerList().remove(customer);
                accountId = em.merge(accountId);
            }
            em.remove(customer);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Customer.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer findCustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Customer> rt = cq.from(Customer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
