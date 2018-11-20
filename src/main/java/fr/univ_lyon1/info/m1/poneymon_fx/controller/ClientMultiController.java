package fr.univ_lyon1.info.m1.poneymon_fx.controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.AskForWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.Command;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.CreateWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.JoinWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.LeaveWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.SelectPoneyCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.ShowWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.command.StringCommand;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.ListRoomView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.MenuView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
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
            listener.setRunning(false);
            return;
        }

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
        ListRoomView lrw = menuView.getListroom();

        // Back to main menu from list
        lrw.getBtnBack().setOnMouseClicked(event -> menuView.backToMainMenu());

        // Create a game
        lrw.getBtnHost().setOnMouseClicked(event -> {
            String roomName = lrw.getNameFieldValue();
            if (roomName == null || roomName.isEmpty()) {
                return;
            }

            char[] roomPswd = lrw.getPswdFieldValue();
            if (roomPswd == null || roomPswd.length == 0) {
                return;
            }

            CreateWaitingRoomCmd cwrc = new CreateWaitingRoomCmd(roomName, roomPswd);
            messagingSystemEvt.sendCommand(cwrc);

            StringCommand sc = (StringCommand) messagingSystemEvt.receiveCommand();

            if (sc != null && sc.getMot().equals("OK")) {
                menuView.activateWaitingRoom();
                menuView.getWaitingRoomView().setNbPlayerInRoom(1);
            }
        });

        // Refresh available games
        lrw.getBtnRefresh().setOnMouseClicked(event -> {
            messagingSystemEvt.sendCommand(new AskForWaitingRoomCmd());
            ShowWaitingRoomCmd swrc = (ShowWaitingRoomCmd) messagingSystemEvt.receiveCommand();
            StringCommand sc = (StringCommand) messagingSystemEvt.receiveCommand();

            if (!sc.getMot().equals("OK") || swrc != null) {
                List<WaitingRoom> waitingRooms = swrc.getRooms();
                List<HBox> containers = lrw.setWaitingRooms(waitingRooms);

                for (HBox container : containers) {
                    String roomName = ((Text) container.getChildren().get(0)).getText();
                    Button joinBtn = (Button) container.getChildren().get(2);

                    joinBtn.setOnMouseClicked(e -> {
                        char[] pswd =
                            ((TextField) container.getChildren().get(1)).getText().toCharArray();
                        messagingSystemEvt.sendCommand(new JoinWaitingRoomCmd(roomName, pswd));

                        StringCommand reponse = (StringCommand) messagingSystemEvt.receiveCommand();

                        System.out.println(reponse.getMot());
                        if (reponse != null && reponse.getMot().equals("OK")) {
                            menuView.activateWaitingRoom();
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

            StringCommand reponse = (StringCommand) messagingSystemEvt.receiveCommand();
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

    @Override
    public void setFieldModel(FieldModel fm) {
        this.fieldModel = fm;
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
