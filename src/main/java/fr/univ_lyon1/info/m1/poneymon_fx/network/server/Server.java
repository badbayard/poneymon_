package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ProcessClientWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {

    private int port;
    private ServerSocket server = null;
    private boolean isRunning = true;
    private ListRoom listRoom;

    /**
     * Constructeur par défaut du serveur, écotant sur toutes les adresses IP.
     */
    public Server() {
        this(4242);
    }

    /**
     * Constructeur du serveur avec port écoutant sur toutes les adresses IP.
     *
     * @param port port du serveur
     */
    public Server(int port) {
        this.port = port;
        listRoom = ListRoom.getInstance();
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
                        Thread t = new Thread(new ProcessClientWaitingRoom());
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

