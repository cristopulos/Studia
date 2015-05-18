	.data
fields:	.space 48
space:	.asciiz " "
nextLine:.asciiz "\n"
exc:	.asciiz "dla wygenerowanego wejsciowego zestawu nie ma rozwiazania, rozpoczynam od nowa\n"
prompt:	.asciiz "\nPodaj ile wygenerowac rozwiazan lamiglowki : "
# 42 - rand int raqnge
	.text
.macro 	printSpace (%times)
	la $a0, space
	li $v0, 4
	li $t0,0
Loop:
	beq $t0,%times,Cnt
	syscall
	addi $t0,$t0,1
	j Loop
Cnt:
.end_macro

.macro newLine
la $a0, nextLine
li $v0,4
syscall
.end_macro

.macro printField (%x)
la $t0,fields
li $t2,%x
sub $t1,$t2,1
mul $t1,$t1,4
add $t0,$t0,$t1
lw $a0, 0($t0)
li $v0, 1
syscall
.end_macro
	
main:
	la $a0, prompt
	li $v0, 4
	syscall
	li $v0, 5
	syscall
	move $s0,$v0
	li $s1,0
Loop:
	beq $s0,$s1,exit
	jal Solve
	addi $s1,$s1,1
	j Loop
	
exit:
	li $v0,10
	syscall	
	
	
#----------	
Solve:
	addi $sp,$sp -68
	sw $s0,0($sp)
	sw $s1,4($sp)
	sw $s2,8($sp)
	sw $s3,12($sp)
	sw $s4,16($sp)
	sw $s5,20($sp)
	sw $s6,24($sp)
	sw $s7,28($sp)
	sw $t0,32($sp)
	sw $t1,36($sp)
	sw $t2,40($sp)
	sw $t3,44($sp)
	sw $t4,48($sp)
	sw $t5,52($sp)
	sw $t6,56($sp)
	sw $t7,60($sp)
	sw $ra,64($sp)
solveRestart:
	la $s0,fields
	move $t1,$s0
	li $t2,0
	generateStartingFieldsLoop:
		bge $t2,6,generateStartingFieldsLoopExit
		jal nextRand			# wygenerowanie liczby z przedzialu 0-8
		add $t0,$v0,1			# dodanie do niej jedynki
		addi $t2,$t2,1			# zwiekszenie licznika petli
		sw $t0,0($t1)
		addi $t1,$t1,4			# przesuniecie wskaznika na pamiec
		j generateStartingFieldsLoop
generateStartingFieldsLoopExit:	
	lw $t2,0($s0)
	lw $t3,4($s0)
	add $s1,$t2,$t3			# s1 - krawedz nr 13
	lw $t2,8($s0)
	add $s2,$t2,$t3			# s2 - krawedz nr 14
	lw $t3,12($s0)
	add $s3,$t2,$t3			# s3 - krawedz nr 15
	lw $t2,16($s0)
	add $s4,$t2,$t3			# s4 - krawedz nr 16
	lw $t3,20($s0)
	add $s5,$t2,$t3			# s5 - krawedz nr 17
	lw $t2,0($s0)
	add $s6,$t2,$t3			# s6 - krawedz nr 18
#----------uzupelnianie pozostalych pol 1-szego trojkata-----------#
	sub $t2,$s4,$s6			# $t2 - roznica pomiedzy krawedzia 16 i 18  
	bgez $t2,solveCnt
		mul $t3,$t2,2
		sub $t2,$t2,$t3
