## Installation plugins Intellij

- File | Settings | Chercher "Plugins" et cliquer sur le résultat Plugins | Browse Repositories...
- Chercher "checkstyle" et installer "Checkstyle-IDEA"
- Chercher "sonar" et installer "SonarLint"
- Redémarrer Intellij

#

- File | Settings | Chercher "Code Style" et cliquer sur Java | En haut, sélectionner "Stored in IDE - Default", puis sur l'écrou à droite cliquer "Import Scheme" -> "Checkstyle Configuration" et trouver le checkstyle.xml correspondant au projet Maven
- Dans l'onglet JavaDoc, vérifier que "Indent continuation lines" est bien coché
- Dans l'onglet Imports, dans les champs "Class count to use import with '\*'" et "Names count to use static import with '\*'" entrer 100

#

- File | Settings | Chercher "Checkstyle" et cliquer sur le résultat Checkstyle, dans la partie "Configuration File", à droite du tableau cliquer sur le + et ajouter la checkstyle.xml en plus de sa description (Maven Checkstyle par exemple...)
- Cocher la case de la colonne "Active" en face du fichier nouvellement importé
