package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;

public class UpdateGameCmd extends GameCommand {

    public UpdateGameCmd(FieldModel fieldModel) {
        this.gameRoom.getServerMultiController().setFieldModel(fieldModel);
    }

    public FieldModel getFieldModel() {
        return gameRoom.getFieldModel();
    }

    @Override
    public boolean atReceive() {
        System.out.println("Le serveur envois le model");
        return true;
    }
}
