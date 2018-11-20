package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

public class LaunchGame extends InGameCommand {

    @Override
    public void atReceive(){
        GameRoom gameRoom = new GameRoom(actualRoom.getFieldModel());
        // annoncer à tout les joueurs
        // lancer les process (celui d'update et celui d'écoute des actions )
        // pour tout les joueurs

    }
}
