package fr.univ_lyon1.info.m1.poneymon_fx;


import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LevelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilderTest {

    private LevelBuilder lvBuild;

    @Before
    public void setUp () {
        lvBuild = new LevelBuilder();
    }

    @Test
    public void readTestFile() {
        File file = lvBuild.chooseRandomLevelFile();
        System.out.print("lecture du fichier : " + file.getName() +" \n");
        assert(lvBuild.readFile(file));
    }

    @Test
    public void readEmptyFile() {
        String path = lvBuild.getRepLevels().getFilePath();
        File file =  new File (path + "/emptyFile");
        System.out.print("lecture du fichier vide : " + file.getName() +" \n");
        assert(lvBuild.readFile(file));
    }


    @Test
    public void readTestFileThatNotExist()  {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File (path + "/definitelyNotExists");
        System.out.print("lecture du fichier inexistant : " + file.getName() +" \n");
        assert(!lvBuild.readFile(file));
    }

    @Test
    public void randomFile() {

        LevelBuilder TesterBuilder;
        TesterBuilder = new LevelBuilder();
        File ourFile;
        File testerFile;
        int match = 0;

        for (int i = 0 ; i < 100 ; i ++) {
            ourFile = lvBuild.chooseRandomLevelFile();
            testerFile = TesterBuilder.chooseRandomLevelFile();
            if(ourFile.equals(testerFile)) {
                match++;
            }
        }
        assert(match < 100);
        System.out.println( match +"% de chances de tomber sur le meme fichier");
    }

    @Test
    public void buildFixedEntityTabOnReadingEmptyFile() {
        List<FixedEntityModel> ExpectedFixedEntities = new ArrayList<>();
        String path = lvBuild.getRepLevels().getFilePath();
        File fileValid = new File (path + "/emptyFile");
        lvBuild.readFile(fileValid);

        assert(lvBuild.fixedEntitiesEquals(ExpectedFixedEntities));
    }

    @Test
    public void buildFixedEntityTabOnReadingValidFile() {
        List<FixedEntityModel> ExpectedFixedEntities = new ArrayList<>();
        String path = lvBuild.getRepLevels().getFilePath();
        File fileValid = new File (path + "/levelTest");
        lvBuild.readFile(fileValid);


        ExpectedFixedEntities.add(new FixedEntityModel(3, 0.1));
        ExpectedFixedEntities.add(new FixedEntityModel(4, 0.3));
        ExpectedFixedEntities.add(new FixedEntityModel(2, 0.6));
        ExpectedFixedEntities.add(new FixedEntityModel(0, 0.8));
        ExpectedFixedEntities.add(new FixedEntityModel(3, 1.1));
        ExpectedFixedEntities.add(new FixedEntityModel(4, 2.3));
        ExpectedFixedEntities.add(new FixedEntityModel(2, 3.6));
        ExpectedFixedEntities.add(new FixedEntityModel(0, 4.8));


        /**
         mur 0.1 3 0
         mur 0.3 4 0
         mur 0.6 2 0
         mur 0.8 0 0
         mur 0.1 3 1
         mur 0.3 4 2
         mur 0.6 2 3
         mur 0.8 0 4
         */

        assert (lvBuild.fixedEntitiesEquals(ExpectedFixedEntities));

    }

}
