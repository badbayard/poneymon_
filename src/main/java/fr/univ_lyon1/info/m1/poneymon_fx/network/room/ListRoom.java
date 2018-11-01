package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

import java.util.ArrayList;
import java.util.List;

public class ListRoom extends Room {

    private static ListRoom instance = null;
    private List<WaitingRoom> rooms;

    public List<WaitingRoom> getRooms() {
        return rooms;
    }

    private ListRoom() {
        super(2);
        rooms = new ArrayList<>();
    }

    /**
     * Return the current instance of Listroom, which is a singleton.
     *
     * @return the singleton instance of ListRoom
     */
    public static synchronized ListRoom getInstance() {
        if (ListRoom.instance == null) {
            ListRoom.instance = new ListRoom();
        }
        return ListRoom.instance;
    }

    @Override
    public synchronized boolean join(Client player) {
        clients.add(player);
        nbPlayers++;
        maxNbPlayers++;
        return true;
    }
}
