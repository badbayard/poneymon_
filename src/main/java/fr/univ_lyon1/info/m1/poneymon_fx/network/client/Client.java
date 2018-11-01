package fr.univ_lyon1.info.m1.poneymon_fx.network.client;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

import java.net.Socket;
import java.util.Random;

/**
 * Class permettant de représenter un joueur humain aux yeux du serveur.
 */
public class Client {
    private boolean isChief = false;
    private int playerId;
    private CommunicationSystem communicationSystem;
    private Socket client;

    public Client(Integer id, Socket c) {
        playerId = id;
        client = c;
        communicationSystem = new CommunicationSystem(client);
    }

    public int getPlayerId() {
        return playerId;
    }

    public boolean getChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public void sendCommand(Command cmd) {
        communicationSystem.sendCommand(cmd);
    }

    public Command receiveCommand() {
        return communicationSystem.receiveCommand();
    }
}
