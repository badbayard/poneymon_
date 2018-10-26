package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class ClientMultiController extends ClientController {
    /**
     * Override inherited method since in the case of a client in a multiplayer game, the controller
     * doesn't update the fieldModel, it only assigns the FieldModel received from the server.
     *
     * @param msElapsed number of ms since last update
     */
    @Override
    void updateFieldModel(double msElapsed, FieldModel fm) {
        // Each time the event is triggered, update the model
        fieldModel.update(msElapsed, fm);
    }
}
