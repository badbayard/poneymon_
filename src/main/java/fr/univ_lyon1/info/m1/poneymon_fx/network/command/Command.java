package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

import java.io.Serializable;
import java.util.List;

public class Command implements Serializable {

    protected int idPlayer;

    public Command() {
    }

    public int getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(int idPlayer) {
        this.idPlayer = idPlayer;
    }

    public void affichage() {
        System.err.println("Je suis une simple Command : ");
    }


    public void beforeSend() {
    }

    public boolean atReceive() {
        return true;
    }

    void notifyOtherPlayers(List<Client> others, Command command) {
        for (Client other : others) {
            other.sendCommandCnt(command);
        }
    }
}
