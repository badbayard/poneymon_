package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.collision.Collider;
import fr.univ_lyon1.info.m1.poneymon_fx.collision.Trigger;

public class FixedEntityModel extends EntityModel {

    // On which lap the fixedEntity will appear.
    protected int lapPosition;
    // Visible.
    protected boolean visible = false;
    //Race finished flag
    protected boolean raceFinished = false;
    
    /**
     * Getter for the raceFinished Flag
     */
    public boolean isRaceFinished() {
        return raceFinished;
    }

    /**
     * Setter for the raceFinished Flag
     */
    public void setRaceFinished(boolean raceFinished) {
        this.raceFinished = raceFinished;
    }

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

    /**
     * Accesseur lapPosition.
     * @return lapPosition
     */
    public int getLapPosition() {
        return lapPosition;
    }

    /**
     * Teste l'egalitée entre l'FixedEntityModel courante et une autre FixedEntityModel.
     * @param fixedEnt FixedEntityModel a tester
     * @return boolean
     */
    public boolean fixedEntityEquals(FixedEntityModel fixedEnt) {
        return (this.entityModelEquals(fixedEnt)
                && (this.getLapPosition() == fixedEnt.getLapPosition())
                && (this.isVisible() == fixedEnt.isVisible()));
    }
    
    @Override
    public void onTrigger(Collider col,Trigger tr) {
        
    }
}
