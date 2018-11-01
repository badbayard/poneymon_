package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

import java.util.ArrayList;

public class ListRoom extends Room {

    private static ListRoom instance = null;
    private ArrayList<WaitingRoom> rooms;

    public ArrayList<WaitingRoom> getRooms() {
        return rooms;
    }

    private ListRoom() {
        super();
        rooms = new ArrayList<>();
    }

    public static synchronized ListRoom getInstance() {
        if (ListRoom.instance == null) {
            ListRoom.instance = new ListRoom();
        }
        return ListRoom.instance;
    }

    @Override
    public synchronized boolean join(Client player) {
        players.add(player);
        nbPlayers++;
        maxNbPlayers++;
        return true;
    }
}
