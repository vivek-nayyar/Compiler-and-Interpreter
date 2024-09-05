package environment;
import ast.*;
import java.util.*;
import java.util.Map;
import java.util.HashMap;

/**
 * The Environment class creates environments that contains instructions
 * for how code should be evaluated + executed
 *
 * @author Vivek Nayyar
 * @version March 22 2024
 */
public class Environment
{
    private HashMap<String, Integer> variables;
    private HashMap<String, ProcedureDeclaration> prodec;
    private Environment parent;

    /**
     * Constructs objects of the Environment class
     */
    public Environment()
    {
        this.variables = new HashMap<String, Integer>();
        this.prodec = new HashMap<String, ProcedureDeclaration>();
    }

    /**
     * Constructs an Environment object with a specified parent environment.
     *
     * @param parent the parent environment
     */
    public Environment(Environment parent)
    {
        this.variables = new HashMap<String, Integer>();
        this.prodec = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }

    /**
     * Adds variable to the environment (map) if it isn't contained
     * already
     * @param variable the variable to be stored
     * @param value the value to be stored by the variable
     */
    public void setVariable(String variable, int value)
    {
        if(variables.containsKey(variable))
        {
            variables.put(variable, value);
            return;
        }
        else
        {
            if(parent != null)
            {
                if(parent.variables.containsKey(variable))
                {
                    parent.variables.put(variable, value);
                    return;
                }
            }
        }
        variables.put(variable, value);
    }


    /**
     * Returns the value or the variable contained within the
     * environment/map. 
     * 
     * @param variable the name of the variable that is searched for in the map
     * @return the value of the variable
     */
    public int getVariable(String variable)
    {
        if(variables.containsKey(variable))
        {
            return variables.get(variable);
        }
        else
        {
            if(parent != null)
            {
                if(parent.variables.containsKey(variable))
                {
                    return parent.variables.get(variable);
                }
            }
        }
        return variables.get(variable);
    }

    /**
     * Adds a procedure to the environment.
     *
     * @param name the name of the procedure
     * @param proc the procedure declaration
     */
    public void setProcedure(String name, ProcedureDeclaration proc)
    {
        prodec.put(name, proc);
    }

    /**
     * Returns the procedure with the specified name from the environment.
     * If the procedure is not found in the current environment, it is searched for in the parent environment.
     *
     * @param name the name of the procedure to be searched for
     * @return the procedure declaration, or null if not found
     */
    public ProcedureDeclaration getProcedure(String name)
    {
        if(parent != null)
        {
            return parent.getProcedure(name);
        }
        return prodec.get(name);
    }

    /**
     * Declares a new variable in the current environment.
     *
     * @param variable the name of the variable to be declared
     * @param value the value to be assigned to the variable
     */
    public void declareVariable(String variable, int value)
    {
        variables.put(variable, value);
    }

    /**
     * Returns the parent environment.
     *
     * @return the parent environment
     */
    public Environment getParent()
    {
        return parent;
    }

}
