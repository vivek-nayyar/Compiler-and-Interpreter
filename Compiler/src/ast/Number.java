package ast;
import environment.Environment;

/**
 * The Number class represents numbers within arithmetic expressions
 *
 * @author Vivek Nayyar
 * @version March 22 2024
 */
public class Number extends Expression
{
    private int value;

    /**
     * Constructs objects of the Number class
     * @param num the number to be represented
     */
    public Number(int num)
    {
        value = num;
    }

    /**
     * Evaluates the given number by returning its value
     * 
     * @param env the environment in which the classes evaluate code
     * @return the value of the number
     */
    @Override
    public int eval(Environment env) 
    {
        return value;
    }

    /**
     * Emits MIPS code using the given emitter to load and store
     * the value of a number in the $v0 register.
     *
     * @param e the emitter that emits the MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }

}
