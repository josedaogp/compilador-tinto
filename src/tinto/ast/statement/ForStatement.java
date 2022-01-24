package tinto.ast.statement;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucción FOR
 */
public class ForStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condición de la instrucción FOR
	 */
	private Expression condition;
	
	/**
	 * Inicialización FOR
	 */
	private Statement inici;
	
	/**
	 * Incremento FOR
	 */
	private Statement incre;
	
	/**
	 * Cuerpo FOR
	 */
	private Statement body;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public ForStatement(Statement inici, Expression exp,  Statement incre, Statement body)
	{
		this.condition = exp;
		this.inici = inici;
		this.incre = incre;
		this.body = body;
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
	 * Obtiene la inicialización
	 * @return
	 */
	public Statement getInicializacion() 
	{
		return this.inici;
	}
	
	/**
	 * Obtiene el incremento
	 * @return
	 */
	public Statement getIncremento() 
	{
		return this.incre;
	}
	
	/**
	 * Obtiene el cuerpo del for
	 * @return
	 */
	public Statement getBody() 
	{
		return this.body;
	}
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() 
	{
		return body.returns();
	}
}
