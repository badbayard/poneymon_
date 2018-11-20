package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.ListRoomProcess;

public class ExitWaitingRoom extends InGameCommand {

    @Override
    public void atReceive() {
        System.out.println(idPlayer + " : envois quitter la room");
        if (actualRoom == null) {
            System.err.println("Pas de room à quitter");
        } else {
            Client client = actualRoom.remove(idPlayer);
            if (client != null && ListRoom.getInstance().join(client)) {
                ProcessManager.getProcessManager()
                        .createAndRunThread(
                                new ListRoomProcess(client));
            } else {
                System.err.println("ECHEC Quit!");
            }
        }
    }
}
