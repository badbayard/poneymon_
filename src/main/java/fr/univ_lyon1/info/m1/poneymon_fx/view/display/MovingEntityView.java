package fr.univ_lyon1.info.m1.poneymon_fx.view.display;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MovingEntityView extends EntityView {
    // The rank view handled
    protected final RankView rankView;
    protected Image currentParticipantImage;
    protected Image deadParticipant;
    protected Image jumpingUpParticipant;
    protected Image jumpingDownParticipant;

    /**
     * Generic Constructor.
     */
    public MovingEntityView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight,
                            String imgUrlParticipant, String imgUrlJumpUp, String imgUrlJumpDown) {
        super(m, gc, cWidth, cHeight, imgUrlParticipant);
        deadParticipant = new Image("assets/entity/moving/DeadPoney.gif");
        jumpingUpParticipant = new Image(imgUrlJumpUp);
        jumpingDownParticipant = new Image(imgUrlJumpDown);
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
        double jumpOffset = 0;
        // Get the jump Y offset
        if (((MovingEntityModel) participantModel).isJumping()) {

            jumpOffset = calculateJumpOffset();
        }

        y = (int) (((row + 1) * space + row * imageHeight) - jumpOffset);
        if (((MovingEntityModel) participantModel).isVisible()) {
            if (((MovingEntityModel) participantModel).isDead()) {
                graphicsContext.drawImage(deadParticipant, x, y);
            } else {
                graphicsContext.drawImage(classicImage, x, y);
            }
        }
    }

    /**
     * Get the model.
     *
     * @return the entityModel associated
     */
    public MovingEntityModel getModel() {
        return (MovingEntityModel) participantModel;
    }

    /**
     * JumpOffset.
     */
    double calculateJumpOffset() {
        double offset;
        double jumpDuration = MovingEntityModel.JUMP_DURATION;
        double maxHeight = MovingEntityModel.MAX_JUMP_HEIGHT;
        double jumpStartTime = ((MovingEntityModel) participantModel).getJumpStartTime();
        double x = Math.abs((System.currentTimeMillis() - jumpStartTime));
        if (jumpState() == 1) { // If the player is going up
            offset = ((2 * maxHeight * x) / jumpDuration);
        } else if (jumpState() == -1) { // If the player is going down
            offset = ((-2 * maxHeight * x) / jumpDuration) + 2 * maxHeight;
        } else {
            offset = 0;
        }
        return offset;
    }

    /**
     * Jump State.
     */
    int jumpState() {
        if ((System.currentTimeMillis() - ((MovingEntityModel) participantModel)
            .getJumpStartTime()) < (MovingEntityModel.JUMP_DURATION / 2)) {
            return 1;
        } else if (System.currentTimeMillis() - ((MovingEntityModel) participantModel)
            .getJumpStartTime() < (MovingEntityModel.JUMP_DURATION)) {
            return -1;
        } else {
            return 0;
        }

    }
    
    @Override
    public double getSpeed() {
        return (((MovingEntityModel) participantModel).getSpeed() * canvasWidth);
    }
}
