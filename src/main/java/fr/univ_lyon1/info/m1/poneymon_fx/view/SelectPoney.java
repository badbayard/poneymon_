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
public class SelectPoney extends Parent {
    
    //Ajouter une VBox par categorie (faire un tableau de VBox ne fonctionne pas)
    private VBox ponyBox;
    private VBox ponyCloneBox;
    
    private AssetsRepertories assetsRepertories;
    private String path;
    private String [] availableEntity;
    private String [] entityColor;

    /**
     * Constructor.
     *
     * @param x coord
     * @param y coord
     */
    public SelectPoney(int x, int y) {       
        path = System.getProperty("user.dir") + "/src/main/resources/assets/entity";
        
        assetsRepertories  = new AssetsRepertories(path);
        availableEntity = assetsRepertories.availableEntities();
        
        ponyBox = new VBox(10);
        ponyCloneBox = new VBox(10);
        
        final ToggleGroup group = new ToggleGroup();
        
        ponyBox.setTranslateX(x / 8);
        ponyBox.setTranslateY(y / 6);
        
        ponyCloneBox.setTranslateX(x / 3);
        ponyCloneBox.setTranslateY(y / 6);
        
        for (int i = 0; i < availableEntity.length; ++i) {
            entityColor = assetsRepertories.searchAndFilter(availableEntity[i] + "-[a-zA-Z]*(.gif)");
            System.out.println(availableEntity[i]);

            for (int j = 0; j < entityColor.length; ++j) {
                Image entityImage = new Image("assets/entity/" + availableEntity[i] + "-" + entityColor[j] + ".gif");
                ImageView imageView = new ImageView(entityImage);
                imageView.setFitWidth(75);
                imageView.setFitHeight(75);

                ToggleButton newButton = new ToggleButton("", imageView);
                newButton.setToggleGroup(group);
                
                newButton.setUserData(availableEntity[i] + "-" + entityColor[j] + ".gif");

                System.out.println("J : " + j + " ," + entityColor[j]);
                
                switch (availableEntity[i]) {
                case "pony" :
                    ponyBox.getChildren().add(newButton);
                    break;
                case "ponyClone" :
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
        
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    if (new_toggle == null)
                        System.out.println("No toggle selected ?"); 
                    else
                        System.out.println("Toggle selected: " + new_toggle.getUserData());
                    
                 }
        });
    }
}
