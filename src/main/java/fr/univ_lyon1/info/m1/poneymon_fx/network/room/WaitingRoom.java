package fr.univ_lyon1.info.m1.poneymon_fx.network.room;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class WaitingRoom extends Room {

    private FieldModel fieldModel;

    public WaitingRoom(char[] password, int nbPlayerMax, String name) {
        super(password, nbPlayerMax, name);
        fieldModel = new FieldModel(5);
    }

    public FieldModel getFieldModel() {
        return fieldModel;
    }
}
