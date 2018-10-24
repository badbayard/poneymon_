package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Random;


public class LevelBuilder {

    private AssetsRepertories repLevels;

    /**
     * constructeur LevelBuilder.
     */
    public LevelBuilder() {
        final String testPath = System.getProperty("user.dir")
            + "/src/main/resources/assets/levels";
        repLevels = new AssetsRepertories(testPath);
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

        int charsRead = 0;
        try (BufferedReader input = new BufferedReader(new FileReader(level))) {
            String line;
            String[] parts;
            while ((line = input.readLine()) != null) {
                parts = line.split(" ");
                for (String s : parts) {
                    System.out.println(s);
                    charsRead++;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return (charsRead > 0);
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

}
