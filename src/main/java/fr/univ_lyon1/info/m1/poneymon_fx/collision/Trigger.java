package fr.univ_lyon1.info.m1.poneymon_fx.collision;

public interface Trigger {
    /**
     * Get collision type.
     */
    int getCollisionLayer();

    /**
     * Get the Collider width.
     */
    double getTrWidth();

    /**
     * Get the Collider height.
     */
    double getTrHeight();

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
    boolean isActive();
    
    double getSpeed();
    Transform getTransform();
}
