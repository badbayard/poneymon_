package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.ClientManger;

public class WaitingRoom extends Room {

    private static WaitingRoom instance = null;

    private WaitingRoom() {
        super(100);
    }

    public static synchronized WaitingRoom getInstance() {
        if (WaitingRoom.instance == null) {
            WaitingRoom.instance = new WaitingRoom();
        }
        return WaitingRoom.instance;
    }

    @Override
    public boolean join(ClientManger player) {
        // TODO waiting sans nombre max de joueurs
        return super.join(player);
    }
}
