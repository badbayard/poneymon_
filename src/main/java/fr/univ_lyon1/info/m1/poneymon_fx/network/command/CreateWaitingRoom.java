package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ProcessClientWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;

public class CreateWaitingRoom extends RoomCommand {

    String name;
    String password;

    @Override
    public void atReceive() {
        System.out.println("Commande pour crée un partie.");
        System.out.println("On crée la partie : " + name + " avec le mot " +
                "de passe : " + password);


    }

}
