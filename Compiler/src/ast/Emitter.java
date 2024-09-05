package ast;

import java.io.*;

/**
 * The Emitter class is responsible for writing MIPS assembly code
 * to a specified output file. It provides methods to emit lines of code,
 * handle stack operations, and generate unique label identifiers.
 *
 * @author Anu Datar
 * @author Vivek Nayyar
 * @version May 15, 2024
 */
public class Emitter
{
	private PrintWriter out;
	private int counter;


	/**
	 * Constructs an Emitter for writing to a new file with the given name.
	 *
	 * @param outputFileName the name of the output file
	 * @throws RuntimeException if an IOException occurs while opening the file
	 */	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints one line of code to the file, with non-labels indented.
	 *
	 * @param code the line of code to be emitted
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Closes the output file. This method should be called after all calls to emit.
	 */	public void close()
	{
		out.close();
	}

	/**
	 * Emits the MIPS code to push an element onto a stack
	 * from the given register by moving the stack pointer
	 * 4 spaces.
	 *
	 * @param reg the name of the register of the element being pushed onto the stack
	 */
	public void emitPush(String reg)
	{
		emit("subu $sp $sp 4");
		emit("sw " + reg + " ($sp)");
	}

	/**
	 * Emits the MIPS code to pop an element from a stack
	 * from the given register by moving the stack pointer
	 * 4 spaces.
	 *
	 * @param reg the name of the register of the element being popped from the stack
	 */
	public void emitPop(String reg)
	{
		emit("lw " + reg + " ($sp)");
		emit("addu $sp $sp 4");
	}

	/**
	 * Returns the next label ID accounted for by the accumulating
	 * instance variable counter.
	 *
	 * @return the next label ID
	 */
	public int nextLabelID()
	{
		counter++;
		return counter;
	}
}