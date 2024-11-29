# ChâTop

Cette application est un back-end développé en Java avec **Spring Boot** et utilise **MySQL** comme base de données.

---

## Prérequis

Assurez-vous que les outils suivants soient installés sur votre machine :
- [Java JDK 17+](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/install.html)
- [MySQL Server](https://dev.mysql.com/downloads/)
- [Postman](https://www.postman.com/downloads/) (facultatif, pour tester les API)

---

## Installation et configuration

1. **Cloner le projet depuis GitHub.**
2. **Configurer l'accès à la base de données :** La configuration de la base de données est déjà définie dans le fichier src/main/resources/application.properties.
3. **Installer les dépendances Maven :** Maven est utilisé pour gérer les bibliothèques nécessaires au projet. Exécutez cette commande dans le dossier racine du projet :

mvn clean install

4. **Démarrer le serveur MySQL :**
5. **Lancer l'application en exécutant cette commande :** 

mvn spring-boot:run

L'application sera accessible à l'adresse suivante :

http://localhost:9000

(Sur un IDE comme Eclipse, il est également possible de lancer l'application via la commande Run As > Java Application)

6. **Tester l'application :** Vous pouvez tester les endpoints REST via Postman.
7. **Swagger :** Le Swagger est disponible à l'adresse suivante : 

http://localhost:9000/swagger-ui/index.html 
   
---

## Structure du projet

Le projet suit une architecture typique Spring Boot :

- **controller** : Contient les contrôleurs REST.
- **service** : Contient la logique métier.
- **repository** : Interfaces pour l'accès à la base de données.
- **model** : Définit les entités JPA (ex : Message) - Contient aussi un fichier DTO.
- **security** : Contien la configuration de la sécurité pour utiliser le JWT avec Spring Security.

---

## Outils utilisés

- **Spring Boot** : Framework pour la création rapide d'applications Java.
- **MySQL** : Base de données relationnelle.
- **Hibernate/JPA** : ORM pour la gestion des entités.
- **Maven** : Gestionnaire de dépendances.
