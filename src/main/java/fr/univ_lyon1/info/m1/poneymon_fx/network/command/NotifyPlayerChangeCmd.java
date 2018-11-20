package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class NotifyPlayerChangeCmd extends RoomCommand {
    private int nbPlayers;

    NotifyPlayerChangeCmd(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public int getNbPlayers() {
        return nbPlayers;
    }
}
