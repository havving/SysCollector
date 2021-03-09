package com.havving.system.domain.impl;

import com.havving.system.domain.SysModel;
import org.joda.time.DateTime;

/**
 * Created by HAVVING on 2021-03-09.
 */
public class ExceptionSysModel implements SysModel {
    private final String type = "exception";
    private String host;
    private String hostName;
    private String message;
    private InnerException[] data;

    public ExceptionSysModel(String hostName, Exception e) {
        this.host = hostName;
        StackTraceElement[] elems = e.getStackTrace();
        init(elems.length);
        for (int i = 0; i < elems.length; i++) {
            data[i] = new InnerException(elems[i].getClassName(), elems[i].getMethodName(), elems[i].getLineNumber());
        }
    }

    private void init(int i) {
        data = new InnerException[i];
    }

    public String getHost() {
        return null;
    }

    public String getType() {
        return null;
    }

    public long getTime() {
        return 0;
    }

    public String getHostName() {
        return null;
    }

    public void setHostName(String hostName) {

    }

    private static class InnerException {
        private long time;
        private String className;
        private String methodName;
        private int lineNumber;

        InnerException(String className, String methodName, int lineNumber) {
            this.className = className;
            this.methodName = methodName;
            this.lineNumber = lineNumber;
            this.time = new DateTime().getMillis();
        }

    }
}
