package ast;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/*
 * FileHandler
 * -----------------
 * This class is dedicated to interacting with files and directories.
 */
class FileHandler {

    /*
     * verifyDirectory
     * --------------------
     * This method takes a File as input and returns whether the given file is a directory or not.
     * This method will also print to console the problem found when determined not to be a directory.
     *
     * Parameters:
     *      directory - The directory to verify.
     *
     * Output:
     *      Returns: boolean - True if a valid directory; false if otherwise
     *      Console: if not a directory, prints what the issue was (a file, could not be found)
     */
    public static boolean verifyDirectory(File directory){
        if(directory.isFile()){
            System.out.println("Error: Given a file, not a directory");
            return false;
        }

        if(!directory.isDirectory()){
            System.out.println("Error: The given directory could not be found or does not exits.");
            return false;
        }

        return true;
    }

    /*
     * getJavaFiles
     * --------------------
     * This method takes a directory as input and returns a list of all java files found within that directory
     * and all subdirectories. Returns an empty list if not java files could be found.
     *
     * Parameters:
     *      headDirectory - The directory to search for java files in
     *
     * Output:
     *      Returns: List<File> - a list of the java files found.
     */
    public static List<File> getJavaFiles(File headDirectory){
        List<File> javaFiles = new ArrayList<File>();
        File[] searchFiles = headDirectory.listFiles();
        
        if(searchFiles == null) {
        	//return an empty list if there are no files in the directory.
        	return javaFiles;
        }

        for(File file : searchFiles){
            //if subdirectory, recurse on the directory and add the inner java files to the main list
            if(file.isDirectory()){
                List<File> subFiles = getJavaFiles(file);
                javaFiles.addAll(subFiles);
            }else if(file.isFile()){
                if(file.getAbsolutePath().endsWith(".java")){
                    javaFiles.add(file);
                }
            }
        }

        return javaFiles;
    }

    /*
     * getFileContents
     * --------------------
     * This method takes a file as input, opens and reads the file, and returns the contents as plain text in
     * a string.
     *
     * Parameters:
     *      file - The file to open and extract the contents from.
     *
     * Output:
     *      Returns: String - plaintext representation of the opened file
     *      Console: alerts user if there is an issue reading a file
     *      Throws: IOException - if the given file is invalid, throw error
     */
    public static String getFileContents(File file) throws IOException {
        String fileContents = "";
        //verify the file given can be worked with
        if(!file.isFile()){
            throw new IOException("Trying to read a non-existent file: " + file.getAbsolutePath());
        }

        try{
            //string builder automatically configures strings together (instead of using string operation +)
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line;

            //read the file line-by-line, and append new-lines, as BufferedReader ignores those with readLine()
            while((line = fileReader.readLine()) != null){
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }

            fileContents = stringBuilder.toString();
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error: Problem reading file " + file.getAbsolutePath() + ".");
            e.printStackTrace();
        }

        return fileContents;
    }

    /*
     * writeNewFile
     * --------------------
     * This method takes a file as input, then opens (or creates) a new file with the same path and name as
     * the given file, but with the added extension of .ast. Then, the method writes to the file the String
     * passed as plaintext.
     *
     * Parameters:
     *      oldFile - The file to base the name of the file to open. Same path and name, ending with .ast
     *      contents - What is being written to the file
     *
     * Output:
     *      Console: alerts user of the creation of a new file, overwritting an old file, and the case
     *               of a writing error.
     */
    public static void writeNewFile(File oldFile, String contents){
        String newFileName = oldFile.getAbsolutePath() + ".ast";
        File newFile = new File(newFileName);
        BufferedWriter fileWriter;

        try{
            if(newFile.createNewFile()){
                //alert user of the creation of a new file
                System.out.println("Successfully created file " + newFile.getAbsolutePath());
            }else{
                //alert user of an overwritten file
                System.out.println("File " + newFile.getAbsolutePath() + " already exists. Overwriting contents.");
            }
            fileWriter = new BufferedWriter(new FileWriter(newFile));
            fileWriter.write(contents);
        } catch (IOException e) {
            //alert user of a writing error
            System.out.println("Error: Problem writing to file " + newFile.getAbsolutePath());
        }

    }
}
