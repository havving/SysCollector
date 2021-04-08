package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by HAVVING on 2021-03-20.
 */

@XmlTransient
@XmlSeeAlso({JpaStore.class, EsStore.class})
public abstract class Store {
    // JAXB does not deal with interfaces by default

}
