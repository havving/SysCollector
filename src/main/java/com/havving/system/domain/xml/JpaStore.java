package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-20.
 */
public class JpaStore implements Store {
    @XmlAttribute(name = "hibernate-path")
    public String hibernatePath;
}
