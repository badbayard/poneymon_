package fr.univ_lyon1.info.m1.poneymon_fx.network.client;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

import java.net.Socket;

/**
 * Class representing a human player for the server.
 */
public class Client {
    private boolean isChief = false;
    private int playerId;
    private CommunicationSystem communicationSystem;
    private Socket socket;

    /**
     * Constructor for client, assigning a unique int ID and a communication socket.
     *
     * @param id unique int ID
     * @param s  communication socket
     */
    public Client(int id, Socket s) {
        playerId = id;
        socket = s;
        communicationSystem = new CommunicationSystem(socket, playerId);
    }

    public boolean getChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public Socket getSocket() {
        return socket;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Command receiveCommand() {
        return communicationSystem.receiveCommand();
    }

    public void sendCommand(Command cmd) {
        communicationSystem.sendCommand(cmd);
    }
}
