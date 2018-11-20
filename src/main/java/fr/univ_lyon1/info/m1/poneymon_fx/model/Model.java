package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Interface for any model used in the game.
 */
public interface  Model {
    /**
     * Updates the specialized model.
     *
     * @param msElapsed time elapsed since last update
     */
    void update(double msElapsed);
}