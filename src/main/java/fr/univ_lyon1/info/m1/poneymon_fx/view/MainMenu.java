package fr.univ_lyon1.info.m1.poneymon_fx.view;

import java.io.IOException;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;

@SuppressWarnings("restriction")

/**
 * Class creating the main menu.
 */
public class MainMenu extends Parent {

    private VBox mainMenu;
    private ButtonMenu btnSolo;
    private ButtonMenu btnMulti;
    private ButtonMenu btnExit;
    
    private VBox poneyBox;

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
        
        poneyBox = new VBox(10);
        
        poneyBox.setTranslateX(x / 2);
        poneyBox.setTranslateY(y / 2);
        
        Image image = new Image("assets/entity/pony-blue.gif");
        ImageView imageV = new ImageView(image);
        imageV.setFitWidth(75);
        imageV.setFitHeight(75);
        ToggleButton tb3 = new ToggleButton("", imageV);

        poneyBox.getChildren().addAll(tb3);
        
        getChildren().addAll(mainMenu/*, poneyBox*/);
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
