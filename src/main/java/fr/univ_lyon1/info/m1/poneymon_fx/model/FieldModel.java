package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.collision.CollisionManager;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

import java.io.Serializable;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Model of the game board.
 */
public class FieldModel implements Model, Serializable {
    private LaneEntityModel[] lanes;
    private MovingEntityModel[] participants;
    private static final int NB_LAPS = 5;
    private transient int participantsFinished = 0;

    public static final CollisionManager COLLISIONMANAGER = new CollisionManager();
    // State of the poneys. True : AI, False : Human
    private static final boolean[] isAi = new boolean[] {true, true, true, false, false};

    private transient ArrayList<MovingEntityModel> rankings;

    private transient LevelBuilder levelsBuild = new LevelBuilder();

    /**
     * FieldModel constructor.
     * @param nbParticipants the number of participants in the game
     * @param soloGame wether this is a solo or multiplayer game
     */
    public FieldModel(final int nbParticipants, final boolean soloGame) {
        // If the number of participants is acceptable
        if (2 <= nbParticipants && nbParticipants <= 5) {
            participants = new PoneyModel[nbParticipants];
            lanes = new LaneEntityModel[nbParticipants];
        } else { // 5 poneys by default
            participants = new PoneyModel[5];
            lanes = new LaneEntityModel[5];
        }

        if (soloGame) {
            chooseRandomFileAndGenerateObstacles();
            // Initializing participants and their specific lanes except for the first one.
            for (int i = 1; i < participants.length; i++) {
                participants[i] = new PoneyModel(PoneyModel.getColor(i), i, true, NB_LAPS);
                participants[i].addSelfToTransforms();
                lanes[i] = new LaneEntityModel(i, participants[i]);
                for (FixedEntityModel fe : levelsBuild.getFixedEntities()) {
                    if (fe.getRow() == i) {
                        lanes[i].addFixedEntity(fe);
                    }
                }
            }
        }
    }


    /**
     * Hide all obstacles when the race is finished.
     */
    void finishRace() {
        for(LaneEntityModel l: lanes) {
            l.finishRace();
        }
    }
    
    /**
     * FieldModel constructor.
     *
     * @param nbParticipants the number of participants in the game
     */
    public FieldModel(final int nbParticipants) {
        chooseRandomFileAndGenerateObstacles();
        // If the number of participants is acceptable
        if (2 <= nbParticipants && nbParticipants <= 5) {
            participants = new PoneyModel[nbParticipants];
            lanes = new LaneEntityModel[nbParticipants];
        } else { // 5 poneys by default
            participants = new PoneyModel[5];
            lanes = new LaneEntityModel[5];
        }

        // Initializing participants and their specific lanes
        for (int i = 0; i < participants.length; i++) {
            participants[i] = new PoneyModel(PoneyModel.getColor(i), i, isAi[i], NB_LAPS);
            participants[i].addSelfToTransforms();
            lanes[i] = new LaneEntityModel(i, participants[i]);
            for (FixedEntityModel fe : levelsBuild.getFixedEntities()) {
                if (fe.getRow() == i) {
                    lanes[i].addFixedEntity(fe);
                }
            }
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

    /*
    private LaneEntityModel[] lanes;
    private MovingEntityModel[] participants;
    private static final int NB_LAPS = 5;
    private transient int participantsFinished = 0;

    public static final CollisionManager COLLISIONMANAGER = new CollisionManager();
    // State of the poneys. True : AI, False : Human
    private static final boolean[] isAi = new boolean[] {true, true, true, false, false};

    private transient ArrayList<MovingEntityModel> rankings;

    private transient LevelBuilder levelsBuild = new LevelBuilder();
    */

    public void assign(FieldModel other) {
        lanes = other.lanes;
        for (int i = 0; i < other.participants.length; i++) {
            participants[i].assign(other.participants[i]);
        }
        participantsFinished = other.participantsFinished;
    }


    /**
     * Set a new participant for the race.
     * @param entityType string
     * @param color string
     * @param indice int
     */
    public void setParticipant(String entityType, String color, int indice) {
        if (indice < 0 || indice >= 5) {
            indice = 0;
        }
        switch (entityType) {
            case "pony" :
                participants[indice] = new PoneyModel(color, indice, false, NB_LAPS);
                participants[indice].addSelfToTransforms();
                lanes[indice] = new LaneEntityModel(indice, participants[indice]);
                for (FixedEntityModel fe : levelsBuild.getFixedEntities()) {
                    if (fe.getRow() == indice) {
                        lanes[indice].addFixedEntity(fe);
                    }
                }
                break;
            case "ponyClone" :
                //TODO mettre PonyClone au lieu de PoneyModel
                participants[indice] = new PoneyModel(color, indice, false, NB_LAPS);
                participants[indice].addSelfToTransforms();
                lanes[indice] = new LaneEntityModel(indice, participants[indice]);
                for (FixedEntityModel fe : levelsBuild.getFixedEntities()) {
                    if (fe.getRow() == indice) {
                        lanes[indice].addFixedEntity(fe);
                    }
                }
                break;
            //Cas par defaut si le type n'existe pas
            default :
                participants[indice] = new PoneyModel(color, indice, false, NB_LAPS);
                participants[indice].addSelfToTransforms();
                lanes[indice] = new LaneEntityModel(indice, participants[indice]);
                for (FixedEntityModel fe : levelsBuild.getFixedEntities()) {
                    if (fe.getRow() == indice) {
                        lanes[indice].addFixedEntity(fe);
                    }
                }
                break;
        }

    }

    /**
     * Set the neighbor for each entity.
     */
    public void setNeighbor() {
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
     * Update the model and its components.
     *
     * @param msElapsed time elapsed in ms
     */
    public void update(final double msElapsed) {
        for (int i = 0; i < participants.length;i++) {
            participants[i].update(msElapsed);
            lanes[i].update(msElapsed,participants[i].getNbLap());
        }

        rankParticipants();
        checkRaceFinished();
    }

    /** Return the lanes.
     */
    public LaneEntityModel[] getLanes() {
        return lanes;
    }

    /**
     * Mutateur lanes.
     * @param newLanes tableau de LaneEntityModel
     */
    public void setLanes(LaneEntityModel[] newLanes) {
        lanes = newLanes;
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
     * Mutateur participant.
     * @param newParticipant tableau de MovingEntityModel
     */
    public void setParticipantModels(MovingEntityModel[] newParticipant) {
        participants = newParticipant;
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

    /**
     * Lis le contenu d'un fichier level (choisis au hasard).
     */
    public void chooseRandomFileAndGenerateObstacles() {
        File file = levelsBuild.chooseRandomLevelFile();
        levelsBuild.readFile(file);
    }

}
