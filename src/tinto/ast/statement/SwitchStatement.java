
package tinto.ast.statement;

import java.util.ArrayList;

import tinto.ast.expression.Expression;

/**
 * Clase que describe la instrucción SWITCH
 * 
 */
public class SwitchStatement extends Statement {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Expresion de la instrucción SWITCCH
	 */
	private Expression condition;
	
	/**
	 * Clausula Case
	 */
	private ArrayList<CaseClause> caseClause;
	
	/**
	 * Clausula Default
	 */
	private ArrayList<DefaultClause> defaultClause;

	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * @param type
	 */
	public SwitchStatement(Expression cond, ArrayList<CaseClause> caseClause, ArrayList<DefaultClause> defaultClause)
	{
		this.condition = cond;
		this.caseClause = caseClause;
		this.defaultClause = defaultClause;
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
	 * Obtiene la clausula Case
	 * @return
	 */
	public ArrayList<CaseClause> getCaseClause() 
	{
		return this.caseClause;
	}
	
	/**
	 * Obtiene la clausula Default
	 * @return
	 */
	public ArrayList<DefaultClause> getDefaultClause() 
	{
		return this.defaultClause;
	}
	
	/**
	 * Devuelve true si los literales de los case se repiten alguna vez
	 * @return
	 */
	public boolean repeatedLiteral() //Ver si hay algun literal repetido
	{
		for (int i = 0; i < caseClause.size(); i++) {
			System.out.println(caseClause.get(i).getExprCase().getValue());
		}
		
		int i = 0;
		boolean repe = false;
		while(!repe && i < caseClause.size()) {
			int aux=caseClause.get(i).getExprCase().getValue();
			int j = 0;
			
			while(!repe && j < caseClause.size()){
				int v=caseClause.get(j).getExprCase().getValue();
				if (aux==v) {
					if(i!=j) repe = true; else j++;
				}else j++;
			}
			
			if (!repe) i++;
				
		}
		return repe;
	}
	/**
	 * Verifica si la instrucción alcanza siempre un "return".
	 * @return
	 */
	public boolean returns() 
	{
		//Se calculan los returns de todas las cláusulas Case
		int i=0;
		boolean casetrue = true;
		while(i < caseClause.size() && casetrue) {
			if (!caseClause.get(i).returns()) {
				casetrue = false;
			}
			i++;
		}
		
		//Se calculan los returns de todas las cláusulas Default
		int j=0;
		boolean defaulttrue = true;
		while(j < defaultClause.size() && defaulttrue) {
			if (!defaultClause.get(i).returns()) {
				defaulttrue = false;
			}
			j++;
		}
					
		return casetrue && defaulttrue;
	}
}