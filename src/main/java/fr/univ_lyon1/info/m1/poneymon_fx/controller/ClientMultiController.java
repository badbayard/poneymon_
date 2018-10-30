package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;

import java.io.IOException;
import java.net.Socket;

public class ClientMultiController extends ClientController implements Runnable {
    private Socket socket = null;
    private CommunicationSystem messagingSystem;

    /**
     * Constructeur du controller multi côté client avec infos de connexion.
     *
     * @param host adresse IP de l'hôte
     * @param port port auquel se connecter sur l'hôte
     */
    public ClientMultiController(String host, int port) {
        super();

        try {
            socket = new Socket(host, port);
            messagingSystem = new CommunicationSystem(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
//        System.out.println("C'est parti pour les commandes");
//
//        Command cmd1 = new Command();
//        StringCommand cmd2 = new StringCommand("Lol");
//
//        messagingSystem.sendCommand(cmd1);
//        System.out.println("Client : J'ai Envoyé !");
//        System.out.println("Client : J'attends");
//        Command rep1 = messagingSystem.receiveCommand();
//        System.out.println("Client : J'ai Reçu !");
//        rep1.affichage();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        messagingSystem.sendCommand(cmd2);
//        System.out.println("Client : J'ai Envoyé !");
//        System.out.println("Client : J'attends");
//        Command rep2 = messagingSystem.receiveCommand();
//
//        System.out.println("Client : J'ai Reçu !");
//        rep2.affichage();
//
//
//        System.err.println("C'est finis pour les commandes");
//        messagingSystem.close();
    }

    /**
     * Override inherited method since in the case of a client in a multiplayer game, the controller
     * doesn't update the fieldModel, it only assigns the FieldModel received from the server.
     *
     * @param msElapsed number of ms since last update
     */
    @Override
    void updateFieldModel(double msElapsed, FieldModel fm) {
        // Each time the event is triggered, update the model
        fieldModel.update(msElapsed, fm);
    }

    @Override
    public void exit() {
        if (socket != null) {
            try {
                socket.close();
                System.out.println("ON FERME AUSSI LE SOCKET");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.exit();
    }
}
