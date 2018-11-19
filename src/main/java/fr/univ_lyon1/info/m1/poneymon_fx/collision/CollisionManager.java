package fr.univ_lyon1.info.m1.poneymon_fx.collision;

import java.util.ArrayList;

public class CollisionManager {
    // Contain all Colliders of the fieldCollider.
    private ArrayList<Collider> colliders;
    // Contain all Transforms of the fieldModel.
    //TODO Remove, since unused
    private ArrayList<Transform> transforms;

    public CollisionManager() {
        colliders = new ArrayList<Collider>();
        transforms = new ArrayList<Transform>();
    }

    /**
     * Add to colliders array.
     */
    public void addToColliders(Collider c) {
        colliders.add(c);
    }

    /**
     * Remove from colliders array.
     */
    public void removeFromColliders(Collider c) {
        colliders.remove(c);
    }

    /**
     * Add to transforms array.
     */
    public void addToTransforms(Transform t) {
        transforms.add(t);
    }

    /**
     * Remove from transforms array.
     */
    public void removeFromTransforms(Transform t) {
        transforms.remove(t);
    }

    /**
     * Check if there is any collision between the colliders and notify the corresponding transform
     * if there is.
     */
    public void checkCollision() {
        for (int i = 0; i < colliders.size() - 1; i++) {
            for (int j = i + 1; j < colliders.size(); j++) {
                // if the two collider are on the same layer and are active
                if ((colliders.get(i).getCollisionLayer() == colliders.get(j).getCollisionLayer())
                        && (colliders.get(i).isActive() == colliders.get(j).isActive()
                                && colliders.get(j).isActive() == true)) {
                    // If the two collider are colliding
                    if (areColliding(colliders.get(i), colliders.get(j))) {
                        colliders.get(i).getTransform().onCollision(colliders.get(j));
                        colliders.get(j).getTransform().onCollision(colliders.get(i));
                    }
                }
            }
        }
    }

    /**
     * Check if 2 colliders are touching.
     */
    public boolean areColliding(Collider colA, Collider colB) {
        return rangeIntersect(colA.getColX(), colA.getColX() + colA.getColWidth(), colB.getColX(),
                colB.getColX() + colB.getColWidth())
                && rangeIntersect(colA.getColY(), colA.getColY() + colA.getColHeight(), colB.getColY(),
                        colB.getColY() + colB.getColHeight());

    }

    /**
     * Check if two ranges are overlapping.
     */
    private boolean rangeIntersect(double min1, double max1, double min2, double max2) {
        return Math.max(min1, max1) >= Math.min(min2, max2)
                && Math.min(min1, max1) <= Math.max(min2, max2);
    }

    /**
     * Accesseur transforms.
     * @return List Transform
     */
    public ArrayList<Transform> getTransforms() {
        return transforms;
    }

    /**
     * Accesseur colliders.
     * @return List Collider
     */
    public ArrayList<Collider> getColliders() {
        return colliders;
    }

    /**
     * Mutateur transforms.
     * @param newTransform List Transform
     */
    public void setTransforms(ArrayList<Transform> newTransform) {
        transforms = newTransform;
    }

    /**
     * Mutateur collider.
     * @param newColliders List Collider.
     */
    public void setColliders(ArrayList<Collider> newColliders) {
        colliders = newColliders;
    }


}
