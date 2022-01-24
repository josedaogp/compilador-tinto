package tinto.ast.statement;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucción DO WHILE
 * 
 */
public class DoWhileStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condición de la instrucción DO WHILE
	 */
	private Expression condition;
	
	/**
	 * Instrucción a desarrollar mientras la condición se cumple
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
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la condición de la instrucción
	 * @return
	 */
	public Expression getCondition() 
	{
		return this.condition;
	}
	
	/**
	 * Obtiene la instrucción a realizar mientras la condición se cumple
	 * @return
	 */
	public Statement getInstruction() 
	{
		return this.instruction;
	}
	
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() 
	{
		return false;
	}
}
