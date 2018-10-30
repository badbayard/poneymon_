package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import fr.univ_lyon1.info.m1.poneymon_fx.view.MainMenu;

/**
 * Class handling the communication between the menus and jfxView.
 */
public class MenuView {

    private MainMenu mainMenu;
    private SelectEntity selectMenu;
    private ListRoom listroom;
    private Scene scene;
    private int width;
    private int height;

    private boolean isSolo;

    /**
     * Constructor of MenuView.
     */
    public MenuView(int x, int y) {

        isSolo = false;

        width = x;
        height = y;

        // Creation de la fenêtre.
        Pane root = new Pane();
        root.setPrefSize(width, height);

        // Chargement du background.
        Image background = new Image("assets/bgMenu.jpg");

        // Adapte l'image a la taille de la fenêtre
        ImageView imgView = new ImageView(background);
        imgView.setFitWidth(width);
        imgView.setFitHeight(height);

        // Affiche le menu principal
        mainMenu = new MainMenu(width, height);
        mainMenu.setVisible(true);

        // Prépare le menu de sélection de poney
        selectMenu = new SelectEntity(width, height);
        selectMenu.setVisible(false);

        // Prépare la listroom
        listroom = new ListRoom(width, height);
        listroom.setVisible(false);

        root.getChildren().addAll(imgView, mainMenu, selectMenu, listroom);

        scene = new Scene(root);
    }

    /**
     * Get the scene.
     * 
     * @return the scene attribut
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Get the main menu.
     * 
     * @return field mainMenu.
     */
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    /**
     * Get the select menu.
     * 
     * @return field selectMenu;
     */
    public SelectEntity getSelectMenu() {
        return selectMenu;
    }

    /**
     * Get the listroom menu
     * 
     * @return field listroom;
     */
    public ListRoom getListroom() {
        return listroom;
    }

    /**
     * Display the select menu.
     */
    public void activateSelectMenu() {
        mainMenu.setVisible(false);
        selectMenu.setVisible(true);
    }

    /**
     * Display the main menu.
     */
    public void backToMainMenu() {
        mainMenu.setVisible(true);
        selectMenu.setVisible(false);
    }

    /**
     * Display the Listroom.
     */
    public void activateSelectlistroom() {
        mainMenu.setVisible(false);
        listroom.setVisible(true);
    }

    /**
     * Display the main menu from listroom.
     */
    public void backToMainMenu2() {
        mainMenu.setVisible(true);
        listroom.setVisible(false);
    }

    /**
     * Get field indicating wether a game is solo or multiplayer.
     * 
     * @return isSolo
     */
    public boolean getIsSolo() {
        return isSolo;
    }

    /**
     * Set the field isSolo to the new value.
     * 
     * @param newValue true if solo, false otherwise
     */
    public void setIsSolo(boolean newValue) {
        isSolo = newValue;
    }
}
