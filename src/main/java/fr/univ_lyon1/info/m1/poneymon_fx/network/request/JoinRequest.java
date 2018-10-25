package fr.univ_lyon1.info.m1.poneymon_fx.network.request;

public class JoinRequest extends Request {
    private static String type = "JOIN";
    private String name;

    JoinRequest() {
        name = "";
    }

    JoinRequest(String name) {
        this.name = name;
    }
}
