package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Class storing data relative to a Poney.
 */
public class PoneyModel extends MovingEntityModel implements Model {
    // Flag up when boost has been used
    private boolean usedBoost;
    private boolean boosted;

    /**
     * PoneyModel constructor.
     *
     * @param color the poney color
     * @param r     the poney row
     * @param isIa  <code>true</code> if the poney is an AI.
     *              <code>false</code> otherwise.
     */
    public PoneyModel(String color, int r, boolean isIa, int nbLaps) {
        super(color, r, nbLaps);
        x = 0.0;
        nbLap = 0;
        boosted = usedBoost = raceFinished = false;
        isAi = isIa;

        // Random speed
        setRandomSpeed();
    }

    /**
     * Triggers the boost if the AI is OK.
     */
    public void boostIfNecessary() {
        if (!usedBoost && aiNianMode()) {
            turnIntoNianPoney();
        }
    }

    /**
     * Turns the poney into nianPoney.
     */
    public void turnIntoNianPoney() {
        if (!usedBoost) {
            // Levé de drapeaux
            boosted = true;
            usedBoost = true;
            playSound = true;

            // Speed increased
            speed *= 2;
        }
    }

    /**
     * AI has to choose whether to trigger the boost.
     *
     * @return <code>true</code> if the AI chose to trigger the boost.
     *     <code>false</code> otherwise.
     */
    private boolean aiNianMode() {
        final int lapsLeft = nbLaps - nbLap - 1;
        return Math.pow(1 - speed, lapsLeft) > 0.65;
    }

    /**
     * The game just started, if the poney is an AI, it needs to choose to
     * boost.
     */
    public void start() {
        if (isAi) {
            boostIfNecessary();
        }
    }

    /**
     * Update of the poneyModel (displacement, lap completion...).
     *
     * @param msElapsed time elapsed since the last update
     */
    public void update(double msElapsed) {
        // Update if the race isn't finished
        if (raceFinished) {
            return;
        }

        x += speed * msElapsed / (MINIMAL_TIME * 1000);

        // Return to the left of the screen if a lap is completed
        if (x > 1) {
            //Check of poney state
            if (boosted) {
                boosted = false;
                usedBoost = true;
            }

            x = 0;

            // Change of speed
            setRandomSpeed();

            nbLap++;

            if (isAi) {
                // AI boost decision
                boostIfNecessary();
            }
        }
    }

    /**
     * Sets the boost capacity flag.
     *
     * @param cap the new value for the flag
     */
    public void setBoostCapacity(boolean cap) {
        usedBoost = !cap;
    }

    /* Gets the boost flag.
     *
     * @return the boost flag
     */
    public boolean isBoosted() {
        return boosted;
    }

    /**
     * Gets the boost capacity flag.
     *
     * @return the boost capacity flag
     */
    public boolean canBoost() {
        return !usedBoost;
    }

    boolean getUsedBoost() {
        return usedBoost;
    }

    /**
     * constructeur par copie (remplace l'interface cloneable).
     *
     * @param clone PoneyModel
     */
    public PoneyModel(PoneyModel clone) {
        super(clone);
        boosted = clone.isBoosted();
        usedBoost = clone.getUsedBoost();
        raceFinished = clone.getRaceFinished();
    }
}
