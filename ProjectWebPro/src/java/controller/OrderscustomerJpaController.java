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
import model.Customer;
import model.Orderdetail;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Orderscustomer;

/**
 *
 * @author Mild-TN
 */
public class OrderscustomerJpaController implements Serializable {

    public OrderscustomerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderscustomer orderscustomer) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (orderscustomer.getOrderdetailList() == null) {
            orderscustomer.setOrderdetailList(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customernumber = orderscustomer.getCustomernumber();
            if (customernumber != null) {
                customernumber = em.getReference(customernumber.getClass(), customernumber.getCustomernumber());
                orderscustomer.setCustomernumber(customernumber);
            }
            List<Orderdetail> attachedOrderdetailList = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailListOrderdetailToAttach : orderscustomer.getOrderdetailList()) {
                orderdetailListOrderdetailToAttach = em.getReference(orderdetailListOrderdetailToAttach.getClass(), orderdetailListOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailList.add(orderdetailListOrderdetailToAttach);
            }
            orderscustomer.setOrderdetailList(attachedOrderdetailList);
            em.persist(orderscustomer);
            if (customernumber != null) {
                customernumber.getOrderscustomerList().add(orderscustomer);
                customernumber = em.merge(customernumber);
            }
            for (Orderdetail orderdetailListOrderdetail : orderscustomer.getOrderdetailList()) {
                Orderscustomer oldOrderscustomerOfOrderdetailListOrderdetail = orderdetailListOrderdetail.getOrderscustomer();
                orderdetailListOrderdetail.setOrderscustomer(orderscustomer);
                orderdetailListOrderdetail = em.merge(orderdetailListOrderdetail);
                if (oldOrderscustomerOfOrderdetailListOrderdetail != null) {
                    oldOrderscustomerOfOrderdetailListOrderdetail.getOrderdetailList().remove(orderdetailListOrderdetail);
                    oldOrderscustomerOfOrderdetailListOrderdetail = em.merge(oldOrderscustomerOfOrderdetailListOrderdetail);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrderscustomer(orderscustomer.getOrdernumber()) != null) {
                throw new PreexistingEntityException("Orderscustomer " + orderscustomer + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderscustomer orderscustomer) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderscustomer persistentOrderscustomer = em.find(Orderscustomer.class, orderscustomer.getOrdernumber());
            Customer customernumberOld = persistentOrderscustomer.getCustomernumber();
            Customer customernumberNew = orderscustomer.getCustomernumber();
            List<Orderdetail> orderdetailListOld = persistentOrderscustomer.getOrderdetailList();
            List<Orderdetail> orderdetailListNew = orderscustomer.getOrderdetailList();
            List<String> illegalOrphanMessages = null;
            for (Orderdetail orderdetailListOldOrderdetail : orderdetailListOld) {
                if (!orderdetailListNew.contains(orderdetailListOldOrderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetail " + orderdetailListOldOrderdetail + " since its orderscustomer field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customernumberNew != null) {
                customernumberNew = em.getReference(customernumberNew.getClass(), customernumberNew.getCustomernumber());
                orderscustomer.setCustomernumber(customernumberNew);
            }
            List<Orderdetail> attachedOrderdetailListNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailListNewOrderdetailToAttach : orderdetailListNew) {
                orderdetailListNewOrderdetailToAttach = em.getReference(orderdetailListNewOrderdetailToAttach.getClass(), orderdetailListNewOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailListNew.add(orderdetailListNewOrderdetailToAttach);
            }
            orderdetailListNew = attachedOrderdetailListNew;
            orderscustomer.setOrderdetailList(orderdetailListNew);
            orderscustomer = em.merge(orderscustomer);
            if (customernumberOld != null && !customernumberOld.equals(customernumberNew)) {
                customernumberOld.getOrderscustomerList().remove(orderscustomer);
                customernumberOld = em.merge(customernumberOld);
            }
            if (customernumberNew != null && !customernumberNew.equals(customernumberOld)) {
                customernumberNew.getOrderscustomerList().add(orderscustomer);
                customernumberNew = em.merge(customernumberNew);
            }
            for (Orderdetail orderdetailListNewOrderdetail : orderdetailListNew) {
                if (!orderdetailListOld.contains(orderdetailListNewOrderdetail)) {
                    Orderscustomer oldOrderscustomerOfOrderdetailListNewOrderdetail = orderdetailListNewOrderdetail.getOrderscustomer();
                    orderdetailListNewOrderdetail.setOrderscustomer(orderscustomer);
                    orderdetailListNewOrderdetail = em.merge(orderdetailListNewOrderdetail);
                    if (oldOrderscustomerOfOrderdetailListNewOrderdetail != null && !oldOrderscustomerOfOrderdetailListNewOrderdetail.equals(orderscustomer)) {
                        oldOrderscustomerOfOrderdetailListNewOrderdetail.getOrderdetailList().remove(orderdetailListNewOrderdetail);
                        oldOrderscustomerOfOrderdetailListNewOrderdetail = em.merge(oldOrderscustomerOfOrderdetailListNewOrderdetail);
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
                Integer id = orderscustomer.getOrdernumber();
                if (findOrderscustomer(id) == null) {
                    throw new NonexistentEntityException("The orderscustomer with id " + id + " no longer exists.");
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
            Orderscustomer orderscustomer;
            try {
                orderscustomer = em.getReference(Orderscustomer.class, id);
                orderscustomer.getOrdernumber();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderscustomer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Orderdetail> orderdetailListOrphanCheck = orderscustomer.getOrderdetailList();
            for (Orderdetail orderdetailListOrphanCheckOrderdetail : orderdetailListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orderscustomer (" + orderscustomer + ") cannot be destroyed since the Orderdetail " + orderdetailListOrphanCheckOrderdetail + " in its orderdetailList field has a non-nullable orderscustomer field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Customer customernumber = orderscustomer.getCustomernumber();
            if (customernumber != null) {
                customernumber.getOrderscustomerList().remove(orderscustomer);
                customernumber = em.merge(customernumber);
            }
            em.remove(orderscustomer);
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

    public List<Orderscustomer> findOrderscustomerEntities() {
        return findOrderscustomerEntities(true, -1, -1);
    }

    public List<Orderscustomer> findOrderscustomerEntities(int maxResults, int firstResult) {
        return findOrderscustomerEntities(false, maxResults, firstResult);
    }

    private List<Orderscustomer> findOrderscustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderscustomer.class));
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

    public Orderscustomer findOrderscustomer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderscustomer.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderscustomerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderscustomer> rt = cq.from(Orderscustomer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
