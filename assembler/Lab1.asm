	.data
prompt:	.asciiz "Podaj x : "
result:	.asciiz "Wynik to : "
double:	.double 1
convert:.word 1
	.text
	la $a0,prompt # load prompt adress
	li $v0,4 # set syscall to print string
	syscall 
	li $v0,5 #set syscall to read double
	syscall
	move $s0,$v0
	li $t0,3
	mul $t0,$s0,$t0 #x*3
	move $s4,$t0
	li $t0,33
	mul $t0,$s0,$t0 #x*33
	add $s4,$s4,$t0 # s4 = x*3 + x*33
	li $t0,333 
	mul $t0,$s0,$t0 # x* 333
	sub $s2,$t0,$s4 # s2 = 333x -36x
	mul $s4,$s2,$s2 # s4 = x^2
	mul $s4,$s4,$s2 # s4 = x * x^2
	move $a0,$s4
	li $v0, 1
	syscall
	mfhi $t2
	bnez $t2,exception
	sw $s4,convert
	lwc1 $f2,convert
	cvt.d.s $f2,$f2
	move $a0,$s0
	jal fraction
	sub.s $f0,$f2,$f0
	la $a0,result
	li $v0,4
	syscall
	movf.d $f12,$f0
	li $v0,2
	syscall
	j out
	
	
	#===================================#
#arguments are $a0 result in $v1
fraction:subi $t0,$a0,1 # t0 = x-1
	sw $a0,convert
	lwc1 $f0,convert
	lwc1 $f1,double
	div.s $f0,$f1,$f0 # 1/(x-1)
	add.s $f0,$f0,$f1 # t0 = 1 + (1/(1-x))	
	div.s $f0,$f1,$f0 # t0 = 1 / t0
	add.s $f0,$f1,$f0 # t0 = 1 + t0
	div.s $f0,$f1,$f0
	jr $ra
	
	.data 
error:	.asciiz "nastapilo przepelnienie przy mnozeniu" 
		.text
exception:	li $v0,4
		la $a0,error
		syscall
		j out
		
out:	li $v0,10
	syscall
		