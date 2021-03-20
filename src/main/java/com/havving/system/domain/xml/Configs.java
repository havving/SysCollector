package com.havving.system.domain.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-06.
 * syscollector.xml configure class
 */

@XmlRootElement
public class Configs {
    @XmlAttribute
    public String host;
    @XmlElement(name = "system-collect")
    public SystemCollect systemCollect;
    @XmlElementWrapper(name = "process-collect")
    public List<Process> process;
    @XmlElement(name = "es-collect")
    public EsStore esCollect;
    @XmlElement
    public Store store;


    /**
     * 프로세스가 존재하는지 확인
     * 
     * @param args
     * @return
     */
    public boolean containsLookupProcess(String args) {
        if (process != null) {
            for (Process p : process) {
                if (p.name.equals(args)) {
                    return true;
                }
            }
        }

        return false;
    }


}
