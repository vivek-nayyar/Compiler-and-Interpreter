import environment.Environment;
import parser.Parser;
import scanner.*;

import java.io.*;

/**
 * testers
 *
 * @author  Vivek Nayyar
 * @version March 22 2024
 */
public class Main {
    /**
     * Tests the scanner and parser
     *
     * @param args info from command line
     * @throws IOException if FileIO dies
     */
    public static void main(String[] args) throws IOException, ScanErrorException {
        FileInputStream fileInputStream = new FileInputStream(new File(
                "/Users/viveknayyar/Downloads/parserTest9 (1).txt"));
        Scanner scanner = new Scanner(fileInputStream);
        Parser parser = new Parser(scanner);

        parser.parseProgram().compile("output.asm");
    }
}