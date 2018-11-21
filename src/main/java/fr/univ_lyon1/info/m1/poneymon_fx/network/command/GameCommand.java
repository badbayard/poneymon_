package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

public class GameCommand extends Command {
    protected GameRoom gameRoom;

    public void setGameRoom(
            GameRoom gameRoom) {
        this.gameRoom = gameRoom;
    }
}
