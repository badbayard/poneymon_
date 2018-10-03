package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Model of the game board.
 */
public class FieldModel implements Model {

    private PoneyModel[] poneys;
    // Poneys' colors
    private static final String[] COLOR_MAP = new String[] {"blue", "green", "orange", "purple",
        "yellow"};
    // State of the poneys. True : AI, False : Human
    private static final boolean[] isIa = new boolean[] {true, true, true, false, false};

    /**
     * FieldModel constructor.
     *
     * @param nbPoneys the number of poneys in the game
     */
    public FieldModel(final int nbPoneys) {
        // If the number of poneys is acceptable
        if (2 <= nbPoneys && nbPoneys <= 5) {
            poneys = new PoneyModel[nbPoneys];
        } else { // 5 poneys by default
            poneys = new PoneyModel[5];
        }

        // Initializing poneys
        for (int i = 0; i < poneys.length; i++) {
            poneys[i] = new PoneyModel(COLOR_MAP[i], i, isIa[i]);
        }
        // make them know the others
        for (int i = 0; i < poneys.length; i++) {
            for (int j = 0; j < poneys.length; j++) {
                if (j != i) {
                    poneys[i].addNeighbor(poneys[j]);
                }
            }
        }
    }

    /**
     * Notify the model the game just started.
     */
    public void start() {
        for (PoneyModel poney : poneys) {
            poney.start();
        }
    }

    /**
     * Update the model and its components.
     *
     * @param msElapsed time elapsed in ms
     */
    public void update(final double msElapsed) {
        for (PoneyModel poney : poneys) {
            poney.update(msElapsed);
        }
    }

    /**
     * PoneyModels getter.
     *
     * @return the PoneyModels stored in this instance
     */
    public PoneyModel[] getPoneyModels() {
        return poneys;
    }

    /**
     * Getter on the number of PoneyModels.
     *
     * @return the number of PoneyModels stored in this instance
     */
    public int countPoneys() {
        return poneys.length;
    }
}
