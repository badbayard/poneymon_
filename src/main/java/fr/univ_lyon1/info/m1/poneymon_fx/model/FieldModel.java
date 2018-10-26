package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientSoloController;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model of the game board.
 */
public class FieldModel implements Model {
    private StaticEntityModel[] challengers;
    private MovingEntityModel[] participants;
    private static final int NB_LAPS = 5;
    private int participantsFinished = 0;

    // State of the poneys. True : AI, False : Human
    private static final boolean[] isAi = new boolean[] {true, true, true, false, false};

    private ArrayList<MovingEntityModel> rankings;

    /**
     * FieldModel constructor.
     *
     * @param nbParticipants the number of participants in the game
     */
    public FieldModel(final int nbParticipants) {
        // If the number of participants is acceptable
        if (2 <= nbParticipants && nbParticipants <= 5) {
            participants = new PoneyModel[nbParticipants];
        } else { // 5 poneys by default
            participants = new PoneyModel[5];
        }

        // Initializing participants
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new PoneyModel(PoneyModel.getColor(i), i, isAi[i], NB_LAPS);
        }

        // make them know the others
        for (int i = 0; i < participants.length; i++) {
            for (int j = 0; j < participants.length; j++) {
                if (j != i) {
                    participants[i].addNeighbor(participants[j]);
                }
            }
        }
    }

    /**
     * Notify the model the game just started.
     */
    public void start() {
        for (MovingEntityModel participant : participants) {
            participant.start();
        }
    }

    /**
     * Update the model and its components.
     *
     * @param msElapsed time elapsed in ms
     */
    public void update(final double msElapsed) {
        for (MovingEntityModel participant : participants) {
            participant.update(msElapsed);
            rankParticipants();
            checkRaceFinished();
        }
    }

    /**
     * If the model in parameters is different from the current FieldModel, it means we are in a
     * multiplayer context and we only have to assign the current FieldModel to the received one.
     * Else it means we have to update like usual.
     *
     * @param msElapsed time elapsed since last update in ms
     * @param fm other FieldModel to assign
     */
    public void update(final double msElapsed, FieldModel fm) {
        if (this == fm) {
            update(msElapsed);
            return;
        }

        participants = fm.participants;
        participantsFinished = fm.participantsFinished;
        rankings = fm.rankings;
    }

    /**
     * MovingEntityModels getter.
     *
     * @return the MovingEntityModels stored in this instance
     */
    public MovingEntityModel[] getParticipantModels() {
        return participants;
    }

    /**
     * Returns a specific participant from the field model.
     *
     * @param index index of the participant
     * @return participant at index in the arraylist of participants
     */
    public MovingEntityModel getParticipantModel(int index) {
        return participants[index];
    }

    /**
     * Getter on the number of MovingEntityModel.
     *
     * @return the number of MovingEntityModel stored in this instance
     */
    public int countParticipants() {
        return participants.length;
    }

    /**
     * Renvoit la liste des indices triés des PoneyModel classés par
     * progresion croissante.
     */
    public void rankParticipants() {
        rankings = new ArrayList<>(Arrays.asList(participants));
        Collections.sort(rankings);

        int i = 1;
        for (MovingEntityModel mem : rankings) {
            mem.setRank(i++);
        }
    }

    private void checkRaceFinished() {
        for (MovingEntityModel pm : participants) {
            if (!pm.getRaceFinished() && pm.getNbLap() == NB_LAPS) {
                pm.setRaceFinished(true);
                participantsFinished++;

                if (participantsFinished == participants.length) {
                    Controller.getInstance().endRace();
                }
            }
        }
    }

    /**
     * accesseur ranking.
     *
     * @return ranking
     */
    public List<MovingEntityModel> getRankings() {
        return rankings;
    }
}
