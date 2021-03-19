package com.havving.system.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

/**
 * Created by HAVVING on 2021-03-19.
 */
public class JDBCConnection {
    private static Logger logger = LoggerFactory.getLogger(JDBCConnection.class);
    private Connection connection;

    public JDBCConnection(Connection connection) {
        this.connection = connection;
    }

}
