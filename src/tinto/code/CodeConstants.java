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

package tinto.code;

/**
 * Interfaz que describe los c�digos de todas las instrucciones
 * 
 * @author Francisco Jos� Moreno Velo
 */
public interface CodeConstants {
	
	//----------------------------------------------------------------//
	// Tipos de instrucciones en c�digo intermedio                    //
	//----------------------------------------------------------------//

	/**
	 * Etiqueta
	 */
	public int LABEL = 1;
	
	/**
	 * Instrucci�n: Asignaci�n
	 */
	public int ASSIGN = 2;
	
	/**
	 * Instrucci�n: Suma
	 */
	public int ADD = 3;
		
	/**
	 * Instrucci�n: Resta
	 */
	public int SUB = 4;
	
	/**
	 * Instrucci�n: Multiplicaci�n
	 */
	public int MUL = 5;
	
	/**
	 * Instrucci�n: Divisi�n
	 */
	public int DIV = 6;
	
	/**
	 * Instrucci�n: M�dulo
	 */
	public int MOD = 7;
	
	/**
	 * Instrucci�n: Cambio de signo
	 */
	public int INV = 8;
	
	/**
	 * Instrucci�n: Conjunci�n de datos booleanos
	 */
	public int AND = 9;
	
	/**
	 * Instrucci�n: Disyunci�n de datos booleanos
	 */
	public int OR = 10;

	/**
	 * Instrucci�n: NOT
	 */
	public int NOT = 11;
	
	/**
	 * Instrucci�n: Salto incondicional
	 */
	public int JUMP = 12;

	/**
	 * Instrucci�n: Salto condicionado a la igualdad entre dos datos
	 */
	public int JMPEQ = 13;
	
	/**
	 * Instrucci�n: Salto condicionado a la desigualdad entre dos datos
	 */
	public int JMPNE = 14;
	
	/**
	 * Instrucci�n: Salto condicionado a la comparaci�n "mayor" 
	 * entre dos datos
	 */
	public int JMPGT = 15;
	
	/**
	 * Instrucci�n: Salto condicionado a la comparaci�n "mayor o igual" 
	 * entre dos datos
	 */
	public int JMPGE = 16;
	
	/**
	 * Instrucci�n: Salto condicionado a la comparaci�n "menor" 
	 * entre dos datos
	 */
	public int JMPLT = 17;
	
	/**
	 * Instrucci�n: Salto condicionado a la comparaci�n "menor o igual" 
	 * entre dos datos
	 */
	public int JMPLE = 18;
	
	/**
	 * Instrucci�n: Salto condicional cuando una variable es 1 (true)
	 */
	public int JMP1 = 19;
	
	/**
	 * Instrucci�n: Almacenamiento de un par�metro anterior a
	 * una llamada a una funci�n
	 */
	public int PARAM = 20;

	/**
	 * Instrucci�n: Llamada a una funci�n
	 */
	public int CALL = 21;

	/**
	 * Instrucci�n: Devoluci�n de un valor en una funci�n
	 */
	public int RETURN = 22;

	/**
	 * Instrucci�n: Notificaci�n de una pr�xima llamada a funci�n colocada
	 * antes del c�lculo del valor de los argumentos
	 */
	public int PRECALL = 23;

}
