package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    private int port = 4242;
    private ServerSocket server = null;
    private boolean isRunning = true;

    /**
     * Constructeur par défaut du serveur.
     */
    public Server() {
        try {
            server = new ServerSocket(port, 100);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Constructeur du serveur avec port.
     * @param port port du serveur
     */
    public Server(int port) {
        this.port = port;
        try {
            server = new ServerSocket(port, 100);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Ouvre le serveur.
     */
    public void open() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (isRunning == true) {
                    try {
                        Socket client = server.accept();
                        System.err.println("Connexion cliente reçue.");
                        Thread t = new Thread(new ProccessInServer(client));
                        t.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    server = null;
                }
            }
        });
        t.start();
    }

    public void close() {
        isRunning = false;
    }
}
