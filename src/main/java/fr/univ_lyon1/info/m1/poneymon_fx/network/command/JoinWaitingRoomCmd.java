package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.WaitingRoomProcess;

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
    public boolean atReceive() {
        if (!(actualRoom instanceof ListRoom)
            || ((ListRoom) actualRoom).getRooms().isEmpty()) {
        } else {
            ArrayList<WaitingRoom> possibleRooms =
                (ArrayList<WaitingRoom>) ((ListRoom) actualRoom).getRooms();

            WaitingRoom waitingRoom;

            if (possibleRooms == null) {
                System.err.println("Pas de parties joignables disponible.");
            } else {
                for (WaitingRoom possibleRoom : possibleRooms) {

                    waitingRoom = possibleRoom;
                    if (waitingRoom.getName().equals(name)
                        && waitingRoom.getPassword().isExpectedPassword(password)) {

                        Client client = actualRoom.remove(idPlayer);
                        if (client != null) {
                            if (waitingRoom.join(client)) {
                                ProcessManager.getProcessManager()
                                    .createAndRunThread(
                                        new WaitingRoomProcess(client, waitingRoom));

                                notifyOtherPlayers(waitingRoom.getClients(),
                                    new NotifyPlayerChangeCmd(waitingRoom.getNbPlayers()));
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
