package fr.univ_lyon1.info.m1.poneymon_fx.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AssetsRepertories {

    private String filePath;
    private File[] files;

    public AssetsRepertories(String path) {
        filePath = path;
    }

    /**
     * Accesseur FilePath.
     *
     * @return FilePath.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Mutateur FilePath.
     *
     * @param newPath String
     */
    public void setFilePath(String newPath) {
        filePath = newPath;
    }

    /**
     * Accesseur files.
     *
     * @return files.
     */
    public File[] getFiles() {
        return files;
    }

    /**
     * Mutateur files.
     *
     * @param newFiles File []
     */
    public void setFiles(File[] newFiles) {
        files = newFiles;
    }

    /**
     * parcours les fichiers des Assets et retourne sans distinction tour le contenu.
     */
    public void browseAssets() {
        File directory = new File(this.getFilePath());
        File[] fList = directory.listFiles();
        if (fList != null) {
            this.setFiles(fList);
        }
    }

    /**
     * teste le parcours repertoire.
     */
    public void display() {
        for (File file : this.getFiles()) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * Filtre les fichiers en fonction de l'expression rationelle passée en parametre.
     *
     * @param regexFilter String
     */
    public void filter(String regexFilter) {
        List<File> result = new ArrayList<>();

        for (File file : this.getFiles()) {
            if (file.isFile() && file.getName().matches(regexFilter)) {
                result.add(file);
            }
        }

        File[] filteredFiles = new File[result.size()];

        for (int i = 0; i < result.size(); i++) {
            filteredFiles[i] = result.get(i);
        }

        this.setFiles(filteredFiles);
    }

    /**
     * filtre les couleurs dans les fichiers
     * Dans un fichier Entitee:
     * 1er param nom de famille
     * 2e param couleur
     * 3e param optionnel.
     *
     * @param filteredFiles liste de fichier.
     * @return tableau de couleur
     */
    public String[] filterColor(File[] filteredFiles) {
        String[] colors = new String[filteredFiles.length];
        int indexColor = 0;
        String pattern = "[^a-zA-Z0-9]+";
        String[] parts;
        for (File str : filteredFiles) {
            parts = str.getName().split(pattern);
            if (indexColor <= colors.length) {
                colors[indexColor] = parts[1];
                indexColor++;
            }
        }
        return colors;
    }

    /**
     * Combine les differentes fonctions de la classe et retourne le tableau de couleur
     * pour une famille d'entitée.
     *
     * @return tableau de couleur
     */
    public String[] searchAndFilter(String regexFilter) {
        this.browseAssets();
        this.filter(regexFilter);
        return this.filterColor(this.getFiles());
    }

    /**
     * Liste les entites de la forme pony-green(-blablah)*.gif
     * -> pony (si deja retourné pas de doublons)
     * @return liste de famille
     */
    public String[] availableEntities() {
        String filter = "(.)*-(.)*.gif";
        this.browseAssets();
        this.filter(filter);
        this.cleanseDoubleFamilyName();
        return this.filterNameEntity();
    }

    /**
     * elimine les fichiers de la liste ayant le meme nom de famille.
     */
    public void cleanseDoubleFamilyName() {
        List<String> tempSplit = new ArrayList<>(); //familles deja ajoutées
        List<File> tempFile = new ArrayList<>(); // liste de fichier temporaire
        File[] result; // tableau de fichier pour le set
        String pattern = "[^a-zA-Z0-9]+";
        String[] parts;

        for (File str : this.getFiles()) {
            parts = str.getName().split(pattern);
            if (!tempSplit.contains(parts[0])) {
                tempSplit.add(parts[0]);
                tempFile.add(str);
            }
        }

        tempSplit.clear();
        result = new File [tempFile.size()];

        for (int i = 0;i < tempFile.size();i++) {
            result[i] = tempFile.get(i);
        }
        tempFile.clear();
        this.setFiles(result);
    }


    /**
     * Recupere le nom des entitees dans la liste des fichiers.
     * @return tableau de nom d'entitees
     */
    public String[] filterNameEntity() {
        String pattern = "[^a-zA-Z0-9]+";
        String[] result;
        List<String> resulTtmp = new ArrayList<>();
        String[] parts;

        for (File fi : this.getFiles()) {
            parts = fi.getName().split(pattern);
            resulTtmp.add(parts[0]);
        }

        result = new String [resulTtmp.size()];
        for (int i = 0;i < resulTtmp.size();i++) {
            result[i] = resulTtmp.get(i);
        }
        return result;
    }

    /**
     * retourne le nom des fichiers sous forme de chaine de charactere.
     * @return tableau de nom de fichiers.
     */
    public String [] getFilesName() {
        String [] nameFiles = new String [this.getFiles().length];
        for (int i = 0; i < this.getFiles().length; i++) {
            nameFiles[i] = this.getFiles()[i].getName();
        }
        return nameFiles;
    }


    /**
     * retourne l'URL de l'image pour une entitee.
     * @param family Famille
     * @param color Couleur
     * @param option option possible (running,rainbow,...)
     * @return URL de l'entitee
     */
    public String getUrl(String family,String color, String option) {

        this.browseAssets();
        String filter;
        if (!option.equals("")) {
            filter = family + "-" + color + "-" + option + ".gif";
        } else {
            filter = family + "-" + color + ".gif";
        }
        this.filter(filter);

        if (this.getFiles().length > 0) {
            return filePath + "/" + this.getFiles()[0].getName();
        } else {
            return filePath;
        }
    }

    /**
     * verifie que tous les elements du premier elements sont present dans le second element
        (mais pas forcement dans le meme ordre).
     * @param colorsTest colorsTest
     * @param colorsExpected colorsTestExpected
     * @return boolean
     */
    public boolean allElementsAreInTab(String [] colorsTest, String [] colorsExpected) {
        int colorCount = 0;
        for (String strTest : colorsTest) {
            for (String strExpect : colorsExpected) {
                if (strTest.equals(strExpect)) {
                    colorCount++;
                    break;
                }
            }
        }
        return ((colorsTest.length == colorsExpected.length)
            && (colorCount == colorsExpected.length));
    }


    /**
     * verifie que le tableau de fichiers passer en parametres sont présents dans la liste des
     fichiers des assets (pas forcement dans le meme ordre).
     * @param filesExpected tableau de fichiers attendu
     * @return boolean
     */
    public boolean allFilesAreInTab(File [] filesExpected) {
        int filesCount = 0;
        for (File fiToTest : this.getFiles()) {
            for (File fiExpect : filesExpected) {
                if (fiToTest.equals(fiExpect)) {
                    filesCount++;
                    break;
                }
            }
        }
        return ((this.getFiles()).length == filesExpected.length)
            && (filesCount == filesExpected.length);
    }


}

