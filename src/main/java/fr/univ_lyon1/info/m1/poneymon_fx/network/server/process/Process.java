package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

public abstract class Process implements Runnable {
    protected Client client;
    protected volatile boolean isRunning = true;

    public void close() {
        isRunning = false;
    }

    /**
     * Annonce la fin de vie d'un thread et ferme la boucle d'execution de ce
     * dernier.
     */
    public void deco() {
        System.out.println("Client disconnected");
        close();
        return;
    }

    public int getIdClient() {
        return client.getPlayerId();
    }
}
