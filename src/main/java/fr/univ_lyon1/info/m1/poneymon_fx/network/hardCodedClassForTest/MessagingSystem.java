package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

import java.io.*;
import java.net.Socket;

public class MessagingSystem {
    private Socket socket;
    private PrintWriter writer = null;
    private BufferedInputStream reader = null;
    private ObjectInputStream objectReader = null;
    private ObjectOutputStream objectWriter = null;

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
            //objectReader = new ObjectInputStream(socket.getInputStream());
            objectWriter = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Send through the socket the message.
     *
     * @param message the message to send
     */
    public void sendMessage(String message) {
        writer.write(message);
        writer.flush();
    }

    public void sendCommand(Command cmd) {
        try {
            objectWriter.writeObject(cmd);
        } catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Wait for the reception of a message.
     *
     * @return the message received
     */
    public String receiveMessage() throws IOException {
        String response;
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    public Command receiveCommand() {
        try {
            if(objectReader == null){
                objectReader = new ObjectInputStream(socket.getInputStream());
            }
            return (Command) objectReader.readObject();
        } catch (IOException exception){
            exception.printStackTrace();
            return null;
        } catch (ClassNotFoundException exception){
            exception.printStackTrace();
            return null;
        }

    }

    /**
     * Close all the stream.
     */
    public void close() {
        try {
            writer.close();
            reader.close();
            objectWriter.close();
            objectReader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }
}
