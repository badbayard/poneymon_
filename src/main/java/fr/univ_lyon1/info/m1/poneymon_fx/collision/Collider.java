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
    double getWidth();

    /**
     * Get the Collider height.
     */
    double getHeight();

    /**
     * Get the Collider X.
     */
    int getX();

    /**
     * Get the Collider Y.
     */
    int getY();

    /**
     * If the collider is active or not.
     * 
     * @return boolean
     */
    boolean isActive();

    Transform getTransform();
}
