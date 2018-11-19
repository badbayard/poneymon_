package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

public class SelectPoney extends InGameCommand {

    String entityType;
    String color;

    public SelectPoney(String entityType, String color) {
        this.entityType = entityType;
        this.color = color;
    }

    @Override
    public void atReceive() {
        System.out.println(idPlayer + " : envois selection de poney");

        if (actualRoom == null) {
            System.err.println("Pas de room pour selectionner le "
                    + "poney");
        } else {
            int idInRoom = actualRoom.getIndexClient(idPlayer);
            actualRoom.getFieldModel()
                    .setParticipant(entityType, color, idInRoom);
            InGameCommand cmd = new InGameCommand();
            cmd.setActualRoom(actualRoom);
            actualRoom.getClient(idPlayer).sendCommand(cmd);
        }
    }
}
