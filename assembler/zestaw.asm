	.data
yolo:	.word 20 
	.text
	add $t1,$t2,$t3
	addi $t1,$t2,10
	addiu $t1,$t2,100
	addu $t2,$t1,$t3
	sub $t3,$t1,$t2
	subu $t3,$t1,$t2
	li $t2,1
	and $t3,$t2,$zero
	andi $t3,$t2,3
	nor $t3,$zero,$zero
	or $t3,$t2,$zero
	ori $t3,$zero,2
	xor $t3,$t2,$zero
	xori $t3,$t2,3
	li $t2,7
	sll $t3,$t2,2
	srl $t3,$t2,1
	li $t1,3
	sllv $t3,$t2,$t1
	srlv $t3,$t2,$t1
	li $t2,-10
	srav $t3,$t2,$t1
	li $t2,3
	li $t3,2
	slt $t1,$t3,$t2
	slti $t1,$t2,1
	sltiu $t1,$t2,-1
	li $t3,-2
	sltu $t1,$t2,$t3
	li $t2,2
	li $t3,2
	beq $t2,$t3,branch1
	li $t4,1337
branch1:bne $t2,$t3,branch2
	li $t4,1939
branch2:li $t3,1	
	blt $t3,$t2,branch3
	li $t4,0
branch3:bgt $t3,$t2,branch4
	li $t4,-1410
branch4:ble $t3,$t2,branch5
	li $t4,1494
branch5:bge $t3,$t2,branch6
	li $t4,996
branch6:j lab1
	li $t4,1234
lab1:	jal lab2
	addi $t5,$t5,12
	addi $t4,$t4,1
	jalr $t5
	addi $t4,$t4,1
	la $t4,yolo
	lb $t3,0($t4)
	lbu $t3,0($t4)
	lh $t3,0($t4)
	lhu $t3,0($t4)
	lui $t3,100
	lw $t3,0($t4)
	sub $t3,$t3,2
	sw $t3,0($t4)
	lw $t5,0($t4)
	sh $t3,0($t4)
	sb $t3,0($t4)
	li $t2,15
	li $t3,-3 
	div $t4,$t2,$t3
	divu $t4,$t2,$t3
	mult $t4,$t2,$t3
	multu $t4,$t2,$t3
	
	
	

	
	j out

out:	li   $v0, 10          # system call for exit
	syscall
lab2:	addi $t4,$t4,1
	move $t5,$ra
	jr $ra