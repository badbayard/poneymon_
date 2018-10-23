package fr.univ_lyon1.info.m1.poneymon_fx.model;

/**
 * Model of the game board.
 */
public class FieldModel implements Model {

    private MovingEntityModel[] participants;

    // State of the participants. True : AI, False : Human
    private static final boolean[] isIa = new boolean[] { true, true, true, false, false };

    private int[] ranking;

    /**
     * FieldModel constructor.
     *
     * @param nbParticipants
     *            the number of participants in the game
     */
    public FieldModel(final int nbParticipants) {
        ranking = new int[nbParticipants];
        // If the number of participants is acceptable
        if (2 <= nbParticipants && nbParticipants <= 5) {
            participants = new PoneyModel[nbParticipants];
        } else { // 5 poneys by default
            participants = new PoneyModel[5];
        }

        // Initializing participants
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new PoneyModel(PoneyModel.getColor(i), i, isIa[i]);
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
     * @param msElapsed
     *            time elapsed in ms
     */
    public void update(final double msElapsed) {
        for (MovingEntityModel participant : participants) {
            participant.update(msElapsed);
        }
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
     * @param index
     *            index of the participant
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
     * Renvoit la liste des indices triés des MovingEntityModels classés par progresion croissante.
     */
    public void rankParticipant() {
        MovingEntityModel[] participantCpy = new MovingEntityModel[participants.length];
        for (int i = 0; i < participants.length; i++) {
            participantCpy[i] = new MovingEntityModel(participants[i]);
        }
        int indiceMax;
        for (int i = 0; i < participantCpy.length; i++) {
            indiceMax = max(participantCpy);
            ranking[i] = indiceMax;
        }
    }

    /**
     * Recherche la progression maximum dans le tableau de poneyCpy.
     *
     * @return indice du maximum
     */
    private int max(MovingEntityModel[] participantCpy) {
        int indiceMax = 0;
        double maxim = participantCpy[0].totalProgress();
        for (int i = 1; i < participantCpy.length; i++) {
            if (maxim < participantCpy[i].totalProgress()) {
                maxim = participantCpy[i].totalProgress();
                indiceMax = i;
            }
        }
        participantCpy[indiceMax].setX(0);
        participantCpy[indiceMax].setNbLap(0);
        return indiceMax;
    }

    /**
     * accesseur ranking.
     * 
     * @return ranking
     */
    public int[] getRanking() {
        return ranking;
    }
}
