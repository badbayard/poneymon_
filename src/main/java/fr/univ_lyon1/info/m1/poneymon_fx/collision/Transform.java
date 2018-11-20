package fr.univ_lyon1.info.m1.poneymon_fx.collision;

/**
 * Interface referencing all the models that have view 
 * with a Collider attached.
 * @author ali
 *
 */
public interface Transform {
    
    void onCollision(Collider col);
    void onTrigger(Collider collider);

}
