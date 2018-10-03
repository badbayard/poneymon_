package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;

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
    // Controller of this view
    private Controller controller = null;
    // Model of this view
    private FieldModel fieldModel;
    // DataView managed
    private DataView dataView;

    /**
     * FieldView constructor.
     *
     * @param w the width of the view
     * @param h the height of the view
     */
    public FieldView(int w, int h) {
        super(w, h);
        width = w;
        height = h;

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

        // Set the canvas width and the poneyModel
        if (dataView != null) {
            dataView.setCanvasWidth(width);
            dataView.setPoneyModel(poneyModels[0]);
            poneyModels[0].addView(dataView);
        }
    }

    /**
     * Sets the view controller.
     *
     * @param c the controller
     */
    public void setController(Controller c) {
        controller = c;
        for (int i = 0; i < poneyViews.size(); i++) {
            poneyViews.get(i).setController(c);
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
        if (dataView == null) {
            return;
        }

        boolean focusOnPoney = false;
        PoneyModel pm = null;
        // Search for the poney clicked
        for (PoneyView pv : poneyViews) {
            pm = pv.getModel();

            int x = pv.getX();
            int y = pv.getY();
            int imgWidth = (int) pv.getImageWidth();
            int imgHeight = (int) pv.getImageHeight();
            
            boolean poneyClicked = x <= xClick && xClick <= x + imgWidth
                                    && y <= yClick && yClick <= y + imgHeight;

            // Add the view to the new focused poney
            if (poneyClicked) {
                dataView.setPoneyModel(pm);
                pm.addView(dataView);
                focusOnPoney = true;
            }
        }

        // Remove the view from the old focused poney
        if (focusOnPoney && pm != null) {
            for (PoneyView pv : poneyViews) {
                if (!pv.getModel().equals(pm)) {
                    pm.removeView(dataView);
                }
            }
        }
    }

    /**
     * Updates the field view.
     */
    public void update() {
        // Nothing to be done for now
    }

    /**
     * Renders the game field and the poneys.
     */
    public void display() {
        graphicsContext.clearRect(0, 0, width, height);
        graphicsContext.setFill(Color.rgb(240, 240, 240));
        graphicsContext.fillRect(0, 0, width, height);

        // Render the poneys
        for (PoneyView poney : poneyViews) {
            poney.display();
        }
    }

    /**
     * Gets the poney views.
     *
     * @return a list of PoneyViews
     */
    public List<PoneyView> getPoneyViews() {
        return poneyViews;
    }
    
    /**
     * Set the DataView.
     *
     * @param dv the dataView
     */
    public void setDataView(DataView dv) {
        dataView = dv;
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
     *
     * @return the poneyView of index i
     */
    public PoneyView getPoneyView(int i) {
        return poneyViews.get(i);
    }
}