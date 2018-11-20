package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.RoomCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

public class ListRoomProcess extends Process {
    private ListRoom listRoom;

    /**
     * Creates a process associated with a specific client to the ListRoomView.
     *
     * @param client the client the process is associated to
     */
    public ListRoomProcess(Client client) {
        this.client = client;
        listRoom = ListRoom.getInstance();
    }

    @Override
    public void run() {
        while (isRunning) {
            RoomCommand cmd = (RoomCommand) client.receiveCommandEvt();
            /*
             * ReceiveCommand returns null if an IOException is thrown (ie. something went wrong
             * network-wise).
             */
            if (cmd == null) {
                listRoom.remove(client);
                close();
                return;
            }

            cmd.setActualRoom(listRoom);
            cmd.atReceive();
            client.sendCommandEvt(new StringCommand("OK"));
        }
    }
}
