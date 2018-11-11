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
import model.Orderdetail;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import model.Orderscustomer;

/**
 *
 * @author kao-tu
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
        if (orderscustomer.getOrderdetailCollection() == null) {
            orderscustomer.setOrderdetailCollection(new ArrayList<Orderdetail>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Orderdetail> attachedOrderdetailCollection = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionOrderdetailToAttach : orderscustomer.getOrderdetailCollection()) {
                orderdetailCollectionOrderdetailToAttach = em.getReference(orderdetailCollectionOrderdetailToAttach.getClass(), orderdetailCollectionOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailCollection.add(orderdetailCollectionOrderdetailToAttach);
            }
            orderscustomer.setOrderdetailCollection(attachedOrderdetailCollection);
            em.persist(orderscustomer);
            for (Orderdetail orderdetailCollectionOrderdetail : orderscustomer.getOrderdetailCollection()) {
                Orderscustomer oldOrderscustomerOfOrderdetailCollectionOrderdetail = orderdetailCollectionOrderdetail.getOrderscustomer();
                orderdetailCollectionOrderdetail.setOrderscustomer(orderscustomer);
                orderdetailCollectionOrderdetail = em.merge(orderdetailCollectionOrderdetail);
                if (oldOrderscustomerOfOrderdetailCollectionOrderdetail != null) {
                    oldOrderscustomerOfOrderdetailCollectionOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionOrderdetail);
                    oldOrderscustomerOfOrderdetailCollectionOrderdetail = em.merge(oldOrderscustomerOfOrderdetailCollectionOrderdetail);
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
            Collection<Orderdetail> orderdetailCollectionOld = persistentOrderscustomer.getOrderdetailCollection();
            Collection<Orderdetail> orderdetailCollectionNew = orderscustomer.getOrderdetailCollection();
            List<String> illegalOrphanMessages = null;
            for (Orderdetail orderdetailCollectionOldOrderdetail : orderdetailCollectionOld) {
                if (!orderdetailCollectionNew.contains(orderdetailCollectionOldOrderdetail)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Orderdetail " + orderdetailCollectionOldOrderdetail + " since its orderscustomer field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Orderdetail> attachedOrderdetailCollectionNew = new ArrayList<Orderdetail>();
            for (Orderdetail orderdetailCollectionNewOrderdetailToAttach : orderdetailCollectionNew) {
                orderdetailCollectionNewOrderdetailToAttach = em.getReference(orderdetailCollectionNewOrderdetailToAttach.getClass(), orderdetailCollectionNewOrderdetailToAttach.getOrderdetailPK());
                attachedOrderdetailCollectionNew.add(orderdetailCollectionNewOrderdetailToAttach);
            }
            orderdetailCollectionNew = attachedOrderdetailCollectionNew;
            orderscustomer.setOrderdetailCollection(orderdetailCollectionNew);
            orderscustomer = em.merge(orderscustomer);
            for (Orderdetail orderdetailCollectionNewOrderdetail : orderdetailCollectionNew) {
                if (!orderdetailCollectionOld.contains(orderdetailCollectionNewOrderdetail)) {
                    Orderscustomer oldOrderscustomerOfOrderdetailCollectionNewOrderdetail = orderdetailCollectionNewOrderdetail.getOrderscustomer();
                    orderdetailCollectionNewOrderdetail.setOrderscustomer(orderscustomer);
                    orderdetailCollectionNewOrderdetail = em.merge(orderdetailCollectionNewOrderdetail);
                    if (oldOrderscustomerOfOrderdetailCollectionNewOrderdetail != null && !oldOrderscustomerOfOrderdetailCollectionNewOrderdetail.equals(orderscustomer)) {
                        oldOrderscustomerOfOrderdetailCollectionNewOrderdetail.getOrderdetailCollection().remove(orderdetailCollectionNewOrderdetail);
                        oldOrderscustomerOfOrderdetailCollectionNewOrderdetail = em.merge(oldOrderscustomerOfOrderdetailCollectionNewOrderdetail);
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
            Collection<Orderdetail> orderdetailCollectionOrphanCheck = orderscustomer.getOrderdetailCollection();
            for (Orderdetail orderdetailCollectionOrphanCheckOrderdetail : orderdetailCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Orderscustomer (" + orderscustomer + ") cannot be destroyed since the Orderdetail " + orderdetailCollectionOrphanCheckOrderdetail + " in its orderdetailCollection field has a non-nullable orderscustomer field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
