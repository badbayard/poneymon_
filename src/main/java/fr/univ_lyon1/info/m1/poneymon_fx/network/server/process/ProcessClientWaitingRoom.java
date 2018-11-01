package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

public class ProcessClientWaitingRoom extends Process {
    private WaitingRoom waitingRoom;

    public  ProcessClientWaitingRoom(Client client, WaitingRoom waitingRoom){
        this.client = client;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {

    }
}
