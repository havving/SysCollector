package com.havving.system.global;

import org.hibernate.Session;

/**
 * Created by HAVVING on 2021-03-19.
 */
public class ORMConnection {
    protected JDBCConnection jdbcConnection = null;
    private Session session = null;

    public ORMConnection(Session session) {
        this.session = session;
    }

    public void beginTransaction() {
        session.beginTransaction();
    }

    public void commit() {
        session.getTransaction().commit();
    }

    public void rollback() {
        session.getTransaction().rollback();
    }

    public void close() {
        session.getTransaction().rollback();
    }
}
