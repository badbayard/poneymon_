package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ListRoomView {

    private ListRoom listroom;
    private Scene scene2;
    private int width;
    private int height;

    /**
     * Constructor of MenuView.
     */
    public ListRoomView(int x, int y) {

        width = x;
        height = y;

        // Creation de la fenêtre.
        Pane root = new Pane();
        root.setPrefSize(width, height);

        // Chargement du background.
        Image background = new Image("assets/menu/bgMenu.jpg");

        // Adapte l'image a la taille de la fenêtre
        ImageView imgView2 = new ImageView(background);
        imgView2.setFitWidth(width);
        imgView2.setFitHeight(height);

        // Affiche le menu principal
        listroom = new ListRoom(width, height);
        listroom.setVisible(true);

        root.getChildren().addAll(imgView2, listroom);

        scene2 = new Scene(root);
    }

    /**
     * Get the scene.
     * 
     * @return the scene attribut
     */
    public Scene getScene2() {
        return scene2;
    }

    /**
     * Get the main menu.
     * 
     * @return field mainMenu.
     */
    public ListRoom getListRoom() {
        return listroom;
    }
}