solveCnt:	
	li $t6,20
	sub $t7,$t6,$s2	
	sub $t3,$t7,$t2
	div $t5,$t3,2
	mfhi $t4
	mflo $t5
	bnez $t4, solveExc
		bge $s4, $s6, sixteenthGreater
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,24($s0)		# pole nr 7 przyjmuje mniejsza wartosc 
			add $s6,$s6,$t5
			add $s2,$s2,$t5		# update wartosci krawedzi 14 i 18
			add $t5,$t5,$t2
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,32($s0)		# pole nr 9 przyjmuje wieksza wartosc
			add $s4,$s4,$t5
			add $s2,$s2,$t5		# update wartosci krawedzi 14 i 16
			j solveCnt2
		sixteenthGreater:
			bge $t5,10,solveExc
			blez $t5, solveExc		
			sw $t5,32($s0)		# pole nr 9 przyjmuje mniejsza wartosc 
			add $s4,$s4,$t5
			add $s2,$s2,$t5		# update wartosci krawedzi 14 i 16
			add $t5,$t5,$t2
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,24($s0)		# pole nr 7 przyjmuje wieksza wartosc
			add $s6,$s6,$t5
			add $s2,$s2,$t5		# update wartosci krawedzi 14 i 18
	solveCnt2:
	sub $t5,$t6,$s6
	bge $t5,10,solveExc			# jezeli po ustaleniu optymalnych wartosci pol 7 i 9 pola 11 nie da sie wypelnic poprawna wartoscia lamiglowki nie da sie rozwiazac
	blez $t5, solveExc
	sw $t5,40($s0)
#--------uzupelnianie pozostalych pol 2-giego trojkata----------#
	sub $t2,$s3,$s5			# $t2 - roznica pomiedzy krawedzia 15 i 17  
	bgez $t2,solveCnt4
		mul $t3,$t2,2
		sub $t2,$t2,$t3
solveCnt4:
	sub $t7,$t6,$s1	
	sub $t3,$t7,$t2
	div $t5,$t3,2
	mfhi $t4
	mflo $t5
	bnez $t4, solveExc
		bge $s3, $s5, fifteenthGreater
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,44($s0)		# pole nr 12 przyjmuje mniejsza wartosc 
			add $s5,$s5,$t5
			add $s1,$s1,$t5		# update wartosci krawedzi 13 i 17
			add $t5,$t5,$t2
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,28($s0)		# pole nr 8 przyjmuje wieksza wartosc
			add $s3,$s3,$t5
			add $s1,$s1,$t5		# update wartosci krawedzi 13 i 15
			j solveCnt3
		fifteenthGreater:
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,28($s0)		# pole nr 8 przyjmuje mniejsza wartosc 
			add $s3,$s3,$t5
			add $s1,$s1,$t5		# update wartosci krawedzi 13 i 15
			add $t5,$t5,$t2
			bge $t5,10,solveExc
			blez $t5, solveExc
			sw $t5,44($s0)		# pole nr 7 przyjmuje wieksza wartosc
			add $s5,$s5,$t5
			add $s1,$s1,$t5		# update wartosci krawedzi 13 i 17
	solveCnt3:
	sub $t5,$t6,$s5
	bge $t5,10,solveExc			# jezeli po ustaleniu optymalnych wartosci pol 7 i 9 pola 8 nie da sie wypelnic poprawna wartoscia lamiglowki nie da sie rozwiazac
	blez $t5, solveExc
	sw $t5,36($s0)
	
printSolution:
	printSpace (3)
	printField (7)
	
	newLine
	printField (12)
	printSpace (1)
	printField (1)
	printSpace (1)
	printField (2)
	printSpace(1)
	printField (8)
	
	newLine
	printSpace (1)
	printField (6)
	printSpace (3)
	printField (3)
	
	newLine
	printField (11)
	printSpace (1)
	printField (5)
	printSpace (1)
	printField (4)
	printSpace (1)
	printField (9)
	
	newLine
	printSpace(3)
	printField(10)
	newLine
	newLine

solveExit:
	lw $s0,0($sp)
	lw $s1,4($sp)
	lw $s2,8($sp)
	lw $s3,12($sp)
	lw $s4,16($sp)
	lw $s5,20($sp)
	lw $s6,24($sp)
	lw $s7,28($sp)
	lw $t0,32($sp)
	lw $t1,36($sp)
	lw $t2,40($sp)
	lw $t3,44($sp)
	lw $t4,48($sp)
	lw $t5,52($sp)
	lw $t6,56($sp)
	lw $t7,60($sp)
	lw $ra,64($sp)
	addi $sp,$sp 68
	jr $ra

nextRand:
	li $a0,1337
	li $a1,9
	li $v0,42
	syscall
	move $v0,$a0
	jr $ra
	
solveExc :
	#la $a0,exc
	#li $v0,4
	#syscall
	j solveRestart