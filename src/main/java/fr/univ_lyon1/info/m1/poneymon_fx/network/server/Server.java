package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.ClientManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ProcessClientWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ProcessListRoom;

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
    Server() {
        this(4242);
    }

    /**
     * Constructeur du serveur avec port écoutant sur toutes les adresses IP.
     *
     * @param port port du serveur
     */
    Server(int port) {
        this.port = port;
        listRoom = ListRoom.getInstance();
        try {
            server = new ServerSocket(port, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre le serveur.
     */
    void open() {
        Thread t = new Thread(() -> {
            while (isRunning) {
                try  {
                    Socket client = server.accept();
                    Thread t1 = new Thread(new ProcessListRoom(client, listRoom));
                    boolean aa = listRoom.join(new ClientManager(client));

                    if (aa) {
                        System.out.println("Client rejonit listroom");
                    } else {
                        System.out.println("Listroom pleine");
                    }

                    t1.start();
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
        });
        t.start();
    }

    public void close() {
        isRunning = false;
    }
}

