package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import java.io.Serializable;

public class Command implements Serializable {

    public Command() {
    }

    public void affichage() {
        System.err.println("Je suis une simple Command : ");
    }
}