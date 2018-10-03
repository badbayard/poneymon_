package fr.univ_lyon1.info.m1.poneymon_fx.view;

/**
 * Interface for any view used in the game.
 */
public interface View {
    /**
     * Update logically the view.
     */
    void update();
    
    /**
     * Display the view on the screen.
     */
    void display();
}