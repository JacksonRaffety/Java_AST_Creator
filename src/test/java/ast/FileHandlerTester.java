package ast;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;

public class FileHandlerTester {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testVerifyDirectorySuccess(){
        try{
            File testFile = tempFolder.newFolder();
            Assert.assertTrue("Should be a directory ", FileHandler.verifyDirectory(testFile));
        } catch(IOException err){
            Assert.fail();
        }
    }

    @Test
    public void testVerifyDirectoryFailure(){
        try{
            File testFile = File.createTempFile("test",".txt");
            testFile.deleteOnExit();
            Assert.assertFalse("Should be a file ", FileHandler.verifyDirectory(testFile));
        } catch(IOException err){
            Assert.fail();
        }
    }

    @Test
    public void testGetJavaFiles() throws IOException {
        //generate test files
        File folder = tempFolder.newFolder("folder");
        File file1 = File.createTempFile("file1",".java",folder);
        File file2 = File.createTempFile("file2",".java",folder);
        File file3 = File.createTempFile("file3",".txt",folder);
        file1.deleteOnExit();
        file2.deleteOnExit();
        file3.deleteOnExit();

        //create expected output
        List<File> fileList = new ArrayList<File>();
        fileList.add(file1);
        fileList.add(file2);

        //test
        List<File> outputList = FileHandler.getJavaFiles(folder);
        Assert.assertArrayEquals("should have same content",fileList.toArray(),outputList.toArray());
    }

    @Test
    public void testGetJavaFilesEmptyList() throws IOException {
        //generate test files
        File folder = tempFolder.newFolder("folder");
        File file1 = File.createTempFile("file1",".txt",folder);
        File file2 = File.createTempFile("file2",".txt",folder);
        File file3 = File.createTempFile("file3",".txt",folder);
        file1.deleteOnExit();
        file2.deleteOnExit();
        file3.deleteOnExit();

        //test
        List<File> outputList = FileHandler.getJavaFiles(folder);
        Assert.assertTrue("should be empty",outputList.isEmpty());
    }

    @Test
    public void testGetFileContentsWithException(){
        try{
            File testFile = tempFolder.newFolder();
            FileHandler.getFileContents(testFile);
        }catch (IOException err){
            Assert.assertThat(err.getMessage(), containsString("Trying to read a non-existent file:"));
        }
    }

    @Test
    public void testGetFileContents() throws IOException {
        String contents = "this is a test";

        //write to temporary file
        File testFile = File.createTempFile("testFile", ".txt");
        testFile.deleteOnExit();
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(testFile));
        fileWriter.write(contents);
        fileWriter.close();

        //preform the test
        try {
            String output = FileHandler.getFileContents(testFile);
            Assert.assertEquals("Strings should be the same ", contents + System.getProperty("line.separator"), output);
        } catch (IOException err) {
            Assert.fail();
        }
    }

}
