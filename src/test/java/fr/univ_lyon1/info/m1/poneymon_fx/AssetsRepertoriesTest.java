package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AssetsRepertoriesTest {

    private AssetsRepertories repAsset;


    @Before
    public void setUp () {
        final String testPath = System.getProperty("user.dir") + "/src/main/resources/assets";
        repAsset = new AssetsRepertories(testPath);
    }


    @Test
    public void displayTest () {
        repAsset.browseAssets();
        repAsset.display();
        assert true;
    }

    @Test
    public void testPathValid() {
        File fileWithClass = new File(repAsset.getFilePath());
        assert (fileWithClass.isDirectory());
        assert(fileWithClass.exists());
    }


    @Test
    public void testFilter() {
        repAsset.browseAssets();
        int nbFiles = repAsset.getFiles().length;
        assert(nbFiles > 0);

        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        List<String> test = repAsset.filter(regexFilter);

        assert(test.size() == 5);

    }

    @Test
    public void testColor () {
        repAsset.browseAssets();
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        List<String> test = repAsset.filter(regexFilter);

        String [] colorsTest = repAsset.filterColor(test);
        String [] colorsExpected= new String[] {"green", "blue", "orange", "purple", "yellow"};

        assertEquals(colorsTest,colorsExpected);

    }

    @Test
    public void testAndFilter () {
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        String [] colorsTest = repAsset.searchAndFilter(regexFilter);

        String [] colorsExpected = new String[] {"green", "blue", "orange", "purple", "yellow"};
        assertEquals(colorsTest,colorsExpected);

        String regexFilterRainbow = "pony-[a-zA-Z]*-rainbow.gif";
        String [] colorsTestRainbow = repAsset.searchAndFilter(regexFilterRainbow);
        colorsExpected = new String[] {"yellow", "blue", "green", "purple", "orange"};
        assertEquals(colorsTestRainbow,colorsExpected);

    }



}
