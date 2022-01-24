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

package tinto.mips.instructions;

import tinto.mips.DispRegister;
import tinto.mips.Register;

/**
 * Clase que describe una instrucci�n sobre dos registros y un desplazamiento
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class RDRInstruction extends Instruction {

	/**
	 * Referencia al registro de destino de la instrucci�n
	 */
	private Register target;

	/**
	 * Referencia al registro desplazado
	 */
	private DispRegister source;

	/**
	 * Constructor basado en las referencias a los registros
	 */
	public RDRInstruction(int code, Register target, DispRegister s) 
	{
		super(code);
		this.target = target;
		this.source = s;
	}

	/**
	 * Constructor basado en las referencias a los registros
	 */
	public RDRInstruction(int code, Register target, Register s, int disp) 
	{
		super(code);
		this.target = target;
		this.source = new DispRegister(disp, s);
	}

	/**
	 * Constructor basado en los c�digos de los registros
	 */
	public RDRInstruction(int code, int target, int source, int disp) 
	{
		super(code);
		this.target = new Register(target);
		this.source = new DispRegister(disp, source);
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
	 * Obtiene la referencia al registro desplazado
	 * 
	 * @return
	 */
	public DispRegister getSource() 
	{
		return this.source;
	}

	/**
	 * Obtiene la representaci�n de la instrucci�n en ensamblador
	 * 
	 * @return
	 */
	public String getAssembler() 
	{
		String asm = "\t" + getInstructionName();
		asm += " " + target.getName();
		asm += " " + source.getName();
		return asm;
	}

}
