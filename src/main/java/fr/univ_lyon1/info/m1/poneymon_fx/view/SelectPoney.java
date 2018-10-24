package fr.univ_lyon1.info.m1.poneymon_fx.view;

import java.io.IOException;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;

import javafx.scene.shape.Rectangle;

import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;

/**
 * Class allowing the player to select a poney considering different entitys.  
 */
public class SelectPoney extends Parent {
    
    private VBox poneyBox;
    private AssetsRepertories assetsRepertories;
    //private String [] 
    
    /**
     * Constructor.
     * @param x coord
     * @param y coord
     */
    public SelectPoney(int x, int y) {
        
        
        poneyBox = new VBox(10);
        
        poneyBox.setTranslateX(x / 5);
        poneyBox.setTranslateY(y / 5);
        
        Image image = new Image("assets/entity/pony-blue.gif");
        ImageView imageV = new ImageView(image);
        imageV.setFitWidth(75);
        imageV.setFitHeight(75);
        ToggleButton tb3 = new ToggleButton("", imageV);
        
        poneyBox.getChildren().addAll(tb3);
        
        getChildren().addAll(poneyBox);
    }
}

// Créer 1 ToggleButton par image récupéré
