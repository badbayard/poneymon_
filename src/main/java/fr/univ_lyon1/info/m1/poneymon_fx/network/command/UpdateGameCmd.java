package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class UpdateGameCmd extends GameCommand {
    private FieldModel fieldModel;

    public UpdateGameCmd(FieldModel fm) {
        fieldModel = fm;
    }

    public FieldModel getFieldModel() {
        return fieldModel;
    }

    @Override
    public boolean atReceive() {
        return true;
    }
}
