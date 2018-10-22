package fr.univ_lyon1.info.m1.poneymon_fx.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class AssetsRepertories {

    private String filePath;
    private File [] files;

    public AssetsRepertories(String path) {
        filePath = path;
    }

    /**
     * Accesseur FilePath.
     * @return FilePath.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Mutateur FilePath.
     * @param newPath String
     */
    public void setFilePath(String newPath) {
        filePath = newPath;
    }

    /**
     * Accesseur files.
     * @return files.
     */
    public  File [] getFiles() {
        return files;
    }

    /**
     * Mutateur files.
     * @param newFiles File []
     */
    public void setFiles(File []  newFiles) {
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
     *
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
     * @param regexFilter String
     */
    public List<String> filter(String regexFilter) {
        List<String> result = new ArrayList<>();
        for (File file : this.getFiles()) {
            if (file.isFile() && file.getName().matches(regexFilter)) {
                result.add(file.getName());
            }
        }
        return result;
    }

    /**
     * filtre les couleurs dans les fichiers
     * Dans un fichier Entitee:
     * 1er param nom de famille
     * 2e param couleur
     * 3e param optionnel.
     * @param filteredFiles liste de fichier.
     * @return tableau de couleur
     */
    public String [] filtreColor(List<String> filteredFiles) {
        String [] colors = new String [filteredFiles.size()];
        int indexColor = 0;
        String pattern = "[^a-zA-Z]+";
        String [] parts;
        for (String str : filteredFiles) {
            parts = str.split(pattern);
            if (indexColor <= colors.length) {
                colors[indexColor] = parts[1];
                indexColor++;
            }
        }
        return colors;
    }

    /**
     * Combine les differentes fonctions de la classe et retourne le tableau de couleur
        pour une famille d'entitée.
     * @return tableau de couleur
     */
    public String [] searchAndFiltre(String regexFilter) {
        this.browseAssets();
        List<String> files = this.filter(regexFilter);
        return this.filtreColor(files);
    }

}
