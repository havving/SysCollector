package com.havving.system.domain.jpa;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created by HAVVING on 2021-03-20.
 */
@Embeddable
@MappedSuperclass
public class SimpleId implements Serializable {

    @Column(name = "DATE_TIME")
    private long dateTime;
    @Column(name = "HOST_NAME")
    private String hostName;

    public SimpleId() {
    }

    public SimpleId(long dateTime, String hostName) {
        this.dateTime = dateTime;
        this.hostName = hostName;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
}
