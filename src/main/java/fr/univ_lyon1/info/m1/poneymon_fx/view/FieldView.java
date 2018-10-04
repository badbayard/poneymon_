package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.Canvas;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.scene.paint.ImagePattern;

/**
 * Game field View.
 */
public final class FieldView extends Canvas implements View {
    // Size of the field
    private final int width;
    private final int height;
    // Drawing tool
    private final GraphicsContext graphicsContext;
    // PoneyViews managed
    private List<PoneyView> poneyViews = new ArrayList<>();
    // Model of this view
    private FieldModel fieldModel;
    // Background image pattern
    private final ImagePattern grassPattern;

    /**
     * FieldView constructor.
     *
     * @param w the width of the view
     * @param h the height of the view
     */
    FieldView(int w, int h) {
        super(w, h);

        Controller.CONTROLLER.addView(this);

        width = w;
        height = h;

        grassPattern = new ImagePattern(new Image("assets/grass.png"), 0, 0, 32, 32, false);

        graphicsContext = getGraphicsContext2D();
    }

    /**
     * Sets the model of the view.
     *
     * @param fm the model of the view
     */
    public void setModel(FieldModel fm) {
        fieldModel = fm;

        // Build as many PoneyView as there is PoneyModel in FieldModel
        poneyViews.clear();
        PoneyModel[] poneyModels = fm.getPoneyModels();
        for (PoneyModel poneyModel : poneyModels) {
            poneyViews.add(new PoneyView(poneyModel, graphicsContext, width, height));
        }
    }

    /**
     * Determines whether the user clicked on a poney to update the data view.
     *
     * @param xClick the abscissa of the click
     * @param yClick the ordinate of the click
     */
    public void manageClick(double xClick, double yClick) {
        // Nothing to do if the field doesn't handle a dataView
        DataView dataView = Controller.CONTROLLER.getDataView();

        if (dataView == null) {
            return;
        }

        PoneyModel poneyModel = null;

        // Search for the poney clicked
        for (PoneyView poneyView : poneyViews) {
            poneyModel = poneyView.getModel();

            int x = poneyView.getX();
            int y = poneyView.getY();
            int imgWidth = (int) poneyView.getImageWidth();
            int imgHeight = (int) poneyView.getImageHeight();

            boolean poneyClicked = x <= xClick && xClick <= x + imgWidth
                && y <= yClick && yClick <= y + imgHeight;

            // Add the view to the new focused poney
            if (poneyClicked) {
                System.out.println(poneyModel.getColor());
                dataView.setPoneyModel(poneyModel);
            }
        }
    }

    /**
     * Updates the field view.
     */
    public void update() {
        graphicsContext.setFill(grassPattern);
        graphicsContext.fillRect(0, 0, width, height);
    }

    /**
     * Gets the poney views.
     *
     * @return a list of PoneyViews
     */
    List<PoneyView> getPoneyViews() {
        return poneyViews;
    }

    /**
     * Get the number of poney views.
     *
     * @return the number of PoneyViews
     */
    public int countPoneyViews() {
        return poneyViews.size();
    }

    /**
     * Gets the poney view i.
     *
     * @param i the index of the view wanted
     * @return the poneyView of index i
     */
    public PoneyView getPoneyView(int i) {
        return poneyViews.get(i);
    }
}