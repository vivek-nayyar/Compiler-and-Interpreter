package ast;
import java.util.*;
import environment.Environment;
/**
 * The Program class represents a complete program consisting of
 * a list of variable names, procedure declarations, and a main statement.
 * It provides methods for executing the program in a given environment
 * and compiling the program to MIPS assembly code.
 *
 * @author Vivek Nayyar
 * @version May 15, 2024
 */
public class Program extends Statement

{

    private List<String> varNames;
    private List<ProcedureDeclaration> prodecs;
    private Statement statement;

    /**
     * Constructs a Program with the specified variable names, procedure declarations,
     * and main statement.
     *
     * @param varNames the list of variable names used in the program
     * @param prodecs the list of procedure declarations in the program
     * @param statement the main statement of the program
     */
    public Program(List<String> varNames, List<ProcedureDeclaration> prodecs, Statement statement)
    {
        this.prodecs = prodecs;
        this.statement = statement;
        this.varNames = varNames;

    }

    /**
     * Returns the list of procedure declarations in the program.
     *
     * @return the list of ProcedureDeclaration objects
     */
    public List<ProcedureDeclaration> getProcedures()
    {
        return prodecs;
    }

    /**
     * Returns the main statement of the program.
     *
     * @return the main Statement object
     */
    public Statement getStatement()
    {
        return statement;
    }

    /**
     * Executes the program within the given environment.
     *
     * This method first executes all procedure declarations to define them in the environment,
     * and then executes the main statement.
     *
     * @param env the Environment in which the program is executed
     */
    public void exec(Environment env)
    {
        for(ProcedureDeclaration p : prodecs)
        {
            p.exec(env);
        }
        statement.exec(env);
    }

    /**
     * Compiles the program to MIPS assembly code and writes the output to a file with the specified name.
     *
     * @param fileName the name of the file to which the compiled code is written
     */
    public void compile(String fileName)
    {
        Emitter e = new Emitter(fileName);
    }

    /**
     * Compiles the program to MIPS assembly code using the specified Emitter.
     *
     * This method generates the data and text segments of the MIPS assembly code,
     * declares global variables, and compiles the main statement. It also includes the
     * necessary system calls to terminate the program.
     *
     * @param e the Emitter used to output the compiled code
     */
    public void compile(Emitter e)
    {
        e.emit(".data");
        e.emit("nL:    .asciiz    \"\\n\"");
        for(String v: varNames)
        {
            e.emit("var" + v + ":\t .word \t 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main: ");
        statement.compile(e);
        e.emit("li $v0 10");
        e.emit("syscall");
        e.close();
    }

}
