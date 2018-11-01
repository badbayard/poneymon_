package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.FixedEntityModel;
import fr.univ_lyon1.info.m1.poneymon_fx.model.LevelBuilder;
import fr.univ_lyon1.info.m1.poneymon_fx.model.ObstacleModel;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LevelBuilderTest {

    private LevelBuilder lvBuild;

    @Before
    public void setUp() {
        lvBuild = new LevelBuilder();
    }

    @Test
    public void readTestFile() {
        File file = lvBuild.chooseRandomLevelFile();
        System.out.print("lecture du fichier : " + file.getName() + " \n");
        assert (lvBuild.readFile(file));
    }


    @Test
    public void readTestFileLevelInvalid() {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File(path + "/levelTestInvalid");
        System.out.print("lecture du fichier : " + file.getName() + " \n");
        assert (lvBuild.readFile(file));
    }


    @Test
    public void readTestFileLevelInvalidTypes() {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File(path + "/levelInvalidTypes");
        System.out.print("lecture du fichier : " + file.getName() + " \n");
        assert (lvBuild.readFile(file));
    }


    @Test
    public void readEmptyFile() {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File(path + "/emptyFile");
        System.out.print("lecture du fichier vide : " + file.getName() + " \n");
        assert (lvBuild.readFile(file));
    }

    @Test
    public void readTestFileThatNotExist() {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File(path + "/definitelyNotExists");
        System.out.print("lecture du fichier inexistant : " + file.getName() + " \n");
        assert (!lvBuild.readFile(file));
    }

    @Test
    public void randomFile() {

        LevelBuilder testerBuilder;
        testerBuilder = new LevelBuilder();
        File ourFile;
        File testerFile;
        int match = 0;

        for (int i = 0; i < 100; i++) {
            ourFile = lvBuild.chooseRandomLevelFile();
            testerFile = testerBuilder.chooseRandomLevelFile();
            if (ourFile.equals(testerFile)) {
                match++;
            }
        }
        assert (match < 100);
        System.out.println(match + "% de chances de tomber sur le meme fichier");
    }

    @Test
    public void buildFixedEntityTabOnReadingEmptyFile() {
        List<FixedEntityModel> expectedFixedEntities = new ArrayList<>();
        String path = lvBuild.getRepLevels().getFilePath();
        File fileValid = new File(path + "/emptyFile");
        lvBuild.readFile(fileValid);

        assert (lvBuild.fixedEntitiesEquals(expectedFixedEntities));
    }

    @Test
    public void buildFixedEntityTabOnReadingValidFileWithValidEntities() {
        List<FixedEntityModel> expectedFixedEntities = new ArrayList<>();
        String path = lvBuild.getRepLevels().getFilePath();
        File fileValid = new File(path + "/levelTest");
        lvBuild.readFile(fileValid);

        expectedFixedEntities.add(new ObstacleModel(3, 0.1, 0));
        expectedFixedEntities.add(new ObstacleModel(4, 0.3, 0));
        expectedFixedEntities.add(new ObstacleModel(2, 0.6, 0));
        expectedFixedEntities.add(new ObstacleModel(0, 0.8, 0));
        expectedFixedEntities.add(new ObstacleModel(3, 0.1, 1));
        expectedFixedEntities.add(new ObstacleModel(4, 0.3, 2));
        expectedFixedEntities.add(new ObstacleModel(2, 0.6, 3));
        expectedFixedEntities.add(new ObstacleModel(0, 0.8, 4));

        /*
         * puddle 0.1 3 0
           fence 0.3 4 0
           fence 0.6 2 0
           puddle 0.8 0 0
           fence 0.1 3 1
           fence 0.3 4 2
           puddle 0.6 2 3
           fence 0.8 0 4
         */

        assert (lvBuild.fixedEntitiesEquals(expectedFixedEntities));

    }

    @Test
    public void buildFixedEntityTabOnReadingValidFileWithInvalidEntities() {
        List<FixedEntityModel> expectedFixedEntities = new ArrayList<>();
        String path = lvBuild.getRepLevels().getFilePath();
        File fileValid = new File(path + "/levelTestInvalid");
        lvBuild.readFile(fileValid);

        assert (lvBuild.fixedEntitiesEquals(expectedFixedEntities));
    }


    @Test
    public void TestObstacleExist(){
        assert(!lvBuild.obstacleExistInRepertories("poney"));
        assert(!lvBuild.obstacleExistInRepertories("Obstacle"));
        assert(!lvBuild.obstacleExistInRepertories("obstacle-puddle"));
        assert(!lvBuild.obstacleExistInRepertories("fence-brown"));

        assert(lvBuild.obstacleExistInRepertories("fence"));
        assert(lvBuild.obstacleExistInRepertories("puddle"));
    }


    @Test
    public void TestObstacleCreator(){
        FixedEntityModel obstacleCreated;
        FixedEntityModel obstacleExpected;

        obstacleCreated = lvBuild.createSpecificObstacle("definitlyNotExist"
            , 1, 0.5 , 0);

        obstacleExpected = new ObstacleModel(1, 0.5,0);

        assert(obstacleCreated.fixedEntityEquals(obstacleExpected));
        //TODO tests des classes existantes (quand elles existeront)


    }

    @Test
    public void AddFixedEntityValidLine() {
        LevelBuilder lvBuildTester = new LevelBuilder();

        lvBuild.addFixedEntity("fence 0 0 0");

        lvBuildTester.getFixedEntities().add(
            lvBuildTester.createSpecificObstacle("fence", 0, 0, 0));


        assert(lvBuild.listFixedEntityModelEquals(lvBuildTester));
    }


    @Test
    public void AddFixedEntityNotValidLine() {

        lvBuild.addFixedEntity("DefinitlyNotValid 0 0 0");


        assert(lvBuild.getFixedEntities().size() == 0);

        lvBuild.addFixedEntity("fence .4!4§6 o/ blahblah");

        assert(lvBuild.getFixedEntities().size() == 0);

    }


}
