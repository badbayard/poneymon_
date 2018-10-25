package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest.request;

public class CreateRequest extends Request {
    private static String type = "CREATE";
    private String name;
    private String password;

    public CreateRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public static String getType() {
        return type;
    }
}
