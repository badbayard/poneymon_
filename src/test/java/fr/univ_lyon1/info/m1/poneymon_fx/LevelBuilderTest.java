package fr.univ_lyon1.info.m1.poneymon_fx;


import fr.univ_lyon1.info.m1.poneymon_fx.model.LevelBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class LevelBuilderTest {

    private LevelBuilder lvBuild;

    @Before
    public void setUp () {
        lvBuild = new LevelBuilder();
    }

    @Test
    public void readTestFile() {
        File file = lvBuild.chooseRandomLevelFile();
        System.out.print("lecture du fichier : \n");
        assert(lvBuild.readFile(file));
    }


    @Test
    public void readTestFileThatNotExist()  {
        String path = lvBuild.getRepLevels().getFilePath();
        File file = new File (path + "/definitlyNotExist.txt");
        System.out.print("lecture du fichier : \n");
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
        System.out.println( match +"% de chance de tomber sur le meme fichier");
    }

}
