package com.vtb.kortunov.lesson19;

import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;
import java.util.List;

public class DbManagement {

    private Session session;
    private final Factory factory;

    public DbManagement(Session session, Factory factory) {
        this.session = session;
        this.factory = factory;
    }

    public void beginTransaction() {
        session = factory.getSession();
        session.beginTransaction();
    }

    public void createNativeQuery(String query) {
        session.createNativeQuery(query).executeUpdate();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollBack() {
        session.getTransaction().rollback();
    }

    public void close() {
        if (session != null) {
            session.close();
        }
    }

    public Lot getLot(Long id) {
        return session.get(Lot.class, id);
    }

    public User getUser(Long id) {
        return session.get(User.class, id);
    }


    public Lot getLotPessimistic(Long id) {
        Query<Lot> query = session.createQuery("SELECT l FROM Lot l WHERE l.id = :id", Lot.class);
        query.setParameter("id", id);
        query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
        return query.getSingleResult();
    }

    public void raisePessimistic(Long userId, Long lotId, int bet) {
        User user = getUser(userId);
        Lot lot = getLotPessimistic(lotId);
        lot.setCurrentRate((lot.getCurrentRate() + bet));
        lot.setOwnerLastBet(user);
    }

    public void raise(Long userId, Long lotId, int bet) {
        User user = getUser(userId);
        Lot lot = getLot(lotId);
        lot.setCurrentRate((lot.getCurrentRate() + bet));
        lot.setOwnerLastBet(user);
    }

    public List<User> getUsers() {
        return session.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    public List<Lot> getLots() {
        return session.createQuery("SELECT l FROM Lot l", Lot.class).getResultList();
    }
}
