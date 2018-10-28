package fr.univ_lyon1.info.m1.poneymon_fx.network.communicationSystem;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Receiver {
    private Socket socket;
    private ObjectInputStream objectReader = null;

    public Receiver(Socket socket) {
        this.socket = socket;
    }

    public Command receiveCommand() {
        try {
            if (objectReader == null) {
                objectReader = new ObjectInputStream(socket.getInputStream());
            }
            return (Command) objectReader.readObject();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }

    }

    public void close() {
        try {
            objectReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
