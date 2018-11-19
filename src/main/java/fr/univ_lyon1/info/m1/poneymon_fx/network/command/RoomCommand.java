package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.Room;

public class RoomCommand extends Command {

    Room actualRoom;

    public void setActualRoom(Room actualRoom) {
        this.actualRoom = actualRoom;
    }
}
