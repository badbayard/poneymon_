package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.RoomCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

public class ListRoomProcess extends Process {
   private ListRoom listRoom;

    /**
     * Creates a process associated with a specific client to the ListRoom.
     *
     * @param client the client the process is associated to
     */
    public ListRoomProcess(Client client) {
        this.client = client;
        listRoom = ListRoom.getInstance();
    }

    @Override
    public void run() {
        while (isRunning) {
            RoomCommand cmd = (RoomCommand) client.receiveCommand();
            cmd.setActualRoom(listRoom);
            cmd.atReceive();

            /*
             * ReceiveCommand returns null if an IOException is thrown (ie. something went wrong
             * network-wise).
             */
            if (cmd == null) {
                System.out.println("Client disconnected");
                listRoom.removeClient(client.getPlayerId());
                close();
            }
        }

//        Command cmd = new
//        messagingSystem = new CommunicationSystem(socket);
//        System.out.println("Serveur : J'attends");
//        Command cmd = messagingSystem.receiveCommand();
//        System.out.println("Serveur : J'ai reçu !");
//        messagingSystem.sendCommand(cmd);
//        System.out.println("Serveur : J'ai Envoyé !");
//
//        System.out.println("Serveur : J'attends");
//        Command cmd2 = messagingSystem.receiveCommand();
//        System.out.println("Serveur : J'ai reçu !");
//        messagingSystem.sendCommand(cmd2);
//        System.out.println("Serveur : J'ai Envoyé !");
    }
}
