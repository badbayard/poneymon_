package fr.univ_lyon1.info.m1.poneymon_fx.view.menu;

import fr.univ_lyon1.info.m1.poneymon_fx.network.command.JoinWaitingRoomCmd;
import fr.univ_lyon1.info.m1.poneymon_fx.network.communication_system.CommunicationSystem;
import fr.univ_lyon1.info.m1.poneymon_fx.network.room.WaitingRoom;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ListRoomView extends Parent {
    private VBox listroom;
    private ButtonMenu btnhost;
    private ButtonMenu btnrefresh;
    private ButtonMenu btnback;
    private GridPane waitingRooms;

    /**
     * Constructor.
     *
     * @param x int
     * @param y int
     */
    public ListRoomView(int x, int y) {
        listroom = new VBox(10);
        listroom.setTranslateX(x / 8);
        listroom.setTranslateY(y / 6);

        waitingRooms = new GridPane();

        btnhost = new ButtonMenu("host");
        btnrefresh = new ButtonMenu("refresh");
        btnback = new ButtonMenu("back");

        listroom.getChildren().addAll(btnhost, btnrefresh, btnback, waitingRooms);

        getChildren().addAll(listroom);
    }

    public List<HBox> setWaitingRooms(List<WaitingRoom> waitingRoomsList) {
        waitingRooms.getChildren().clear();
        List<HBox> roomBoxes = new ArrayList<>();

        HBox emptySpace = new HBox();
        emptySpace.setMinHeight(30);
        waitingRooms.add(emptySpace, 0, 0);

        for (int i = 0; i < waitingRoomsList.size(); i++) {
            WaitingRoom wr = waitingRoomsList.get(i);

            Text roomNameText = new Text(wr.getName());
            TextField passwordField = new TextField();
//            ButtonMenu joinBtn = new ButtonMenu("join");
            Button joinBtn = new Button("join");

            HBox container = new HBox();
            container.getChildren().add(roomNameText);
            container.getChildren().add(passwordField);
            container.getChildren().add(joinBtn);
            waitingRooms.add(container, 0, i + 1);

            roomBoxes.add(container);
        }

        return roomBoxes;
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

    public ButtonMenu getBtnBack() {
        return btnback;
    }

}
