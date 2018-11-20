package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.room.GameRoom;

public class LaunchGame extends LeaveWaitingRoomCmd {


    @Override
    public void atReceive() {
        if (actualRoom == null || actualRoom.getIndexClient(idPlayer) != 0) {
            System.err.println("Pas de room ou joueurs pas host !");
        } else {
            GameRoom gameRoom = new GameRoom(actualRoom.getFieldModel());

            for (int i = 0; i < actualRoom.getClients().size(); ++i) {
                actualRoom.getClients().get(i)
                        .sendCommandEvt(new UpdateGameCmd(getFieldModel()));
            }
            
            // lancer les process (celui d'update et celui d'écoute des
            // actions )
            // pour tout les joueurs
        }
    }
}
