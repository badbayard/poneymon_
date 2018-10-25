package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import org.junit.Before;
import org.junit.Test;

import java.io.File;


public class AssetsRepertoriesTest {

    private AssetsRepertories repAsset;


    @Before
    public void setUp () {
        final String testPath = System.getProperty("user.dir") + "/src/main/resources/assets/entity";
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



        regexFilter = "pony-[a-zA-Z]*-rainbow.gif";
        colorsTest = repAsset.searchAndFilter(regexFilter);
        colorsExpected = new String[] {"yellow", "blue", "green", "purple", "orange"};
        assert(allElementsAreInTab(colorsTest,colorsExpected));

    }

    /**
     * verifie que tous les elements du premier elements sont present dans le second element (mais pas forcement dans le meme ordre).
     * @param colorsTest colorsTest
     * @param colorsExpected colorsTestExpected
     * @return boolean
     */
    private boolean allElementsAreInTab (String [] colorsTest , String [] colorsExpected) {
        int colorCount = 0;
        for (String strTest : colorsTest) {
            for (String strExpect : colorsExpected) {
                if (strTest.equals(strExpect)) {
                    colorCount ++;
                    break;
                }
            }
        }
        return ((colorsTest.length == colorsExpected.length)
            && (colorCount == colorsExpected.length));

    }


    /**
     * verifie que tous les elements du premier elements sont present dans le second element (mais pas forcement dans le meme ordre).
     * @param FilesToTest tableau de fichiers a tester
     * @param FilesExpected tableau de fichiers attendu
     * @return boolean
     */
    private boolean allFilesAreInTab (File [] FilesToTest , File [] FilesExpected) {
        int colorCount = 0;
        for (File fiToTest : FilesToTest) {
            for (File fiExpect : FilesExpected) {
                if (fiToTest.equals(fiExpect)) {
                    colorCount ++;
                    break;
                }
            }
        }
        return ((FilesToTest.length == FilesExpected.length)
            && (colorCount == FilesExpected.length));

    }



    @Test
    public void TestListAllEntitiesInAssets() {

        String[] entities = repAsset.availableEntities();
        String[] expectedEntities = {"pony","ponyClone"};

        //for (String s : entities) System.out.println(s);

        assert(allElementsAreInTab(entities,expectedEntities));
    }


    @Test
    public void TestCleanseDoubleFamilyName() {
        String filter = "(.)*-(.)*.gif";
        repAsset.browseAssets();
        repAsset.filter(filter);
        repAsset.cleanseDoubleFamilyName();

        File [] FilesExpected = {new File (repAsset.getFilePath() + "/pony-green.gif"),
            new File (repAsset.getFilePath() + "/ponyClone-orange.gif")};

        assert(allFilesAreInTab(repAsset.getFiles(),FilesExpected));
    }

    @Test
    public void TestGetUrlExists() {
        String url = repAsset.getUrl("pony","blue","running");
        String urlExpected = repAsset.getFilePath() + "/pony-blue-running.gif";

        assert(url.equals(urlExpected));
    }

    @Test
    public void TestGetUrlNotExists() {
        String url = repAsset.getUrl("ponyUnknown","black","whoAmI");
        String urlExpected = repAsset.getFilePath();

        assert(url.equals(urlExpected));
    }

}
