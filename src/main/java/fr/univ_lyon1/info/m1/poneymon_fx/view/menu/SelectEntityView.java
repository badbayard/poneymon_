package fr.univ_lyon1.info.m1.poneymon_fx.view.menu;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import fr.univ_lyon1.info.m1.poneymon_fx.view.menu.ButtonMenu;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class allowing the player to select a poney considering different entitys.
 */
public class SelectEntityView extends Parent {

    //Ajouter une VBox par categorie (faire un tableau de VBox ne fonctionne pas)
    private VBox ponyBox;
    private VBox ponyCloneBox;
    private VBox buttonBox;

    private AssetsRepertories assetsRepertories;
    private String path;
    private String[] availableEntity;
    private String[] entityColor;

    final ToggleGroup group;
    private String selectedEntity;

    private String type;
    private String color;

    private ButtonMenu btnConfirm;
    private ButtonMenu btnBack;

    /**
     * Constructor.
     *
     * @param x coord
     * @param y coord
     */
    SelectEntityView(int x, int y) {
        path = System.getProperty("user.dir") + "/src/main/resources/assets/entity/moving";

        assetsRepertories = new AssetsRepertories(path);
        availableEntity = assetsRepertories.availableEntities();

        ponyBox = new VBox(10);
        ponyCloneBox = new VBox(10);
        buttonBox = new VBox(10);

        group = new ToggleGroup();

        ponyBox.setTranslateX(x / 8);
        ponyBox.setTranslateY(y / 6);

        ponyCloneBox.setTranslateX(x / 3);
        ponyCloneBox.setTranslateY(y / 6);

        buttonBox.setTranslateX(x / 2);
        buttonBox.setTranslateY(y / 8);

        btnConfirm = new ButtonMenu("Confirm");
        btnConfirm.setDisable(true);
        btnBack = new ButtonMenu("Back");

        buttonBox.getChildren().addAll(btnConfirm, btnBack);

        for (String anAvailableEntity : availableEntity) {
            entityColor = assetsRepertories.searchAndFilter(anAvailableEntity
                + "-[a-zA-Z]*(.gif)");

            for (String anEntityColor : entityColor) {
                Image entityImage = new Image("assets/entity/moving/"
                    + anAvailableEntity + "-" + anEntityColor + ".gif");
                ImageView imageView = new ImageView(entityImage);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);

                ToggleButton newButton = new ToggleButton("", imageView);
                newButton.setToggleGroup(group);

                newButton.setUserData(anAvailableEntity + "-" + anEntityColor + ".gif");

                switch (anAvailableEntity) {
                    case "pony":
                        ponyBox.getChildren().add(newButton);
                        break;
                    case "ponyClone":
                        ponyCloneBox.getChildren().add(newButton);
                        break;
                    default:
                        System.out.println("Erreur, ce type n'existe pas. "
                            + "Modifier le fichier SelectPoneyCmd.java");
                        break;
                }
            }
        }

        Text text = new Text(x / 8, y / 8, "Select your player !");
        text.setFont(Font.font("Verdana", 20));
        text.setFill(Color.BLUE);

        //Penser à ajouter les VBox lorsque l'on ajoute un nouveau type
        getChildren().addAll(ponyBox, ponyCloneBox, buttonBox, text);
        setEvent();
    }

    /**
     * Set the events for when the user clic on an Entity.
     */
    private void setEvent() {
        group.selectedToggleProperty().addListener((ov, toggle, newToggle) -> {
            if (newToggle != null) {
                selectedEntity = newToggle.getUserData().toString();
                btnConfirm.setDisable(false);
                setColor();
                setType();
            }
        });
    }

    /**
     * Getters of the selectedEntity.
     *
     * @return the field selectedEntity
     */
    public String getSelectedEntity() {
        return selectedEntity;
    }

    /**
     * Getters of the type.
     *
     * @return field type
     */
    public String getType() {
        return type;
    }

    /**
     * Getters of the color.
     *
     * @return field color
     */
    public String getColor() {
        return color;
    }

    /**
     * Setters of the type.
     */
    public void setType() {
        type = assetsRepertories.getEntityName(selectedEntity);
    }

    /**
     * Setters of the color.
     */
    public void setColor() {
        color = assetsRepertories.getEntityColor(selectedEntity);
    }

    /**
     * Getters of the button confirm.
     *
     * @return field btnConfirm
     */
    public ButtonMenu getBtnConfirm() {
        return btnConfirm;
    }

    /**
     * Getters of the button back.
     *
     * @return field btnBack
     */
    public ButtonMenu getBtnBack() {
        return btnBack;
    }
}
