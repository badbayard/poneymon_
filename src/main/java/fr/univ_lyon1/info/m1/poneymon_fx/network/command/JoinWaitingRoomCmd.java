package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.WaitingRoomProcess;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;
import java.util.Arrays;

public class JoinWaitingRoomCmd extends RoomCommand {

    private String name;
    private char[] password;

    public JoinWaitingRoomCmd(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public void atReceive() {
        System.out.println(idPlayer + "envois : commande pour rejoindre une partie.");

        System.out.println("On cherche la partie : " + name
            + " avec le mot de passe : " + Arrays.toString(password));

        if (!(actualRoom instanceof ListRoom) || ((ListRoom) actualRoom).getRooms().isEmpty()) {
            System.err.println(
                "Pas de room sur laquelle rechercher les parties.");
        } else {
            System.out.println("Il y a des rooms pour chercher la partie.");

            ArrayList<WaitingRoom> possibleRooms =
                (ArrayList<WaitingRoom>) ((ListRoom) actualRoom).getRooms();

            WaitingRoom waitingRoom;

            if (possibleRooms == null) {
                System.err.println("Pas de parties joignables disponible.");
            } else {
                for (WaitingRoom possibleRoom : possibleRooms) {
                    waitingRoom = possibleRoom;

                    System.out.println(waitingRoom.getName().equals(name));

                    if (waitingRoom.getName().equals(name)
                        && waitingRoom.getPassword().isExpectedPassword(password)) {
                        System.out.println("On a trouvé la bonne room, on join");

                        Client client = actualRoom.remove(idPlayer);
                        if (client != null) {
                            if (waitingRoom.join(client)) {
                                ProcessManager.getProcessManager()
                                    .createAndRunThread(
                                        new WaitingRoomProcess(client,
                                            waitingRoom));
                            } else {
                                System.err.println("ECHEC Join!");
                            }
                        } else {
                            System.err.println("ECHEC Récupération client !");
                        }
                    }
                }
            }
        }
    }

}
