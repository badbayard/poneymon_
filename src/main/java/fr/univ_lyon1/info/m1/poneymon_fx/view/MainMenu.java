package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

@SuppressWarnings("restriction")

/**
 * Class creating the main menu.
 */
public class MainMenu extends Parent {
	
    private VBox mainMenu;
    private ButtonMenu btnSolo;
	
    /**
     * Constructor of MainMenu.
     */
    public MainMenu() {
        //La valeur 10 corresponds à l'écarts entre les différents éléments
        mainMenu = new VBox(10);
		
        btnSolo = new ButtonMenu("Play alone :'(");
		
        mainMenu.getChildren().add(btnSolo);
        
        getChildren().addAll(mainMenu);
    }
}