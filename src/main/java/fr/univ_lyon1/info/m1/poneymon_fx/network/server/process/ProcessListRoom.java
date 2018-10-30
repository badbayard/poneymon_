package fr.univ_lyon1.info.m1.poneymon_fx.network.server.process;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.ListRoom;

import java.net.Socket;

public class ProcessListRoom extends Process {
    private ListRoom listRoom;
    private CommunicationSystem messagingSystem;
    private Socket socket;

    public ProcessListRoom(Socket sock, ListRoom lr) {
        socket = sock;
        listRoom = lr;
        messagingSystem = new CommunicationSystem(socket);
    }

    @Override
    public void run() {
        while (isRunning) {
            Command cmd = messagingSystem.receiveCommand();

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
