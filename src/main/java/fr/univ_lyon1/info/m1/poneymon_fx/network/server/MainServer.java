package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

public class MainServer {

    /**
     * Lance le serveur.
     *
     * @param args
     */
    public static void main(String[] args) {
        Server server = new Server();
        server.open();
        System.err.println("server is running");
    }
}
