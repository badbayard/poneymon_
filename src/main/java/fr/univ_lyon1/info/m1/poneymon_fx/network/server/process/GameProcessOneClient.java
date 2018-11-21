package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

public class GameProcessOneClient extends GameRoomProcess {

    public GameProcessOneClient(GameRoom gameRoom, Client client) {
        this.gameRoom = gameRoom;
        this.client = client;
    }

    @Override
    public void run() {
        while (isRunning) {


        }
    }
}
