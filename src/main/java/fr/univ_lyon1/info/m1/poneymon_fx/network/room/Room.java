package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest.Player;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

public abstract class Room {
    Player[] players;
    private int nbPlayers;
    private int maxNbPlayers;
    private Password password;
    private boolean hasPassword = false;

    Room() {
        players = new Player[4];
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        players = new Player[maxNbPlayers];
    }

    Room(String password) {
        this.password = new Password(password);
        hasPassword = true;
    }

    Room(String password, int nbPlayers) {
        this(password);
        players = new Player[nbPlayers];
    }

    public boolean join(Player player) {
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
