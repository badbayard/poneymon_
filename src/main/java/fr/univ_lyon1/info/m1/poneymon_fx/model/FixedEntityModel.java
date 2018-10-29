package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.Console;

public class FixedEntityModel extends EntityModel {

    // On which lap the fixedEntity will appear.
    protected int lapPosition;
    // Visible.
    protected boolean visible = false;

    /**
     * Constructor for the fixedEntity.
     * 
     * @param r
     *            row of the object.
     * @param x
     *            Position of the object.
     * @param lapPosition
     *            On which lap the object is gonna show.
     */
    public FixedEntityModel(int r, double x, int lapPosition) {
        super(r);
        this.lapPosition = lapPosition;
        this.setX(x);
    }

    /**
     * Set the visibility of the object.
     */
    public void update(double msElapsed, int currentLap) {
        if (currentLap == lapPosition) {
            visible = true;
        } else {
            visible = false;
        }
    }

    public boolean isVisible() {
        return visible;
    }

}
