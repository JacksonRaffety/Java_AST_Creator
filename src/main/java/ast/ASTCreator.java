package ast;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;


class ASTCreator {

    public static void main(String[] args){

        if(args.length < 1){
            System.out.println("Not enough arguments");
            System.exit(1);
        }
        File headDirectory = new File(args[0]);

        //ensure that the given argument is a directory; prematurely exit the program if otherwise
        if(!FileHandler.verifyDirectory(headDirectory)){
            System.exit(1);
        }

        //get all the java files from the directory
        List<File> javaFiles = FileHandler.getJavaFiles(headDirectory);

        if(javaFiles.isEmpty()){
            System.out.print("No java files were found in this directory and it's subdirectories.");
        }

        //create ASTs for all the java files and write them to file
        for(File file : javaFiles){
            try{
                String fileOutput = getAST(file);
                FileHandler.writeNewFile(file, fileOutput);
            } catch (IOException e) {
                System.out.println("ERROR: " + file.getAbsolutePath() + " should be a file, but was found to not be.");
            } catch(ParseProblemException err){
                String outputError = "Error: The file " + file.getAbsolutePath() + " could not be properly parsed." +
                        " Either it is not a java file or contains faulty java code that could not be parsed.";
                System.out.println(outputError);
            }
        }
    }

    /*
     * getAST
     * --------------------
     * This method takes a file as input and creates an AST based on the Java code provided.
     * The AST will then be converted to a text format via Yaml formatting and returned as a string.
     *
     * Parameters:
     *      file - The file to parse and create AST based on.
     *
     * Output:
     *      Returns: String - the AST of the file in plaintext form.
     *      Throws: IOException - if the given file can't be read properly
     *              ParseProblemException - if there was an issue parsing the file
     */
    public static String getAST(File file) throws IOException, ParseProblemException {
        String contents = FileHandler.getFileContents(file);
        CompilationUnit headNode = StaticJavaParser.parse(contents);
        YamlPrinter printer = new YamlPrinter(true);
        return printer.output(headNode);
    }
}
