//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2017, Francisco Jos� Moreno Velo                   //
// All rights reserved.                                             //
//                                                                  //
// Redistribution and use in source and binary forms, with or       //
// without modification, are permitted provided that the following  //
// conditions are met:                                              //
//                                                                  //
// * Redistributions of source code must retain the above copyright //
//   notice, this list of conditions and the following disclaimer.  // 
//                                                                  //
// * Redistributions in binary form must reproduce the above        // 
//   copyright notice, this list of conditions and the following    // 
//   disclaimer in the documentation and/or other materials         // 
//   provided with the distribution.                                //
//                                                                  //
// * Neither the name of the University of Huelva nor the names of  //
//   its contributors may be used to endorse or promote products    //
//   derived from this software without specific prior written      // 
//   permission.                                                    //
//                                                                  //
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND           // 
// CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,      // 
// INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF         // 
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE         // 
// DISCLAIMED. IN NO EVENT SHALL THE COPRIGHT OWNER OR CONTRIBUTORS //
// BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,         // 
// EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED  //
// TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,    //
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND   // 
// ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT          //
// LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING   //
// IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF   //
// THE POSSIBILITY OF SUCH DAMAGE.                                  //
//------------------------------------------------------------------//

//------------------------------------------------------------------//
//                      Universidad de Huelva                       //
//          Departamento de Tecnolog�as de la Informaci�n           //
//   �rea de Ciencias de la Computaci�n e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.ast.expression;

import tinto.ast.Type;

/**
 * Clase que describe un operaci�n unaria
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class UnaryExpression extends OperatorExpression {

	//----------------------------------------------------------------//
	//                       Constantes p�blicas                      //
	//----------------------------------------------------------------//

	/**
	 * C�digo para indicar que no hay operador unario
	 */
	public static final int NONE = 0;

	/**
	 * Operador: PLUS
	 */
	public static final int PLUS = 1;

	/**
	 * Operador: MINUS
	 */
	public static final int MINUS = 2;

	/**
	 * Operador: NOT
	 */
	public static final int NOT = 3;

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * C�digo del operador
	 */
	private int op;
	
	/**
	 * Expresi�n a la que se aplica el operador
	 */
	private Expression exp;
	
	//----------------------------------------------------------------//
	//                            Constructores                       //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 */
	public UnaryExpression(int op, Expression exp) 
	{
		super(computeType(op,exp));
		this.op = op;
		this.exp = exp;
	}
	
	//----------------------------------------------------------------//
	//                          M�todos p�blicos                      //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el c�digo del operador
	 * @return
	 */
	public int getOperator() 
	{
		return this.op;
	}
	
	/**
	 * Obtiene la referencia a la expresi�n
	 * @return
	 */
	public Expression getExpression() 
	{
		return this.exp;
	}
	
	//----------------------------------------------------------------//
	//                          M�todos privados                      //
	//----------------------------------------------------------------//

	/**
	 * Calcula el tipo de dato de la expresi�n unaria
	 * @param op
	 * @param exp
	 * @return
	 */
	private static int computeType(int op, Expression exp)
	{
		switch(op)
		{
		case NOT: 
			return Type.BOOLEAN_TYPE;
		case MINUS: 
			return exp.getType();
		default: 
			return exp.getType();
		}
	}
	
}
