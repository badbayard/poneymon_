package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.client.Client;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.RoomCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

public class ProcessListRoom extends Process {
    private ListRoom listRoom;

    public ProcessListRoom(ListRoom lr, Client client) {
        listRoom = lr;
        this.client = client;
    }

    @Override
    public void run() {
        while (isRunning) {
            RoomCommand cmd = (RoomCommand) client.receiveCommand();
            cmd.setActualRoom(listRoom);
            cmd.atReceive();

            if (cmd == null) {
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
