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
        assert(repAsset.allElementsAreInTab(colorsTest,colorsExpected));

    }

    @Test
    public void testAndFilter () {
        String regexFilter = "pony-[a-zA-Z]*(.gif)";
        String [] colorsTest = repAsset.searchAndFilter(regexFilter);
        String [] colorsExpected = new String[] {"green", "blue", "orange", "purple", "yellow"};
        assert(repAsset.allElementsAreInTab(colorsTest,colorsExpected));



        regexFilter = "pony-[a-zA-Z]*-rainbow.gif";
        colorsTest = repAsset.searchAndFilter(regexFilter);
        colorsExpected = new String[] {"yellow", "blue", "green", "purple", "orange"};
        assert(repAsset.allElementsAreInTab(colorsTest,colorsExpected));

    }

    @Test
    public void TestListAllEntitiesInAssets() {

        String[] entities = repAsset.availableEntities();
        String[] expectedEntities = {"pony","ponyClone"};

        //for (String s : entities) System.out.println(s);

        assert(repAsset.allElementsAreInTab(entities,expectedEntities));
    }


    @Test
    public void TestCleanseDoubleFamilyName() {
        String filter = "(.)*-(.)*.gif";
        repAsset.browseAssets();
        repAsset.filter(filter);
        repAsset.cleanseDoubleFamilyName();

        File [] FilesExpected = {new File (repAsset.getFilePath() + "/pony-green.gif"),
            new File (repAsset.getFilePath() + "/ponyClone-orange.gif")};

        assert(repAsset.allFilesAreInTab(FilesExpected));
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

    @Test
    public void allFilesAreInTabTest() {
        repAsset.browseAssets();

        File [] FilesNotExpected = {new File (repAsset.getFilePath() + "/pony-green.gif"),
            new File (repAsset.getFilePath() + "/ponyClone-orange.gif")};

        assert(!repAsset.allFilesAreInTab(FilesNotExpected));

        String filter = "ponyClone-orange.gif";
        repAsset.filter(filter);
        File [] FilesExpected =  {new File (repAsset.getFilePath() + "/ponyClone-orange.gif")};

        assert(repAsset.allFilesAreInTab(FilesExpected));

    }

    @Test
    public void allElementsAreInTabTest() {
        String [] stringToTest = {"1","2","3","4","5"};
        String [] stringNotValid;
        String [] stringValid;

        //cas des tests valides
        stringNotValid = new String[] {"2", "2", "2", "3", "4", "5"};
        assert(!repAsset.allElementsAreInTab(stringToTest, stringNotValid));

        stringNotValid = new String[] {"1","2","2","4","5"};;
        assert(!repAsset.allElementsAreInTab(stringToTest,stringNotValid));


        //cas des tests non valides
        stringValid = new String[] {"1", "2", "3", "4", "5"};
        assert(repAsset.allElementsAreInTab(stringToTest, stringValid));

        stringValid = new String[] {"5", "4", "3", "2", "1"};
        assert(repAsset.allElementsAreInTab(stringToTest, stringValid));
    }

}
