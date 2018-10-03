package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

/**
 * Class handling the rank displayed graphically.
 */
public class RankView implements View {
    // View linked to this one
    private final PoneyView poneyView;
    // Drawing tool
    private final GraphicsContext graphicsContext;
    // Poney image size
    private final int imgWidth;
    private final int imgHeight;
    // Rank coordinates
    private int x;
    private int y;
    // Rank to be displayed
    private int rank;

    /**
     * RankView constructor.
     *
     * @param pv the PoneyView associated
     * @param gc the drawing tool
     * @param imWidth the image width
     * @param imHeight the image height
     */
    public RankView(PoneyView pv, GraphicsContext gc, int imWidth,
            int imHeight) {
        poneyView = pv;
        graphicsContext = gc;
        imgHeight = imHeight;
        imgWidth = imWidth;
    }

    /**
     * Update the view.
     */
    public void update() {
        x = poneyView.getX() - 90 + imgWidth;
        y = poneyView.getY() + 30;
        rank = poneyView.getModel().getRank();
    }

    /**
     * Render the view.
     */
    public void display() {
        // Display parameters
        graphicsContext.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        switch (rank) {
            // Gold color
            case 1 :
                graphicsContext.setFill(Color.rgb(249, 200, 2));
                break;
            // Silver color
            case 2 :
                graphicsContext.setFill(Color.rgb(190, 190, 190));
                break;
            // Bronze color
            case 3 :
                graphicsContext.setFill(Color.rgb(240, 127, 50));
                break;
            // Black color
            default :
                graphicsContext.setFill(Color.BLACK);
                break;
        }
        // Display the rank
        graphicsContext.fillText(Integer.toString(rank), x, y);
    }
}
