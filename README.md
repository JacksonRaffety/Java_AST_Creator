# Java_AST_Creator

============

Description
----------
A basic java package that takes a path to a directory as an argument and creates AST (Abstract Syntax Tree) files from found java files.
The program searches the directory and all of it's subdirectories for java files (*.java) and then creates an AST from the code in the file.
The program creates a file containting the plaintext of the created AST in the same directory as the found file, with the name *.java.ast.

Installation
--------------
Download this project as a ZIP and extract, or clone the project via:
```bash
git clone https://github.com/JacksonRaffety/Java_AST_Creator
```

You can compile the code and run tests with Maven (at least version 1.7 as it is targetted in the pom.xml)
```bash
# for installing and compiling
mvn install
mvn compile
```

Running the Program
----------------------

```bash
# running with maven
mvn exec:java -Dexec.mainClass="ast.ASTCreator" -Dexec.args="<path to directory here>"

# example
mvn exec:java -Dexec.mainClass="ast.ASTCreator" -Dexec.args="src/main"
```
