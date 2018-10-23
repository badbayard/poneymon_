package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;
import javafx.scene.canvas.GraphicsContext;

public class StaticEntityView extends EntityView {

    public StaticEntityView(EntityModel m, GraphicsContext gc, int cWidth, int cHeight,
            String imgUrl) {
        super(m, gc, cWidth, cHeight, imgUrl);
    }

}
