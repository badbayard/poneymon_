package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.JfxView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MenuView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.ButtonMenu;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.SoundController;

/**
 * Main application class. Needs JavaFx.
 */
public class App extends Application {
    private MenuView menu;
    private Stage stage;
    private SoundController m;

    /**
     * Start() launch the application.
     *
     * @param s the application stage
     * @see <a href=
     *      "http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">
     *      jfxpub-scenegraph.htm</a>
     */
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        m = new SoundController();

        // Secondary view
        /*
         * Stage s3 = new Stage(); JfxView v2 = new JfxView(s3, 1000, 600);
         * c.addView(v2); v2.setModel(m); v2.setController(c);
         */

        menu = new MenuView(800, 600);

        stage.setScene(menu.getScene());
        stage.show();
        m.playchunk();

        setEvents();
    }

    /**
     * Set the events for each buttons of the menus.
     */
    private void setEvents() {
        
        //********** EVENT MAIN MENU **********//
        
        // Event Play solo
        ButtonMenu btnPlay = menu.getMainMenu().getBtnPlay();
        btnPlay.setOnMouseClicked(event -> openSelectMenuSolo());

        // Event Play multi
        ButtonMenu btnPlayMulti = menu.getMainMenu().getBtnPlayMulti();
        btnPlayMulti.setOnMouseClicked(event -> openListRoom());

        // Event exit game
        ButtonMenu btnExit = menu.getMainMenu().getBtnExit();
        btnExit.setOnMouseClicked(event -> Platform.exit());
        
        //********** EVENT SELECT MENU **********//

        // Event confirm selected poney
        ButtonMenu btnConfirm = menu.getSelectMenu().getBtnConfirm();
        btnConfirm.setOnMouseClicked(event -> soloOrMulti());

        // Event back to main menu
        ButtonMenu btnBack = menu.getSelectMenu().getBtnBack();
        btnBack.setOnMouseClicked(event -> backToMain());
        
        //********** EVENT LIST ROOM **********//
        
        // Event back to main menu from list
        ButtonMenu btnBackList = menu.getListroom().getBtnBack();
        btnBackList.setOnMouseClicked(event -> backToMain());
        
        ButtonMenu btnHost = menu.getListroom().getBtnHost();
        btnHost.setOnMouseClicked(event -> hostGame());
        
        //********** EVENT WAITING ROOM **********//

        // Event back to ListRoom from WaitingRoom
        ButtonMenu btnBackToList = menu.getWaitingRoom().getBtnBack();
        btnBackToList.setOnMouseClicked(event -> backToList());
    }
    
    /**
     * Create a salon for multiplayer.
     */
    private void hostGame() {
        menu.activateWaitingRoom();
        menu.getWaitingRoom().asJoinRoom();
    }

    /**
     * Launch a solo game or send data to the server.
     */
    private void soloOrMulti() {
        if (menu.getIsSolo()) {
            createGameSolo();
        } else {
            // TODO send data to the serv
        }
    }

    /**
     * Hide the current menu and display the main menu.
     */
    private void backToMain() {
        menu.backToMainMenu();
    }

    /**
     * Hide the WaitingRoom and display the ListRoom.
     */
    private void backToList() {
        menu.getWaitingRoom().asLeftRoom();
        menu.backToListRoom();
    }
    
    /**
     * Display the SelectEntity menu.
     */
    private void openSelectMenuSolo() {
        menu.setIsSolo(true);
        menu.activateSelectMenu();
    }

    /**
     * Display the Listroom.
     */
    private void openListRoom() {
        menu.setIsSolo(false);
        menu.activateSelectlistroom();
    }

    /**
     * Create a one-player game.
     */
    private void createGameSolo() {
        // Second window (stats)
        Stage s2 = new Stage();
        s2.setX(stage.getX() + stage.getWidth());
        s2.setY(stage.getY());

        DataView dataView = new DataView(s2, 210, 180);
        Controller.CONTROLLER.setDataView(dataView);

        // Creates five poneys in the game field
        FieldModel fieldModel = new FieldModel(5, true);
        
        fieldModel.setParticipant(menu.getSelectMenu().getType(),
                menu.getSelectMenu().getColor(), 0);
        fieldModel.setNeighbor();

        // Set a default poney model to the data view
        dataView.setParticipantModel((PoneyModel) fieldModel.getParticipantModel(0));

        Controller.CONTROLLER.setFieldModel(fieldModel);

        // Creates a window 1200x800 px
        JfxView jfxView = new JfxView(stage, 800, 600);
        // Trigger the waterfall initialization
        jfxView.setModel(fieldModel);

        // Launch the game
        Controller.CONTROLLER.startTimer();

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
