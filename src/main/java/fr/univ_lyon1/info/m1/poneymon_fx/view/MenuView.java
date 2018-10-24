package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import fr.univ_lyon1.info.m1.poneymon_fx.view.MainMenu;

/**
 * Class handling the communication between the menus and jfxView.
 */
@SuppressWarnings("restriction")
public class MenuView {
	
    private MainMenu mainMenu;
    private SelectPoney selectMenu;
    private Scene scene;
    private int width;
    private int height;
    
    /**
	 * Constructor of MenuView.
	 */
    public MenuView(int x, int y) {
		
        width = x;
        height = y;
        
        //Creation de la fenêtre.
        Pane root = new Pane();
        root.setPrefSize(width, height);
        
        //Chargement du background.
        Image background = new Image("assets/bgMenu.jpg");
        
        //Adapte l'image a la taille de la fenêtre
        ImageView imgView = new ImageView(background);
        imgView.setFitWidth(width);
        imgView.setFitHeight(height);
        
        //Affiche le menu principal
        mainMenu = new MainMenu(width, height);
        mainMenu.setVisible(true); 
        
        
        //Prépare le menu de sélection de poney
        selectMenu = new SelectPoney(width, height);
        selectMenu.setVisible(false);
        
        root.getChildren().addAll(imgView, mainMenu, selectMenu);
        
        scene = new Scene(root);
    }

    /**
     * Get the scene.
     * @return the scene attribut
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Get the main menu.
     * @return field mainMenu.
     */
    public MainMenu getMainMenu() {
        return mainMenu;
    }
}
