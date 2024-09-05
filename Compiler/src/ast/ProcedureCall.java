package ast;
import java.util.*;
import environment.Environment;

/**
 * ProcedureCall objects have a name and AL of
 * params. ProcedureCall has an eval method that
 * evaluates procedures in the specified child environment.
 *
 * @author Vivek Nayyar
 * @version May 14, 2024
 */
public class ProcedureCall extends Expression
{
    private String name;
    private ArrayList<Expression> plist;

    /**
     * Constructor for ProcedureCall objects
     *
     * @param name name of the ProcedureCall
     * @param plist list of parameters the ProcedureCall uses
     */
    public ProcedureCall(String name, ArrayList<Expression> plist)
    {
        this.name = name;
        this.plist = plist;
    }

    /**
     * Evaluates the environment's procedures in the child environment extending
     * the specified environment. Runs the child's procedures' body.
     *
     * @param env the environment whose procedures are evaled + run
     * @return the value of the name variable in the child env's HashMap
     */
    public int eval(Environment env)
    {
        ProcedureDeclaration pd = env.getProcedure(name);
        Environment temp = env;
        if(temp.getParent() != null)
        {
            temp = temp.getParent();
        }
        Environment child = new Environment(temp);
        child.declareVariable(name, 0);
        ArrayList<String> parameters = pd.getParameters();
        for(int i = 0; i < plist.size(); i++)
        {
            child.declareVariable(parameters.get(i), plist.get(i).eval(env));
        }
        pd.getBody().exec(child);
        return child.getVariable(name);
    }
}
