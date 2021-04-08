package com.havving.system.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-21.
 */
@Entity
@Table(name = "SYS_EXCEPTION")
public class Exception implements Serializable {
    private static final long serialVersionUID = 1783270528883085659L;

    @Id
    @Column(name = "ID")
    private long id;
    @Column(name = "DATE_TIME")
    private long dateTime;
    @Column(name = "CLASS_NAME")
    private String className;
    @Column(name = "EXCEPTION_CLASS")
    private String exceptionClass;
    @Column(name = "MESSAGE")
    private String message;
    @Column(name = "METHOD_NAME")
    private String methodName;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getExceptionClass() {
        return exceptionClass;
    }

    public void setExceptionClass(String exceptionClass) {
        this.exceptionClass = exceptionClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
