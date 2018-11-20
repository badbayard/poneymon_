package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.CreateWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.LeaveWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.SelectPoneyCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.ButtonMenu;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.MenuView;

import java.io.IOException;
import java.net.Socket;

public class ClientMultiController extends ClientController implements Runnable {
    private Socket socket = null;
    private CommunicationSystem messagingSystem;
    // Donnez par le serveur nécéssaire dans chaque communication
    private int idClient;

    /**
     * Constructeur du controller multi côté client.
     */
    public ClientMultiController() {
        super();
    }

    /**
     * Initialise la connexion au serveur.
     *
     * @param host adresse IP de l'hôte
     * @param port port auquel se connecter sur l'hôte
     */
    public boolean initNetwork(String host, int port) {
        try {
            socket = new Socket(host, port);
            messagingSystem = new CommunicationSystem(socket, 0);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void setEvents(MenuView menuView) {
        if (eventsSet) {
            return;
        }

        super.setEvents(menuView);

        //********** EVENT LIST ROOM **********//

        // Back to main menu from list
        ButtonMenu btnBackList = menuView.getListroom().getBtnBack();
        btnBackList.setOnMouseClicked(event -> menuView.backToMainMenu());

        menuView.getListroom().getBtnHost().setOnMouseClicked(event -> {
            char[] pswTest = {'n', 'o', 'n'};
            CreateWaitingRoomCmd cwrc = new CreateWaitingRoomCmd("truc", pswTest);
            messagingSystem.sendCommand(cwrc);

            menuView.activateWaitingRoom();
            menuView.getWaitingRoomView().hasJoinRoom();
        });

        //********** EVENT WAITING ROOM **********//

        // Back to ListRoomView from WaitingRoomView
        menuView.getWaitingRoomView().getBtnBack().setOnMouseClicked(event -> {
            LeaveWaitingRoomCmd lwr = new LeaveWaitingRoomCmd();
            messagingSystem.sendCommand(lwr);

            menuView.backToListRoom();
            menuView.getWaitingRoomView().hasLeftRoom();
        });

        eventsSet = true;
    }

    /**
     * Signals the server of the player's entity choice.
     * @param entityType String, type of entity
     * @param color String, color of entity
     */
    public void selectEntity(String entityType, String color) {
        SelectPoneyCmd spc = new SelectPoneyCmd(entityType, color);

        messagingSystem.sendCommand(spc);
    }

    @Override
    public void run() {
        Command cmd = messagingSystem.receiveCommand();
        idClient = cmd.getIdPlayer();
        messagingSystem.setIdClient(idClient);
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
