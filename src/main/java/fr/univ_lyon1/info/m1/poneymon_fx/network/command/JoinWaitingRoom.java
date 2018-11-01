package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ProcessClientWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.util.Password;

import java.util.ArrayList;

public class JoinWaitingRoom extends RoomCommand {

    String name;
    String password;

    @Override
    public void atReceive() {
        System.out.println("Commande pour rejoindre un partie.");
        System.out.println("On cherche la partie : " + name + " avec le mot " +
                "de passe : " + password);
        if (actualRoom == null) {
            System.err.println("Pas de room sur laquelle rechercher les " +
                    "parties.");
        } else {
            Password hashedPassword = new Password(password);
            ArrayList<WaitingRoom> possibleRooms =
                    ((ListRoom) actualRoom).getRooms();
            WaitingRoom waitingRoom;
            if (possibleRooms == null) {
                System.err.println("Pas de parties joignables disponible.");
            } else {
                for (int i = 0; i < possibleRooms.size(); ++i) {
                    waitingRoom = possibleRooms.get(i);
                    if (waitingRoom.getName() == name &&
                            waitingRoom.getPassword() == hashedPassword) {
                        System.out.println("On a trouvé la bonne room, on " +
                                "join");
                        Client client = waitingRoom.removeClient(idPlayer);
                        ProcessManager.getProcessManager().createAndRunThread(
                                new ProcessClientWaitingRoom(client,
                                        waitingRoom));
                    }
                }
            }
        }
    }

}
