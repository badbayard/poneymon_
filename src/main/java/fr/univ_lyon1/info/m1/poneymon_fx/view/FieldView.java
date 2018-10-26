package fr.univ_lyon1.info.m1.poneymon_fx.view;

import java.util.ArrayList;
import java.util.List;

import fr.univ_lyon1.info.m1.poneymon_fx.controller.ClientController;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
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
    // MovingEntityViews managed
    private List<MovingEntityView> participantViews = new ArrayList<>();
    // Model of this view
    private FieldModel fieldModel;
    // Background image pattern
    private final ImagePattern grassPattern;

    /**
     * FieldView constructor.
     *
     * @param w
     *            the width of the view
     * @param h
     *            the height of the view
     */
    FieldView(int w, int h) {
        super(w, h);

        ClientController cc = (ClientController) Controller.getInstance();
        cc.addView(this);

        width = w;
        height = h;

        grassPattern = new ImagePattern(new Image("assets/grass.png"), 0, 0, 32, 32, false);

        graphicsContext = getGraphicsContext2D();
    }

    /**
     * Sets the model of the view.
     *
     * @param fm
     *            the model of the view
     */
    public void setModel(FieldModel fm) {
        fieldModel = fm;

        // Build as many MovingEntityView as there is MovingEntityModel in FieldModel
        participantViews.clear();
        MovingEntityModel[] participantModels = fm.getParticipantModels();
        for (MovingEntityModel participantModel : participantModels) {
            // TODO : add each new player created here
            if (participantModel instanceof PoneyModel) {
                participantViews.add(new PoneyView((PoneyModel) participantModel, graphicsContext,
                        width, height));
            }
        }
    }

    /**
     * Determines whether the user clicked on a participant to update the data view.
     *
     * @param xClick
     *            the abscissa of the click
     * @param yClick
     *            the ordinate of the click
     */
    public void manageClick(double xClick, double yClick) {
        ClientController cc = (ClientController) Controller.getInstance();
        cc.addView(this);
        // Nothing to do if the field doesn't handle a dataView
        DataView dataView = cc.getDataView();

        if (dataView == null) {
            return;
        }

        MovingEntityModel participantModel = null;

        // Search for the participant clicked
        for (MovingEntityView participantView : participantViews) {
            participantModel = participantView.getModel();

            int x = participantView.getX();
            int y = participantView.getY();
            int imgWidth = (int) participantView.getImageWidth();
            int imgHeight = (int) participantView.getImageHeight();

            boolean participantClicked = x <= xClick && xClick <= x + imgWidth && y <= yClick
                    && yClick <= y + imgHeight;

            // Add the view to the new focused participant
            if (participantClicked) {
                System.out.println(participantModel.getColor());
                dataView.setParticipantModel(participantModel);
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
     * Gets the participant views.
     *
     * @return a list of MovingEntityViews
     */
    List<MovingEntityView> getParticipantViews() {
        return participantViews;
    }

    /**
     * Get the number of participant views.
     *
     * @return the number of MovingEntityViews
     */
    public int countParticipantViews() {
        return participantViews.size();
    }

    /**
     * Gets the participant view i.
     *
     * @param i
     *            the index of the view wanted
     * @return the MovingEntityView of index i
     */
    public MovingEntityView getParticipantView(int i) {
        return participantViews.get(i);
    }
}