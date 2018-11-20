package fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

import java.net.Socket;

public class CommunicationSystem {
    private int idClient;
    private Receiver receiver;
    private Forwarder forwarder;

    /**
     * Constructeur avec socket.
     *
     * @param socket socket du système
     */
    public CommunicationSystem(Socket socket, int idClient) {
        this.idClient = idClient;
        receiver = new Receiver(socket);
        forwarder = new Forwarder(socket);
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void sendCommand(Command cmd) {
        cmd.setIdPlayer(idClient);
        forwarder.sendCommand(cmd);
    }

    public Command receiveCommand() {
        return receiver.receiveCommand();
    }

    /**
     * Close all the stream.
     */
    public void close() {
        forwarder.close();
        receiver.close();
    }
}
