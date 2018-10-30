package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientController;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientSoloController;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
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
    private Group root;

    /**
     * Constructor of JfxView.
     *
     * @param stage
     *            the stage of the view
     * @param width
     *            the width of the view
     * @param height
     *            the height of the view
     */
    public JfxView(Stage stage, final int width, final int height) {
        ClientController cc = (ClientController) Controller.getInstance();
        cc.addView(this);

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
    }

    /**
     * Adds the event listeners.
     */
    private void listenToEvent() {
        // Event Listener de la souris
        ClientController cc = (ClientController) Controller.getInstance();

        scene.setOnMouseClicked(
            m -> cc.mouseClicked(m.getSceneX(), m.getSceneY(), fieldView));
    }

    /**
     * View update() implementation.
     */
    public void update() {
        // nothing to be done
    }

    /**
     * Sets the field model.
     *
     * @param fm
     *            the field model
     */
    public void setModel(FieldModel fm) {
        fieldView.setModel(fm);

        setButtons();
    }

    /**
     * Creates a button for each poney controlled by a human and a button to pause/resume the game.
     */
    private void setButtons() {
        HBox hb = new HBox();
        ClientController cc = (ClientController) Controller.getInstance();

        // Buttons to boost the poneys
        for (MovingEntityView participantView : fieldView.getParticipantViews()) {
            final MovingEntityModel participantModel = participantView.getModel();
            // We don't want buttons for the AIs and the non poney players
            // TODO : Maybe add "Special" button instead, used on every player
            if (participantModel instanceof PoneyModel) {
                if (participantModel.isPlayer()) {
                    Button boostPoney = new Button("Boost poney: " + participantModel.getColor());

                    boostPoney.setOnMouseClicked(
                        m -> cc.boostButton((PoneyModel) participantModel));
                    hb.getChildren().add(boostPoney);
                }
            }
        }

        if (cc instanceof ClientSoloController) {
            ClientSoloController csc = (ClientSoloController) cc;

            // Button to pause/resume the game.
            final Button pauseResume = new Button("Pause");
            pauseResume.setOnMouseClicked(m -> {
                csc.pauseResume();

                if (csc.getTimerActive()) {
                    pauseResume.setText("Pause");
                } else {
                    pauseResume.setText("Resume");
                }

            });
            hb.getChildren().add(pauseResume);
        }

        root.getChildren().add(hb);
    }

    public Scene getScene() {
        return scene;
    }
}
