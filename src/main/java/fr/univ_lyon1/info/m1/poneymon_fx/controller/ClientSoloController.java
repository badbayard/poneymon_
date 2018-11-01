package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import javafx.animation.AnimationTimer;

public class ClientSoloController extends ClientController {

    // The game is paused
    private boolean resume;
    // The game is launched
    private boolean timerActive;

    /**
     * Default constructor for ClientSoloController.
     */
    public ClientSoloController() {
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                step(currentNanoTime);
            }
        };
    }

    void step(long currentNanoTime) {
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
            fieldModel.update(msElapsed, fieldModel);
            // refresh the views
            notifyViews();
            // update the last timer update
            lastTimerUpdate = currentNanoTime;
            // Check if a boost sound must be played
            playBoostSound();
        }
    }

    /**
     * Starts the controller's timer.
     */
    @Override
    public void startTimer() {
        super.startTimer();
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
     * Gets the timerActive flag.
     *
     * @return <code>true</code> if the timer is active. <code>false</code> otherwise.
     */
    public boolean getTimerActive() {
        return timerActive;
    }
}
