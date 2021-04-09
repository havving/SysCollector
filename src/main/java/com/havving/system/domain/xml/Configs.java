package com.havving.system.domain.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-06.
 * syscollector.xml configure class
 */

@XmlRootElement
public class Configs {
    Configs() {
        jpaStore = new JpaStore();
        esStore = new EsStore();
    }

    @XmlAttribute
    public String host;
    @XmlElement(name = "system-collect")
    public SystemCollect systemCollect;
    // TODO How to <process> list
    @XmlElementWrapper(name = "process-collect")
    @XmlElement(name = "process", type = Process.class)
    public List<Process> process;
    @XmlElement(name = "es-collect")
    public EsStore esCollect;
    @XmlElement(type = Object.class)
    public Store jpaStore;
    @XmlElement(type = Object.class)
    public Store esStore;


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
