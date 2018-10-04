package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 * Display a window using JavaFX.
 */
public class JfxView implements View {
    // Window title
    private static final String WINDOW_TITLE = "Poneymon";
    private final Scene scene;
    // Field model 
    private FieldView fieldView;
    private Controller controller;
    private Group root;

    /**
     * Constructor of JfxView.
     *
     * @param stage  the stage of the view
     * @param width  the width of the view
     * @param height the height of the view
     */
    public JfxView(Stage stage, final int width, final int height) {
        stage.setTitle(WINDOW_TITLE);

        root = new Group();
        scene = new Scene(root);

        // Creation of the game board and addition to the scene's root
        fieldView = new FieldView(width, height);
        root.getChildren().add(fieldView);

        // MouseListener
        listenToEvent();

        // On ajoute la scene a la fenetre et on affiche
        stage.setScene(scene);
        stage.show();

        //Close all the stages when the main stage is closed.
        stage.setOnCloseRequest(e -> Platform.exit());
    }

    /**
     * Adds the event listeners.
     */
    private void listenToEvent() {
        // Event Listener de la souris
        scene.setOnMouseClicked(m ->
            controller.mouseClicked(m.getSceneX(), m.getSceneY(), fieldView)
        );
    }

    /**
     * View display() implementation.
     */
    public void display() {
        fieldView.display();
    }

    /**
     * View update() implementation.
     */
    public void update() {
        fieldView.update();
    }

    /**
     * Sets the field model.
     *
     * @param fm the field model
     */
    public void setModel(FieldModel fm) {
        fieldView.setModel(fm);

        setButtons();
    }

    /**
     * Sets the controller.
     *
     * @param c the controller
     */
    public void setController(Controller c) {
        controller = c;
        fieldView.setController(c);
    }

    /**
     * Sets the data view.
     *
     * @param dv the dataView
     */
    public void setDataView(DataView dv) {
        fieldView.setDataView(dv);
    }

    /**
     * Creates a button for each poney controlled by a human and a button to
     * pause/resume the game.
     */
    private void setButtons() {
        HBox hb = new HBox();

        //Buttons to boost the poneys
        for (PoneyView poneyView : fieldView.getPoneyViews()) {
            final PoneyModel poneyModel = poneyView.getModel();
            //We don't want buttons for the AIs
            if (poneyModel.isAPlayer()) {
                Button boostPoney = new Button("Boost poney: " + poneyModel.getColor());

                boostPoney.setOnMouseClicked(m -> controller.boostButton(poneyModel));
                hb.getChildren().add(boostPoney);
            }
        }
        //Button to pause/resume the game.
        final Button pauseResume = new Button("Pause");
        pauseResume.setOnMouseClicked(m -> {
            controller.pauseResume();

            if (controller.getTimerActive()) {
                pauseResume.setText("Pause");
            } else {
                pauseResume.setText("Resume");
            }

        });
        hb.getChildren().add(pauseResume);
        root.getChildren().add(hb);
    }

    public Scene getScene() {
        return scene;
    }
}
