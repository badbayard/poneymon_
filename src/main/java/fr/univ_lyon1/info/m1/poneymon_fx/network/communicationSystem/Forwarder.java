package fr.univ_lyon1.info.m1.poneymon_fx.network.communicationSystem;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Forwarder {
    private Socket socket;
    private ObjectOutputStream objectWriter = null;

    public Forwarder(Socket socket) {
        this.socket = socket;
        try {
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void sendCommand(Command cmd) {
        try {
            objectWriter.writeObject(cmd);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void close() {
        try {
            objectWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
