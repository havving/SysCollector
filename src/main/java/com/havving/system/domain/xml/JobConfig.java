package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-28.
 */
public class JobConfig {
    @XmlAttribute
    public String group;
    @XmlAttribute
    public String name;
    @XmlAttribute
    public String triggerName;
    @XmlAttribute
    public String cronExpression;
    @XmlAttribute
    public String targetClass;
}
