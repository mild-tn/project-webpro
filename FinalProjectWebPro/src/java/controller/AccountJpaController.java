/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.IllegalOrphanException;
import controller.exceptions.NonexistentEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.Register;
import model.Customer;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Account;

/**
 *
 * @author Mild-TN
 */
public class AccountJpaController implements Serializable {

    public AccountJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Account account) throws RollbackFailureException, Exception {
        if (account.getCustomerList() == null) {
            account.setCustomerList(new ArrayList<Customer>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Register registerId = account.getRegisterId();
            if (registerId != null) {
                registerId = em.getReference(registerId.getClass(), registerId.getRegisterId());
                account.setRegisterId(registerId);
            }
            List<Customer> attachedCustomerList = new ArrayList<Customer>();
            for (Customer customerListCustomerToAttach : account.getCustomerList()) {
                customerListCustomerToAttach = em.getReference(customerListCustomerToAttach.getClass(), customerListCustomerToAttach.getCustomernumber());
                attachedCustomerList.add(customerListCustomerToAttach);
            }
            account.setCustomerList(attachedCustomerList);
            em.persist(account);
            if (registerId != null) {
                registerId.getAccountList().add(account);
                registerId = em.merge(registerId);
            }
            for (Customer customerListCustomer : account.getCustomerList()) {
                Account oldAccountIdOfCustomerListCustomer = customerListCustomer.getAccountId();
                customerListCustomer.setAccountId(account);
                customerListCustomer = em.merge(customerListCustomer);
                if (oldAccountIdOfCustomerListCustomer != null) {
                    oldAccountIdOfCustomerListCustomer.getCustomerList().remove(customerListCustomer);
                    oldAccountIdOfCustomerListCustomer = em.merge(oldAccountIdOfCustomerListCustomer);
                }
            }
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

    public void edit(Account account) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Account persistentAccount = em.find(Account.class, account.getAccountId());
            Register registerIdOld = persistentAccount.getRegisterId();
            Register registerIdNew = account.getRegisterId();
            List<Customer> customerListOld = persistentAccount.getCustomerList();
            List<Customer> customerListNew = account.getCustomerList();
            List<String> illegalOrphanMessages = null;
            for (Customer customerListOldCustomer : customerListOld) {
                if (!customerListNew.contains(customerListOldCustomer)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Customer " + customerListOldCustomer + " since its accountId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (registerIdNew != null) {
                registerIdNew = em.getReference(registerIdNew.getClass(), registerIdNew.getRegisterId());
                account.setRegisterId(registerIdNew);
            }
            List<Customer> attachedCustomerListNew = new ArrayList<Customer>();
            for (Customer customerListNewCustomerToAttach : customerListNew) {
                customerListNewCustomerToAttach = em.getReference(customerListNewCustomerToAttach.getClass(), customerListNewCustomerToAttach.getCustomernumber());
                attachedCustomerListNew.add(customerListNewCustomerToAttach);
            }
            customerListNew = attachedCustomerListNew;
            account.setCustomerList(customerListNew);
            account = em.merge(account);
            if (registerIdOld != null && !registerIdOld.equals(registerIdNew)) {
                registerIdOld.getAccountList().remove(account);
                registerIdOld = em.merge(registerIdOld);
            }
            if (registerIdNew != null && !registerIdNew.equals(registerIdOld)) {
                registerIdNew.getAccountList().add(account);
                registerIdNew = em.merge(registerIdNew);
            }
            for (Customer customerListNewCustomer : customerListNew) {
                if (!customerListOld.contains(customerListNewCustomer)) {
                    Account oldAccountIdOfCustomerListNewCustomer = customerListNewCustomer.getAccountId();
                    customerListNewCustomer.setAccountId(account);
                    customerListNewCustomer = em.merge(customerListNewCustomer);
                    if (oldAccountIdOfCustomerListNewCustomer != null && !oldAccountIdOfCustomerListNewCustomer.equals(account)) {
                        oldAccountIdOfCustomerListNewCustomer.getCustomerList().remove(customerListNewCustomer);
                        oldAccountIdOfCustomerListNewCustomer = em.merge(oldAccountIdOfCustomerListNewCustomer);
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
                Integer id = account.getAccountId();
                if (findAccount(id) == null) {
                    throw new NonexistentEntityException("The account with id " + id + " no longer exists.");
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
            Account account;
            try {
                account = em.getReference(Account.class, id);
                account.getAccountId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The account with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Customer> customerListOrphanCheck = account.getCustomerList();
            for (Customer customerListOrphanCheckCustomer : customerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Account (" + account + ") cannot be destroyed since the Customer " + customerListOrphanCheckCustomer + " in its customerList field has a non-nullable accountId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Register registerId = account.getRegisterId();
            if (registerId != null) {
                registerId.getAccountList().remove(account);
                registerId = em.merge(registerId);
            }
            em.remove(account);
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

    public List<Account> findAccountEntities() {
        return findAccountEntities(true, -1, -1);
    }

    public List<Account> findAccountEntities(int maxResults, int firstResult) {
        return findAccountEntities(false, maxResults, firstResult);
    }

    private List<Account> findAccountEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Account.class));
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

    public Account findAccount(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Account.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccountCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Account> rt = cq.from(Account.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public Account findByEmail(String email) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Account.findByEmail");
        query.setParameter("email", email);
        try {
            return (Account) query.getSingleResult();
        } finally {
            em.close();
        }
    }
}
