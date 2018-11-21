package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

import java.util.ArrayList;
import java.util.List;

public class ShowWaitingRoomCmd extends Command {

    private List<WaitingRoom> rooms;

    public ShowWaitingRoomCmd(List<WaitingRoom> rooms) {
        this.rooms = rooms;
    }

    public List<WaitingRoom> getRooms() {
        return rooms;
    }

    @Override
    public boolean atReceive() {
        return true;
    }
}
