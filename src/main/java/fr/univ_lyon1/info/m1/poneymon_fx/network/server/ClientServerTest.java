package fr.univ_lyon1.info.m1.poneymon_fx.network.server;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.AskForWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.CreateWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.JoinWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.LeaveWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.SelectPoneyCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.ShowWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.WaitingRoomCommand;
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
            System.out.println("- \"back\" = return to list room");
            System.out.println("- \"quit\" = exit server");
            System.out.print("> ");
            String rep = sc.nextLine();
            switch (rep) {
                case "quit":
                    exit = true;
                    try {
                        client.socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    client.messagingSystem.close();
                    break;
                case "txt":
                    System.out.println("Que voulez vous dire au serveur ?");
                    String msg = sc.nextLine();
                    cmd = new StringCommand(msg);
                    client.messagingSystem.sendCommand(cmd);
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                case "join": {
                    System.out.println("Nom de la salle à rejoindre ?");
                    String name = sc.nextLine();
                    System.out.println(
                            "Mot de passe de la salle à rejoindre ?");
                    String password = sc.nextLine();
                    char[] pswArr = password.toCharArray();
                    cmd = new JoinWaitingRoomCmd(name, pswArr);
                    client.messagingSystem.sendCommand(cmd);
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                }
                case "create": {
                    System.out.println("Nom de la salle à creer ?");
                    String name = sc.nextLine();
                    System.out
                            .println("Mot de passe de la salle à creer ?");
                    String password = sc.nextLine();
                    char[] pswArr = password.toCharArray();
                    cmd = new CreateWaitingRoomCmd(name, pswArr);
                    client.messagingSystem.sendCommand(cmd);
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                }
                case "show": {
                    cmd = new AskForWaitingRoomCmd();
                    client.messagingSystem.sendCommand(cmd);
                    ShowWaitingRoomCmd cmd2 =
                            (ShowWaitingRoomCmd) client.messagingSystem
                                    .receiveCommand();
                    cmd2.atReceive();
                    List<WaitingRoom> rooms = cmd2.getRooms();
                    for (int i = 0; i < rooms.size(); ++i) {
                        System.out.println(rooms.get(i).getName());
                    }
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                }
                case "select": {
                    System.out.println("Choose Color :");
                    String color = sc.nextLine();
                    cmd = new SelectPoneyCmd("pony", color);
                    client.messagingSystem.sendCommand(cmd);
                    WaitingRoomCommand cmd2 =
                            (WaitingRoomCommand) client.messagingSystem
                                    .receiveCommand();
                    FieldModel field = cmd2.getFieldModel();
                    for (int i = 0; i < field.getParticipantModels().length;
                         ++i) {
                        System.out.println(
                                field.getParticipantModel(i).getColor());
                    }
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                }
                case "back": {
                    cmd = new LeaveWaitingRoomCmd();
                    client.messagingSystem.sendCommand(cmd);
                    (client.messagingSystem.receiveCommand()).atReceive();
                    break;
                }
                default:
                    System.out.println("Commande inconnue");
                    break;
            }
        }
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
        messagingSystem.setIdClient(idClient);
    }
}
