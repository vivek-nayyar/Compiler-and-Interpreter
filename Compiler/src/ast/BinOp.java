package ast;
import environment.Environment;

/**
 * The BinOp class represents arithmetic expressions
 * that implement a binary operator and two expresssions,
 * one on the left, one on the right. The expression is then
 * evaluated to form a product, quotient, sum, difference, etc.
 * 
 * @author Vivek Nayyar
 * @version March 22 2024
 */
public class BinOp extends Expression
{
    private String operand;
    private Expression left;
    private Expression right;

    /**
     * Constructs objects of the BinOp class
     * @param operand the operator the expressions will be evaluated with
     * @param left the expression left of the operator
     * @param right the expression right of the operator
     */
    public BinOp(String operand, Expression left, Expression right)
    {
        this.operand = operand;
        this.left = left;
        this.right = right;
    }

    /**
     * Evaluates an expression that contains a binary operator between a left
     * and right expression
     * @return the quotient, product, sum, difference, etc. (the value of the evaluated arithmetic)
     * @param env the environment in which the classes evaluate code
     */
    @Override
    public int eval(Environment env) 
    {
        if (operand.equals("*"))
        {
            return left.eval(env) * right.eval(env);
        }
        if (operand.equals("/"))
        {
            return left.eval(env) / right.eval(env);
        }
        if (operand.equals("+"))
        {
            return left.eval(env) + right.eval(env);
        }
        if (operand.equals("-"))
        {
            return left.eval(env) - right.eval(env);
        }
        else
        {
            return left.eval(env) % right.eval(env);
        }
    }

    /**
     * Emits code to evaluate a BinOp by storing the values of the expressions
     * in a stack and different registers. Then emits code to perform the arithmetic
     * based on the operand.
     *
     * @param e the emitter that emits the MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        left.compile(e);
        e.emitPush("$v0");
        right.compile(e);
        e.emitPop("$t0");
        if (operand.equals("*"))
        {
            e.emit("mult $t0 $v0");
            e.emit("mflo $v0");
        }
        if (operand.equals("/"))
        {
            e.emit("div $t0 $v0");
            e.emit("mflo $v0");
        }
        if (operand.equals("+"))
        {
            e.emit("addu $v0 $t0 $v0");
        }
        if (operand.equals("-"))
        {
            e.emit("subu $v0 $t0 $v0");
        }
    }
}
