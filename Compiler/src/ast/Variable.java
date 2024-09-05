package ast;
import environment.Environment;
/**
 * The Variable class is used to create
 * and instantiate variables
 *
 * @author Vivek Nayyar
 * @version March 22 2024
 * */
public class Variable extends Expression
{
    private String name;

    /**
     * Creates objs of the Variable class
     * @param name the name of the var
     */
    public Variable(String name)
    {
        this.name = name;
    }

    /**
     * Evaluates objs of the Variable class by returning
     * the value within the given environment
     * 
     * @param env the environment in which the classes evaluate
     * @return the value of the var

     */
    @Override
    public int eval(Environment env) 
    {
        return env.getVariable(name);
    }

    /**
     * Compiles the variable declaration to MIPS assembly code.
     *
     * This method generates the assembly code to load the address of the variable into
     * a register and then load the variable's value into another register.
     *
     * @param e the {@code Emitter} used to output the compiled code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("la $t1 var" + name);
        e.emit("lw $v0 ($t1)");
    }
}
