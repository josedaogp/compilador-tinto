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
	addiu $sp $sp -64
	sw $ra 56($sp)
	sw $fp 52($sp)
	or $fp $0 $sp
	ori $a0 $0 0
	sw $a0 8($fp)
	lw $a0 8($fp)
	nop
	sw $a0 0($fp)
	ori $a0 $0 200
	sw $a0 12($fp)
	lw $a0 12($fp)
	nop
	sw $a0 4($fp)
Main_Main_2:
Main_Main_3:
	ori $a0 $0 1
	sw $a0 24($fp)
	lw $a0 4($fp)
	lw $a1 24($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 20($fp)
	lw $a0 20($fp)
	nop
	sw $a0 4($fp)
	ori $a0 $0 5
	sw $a0 28($fp)
	lw $a0 0($fp)
	lw $a1 28($fp)
	nop
	beq $a0 $a1 Main_Main_6
	nop
	j Main_Main_7
	nop
Main_Main_6:
	j Main_Main_1
	nop
Main_Main_7:
	ori $a0 $0 1
	sw $a0 36($fp)
	lw $a0 0($fp)
	lw $a1 36($fp)
	nop
	addu $v0 $a0 $a1
	sw $v0 32($fp)
	lw $a0 32($fp)
	nop
	sw $a0 0($fp)
	addiu $sp $sp -4
	lw $a0 4($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_0
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 40($fp)
	addiu $sp $sp 4
	addiu $sp $sp -4
	ori $a0 $0 32
	sw $a0 44($fp)
	lw $a0 44($fp)
	nop
	sw $a0 0($sp)
	jal Console_print_1
	nop
	lw $v0 -4($sp)
	nop
	sw $v0 48($fp)
	addiu $sp $sp 4
	ori $a0 $0 10
	sw $a0 16($fp)
	lw $a0 0($fp)
	lw $a1 16($fp)
	nop
	slt $v0 $a0 $a1
	bne $v0 $0 Main_Main_4
	nop
	j Main_Main_5
	nop
Main_Main_4:
	j Main_Main_3
	nop
Main_Main_5:
Main_Main_1:
Main_Main_ret:
	sw $v0 60($fp)
	or $sp $0 $fp
	lw $ra 56($sp)
	lw $fp 52($sp)
	addiu $sp $sp 64
	jr $ra
	nop
	.end	Main_Main

