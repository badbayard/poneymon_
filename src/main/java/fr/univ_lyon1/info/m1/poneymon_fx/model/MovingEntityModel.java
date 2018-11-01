package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class MovingEntityModel extends EntityModel implements Model, Comparable<MovingEntityModel> {
    // Number of laps to win the race
    int nbLaps;
    // Minimal time for the Entity to complete a lap (s)
    public static final int MINIMAL_TIME = 5;
    // Minimal speed an entity can have
    public static final double MIN_SPEED = 0.3;
    // Max speed an entity can have
    public static final double MAX_SPEED = 1.0;
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
    protected static final String[] COLOR_MAP = new String[] {"blue", "green", "orange", "purple",
        "yellow"};
    protected List<MovingEntityModel> neighbors = new ArrayList<>();
    // Random number generator for speed
    static final Random RANDOM_GENERATOR = new Random();

    /**
     * Moving Entity Model constructor.
     */
    public MovingEntityModel(String entityColor, int row, int nbLaps) {
        super(row);
        this.entityColor = entityColor;
        nbLap = 0;
        this.nbLaps = nbLaps;
    }

    /**
     * Copy constructor for Moving Entity.
     */
    public MovingEntityModel(MovingEntityModel clone) {
        super(clone);
        nbLap = clone.getNbLap();
        entityColor = clone.getColor();
        speed = clone.getSpeed();
        isAi = clone.isAi();
    }

    /**
     * Sets the poney speed.
     *
     * @param s the new speed
     */
    public void setSpeed(double s) {
        speed = s;
    }

    /**
     * /**
     * Set the new random speed of the poney.
     */
    protected void setRandomSpeed() {
        speed = MIN_SPEED + (MAX_SPEED - MIN_SPEED) * RANDOM_GENERATOR.nextFloat();
    }

    void setRank(int r) {
        rank = r;
    }

    /**
     * Determine la progression totale d'un poney.
     *
     * @return progression totale d'un poney
     */
    public double getTotalProgress() {
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
     * @param lap the new value for nbLap
     * @throws IllegalArgumentException if lap is < 0
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
     * @param index index of the color
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
    public boolean isPlayer() {
        return !isAi;
    }

    /**
     * Gets the AI flag.
     *
     * @return the AI flag
     */
    public boolean isAi() {
        return isAi;
    }

    /**
     * Gets the end of race flag.
     *
     * @return the end of race flag
     */
    public boolean getRaceFinished() {
        return raceFinished;
    }

    void setRaceFinished(boolean rf) {
        raceFinished = rf;
    }

    /**
     * Adds a neighbor to poney.
     *
     * @param poney the neighbor to add
     */
    void addNeighbor(MovingEntityModel poney) {
        neighbors.add(poney);
    }

    /**
     * Removes a neighbor.
     *
     * @param poney the neighbor to remove
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
     * @param pm the other poney needed to get a distance
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
     * Generic update for an entity (displacement, lap completion...).
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
            x = 0;
            // Change of speed
            setRandomSpeed();

            nbLap++;

            if (isAi) {
                // AI generic decision
            }
        }
    }

    @Override
    public int compareTo(MovingEntityModel mem) {
        if (this.getTotalProgress() == mem.getTotalProgress()) {
            return 0;
        } else if (this.getTotalProgress() > mem.getTotalProgress()) {
            return -1;
        }
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
