package com.havving.system.service.batch;

import com.havving.system.global.ORMConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * Created by HAVVING on 2021-03-19.
 */
public abstract class BatchExecutor {
    protected Logger logger = null;
    private ORMConnection ormConnection = null;

    public BatchExecutor(ORMConnection ormConnection) {
        this.ormConnection = ormConnection;
        logger = LoggerFactory.getLogger(this.getClass().getName());
    }

    public ORMConnection getConnection() {
        return ormConnection;
    }

    public abstract void execute() throws SQLException;
}
