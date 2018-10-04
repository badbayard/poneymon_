package fr.univ_lyon1.info.m1.poneymon_fx.view;

import java.io.IOException;

import javafx.scene.Node;
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
    private ButtonMenu btnMulti;
    private ButtonMenu btnExit;
	
    /**
     * Constructor of MainMenu.
     */
    public MainMenu() {
        //La valeur 10 corresponds à l'écarts entre les différents éléments
        mainMenu = new VBox(10);
        
        mainMenu.setTranslateX(100);
        mainMenu.setTranslateY(200);
        
        btnSolo = new ButtonMenu("Play alone");
        btnMulti = new ButtonMenu("Multiplayer");
        btnExit = new ButtonMenu("Exit");
		
        mainMenu.getChildren().addAll(btnSolo, btnMulti, btnExit);
        
        getChildren().addAll(mainMenu);
    }

    public ButtonMenu getBtnPlay() {
        return btnSolo;
    }

    public ButtonMenu getBtnExit() {
        return btnExit;
    }
}