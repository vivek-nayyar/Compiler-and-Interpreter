package ast;
import environment.Environment;


/**
 * Statement class is the abstract class representing
 * all statements that are used in executing code
 *
 * @author Vivek Nayyar
 * @version March 22 2024
 */
public abstract class Statement
{   
    /**
     * Provides subclasses with a type of template 
     * on how they will instruct themselves
     * 
     * @param env the environment in which the classes execute code
     */
    public abstract void exec (Environment env);

    /**
     * Compiles the statement into its corresponding machine or intermediate code representation.
     *
     * @param e the {@code Emitter} used to output the compiled code
     * @throws RuntimeException if the method is not overridden by a subclass
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!!!!!");
    }

}

