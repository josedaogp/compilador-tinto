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

import java.io.PrintStream;

import tinto.ast.struct.Variable;

/**
 * Clase que describe toda la informaci�n asociada a la codificaci�n de una funci�n
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class FunctionCodification implements CodeConstants {

	/**
	 * Etiqueta de comienzo de la funci�n
	 */
	private String label;

	/**
	 * Tipo de dato que devuelve la funci�n
	 */
	private int type;

	/**
	 * Contador utilizado por el generador de etiquetas
	 */
	private int labelcount;

	/**
	 * Contador utilizado por el generador de variables temporales
	 */
	private int tmpcount;

	/**
	 * Lista de variables del registro de activaci�n de la funci�n
	 */
	private CodeVariable[] var;

	/**
	 * Lista de argumentos de entrada de la funci�n
	 */
	private CodeVariable[] arg;

	/**
	 * Lista de variables descritas en el �rbol de Sintaxis Abstracta
	 */
	private Variable[] astvar;

	/**
	 * Lista de instrucciones
	 */
	private CodeInstructionList list;

	/**
	 * Constructor
	 * @param label	Etiqueta de identificaci�n de la funci�n
	 * @param type	Tipo de dato que devuelve la funci�n
	 * @param argument	Lista de argumentos de llamada
	 * @param local	Lista de variables locales definidas en el c�digo de la funci�n
	 */
	public FunctionCodification(String label, int type, Variable[] argument, Variable[] local) 
	{
		this.label = label;
		this.type = type;
		this.labelcount = 0;
		this.tmpcount = 0;
		this.list = new CodeInstructionList();
		this.var = new CodeVariable[argument.length+local.length];
		this.astvar = new Variable[argument.length+local.length];
		this.arg = new CodeVariable[argument.length];
		for(int i=0; i<argument.length; i++) 
		{
			this.var[i] = new CodeVariable("arg_"+i,argument[i].getName());
			this.astvar[i] = argument[i];
			this.arg[i] = this.var[i];
		}
		int indent = argument.length;
		for(int i=0; i<local.length; i++) 
		{
			this.var[indent+i] = new CodeVariable("local_"+i,local[i].getName());
			this.astvar[indent+i] = local[i];
		}
	}

	/**
	 * Obtiene el nombre de la etiqueta de la funci�n
	 * @return
	 */
	public String getFunctionLabel() 
	{
		return this.label;
	}

	/**
	 * Devuelve el tipo de dato de la funci�n
	 * @return
	 */
	public int getType() 
	{
		return this.type;
	}

	/**
	 * Obtiene el tama�o del registro de activaci�n 
	 * @return
	 */
	public int getFrameSize() 
	{
		int framesize = 12; // ra + fp + return
		for(int i=arg.length; i<var.length; i++) 
		{
			if(var[i].inRegister()) continue;
			framesize += 4;
		}

		int argloc = framesize;
		for(int i=0; i<arg.length; i++) 
		{
			var[i].setLocation(argloc);
			argloc += 4;
		}

		int varloc = 0;
		for(int i=arg.length; i<var.length; i++) 
		{
			if(var[i].inRegister()) continue;
			var[i].setLocation(varloc);
			varloc += 4;
		}
		return framesize;
	}

	/**
	 * Obtiene la lista de instrucciones de la funci�n
	 * @return
	 */
	public CodeInstructionList getCodeInstructionList() 
	{
		return this.list;
	}

	/**
	 * Obtiene una nueva etiqueta para la funci�n
	 * @return
	 */
	public CodeLabel getNewLabel() 
	{
		labelcount++;
		return new CodeLabel(label+"_"+labelcount);
	}

	/**
	 * Obtiene una nueva variable temporal 
	 * @return
	 */
	public CodeVariable getNewTemp() 
	{
		String tmpname = "tmp_"+tmpcount;
		CodeVariable tmp = new CodeVariable(tmpname,tmpname);
		tmpcount++;
		CodeVariable[] aux = new CodeVariable[var.length+1];
		System.arraycopy(var,0,aux,0,var.length);
		aux[var.length] = tmp;
		this.var = aux;
		return tmp;
	}

	/**
	 * Obtiene una variable a partir de su referencia en el AST
	 * @return
	 */
	public CodeVariable getVariable(Variable v) 
	{
		for(int i=0; i<astvar.length; i++) if(astvar[i] == v) return var[i];
		return null;
	}

	/**
	 * Obtiene una variable a partir de su posici�n
	 * @param index
	 * @return
	 */
	public CodeVariable getVariable(int index) 
	{
		return var[index];
	}

	/**
	 * Obtiene la lista de argumentos de la funci�n
	 * @return
	 */
	public CodeVariable[] getArguments() 
	{
		return arg;
	}

	/**
	 * Obtiene la lista de variables de la funci�n
	 * @return
	 */
	public CodeVariable[] getVariables() 
	{
		return var;
	}

	/**
	 * Asigna todas las instrucciones 
	 * @param list
	 */
	public void setInstructionList(CodeInstructionList list) 
	{
		this.list = list;
	}

	/**
	 * Escribe el c�digo completo de la funci�n sobre un flujo
	 * 
	 * @return
	 */
	public void print(PrintStream stream) 
	{
		CodeLabel begin = new CodeLabel(label);
		CodeInstruction beginInst = new CodeInstruction(LABEL,begin,null,null);
		stream.println(beginInst);
		CodeInstruction[] instlist = list.getList();
		for(int i=0; i<instlist.length; i++) stream.println(instlist[i]);
	}

}
