package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientMultiController;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientSoloController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.display.JfxView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.MenuView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.display.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.SoundController;

/**
 * Main application class. Needs JavaFx.
 */
public class App extends Application {
    private MenuView menu;
    private Stage stage;
    private SoundController soundController;

    private String host = "127.0.0.1";
    private int port = 4242;

    /**
     * Start() launch the application.
     *
     * @param s the application stage
     * @see <a href=
     *     "http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">
     *     jfxpub-scenegraph.htm</a>
     */
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        soundController = new SoundController();

        // Secondary view
        /*
         * Stage s3 = new Stage(); JfxView v2 = new JfxView(s3, 1000, 600);
         * c.addView(v2); v2.setModel(soundController); v2.setController(c);
         */

        menu = new MenuView(800, 600);

        stage.setScene(menu.getScene());
        stage.show();
        // soundController.playchunk();

        setEvents();
    }

    /**
     * Set the events for each buttons of the menus.
     */
    private void setEvents() {
        //********** EVENT APPLICATION **********//
        // Close all windows and sockets
        stage.setOnCloseRequest(e -> exit());

        //********** EVENT MAIN MENU **********//

        // Play solo
        menu.getMainMenuView().getBtnPlay().setOnMouseClicked(event -> openSelectMenuSolo());

        // Play multi
        menu.getMainMenuView().getBtnPlayMulti().setOnMouseClicked(event -> {
            if (initServerConnection()) {
                openListRoom();
                Controller.getInstance().setEvents(menu);
            }
        });

        // Exit game
        menu.getMainMenuView().getBtnExit().setOnMouseClicked(event -> exit());

        //********** EVENT SELECT MENU **********//

        // Confirm selected poney
        menu.getSelectMenu().getBtnConfirm().setOnMouseClicked(event -> soloOrMulti());

        // Back to main menu
        menu.getSelectMenu().getBtnBack().setOnMouseClicked(event -> menu.backToMainMenu());
    }

    private void exit() {
        if (Controller.getInstance() != null) {
            Controller.getInstance().exit();
        } else {
            Platform.exit();
        }
    }

    /**
     * Launch a solo game or sends data to the server.
     */
    private void soloOrMulti() {
        if (menu.getIsSolo()) {
            createGameSolo();
        } else {
            ClientMultiController cmc = (ClientMultiController) Controller.getInstance();
            if (cmc != null) {
                cmc.selectEntity(menu.getSelectMenu().getType(), menu.getSelectMenu().getColor());
            }
        }
    }

    /**
     * Display the SelectEntityView menu.
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
        ClientSoloController csc =
            (ClientSoloController) Controller.setInstance(new ClientSoloController());

        // Creates five poneys in the game field
        FieldModel fieldModel = new FieldModel(5, true);

        fieldModel.setParticipant(menu.getSelectMenu().getType(),
            menu.getSelectMenu().getColor(), 0);
        fieldModel.setNeighbor();
        csc.setDataView(createDataView(fieldModel));

        csc.setFieldModel(fieldModel);

        // Creates a window 1200x800 px
        JfxView jfxView = new JfxView(stage, 800, 600);
        // Trigger the waterfall initialization
        jfxView.setModel(fieldModel);

        // Launch the game
        csc.startTimer();
    }

    /**
     * Connects to the server and displays the list of current WaitingRooms.
     */
    private boolean initServerConnection() {
        // Get and Set the controller
        ClientMultiController cmc =
            (ClientMultiController) Controller.setInstance(new ClientMultiController());

        if (cmc.initNetwork(host, port)) {
            Thread t = new Thread(cmc);
            t.start();
            return true;
        }

        return false;
    }

    /**
     * Creates the secondary data view.
     *
     * @param fieldModel the fieldmodel of the game
     * @return the created DataView
     */
    private DataView createDataView(FieldModel fieldModel) {
        Stage stage2 = new Stage();
        stage2.setX(stage.getX() + stage.getWidth());
        stage2.setY(stage.getY());

        DataView dataView = new DataView(stage2, 210, 180);

        // Set a default poney model to the data view
        dataView.setParticipantModel(fieldModel.getParticipantModel(0));

        return dataView;
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
