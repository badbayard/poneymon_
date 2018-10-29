package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.poneymon_fx.collision.CollisionManager;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.FieldView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.View;
import javafx.animation.AnimationTimer;

/**
 * Controller of the game, handle the time.
 */
public class Controller {

    // Subscribed views for display events
    private List<View> views = new ArrayList<>();
    // Subscribed models for update events
    private FieldModel fieldModel;
    // Tiny window displaying data about a participant
    private DataView dataView;
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

    public static final Controller CONTROLLER = new Controller();

    /**
     * Controller constructor.
     */
    public Controller() {
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                // Prevent from resuming the game when the race is over
                if (!gameOver) {
                    // Allow to resume the game to it's last position
                    if (resume) {
                        lastTimerUpdate = currentNanoTime;
                        resume = false;
                    }
                    // Time elapsed since the last update
                    double msElapsed = (currentNanoTime - lastTimerUpdate) / 1000000.0;
                    // Each time the event is triggered, update the model
                    fieldModel.update(msElapsed);
                    // Check for collisions
                    FieldModel.COLLISIONMANAGER.checkCollision();
                    // refresh the views
                    notifyViews();
                    // update the last timer update
                    lastTimerUpdate = currentNanoTime;
                    // Check if a boost sound must be played
                    playBoostSound();
                }
            }
        };
    }

    /**
     * Gives a new view to the controller.
     *
     * @param view
     *            the view that wants to subscribe to the display event
     */
    public void addView(View view) {
        views.add(view);
    }

    /**
     * Removes a specific view from the list of views.
     *
     * @param view
     *            the view that needs to be removed
     */
    public void removeView(View view) {
        views.remove(view);
    }

    /**
     * request the views to be rendered.
     */
    private void notifyViews() {
        for (View v : views) {
            v.update();
        }
    }

    /**
     * Starts the controller's timer.
     */
    public void startTimer() {
        // Notify models of the start
        fieldModel.start();

        // Launch the timer
        lastTimerUpdate = System.nanoTime();
        timer.start();
        timerActive = true;
        resume = false;
    }

    /**
     * Pause or resume the game depending on current state.
     */
    public void pauseResume() {
        timerActive = !timerActive;

        if (timerActive) {
            timer.start();
            soundController.resume();
            resume = true;
        } else {
            timer.stop();
            resume = false;
            soundController.pause();
        }
    }

    /**
     * Handles mouseclick.
     *
     * @param xClick
     *            the abscissa of the click
     * @param yClick
     *            the ordinate of the click
     * @param fieldView
     *            the fieldView in which the click happened
     */
    public void mouseClicked(double xClick, double yClick, FieldView fieldView) {
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
     * @param poneyModel
     *            the PoneyModel of the poney the user wants to boost
     */
    public void boostButton(PoneyModel poneyModel) {
        if (poneyModel.canBoost()) {
            poneyModel.turnIntoNianPoney();
        }
    }

    /**
     * Asks to the SoundController to play a sound.
     */
    public void playBoostSound() {
        PoneyModel[] poneys = (PoneyModel[]) fieldModel.getParticipantModels();
        for (PoneyModel pm : poneys) {
            if (pm.shouldPlaySound()) {
                soundController.playBoostSound();
            }
        }
    }

    /**
     * Gets the timerActive flag.
     *
     * @return <code>true</code> if the timer is active. <code>false</code> otherwise.
     */
    public boolean getTimerActive() {
        return timerActive;
    }

    /**
     * Sets the data view.
     *
     * @param dv
     *            the dataView
     */
    public void setDataView(DataView dv) {
        dataView = dv;
    }

    public DataView getDataView() {
        return dataView;
    }

    public void setFieldModel(FieldModel fm) {
        fieldModel = fm;
    }
}
