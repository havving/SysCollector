package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by HAVVING on 2021-04-12.
 */
public class RestStore implements Store {
    @XmlAttribute
    public String url;
}
