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
        final String testPath = "/home/yann/Documents/M1/multimif/poneymon/src/main/resources/assets";
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

        String [] colorsTest = repAsset.filtreColor(test);

        String [] strAttendu= new String[] {"green", "blue", "orange", "purple", "yellow"};

        assertEquals(colorsTest,strAttendu);

    }

    @Test
    public void testAndFiltre () {
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        String [] colorsTest = repAsset.searchAndFiltre(regexFilter);

        String [] strAttendu= new String[] {"green", "blue", "orange", "purple", "yellow"};
        assertEquals(colorsTest,strAttendu);

        String regexFilterRainbow = "pony-[a-zA-Z]*-rainbow.gif";
        String [] colorsTestRainbow = repAsset.searchAndFiltre(regexFilterRainbow);
        String [] strAttenduRainbow= new String[] {"yellow", "blue", "green", "purple", "orange"};
        assertEquals(colorsTestRainbow,strAttenduRainbow);

    }







}
