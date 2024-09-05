package parser;
import scanner.Scanner;
import scanner.ScannerTester;
import scanner.ScanErrorException;
import java.io.*;
/**
 * Tests the parser.Parser class via test files
 * @author Vivek Nayyar
 * @version March 12 2024
 */
public class ParserTester
{
    /**
     * Tests the scanner.Scanner using "ScannerText.txt" file
     * @param str array of String objects
     * @throws FileNotFoundException if there is an I/O error
     * @throws ScanErrorException if an token is invalid
     */
    public static void main(String[] str) throws FileNotFoundException, 
                                                 ScanErrorException
    {
        InputStream inputstream = new FileInputStream("/"
                +"Users/viveknayyar/Downloads/parserTest4.txt");
        Scanner scanner = new Scanner(inputstream);
        Parser parser = new Parser(scanner);
        parser.parseStatement();
        parser.parseStatement();
        parser.parseStatement();
    }
}