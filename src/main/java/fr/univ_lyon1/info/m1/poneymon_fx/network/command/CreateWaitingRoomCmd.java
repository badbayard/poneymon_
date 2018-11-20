package fr.univ_lyon1.info.m1.poneymon_fx.network.command;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.ProcessManager;
import fr.univ_lyon1.info.m1.poneymon_fx.network.server.process.WaitingRoomProcess;

import java.util.Arrays;

public class CreateWaitingRoomCmd extends RoomCommand {

    private String name;
    private char[] password;

    public CreateWaitingRoomCmd(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean atReceive() {
        System.out.println(idPlayer + " envois : commande pour crée un partie.");
        System.out.println("On essaye créer la partie : " + name + " avec le "
            + "mot de passe : " + Arrays.toString(password));
        if (actualRoom == null) {
            System.err.println("Pas room assigné à la commande !");
        } else {
            boolean roomAlreadyExists = false;

            ListRoom lr;
            if (actualRoom instanceof ListRoom) {
                lr = (ListRoom) actualRoom;
            } else {
                return false;
            }
            for (int i = 0; i < lr.getRooms().size(); ++i) {
                if (lr.getRooms().get(i).getName().equals(name)) {
                    roomAlreadyExists = true;
                }
            }
            if (roomAlreadyExists) {
                System.err.println("Room existe déjà ! on doit renvoyer un false au client..");
            } else {
                System.out.println("La room n'exsite pas, on l'a crée");
                WaitingRoom newRoom = new WaitingRoom(password, 5, name);
                lr.getRooms().add(newRoom);
                Client client = actualRoom.getClient(idPlayer);
                newRoom.join(client);
                ProcessManager.getProcessManager().createAndRunThread(
                    new WaitingRoomProcess(client, newRoom));
                notifyOtherPlayers(newRoom.getClients(),
                    new NotifyPlayerChangeCmd(newRoom.getNbPlayers()));
                return true;
            }
        }

        return false;
    }
}
