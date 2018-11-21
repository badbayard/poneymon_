package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ServerTimer;

public class ServerMultiController extends Controller {
    private ServerTimer serverTimer;

    /**
     * Initialize the controller.
     * @param model the model to use in the controller
     */
    public ServerMultiController(FieldModel model) {
        this.fieldModel = model;
        serverTimer = new ServerTimer();
        serverTimer.start();
    }

    /**
     * Accesseur à fieldModel permettant au Seveur de récupérer l'objet et
     * l'envoyer aux clients.
     *
     * @return le fieldModel tournant sur le serveur
     */
    public FieldModel getFieldModel() {
        return fieldModel;
    }

    @Override
    void step(long currentNanoTime) {
        if (!gameOver) {
            // Each time the event is triggered, update the model
            fieldModel.update(currentNanoTime / 1e6);
            // Check for collisions
            FieldModel.COLLISIONMANAGER.checkCollision();
        }
    }

    /**
     * Update the model.
     */
    public void update() {
        serverTimer.stop();
        step(serverTimer.getTimeNano());
        serverTimer.start();
    }
}
