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
    private int portEvt;
    private int portCnt;
    private ServerSocket serverEvt = null;
    private ServerSocket serverCnt = null;
    private boolean isRunning = true;
    private ListRoom listRoom;

    /**
     * Constructeur par défaut du serveur, écotant sur toutes les adresses IP.
     */
    Server() {
        this(4242, 4243);
    }

    /**
     * Constructeur du serveur avec portEvt écoutant sur toutes les adresses IP.
     *
     * @param portEvt portEvt du serveur pour les événements
     * @param portCnt portEvt du serveur pour le transfert en continu
     */
    Server(int portEvt, int portCnt) {
        this.portEvt = portEvt;
        this.portCnt = portCnt;
        listRoom = ListRoom.getInstance();

        try {
            serverEvt = new ServerSocket(this.portEvt, 100);
            serverCnt = new ServerSocket(this.portCnt, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre le serveur.
     */
    void open() {
        System.err.println("server is running");
        Thread t = new Thread(() -> {
            while (isRunning) {
                try {
                    Socket clientSocketEvt = serverEvt.accept();
                    Socket clientSocketCnt = serverCnt.accept();

                    Client client = new Client(idClient, clientSocketEvt,
                            clientSocketCnt);
                    incrementId();

                    client.sendCommandEvt(new StringCommand("Welcome"));

                    if (ListRoom.getInstance().join(client)) {
                        ProcessManager.getProcessManager().createAndRunThread(
                                new ListRoomProcess(client));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                serverEvt.close();
                serverCnt.close();
            } catch (IOException e) {
                e.printStackTrace();
                serverEvt = null;
                serverCnt = null;
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

