package fr.univ_lyon1.info.m1.poneymon_fx;

import fr.univ_lyon1.info.m1.poneymon_fx.model.AssetsRepertories;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

public class AssetsRepertoriesTest {

    private AssetsRepertories repAsset;

    /**
     * Setting up for tests.
     */
    @Before
    public void setUp() {
        final String testPath = System.getProperty("user.dir")
                + "/src/main/resources/assets/entity/moving";
        repAsset = new AssetsRepertories(testPath);
    }

    @Test
    public void testPathValid() {
        File fileWithClass = new File(repAsset.getFilePath());
        assert (fileWithClass.isDirectory());
        assert (fileWithClass.exists());
    }

    @Test
    public void testFilter() {
        repAsset.browseAssets();
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        repAsset.filter(regexFilter);
        assert (repAsset.getFiles().length == 5);
    }

    @Test
    public void testColor() {
        repAsset.browseAssets();
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        repAsset.filter(regexFilter);

        String[] colorsTest = repAsset.filterColor(repAsset.getFiles());
        String[] colorsExpected = new String[] { "green", "blue", "orange", "purple", "yellow" };
        assert (repAsset.allElementsAreInTab(colorsTest, colorsExpected));

    }

    @Test
    public void testAndFilter() {
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        String[] colorsTest = repAsset.searchAndFilter(regexFilter);
        String[] colorsExpected = new String[] { "green", "blue", "orange", "purple", "yellow" };
        assert (repAsset.allElementsAreInTab(colorsTest, colorsExpected));

        regexFilter = "pony-[a-zA-Z]*-rainbow.gif";
        colorsTest = repAsset.searchAndFilter(regexFilter);
        colorsExpected = new String[] { "yellow", "blue", "green", "purple", "orange" };
        assert (repAsset.allElementsAreInTab(colorsTest, colorsExpected));

    }

    @Test
    public void testListAllEntitiesInAssets() {

        String[] entities = repAsset.availableEntities();
        String[] expectedEntities = { "pony", "ponyClone" };

        // for (String s : entities) System.out.println(s);

        assert (repAsset.allElementsAreInTab(entities, expectedEntities));
    }

    @Test
    public void testCleanseDoubleFamilyNameNormalOrder() {
        File[] files = { new File(repAsset.getFilePath() + "/ponyClone-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-green.gif"),
            new File(repAsset.getFilePath() + "/pony-blue.gif"),
            new File(repAsset.getFilePath() + "/pony-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-purple.gif"),
            new File(repAsset.getFilePath() + "/pony-purple.gif"),
            new File(repAsset.getFilePath() + "/pony-yellow.gif") };
        repAsset.setFiles(files);
        repAsset.cleanseDoubleFamilyName();

        File[] filesExpected = { new File(repAsset.getFilePath() + "/ponyClone-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-green.gif") };

        assert (repAsset.allFilesAreInTab(filesExpected));
    }

    @Test
    public void testCleanseDoubleFamilyNameInvertOrder() {

        File[] files = { new File(repAsset.getFilePath() + "/ponyClone-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-green.gif"),
            new File(repAsset.getFilePath() + "/pony-blue.gif"),
            new File(repAsset.getFilePath() + "/pony-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-purple.gif"),
            new File(repAsset.getFilePath() + "/pony-purple.gif"),
            new File(repAsset.getFilePath() + "/pony-yellow.gif") };
        repAsset.setFiles(files);
        repAsset.cleanseDoubleFamilyName();

        File[] filesExpected = { new File(repAsset.getFilePath() + "/pony-green.gif"),
            new File(repAsset.getFilePath() + "/ponyClone-orange.gif") };

        assert (repAsset.allFilesAreInTab(filesExpected));
    }

    @Test
    public void testCleanseDoubleFamilyNameNoDouble() {
        File[] filesForRep = { new File(repAsset.getFilePath() + "/pony-green.gif"),
            new File(repAsset.getFilePath() + "/ponyClone-orange.gif") };
        repAsset.setFiles(filesForRep);
        repAsset.cleanseDoubleFamilyName();

        assert (repAsset.allFilesAreInTab(filesForRep));
    }

