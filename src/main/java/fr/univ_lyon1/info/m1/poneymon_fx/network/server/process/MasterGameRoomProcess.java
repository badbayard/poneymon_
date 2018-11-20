package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.UpdateGameCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

import java.util.ArrayList;

public class MasterGameRoomProcess extends Process {
    private GameRoom actualRoom;

    public FieldModel getFieldModel() {
        return actualRoom.getFieldModel();
    }

    @Override
    public void run() {
        while (isRunning) {
            // actualRoom.getFieldModel().update(1, actualRoom.getFieldModel());
            ArrayList<Client> clients = actualRoom.getClients();
            UpdateGameCmd cmd = new UpdateGameCmd(actualRoom.getFieldModel());
            for (int i = 0; i < clients.size(); ++i) {
                clients.get(i).sendCommand(cmd);
            }
        }
    }
}
