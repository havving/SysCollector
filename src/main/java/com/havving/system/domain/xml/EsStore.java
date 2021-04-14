package com.havving.system.domain.xml;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-06.
 */
@ToString
public class EsStore implements Store {
    @XmlAttribute
    public String masterIp;
    @XmlAttribute
    public String clusterName;
    @XmlAttribute
    public int destinationPort;
    @XmlAttribute
    public String ttl;
    @XmlAttribute
    public String indexPrefix;
    @XmlAttribute
    public boolean hotThreadCheckEnable = false;

}
