package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;

public abstract class Room {
    protected ArrayList<Client> players;
    protected int nbPlayers = 0;
    protected int maxNbPlayers;
    protected String name = "Default";
    protected Password password;
    protected boolean hasPassword = false;

    Room() {
        players = new ArrayList<>(4);
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        players = new ArrayList<>(maxNbPlayers);
    }

    Room(String password) {
        this(password, 4);
    }

    Room(String password, int nbPlayers) {
        this.password = new Password(password);
        hasPassword = true;
        players = new ArrayList<>(nbPlayers);
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
            players.add(player);
            nbPlayers++;
            return true;
        }
        return false;
    }

    public Password getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public synchronized Client removeClient(int idClient) {
        int i = 0;
        while (players.get(i).getPlayerId() != idClient) {
            ++i;
        }
        Client res = players.get(i);
        players.remove(i);
        return res;
    }
}
