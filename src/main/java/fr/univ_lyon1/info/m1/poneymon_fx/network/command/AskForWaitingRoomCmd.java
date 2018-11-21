package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

public class AskForWaitingRoomCmd extends RoomCommand {
    @Override
    public boolean atReceive() {
        if (actualRoom == null) {
            System.err.println("Pas room assigné à la commande !");
        } else {
            if (actualRoom instanceof ListRoom) {
                ListRoom lr = (ListRoom) actualRoom;
                Client client = lr.getClient(idPlayer);
                client.sendCommandEvt(new ShowWaitingRoomCmd(lr.getRooms()));
                return true;
            }
        }
        return false;
    }
}
