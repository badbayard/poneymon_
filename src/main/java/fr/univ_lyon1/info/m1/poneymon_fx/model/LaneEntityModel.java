package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.Serializable;
import java.util.ArrayList;

public class LaneEntityModel implements Serializable {
    private int row;
    private MovingEntityModel boundParticipant;
    private ArrayList<FixedEntityModel> fixedEntities;

    public ArrayList<FixedEntityModel> getFixedEntities() {
        return fixedEntities;
    }

    /**
     * Constructor of the lane Entity.
     * @param boundParticipant the participant on the lane.
     */
    public LaneEntityModel(int row, MovingEntityModel boundParticipant) {
        super();
        this.row = row;
        this.boundParticipant = boundParticipant;
        fixedEntities = new ArrayList<FixedEntityModel>();
    }
    
    /**
     * Constructor of the lane Entity.
     * @param boundParticipant the participant on the lane.
     */
    public LaneEntityModel(int row, MovingEntityModel boundParticipant,
            ArrayList<FixedEntityModel> fixedEntities) {
        super();
        this.row = row;
        this.boundParticipant = boundParticipant;
        this.fixedEntities = fixedEntities;
    }

    public void addFixedEntity(FixedEntityModel fe) {
        fixedEntities.add(fe);
        fe.addSelfToTransforms();
    }

    public void removeFixedEntity(FixedEntityModel fe) {
        fixedEntities.remove(fe);
    }

    /**
     * Update all.
     * 
     */
    void update(double msElapsed, int currentLap) {
        for (int i = 0; i < fixedEntities.size(); i++) {
            fixedEntities.get(i).update(msElapsed, currentLap);
        }
    }


}
