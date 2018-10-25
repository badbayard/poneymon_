package fr.univ_lyon1.info.m1.poneymon_fx.controller;

public class ClientSoloController extends ClientController {

    // The game is paused
    private boolean resume;

    private ClientSoloController() {

    }

    /**
     * Starts the controller's timer.
     */
    @Override
    public void startTimer() {
        super.startTimer();
        resume = false;
    }
}
