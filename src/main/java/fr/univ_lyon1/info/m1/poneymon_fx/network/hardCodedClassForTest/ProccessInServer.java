package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communicationSystem.CommunicationSystem;

import java.net.Socket;

public class ProccessInServer implements Runnable {

    private Socket sock;
    private CommunicationSystem messagingSystem;
    private int[][] rooms;
    private int nbRooms = 0;
    private int roomSize = 4;

    public ProccessInServer(Socket sock) {
        this.sock = sock;
    }

    /**
     * Lance le processus.
     */
    public void run() {
        System.err.println("Lancement du traitement de la connexion cliente");

        /*
        boolean closeConnexion = false;
        while (!sock.isClosed()) {
            try {
                messagingSystem = new CommunicationSystem(sock);

                String response = messagingSystem.receiveMessage();
                InetSocketAddress remote =
                        (InetSocketAddress) sock.getRemoteSocketAddress();

                String command = "Command reçue : " + response;
                System.err.println(command);

                // Traiement de la réponse
                String toSend = "";
                switch (response.toUpperCase()) {
                    case "join":
                        break;
                    case "FULL":
                        toSend = DateFormat.getDateTimeInstance(DateFormat.FULL,
                                DateFormat.MEDIUM).format(new Date());
                        break;
                    case "DATE":
                        toSend = DateFormat.getDateInstance(DateFormat.FULL)
                                .format(new Date());
                        break;
                    case "HOUR":
                        toSend = DateFormat.getTimeInstance(DateFormat.MEDIUM)
                                .format(new Date());
                        break;
                    case "CLOSE":
                        toSend = "Communication terminée";
                        closeConnexion = true;
                        break;
                    default:
                        toSend = "Command inconnu !";
                        break;
                }

                messagingSystem.sendMessage(toSend);

                if (closeConnexion) {
                    System.err.println("Command de fermeture ! ");
                    messagingSystem.close();
                    sock.close();
                    break;
                }
            } catch (SocketException e) {
                System.err.println("Connexion interrompue ");
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */


        messagingSystem = new CommunicationSystem(sock);
        System.out.println("Serveur : J'attends");
        Command cmd = messagingSystem.receiveCommand();
        System.out.println("Serveur : J'ai reçu !");
        messagingSystem.sendCommand(cmd);
        System.out.println("Serveur : J'ai Envoyé !");

        System.out.println("Serveur : J'attends");
        Command cmd2 = messagingSystem.receiveCommand();
        System.out.println("Serveur : J'ai reçu !");
        messagingSystem.sendCommand(cmd2);
        System.out.println("Serveur : J'ai Envoyé !");


    }

}
