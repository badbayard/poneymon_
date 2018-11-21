## Équipe

Product owner : Ali  
Tracker : Yannis  
Responsable qualité : Yann  
Scrum master : Geoffroy  
  
Référents techniques :
- Réseau : Robin
- IA : Geoffroy
- Architecture : Ali
- Java/JavFX : Hugo

## Remarques et Pré-requis
Ce projet a été développé et testé sous Windows 10 et Ubuntu 18.04.  
Les développeurs ne garantisse pas le bon focntionnement de ce logiciel s'il est utilisé sur une autre distribution.  
  
Pour que ce logiciel fonctionne de manière relativement sûr, il faut au préalable avoir installé : 
- Java  (version 1.8 ou ultérieur)
- Maven (version 4.0 ou ultérieur)


## Compilation

À la racine du projet et dans un invite de commande :  
`mvn compile`

## Execution (après compilation)

Pour lancer un serveur : 
`mvn exec:java@server`  
Pour lancer un client :
`mvn exec:java@client  `

## Conventions

- Commentaires en français
- Code en anglais
