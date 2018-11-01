package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class CreateWaitingRoom extends RoomCommand {

    String name;
    String password;

    @Override
    public void atReceive() {
        System.out.println("Commande pour crée un partie.");
        System.out.println("On crée la partie : " + name + " avec le mot "
            + "de passe : " + password);
    }

}
