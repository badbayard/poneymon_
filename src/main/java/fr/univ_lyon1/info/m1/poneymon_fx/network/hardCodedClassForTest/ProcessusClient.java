package fr.univ_lyon1.info.m1.poneymon_fx.network.hardCodedClassForTest;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class ProcessusClient implements Runnable {

    private Socket connexion = null;
    private String[] listCommands = {"FULL", "DATE", "HOUR", "NONE"};
    private CommunicationSystem messagingSystem;

    /**
     * Constructeur de ProcessusClient avec paramètres.
     *
     * @param host adresse IP de l'hôte
     * @param port port auquel se connecter sur l'hôte
     */
    public ProcessusClient(String host, int port) {
        try {
            connexion = new Socket(host, port);
            messagingSystem = new CommunicationSystem(connexion);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Envois des commandes random pour tester le bon fonctionnement du serveur.
     */
    public void run() {
        /*
        for (int i = 0; i < 10; i++) {
            try {
                // On envois toute les secondes un message
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                //On envoie la commande au serveur

                String commande = sendRandomCommand();
                messagingSystem.sendMessage(commande);

                System.out.println(
                    "Command " + commande + " envoyée au serveur");

                String response = messagingSystem.receiveMessage();
                System.out.println("Réponse reçue " + response);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            String join = "join";
            messagingSystem.sendMessage(join);

            System.out.println("Command join envoyée au serveur");

            String rep = messagingSystem.receiveMessage();

            System.out.println("Réponse " + rep + " reçue");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messagingSystem.sendMessage("CLOSE");
        messagingSystem.close();
        */


        System.out.println("C'est partis pour les commandes");

        Command cmd1 = new Command();
        StringCommand cmd2 = new StringCommand("Lol");

        messagingSystem.sendCommand(cmd1);
        System.out.println("Client : J'ai Envoyé !");
        System.out.println("Client : J'attends");
        Command rep1 = messagingSystem.receiveCommand();
        System.out.println("Client : J'ai Reçu !");
        rep1.affichage();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messagingSystem.sendCommand(cmd2);
        System.out.println("Client : J'ai Envoyé !");
        System.out.println("Client : J'attends");
        Command rep2 = messagingSystem.receiveCommand();
        System.out.println("Client : J'ai Reçu !");
        rep2.affichage();


        System.err.println("C'est finis pour les commandes");
        messagingSystem.close();

    }


    private String sendRandomCommand() {
        Random rand = new Random();
        return listCommands[rand.nextInt(listCommands.length)];
    }
}
