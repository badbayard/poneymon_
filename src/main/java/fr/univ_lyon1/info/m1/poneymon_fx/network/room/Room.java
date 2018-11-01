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
        maxNbPlayers = 4;
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        players = new ArrayList<>(maxNbPlayers);
    }

    Room(String password) {
        this(password, 4);
    }

    Room(String password, int nbPlayers) {
        this(nbPlayers);
        this.password = new Password(password);
        hasPassword = true;
        players = new ArrayList<>(nbPlayers);
    }

    Room(String password, int nbPlayers, String name) {
        this(password, nbPlayers);
        this.name = name;
    }

    /**
     * Add a client to the room.
     * @param client the client that joins the room
     * @return true if the client could join
     */
    public boolean join(Client client) {
        if (nbPlayers < maxNbPlayers) {
            players.add(client);
            nbPlayers++;
            System.out.println("Client rejoint :" + nbPlayers + "/" + maxNbPlayers);
            return true;
        }
        System.out.println("Room pleine");
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
