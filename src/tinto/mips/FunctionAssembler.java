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

package tinto.mips;

import java.io.PrintStream;
import tinto.code.*;
import tinto.mips.instructions.Instruction;
import tinto.mips.instructions.InstructionFactory;
import java.util.Vector;
import java.util.Stack;

/**
 * Clase que contiene la descripci�n una funci�n en ensamblador
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class FunctionAssembler {

	//----------------------------------------------------------------//
	//                        Miembros privados                       //
	//----------------------------------------------------------------//

	/**
	 * Etiqueta de comienzo del m�todo
	 */
	private String label;

	/**
	 * Tama�o del registro de activaci�n (en bytes)
	 */
	private int size;

	/**
	 * Pila de los tama�os de PRECALL. Es necesaria para conocer el
	 * desplazamiento del Stack Pointer a la vuelta de una llamada
	 */
	private Stack<CodeLiteral> callstack;

	/**
	 * Lista de instrucciones
	 */
	private Instruction[] list;

	//----------------------------------------------------------------//
	//                            Constructor                         //
	//----------------------------------------------------------------//

	/**
	 * Constructor
	 * 
	 * @param label
	 */
	public FunctionAssembler(FunctionCodification codif) 
	{
		this.label = codif.getFunctionLabel();
		this.size = codif.getFrameSize();
		this.callstack = new Stack<CodeLiteral>();
		this.list = createAssembler(codif);
	}

	//----------------------------------------------------------------//
	//                        M�todos p�blicos                        //
	//----------------------------------------------------------------//

	/**
	 * Obtiene el nombre de la etiqueta de la funci�n
	 * 
	 * @return
	 */
	public String getMethodLabel() 
	{
		return this.label;
	}

	/**
	 * Obtiene la lista de instrucciones de la funci�n
	 * 
	 * @return
	 */
	public Instruction[] getInstructionList() 
	{
		return this.list;
	}

	/**
	 * Escribe el c�digo completo de la funci�n sobre un flujo
	 * 
	 * @return
	 */
	public void print(PrintStream stream) 
	{
		stream.println("#------------------------------------------------------------------");
		stream.println("# " + label);
		stream.println("#------------------------------------------------------------------");
		stream.println();
		stream.println("\t.globl\t" + label);
		stream.println("\t.ent\t" + label);

		for (int i = 0; i < list.length; i++)
			stream.println(list[i].getAssembler());

		stream.println("\t.end\t" + label);
		stream.println();
	}

	//----------------------------------------------------------------//
	//                         M�todos privados                       //
	//----------------------------------------------------------------//

	/**
	 * Traduce el c�digo intermedio de la funci�n a c�digo ensamblador
	 * 
	 * @param codif
	 * @return
	 */
	private Instruction[] createAssembler(FunctionCodification codif) 
	{
		Vector<Instruction> vector = new Vector<Instruction>();
		int retsize = 4;

		// Instrucciones de entrada a una funci�n:
		//
		// function_label:              # Etiqueta de salto a la funci�n
		// addiu $sp $sp -size          # Reserva espacio para el registro de activaci�n
		// sw $ra (size-8)($fp)         # Almacena la direcci�n de retorno
		// sw $fp (size-12)($fp)        # Almacena el frame pointer
		// move $fp $sp                 # Asigna el nuevo frame pointer
		//
		vector.add(InstructionFactory.createLabel(label));
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, -size));
		vector.add(InstructionFactory.createSW(RegisterSet.ra, RegisterSet.sp,size - retsize - 4)); 
		vector.add(InstructionFactory.createSW(RegisterSet.fp, RegisterSet.sp, size - retsize - 8));
		vector.add(InstructionFactory.createMOVE(RegisterSet.fp, RegisterSet.sp)); 

		// Traduce a ensamblador el cuerpo del m�todo
		CodeInstruction codelist[] = codif.getCodeInstructionList().getList();
		for (int i = 0; i < codelist.length; i++) 
		{
			createAssembler(vector, codelist[i]);
		}

		// Instrucciones de salida de una funci�n:
		//
		// function_label_ret:          # Etiqueta de retorno de la funci�n
		// sw $v0 (size-4)($fp)         # Almacena el valor de retorno
		// move $sp $fp                 # Libera todo el registro de activaci�n
		// lw $ra (size-8)($sp)         # Recupera la direcci�n de retorno
		// lw $fp (size-12)($sp)        # Recupera el frame pointer anterior
		// addiu $sp $sp size           # Recupera el stack pointer anterior
		// jr $ra                       # Salta a la direci�n de retorno
		// nop                          # Instrucci�n en blanco
		//
		vector.add(InstructionFactory.createLabel(label + "_ret"));  
		vector.add(InstructionFactory.createSW(RegisterSet.v0, RegisterSet.fp,(size - retsize)));  
		vector.add(InstructionFactory.createMOVE(RegisterSet.sp, RegisterSet.fp)); 
		vector.add(InstructionFactory.createLW(RegisterSet.ra, RegisterSet.sp, (size - retsize - 4))); 
		vector.add(InstructionFactory.createLW(RegisterSet.fp, RegisterSet.sp, (size - retsize - 8))); 
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp,RegisterSet.sp, size));  
		vector.add(InstructionFactory.createJR(RegisterSet.ra)); 
		vector.add(InstructionFactory.createNOP());

		Instruction[] list = new Instruction[vector.size()];
		vector.toArray(list);
		return list;
	}

	/**
	 * Genera la descripci�n en ensamblador de cada una de las instrucciones del
	 * c�digo intermedio
	 * 
	 * @param vector
	 * @param inst
	 */
	private void createAssembler(Vector<Instruction> vector,CodeInstruction inst) 
	{
		int kind = inst.getKind();
		CodeAddress target = inst.getTarget();
		CodeAddress source1 = inst.getSource1();
		CodeAddress source2 = inst.getSource2();

		switch (kind) 
		{
		case CodeConstants.LABEL:
			translateLabel(vector, target);
			break;
		case CodeConstants.ASSIGN:
			translateASSIGN(vector, target, source1);
			break;
		case CodeConstants.ADD:
			translateADD(vector, target, source1, source2);
			break;
		case CodeConstants.SUB:
			translateSUB(vector, target, source1, source2);
			break;
		case CodeConstants.MUL:
			translateMUL(vector, target, source1, source2);
			break;
		case CodeConstants.DIV:
			translateDIV(vector, target, source1, source2);
			break;
		case CodeConstants.MOD:
			translateMOD(vector, target, source1, source2);
			break;
		case CodeConstants.INV:
			translateINV(vector, target, source1);
			break;
		case CodeConstants.AND:
			translateAND(vector, target, source1, source2);
			break;
		case CodeConstants.OR:
			translateOR(vector, target, source1, source2);
			break;
		case CodeConstants.NOT:
			translateNOT(vector, target, source1);
			break;
		case CodeConstants.JMPEQ:
			translateJMPEQ(vector, target, source1, source2);
			break;
		case CodeConstants.JMPNE:
			translateJMPNE(vector, target, source1, source2);
			break;
		case CodeConstants.JMPGT:
			translateJMPGT(vector, target, source1, source2);
			break;
		case CodeConstants.JMPGE:
			translateJMPGE(vector, target, source1, source2);
			break;
		case CodeConstants.JMPLT:
			translateJMPLT(vector, target, source1, source2);
			break;
		case CodeConstants.JMPLE:
			translateJMPLE(vector, target, source1, source2);
			break;
		case CodeConstants.JUMP:
			translateJUMP(vector, target);
			break;
		case CodeConstants.JMP1:
			translateJMP1(vector, target, source1);
			break;
		case CodeConstants.PARAM:
			translatePARAM(vector, target, source1);
			break;
		case CodeConstants.PRECALL:
			translatePRECALL(vector, target);
			break;
		case CodeConstants.CALL:
			translateCALL(vector, target, source1);
			break;
		case CodeConstants.RETURN:
			translateRETURN(vector, target);
			break;
		}
	}

	//----------------------------------------------------------------//
	//                  Traducci�n de las etiquetas                   //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para una etiqueta
	 * 
	 * @param vector
	 * @param label
	 */
	private void translateLabel(Vector<Instruction> vector, CodeAddress label) 
	{
		CodeLabel codelb = (CodeLabel) label;
		vector.add(InstructionFactory.createLabel(codelb.toString()));
	}

	//----------------------------------------------------------------//
	//     Traducci�n de las instrucciones aritm�ticas de tipo int    //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para una asignaci�n de tipo int
	 * 
	 * @param vector
	 * @param target
	 * @param source
	 */
	private void translateASSIGN(Vector<Instruction> vector,CodeAddress target, CodeAddress source) 
	{
		Register target_reg = getTargetRegister(target, RegisterSet.a0);
		Register reg = translateDelayedLoadIntValue(vector, source, target_reg);
		translateStoreIntValue(vector, target, reg);
	}

	/**
	 * Genera el c�digo ensamblador para una suma entera
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateADD(Vector<Instruction> vector, CodeAddress target,CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createADDU(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para una resta entera
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateSUB(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createSUBU(target_reg, source1_reg,source2_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para una multiplicaci�n entera
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateMUL(Vector<Instruction> vector, CodeAddress target,CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1,RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2,RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createMULT(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para una divisi�n entera
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateDIV(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFLO(target_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para el resto de una divisi�n entera
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateMOD(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createDIV(source1_reg, source2_reg));
		vector.add(InstructionFactory.createMFHI(target_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para un cambio de signo
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateINV(Vector<Instruction> vector, CodeAddress target, CodeAddress source) 
	{
		Register source_reg = translateDelayedLoadIntValue(vector, source, RegisterSet.a0);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createSUBU(target_reg, RegisterSet.r0, source_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	//----------------------------------------------------------------//
	//             Traducci�n de las instrucciones booleanas          //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para una conjunci�n l�gica
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateAND(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createAND(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para una disyunci�n l�gica
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateOR(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createOR(target_reg, source1_reg, source2_reg));
		translateStoreIntValue(vector, target, target_reg);
	}

	/**
	 * Genera el c�digo ensamblador para una negaci�n l�gica
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateNOT(Vector<Instruction> vector, CodeAddress target,CodeAddress source1) 
	{ 
		Register source1_reg = translateDelayedLoadIntValue(vector, source1, RegisterSet.a0);
		Register target_reg = getTargetRegister(target, RegisterSet.v0);
		vector.add(InstructionFactory.createSLTI(target_reg, source1_reg, 1));
		translateStoreIntValue(vector, target, target_reg);
	}

	//----------------------------------------------------------------//
	//      Instrucciones de salto condicional para el tipo int       //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source1 == source2)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPEQ(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createBEQ(source1_reg, source2_reg, label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source1 != source2)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPNE(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createBNE(source1_reg, source2_reg, label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source1 > source2)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPGT(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source1 >= source2)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPGE(Vector<Instruction> vector, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source1 < source2)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPLT(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2,RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source1_reg, source2_reg));
		vector.add(InstructionFactory.createBNE(RegisterSet.v0, RegisterSet.r0,	label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional si source1 <=
	 * source2
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMPLE(Vector<Instruction> vector, CodeAddress target,	CodeAddress source1, CodeAddress source2) 
	{
		Register source1_reg = translateLoadIntValue(vector, source1, RegisterSet.a0);
		Register source2_reg = translateDelayedLoadIntValue(vector, source2, RegisterSet.a1);
		String label = target.toString();
		vector.add(InstructionFactory.createSLT(RegisterSet.v0, source2_reg, source1_reg));
		vector.add(InstructionFactory.createBEQ(RegisterSet.v0, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	//----------------------------------------------------------------//
	//                  Otras instrucciones de salto                  //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para un salto incondicional
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJUMP(Vector<Instruction> vector, CodeAddress target) 
	{
		String label = target.toString();
		vector.add(InstructionFactory.createJ(label));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo ensamblador para un salto condicional 
	 * jump if(source != 0)
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateJMP1(Vector<Instruction> vector, CodeAddress target, CodeAddress source1) 
	{
		Register source1_reg = translateDelayedLoadIntValue(vector, source1, RegisterSet.a0);
		String label = target.toString();
		vector.add(InstructionFactory.createBNE(source1_reg, RegisterSet.r0, label));
		vector.add(InstructionFactory.createNOP());
	}

	//----------------------------------------------------------------//
	//                Instrucciones de manejo de funciones            //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo ensamblador para una instrucci�n "return temp"
	 * 
	 * @param vector
	 * @param target
	 * @param source1
	 * @param source2
	 */
	private void translateRETURN(Vector<Instruction> vector, CodeAddress target) 
	{
		Register r = translateLoadIntValue(vector, target, RegisterSet.v0);
		if (r != RegisterSet.v0)
			vector.add(InstructionFactory.createMOVE(RegisterSet.v0, r));
		vector.add(InstructionFactory.createJ(this.label + "_ret"));
		vector.add(InstructionFactory.createNOP());
	}

	/**
	 * Genera el c�digo para almacenar el valor de un argumento de la pr�xima
	 * llamada
	 * 
	 * @param vector
	 *            Lista de instrucciones
	 * @param target
	 *            Variable que contiene el valor del argumento
	 * @param source
	 *            Posici�n del argumento respecto al Stack Pointer
	 */
	private void translatePARAM(Vector<Instruction> vector, CodeAddress target, CodeAddress source) {
		Register r = translateDelayedLoadIntValue(vector, target, RegisterSet.a0);
		String hex = ((CodeLiteral) source).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createSW(r, RegisterSet.sp, offset));
	}

	/**
	 * Genera el c�digo previo a una llamada a una funci�n (desplazamiento del
	 * Stack Pointer) conocido los argumentos de la llamada
	 * 
	 * @param vector
	 * @param target
	 */
	private void translatePRECALL(Vector<Instruction> vector, CodeAddress target) 
	{
		callstack.push((CodeLiteral) target);
		String hex = ((CodeLiteral) target).getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp,RegisterSet.sp, -offset));
	}

	/**
	 * Genera el c�digo ensamblador para una instrucci�n "call target function"
	 * 
	 * @param vector
	 *            Lista de instrucciones
	 * @param target
	 *            Variable en la que almacenar el resultado
	 * @param function
	 *            Etiqueta de salto a la funci�n
	 */
	private void translateCALL(Vector<Instruction> vector, CodeAddress target,	CodeAddress function) 
	{
		vector.add(InstructionFactory.createJAL(function.toString()));
		vector.add(InstructionFactory.createNOP());
		vector.add(InstructionFactory.createLW(RegisterSet.v0, RegisterSet.sp,	-4));
		vector.add(InstructionFactory.createNOP());
		translateStoreIntValue(vector, target, RegisterSet.v0);
		CodeLiteral argsize = callstack.pop();
		String hex = argsize.getHexDescription();
		int offset = Integer.parseInt(hex, 16);
		vector.add(InstructionFactory.createADDIU(RegisterSet.sp, RegisterSet.sp, offset));
	}

	//----------------------------------------------------------------//
	//     Instrucciones de intercambio a memoria para el tipo int    //
	//----------------------------------------------------------------//

	/**
	 * Genera el c�digo para volcar un valor (un literal o una variable) en un
	 * registro
	 * 
	 * @param vector
	 *            Lista de instrucciones
	 * @param address
	 *            Referencia al valor a almacenar
	 * @param r
	 *            Registro propuesto para el destino
	 * @return Registro en el que se coloca el valor
	 */
	private Register translateLoadIntValue(Vector<Instruction> vector,	CodeAddress address, Register r) 
	{
		if (address instanceof CodeLiteral)  // Referencia a un valor constante
		{
			return translateLoadLiteral(vector, (CodeLiteral) address, r);
		}
		else  // Referencia a una variable
		{
			CodeVariable svar = (CodeVariable) address;
			if (svar.inRegister())  // Variables almacenadas en registro
			{
				return RegisterSet.getRegister(svar.getLocation());
			} 
			else // Variables almacenadas en el registro de activaci�n de la funci�n
			{ 
				vector.add(InstructionFactory.createLW(r, RegisterSet.fp, svar.getLocation()));
				return r;
			}
		}
	}

	/**
	 * Genera el c�digo para volcar un valor (un literal o una variable) en un
	 * registro
	 * 
	 * @param vector
	 *            Lista de instrucciones
	 * @param address
	 *            Referencia al valor a almacenar
	 * @param r
	 *            Registro propuesto para el destino
	 * @return Registro en el que se coloca el valor
	 */
	private Register translateDelayedLoadIntValue(Vector<Instruction> vector,CodeAddress address, Register r) 
	{
		if (address instanceof CodeLiteral)  // Referencia a un valor constante
		{
			return translateLoadLiteral(vector, (CodeLiteral) address, r);
		} 
		else  // Referencia a una variable
		{
			CodeVariable svar = (CodeVariable) address;
			if (svar.inRegister())  // Variables almacenadas en registro
			{
				return RegisterSet.getRegister(svar.getLocation());
			} 
			else  // Variables almacenadas en el registro de activaci�n de la funci�n
			{
				vector.add(InstructionFactory.createLW(r, RegisterSet.fp,svar.getLocation()));
				vector.add(InstructionFactory.createNOP());
				return r;
			}
		}
	}

	/**
	 * Genera el c�digo para colocar un valor constante (literal) en un registro
	 * 
	 * @param vector
	 * @param literal
	 * @param r
	 * @return
	 */
	private Register translateLoadLiteral(Vector<Instruction> vector, CodeLiteral literal, Register r) 
	{
		String hex = literal.getHexDescription();
		int value = Integer.parseInt(hex, 16);
		if (value > 0x0FFFF)  // Valores mayores de 16 bits
		{
			int upper = (value & 0xFFFF0000) >> 16;
			int lower = (value & 0x0000FFFF);
			vector.add(InstructionFactory.createLUI(r, upper));
			vector.add(InstructionFactory.createORI(r, r, lower));
		} 
		else  // Valores menores de 16 bits
		{
			vector.add(InstructionFactory.createLI(r, value));
		}
		return r;
	}

	/**
	 * Obtiene el registro donde almacenar el resultado de una operaci�n. Si la
	 * variable de destino se almacena en un registro entonces devuelve dicho
	 * registro. Si la variable de destino se almacena en memoria devuelve el
	 * registro destino propuesto.
	 * 
	 * @param address
	 *            Variable de destino de la operaci�n
	 * @param r
	 *            Registro propuesto como destino
	 * @return
	 */
	private Register getTargetRegister(CodeAddress address, Register r) 
	{
		CodeVariable tvar = (CodeVariable) address;
		if (tvar.inRegister())
			return RegisterSet.getRegister(tvar.getLocation());
		else
			return r;
	}

	/**
	 * Genera el c�digo para almacenar un valor en una variable
	 * 
	 * @param vector
	 *            Lista de instrucciones
	 * @param address
	 *            Referencia a la variable
	 * @param r
	 *            Registro en el que se encuentra el valor a almacenar
	 */
	private void translateStoreIntValue(Vector<Instruction> vector, CodeAddress address, Register r) 
	{
		CodeVariable tvar = (CodeVariable) address;
		if (tvar.inRegister() && tvar.getLocation() != r.getCode()) 
		{ 
			Register target = RegisterSet.getRegister(tvar.getLocation());
			vector.add(InstructionFactory.createMOVE(target, r));
		} 
		else if (!tvar.inRegister())
		{ // Variables almacenadas en el registro de activaci�n de una funci�n
			vector.add(InstructionFactory.createSW(r, RegisterSet.fp, tvar.getLocation()));
		}
	}

}
