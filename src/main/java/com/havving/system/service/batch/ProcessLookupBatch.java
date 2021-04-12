package com.havving.system.service.batch;

import com.havving.system.global.Constants;
import com.havving.system.global.ORMConnection;
import com.havving.system.service.SystemCollectorService;

import java.sql.SQLException;

/**
 * Created by HAVVING on 2021-04-12.
 */
public class ProcessLookupBatch extends BatchExecutor {

    public ProcessLookupBatch(ORMConnection ormConnection) {
        super(ormConnection);
    }

    @Override
    public void execute() throws SQLException {
        SystemCollectorService collector = Constants.getInstance().getSystemCollector();
        collector.initLookupProcesses();

    }
}
