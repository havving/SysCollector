package com.havving.system.domain.xml;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-06.
 * syscollector.xml configure class
 */

@XmlRootElement
public class Configs {
/*    public Configs() {
        jpaStore = new JpaStore();
        esStore = new EsStore();
    }*/

    @XmlAttribute
    public String host;
    @XmlElement(name = "system-collect")
    public SystemCollect systemCollect;
    @XmlElementWrapper(name = "process-collect")
    @XmlElement(name = "process", type = Process.class)
    public List<Process> process;
    @XmlElement(name = "es-collect")
    public EsStore esCollect;
/*
    @XmlElement(name = "store", type = JpaStore.class)
    public Store jpaStore;
    @XmlElement(name = "store", type = EsStore.class)
    public Store esStore;
*/
    // Store는 1개만 정의할 수 있다.
    @XmlElements({
            @XmlElement(name = "store", type = EsStore.class),
            @XmlElement(name = "store", type = JpaStore.class)
    })
    public List<Store> store;


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
