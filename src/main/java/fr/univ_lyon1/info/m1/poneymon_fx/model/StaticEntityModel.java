package fr.univ_lyon1.info.m1.poneymon_fx.model;

import fr.univ_lyon1.info.m1.poneymon_fx.model.EntityModel;

public abstract class StaticEntityModel extends EntityModel {

    public StaticEntityModel(int r, double x) {
        super(r);
        this.setX(x);
    }

}
