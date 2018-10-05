package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

/**
 * Class storing data relative to a Poney.
 */
public class PoneyModel implements Model {
    // Number of laps to win the race
    private static final int NB_LAPS = 5;
    // Minimal time for the poney to complete a lap (s)
    public static final int MINIMAL_TIME = 5;
    // Poney's row
    private final int row;
    // Poney's color
    private final String poneyColor;
    // Abscissa of the poney
    private double x;
    // Poney's speed
    private double speed;
    // Laps completed counter
    private int nbLap;
    // Poney's rank
    private int rank;
    // Flag for the boost
    private boolean isNian;
    // Flag up when boost has been used
    private boolean hasBeenNianPoney;
    // Flag for AI
    private boolean isAi;
    // Flag for the race completed
    private boolean raceFinished;
    // Other poneys
    private List<PoneyModel> neighbors = new ArrayList<>();
    // Available poney colors
    private static final String[] COLOR_MAP = new String[] {"blue", "green", "orange", "purple",
        "yellow"};
    // Notifies the controller to play a boost sound
    private boolean playSound = false;

    /**
     * PoneyModel constructor.
     *
     * @param color the poney color
     * @param r     the poney row
     * @param isIa  <code>true</code> if the poney is an AI.
     *              <code>false</code> otherwise.
     */
    public PoneyModel(String color, int r, boolean isIa) {
        x = 0.0;
        row = r;
        nbLap = 0;
        poneyColor = color;
        isNian = hasBeenNianPoney = raceFinished = false;
        isAi = isIa;

        // Random speed
        Random randomGenerator = new Random();
        speed = randomGenerator.nextFloat();
    }

    /**
     * Gives the algebraic distance between the current poney and the other.
     *
     * @param otherPoney the other poney needed to get a distance
     * @return the algebraic distance between the poneys
     */
    public double getRelativeDistanceTo(PoneyModel otherPoney) {
        final double distOther = otherPoney.nbLap + otherPoney.x;
        final double distCurrent = nbLap + x;

        return distCurrent - distOther;
    }

    /**
     * Adds a neighbor to poney.
     *
     * @param poney the neighbor to add
     */
    void addNeighbor(PoneyModel poney) {
        neighbors.add(poney);
    }

    /**
     * Removes a neighbor.
     *
     * @param poney the neighbor to remove
     */
    public void removeNeighbor(PoneyModel poney) {
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
            for (PoneyModel pm : neighbors) {
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
            Random randomGenerator = new Random();
            speed = randomGenerator.nextFloat();

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
     * Gets the nbLap attribute.
     *
     * @return the nbLap attribute
     */
    public int getNbLap() {
        return nbLap;
    }

    /**
     * Gets the poney color.
     *
     * @return the poney color
     */
    public String getColor() {
        return poneyColor;
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
     * Gets the poney abscissa.
     *
     * @return the poney abscissa
     */
    public double getX() {
        return x;
    }

    /**
     * Sets the poney abscissa.
     *
     * @param newValue the new poney abscissa
     */
    public void setX(double newValue) {
        x = newValue;
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
     * Sets the boost capacity flag.
     *
     * @param cap the new value for the flag
     */
    public void setBoostCapacity(boolean cap) {
        hasBeenNianPoney = !cap;
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
     * Gets the poney row.
     *
     * @return the poney row
     */
    public int getRow() {
        return row;
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
     * Gets the boost flag.
     *
     * @return the boost flag
     */
    public boolean isNianPoney() {
        return isNian;
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
     * Gets the boost capacity flag.
     *
     * @return the boost capacity flag
     */
    public boolean canBoost() {
        return !hasBeenNianPoney;
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
     * Determine la progression totale d'un poney.
     *
     * @return progression totale d'un poney
     */
    public double totalProgress() {
        return nbLap + x;
    }

    /**
     * constructeur par copie (remplace l'interface cloneable).
     *
     * @param clone PoneyModel
     */
    public PoneyModel(PoneyModel clone) {
        x = clone.getX();
        row = clone.getRow();
        nbLap = clone.getNbLap();
        poneyColor = clone.getColor();
        isNian = hasBeenNianPoney = raceFinished = clone.isNianPoney();
        isAi = clone.isAnAi();
        speed = clone.getSpeed();
    }

    /**
     * Returns true if a sound must be played and turn the flag to false, else returns false.
     * @return state of playSound
     */
    public boolean shouldPlaySound() {
        if (playSound) {
            playSound = false;
            return true;
        }
        return false;
    }
}
