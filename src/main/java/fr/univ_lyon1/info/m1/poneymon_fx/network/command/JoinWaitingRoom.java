package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.WaitingRoomProcess;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;

public class JoinWaitingRoom extends RoomCommand {

    String name;
    String password;

    public JoinWaitingRoom(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void atReceive() {
        System.out.println(idPlayer + " envois : commande pour rejoindre un "
                + "partie.");

        System.out.println(
                "On cherche la partie : " + name + " avec le mot de passe : "
                        + password);
        if (actualRoom == null || actualRoom.getRooms().isEmpty()) {
            System.err.println(
                    "Pas de room sur laquelle rechercher les parties.");
        } else {
            System.out.println("Il y a des rooms pour chercher la partie.");
            Password hashedPassword = new Password(password);
            ArrayList<WaitingRoom> possibleRooms =
                    (ArrayList<WaitingRoom>) ((ListRoom) actualRoom).getRooms();

            WaitingRoom waitingRoom;

            if (possibleRooms == null) {
                System.err.println("Pas de parties joignables disponible.");
            } else {
                for (WaitingRoom possibleRoom : possibleRooms) {
                    waitingRoom = possibleRoom;

                    if (waitingRoom.getName().equals(name)) {
                        //&& waitingRoom.getPassword() == hashedPassword) {
                        System.out
                                .println("On a trouvé la bonne room, on join");

                        Client client = actualRoom.remove(idPlayer);
                        if (client != null && waitingRoom.join(client)) {
                            ProcessManager.getProcessManager()
                                    .createAndRunThread(
                                            new WaitingRoomProcess(client,
                                                    waitingRoom));
                        } else {
                            System.err.println("ECHEC Join!");
                        }
                    }
                }
            }
        }
    }

}
