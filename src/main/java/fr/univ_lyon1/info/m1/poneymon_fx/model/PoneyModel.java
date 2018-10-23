package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Class storing data relative to a Poney.
 */
public class PoneyModel extends MovingEntityModel implements Model {
    private boolean isNian;
    // Flag up when boost has been used
    private boolean hasBeenNianPoney; 

    /**
     * PoneyModel constructor.
     *
     * @param color the poney color
     * @param r     the poney row
     * @param isIa  <code>true</code> if the poney is an AI.
     *              <code>false</code> otherwise.
     */
    public PoneyModel(String color, int r, boolean isIa) {
    	super(color,r);
        x = 0.0;
        nbLap = 0;
        isNian = hasBeenNianPoney = raceFinished = false;
        isAi = isIa;

        // Random speed
        setRandomSpeed();
    }

    /**
     * Triggers the boost if the AI is OK.
     */
    public void boostIfNecessary() {
        if (!hasBeenNianPoney && startNianMode()) {
            turnIntoNianPoney();
        }
    }

    /**
     * Turns the poney into nianPoney.
     */
    public void turnIntoNianPoney() {
        if (!hasBeenNianPoney) {
            // Levé de drapeaux
            isNian = true;
            hasBeenNianPoney = true;
            playSound = true;

            // Speed increased
            speed *= 2;
        }
    }

    /**
     * AI has to choose whether to trigger the boost.
     *
     * @return <code>true</code> if the AI chose to trigger the boost.
     * <code>false</code> otherwise.
     */
    private boolean startNianMode() {
        final int lapsLeft = NB_LAPS - nbLap - 1;
        return Math.pow(1 - speed, lapsLeft) > 0.5;
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
        if (!raceFinished) {
            x += speed * msElapsed / (MINIMAL_TIME * 1000);

            // Rank update
            rank = neighbors.size() + 1;
            for (MovingEntityModel pm : neighbors) {
                if (!pm.raceFinished
                    && Math.signum(getRelativeDistanceTo(pm)) == 1) {
                    rank--;
                }
            }
        }

        // Return to the left of the screen if a lap is completed
        if (x > 1) {
            //Check of poney state
            if (isNian) {
                isNian = false;
                hasBeenNianPoney = true;
            }

            x = 0;

            // Change of speed
            setRandomSpeed();

            nbLap++;

            // Is the race finished ?
            if (nbLap == NB_LAPS) {
                raceFinished = true;
            } else if (isAi) {
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
        hasBeenNianPoney = !cap;
    }

    /**
     * Gets the boost flag.
     *
     * @return the boost flag
     */
    public boolean isNianPoney() {
        return isNian;
    }

    /**
     * Gets the boost capacity flag.
     *
     * @return the boost capacity flag
     */
    public boolean canBoost() {
        return !hasBeenNianPoney;
    }


    /**
     * constructeur par copie (remplace l'interface cloneable).
     *
     * @param clone PoneyModel
     */
    public PoneyModel(PoneyModel clone) {
    	super(clone);
        isNian = hasBeenNianPoney = raceFinished = clone.isNianPoney();
    }

}
