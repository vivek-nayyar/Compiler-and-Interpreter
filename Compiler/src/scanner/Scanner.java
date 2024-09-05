package scanner;
import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters lab exercise 1
 * @author Vivek Nayyar
 * @version January 30, 2024
 *
 * @notebook
 * 1. If the next character is a new line or an open parenthesis then we can
 * conclude that we've reached the end of the token representing the
 * keyword "IF".
 *
 * 2. (From Hint 4) We pass in the expected char into the eat method. This will be
 * useful later on to keep the scanner from looking at the wrong value.
 *
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     *
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }

    /**
     * Scanner constructor for constructing a scanner that
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     *
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }
    /**
     * Method: getNextChar
     * Sets currentChar to the next char in the file.
     * If at the end of the file, sets eof to true.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if(inp == -1)
                eof = true;
            else
                currentChar = (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    /**
     * Method: eat
     * Checks if the current character is what is expected. If so, advances
     * to the next character. If not,
     * throws a ScanErrorException
     *
     * @param expected the expected char
     * @throws ScanErrorException if currentChar does not equal expected.
     */

    private void eat(char expected) throws ScanErrorException
    {
        if(expected == currentChar)
            getNextChar();
        else
            throw new ScanErrorException("Illegal Character -- Expected <" +
                    currentChar + "> and found <" + expected + ">.");
    }
    /**
     * Method: hasNext
     * Checks if the current character is at the end of the file
     * @return true if currentChar is not at the end of the file;
     *         otherwise, false.
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Removes white spaces from the input until the end of the file or
     * the next non-whitespace character is reached, whichever comes first.
     *
     * @return true if there is a character after currentChar;
     *         otherwise, false.
     * @throws ScanErrorException if currentChar does not match expected char
     */
    private boolean removeWhiteSpace() throws ScanErrorException
    {
        while (isWhiteSpace(currentChar) && hasNext())
            eat(currentChar);

        if(hasNext())
            return true;
        return false;
    }
    /**
     * Method: nextToken
     * Returns the next token as a String, calling the appropriate
     * helper method depending on if currentChar is a white space, digit,
     * or letter. If none of the above, throws a ScanErrorException.
     *
     * @return the next token as a String
     * @throws ScanErrorException if the next token is not defined over
     * the language used in this lab
     */
    public String nextToken() throws ScanErrorException
    {
        try
        {
            if(!removeWhiteSpace())
                return "EOF";
/**
            // remove single-line comments
            while (currentChar == '/')
            {
               // eat(currentChar);

                if (currentChar == '=')
                {
                    if (hasNext())
                        eat(currentChar);
                    return "/=";
                }
                else if(currentChar == '/')
                    while ((currentChar != '\n') && (currentChar != '\r') && hasNext())
                        eat(currentChar);
                if(hasNext())
                    eat(currentChar);
                else
                    return "/";
                if(!removeWhiteSpace())
                    return "EOF";
            }
*/
            if(isDigit(currentChar))
                return scanNumber();
            else if(isLetter(currentChar))
                return scanIdentifier();
            else if(isOperand(currentChar))
                return scanOperand();
            else if (currentChar == '.')
            {
                eof = true;
                return "EOF";
            }
            else
                throw new ScanErrorException("Unrecognized Character: " + currentChar);
        }
        catch (ScanErrorException e)
        {
            throw new ScanErrorException("Unrecognized Character: " + currentChar);
        }
    }


    /**
     * Checks if the input is a digit according to the
     * regular expression: digit:=[0,9].
     *
     * @param input the char to be checked
     * @return true if input is a digit;
     *         otherwise, false.
     */
    public static boolean isDigit(char input)
    {
        return input >= '0' && input <= '9';
    }

    /**
     * Checks if the input is a letter according to the
     * regular expression: letter := [a-z A-Z].
     *
     * @param input the char to be checked
     * @return true if the input is a letter;
     *         otherwise, false.
     */
    public static boolean isLetter(char input)
    {
        return (input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z');
    }

    /**
     * Checks if the input is a white space according to the
     * regular expression: white space := [‘ ‘ ‘\t’ ‘\r’ ‘\n’].
     *
     * @param input the char to be checked
     * @return true if the input is a white space;
     *         otherwise, false.
     */
    public static boolean isWhiteSpace(char input)
    {
        return (input == ' ' || input == '\t' || input == '\r' || input == '\n');
    }

    /**
     * Checks if the input is an operand according to the
     * regular expression: operand := [‘=’ ‘+’ ‘-‘ ‘*’ ‘/’ ‘%’ ‘(‘ ‘)’ ';'].
     *
     * @param input the char to be checked
     * @return true if the input is an operand;
     *         otherwise, false.
     */
    public static boolean isOperand(char input)
    {
        return (input == '=' || input == '+' || input == '-' || input ==
                '*' || input == '/' || input == '%' || input == '(' || input == ')'
                || input == ';' || input == ':' || input == '<' || input == '>' || input == ',');
    }

    /**
     * Scans currentChar and returns it as a String
     * assuming that it is a digit. If it is not a digit, returns an empty
     * String. If currentChar is not a digit, throws ScanErrorException
     *
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not a digit
     */
    private String scanNumber() throws ScanErrorException
    {
        try
        {
            String lex = "";
            while(isDigit(currentChar))
            {
                lex += currentChar;
                eat(currentChar);

                if(eof)
                    return lex;
            }
            return lex;
        }

        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Only call scanNumber with a digit!";
        }
    }

    /**
     * Scans currentChar and returns it as a String
     * assuming that it is a Letter. If it is not a letter, returns an empty
     * String. If currentChar is not a letter, throws ScanErrorException.
     *
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not a letter
     */
    private String scanIdentifier() throws ScanErrorException
    {
        try
        {
            String lex = "";
            while(isLetter(currentChar))
            {
                lex += currentChar;
                eat(currentChar);

                if(eof)
                    return lex;
            }
            return lex;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Only call scanIdentifier with a letter!";
        }
    }

    /**
     * Scans currentChar and returns it as a String
     * assuming that it is a operand. If it is not a operand, returns an empty
     * String. If currentChar is not an operand, throws ScanErrorException
     *
     * @return currentChar as a String
     * @throws ScanErrorException if the method is called when currentChar
     * is not an operand
     */
    private String scanOperand() throws ScanErrorException
    {
        try
        {
            String lex = "";
            if(isOperand(currentChar))
            {
                lex += currentChar;
                if(hasNext())
                    eat(currentChar);
                else
                    return lex;
            }
            if (lex.equals("<") && currentChar == '>')
            {
                lex += currentChar;

                if (hasNext())
                    eat(currentChar);
            }
            else if (currentChar == '=')
            {
                lex += currentChar;

                if (hasNext())
                    eat(currentChar);
            }
            return lex;
        }
        catch (ScanErrorException e)
        {
            e.printStackTrace();
            return "Only call scanOperand with an operand!";
        }
    }
}