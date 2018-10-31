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
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class allowing the player to select a poney considering different entitys.
 */
public class SelectEntity extends Parent {
    
    //Ajouter une VBox par categorie (faire un tableau de VBox ne fonctionne pas)
    private VBox ponyBox;
    private VBox ponyCloneBox;
    private VBox buttonBox;
    
    private AssetsRepertories assetsRepertories;
    private String path;
    private String [] availableEntity;
    private String [] entityColor;
    
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
    public SelectEntity(int x, int y) {       
        path = System.getProperty("user.dir") + "/src/main/resources/assets/entity/moving";
        
        assetsRepertories  = new AssetsRepertories(path);
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
        
        for (int i = 0; i < availableEntity.length; ++i) {
            entityColor = assetsRepertories.searchAndFilter(availableEntity[i] 
                    + "-[a-zA-Z]*(.gif)");
            System.out.println(availableEntity[i]);

            for (int j = 0; j < entityColor.length; ++j) {
                Image entityImage = new Image("assets/entity/moving/"
                        + availableEntity[i] + "-" + entityColor[j] + ".gif");
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
                        System.out.println("Erreur, ce type n'existe pas. "
                                + "Modifier le fichier SelectPoney.java");
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
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle newToggle) {
                    if (newToggle == null) {
                        System.out.println("No toggle selected ?"); 
                    } else {
                        selectedEntity = newToggle.getUserData().toString();
                        System.out.println("Selected entity: " + selectedEntity);
                        btnConfirm.setDisable(false);
                        setColor();
                        setType();
                    }
                }
        });
    }
    
    /**
     * Getters of the selectedEntity.
     * @return the field selectedEntity
     */
    public String getSelectedEntity() {
        return selectedEntity;
    }
    
    /**
     * Getters of the type.
     * @return field type
     */
    public String getType() {
        return type;
    }
    
    /**
     * Getters of the color.
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
     * @return field btnConfirm
     */
    public ButtonMenu getBtnConfirm() {
        return btnConfirm;
    }
    
    /**
     * Getters of the button back.
     * @return field btnBack
     */
    public ButtonMenu getBtnBack() {
        return btnBack;
    }
}
