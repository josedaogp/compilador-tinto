package tinto.ast.statement;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucci�n DO WHILE
 * 
 */
public class DoWhileStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condici�n de la instrucci�n DO WHILE
	 */
	private Expression condition;
	
	/**
	 * Instrucci�n a desarrollar mientras la condici�n se cumple
	 */
	private Statement instruction;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public DoWhileStatement(Expression exp, Statement inst)
	{
		this.condition = exp;
		this.instruction = inst;
	}
	
	//----------------------------------------------------------------//
	//                          M�todos p�blicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la condici�n de la instrucci�n
	 * @return
	 */
	public Expression getCondition() 
	{
		return this.condition;
	}
	
	/**
	 * Obtiene la instrucci�n a realizar mientras la condici�n se cumple
	 * @return
	 */
	public Statement getInstruction() 
	{
		return this.instruction;
	}
	
	/**
	 * Verifica si la instrucci�n alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() 
	{
		return false;
	}
}
