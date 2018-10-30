package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LevelBuilder {

    private AssetsRepertories repLevels;
    private List<FixedEntityModel> fixedEntities;

    /**
     * constructeur LevelBuilder.
     */
    public LevelBuilder() {
        final String testPath = System.getProperty("user.dir")
            + "/src/main/resources/assets/levels";
        repLevels = new AssetsRepertories(testPath);
        fixedEntities = new ArrayList<>();
    }


    /**
     * choisis un fichier Level au hasard dans le reperoire.
     */
    public File chooseRandomLevelFile() {
        int max;
        repLevels.browseAssets();
        max = repLevels.getFiles().length;
        Random rdm = new Random();
        return repLevels.getFiles()[rdm.nextInt(max)];
    }


    /**
     * Lis le fichier passé en parametre.
     * @param level level
     * @return vrai si le fichier existe et si lecture sans erreurs
     */
    public boolean readFile(File level) {
        try (BufferedReader input = new BufferedReader(new FileReader(level))) {
            String line;
            while ((line = input.readLine()) != null) {
                this.addFixedEntity(line);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * accesseur AssetsRepertories.
     * @return repLevels
     */
    public AssetsRepertories getRepLevels() {
        return repLevels;
    }

    /**
     * Mutateur repLevels.
     * @param newFilesLevel AssetsRepertories
     */
    public void setRepLevels(AssetsRepertories newFilesLevel) {
        repLevels = newFilesLevel;
    }

    /**
     * Ajoute une nouvelle entitee a la liste.
     */
    public void addFixedEntity(String line) {
        String familyName = "";
        double posX = -1.0;
        int posY = -1;
        int nbLap = -1;
        String[] parts;
        parts = line.split(" ");
        for (int i = 0; i < parts.length; i++) {
            switch (i) {
                case 0:
                    try {
                        familyName = parts[i];
                    } catch (Exception e) {
                        return;
                    }
                    break;
                case 1:
                    try {
                        posX = Double.parseDouble(parts[i]);
                    } catch (Exception e) {
                        return;
                    }
                    break;
                case 2:
                    try {
                        posY = Integer.parseInt(parts[i]);
                    } catch (Exception e) {
                        return;
                    }
                    break;
                case 3:
                    try {
                        nbLap = Integer.parseInt(parts[i]);
                    } catch (Exception e) {
                        return;
                    }
                    break;
                default:
                    break;
            }
        }

        if (obstacleExistInRepertories(familyName)) {
            fixedEntities.add(createSpecificObstacle(familyName, posY, posX, nbLap));
        }
    }

    /**
     * Accesseur fixedEntities.
     * @return liste de FixedEntityModel
     */
    public  List<FixedEntityModel>  getFixedEntities() {
        return fixedEntities;
    }

    /**
     * Mutateur fixedEntities.
     * @param newOpponents liste de FixedEntityModel
     */
    public void setFixedEntities(List<FixedEntityModel>  newOpponents) {
        fixedEntities = newOpponents;
    }

    /**
     * egalité si tous les elem sont egaux.
     * @param expectedEntities liste de FixedEntityModel.
     * @return boolean
     */
    public boolean fixedEntitiesEquals(List<FixedEntityModel> expectedEntities) {

        if (this.getFixedEntities().size() != expectedEntities.size()) {
            return false;
        }
        for (int i = 0;i < this.getFixedEntities().size(); i++) {
            if (!this.getFixedEntities().get(i).entityModelEquals(expectedEntities.get(i))) {
                return false;
            }
        }
        return true;

    }

    /**
     * Teste si l'obstacle existe dans le repertoire des obstacles.
     * @param familyName nom de famille de l'obstacle
     * @return Boolean
     */
    public boolean obstacleExistInRepertories(String familyName) {

        final String obstaclePath = System.getProperty("user.dir")
            + "/src/main/resources/assets/entity/fixed";

        AssetsRepertories repObstacles = new AssetsRepertories(obstaclePath);
        repObstacles.browseAssets();
        String[] obsDispo = repObstacles.availableEntities();

        for (String str : obsDispo) {
            if (str.equals(familyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * choisis le bon constructeur en fonction du familyName
     (si le cas n'est pas sp&eacute;cifi&eacute; genere un obstacleModel).
     * @param familyName nom de famille
     * @param posY rang&eacute;e de l'obstacle
     * @param posX position verticale de l'obstacle
     * @param nbLap numero du tour d'apparition de l'obstacle
     */
    public FixedEntityModel createSpecificObstacle(String familyName, int posY,
                                                   double posX, int nbLap) {

        FixedEntityModel obstacle;
        switch (familyName) {
            case "puddle": obstacle = new ObstacleModel(posY, posX, nbLap);
                break;
            case "fence": obstacle = new ObstacleModel(posY, posX, nbLap);
                break;
            default : obstacle = new ObstacleModel(posY, posX, nbLap);
                break;
        }
        return obstacle;
    }

    /**
     * Retourne l'egalité entre deux List de FixedEntityModel.
     * @param levelBuild LevelBuilder
     * @return boolean
     */
    public boolean listFixedEntityModelEquals(LevelBuilder levelBuild) {
        for (int i = 0; i < this.getFixedEntities().size(); i++) {
            if (!this.getFixedEntities().get(i).fixedEntityEquals(
                levelBuild.getFixedEntities().get(i))) {
                return false;
            }
        }
        return true;
    }
}
