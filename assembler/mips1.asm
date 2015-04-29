	.data
text:	.asciiz "Podaj liczbe : "
prompt: .asciiz "Wynik to : "
exc:	.asciiz "x=1 nie nalezy do dziedziny f-cji"
tns:	.double 297
one:	.double 1
	
	.text
	la $a0, text
	li $v0,4 		# print string
	syscall
	li $v0,7 		# load double f0 = x
	syscall
	ldc1 $f4,one
	c.eq.d $f0,$f4		# if x==1
	bc1t exception
	ldc1 $f4,tns		# f4 = 297
	mul.d $f2,$f0,$f4 	# f2 = x*297
	mul.d $f10,$f2,$f2 	# f10 = (297x)^2
	mul.d $f10,$f10,$f2 	# f10 = (297x)^2 * 297x
	mov.d $f2,$f10 		# f2 = f10
	ldc1 $f4, one 		# f4 = 1
	add.d $f6,$f2,$f2	# f6 = 2x
	sub.d $f6,$f2,$f4 	# f6 = 2x-1
	div.d $f4,$f2,$f6	# f4 = x / (2x-1)
	add.d $f12,$f2,$f4	# f12 = (297x)^3 + (x/(2x-1))
	la $a0,	prompt
	li $v0, 4		# print string
	syscall
	li $v0, 3		# print double [f12] 
	syscall
out:	li $v0, 10		# exit
	syscall
	
exception:
	la $a0, exc
	li $v0 4
	syscall 
	j out