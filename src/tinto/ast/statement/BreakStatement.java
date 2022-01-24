package tinto.ast.statement;


/**
 * Clase que describe la instrucci�n FOR
 */
public class BreakStatement extends Statement {

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
	public BreakStatement()
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