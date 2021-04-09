package com.havving.system.domain.xml;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAnyAttribute;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-06.
 */
public class Process {
    @XmlAttribute
    public String name;
    @XmlAttribute
    public boolean kill = false;
    @XmlAttribute
    public KillType killType;
    @XmlAttribute
    public int threshold;
    @XmlAttribute
    public int killCount;
    @Getter
    @Setter
    private long pid;

}

enum KillType {
    CPU, MEMORY, BOTH
}
