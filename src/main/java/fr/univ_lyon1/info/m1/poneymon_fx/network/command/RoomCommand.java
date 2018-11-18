package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

public class RoomCommand extends Command {

    ListRoom actualRoom;

    public void setActualRoom(ListRoom actualRoom) {
        this.actualRoom = actualRoom;
    }
}
