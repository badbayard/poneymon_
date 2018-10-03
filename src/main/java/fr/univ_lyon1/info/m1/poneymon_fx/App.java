package fr.univ_lyon1.info.m1.poneymon_fx;

import javafx.application.Application;
import javafx.stage.Stage;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.view.JfxView;
import fr.univ_lyon1.info.m1.poneymon_fx.view.DataView;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

/**
 * Main application class.
 * Needs JavaFx.
 */
public class App extends Application {

    /**
     * Start() launch the application.
     * @see <a
     * href="http://docs.oracle.com/javafx/2/scenegraph/jfxpub-scenegraph.htm">
     * jfxpub-scenegraph.htm</a>
     *
     * @param stage the application stage
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Creates five poneys in the game field
        FieldModel m = new FieldModel(5);
        // Creates a window 1200x800 px
        JfxView v = new JfxView(stage, 1200, 800);
        // Creates a controller
        Controller c = new Controller();

        // Second window (stats)
        Stage s2 = new Stage();
        DataView dv = new DataView(s2, 175, 150);
        
        c.addView(v);
        c.addView(dv);
        c.addModel(m);
        v.setDataView(dv);
        // Trigger the waterfall initialization
        v.setModel(m);
        v.setController(c);

        // Secondary view
        /*Stage s3 = new Stage();
        JfxView v2 = new JfxView(s3, 1000, 600);
        c.addView(v2);
        v2.setModel(m);
        v2.setController(c);*/

        
        // Launch the game
        c.startTimer();
    }

    public static void main(String[] args) {
        // System.out.println( "Hello World!" );
        Application.launch(args);
    }
}