    @Test
    public void testGetUrlExists() {
        String url = repAsset.getUrl("pony", "blue", "running");
        String urlExpected = repAsset.getFilePath() + "/pony-blue-running.gif";

        assert (url.equals(urlExpected));
    }

    @Test
    public void testGetUrlNotExists() {
        String url = repAsset.getUrl("ponyUnknown", "black", "whoAmI");
        String urlExpected = repAsset.getFilePath();

        assert (url.equals(urlExpected));
    }

    @Test
    public void allFilesAreInTabTest() {
        repAsset.browseAssets();
        File[] filesNotExpected;

        // files not valid
        filesNotExpected = new File[] { new File(repAsset.getFilePath() + "/pony-green.gif"),
            new File(repAsset.getFilePath() + "/ponyClone-orange.gif") };

        assert (!repAsset.allFilesAreInTab(filesNotExpected));

        filesNotExpected = new File[] {};
        assert (!repAsset.allFilesAreInTab(filesNotExpected));

        // files valid
        String filter = "ponyClone-orange.gif";
        repAsset.filter(filter);

        File[] filesExpected;
        filesExpected = new File[] { new File(repAsset.getFilePath() + "/ponyClone-orange.gif") };

        assert (repAsset.allFilesAreInTab(filesExpected));

        filter = "(.)*-orange.gif";
        repAsset.browseAssets();
        repAsset.filter(filter);
        filesExpected = new File[] { new File(repAsset.getFilePath() + "/ponyClone-orange.gif"),
            new File(repAsset.getFilePath() + "/pony-orange.gif") };

        assert (repAsset.allFilesAreInTab(filesExpected));

        filesExpected = new File[] { new File(repAsset.getFilePath() + "/pony-orange.gif"),
            new File(repAsset.getFilePath() + "/ponyClone-orange.gif") };
        assert (repAsset.allFilesAreInTab(filesExpected));

    }

    @Test
    public void allElementsAreInTabTest() {
        String[] stringToTest = { "1", "2", "3", "4", "5" };
        String[] stringNotValid;

        // cas des tests valides
        stringNotValid = new String[] { "2", "2", "2", "3", "4", "5" };
        assert (!repAsset.allElementsAreInTab(stringToTest, stringNotValid));

        stringNotValid = new String[] { "1", "2", "2", "4", "5" };
        ;
        assert (!repAsset.allElementsAreInTab(stringToTest, stringNotValid));

        String[] stringValid;
        // cas des tests non valides
        stringValid = new String[] { "1", "2", "3", "4", "5" };
        assert (repAsset.allElementsAreInTab(stringToTest, stringValid));

        stringValid = new String[] { "5", "4", "3", "2", "1" };
        assert (repAsset.allElementsAreInTab(stringToTest, stringValid));
    }

    @Test
    public void getEntityColorTest() {
        String color = repAsset.getEntityColor("ponyClone-orange.gif");
        assert (color.equals("orange"));
    }

    @Test
    public void getEntityColorTestNotValidString() {
        String color = repAsset.getEntityColor("ponyClone-orange");
        assert (color.equals(""));
    }

    @Test
    public void getEntityNameTest() {
        String name = repAsset.getEntityName("ponyClone-orange.gif");
        assert (name.equals("ponyClone"));
    }

    @Test
    public void getEntityNameTestNotValidString() {
        String name = repAsset.getEntityName("ponyClone-orange");
        assert (name.equals(""));
    }

    @Test
    public void getEntityOptionTest() {
        String option = repAsset.getEntityOption("pony-blue-rainbow.gif");
        assert (option.equals("rainbow"));
    }

    @Test
    public void getEntityOptionTestNotValidString() {
        String option = repAsset.getEntityOption("pony-blue-rainbow");
        assert (option.equals(""));
    }

}
