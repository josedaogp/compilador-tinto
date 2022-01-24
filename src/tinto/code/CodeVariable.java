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
 * Clase que describe una variable.
 * 
 * @author Francisco Jos� Moreno Velo
 */
public class CodeVariable extends CodeAddress {

	/**
	 * Nombre de la variable
	 */
	private String name;

	/**
	 * Nombre original de la variable
	 */
	private String des;

	/**
	 * Localizaci�n de la variable dentro del registro de activaci�n del m�todo
	 */
	private int location;

	/**
	 * Marcador que indica que la variable se almacena en un registro.
	 * En ese caso location identifica al registro.
	 */
	private boolean inRegister;

	/**
	 * Constructor
	 * @param name
	 * @param des;
	 */
	public CodeVariable(String name, String des) 
	{
		this.name = name;
		this.des = des;
		this.location = 0;
		this.inRegister = false;		
	}

	/**
	 * Obtiene el nombre de la variable
	 * @return
	 */
	public String toString() 
	{
		return this.name;
	}

	/**
	 * Obtiene el nombre original de la variable
	 * @return
	 */
	public String getDescription() 
	{
		return this.des;
	}

	/**
	 * Obtiene la posici�n de la variable.
	 * @return
	 */
	public int getLocation() 
	{
		return this.location;
	}

	/**
	 * Indica si la variable se almacena en un registro
	 * @return
	 */
	public boolean inRegister()
	{
		return this.inRegister;
	}

	/**
	 * Asigna la variable a un registro del procesador
	 * @param reg
	 */
	public void setRegister(int reg) 
	{
		this.inRegister = true;
		this.location = reg;
	}

	/**
	 * Asigna la posici�n de la variable respecto al frame pointer
	 * @param loc
	 */
	public void setLocation(int loc) 
	{
		this.inRegister = false;
		this.location = loc;
	}

}
