package com.havving.system.domain.xml;

import com.havving.system.domain.impl.ProcessSysModel;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created by HAVVING on 2021-03-06.
 * syscollector.xml configure class
 */
@ToString
@XmlRootElement
public class Configs {
    @XmlAttribute
    public String host;
    @XmlElement(name = "system-collect")
    public SystemCollect systemCollect;
    @XmlElementWrapper(name = "process-collect")
    @XmlElement(name = "process", type = Process.class)
    public List<Process> process;
    @XmlElement(name = "es-collect")
    public EsStore esCollect;
    // Store는 1개만 정의할 수 있다.
    @XmlElements({
            @XmlElement(name = "jpa-store", type = JpaStore.class),
            @XmlElement(name = "es-store", type = EsStore.class)
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

    public boolean containsLookupProcess(ProcessSysModel model) {
        if (model.getArgs() != null && model.getName() != null)
            return containsLookupProcess(model.getArgs()) || containsLookupProcess(model.getName());
        else
            return model.getArgs() == null && containsLookupProcess(model.getName());

    }


    public double getThresholdByPid(long pid) {
        double result = -1d;
        if (process != null) {
            for (Process p : process) {
                if (p.getPid() == pid) {
                    result = p.threshold;
                    break;
                }
            }
        }
        return result;
    }


}
