package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ListRoomProcess;

public class LeaveWaitingRoomCmd extends WaitingRoomCommand {

    @Override
    public void atReceive() {
        System.out.println(idPlayer + " envois : commande pour quitter une "
                + "salle d'attente.");

        if (actualRoom == null) {
            System.err.println("Le client n'est pas dans une waiting room.");
        } else {
            Client client = actualRoom.remove(idPlayer);

            if (client != null) {
                if (ListRoom.getInstance().join(client)) {
                    ProcessManager.getProcessManager().createAndRunThread(
                        new ListRoomProcess(client));

                    for (Client other : actualRoom.getClients()) {
                        if (other.equals(client)) {
                            continue;
                        }

                        other.sendCommandEvt(new NotifyPlayerChangeCmd(actualRoom.getNbPlayers()));
                    }
                } else {
                    System.err.println("ECHEC Join!");
                }
            } else {
                System.err.println("ECHEC Récupération client!");
            }
        }
    }
}
