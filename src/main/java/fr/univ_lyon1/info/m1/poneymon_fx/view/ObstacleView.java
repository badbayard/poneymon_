package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObstacleModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.scene.canvas.GraphicsContext;

public class ObstacleView extends FixedEntityView {

    /**
     * Default constructor for the objectView.
     */
    public ObstacleView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight) {
        // TODO Insert Image for the Obstacle
        super(m, gc, cWidth, cHeight, "assets/entity/obstacle-fence.gif");
        // TODO Auto-generated constructor stub
    }

    /**
     * Update the poney.
     */
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * participantModel.getX() * widthRatio - imageWidth);

        // Get the y coordinate
        y = (int) (participantModel.getRow() * imageHeight);
        // Only draw if the Model is visible
        if (((ObstacleModel) participantModel).isVisible()) {
            graphicsContext.drawImage(classicImage, x, y);
        }
    }
    @Override
    public boolean isActive() {
        return ((ObstacleModel) participantModel).isVisible();
    }

}
