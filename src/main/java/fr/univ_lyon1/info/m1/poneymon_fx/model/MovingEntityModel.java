package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MovingEntityModel extends EntityModel implements Model {
    // Number of laps to win the race
    protected static final int NB_LAPS = 5;
    // Minimal time for the Entity to complete a lap (s)
    public static final int MINIMAL_TIME = 5;
    // Minimal speed an entity can have
    public static final double MINIMAL_SPEED = 0.3;
    // Entity's color
    protected final String entityColor;
    // Entity's speed
    protected double speed;
    // Laps completed counter
    protected int nbLap;
    // Entity's rank
    protected int rank;
    // Flag for AI
    protected boolean isAi;
    // Notifies the controller to play a boost sound
    protected boolean playSound = false;
    // Flag for race's end
    protected boolean raceFinished;
    // Available Entity colors
    protected static final String[] COLOR_MAP = new String[] { "blue", "green", "orange", "purple",
        "yellow" };
    protected List<MovingEntityModel> neighbors = new ArrayList<>();

    /**
     * Moving Entity Model constructor.
     */
    public MovingEntityModel(String entityColor, int row) {
        super(row);
        this.entityColor = entityColor;
        nbLap = 0;
    }

    /**
     * Copy constructor for Moving Entity.
     */
    public MovingEntityModel(MovingEntityModel clone) {
        super(clone);
        nbLap = clone.getNbLap();
        entityColor = clone.getColor();
        speed = clone.getSpeed();
        isAi = clone.isAnAi();
    }

    /**
     * Sets the poney speed.
     *
     * @param s
     *            the new speed
     */
    public void setSpeed(double s) {
        speed = s;
    }

    /**
     * Set the new random speed of the poney.
     */
    protected void setRandomSpeed() {
        speed = 0;
        while (speed < MINIMAL_SPEED) {
            Random randomGenerator = new Random();
            speed = randomGenerator.nextFloat();
        }
    }

    /**
     * Determine la progression totale d'un poney.
     *
     * @return progression totale d'un poney
     */
    public double totalProgress() {
        return nbLap + x;
    }

    /**
     * Gets the poney rank.
     *
     * @return the poney rank
     */
    public int getRank() {
        return rank;
    }

    /**
     * Gets the nbLap attribute.
     *
     * @return the nbLap attribute
     */
    public int getNbLap() {
        return nbLap;
    }

    /**
     * Sets the nbLap attributes.
     *
     * @param lap
     *            the new value for nbLap
     * @throws IllegalArgumentException
     *             if lap is < 0
     */
    public void setNbLap(int lap) throws IllegalArgumentException {
        if (lap >= 0) {
            nbLap = lap;
        } else {
            throw new IllegalArgumentException("NbLap must be >= 0");
        }
    }

    /**
     * Gets the poney color.
     *
     * @return the poney color
     */
    public String getColor() {
        return entityColor;
    }

    /**
     * Returns the color at position index in the color map.
     *
     * @param index
     *            index of the color
     * @return color at position index
     */
    static String getColor(int index) {
        return COLOR_MAP[index % COLOR_MAP.length];
    }

    /**
     * Gets the poney speed.
     *
     * @return the poney speed
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Gets the player flag (inverse of the AI flag).
     *
     * @return the Player flag
     */
    public boolean isAPlayer() {
        return !isAi;
    }

    /**
     * Gets the AI flag.
     *
     * @return the AI flag
     */
    public boolean isAnAi() {
        return isAi;
    }

    /**
     * Gets the end of race flag.
     *
     * @return the end of race flag
     */
    public boolean hasFinishedTheRace() {
        return raceFinished;
    }

    /**
     * Adds a neighbor to poney.
     *
     * @param poney
     *            the neighbor to add
     */
    void addNeighbor(MovingEntityModel poney) {
        neighbors.add(poney);
    }

    /**
     * Removes a neighbor.
     *
     * @param poney
     *            the neighbor to remove
     */
    public void removeNeighbor(MovingEntityModel poney) {
        neighbors.remove(poney);
    }

    /**
     * Counts the neighbors.
     *
     * @return the number of neighbors
     */
    public int countNeighbors() {
        return neighbors.size();
    }

    /**
     * Gives the algebraic distance between the current Entity and the other.
     *
     * @param pm
     *            the other poney needed to get a distance
     * @return the algebraic distance between the poneys
     */
    public double getRelativeDistanceTo(MovingEntityModel pm) {
        final double distOther = pm.nbLap + pm.x;
        final double distCurrent = nbLap + x;

        return distCurrent - distOther;
    }

    /**
     * Returns true if a sound must be played and turn the flag to false, else returns false.
     * 
     * @return state of playSound
     */
    public boolean shouldPlaySound() {
        if (playSound) {
            playSound = false;
            return true;
        }
        return false;
    }

    /**
     * Generic start function.
     */
    public void start() {

    }

    /**
     * Generic update for an entity (displacement, lap completion...).
     *
     * @param msElapsed
     *            time elapsed since the last update
     */
    public void update(double msElapsed) {
        // Update if the race isn't finished
        if (!raceFinished) {
            x += speed * msElapsed / (MINIMAL_TIME * 1000);

            // Rank update
            rank = neighbors.size() + 1;
            for (MovingEntityModel pm : neighbors) {
                if (!pm.raceFinished && Math.signum(getRelativeDistanceTo(pm)) == 1) {
                    rank--;
                }
            }
        }

        // Return to the left of the screen if a lap is completed
        if (x > 1) {
            x = 0;
            // Change of speed
            setRandomSpeed();

            nbLap++;

            // Is the race finished ?
            if (nbLap == NB_LAPS) {
                raceFinished = true;
            } else if (isAi) {
                // AI generic decision

            }
        }
    }
}
