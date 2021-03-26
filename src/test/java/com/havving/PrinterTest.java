package com.havving;

import com.havving.system.domain.xml.Configs;
import com.havving.system.global.Constants;
import org.junit.Ignore;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;


/**
 * Created by HAVVING on 2021-03-07.
 */
public class PrinterTest {

    @Test
    @Ignore
    public void printConfig() {
        File xmlFile = new File("D:\\Project\\SysCollector\\src\\test\\resources\\syscollector.xml");
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Configs.class);
            Unmarshaller u = jaxbContext.createUnmarshaller();
            Configs configs = (Configs) u.unmarshal(xmlFile);
            Constants.setConfig(configs);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
