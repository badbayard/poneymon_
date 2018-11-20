package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

public class WaitingRoomCommand extends Command {
    WaitingRoom actualRoom;

    public void setActualRoom(WaitingRoom waitingRoom) {
        this.actualRoom = waitingRoom;
    }

    public FieldModel getFieldModel() {
        return actualRoom.getFieldModel();
    }
}
