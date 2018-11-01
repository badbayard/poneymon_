package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Room {
    List<Client> clients;
    private int nbPlayers;
    private int maxNbPlayers;
    private Password password;
    private boolean hasPassword = false;
    private String name = "Default";

    Room() {
        maxNbPlayers = 4;
        clients = new ArrayList<>();
    }

    Room(int nbPlayers) {
        maxNbPlayers = nbPlayers;
        clients = new ArrayList<>();
    }

    Room(String password, int nbPlayers) {
        this(nbPlayers);

        this.password = new Password(password);
        hasPassword = true;
    }

    Room(String password) {
        this(password, 4);
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
            nbPlayers++;
            clients.add(client);
            System.out.println("Client rejoint :" + nbPlayers + "/" + maxNbPlayers);
            return true;
        }

        System.out.println("Room pleine");
        return false;
    }

    /**
     * Remove a client from the room.
     * @param client the client that leaves the room
     * @return true if the client was in the room
     */
    public boolean remove(Client client) {
        if (clients.remove(client)) {
            nbPlayers--;
            return true;
        }

        return false;
    }

    public boolean findBySocketAndRemove(Socket client) {
        List<Client> resultat = clients.stream()
            .filter(c1 -> c1.getSocket().equals(client))
            .collect(Collectors.toList());

        if (resultat.isEmpty()) {
            return false;
        }

        if (clients.remove(resultat.get(0))) {
            nbPlayers--;
            return true;
        }

        return false;
    }
}
