package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.poneymon_fx.collision.CollisionManager;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

/**
 * Controller of the game, handle the time.
 */
public abstract class Controller {

    // Subscribed models for update events
    FieldModel fieldModel;
    // Timer handling the time in game
    AnimationTimer timer;
    // Store the timestamps of the last timer update
    long lastTimerUpdate;
    // The game is finished
    boolean gameOver = false;

    private static Controller controller;

    /**
     * Controller constructor.
     */
    public Controller() {
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                step(currentNanoTime);
            }
        };
    }

    void step(long currentNanoTime) {
        // Prevent from resuming the game when the race is over
        if (gameOver) {
            return;
        }

        // Time elapsed since the last update
        double msElapsed = (currentNanoTime - lastTimerUpdate) / 1e6;
        // update the last timer update
        lastTimerUpdate = currentNanoTime;

        updateFieldModel(msElapsed, fieldModel);

        // Check for collisions
        FieldModel.COLLISIONMANAGER.checkCollision();
    }

    /**
     * Works for ServerMultiController and ClientSoloController.
     * Needs to be overridden in ClientMultiController.
     *
     * @param msElapsed number of ms since last update
     */
    void updateFieldModel(double msElapsed, FieldModel fm) {
        // Each time the event is triggered, update the model
        fieldModel.update(msElapsed, fm);
    }

    /**
     * Starts the controller's timer.
     */
    public void startTimer() {
        // Launch the timer
        lastTimerUpdate = System.nanoTime();
        timer.start();
    }

    /**
     * Ends the race.
     */
    public void endRace() {
        timer.stop();
        gameOver = true;
    }

    /**
     * Turns the poney into nian poney if he can boost.
     *
     * @param poneyModel the PoneyModel of the poney the user wants to boost
     */
    public void boostButton(PoneyModel poneyModel) {
        if (poneyModel.canBoost()) {
            poneyModel.turnIntoNianPoney();
        }
    }
    
    /**
     * Make the poney jump.
     *
     * @param poneyModel the PoneyModel of the poney the user wants to jump.
     */
    public void jumpButton(PoneyModel poneyModel) {
        if (!poneyModel.isJumping()) {
            poneyModel.startJump();
        }
    }

    public void setFieldModel(FieldModel fm) {
        fieldModel = fm;
    }

    public static Controller getInstance() {
        return controller;
    }

    public static Controller setInstance(Controller cont) {
        controller = cont;
        return controller;
    }

    public void exit() {
        Platform.exit();
    }
}
