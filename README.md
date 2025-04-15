# SlönguSpil++
**SlönguSpil++** is a JavaFX-based implementation of the classic **Snakes and 
Ladders** board game, supporting **2 - 4** players.

### Features
- 🎲 **Randomized boards** - Each game generates a board based on difficulty.
- 🤖 **Bots** - Challenge up to 3 computer-controlled players.
- ♟️ **Board size** - Choose the board size to your liking.

### Project structure
This is a standard Maven project, so your IDE should be able to automatically 
understand the structure when you `git clone`it.

The java version is **21+**

The **main** code is located in [src/main/java/vidmot](src/main/java/vidmot) and [src/main/java/vinnsla](src/main/java/vinnsla)
The **test** code is located in [src/test/java/vinnsla](src/test/java/vinnsla)

### Maven goals

#### Building 
To build the project, use the following command: `mvn compile`
#### Running
To **run** the project, use: `mvn javafx:run` / `mvn exec:java`
#### Testing
To run the **tests** for the project, use `mvn test`
#### Documentation
View the Maven site for the project to see design documents, dependencies and documentation.
To generate **Maven project site**, use `mvn site`

### Package
To package the project into a JAR file, run the command

`mvn clean package`

Alternatively if you dont have an IDE or Maven, you can package the project 
using the script file **[package.cmd](package.cmd)**

### Running JAR
To run the packaged JAR file, use the script file **[runJAR.cmd](runJAR.cmd)** 

### UML diagram
The UML is located in [UML](src/site/markdown/documentation.md)

### Design pattern
The design patterns that are used are **Observer** pattern and
**Strategy** pattern. They are described [here](src/site/markdown/documentation.md)