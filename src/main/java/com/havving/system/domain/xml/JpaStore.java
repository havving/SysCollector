package com.havving.system.domain.xml;

import lombok.ToString;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-03-20.
 */
@ToString
public class JpaStore implements Store {
    @XmlAttribute
    public String hibernatePath;
}
