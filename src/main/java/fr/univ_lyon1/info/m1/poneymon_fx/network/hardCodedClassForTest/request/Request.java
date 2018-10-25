package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest.request;

import java.io.Serializable;

public abstract class Request implements Serializable {
    private static String type;

    public static String getType() {
        return type;
    }
}
