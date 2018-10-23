package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model of the game board.
 */
public class FieldModel implements Model {
    // Number of laps to win the race
    private static final int NB_LAPS = 5;
    private PoneyModel[] poneys;

    // State of the poneys. True : AI, False : Human
    private static final boolean[] isAi = new boolean[] {true, true, true, false, false};

    private ArrayList<PoneyModel> rankings;

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
            poneys[i] = new PoneyModel(PoneyModel.getColor(i), i, isAi[i], NB_LAPS);
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
            rankPoney();
            checkRaceFinished();
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
     * Returns a specific poney from the field model.
     *
     * @param index index of the poney
     * @return poney at index in the arraylist of poneys
     */
    public PoneyModel getPoneyModel(int index) {
        return poneys[index];
    }

    /**
     * Getter on the number of PoneyModels.
     *
     * @return the number of PoneyModels stored in this instance
     */
    public int countPoneys() {
        return poneys.length;
    }

    /**
     * Renvoit la liste des indices triés des PoneyModel classés par
     * progresion croissante.
     */
    public void rankPoney() {
        rankings = new ArrayList<>(Arrays.asList(poneys));
        Collections.sort(rankings);

        int i = 1;
        for (PoneyModel poneyModel : rankings) {
            poneyModel.setRank(i++);
        }
    }

    private void checkRaceFinished() {
        for (PoneyModel pm : poneys) {
            if (!pm.getRaceFinished() && pm.getNbLap() == NB_LAPS) {
                pm.setRaceFinished(true);
            }
        }
    }

    /**
     * accesseur ranking.
     *
     * @return ranking
     */
    public List<PoneyModel> getRankings() {
        return rankings;
    }
}
