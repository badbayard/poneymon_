package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class UpdateGameCmd extends Command {
    private FieldModel fieldModel;

    public UpdateGameCmd(FieldModel fieldModel) {
        this.fieldModel = fieldModel;
    }

    public FieldModel getFieldModel() {
        return fieldModel;
    }

    @Override
    public boolean atReceive() {
        System.out.println("Le serveur envois le model");
        return true;
    }
}
