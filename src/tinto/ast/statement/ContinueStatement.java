package tinto.ast.statement;


/**
 * Clase que describe la instrucci�n FOR
 */
public class ContinueStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public ContinueStatement()
	{
	}
	
	//----------------------------------------------------------------//
	//                          M�todos p�blicos                      //
	//----------------------------------------------------------------//

	public boolean returns() 
	{
		return false;
	}
}