package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

public abstract class Room {
    protected Client[] players;
    private int nbPlayers;
    private int maxNbPlayers;
    private Password password;
    private boolean hasPassword = false;
    String name = "Default";

    Room() {
        players = new Client[4];
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        players = new Client[maxNbPlayers];
    }

    Room(String password) {
        this(password, 4);
    }

    Room(String password, int nbPlayers) {
        this.password = new Password(password);
        hasPassword = true;
        players = new Client[nbPlayers];
    }

    Room(String password, int nbPlayers, String name) {
        this(password, nbPlayers);
        this.name = name;
    }

    public boolean join(Client player) {
        if (nbPlayers < maxNbPlayers) {
            if (nbPlayers == 0) {
                player.setChief(true);
            } else {
                player.setChief(false);
            }

            players[nbPlayers++] = player;
            return true;
        }

        return false;
    }
}
