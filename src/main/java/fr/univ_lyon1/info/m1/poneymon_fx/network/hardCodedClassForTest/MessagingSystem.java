package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessagingSystem {
    private Socket socket;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;

    /**
     * Constructeur avec socket.
     *
     * @param socket socket du système
     */
    public MessagingSystem(Socket socket) {
        this.socket = socket;
        try {
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedInputStream(socket.getInputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
