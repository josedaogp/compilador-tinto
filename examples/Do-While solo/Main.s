#------------------------------------------------------------------
# Copyright (c) 2017, Francisco José Moreno Velo                   
# All rights reserved.                                             
#------------------------------------------------------------------
#------------------------------------------------------------------
# Main_Main
#------------------------------------------------------------------

	.globl	Main_Main
	.ent	Main_Main
Main_Main:
# PROCESO DE LLAMADA A LA FUNCIÓN MAIN
	addiu $sp $sp -60 
	sw $ra 52($sp)    
	sw $fp 48($sp)    
	or $fp $0 $sp     

	#DECLARACIÓN DE VARIABLE i
	ori $a0 $0 0
	sw $a0 8($fp)
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)

	#DECLARACIÓN DE VARIABLE j
	ori $a0 $0 200
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)

	
Main_Main_2:
Main_Main_3:

	#COMIENZA EL DO
	ori $a0 $0 1
	sw $a0 24($fp)
	lw $a0 4($fp)
	lw $a1 24($fp)
	nop

	#INCREMENTO DE LA i
	addu $v0 $a0 $a1
	sw $v0 20($fp)
	lw $a0 20($fp)
	nop
	sw $a0 4($fp)
	ori $a0 $0 1
	sw $a0 32($fp)
	lw $a0 0($fp)
	lw $a1 32($fp)
	nop

	#INCREMENTO DE LA i
	addu $v0 $a0 $a1
	sw $v0 28($fp)
	lw $a0 28($fp)
	nop
	sw $a0 0($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop

	#IMPRIMIR POR PANTALLA LA J Y EL ESPACIO EN BLANCO
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 36($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	ori $a0 $0 32
	sw $a0 40($fp)
	lw $a0 40($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_1
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 44($fp)
	addiu $sp $sp 4
	ori $a0 $0 10
	sw $a0 16($fp)
	lw $a0 0($fp)
	lw $a1 16($fp)
	nop

	#CONDICIÓN DEL DO-WHILE
	slt $v0 $a0 $a1
	bne $v0 $0 Main_Main_4 #Salta a la etiqueta 4, que luego saltará a la 3 para volver a hacer el bucle
	nop
	j Main_Main_5 #No se cumple la condición y salta al final del programa
	nop
Main_Main_4:
	j Main_Main_3
	nop
Main_Main_5:
Main_Main_1:

# FUNCION RETURN. Devuelve los valores.
Main_Main_ret:
	sw $v0 56($fp)
	or $sp $0 $fp
	lw $ra 52($sp)
	lw $fp 48($sp)
	addiu $sp $sp 60
	jr $ra
	nop
	.end	Main_Main

