package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.FieldView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.View;
import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientController extends Controller {
    // Subscribed views for display events
    private List<View> views = new ArrayList<>();
    // Tiny window displaying data about a participant
    private DataView dataView;
    // Sound controller managed by this controller
    SoundController soundController = new SoundController();

    /**
     * Default constructor for ClientController.
     */
    public ClientController() {
        timer = new AnimationTimer() {
            public void handle(long currentNanoTime) {
                step(currentNanoTime);
            }
        };
    }

    @Override
    void step(long currentNanoTime) {
        super.step(currentNanoTime);

        notifyViews();
        playBoostSound();
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
     * Removes a specific view from the list of views.
     *
     * @param view the view that needs to be removed
     */
    public void removeView(View view) {
        views.remove(view);
    }

    /**
     * request the views to be rendered.
     */
    void notifyViews() {
        for (View v : views) {
            v.update();
        }
    }

    /**
     * Handles mouseclick.
     *
     * @param xClick    the abscissa of the click
     * @param yClick    the ordinate of the click
     * @param fieldView the fieldView in which the click happened
     */
    public void mouseClicked(double xClick, double yClick, FieldView fieldView) {
        fieldView.manageClick(xClick, yClick);
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
     * Sets the data view.
     *
     * @param dv the dataView
     */
    public void setDataView(DataView dv) {
        dataView = dv;
    }

    public DataView getDataView() {
        return dataView;
    }
}
