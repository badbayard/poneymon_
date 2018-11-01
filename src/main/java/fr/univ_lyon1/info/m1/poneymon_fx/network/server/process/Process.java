package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

public abstract class Process implements Runnable {
    protected Client client;
    boolean isRunning = true;

    void close() {
        isRunning = false;
    }
}
