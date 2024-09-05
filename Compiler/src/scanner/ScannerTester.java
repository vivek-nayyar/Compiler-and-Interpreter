package scanner;

import java.io.*;
/**
 * Scanner tester for Compilers and Interpreters lab exercise 1
 * @author Vivek Nayyar
 *
 *
 */
public class ScannerTester
{

    public static void main(String[] args) throws FileNotFoundException, ScanErrorException
    {
        FileInputStream inStream = new FileInputStream(new File("/Users/viveknayyar/Desktop/compilers/Compiler/src/scanner/ScannerTestAdvanced.txt"));
        Scanner lex = new Scanner(inStream);
        testScanner(lex, inStream);
    }

    public static void testScanner(Scanner scan, FileInputStream stream) throws ScanErrorException
    {
        String token = scan.nextToken();
        while(!token.equals("EOF"))
        {
            System.out.println(token);
            token = scan.nextToken();
        }
        System.out.println(token);
    }
}