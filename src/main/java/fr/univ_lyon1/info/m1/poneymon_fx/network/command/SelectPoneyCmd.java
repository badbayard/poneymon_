package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class SelectPoneyCmd extends WaitingRoomCommand {

    String entityType;
    String color;

    public SelectPoneyCmd(String entityType, String color) {
        this.entityType = entityType;
        this.color = color;
    }

    @Override
    public boolean atReceive() {
        if (actualRoom == null) {
            System.err.println("Pas de room pour selectionner le poney");
        } else {
            int idInRoom = actualRoom.getIndexClient(idPlayer);
            actualRoom.getFieldModel().setParticipant(entityType, color, idInRoom);
            return true;
        }

        return false;
    }
}
