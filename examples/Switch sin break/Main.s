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
	addiu $sp $sp -76
	sw $ra 68($sp)
	sw $fp 64($sp)
	or $fp $0 $sp
	ori $a0 $0 1
	sw $a0 8($fp)
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)
	ori $a0 $0 0
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)
	ori $a0 $0 3
	sw $a0 16($fp)
	lw $a0 0($fp)
	lw $a1 16($fp)
	nop
	beq $a0 $a1 Main_Main_3
	nop
	ori $a0 $0 2
	sw $a0 20($fp)
	lw $a0 0($fp)
	lw $a1 20($fp)
	nop
	beq $a0 $a1 Main_Main_4
	nop
	ori $a0 $0 1
	sw $a0 24($fp)
	lw $a0 0($fp)
	lw $a1 24($fp)
	nop
	beq $a0 $a1 Main_Main_5
	nop
	ori $a0 $0 4
	sw $a0 28($fp)
	lw $a0 0($fp)
	lw $a1 28($fp)
	nop
	beq $a0 $a1 Main_Main_6
	nop
	j Main_Main_7
	nop
Main_Main_3:
	ori $a0 $0 1
	sw $a0 32($fp)
	lw $a0 32($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 36($fp)
	addiu $sp $sp 4
Main_Main_4:
	ori $a0 $0 2
	sw $a0 40($fp)
	lw $a0 40($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 44($fp)
	addiu $sp $sp 4
Main_Main_5:
	ori $a0 $0 3
	sw $a0 48($fp)
	lw $a0 48($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 52($fp)
	addiu $sp $sp 4
Main_Main_6:
	ori $a0 $0 4
	sw $a0 56($fp)
	lw $a0 56($fp)
	nop
	sw $a0 4($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 60($fp)
	addiu $sp $sp 4
Main_Main_7:
Main_Main_2:
Main_Main_1:
Main_Main_ret:
	sw $v0 72($fp)
	or $sp $0 $fp
	lw $ra 68($sp)
	lw $fp 64($sp)
	addiu $sp $sp 76
	jr $ra
	nop
	.end	Main_Main

