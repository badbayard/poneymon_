package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.WaitingRoomCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

public class WaitingRoomProcess extends Process {
    private WaitingRoom waitingRoom;

    public WaitingRoomProcess(Client client, WaitingRoom waitingRoom) {
        this.client = client;
        this.waitingRoom = waitingRoom;
    }

    @Override
    public void run() {
        while (isRunning) {
            WaitingRoomCommand cmd = (WaitingRoomCommand) client.receiveCommandEvt();
            /*
             * ReceiveCommand returns null if an IOException is thrown (ie. something went wrong
             * network-wise).
             */
            if (cmd == null) {
                waitingRoom.remove(client);
                close();
                return;
            }

            cmd.setActualRoom(waitingRoom);
            if (cmd.atReceive()) {
                client.sendCommandEvt(new StringCommand("OK"));
            } else {
                client.sendCommandEvt(new StringCommand("NOK"));
            }
        }
    }
}
