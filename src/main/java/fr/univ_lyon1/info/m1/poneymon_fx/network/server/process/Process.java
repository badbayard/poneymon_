package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

public abstract class Process implements Runnable {
    Client client;
    boolean isRunning = true;
    CommunicationSystem messagingSystem;


    public Process(Client client) {
        this.client = client;
        messagingSystem = new CommunicationSystem(client.getSocket());
    }

    void close() {
        isRunning = false;
    }
}
