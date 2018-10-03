package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.text.Text;

import javafx.scene.paint.Color;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

/**
 * Class printing the data of a poney.
 */
public class DataView implements View {
    // Window title
    private static final String WINDOW_TITLE = "Poney Data";
    // Size of the dataView
    private final int width;
    private final int height;
    // PoneyModel which has the focus of the view
    private PoneyModel poneyModel;

    // Text to be displayed
    private Text colorValue = new Text();
    private Text speedValue = new Text();
    private Text progressValue = new Text();
    private Text isAiValue = new Text();
    private Text boostValue = new Text();
    private Text lapValue = new Text();

    // Size of the canvas
    private int canvasWidth;

    /**
     * DataView constructor.
     *
     * @param stage the stage of the view
     * @param w the width of the view
     * @param h the height of the view
     */
    public DataView(Stage stage, int w, int h) {
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
     * Sets the new PoneyModel to focus on.
     *
     * @param pm the new PoneyModel
     */
    void setPoneyModel(PoneyModel pm) {
        poneyModel = pm;
    }

    /**
     * Update the data.
     */
    public void update() {
        // Round the speed

        // Update the texts
        colorValue.setText(poneyModel.getColor());
        colorValue.setStyle("-fx-fill:" + poneyModel.getColor());

        progressValue.setText("" + (int)(poneyModel.getX() * 100) + "%");

        double speed = Math.round(canvasWidth * poneyModel.getSpeed() / PoneyModel.MINIMAL_TIME);
        speedValue.setText("" + speed + " px/s");

        if (!poneyModel.canBoost()) {
            if (poneyModel.isNianPoney()) {
                boostValue.setText("Boosted");
            } else {
                boostValue.setText("Depleted");
            }
        } else {
            boostValue.setText("Available");
        }

        isAiValue.setText("" + poneyModel.isAnAi());

        lapValue.setText("" + poneyModel.getNbLap());
    }

    /**
     * Display the texts.
     */
    public void display() {
    }

    /**
     * Sets the canvas width.
     *
     * @param w the new canvas width
     */
    void setCanvasWidth(int w) {
        canvasWidth = w;
    }
}
