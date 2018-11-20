package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.UpdateGameCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

import java.util.ArrayList;

public class MasterGameRoomProcess extends GameRoomProcess {
    public MasterGameRoomProcess(GameRoom actualRoom, Client host) {
        this.gameRoom = actualRoom;
        this.client = host;
    }

    public FieldModel getFieldModel() {
        return gameRoom.getFieldModel();
    }

    @Override
    public void run() {
        while (isRunning) {
            gameRoom.getServerMultiController().update();
            ArrayList<Client> clients = gameRoom.getClients();
            UpdateGameCmd cmd = new UpdateGameCmd(gameRoom.getFieldModel());
            System.out.println(
                    cmd.getFieldModel().getParticipantModel(0).getX());
            for (int i = 0; i < clients.size(); ++i) {
                clients.get(i).sendCommandEvt(cmd);
            }
        }
    }
}
