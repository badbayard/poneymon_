package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;

import fr.univ_lyon1.info.m1.poneymon_fx.view.View;

import fr.univ_lyon1.info.m1.poneymon_fx.model.Model;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.FieldView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.PoneyView;

/**
 * Controller of the game, handle the time.
 */
public class Controller {
    
    // Subscribed views for display events
    private List<View> views = new ArrayList<View>();
    // Subscribed models for update events
    private List<Model> models = new ArrayList<Model>();
    // Timer handling the time in game
    private AnimationTimer timer;
    // Store the timestamps of the last timer update
    private long lastTimerUpdate;
    // The game is launched
    private boolean timerActive;
    // The game is paused
    private boolean resume;
    // The game is finished
    private boolean gameOver = false;
    // Sound controller managed by this controller
    private SoundController soundController = new SoundController();

    /**
     * Controller constructor.
     */
    public Controller() {
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                //Prevent from resuming the game when the race is over
                if (!gameOver) {
                    //Allow to resume the game to it's last position
                    if (resume) {
                        lastTimerUpdate = currentNanoTime;
                        resume = false;
                    }
                    // Time elapsed since the last update
                    double msElapsed =
                        (currentNanoTime - lastTimerUpdate) / 1000000.0;
                    // Each time the event is triggered, update the model
                    updateModels(msElapsed);
                    // refresh the views
                    displayViews();
                    // update the last timer update
                    lastTimerUpdate = currentNanoTime;
                }
            }
        };
    }

    /**
     * Gives a new view to the controller.
     *
     * @param view the view that wants to subscribe to the display event
     */
    public void addView(View view) {
        views.add(view);
    }

    /**
     * Gives a new model to the controller.
     *
     * @param model the model that wants to subscribe to the update event
     */
    public void addModel(Model model) {
        models.add(model);
    }

    /**
     * Requests the models to update.
     *
     * @param msElapsed time elapsed since last update
     */
    public void updateModels(double msElapsed) {
        for (Model m : models) {
            m.update(msElapsed);
        }
    }

    /**
     * Requests the views to be rendered.
     */
    public void displayViews() {
        for (View v : views) {
            v.display();
        }
    }

    /**
     * Starts the controller's timer.
     */
    public void startTimer() {
        // Notify models of the start
        for (Model m : models) {
            m.start();
        }

        // Launch the timer
        lastTimerUpdate = System.nanoTime();
        timer.start();
        timerActive = true;
        resume = false;
    }

    /**
     * Pauses the game.
     */
    public void pause() {
        timer.stop();
        timerActive = false;
        resume = false;
        soundController.pause();
    }

    /**
     * Resumes the game.
     */
    public void resume() {
        timer.start();
        timerActive = true;
        resume = true;
        soundController.resume();
    }

    /**
     * Handles mouseclick.
     *
     * @param xClick    the abscissa of the click
     * @param yClick    the ordinate of the click
     * @param fieldView the fieldView in which the click happened
     */
    public void mouseClicked(double xClick, double yClick,
                                FieldView fieldView) {
        fieldView.manageClick(xClick, yClick);
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
     * Decides whether to pause or resume the game.
     */
    public void pauseResumeButton() {
        if (timerActive) {
            pause();
        } else {
            resume();
        }
    }

    /**
     * Asks to the SoundController to play a sound.
     */
    public void playBoostSound() {
        soundController.playBoostSound();
    }

    /**
     * Gets the timerActive flag.
     *
     * @return <code>true</code> if the timer is active.
     *         <code>false</code> otherwise.
     */
    public boolean getTimerActive() {
        return timerActive;
    }
}
