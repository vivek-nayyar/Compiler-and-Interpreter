package ast;
import environment.Environment;

/**
 * Represents a subclass of statement in which a variable
 * is assigned to an expression in the given environment
 *
 * @author Vivek Nayyar
 * @version March 22 2024
 */
public class Assignment extends Statement
{
    private String variable;
    private Expression expression;

    /**
     * Constructs objects of the class Assignment
     * 
     * @param variable the variable to get assigned a value
     * @param expression the expression assigned to the given variable
     */
    public Assignment(String variable, Expression expression)
    {
        this.variable = variable;
        this.expression = expression;
    }

    /**
     * Executes objects of the Assignment cless by setting
     * the value of the variable to the given expression
     * in the given environment
     * 
     * @param env the environment in which the classes execute code
     */
    @Override
    public void exec (Environment env)
    {
        env.setVariable(variable, expression.eval(env));
    }

    /**
     * Emits code to handle the assignment of an expresssion to a variable.
     *
     * @param e the emitter that emits the MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        expression.compile(e);
        e.emit("la $t1 var" + variable);
        e.emit("sw $v0, ($t1)");
    }
}
