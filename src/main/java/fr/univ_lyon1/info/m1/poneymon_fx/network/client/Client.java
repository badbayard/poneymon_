package fr.univ_lyon1.info.m1.poneymon_fx.network.client;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

import java.net.Socket;

/**
 * Class representing a human player for the server.
 */
public class Client {
    private boolean isChief = false;
    private int playerId;
    private CommunicationSystem communicationSystemEvt, communicationSystemCnt;
    private Socket socketEvt, socketCnt;
    private MovingEntityModel movingEntityModel;

    /**
     * Constructor for client, assigning a unique int ID and a communication socketEvt.
     *
     * @param id  unique int ID
     * @param evt communication socketEvt event based
     * @param cnt communication socketCnt continuous
     */
    public Client(int id, Socket evt, Socket cnt) {
        playerId = id;
        socketEvt = evt;
        socketCnt = cnt;
        communicationSystemEvt = new CommunicationSystem(socketEvt, playerId);
        communicationSystemCnt = new CommunicationSystem(socketCnt, playerId);
    }

    public boolean getChief() {
        return isChief;
    }

    public void setChief(boolean chief) {
        isChief = chief;
    }

    public Socket getSocketEvt() {
        return socketEvt;
    }

    public int getPlayerId() {
        return playerId;
    }

    public Command receiveCommandEvt() {
        return communicationSystemEvt.receiveCommand();
    }

    public void sendCommandEvt(Command cmd) {
        communicationSystemEvt.sendCommand(cmd);
    }

    public Command receiveCommandCnt() {
        return communicationSystemCnt.receiveCommand();
    }

    public void sendCommandCnt(Command cmd) {
        communicationSystemCnt.sendCommand(cmd);
    }

    public void setMovingEntityModel(MovingEntityModel movingEntityModel) {
        this.movingEntityModel = movingEntityModel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Client)) {
            return false;
        }

        Client c = (Client) obj;
        return playerId == c.getPlayerId();
    }
}
