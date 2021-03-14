package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.Vector;

/**
 * Created by HAVVING on 2021-03-06.
 */
public class SystemCollect {
    @XmlAttribute
    public boolean cpuEnable = false;
    @XmlAttribute
    public boolean memoryEnable = false;
    @XmlAttribute
    public boolean diskEnable = false;
    @XmlAttribute
    public boolean networkEnable = false;
    @XmlAttribute
    public boolean engineEnable = false;
    @XmlElementWrapper(name = "dirNameList")
    public Vector<String> dirList;
    @XmlElementWrapper(name = "ipAddressNameList")
    public Vector<String> ipAddressList;
    @XmlElement(name = "engine")
    public EsStore engine;

}
