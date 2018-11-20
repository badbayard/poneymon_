package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ServerMultiController;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;

public class GameRoom extends Room {
    private ServerMultiController serverMultiController;

    public GameRoom(FieldModel model) {
        serverMultiController = new ServerMultiController(model);
    }
}
