package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.Serializable;

public abstract class EntityModel implements Model, Serializable {

    // Abscissa of the entity
    protected double x;
    // Entity's row
    protected final int row;

    EntityModel(int r) {
        x = 0.0;
        row = r;
    }

    /**
     * Sets the poney abscissa.
     *
     * @param newValue
     *            the new poney abscissa
     */
    public void setX(double newValue) {
        x = newValue;
    }

    public EntityModel(EntityModel clone) {
        x = clone.getX();
        row = clone.getRow();
    }

    /**
     * Gets the entity abscissa.
     *
     * @return the entity abscissa
     */
    public double getX() {
        return x;
    }

    /**
     * Gets the poney row.
     *
     * @return the poney row
     */
    public int getRow() {
        return row;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(double msElapsed) {
        // TODO Auto-generated method stub

    }

}
