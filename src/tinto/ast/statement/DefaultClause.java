
package tinto.ast.statement;


/**
 * Clase que describe la clausula Default
 */
public class DefaultClause extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//
	
	/**
	 * Cuerpo clausula Default
	 */
	private Statement body;
	

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public DefaultClause(Statement body)
	{
		this.body = body;
	}

	//----------------------------------------------------------------//
	//                          Métodos públicos                      //
	//----------------------------------------------------------------//


	
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