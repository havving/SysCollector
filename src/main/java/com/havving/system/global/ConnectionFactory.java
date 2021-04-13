package com.havving.system.global;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Created by HAVVING on 2021-04-05.
 * Hibernate Connection Factory Class
 */
public class ConnectionFactory {
    private static ConnectionFactory connFactory = null;
    private static SessionFactory sessFactory = null;

    public static ConnectionFactory getConnFactory() {
        if (connFactory == null) connFactory = new ConnectionFactory();

        return connFactory;
    }

    public void init() {
        if (sessFactory == null) {
            // configures settings from hibernate.cfg.xml
            sessFactory = new Configuration().configure().buildSessionFactory();
        }
        sessFactory.openSession();
    }

    public ORMConnection getOpenConnection() {
        Session sess = sessFactory.openSession();
        ORMConnection ormConnection = new ORMConnection(sess);

        return ormConnection;
    }


}
