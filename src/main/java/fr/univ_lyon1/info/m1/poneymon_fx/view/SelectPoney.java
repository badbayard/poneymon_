package fr.univ_lyon1.info.m1.poneymon_fx.view;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;

/**
 * Class allowing the player to select a poney considering different entitys.
 */
public class SelectPoney extends Parent {

    private VBox poneyBox;
    private AssetsRepertories assetsRepertories;
    private String path;
    private String[] availableEntity;
    private String[] entityColor;

    private VBox[] entityBox;

    /**
     * Constructor.
     *
     * @param x coord
     * @param y coord
     */
    public SelectPoney(int x, int y) {

        path = System.getProperty("user.dir") + "/src/main/resources/assets";

        assetsRepertories = new AssetsRepertories(path);

        //entityName = assetsRepertories.filterNameEntity();
        availableEntity = assetsRepertories.availableEntities();

        //entityColor = new String [availableEntity.length];

        entityBox = new VBox[availableEntity.length];

        for (int i = 0; i < availableEntity.length - 1; ++i) {
            entityBox[i] = new VBox(10);

            entityBox[i].setTranslateX(x / (i + 1));
            entityBox[i].setTranslateY(y / (i + 1));

            entityColor = assetsRepertories.searchAndFilter(availableEntity[i] + "-[a-zA-Z]*(.gif)");
            System.out.println(availableEntity[i]);

            for (int j = 0; j < entityColor.length; ++j) {
                Image entityImage = new Image("assets/" + availableEntity[i] + "-" + entityColor[j] + ".gif");
                ImageView imageView = new ImageView(entityImage);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);

                ToggleButton newButton = new ToggleButton("", imageView);

                System.out.println("J : " + j + " ," + entityColor[j]);

                entityBox[i].getChildren().add(newButton);
            }
            //getChildren().add(entityBox[i]);
        }

        //poneyBox = new VBox(10);

        //poneyBox.setTranslateX(x / 5);
        //poneyBox.setTranslateY(y / 5);
        
        /*
        Image image = new Image("assets/pony-blue.gif");

        ImageView imageV = new ImageView(image);
        imageV.setFitWidth(75);
        imageV.setFitHeight(75);
        ToggleButton tb3 = new ToggleButton("", imageV);
        
        poneyBox.getChildren().addAll(tb3);
        
        getChildren().addAll(poneyBox);*/

        // System.out.println("EntityBox: " + entityBox[0].getChildren());

        // getChildren().addAll(entityBox[0]);
    }
}

// Créer 1 ToggleButton par image récupéré
