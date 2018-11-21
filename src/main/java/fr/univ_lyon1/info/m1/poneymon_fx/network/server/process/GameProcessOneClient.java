package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.GameCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

public class GameProcessOneClient extends GameRoomProcess {

    public GameProcessOneClient(GameRoom gameRoom, Client client) {
        this.gameRoom = gameRoom;
        this.client = client;
    }

    @Override
    public void run() {
        while (isRunning) {
            GameCommand cmd = (GameCommand) client.receiveCommandEvt();
            /*
             * ReceiveCommand returns null if an IOException is thrown (ie.
             * something went wrong
             * network-wise).
             */
            if (cmd == null) {
                gameRoom.remove(client);
                close();
                return;
            }

            cmd.setGameRoom(gameRoom);
            if (cmd.atReceive()) {
                client.sendCommandEvt(new StringCommand("OK"));
            } else {
                client.sendCommandEvt(new StringCommand("NOK"));
            }
        }
    }
}
