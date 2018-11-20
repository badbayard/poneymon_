package fr.univ_lyon1.info.m1.poneymon_fx.view.display;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MovingEntityView extends EntityView {
    // The rank view handled
    protected final RankView rankView;
    protected Image currentParticipantImage;

    public MovingEntityView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight,
            String imgUrl) {
        super(m, gc, cWidth, cHeight, imgUrl);
        rankView = new RankView(this, graphicsContext, (int) imageWidth, (int) imageHeight);
    }

    /**
     * Generic update for movingEntities.
     */
    @Override
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * participantModel.getX() * widthRatio - imageWidth);

        // Get the y coordinate
        int nbPoneys = ((MovingEntityModel) participantModel).countNeighbors() + 1;
        double poneysHeight = nbPoneys * imageHeight;
        double space = (canvasHeight - poneysHeight) / (nbPoneys + 1);
        int row = participantModel.getRow();
        y = (int) ((row + 1) * space + row * imageHeight);

        graphicsContext.drawImage(classicImage, x, y);
    }

    /**
     * Get the model.
     *
     * @return the entityModel associated
     */
    public MovingEntityModel getModel() {
        return (MovingEntityModel) participantModel;
    }
}
