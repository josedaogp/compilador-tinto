package tinto.ast.statement;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucci�n FOR
 */
public class ForStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Condici�n de la instrucci�n FOR
	 */
	private Expression condition;
	
	/**
	 * Inicializaci�n FOR
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
	 * Obtiene la inicializaci�n
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
	 * Verifica si la instrucci�n alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() 
	{
		return body.returns();
	}
}
