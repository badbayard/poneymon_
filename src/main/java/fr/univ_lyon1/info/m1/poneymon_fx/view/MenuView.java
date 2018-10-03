package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class handling the communication between the menus and jfxView.
 */
@SuppressWarnings("restriction")
public class MenuView {
	
    private MainMenu mainMenu;
    private Scene scene;
    
    /**
	 * Constructor of MenuView.
	 */
    public MenuView() {
		
        //Creation de la fenêtre.
        Pane root = new Pane();
        root.setPrefSize(800, 600);
        
        //Chargement du background.
        Image background = new Image("assets/bgMenu.jpg");
        
        //Adapte l'image a la taille de la fenêtre
        ImageView imgView = new ImageView(background);
        imgView.setFitWidth(800);
        imgView.setFitHeight(600);
        
        //Affiche le menu principal
        mainMenu = new MainMenu();
        mainMenu.setVisible(true);
        
        root.getChildren().addAll(imgView, mainMenu);
        
        scene = new Scene(root);
    }

    /**
     * Get the scene.
     * @return the scene attribut
     */
    public Scene getScene() {
        return scene;
    }
}
