package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.GameProcessOneClient;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.MasterGameRoomProcess;

import java.util.ArrayList;

public class LaunchGameCmd extends WaitingRoomCommand {

    @Override
    public boolean atReceive() {
        if (actualRoom == null || actualRoom.getIndexClient(idPlayer) != 0) {
            System.err.println("Pas de room ou joueurs pas host !");
        } else {
            ArrayList<Client> clients = actualRoom.getClients();
            GameRoom gameRoom = new GameRoom(actualRoom.getFieldModel(), clients);

            UpdateGameCmd cmd = new UpdateGameCmd(getFieldModel());
            for (Client client : clients) {
                client.sendCommandCnt(cmd);
                ProcessManager.getProcessManager().createAndRunThread(
                    new GameProcessOneClient(gameRoom, client));
            }
            ProcessManager.getProcessManager().createAndRunThreadNoClient(
                    new MasterGameRoomProcess(gameRoom, null));
            return true;
        }

        return false;
    }
}
