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
import fr.univ_lyon1.info.m1.poneymon_fx.view.ListRoomView;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.SoundController;

/**
 * Main application class.
 * Needs JavaFx.
 */
public class App extends Application {
    private MenuView menu;
    private ListRoomView menulistroom;
    private Stage stage;
    private Stage stage2;
    private SoundController m;

    /**
     * Start() launch the application.
     *
     * @param s the application stage
     * @see <a
     *     href="http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">
     *     jfxpub-scenegraph.htm</a>
     */
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        stage2 = s;
        m = new SoundController();

        // Secondary view
        /*Stage s3 = new Stage();
        JfxView v2 = new JfxView(s3, 1000, 600);
        c.addView(v2);
        v2.setModel(m);
        v2.setController(c);*/

        menu = new MenuView(800, 600);
        menulistroom = new ListRoomView(800,600);

        stage.setScene(menu.getScene());
        stage.show();
        m.playchunk();

        setEvents();
    }

    private void setEvents() {
        //Event Play solo
        ButtonMenu btnPlay = menu.getMainMenu().getBtnPlay();
        btnPlay.setOnMouseClicked(event -> openSelectMenuSolo());

        //Event Play multi
        ButtonMenu btnPlayMulti = menu.getMainMenu().getBtnPlayMulti();
        btnPlayMulti.setOnMouseClicked(event -> {
            stage2.setScene(menulistroom.getScene2());
            stage.close();
            stage2.show();
        });

        //Event exit game
        ButtonMenu btnExit = menu.getMainMenu().getBtnExit();
        btnExit.setOnMouseClicked(event -> Platform.exit());
        
        //Event confirm selected poney
        ButtonMenu btnConfirm = menu.getSelectMenu().getBtnConfirm();
        btnConfirm.setOnMouseClicked(event -> soloOrMulti());
        
        //Event back to main menu
        ButtonMenu btnBack = menu.getSelectMenu().getBtnBack();
        btnBack.setOnMouseClicked(event -> backToMain());
    }

    /**
     * Launch a solo game or send data to the server.
     */
    private void soloOrMulti() {
        if (menu.getIsSolo()) {
            createGameSolo();
        } else {
            //TODO send data to the serv
        }
    }

    /**
     * Hide the current menu and display the main menu.
     */
    private void backToMain() {
        menu.backToMainMenu();
    }

    /**
     * Display the SelectEntity menu.
     */
    private void openSelectMenuSolo() {
        menu.setIsSolo(true);
        menu.activateSelectMenu();
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
        
        fieldModel.setParticipant(menu.getSelectMenu().getType(),menu.getSelectMenu().getColor() , 0);
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
