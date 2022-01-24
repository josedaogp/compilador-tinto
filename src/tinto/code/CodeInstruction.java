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
 * Clase que describe una instrucci�n en c�digo intermedio
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class CodeInstruction implements CodeConstants {

	/**
	 * C�digo de la instrucci�n
	 */
	private int kind;

	/**
	 * Direcci�n donde se almacena el resultado
	 */
	private CodeAddress target;

	/**
	 * Direcci�n del primer operando
	 */
	private CodeAddress source1;

	/**
	 * Direcci�n del segundo operando
	 */
	private CodeAddress source2;

	/**
	 * Constructor
	 * @param kind
	 */
	public CodeInstruction(int kind, CodeAddress target, CodeAddress source1, CodeAddress source2) 
	{
		this.kind = kind;
		this.target = target;
		this.source1 = source1;
		this.source2 = source2;
	}

	/**
	 * Obtiene el c�digo de la instrucci�n
	 * @return
	 */
	public int getKind() 
	{
		return this.kind;
	}

	/**
	 * Obtiene la direcci�n de destino de la instrucci�n
	 * @return
	 */
	public CodeAddress getTarget() 
	{
		return this.target;
	}

	/**
	 * Obtiene la direcci�n del primer operando
	 * @return
	 */
	public CodeAddress getSource1() 
	{
		return this.source1;
	}

	/**
	 * Obtiene la direcci�n del segundo operando
	 * @return
	 */
	public CodeAddress getSource2() 
	{
		return this.source2;
	}

	/**
	 * Obtiene la descripci�n de la instrucci�n con comentarios
	 */
	public String toString() 
	{
		String code = getCode();
		String comment = getComment();
		if(comment == null || comment.length() == 0) return code;
		StringBuffer buf = new StringBuffer(code);
		for(int i=code.length(); i<50; i++) buf.append(' ');
		buf.append("; ");
		buf.append(comment);
		return buf.toString();
	}

	/**
	 * Obtiene el nombre correspondiente al tipo de la instrucci�n 
	 * @return
	 */
	private String getInstructionName() 
	{
		switch(kind) 
		{
		case LABEL:  return "";
		case ASSIGN: return "assign";
		case ADD:    return "add";
		case SUB:    return "sub";
		case MUL:    return "mul";
		case DIV:    return "div";
		case MOD:    return "mod";
		case INV:    return "inv";
		case AND:    return "and";
		case OR:     return "or";
		case NOT:    return "not";
		case JMPEQ:  return "jmpeq";
		case JMPNE:  return "jmpne";
		case JMPGT:  return "jmpgt";
		case JMPGE:  return "jmpge";
		case JMPLT:  return "jmplt";
		case JMPLE:  return "jmple";
		case JUMP:   return "jump";
		case JMP1:   return "jmp1";
		case PARAM:  return "param";
		case CALL:   return "call";
		case RETURN: return "return";
		case PRECALL:  return "precall";
		default: return "";
		}
	}

	/**
	 * Obtiene la descripci�n de la instrucci�n sin comentarios
	 * @return
	 */
	private String getCode()
	{
		String tg = (target == null? "": target.toString());
		String s1 = (source1 == null? "": source1.toString());
		String s2 = (source2 == null? "": source2.toString());

		String inst = "\t"+getInstructionName();
		switch(kind) {
		case LABEL:  
			return tg+":";
		case ASSIGN: 
		case INV:
		case NOT:
		case JMP1:
		case PARAM:
		case CALL:
			return inst+" "+tg+", "+s1;
		case ADD:
		case SUB:
		case MUL:
		case DIV:
		case MOD:
		case AND:
		case OR:
		case JMPEQ:
		case JMPNE:
		case JMPGT:
		case JMPGE:
		case JMPLT:
		case JMPLE:
			return inst+" "+tg+", "+s1+", "+s2;
		case JUMP: 
		case RETURN:
		case PRECALL:
			return inst+" "+tg;
		default: 
			return "";
		}
	}

	/**
	 * A�ade el comentario de la instrucci�n
	 * @param comment
	 */
	private String getComment() 
	{
		String tg = (target == null? "": target.getDescription());
		String s1 = (source1 == null? "": source1.getDescription());
		String s2 = (source2 == null? "": source2.getDescription());
		switch(kind) 
		{
		case LABEL: 
			return "";  
		case ASSIGN: 
			return tg+" <- "+s1;
		case INV:
			return tg+" <-  -"+s1;	
		case NOT:
			return tg+" <- ! "+s1;
		case JMP1:
			return "if("+s1+"==1) jump "+tg;
		case PARAM:
			return "param["+s1+"] <- "+tg;
		case CALL:
			return tg+" <- "+s1+"()";
		case ADD:
			return tg+" <- "+s1+" + "+s2;
		case SUB:
			return tg+" <- "+s1+" - "+s2;
		case MUL:
			return tg+" <- "+s1+" * "+s2;
		case DIV:
			return tg+" <- "+s1+" / "+s2;
		case MOD:
			return tg+" <- "+s1+" % "+s2;
		case AND:
			return tg+" <- "+s1+" && "+s2;
		case OR:
			return tg+" <- "+s1+" || "+s2;
		case JMPEQ:
			return "if("+s1+" == "+s2+") jump "+tg;
		case JMPNE:
			return "if("+s1+" != "+s2+") jump "+tg;
		case JMPGT:
			return "if("+s1+" > "+s2+") jump "+tg;
		case JMPGE:
			return "if("+s1+" >= "+s2+") jump "+tg;
		case JMPLT:
			return "if("+s1+" < "+s2+") jump "+tg;
		case JMPLE:
			return "if("+s1+" <= "+s2+") jump "+tg;
		case JUMP: 
			return "jump "+tg;
		case RETURN:
			return "return "+tg;
		default: 
			return "";
		}
	}

}
