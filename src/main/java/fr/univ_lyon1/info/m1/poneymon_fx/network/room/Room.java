package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;

public abstract class Room {
    ArrayList<Client> clients;
    int nbPlayers = 0;
    int maxNbPlayers;
    String name = "Default";
    Password password;
    boolean hasPassword = false;

    Room() {
        clients = new ArrayList<>(4);
        maxNbPlayers = 4;
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        clients = new ArrayList<>(maxNbPlayers);
    }

    Room(String password) {
        this(password, 4);
    }

    Room(String password, int nbPlayers) {
        this(nbPlayers);
        this.password = new Password(password);
        hasPassword = true;
        clients = new ArrayList<>(nbPlayers);
    }

    Room(String password, int nbPlayers, String name) {
        this(password, nbPlayers);
        this.name = name;
    }

    /**
     * Add a client to the room.
     *
     * @param client the client that joins the room
     * @return true if the client could join
     */
    public boolean join(Client client) {
        if (nbPlayers < maxNbPlayers) {
            clients.add(client);
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

    /**
     * Removes a client from the room.
     *
     * @param idClient the id of the client
     * @return the removed client
     */
    public synchronized Client remove(int idClient) {
        int i = 0;
        while (clients.get(i).getPlayerId() != idClient) {
            ++i;
        }
        Client res = clients.get(i);
        clients.remove(i);
        return res;
    }

    /**
     * Removes a client from the room.
     *
     * @param client the client that leaves the room
     * @return the removed client
     */
    public synchronized Client remove(Client client) {
        if (clients.remove(client)) {
            nbPlayers--;
        }

        return client;
    }
}
