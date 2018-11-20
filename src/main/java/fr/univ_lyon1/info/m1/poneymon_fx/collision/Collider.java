package fr.univ_lyon1.info.m1.poneymon_fx.collision;

/**
 * Interface referencing the views that have a collider attached.
 * 
 * @author ali
 *
 */
public interface Collider {

    /**
     * Get collision type.
     */
    int getCollisionLayer();

    /**
     * Get the Collider width.
     */
    double getColWidth();

    /**
     * Get the Collider height.
     */
    double getColHeight();

    /**
     * Get the Collider X.
     */
    int getColX();

    /**
     * Get the Collider Y.
     */
    int getColY();

    /**
     * If the collider is active or not.
     * 
     * @return boolean
     */
    double getSpeed();
    boolean isActive();

    Transform getTransform();
}
