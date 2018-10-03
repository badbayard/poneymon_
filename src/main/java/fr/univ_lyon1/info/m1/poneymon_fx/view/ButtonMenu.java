package fr.univ_lyon1.info.m1.poneymon_fx.view;

import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;

import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

/**
 * Class use to create new buttons.
 */
@SuppressWarnings("restriction")
public class ButtonMenu extends StackPane {
	
    private Text text;
    private Rectangle background;
	
    /**
	 * Constructor of ButtonMenu.
	 * @param name the purpose of this button
	 */
    public ButtonMenu(String name) {
		
        //Creation d'un bouton
        text = new Text(name);
        text.setFont(text.getFont().font(20));
        text.setFill(WHITE);

        background = new Rectangle(250,30);
        background.setOpacity(0.6);
        background.setFill(BLACK);
        background.setEffect(new GaussianBlur(3.5));

        setAlignment(Pos.CENTER_LEFT);
        setRotate(-0.5);
        getChildren().addAll(background,text);
        
        setEvents();
    }
	
    /**
	 * Sets the events.
	 */
    private void setEvents() {
        //Permet une modification lors d'un hover
        setOnMouseEntered(event -> {
            background.setTranslateX(10);
            text.setTranslateX(10);
            background.setFill(WHITE);
            text.setFill(BLACK);
        });
        
        //Permer de rétablir les valeurs par défaut a la fin d'un hover
        setOnMouseExited(event -> {
            background.setTranslateX(0);
            text.setTranslateX(0);
            background.setFill(BLACK);
            text.setFill(WHITE);
        });
        
        //Préparation d'un effet pour le clic
        DropShadow drop = new DropShadow(50, WHITE);
        drop.setInput(new Glow());
        
        setOnMousePressed(event -> setEffect(drop));
        
        setOnMouseReleased(event -> setEffect(null));
    }
}
