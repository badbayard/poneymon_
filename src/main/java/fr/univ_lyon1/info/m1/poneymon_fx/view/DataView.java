package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.text.Text;

import javafx.scene.paint.Color;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Class printing the data of a poney.
 */
public class DataView extends Canvas implements View {
    // Window title
    static final String WINDOW_TITLE = "Poney Data";
    // Size of the dataView
    private final int width;
    private final int height;
    // Drawing tool
    private final GraphicsContext graphicsContext;
    private final Stage stage;
    private final Scene scene;
    // PoneyModel which has the focus of the view
    private PoneyModel poneyModel;
    private Group root;
    // Text to be displayed
    private Text poneyColor;
    private Text poneySpeed;
    private Text xMessage;
    private Text row;
    private Text isAi;
    private Text boostActive;
    private Text lapNumber;
    // Size of the canvas
    private int canvasWidth;

    /**
     * DataView constructor.
     *
     * @param s the stage of the view
     * @param w the width of the view
     * @param h the height of the view
     */
    public DataView(Stage s, int w, int h) {
        super(w, h);
        stage = s;
        stage.setTitle(WINDOW_TITLE);

        width = w;
        height = h;

        root = new Group();

        scene = new Scene(root, width, height, Color.LIGHTGRAY);
        
        graphicsContext = getGraphicsContext2D();

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets the new PoneyModel to focus on.
     *
     * @param pm the new PoneyModel
     */
    public void setPoneyModel(PoneyModel pm) {
        // Clean the root
        if (!root.getChildren().isEmpty()) {
            root.getChildren().remove(0, 6);
        }
        // Update the texts
        poneyModel = pm; 
        poneyColor = new Text(15, 20, "Color : " + poneyModel.getColor());
        isAi = new Text(15, 40, "AI : " + poneyModel.isAnAi());

        update();

        // Add the new texts
        root.getChildren().add(0, poneyColor);
        root.getChildren().add(1, poneySpeed);
        root.getChildren().add(2, xMessage);
        root.getChildren().add(3, isAi);
        root.getChildren().add(4, boostActive);
        root.getChildren().add(5, lapNumber);
    }

    /**
     * Update the data.
     */
    public void update() {
        // Round the speed
        double speed = Math.round(canvasWidth * poneyModel.getSpeed() 
                                  / PoneyModel.MINIMAL_TIME);
        // Update the texts
        poneySpeed = new Text(15, 60, "Speed : " + speed + " px/s.");
        xMessage = new Text(15, 80, "X : " + (int)(poneyModel.getX() * canvasWidth));
        boostActive = new Text(15, 100, "Boost : " + poneyModel.isNianPoney());
        lapNumber = new Text(15, 120, "Lap : " + poneyModel.getNbLap());
    }

    /**
     * Display the texts.
     */
    public void display() {  
        root.getChildren().set(1, poneySpeed);
        root.getChildren().set(2, xMessage);
        root.getChildren().set(4, boostActive);
        root.getChildren().set(5, lapNumber);
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
