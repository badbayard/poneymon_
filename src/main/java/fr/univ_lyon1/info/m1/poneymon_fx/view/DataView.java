package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.text.Text;

import javafx.scene.paint.Color;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

/**
 * Class printing the data of a participant.
 */
public class DataView implements View {
    // Window title
    private static final String WINDOW_TITLE = "participant Data";
    // Size of the dataView
    private final int width;
    private final int height;
    // MovingEntityData which has the focus of the view
    private MovingEntityModel participantModel;

    // Text to be displayed
    private Text colorValue = new Text();
    private Text speedValue = new Text();
    private Text progressValue = new Text();
    private Text isAiValue = new Text();
    private Text boostValue = new Text();
    private Text lapValue = new Text();

    /**
     * DataView constructor.
     *
     * @param stage
     *            the stage of the view
     * @param w
     *            the width of the view
     * @param h
     *            the height of the view
     */
    public DataView(Stage stage, int w, int h) {
        Controller.CONTROLLER.addView(this);

        stage.setTitle(WINDOW_TITLE);

        width = w;
        height = h;

        Group root = new Group();
        Scene scene = new Scene(root, width, height, Color.LIGHTGRAY);
        scene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("grid-pane");

        final Text colorLabel = new Text("Color :");

        final Text progressLabel = new Text("Progress :");

        final Text speedLabel = new Text("Speed :");

        final Text boostLabel = new Text("Boost :");

        final Text placeLabel = new Text("Tour n° :");

        gridPane.add(colorLabel, 0, 0);
        gridPane.add(progressLabel, 0, 1);
        gridPane.add(speedLabel, 0, 2);
        gridPane.add(boostLabel, 0, 3);
        gridPane.add(placeLabel, 0, 4);

        gridPane.add(colorValue, 1, 0);
        gridPane.add(progressValue, 1, 1);
        gridPane.add(speedValue, 1, 2);
        gridPane.add(boostValue, 1, 3);
        gridPane.add(lapValue, 1, 4);

        root.getChildren().add(gridPane);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the new MovingEntityModel to focus on.
     *
     * @param m
     *            the new MovingEntityModel
     */
    public void setParticipantModel(MovingEntityModel m) {
        participantModel = m;
    }

    /**
     * Update the data.
     */
    public void update() {
        // Update the texts
        colorValue.setText(participantModel.getColor());
        colorValue.setStyle("-fx-fill:" + participantModel.getColor());

        progressValue.setText("" + (int) (participantModel.getX() * 100) + "%");

        double speed = Math.round(width * participantModel.getSpeed());
        speedValue.setText("" + speed + " px/s");

        if (participantModel instanceof PoneyModel) {
            if (!((PoneyModel) participantModel).canBoost()) {
                if (((PoneyModel) participantModel).isBoosted()) {
                    boostValue.setText("Boosted");
                } else {
                    boostValue.setText("Depleted");
                }
            } else {
                boostValue.setText("Available");
            }
        }

        isAiValue.setText("" + participantModel.isAi());
        lapValue.setText("" + participantModel.getNbLap());
    }
}
