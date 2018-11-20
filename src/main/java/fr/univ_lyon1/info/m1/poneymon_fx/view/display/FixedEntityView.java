package fr.univ_lyon1.info.m1.poneymon_fx.view.display;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import javafx.scene.canvas.GraphicsContext;

public class FixedEntityView extends EntityView {

    public FixedEntityView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight,
            String imgUrl) {
        super(m, gc, cWidth, cHeight, imgUrl);
    }

    @Override
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * participantModel.getX() * widthRatio - imageWidth);

        // Get the y coordinate
        y = (int) (participantModel.getRow() * imageHeight);

        if (!((FixedEntityModel) participantModel).isRaceFinished()) {
            graphicsContext.drawImage(classicImage, x, y);
        }
    }

}
