package ast;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.YamlPrinter;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ASTCreatorTester {

    @Test
    public void testGetAST() throws IOException {
        //create mock java file
        File testFile = File.createTempFile("test",".java");
        testFile.deleteOnExit();

        //write to file
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(testFile));
        String javaContents = "class X { int x; }";
        fileWriter.write(javaContents);
        fileWriter.close();

        //get expected output
        CompilationUnit node = StaticJavaParser.parse(javaContents);
        YamlPrinter printer = new YamlPrinter(true);
        String expectedOutput = printer.output(node);

        //test
        Assert.assertEquals("should be same AST", expectedOutput, ASTCreator.getAST(testFile));
    }
}
