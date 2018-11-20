package fr.univ_lyon1.info.m1.poneymon_fx.view.menu;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Class handling the communication between the menus and jfxView.
 */
public class MenuView {

    private MainMenuView mainMenuView;
    private SelectEntityView selectMenu;
    private ListRoomView listRoomView;
    private WaitingRoomView waitingRoomView;
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
        
        //Chargement du background.
        Image background = new Image("assets/menu/bgMenu.jpg");
        
        //Adapte l'image a la taille de la fenêtre



        ImageView imgView = new ImageView(background);
        imgView.setFitWidth(width);
        imgView.setFitHeight(height);

        // Affiche le menu principal
        mainMenuView = new MainMenuView(width, height);
        mainMenuView.setVisible(true);

        // Prépare le menu de sélection de poney
        selectMenu = new SelectEntityView(width, height);
        selectMenu.setVisible(false);

        // Prépare la listroom
        listRoomView = new ListRoomView(width, height);
        listRoomView.setVisible(false);
        
        //Prépare la waitingRoomView
        waitingRoomView = new WaitingRoomView(width, height, 4);
        waitingRoomView.setVisible(false);

        root.getChildren().addAll(imgView, mainMenuView, selectMenu, listRoomView, waitingRoomView);

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
     * @return field mainMenuView.
     */
    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    /**
     * Get the select menu.
     * 
     * @return field selectMenu;
     */
    public SelectEntityView getSelectMenu() {
        return selectMenu;
    }

    /**
     * Get the listroom menu.
     * 
     * @return field listroom;
     */
    public ListRoomView getListroom() {
        return listRoomView;
    }

    /**
     * Display the select menu.
     */
    public void activateSelectMenu() {
        mainMenuView.setVisible(false);
        selectMenu.setVisible(true);
    }

    /**
     * Display the main menu.
     */
    public void backToMainMenu() {
        mainMenuView.setVisible(true);
        selectMenu.setVisible(false);
        listRoomView.setVisible(false);
    }

    /**
     * Display the Listroom.
     */
    public void activateSelectlistroom() {
        mainMenuView.setVisible(false);
        listRoomView.setVisible(true);
    }
    
    public void activateWaitingRoom() {
        System.out.println("HEO");
        listRoomView.setVisible(false);
        waitingRoomView.setVisible(true);
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

    /**
     * Get the waiting room.
     * @return field waitingRoomView
     */
    public WaitingRoomView getWaitingRoomView() {
        return waitingRoomView;
    }
    
    /**
     * Display the Listroom.
     */
    public void backToListRoom() {
        waitingRoomView.setVisible(false);
        listRoomView.setVisible(true);
    }
}
