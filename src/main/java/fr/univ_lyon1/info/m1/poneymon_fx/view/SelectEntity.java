package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Toggle;
import javafx.scene.image.ImageView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Class allowing the player to select a poney considering different entitys.
 */
public class SelectEntity extends Parent {

    //Ajouter une VBox par categorie (faire un tableau de VBox ne fonctionne pas)
    private VBox ponyBox;
    private VBox ponyCloneBox;

    private AssetsRepertories assetsRepertories;
    private String path;
    private String[] availableEntity;
    private String[] entityColor;

    final ToggleGroup group;
    private String selectedEntity;

    /**
     * Constructor.
     *
     * @param x coord
     * @param y coord
     */
    public SelectEntity(int x, int y) {
        path = System.getProperty("user.dir") + "/src/main/resources/assets/entity";

        assetsRepertories = new AssetsRepertories(path);
        availableEntity = assetsRepertories.availableEntities();

        ponyBox = new VBox(10);
        ponyCloneBox = new VBox(10);

        group = new ToggleGroup();

        ponyBox.setTranslateX(x / 8);
        ponyBox.setTranslateY(y / 6);

        ponyCloneBox.setTranslateX(x / 3);
        ponyCloneBox.setTranslateY(y / 6);

        for (String anAvailableEntity : availableEntity) {
            entityColor = assetsRepertories.searchAndFilter(anAvailableEntity + "-[a-zA-Z]*(.gif)");

            for (String anEntityColor : entityColor) {
                Image entityImage = new Image("assets/entity/" + anAvailableEntity + "-" + anEntityColor + ".gif");
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
                        System.out.println("Erreur, ce type n'existe pas. Modifier le fichier SelectPoney.java");
                        break;
                }
            }
        }

        //Penser à ajouter les VBox lorsque l'on ajoute un nouveau type
        getChildren().addAll(ponyBox, ponyCloneBox);
        setEvent();
    }

    /**
     * Set the events for when the user clic on an Entity.
     */
    private void setEvent() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == null) {
                    System.out.println("No toggle selected ?");
                } else {
                    selectedEntity = new_toggle.getUserData().toString();
                }
                System.out.println("Selected entity: " + selectedEntity);

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
}
