package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class AssetsRepertoriesTest {

    private AssetsRepertories repAsset;


    @Before
    public void setUp () {
        final String testPath = System.getProperty("user.dir") + "/src/main/resources/assets";
        repAsset = new AssetsRepertories(testPath);
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
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        repAsset.filter(regexFilter);
        assert(repAsset.getFiles().length == 5);
    }

    @Test
    public void testColor () {
        repAsset.browseAssets();
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        repAsset.filter(regexFilter);


        String [] colorsTest = repAsset.filterColor(repAsset.getFiles());
        String [] colorsExpected = new String[] {"green", "blue", "orange", "purple", "yellow"};
        assert(allElementsAreInTab(colorsTest,colorsExpected));

    }

    @Test
    public void testAndFilter () {
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        String [] colorsTest = repAsset.searchAndFilter(regexFilter);
        String [] colorsExpected = new String[] {"green", "blue", "orange", "purple", "yellow"};
        assert(allElementsAreInTab(colorsTest,colorsExpected));
        System.out.println("apres filtre simple");
        repAsset.display();


        regexFilter = "pony-[a-zA-Z]*-rainbow.gif";
        colorsTest = repAsset.searchAndFilter(regexFilter);
        colorsExpected = new String[] {"yellow", "blue", "green", "purple", "orange"};
        assert(allElementsAreInTab(colorsTest,colorsExpected));
        System.out.println("\napres filtre rainbow");
        repAsset.display();

    }

    /**
     * verifie que tous les elements du premier elements sont present dans le second element (mais pas forcement dans le meme ordre).
     * @param colorsTest colorsTest
     * @param colorsExpected colorsTestExpected
     * @return boolean
     */
    private boolean allElementsAreInTab (String [] colorsTest , String [] colorsExpected) {
        int colorCount = 0;
        for (String colorTest : colorsTest) {
            for (String colorExpect : colorsExpected) {
                if (colorTest.equals(colorExpect)) {
                    colorCount ++;
                    break;
                }
            }
        }
        return ((colorsTest.length == colorsExpected.length)
            && (colorCount == colorsExpected.length));

    }



}
