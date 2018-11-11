/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.exceptions.NonexistentEntityException;
import controller.exceptions.PreexistingEntityException;
import controller.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import model.Orderdetail;
import model.OrderdetailPK;
import model.Orderscustomer;

/**
 *
 * @author kao-tu
 */
public class OrderdetailJpaController implements Serializable {

    public OrderdetailJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Orderdetail orderdetail) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (orderdetail.getOrderdetailPK() == null) {
            orderdetail.setOrderdetailPK(new OrderdetailPK());
        }
        orderdetail.getOrderdetailPK().setOrdernumber(orderdetail.getOrderscustomer().getOrdernumber());
        orderdetail.getOrderdetailPK().setProductcode(orderdetail.getProduct().getProductcode());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderscustomer orderscustomer = orderdetail.getOrderscustomer();
            if (orderscustomer != null) {
                orderscustomer = em.getReference(orderscustomer.getClass(), orderscustomer.getOrdernumber());
                orderdetail.setOrderscustomer(orderscustomer);
            }
            em.persist(orderdetail);
            if (orderscustomer != null) {
                orderscustomer.getOrderdetailCollection().add(orderdetail);
                orderscustomer = em.merge(orderscustomer);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrderdetail(orderdetail.getOrderdetailPK()) != null) {
                throw new PreexistingEntityException("Orderdetail " + orderdetail + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Orderdetail orderdetail) throws NonexistentEntityException, RollbackFailureException, Exception {
        orderdetail.getOrderdetailPK().setOrdernumber(orderdetail.getOrderscustomer().getOrdernumber());
        orderdetail.getOrderdetailPK().setProductcode(orderdetail.getProduct().getProductcode());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderdetail persistentOrderdetail = em.find(Orderdetail.class, orderdetail.getOrderdetailPK());
            Orderscustomer orderscustomerOld = persistentOrderdetail.getOrderscustomer();
            Orderscustomer orderscustomerNew = orderdetail.getOrderscustomer();
            if (orderscustomerNew != null) {
                orderscustomerNew = em.getReference(orderscustomerNew.getClass(), orderscustomerNew.getOrdernumber());
                orderdetail.setOrderscustomer(orderscustomerNew);
            }
            orderdetail = em.merge(orderdetail);
            if (orderscustomerOld != null && !orderscustomerOld.equals(orderscustomerNew)) {
                orderscustomerOld.getOrderdetailCollection().remove(orderdetail);
                orderscustomerOld = em.merge(orderscustomerOld);
            }
            if (orderscustomerNew != null && !orderscustomerNew.equals(orderscustomerOld)) {
                orderscustomerNew.getOrderdetailCollection().add(orderdetail);
                orderscustomerNew = em.merge(orderscustomerNew);
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
                OrderdetailPK id = orderdetail.getOrderdetailPK();
                if (findOrderdetail(id) == null) {
                    throw new NonexistentEntityException("The orderdetail with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrderdetailPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Orderdetail orderdetail;
            try {
                orderdetail = em.getReference(Orderdetail.class, id);
                orderdetail.getOrderdetailPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderdetail with id " + id + " no longer exists.", enfe);
            }
            Orderscustomer orderscustomer = orderdetail.getOrderscustomer();
            if (orderscustomer != null) {
                orderscustomer.getOrderdetailCollection().remove(orderdetail);
                orderscustomer = em.merge(orderscustomer);
            }
            em.remove(orderdetail);
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

    public List<Orderdetail> findOrderdetailEntities() {
        return findOrderdetailEntities(true, -1, -1);
    }

    public List<Orderdetail> findOrderdetailEntities(int maxResults, int firstResult) {
        return findOrderdetailEntities(false, maxResults, firstResult);
    }

    private List<Orderdetail> findOrderdetailEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Orderdetail.class));
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

    public Orderdetail findOrderdetail(OrderdetailPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Orderdetail.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderdetailCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Orderdetail> rt = cq.from(Orderdetail.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
