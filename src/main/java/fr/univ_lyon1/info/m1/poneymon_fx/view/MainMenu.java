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
    public MainMenu(int x, int y) {
        //La valeur 10 corresponds à l'écarts entre les différents éléments
        mainMenu = new VBox(10);

        mainMenu.setTranslateX(x / 5);
        mainMenu.setTranslateY(y / 3);

        btnSolo = new ButtonMenu("Play alone");
        btnMulti = new ButtonMenu("Multiplayer");
        btnExit = new ButtonMenu("Exit");

        mainMenu.getChildren().addAll(btnSolo, btnMulti, btnExit);

        getChildren().addAll(mainMenu);
    }

    /**
     * Get the button play.
     *
     * @return field btnSolo;
     */
    public ButtonMenu getBtnPlay() {
        return btnSolo;
    }

    /**
     * Get the button multi.
     *
     * @return field btnMulti.
     */
    public ButtonMenu getBtnPlayMulti() {
        return btnMulti;
    }

    /**
     * Get the button exit.
     *
     * @return field btnExit.
     */
    public ButtonMenu getBtnExit() {
        return btnExit;
    }
}
