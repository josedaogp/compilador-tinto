
package tinto.ast.statement;

import tinto.ast.expression.IntegerLiteralExpression;

/**
 * Clase que describe la clausula Case
 */
public class CaseClause extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Expresion del case
	 */
	private IntegerLiteralExpression exprinteg;
	
	/**
	 * Cuerpo clausula Case
	 */
	private Statement body;
	

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public CaseClause(IntegerLiteralExpression exprinteg, Statement body)
	{
		this.exprinteg = exprinteg;
		this.body = body;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene la expresion del Case
	 * @return
	 */
	public IntegerLiteralExpression getExprCase()
	{
		return this.exprinteg;
	}
	
	/**
	 * Obtiene el cuerpo
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