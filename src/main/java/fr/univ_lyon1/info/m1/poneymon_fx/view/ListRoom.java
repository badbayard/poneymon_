package fr.univ_lyon1.info.m1.poneymon_fx.view;

import javafx.scene.Parent;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

public class ListRoom extends Parent {
    private VBox listroom;
    private ButtonMenu btnjoin;
    private ButtonMenu btnhost;
    private ButtonMenu btnrefresh;
    private RadioButton essai1;
    private RadioButton essai2;
    private RadioButton essai3;

    public ListRoom(int x, int y) {
        listroom = new VBox(10);
        listroom.setTranslateX(x / 10);
        listroom.setTranslateY(y / 11);

        btnjoin = new ButtonMenu("join");
        btnhost = new ButtonMenu("host");
        btnrefresh = new ButtonMenu("refresh");

        essai1 = new RadioButton("essai1");
        essai2 = new RadioButton("essai2");
        essai3 = new RadioButton("essai3");

        listroom.getChildren().addAll(btnjoin, btnhost, btnrefresh, essai1, essai2, essai3);

        getChildren().addAll(listroom);

    }

    /**
     * Get the button join.
     *
     * @return field btnSolo;
     */
    public ButtonMenu getBtnJoin() {
        return btnjoin;
    }

    /**
     * Get the button play.
     *
     * @return field btnhost;
     */
    public ButtonMenu getBtnHost() {
        return btnhost;
    }

    /**
     * Get the button play.
     *
     * @return field refresh
     */
    public ButtonMenu getBtnRefresh() {
        return btnrefresh;
    }

}
