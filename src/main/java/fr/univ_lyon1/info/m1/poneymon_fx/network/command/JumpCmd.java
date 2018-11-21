package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

public class JumpCmd extends GameCommand {

    @Override
    public boolean atReceive() {
        if (gameRoom == null) {
            return false;
        } else {
            int index = gameRoom.getIndexClient(idPlayer);
            PoneyModel poney =
                    (PoneyModel) gameRoom.getFieldModel()
                            .getParticipantModel(index);
            if (!poney.isJumping()) {
                poney.jump();
            }
            return true;
        }
    }
}
