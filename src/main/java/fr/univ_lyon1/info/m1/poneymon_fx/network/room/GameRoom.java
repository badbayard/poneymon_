package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ServerMultiController;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class GameRoom extends Room {
    private ServerMultiController serverMultiController;

    GameRoom() {
        // Creates five poneys in the game field
        FieldModel fieldModel = new FieldModel(5, true);
        fieldModel.setNeighbor();

        serverMultiController = new ServerMultiController();
        serverMultiController.setFieldModel(fieldModel);

        serverMultiController.startTimer();
    }
}
