//------------------------------------------------------------------//
//                        COPYRIGHT NOTICE                          //
//------------------------------------------------------------------//
// Copyright (c) 2017, Francisco Jos? Moreno Velo                   //
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
//          Departamento de Tecnolog?as de la Informaci?n           //
//   ?rea de Ciencias de la Computaci?n e Inteligencia Artificial   //
//------------------------------------------------------------------//
//                     PROCESADORES DE LENGUAJE                     //
//------------------------------------------------------------------//
//                                                                  //
//                  Compilador del lenguaje Tinto                   //
//                                                                  //
//------------------------------------------------------------------//

package tinto.mips.instructions;

import tinto.mips.Register;

/**
 * Clase que describe una instrucci?n sobre dos registros y un valor inmediato
 * 
 * @author Francisco Jos? Moreno Velo
 */
public class RRIInstruction extends Instruction {

	/**
	 * Referencia al registro de destino de la instrucci?n
	 */
	private Register target;

	/**
	 * Referencia al registro del primer operando
	 */
	private Register source;

	/**
	 * Referencia al valor del segundo operando
	 */
	private int value;

	/**
	 * Constructor basado en las referencias a los registros
	 * 
	 * @param num
	 */
	public RRIInstruction(int code, Register target, Register s1, int value) 
	{
		super(code);
		this.target = target;
		this.source = s1;
		this.value = value;
	}

	/**
	 * Constructor basado en los c?digos de los registros
	 * 
	 * @param num
	 */
	public RRIInstruction(int code, int target, int s, int value) 
	{
		super(code);
		this.target = new Register(target);
		this.source = new Register(s);
		this.value = value;
	}

	/**
	 * Obtiene la referencia al registro de destino
	 * 
	 * @return
	 */
	public Register getTarget() 
	{
		return this.target;
	}

	/**
	 * Obtiene la referencia al registro del primer operando
	 * 
	 * @return
	 */
	public Register getSource() 
	{
		return this.source;
	}

	/**
	 * Obtiene la referencia al valor del segundo operando
	 * 
	 * @return
	 */
	public int getValue() 
	{
		return this.value;
	}

	/**
	 * Obtiene la representaci?n de la instrucci?n en ensamblador
	 * 
	 * @return
	 */
	public String getAssembler() 
	{
		String asm = "\t" + getInstructionName();
		asm += " " + target.getName();
		asm += " " + source.getName();
		asm += " " + value;
		return asm;
	}

}
