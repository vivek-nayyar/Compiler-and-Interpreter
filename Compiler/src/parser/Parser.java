package parser;
import ast.*;
import scanner.Scanner;
import scanner.ScannerTester;
import scanner.ScanErrorException;
import java.util.*;
import java.util.Map;
import java.util.HashMap;
/**
 * The Parser class parses statements, factors, expressions, and numbers.
 * Assigned variables are stored in a Map with String keys and Integers values.
 *
 * @author Vivek Nayyar
 * @version March 12 2024
 */
public class Parser
{
    private Scanner scanner;
    private String currentToken;

    private Map<String, Integer> map;


    /**
     * Constructs parser based on the given scanner
     * 
     * @param scanner the given scanner
     */
    public Parser(Scanner scanner) throws ScanErrorException
    {
        this.scanner = scanner;
        currentToken = scanner.nextToken();
        map = new HashMap<String, Integer>();

    }

    /**
     * Consumes the next token after checking if the current token matches the next
     * 
     * @param expected the expected token
     * 
     */
    private void eat(String expected) throws ScanErrorException
    {
        if(expected.equals(currentToken))
        {
            currentToken = scanner.nextToken();
        }
        else
        {
            throw(new IllegalArgumentException(expected 
                + " was expected. " + currentToken + " was found.")); 
        }
    }
    
    /**
     * Parses a number
     * 
     * @return the number parsed
     */
    private Expression parseNumber() throws ScanErrorException
    {
        int n = Integer.parseInt(currentToken);
        eat(currentToken);
        return new ast.Number(n);
    }
    
    /**
     * Parses statements with WRITELN, BEGIN, END, and EOF tokens
     *
     */
    public Statement parseStatement() throws ScanErrorException
    {
        if(currentToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression exp = parseExpression();
            eat(")");
            eat(";");
            return new Writeln(exp);
        }
        else if(currentToken.equals("BEGIN"))
        {
            eat("BEGIN");
            ArrayList<Statement>list = new ArrayList<>();
            while(!currentToken.equals("END"))
            {
                list.add(parseStatement());
            }
            Block block = new Block(list);
            eat("END");
            eat(";");
            return block;
        }

        else if(currentToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement stat = parseStatement();
            return new If(cond, stat);
        }
        else if(currentToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement stat = parseStatement();
            return new While(cond, stat);
        }
        else
        {
            String curr = currentToken;
            eat(curr);
            eat(":=");
            Assignment assignment = new Assignment(curr, parseExpression());
            eat(";");
            return assignment;
        }
    }
    
    /**
     * Parses exprs based on the specified factor grammar
     * 
     * @return the parsed factor
     */
    public Expression parseFactor() throws ScanErrorException
    {
        if(currentToken.equals("("))
        {
            eat("(");
            Expression expr = parseExpression();
            eat(")");
            return expr;
        }
        else if(currentToken.equals("-"))
        {
            eat("-");
            return new BinOp("-", new ast.Number(0), parseFactor());
        }
        else
        {
            String token = currentToken;
            try
            {
                int val = Integer.parseInt(currentToken);
                eat(currentToken);
                ast.Number num = new ast.Number(val);
                return num;
            } catch(Exception e)
            {
                eat(currentToken);
                if(currentToken.equals("("))
                {
                    eat("(");
                    ArrayList<Expression> params = new ArrayList();
                    while(!currentToken.equals(")"))
                    {
                        params.add(parseExpression());
                        if(currentToken.equals(","))
                        {
                            eat(",");
                        }
                    }
                    eat(")");
                    return new ProcedureCall(token, params);
                }
                else
                {
                    return new Variable(token);
                }
            }
        }
    }
    
    /**
     * Parses a factor that's divided or multiplied
     * 
     * @return the parsed factor
     */
    private Expression parseTerm() throws ScanErrorException
    {
        Expression val = parseFactor();
        while(currentToken.equals("/") || currentToken.equals("*"))
        {
            if(currentToken.equals("/"))
            {
                eat("/");
                val = new BinOp("/", val, parseFactor());
            }
            else if(currentToken.equals("*"))
            {
                eat("*");
                val = new BinOp("*", val, parseFactor());
            }

        }
        return val;
    }
    
    
    /**
     * Parses a term that is subtracted or added
     * 
     * @return the parsed term
     */
    private Expression parseExpression() throws ScanErrorException
    {
        Expression val = parseTerm();
        while(currentToken.equals("+") || currentToken.equals("-"))
        {
            if(currentToken.equals("+"))
            {
                eat("+");
                val = new BinOp("+", val, parseTerm());
            }
            else if(currentToken.equals("-"))
            {
                eat("-");
                val = new BinOp("-", val, parseTerm());
            }
        }
        return val;
    }
    /**
     * Parses everything in block before END token, breaking into statements that are parsed
     * to an AL and creating a block based on that AL
     *
     * @return the block to parsed
     */
    private Block parseBlock() throws ScanErrorException
    {
        ArrayList<Statement> a =new ArrayList<Statement>();
        while(!currentToken.equals("END"))
        {
            a.add(parseStatement());
        }
        return new Block(a);
    }

    /**
     * Instantiates + returns a Conditional obj with the parsed exprs and comp op
     *
     * @return  conditional obj made with the parsed expr and comp op
     */
    public Condition parseCondition() throws ScanErrorException
    {
        Expression exp1 = parseExpression();
        String compOp = parseCompOp();
        Expression exp2 = parseExpression();
        return new Condition(compOp, exp1, exp2);
    }

    /**
     * This eats the comp op and returns string version
     * @return  string version of comp op
     */
    public String parseCompOp() throws ScanErrorException
    {
        if(currentToken.equals("="))
        {
            eat("=");
            return "=";
        }
        else if(currentToken.equals("<>"))
        {
            eat("<>");
            return "<>";
        }
        else if(currentToken.equals("<"))
        {
            eat("<");
            return "<";
        }
        else if(currentToken.equals(">"))
        {
            eat(">");
            return ">";
        }
        else if(currentToken.equals("<="))
        {
            eat("<=");
            return "<=";
        }
        else
        {
            eat(">=");
            return ">=";
        }
    }

    /**
     * Parses and eats a list of ProcedureDeclaration and list of VAR names
     * given in the body of text after a "PROCEDURE" token and VAR token. Continues parsing
     * tokens as long as the current token is "PROCEDURE" or VAR. Returns
     * a Program object made with the list of VARs, ProcedureDeclarations, and
     * parsed statement.
     *
     * @return the created Program object
     */
    public Program parseProgram() throws ScanErrorException
    {
        ArrayList<String> vars = new ArrayList<String>();
        while(currentToken.equals("VAR"))
        {
            eat("VAR");
            while (!currentToken.equals(";"))
            {
                vars.add(currentToken);
                eat(currentToken);
                if(currentToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(";");
        }
        List<ProcedureDeclaration> pd = new ArrayList<ProcedureDeclaration>();
        while(currentToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = currentToken;
            eat(name);
            eat("(");
            ArrayList<String> params = new ArrayList();
            while(!currentToken.equals(")"))
            {
                String newparam = currentToken;
                eat(newparam);
                params.add(newparam);
                if(currentToken.equals(","))
                {
                    eat(",");
                }
            }
            eat(")");
            eat(";");
            pd.add(new ProcedureDeclaration(name, parseStatement(), params));
        }
        Statement stmt = parseStatement();
        return new Program(vars, pd, stmt);
    }
}
