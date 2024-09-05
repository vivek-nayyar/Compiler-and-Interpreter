package ast;
import environment.Environment;
import java.util.*;

/**
 * ProcedureDeclarations have a name for the procedure, a statement to execute,
 * parameters for the procedure, and get methods.
 * The exec method
 * makes the key value object in the Procedure HashMap in its environment
 *
 * @author Vivek Nayyar
 * @version May 14, 2024
 */
public class ProcedureDeclaration extends Statement {

    private String name;
    private Statement statement;
    private ArrayList<String> parameters;

    /**
     * Constructor for ProcedureDeclaration class
     *
     * @param name  name of prodec
     * @param statement  statement of prodec
     * @param parameters the parameters for the prodec
     */
    public ProcedureDeclaration(String name, Statement statement, ArrayList<String> parameters)
    {
        this.name = name;
        this.statement = statement;
        this.parameters = parameters;
    }

    /**
     * Creates an object in the Procedure HashMap in the given
     * environment.
     *
     * @param env the environment whose HashMap will be changed
     */
    public void exec(Environment env)
    {
        env.setProcedure(name, this);
    }

    /**
     * Returns statement of the prodec
     *
     * @return statement of the prodec
     */
    public Statement getBody()
    {
        return statement;
    }

    /**
     * Returns the parameters of the prodec
     *
     * @return parameters of the prodec
     */
    public ArrayList<String> getParameters()
    {
        return parameters;
    }

}
