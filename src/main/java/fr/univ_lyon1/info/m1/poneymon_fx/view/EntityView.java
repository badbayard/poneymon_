package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.collision.Collider;
import fr.univ_lyon1.info.m1.poneymon_fx.collision.Transform;
import fr.univ_lyon1.info.m1.poneymon_fx.controller.Controller;
import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.FieldModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class EntityView implements View, Collider {

    // Graphics context
    protected final GraphicsContext graphicsContext;
    // Model associated with the view
    protected final EntityModel participantModel;
    // Size of the canvas
    protected final int canvasWidth;
    protected final int canvasHeight;
    // Entity coordinates
    protected int x;
    protected int y;
    // Image of the entity
    protected Image classicImage;
    // Size of the image
    protected final double imageHeight;
    protected final double imageWidth;
    protected final double widthRatio;

    /**
     * Entity View Constructor.
     * 
     * @param imgUrl
     *            Url of the Entity image.
     */
    public EntityView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight, String imgUrl) {
        Controller.CONTROLLER.addView(this);
        FieldModel.COLLISIONMANAGER.addToColliders(this);
        graphicsContext = gc;
        participantModel = m;
        canvasWidth = cWidth;
        canvasHeight = cHeight;
        classicImage = new Image(imgUrl);
        imageHeight = classicImage.getHeight();
        imageWidth = classicImage.getWidth();
        widthRatio = (canvasWidth + (imageWidth / 2)) / canvasWidth;
        // TODO : Change this with URL generation
    }

    @Override
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * participantModel.getX() * widthRatio - imageWidth);

        // Get the y coordinate
        y = (int) (participantModel.getRow() * imageHeight);

        graphicsContext.drawImage(classicImage, x, y);
    }

    /**
     * Get the model.
     *
     * @return the entityModel associated
     */
    public EntityModel getModel() {
        return participantModel;
    }

    /**
     * Get the image width.
     *
     * @return the image width
     */
    double getImageWidth() {
        return classicImage.getWidth();
    }

    /**
     * Get the image height.
     *
     * @return the image height
     */
    double getImageHeight() {
        return classicImage.getHeight();
    }

    /**
     * Get the abscissa of the poney.
     *
     * @return the abscissa of the poney
     */
    public int getX() {
        return x;
    }

    /**
     * Get the ordinate of the poney.
     *
     * @return the ordinate of the poney
     */
    public int getY() {
        return y;
    }

    @Override
    public int getCollisionLayer() {
        return participantModel.getRow();
    }

    @Override
    public double getWidth() {
        return imageWidth;
    }

    @Override
    public double getHeight() {
        return imageHeight;
    }

    @Override
    public Transform getTransform() {
        return (Transform) participantModel;
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
