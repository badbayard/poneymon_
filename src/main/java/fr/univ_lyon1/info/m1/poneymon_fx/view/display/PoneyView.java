package fr.univ_lyon1.info.m1.poneymon_fx.view.display;

import fr.univ_lyon1.info.m1.poneymon_fx.model.MovingEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.PoneyModel;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Class handling the graphic side of a Poney.
 */
public class PoneyView extends MovingEntityView implements View {
    // 3 images are created
    private Image nianPoneyImage;

    /**
     * PoneyView constructor.
     *
     * @param pm      the poneymodel associated
     * @param gc      the drawing tool
     * @param cWidth  the canvas width
     * @param cHeight the canvas height
     */
    public PoneyView(PoneyModel pm, GraphicsContext gc, int cWidth, int cHeight) {
        super(pm, gc, cWidth, cHeight,
            "assets/entity/moving/pony-" + pm.getColor() + "-running.gif",
            "assets/entity/moving/pony-" + pm.getColor() + "-jumpingUp.gif",
            "assets/entity/moving/pony-" + pm.getColor() + "-jumpingDown.gif");

        String color = ((MovingEntityModel) participantModel).getColor();
        nianPoneyImage = new Image("assets/entity/moving/pony-" + color + "-rainbow.gif");

        // Update the variable attributes of PoneyView
        update();
    }

    /**
     * Update the poney.
     */
    public void update() {
        // Get the x coordinate
        x = (int) (canvasWidth * participantModel.getX() * widthRatio - imageWidth);

        if(participantModel.getRow() == 0) {
            System.out.println("Poneyview : " + participantModel + " - " + participantModel.getX());
        }

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
        // Set the right image
        if (((PoneyModel) participantModel).isBoosted()) {
            currentParticipantImage = nianPoneyImage;
        } else {
            currentParticipantImage = classicImage;
        }
        if (((MovingEntityModel) participantModel).isJumping() && jumpState() == 1) {
            currentParticipantImage = jumpingUpParticipant;
        } else if (((MovingEntityModel) participantModel).isJumping() && jumpState() == -1) {
            currentParticipantImage = jumpingDownParticipant;
        }

        // Update of the rank view
        rankView.update();

        if (((MovingEntityModel) participantModel).isVisible()) {
            if (((MovingEntityModel) participantModel).isDead()) {
                graphicsContext.drawImage(deadParticipant, x, y);
            } else {
                graphicsContext.drawImage(currentParticipantImage, x, y);
            }
        }

    }

    @Override
    public int getColX() {
        return (int) (x + imageWidth / 2);
    }

    @Override
    public double getColWidth() {
        return imageWidth / 2;
    }
}
