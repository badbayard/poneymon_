package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

import java.util.List;

public class CreateWaitingRoom extends RoomCommand {

    private String name;
    private String password;

    public CreateWaitingRoom(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void atReceive() {
        System.out.println(idPlayer + " envois : commande pour crée un partie"
                + ".");
        System.out.println("On essaye créer la partie : " + name + " avec le "
                + "mot de passe : " + password);
        if (actualRoom == null) {
            System.err.println("Pas room assigné à la commande !");
        } else {
            boolean roomAlreadyExists = false;

            ListRoom lr;
            if (actualRoom instanceof ListRoom) {
                lr = (ListRoom) actualRoom;
            } else {
                return;
            }


            for (int i = 0; i < lr.getRooms().size(); ++i) {
                if (lr.getRooms().get(i).getName().equals(name)) {
                    roomAlreadyExists = true;
                }
            }
            if (roomAlreadyExists) {
                System.err.println("Room existe déjà !");
            } else {
                System.out.println("La room n'exsite pas, on l'a crée");
                WaitingRoom newRoom = new WaitingRoom(password, 5, name);
                lr.getRooms().add(newRoom);
                JoinWaitingRoom cmdJoin = new JoinWaitingRoom(name, password);
                cmdJoin.setActualRoom(actualRoom);
                cmdJoin.setIdPlayer(idPlayer);
                cmdJoin.atReceive();
            }
        }
    }

}
