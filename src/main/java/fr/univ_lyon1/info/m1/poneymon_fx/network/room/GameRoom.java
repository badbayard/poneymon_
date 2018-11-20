package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ServerMultiController;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class GameRoom extends Room {
    private ServerMultiController serverMultiController;

    /**
     * Constructeur par défault.
     */
    public GameRoom() {
        // Creates five poneys in the game field
        FieldModel fieldModel = new FieldModel(5, true);
        fieldModel.setNeighbor();

        serverMultiController = new ServerMultiController(fieldModel);
        serverMultiController.setFieldModel(fieldModel);
        serverMultiController.startTimer();

    }

    /**
     * Constructeur avec un fieldModel en parametre.
     * @param model le model en parametre
     */
    public GameRoom(FieldModel model) {
        model.setNeighbor();
        serverMultiController = new ServerMultiController(model);
        serverMultiController.startTimer();
    }

    public FieldModel getFieldModel(){
        return serverMultiController.getFieldModel();
    }
}
