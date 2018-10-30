package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.ClientManager;

public class ListRoom extends Room {

    private static ListRoom instance = null;

    private ListRoom() {
        super(1);
    }

    public static synchronized ListRoom getInstance() {
        if (ListRoom.instance == null) {
            ListRoom.instance = new ListRoom();
        }
        return ListRoom.instance;
    }

    @Override
    public synchronized boolean join(ClientManager player) {
        // TODO waiting sans nombre max de joueurs
        return super.join(player);
    }
}
