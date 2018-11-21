package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ServerMultiController;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;

import java.util.ArrayList;

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

    }

    /**
     * Constructeur avec un fieldModel en parametre.
     *
     * @param model   le model en parametre
     * @param clients les clients du jeu
     */
    public GameRoom(FieldModel model, ArrayList<Client> clients) {
        this.clients = clients;
        model.setNeighbor();
        serverMultiController = new ServerMultiController(model);
    }

    public FieldModel getFieldModel() {
        return serverMultiController.getFieldModel();
    }

    public ServerMultiController getServerMultiController() {
        return serverMultiController;
    }
}
