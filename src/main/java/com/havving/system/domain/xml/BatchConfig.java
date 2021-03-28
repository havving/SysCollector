package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by HAVVING on 2021-03-28.
 * batchConfig.xml configure class
 */
@XmlRootElement
public class BatchConfig {
    @XmlElement
    public JobConfig job;
}
