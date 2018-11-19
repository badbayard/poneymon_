package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ListRoomProcess;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static Integer idClient = 0;
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
                try {
                    Socket clientSocket = server.accept();

                    Client client = new Client(idClient, clientSocket);
                    incrementId();
                    client.sendCommand(new StringCommand("Welcome"));

                    if (ListRoom.getInstance().join(client)) {
                        ProcessManager.getProcessManager().createAndRunThread(
                                new ListRoomProcess(client));
                    }


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

    private void incrementId() {
        if (idClient == Integer.MAX_VALUE - 1) {
            idClient = 0;
        } else {
            idClient++;
        }
    }
}

