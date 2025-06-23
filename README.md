# ChaTop – Back-end

Ce dépôt contient la partie **back-end** du projet ChaTop, une API REST en Spring Boot.

---

## 📖 Table des matières

1. [Présentation](#présentation)
2. [Prérequis](#prérequis)
3. [Installation de WSL & Docker](#installation-de-wsl--docker)
4. [Configuration](#configuration)
5. [Compilation & tests (hors Docker)](#compilation--tests-hors-docker)
6. [Construction de l’image Docker](#construction-de-limage-docker)
7. [Déploiement avec Docker Compose](#déploiement-avec-docker-compose)
8. [Tests & documentation de l’API](#tests--documentation-de-lapi)
9. [Commandes utiles](#commandes-utiles)
10. [Structure du projet](#structure-du-projet)
11. [FAQ](#faq)

---

## ✨ Présentation

ChaTop est une API REST pour la gestion d’un service de location (utilisateurs, annonces, messages…).
Elle expose des endpoints pour :

* Authentification (JWT)
* CRUD utilisateurs
* CRUD annonces (“rentals”)
* Envoi de messages

---

## 🛠 Prérequis

* **Windows 10/11** avec **WSL 2** activé
* **Ubuntu** (ou distribution Linux) sous WSL 2
* **Java 17+** (JDK) installé dans WSL
* **Maven 3.6+**
* **Docker Desktop** pour Windows (avec intégration WSL 2)
* **Docker Compose 1.29+** (fourni avec Docker Desktop)

Vérifiez les versions dans WSL :

```bash
java -version
mvn -v
docker --version
docker-compose --version
```

---

##  Installation de WSL & Docker

### 1. Activer WSL 2 (sous PowerShell en mode administrateur)

```powershell
wsl --install
# Si WSL existant, passer à la version 2
wsl --set-default-version 2
```

Redémarrez Windows si nécessaire.

### 2. Installer une distribution Linux (Ubuntu)

1. Ouvrez le **Microsoft Store**.
2. Cherchez et installez **Ubuntu**.
3. Lancez Ubuntu et créez votre utilisateur Linux.

### 3. Installer Java et Maven dans Ubuntu

```bash
sudo apt update && sudo apt install -y openjdk-17-jdk maven git
```

### 4. Installer Docker Desktop pour Windows

1. Téléchargez Docker Desktop : [https://www.docker.com/products/docker-desktop](https://www.docker.com/products/docker-desktop)
2. Pendant l’installation, activez « Use the WSL 2 based engine ».
3. Dans Docker Desktop → Settings → Resources → WSL Integration, activez votre distribution Ubuntu.

---

## Configuration

1. Clonez le projet :

   ```bash
    git clone https://github.com/Arnaud1720/P3-Developpez-le-back-end-en-utilisant-Java-et-Spring.git ChaTop-backend
    cd ChaTop-backend
2. Copiez (ou créez) un fichier `.env` à la racine :
   ```ini
    SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/chatop
    SPRING_DATASOURCE_USERNAME=root
    SPRING_DATASOURCE_PASSWORD=password123
````

3. Vérifiez `src/main/resources/application.yml` : il utilise ces variables d’environnement.

---

##  Compilation & tests (hors Docker)

Depuis WSL/Ubuntu :

```bash
mvn clean verify
```

* Compile le code
* Exécute les tests unitaires
* Génère le jar `target/ChaTop-0.0.1-SNAPSHOT.jar`

---

##  Construction de l’image Docker

mvn clean package -DskipTests

````
2. Construisez l’image :
   ```bash
    docker build -t chatop-backend:latest .
````

Vérifiez l’image :

```bash
docker images | grep chatop-backend
```

---

## Déploiement avec Docker Compose

Un fichier `docker-compose.yml` est fourni :

```bash
docker-compose up -d
```

* **MySQL:** `localhost:3306`
* **Adminer :** `http://localhost:8081`
* **API :** `http://localhost:8080`

Vérifiez l’état :

```bash
docker-compose ps
```

Pour arrêter :

```bash
docker-compose down
```

---

## 🔍 Tests & documentation de l’API

* **Swagger UI :**
  `http://localhost:8080/swagger-ui/index.html`
* **Postman :** importer `resources/postman/ChaTop.postman_collection.json`
depuis le dossier postman pour charger les configurations de l'API 
---

##  Commandes utiles

```bash
# Logs du backend
docker-compose logs -f backend

# Réinitialiser la base (toutes données) et relancer
docker-compose down -v
docker-compose up -d
---

## 📂 Structure du projet

```text
.
├── src/
│   ├── main/
│   │   ├── java/com/arnaud/p3/ChaTop
│   │   └── resources/application.yml
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── README.md
```

---
> README généré pour la partie back-end du projet ChaTop.
