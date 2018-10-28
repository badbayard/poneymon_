package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.ClientManger;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

public abstract class Room {
    protected ClientManger[] players;
    private int nbPlayers;
    private int maxNbPlayers;
    private Password password;
    private boolean hasPassword = false;

    Room() {
        players = new ClientManger[4];
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        players = new ClientManger[maxNbPlayers];
    }

    Room(String password) {
        this(password, 4);
    }

    Room(String password, int nbPlayers) {
        this.password = new Password(password);
        hasPassword = true;
        players = new ClientManger[nbPlayers];
    }

    public boolean join(ClientManger player) {
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
