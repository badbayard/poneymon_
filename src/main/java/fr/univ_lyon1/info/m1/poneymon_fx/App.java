package fr.univ_lyon1.info.m1.poneymon_fx;

import javafx.application.Application;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.JfxView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.MenuView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.ButtonMenu;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;

import java.io.IOException;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

/**
 * Main application class.
 * Needs JavaFx.
 */
public class App extends Application {

	
    private MenuView menu;
    private Stage stage;
    
    /**
     * Start() launch the application.
     * @see <a
     * href="http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">
     * jfxpub-scenegraph.htm</a>
     *
     * @param s the application stage
     */
    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        
        // Secondary view
        /*Stage s3 = new Stage();
        JfxView v2 = new JfxView(s3, 1000, 600);
        c.addView(v2);
        v2.setModel(m);
        v2.setController(c);*/
        
        menu = new MenuView();
        stage.setScene(menu.getScene());
        stage.show();
        
        setEvents();
    }

    private void setEvents() {
        //Event Play solo
        ButtonMenu btnPlay = menu.getMainMenu().getBtnPlay();
        btnPlay.setOnMouseClicked(event -> {
            createGameSolo();
        });
        
        //Event Play multi
        ButtonMenu btnPlayMulti = menu.getMainMenu().getBtnPlayMulti();
        btnPlayMulti.setOnMouseClicked(event -> {
            //Menu suivant ?
        });

        //Event exit game
        ButtonMenu btnExit = menu.getMainMenu().getBtnExit();
        btnExit.setOnMouseClicked(event -> {
            System.exit(0);
        });
    }

    /**
     * Create a one-player game.
     */
    private void createGameSolo() {
        // Creates a window 1200x800 px
        JfxView jfxView = new JfxView(stage, 1200, 800);
        // Creates a controller
        Controller controller = new Controller();

        // Second window (stats)
        Stage s2 = new Stage();
        s2.setX(stage.getX() + stage.getWidth());
        s2.setY(stage.getY());

        DataView dataView = new DataView(s2, 210, 180);

        // Creates five poneys in the game field
        FieldModel fieldModel = new FieldModel(5);

        controller.addView(jfxView);
        controller.addView(dataView);
        controller.addModel(fieldModel);
        
        jfxView.setDataView(dataView);
        // Trigger the waterfall initialization
        jfxView.setModel(fieldModel);
        jfxView.setController(controller);

        // Launch the game
        controller.startTimer();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
