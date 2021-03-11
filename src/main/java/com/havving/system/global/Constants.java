package com.havving.system.global;

import com.havving.system.domain.xml.Configs;

/**
 * Created by HAVVING on 2021-03-09.
 */
public class Constants {
    private static final Constants constants = new Constants();

    private Configs configs;

    private Constants() {}

    public static Constants getInstance() {
        if (constants == null) {
            synchronized (Constants.class) {
                return constants;
            }
        } else return constants;
    }

    public static Configs getConfig() {
        return getInstance().configs;
    }
}
