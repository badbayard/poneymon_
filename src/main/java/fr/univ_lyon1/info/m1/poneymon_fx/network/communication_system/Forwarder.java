package fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Forwarder {
    private Socket socket;
    private ObjectOutputStream objectWriter = null;

    /**
     * Constructor for Forwarder using a socket.
     *
     * @param socket socket corresponding to the Forwarder.
     */
    public Forwarder(Socket socket) {
        this.socket = socket;
        try {
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Sends a command through the socket.
     * @param cmd the command to send
     */
    public void sendCommand(Command cmd) {
        try {
            objectWriter.writeObject(cmd);
            objectWriter.flush();
            objectWriter.reset();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Close the Forwarder.
     */
    public void close() {
        try {
            objectWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
