package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-06.
 */
public class EsStore {
    @XmlAttribute
    public String masterIp;
    @XmlAttribute(name = "destination-port")
    public int destinationPort;

}
