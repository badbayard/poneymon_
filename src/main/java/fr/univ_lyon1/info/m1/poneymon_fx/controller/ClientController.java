package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class ClientController extends Controller {
    // Subscribed views for display events
    private List<View> views = new ArrayList<>();
    // Tiny window displaying data about a participant
    private DataView dataView;
    // Sound controller managed by this controller
    private SoundController soundController = new SoundController();

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
    private void notifyViews() {
        for (View v : views) {
            v.update();
        }
    }
}
