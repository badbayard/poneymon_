package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

public class Main {

    /**
     * Méthode principale de la classe principale.
     *
     * @param args pas de paramètres ici
     */
    public static void main(String[] args) {

        String host = "127.0.0.1";
        int port = 1234;

        Server server = new Server(port);
        server.open();
        System.err.println("Serveur initialisé et lancé");

        Thread t = new Thread(new ProcessusClient(host, port));
        t.start();

    }
}
