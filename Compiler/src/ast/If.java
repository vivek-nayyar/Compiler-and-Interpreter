package ast;
import environment.Environment;

/**
 * The If class contains instructions on how the compiler should
 * handle "If" conditional statements
 * 
 * @author Vivek Nayyar
 * @version March 22 2024
 *
 */
public class If extends Statement
{
    private Condition condition;
    private Statement stat1;
    private Statement stat2;

    /**
     * Constructs objects of the If class when given 1 statements
     * Forms a statements in the form "If (condition) then (stat1)"
     * @param c the conditional expression to be executed
     * @param s1 the first statement to be executed if conditional is true
     */
    public If(Condition c, Statement s1)
    {
        condition = c;
        stat1 = s1;
    }

    /**
     * Constructs objects of the If class when given 2 statements
     * Forms a statements in the form "If (condition) then (stat1), else (stat2)"
     * 
     * @param c the conditional expression to be executed
     * @param s1 the first statement to be executed if conditional is true
     * @param s2 the second statement to be executed (else statement)
     */
    public If(Condition c, Statement s1, Statement s2)
    {
        condition = c;
        stat1 = s1;
        stat2 = s2;
    }

    /**
     * Executes an if statement
     * Checks the conditional, if yes, executes the first statement if it isn't null,
     * and executes the second (else) statement if it isn't null
     * 
     * @param env the environment in which the classes execute code
     */
    @Override
    public void exec(Environment env) 
    {
        int val = condition.eval(env);
        if (val == 1)
        {
            if (stat1 != null)
            {
                stat1.exec(env);
            }
        }
        else
        {
            if (stat2 != null)
            {
                stat2.exec(env);
            }
        }
    }


    /**
     * Emits code to evaluate "if" conditional statements.
     * Utilizes the label ID to keep track of which statement is
     * being compiled. Jumps to the according conditions as each
     * statement is compiled.
     *
     * @param e the emitter that emits the MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        int label = e.nextLabelID();
        condition.compile(e, "elseif" + label);
        stat1.compile(e);
        e.emit("j endif" + label);
        e.emit("elseif" + label+ ":");
        if (stat2 != null)
        {
            stat2.compile(e);
        }
        e.emit("endif" + label+ ":");
    }
}