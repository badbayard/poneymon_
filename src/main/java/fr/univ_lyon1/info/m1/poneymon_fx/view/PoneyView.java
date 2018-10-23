package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

/**
 * Class handling the graphic side of a Poney.
 */
public class PoneyView implements View {
    // The rank view handled
    private final RankView rankView;
    // Size of the canvas
    private final int canvasWidth;
    private final int canvasHeight;
    // Size of the image
    private final double imageHeight;
    private final double imageWidth;
    private final double widthRatio;
    // Model associated with the view
    private final PoneyModel poneyModel;
    // Graphics context
    private final GraphicsContext graphicsContext;
    // Poney coordinates
    private int x;
    private int y;
    // 3 images are created
    private Image currentPoneyImage;
    private Image nianPoneyImage;
    private Image classicPoneyImage;

    /**
     * PoneyView constructor.
     *
     * @param pm      the poneymodel associated
     * @param gc      the drawing tool
     * @param cWidth  the canvas width
     * @param cHeight the canvas height
     */
    public PoneyView(PoneyModel pm, GraphicsContext gc, int cWidth, int cHeight) {
        Controller.CONTROLLER.addView(this);

        poneyModel = pm;

        graphicsContext = gc;
        canvasWidth = cWidth;
        canvasHeight = cHeight;

        String color = poneyModel.getColor();

        classicPoneyImage = new Image("assets/pony-" + color + "-running.gif");
        nianPoneyImage = new Image("assets/pony-" + color + "-rainbow.gif");

        imageHeight = classicPoneyImage.getHeight();
        imageWidth = classicPoneyImage.getWidth();

        widthRatio = (canvasWidth + (imageWidth / 2)) / canvasWidth;

        rankView = new RankView(this, graphicsContext, (int) imageWidth, (int) imageHeight);

        // Update the variable attributes of PoneyView
        update();
    }

    /**
     * Update the poney.
     */
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * poneyModel.getX() * widthRatio - imageWidth);

        // Get the y coordinate
        int nbPoneys = poneyModel.countNeighbors() + 1;
        double poneysHeight = nbPoneys * imageHeight;
        double space = (canvasHeight - poneysHeight) / (nbPoneys + 1);
        int row = poneyModel.getRow();
        y = (int) ((row + 1) * space + row * imageHeight);

        // Set the right image
        if (poneyModel.isBoosted()) {
            currentPoneyImage = nianPoneyImage;
        } else {
            currentPoneyImage = classicPoneyImage;
        }

        // Update of the rank view
        rankView.update();
        graphicsContext.drawImage(currentPoneyImage, x, y);

    }

    /**
     * Get the model.
     *
     * @return the poneyModel associated
     */
    public PoneyModel getModel() {
        return poneyModel;
    }

    /**
     * Get the image width.
     *
     * @return the image width
     */
    double getImageWidth() {
        return currentPoneyImage.getWidth();
    }

    /**
     * Get the image height.
     *
     * @return the image height
     */
    double getImageHeight() {
        return currentPoneyImage.getHeight();
    }

    /**
     * Get the abscissa of the poney.
     *
     * @return the abscissa of the poney
     */
    int getX() {
        return x;
    }

    /**
     * Get the ordinate of the poney.
     *
     * @return the ordinate of the poney
     */
    int getY() {
        return y;
    }
}
