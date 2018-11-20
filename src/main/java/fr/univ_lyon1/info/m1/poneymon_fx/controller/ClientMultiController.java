package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.*;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.MenuView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientMultiController extends ClientController {
    private Socket socketEvt = null;
    private Socket socketCnt = null;
    private CommunicationSystem messagingSystemEvt;
    private CommunicationSystem messagingSystemCnt;
    // Donnez par le serveur nécéssaire dans chaque communication
    private int idClient;
    MenuView menuView;
    Thread listenerThread;
    ControllerListener listener;
    boolean init = false;

    /**
     * Constructeur du controller multi côté client.
     */
    public ClientMultiController() {
        super();
    }

    /**
     * Initialise la connexion au serveur.
     *
     * @param host        adresse IP de l'hôte
     * @param portEvents  port auquel se connecter sur l'hôte pour les événements
     * @param portContinu port auquel se connecter sur l'hôte pour les infos continus
     */
    public boolean initNetwork(String host, int portEvents, int portContinu) {
        if (init) {
            return true;
        }
        try {
            socketEvt = new Socket(host, portEvents);
            socketCnt = new Socket(host, portContinu);

            messagingSystemEvt = new CommunicationSystem(socketEvt, 0);
            messagingSystemCnt = new CommunicationSystem(socketCnt, 0);

            listener = new ControllerListener(this);
            listenerThread = new Thread(listener);
            listenerThread.start();

            Command cmd = messagingSystemEvt.receiveCommand();

            idClient = cmd.getIdPlayer();
            messagingSystemEvt.setIdClient(idClient);
            messagingSystemCnt.setIdClient(idClient);

            init = true;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    void step(long currentNanoTime) {
        // Prevent from resuming the game when the race is over
        if (gameOver) {
            return;
        }

        // Time elapsed since the last update
        double msElapsed = (currentNanoTime - lastTimerUpdate) / 1e6;
        // update the last timer update
        lastTimerUpdate = currentNanoTime;

        updateFieldModel(msElapsed, fieldModel);

        // Check for collisions
        FieldModel.COLLISIONMANAGER.checkCollision();
    }

    @Override
    public void setEvents(MenuView menuView) {
        if (eventsSet) {
            return;
        }
        super.setEvents(menuView);
        this.menuView = menuView;

        //********** EVENT LIST ROOM **********//

        // Back to main menu from list
        menuView.getListroom().getBtnBack().setOnMouseClicked(event -> menuView.backToMainMenu());

        menuView.getListroom().getBtnHost().setOnMouseClicked(event -> {
            char[] pswTest = {'n', 'o', 'n'};
            CreateWaitingRoomCmd cwrc = new CreateWaitingRoomCmd("truc", pswTest);
            messagingSystemEvt.sendCommand(cwrc);

            StringCommand sc = (StringCommand) messagingSystemEvt.receiveCommand();

            if (sc != null) {
                menuView.activateWaitingRoom();
                menuView.getWaitingRoomView().setNbPlayerInRoom(1);
            }
        });


        menuView.getListroom().getBtnRefresh().setOnMouseClicked(event -> {
            messagingSystemEvt.sendCommand(new AskForWaitingRoomCmd());
            ShowWaitingRoomCmd swrc = (ShowWaitingRoomCmd) messagingSystemEvt.receiveCommand();
            StringCommand sc = (StringCommand) messagingSystemEvt.receiveCommand();

            if (!sc.getMot().equals("OK") || swrc != null) {
                List<WaitingRoom> waitingRooms = swrc.getRooms();
                List<HBox> containers = menuView.getListroom().setWaitingRooms(waitingRooms);

                for (HBox container : containers) {
                    String roomName = ((Text) container.getChildren().get(0)).getText();
                    Button joinBtn = (Button) container.getChildren().get(2);

                    joinBtn.setOnMouseClicked(e -> {
                        char[] pswd =
                            ((TextField) container.getChildren().get(1)).getText().toCharArray();
                        messagingSystemEvt.sendCommand(new JoinWaitingRoomCmd(roomName, pswd));

                        StringCommand reponse = (StringCommand) messagingSystemEvt.receiveCommand();

                        if (reponse != null) {
                            menuView.activateWaitingRoom();
                            menuView.getWaitingRoomView().hasJoinRoom();
                        }
                    });
                }
            }
        });

        //********** EVENT WAITING ROOM **********//

        // Back to ListRoomView from WaitingRoomView
        menuView.getWaitingRoomView().getBtnBack().setOnMouseClicked(event -> {
            LeaveWaitingRoomCmd lwr = new LeaveWaitingRoomCmd();
            messagingSystemEvt.sendCommand(lwr);

            menuView.backToListRoom();
        });

        eventsSet = true;
    }

    Command receiveCommandCnt() {
        return messagingSystemCnt.receiveCommand();
    }

    void sendCommandCnt(Command cmd) {
        messagingSystemCnt.sendCommand(cmd);
    }

    /**
     * Signals the server of the player's entity choice.
     *
     * @param entityType String, type of entity
     * @param color      String, color of entity
     */
    public void selectEntity(String entityType, String color) {
        SelectPoneyCmd spc = new SelectPoneyCmd(entityType, color);

        messagingSystemEvt.sendCommand(spc);
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
        if (socketEvt != null) {
            try {
                socketEvt.close();
                System.out.println("ON FERME AUSSI LE SOCKET");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.exit();
    }

    void changeNbPlayers(int nbPlayers) {
        menuView.getWaitingRoomView().setNbPlayerInRoom(nbPlayers);
    }
}
