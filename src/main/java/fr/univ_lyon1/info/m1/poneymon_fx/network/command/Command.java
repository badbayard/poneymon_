package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import java.io.Serializable;

public class Command implements Serializable {
    protected int id;

    public Command() {
        id = 0;
    }

    public Command(int id) {
        this.id = id;
    }

    public void affichage() {
        System.err.println("Je suis une Command : " + id);
    }
}
