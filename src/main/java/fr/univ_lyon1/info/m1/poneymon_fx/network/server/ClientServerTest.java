package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.AskForWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.CreateWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.InGameCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.JoinWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.SelectPoney;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.ShowWaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientServerTest {
    private Socket socket;
    private CommunicationSystem messagingSystem;
    private int idClient;
    private boolean isInGameRoom = false;

    /**
     * Initialize the client.
     */
    public ClientServerTest() {
        try {
            socket = new Socket("127.0.0.1", 4242);
            messagingSystem = new CommunicationSystem(socket, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Execute un petit client textuel pour un serveur local.
     *
     * @param args parameter for the main program
     */
    public static void main(String[] args) {
        ClientServerTest client = new ClientServerTest();
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        boolean knownCmd = false;
        Command cmd = client.messagingSystem.receiveCommand();
        client.setIdClient(cmd.getIdPlayer());
        cmd.atReceive();
        while (!exit) {
            System.out.println("Choisissez votre commande : ");
            System.out.println("- \"txt\" = StringCommand");
            System.out.println("- \"join\" = join a room");
            System.out.println("- \"create\" = create a room");
            System.out.println("- \"show\" = show all rooms");
            System.out.println("- \"select\" = select a poney");
            System.out.println("- \"quit\" = exit server");
            System.out.print("> ");
            String rep = sc.nextLine();
            if (rep.equals("quit")) {
                exit = true;
                try {
                    client.socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                client.messagingSystem.close();
            } else if (rep.equals("txt")) {
                System.out.println("Que voulez vous dire au serveur ?");
                String msg = sc.nextLine();
                cmd = new StringCommand(msg);
                client.messagingSystem.sendCommand(cmd);
                (client.messagingSystem.receiveCommand()).atReceive();
            } else if (rep.equals("join")) {
                System.out.println("Nom de la salle à rejoindre ?");
                String name = sc.nextLine();
                System.out.println("Mot de passe de la salle à rejoindre ?");
                String password = sc.nextLine();
                cmd = new JoinWaitingRoom(name, password);
                client.messagingSystem.sendCommand(cmd);
                (client.messagingSystem.receiveCommand()).atReceive();
            } else if (rep.equals("create")) {
                System.out.println("Nom de la salle à creer ?");
                String name = sc.nextLine();
                System.out.println("Mot de passe de la salle à creer ?");
                String password = sc.nextLine();
                cmd = new CreateWaitingRoom(name, password);
                client.messagingSystem.sendCommand(cmd);
                (client.messagingSystem.receiveCommand()).atReceive();
            } else if (rep.equals("show")) {
                cmd = new AskForWaitingRoom();
                client.messagingSystem.sendCommand(cmd);
                ShowWaitingRoom cmd2 =
                        (ShowWaitingRoom) client.messagingSystem
                                .receiveCommand();
                cmd2.atReceive();
                List<WaitingRoom> rooms = cmd2.getRooms();
                for (int i = 0; i < rooms.size(); ++i) {
                    System.out.println(rooms.get(i).getName());
                }
                (client.messagingSystem.receiveCommand()).atReceive();
            } else if (rep.equals("select")) {
                System.out.println("Choose Color :");
                String color = sc.nextLine();
                cmd = new SelectPoney("pony", color);
                client.messagingSystem.sendCommand(cmd);
                InGameCommand cmd2 =
                        (InGameCommand) client.messagingSystem.receiveCommand();
                FieldModel field = cmd2.getFieldModel();
                for (int i = 0; i < field.getParticipantModels().length; ++i) {
                    System.out.println(field.getParticipantModel(i).getColor());
                }
                (client.messagingSystem.receiveCommand()).atReceive();
            } else {
                System.out.println("Commande inconnue");
            }
        }
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
        messagingSystem.setIdClient(idClient);
    }
}
