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
import model.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Register;

/**
 *
 * @author Mild-TN
 */
public class RegisterJpaController implements Serializable {

    public RegisterJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Register register) throws RollbackFailureException, Exception {
        if (register.getAccountList() == null) {
            register.setAccountList(new ArrayList<Account>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Account> attachedAccountList = new ArrayList<Account>();
            for (Account accountListAccountToAttach : register.getAccountList()) {
                accountListAccountToAttach = em.getReference(accountListAccountToAttach.getClass(), accountListAccountToAttach.getAccountId());
                attachedAccountList.add(accountListAccountToAttach);
            }
            register.setAccountList(attachedAccountList);
            em.persist(register);
            for (Account accountListAccount : register.getAccountList()) {
                Register oldRegisterIdOfAccountListAccount = accountListAccount.getRegisterId();
                accountListAccount.setRegisterId(register);
                accountListAccount = em.merge(accountListAccount);
                if (oldRegisterIdOfAccountListAccount != null) {
                    oldRegisterIdOfAccountListAccount.getAccountList().remove(accountListAccount);
                    oldRegisterIdOfAccountListAccount = em.merge(oldRegisterIdOfAccountListAccount);
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

    public void edit(Register register) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Register persistentRegister = em.find(Register.class, register.getRegisterId());
            List<Account> accountListOld = persistentRegister.getAccountList();
            List<Account> accountListNew = register.getAccountList();
            List<String> illegalOrphanMessages = null;
            for (Account accountListOldAccount : accountListOld) {
                if (!accountListNew.contains(accountListOldAccount)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Account " + accountListOldAccount + " since its registerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Account> attachedAccountListNew = new ArrayList<Account>();
            for (Account accountListNewAccountToAttach : accountListNew) {
                accountListNewAccountToAttach = em.getReference(accountListNewAccountToAttach.getClass(), accountListNewAccountToAttach.getAccountId());
                attachedAccountListNew.add(accountListNewAccountToAttach);
            }
            accountListNew = attachedAccountListNew;
            register.setAccountList(accountListNew);
            register = em.merge(register);
            for (Account accountListNewAccount : accountListNew) {
                if (!accountListOld.contains(accountListNewAccount)) {
                    Register oldRegisterIdOfAccountListNewAccount = accountListNewAccount.getRegisterId();
                    accountListNewAccount.setRegisterId(register);
                    accountListNewAccount = em.merge(accountListNewAccount);
                    if (oldRegisterIdOfAccountListNewAccount != null && !oldRegisterIdOfAccountListNewAccount.equals(register)) {
                        oldRegisterIdOfAccountListNewAccount.getAccountList().remove(accountListNewAccount);
                        oldRegisterIdOfAccountListNewAccount = em.merge(oldRegisterIdOfAccountListNewAccount);
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
                Integer id = register.getRegisterId();
                if (findRegister(id) == null) {
                    throw new NonexistentEntityException("The register with id " + id + " no longer exists.");
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
            Register register;
            try {
                register = em.getReference(Register.class, id);
                register.getRegisterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The register with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Account> accountListOrphanCheck = register.getAccountList();
            for (Account accountListOrphanCheckAccount : accountListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Register (" + register + ") cannot be destroyed since the Account " + accountListOrphanCheckAccount + " in its accountList field has a non-nullable registerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(register);
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

    public List<Register> findRegisterEntities() {
        return findRegisterEntities(true, -1, -1);
    }

    public List<Register> findRegisterEntities(int maxResults, int firstResult) {
        return findRegisterEntities(false, maxResults, firstResult);
    }

    private List<Register> findRegisterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Register.class));
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

    public Register findRegister(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Register.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegisterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Register> rt = cq.from(Register.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Register findByEmail(String email) {
        EntityManager em = getEntityManager();
        Query query = em.createNamedQuery("Register.findByEmail");
        query.setParameter("email", email);
        try {
            return (Register) query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
}
