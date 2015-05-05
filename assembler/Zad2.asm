	.data
cyfra:	.asciiz "\npodaj ilosc cyfr dzielnej :"
dziel1: 	.asciiz "\npodaj wartosc dzielnej :"
dziel2:	.asciiz "\npodaj wartosc dzielnika :"

	.text
	la $a0,cyfra
	li $v0, 4
	syscall 	# wypisanie komunikatu
	li $v0, 5
	syscall 	# wczytanie int-a
	addi $v0,$v0,1
	move $s1, $v0	# $s1 - ilosc cyfr do wpisania
	move $a0,$v0	# wczytanie argmentow do metody
	jal dopDo4	# dopelnienie wartosci do wielokrotnosci 4
	move $a0,$v0	# pobranie wyniku
	li $v0, 9 	
	syscall		# alokacja pamieci na  $v0 znakow
	move $s0,$v0 	# adres do zaalokowanej pamieci na dzielna w $s0
	syscall		# alokacja pamieci na wynik
	move $s2,$v0	# przesuniecie adresu do rejestru $s3
	la $a0, dziel1
	li $v0, 4
	syscall		# wypisanie komunikatu
	move $a0,$s0	# w $a0 adres do obszaru zarezerwowanego na dzielna
	move $a1,$s1
	li $v0, 8
	syscall		# pobranie dzielnej jako ciag znakow
	
	la $a0,dziel2
	li $v0, 4
	syscall
	li $v0, 5
	syscall		# wczytanie dzielnika
	move $s1,$v0 	# dzielnik w rejestrze $s1
	
	move $a0,$s0
	move $a1,$s1
	move $a2,$s2
	jal division 	# $a0 - dzielna, $a1 - dzielnik, $a2 adres na pamiec na wynik
	
	
	
	
	
	li $v0, 10
	syscall
	

	
dopDo4:
	li $t0,4
	div $v0,$t0
	mfhi $t1
	beqz $t1,return
	sub $t0,$t0,$t1
	add $v0,$a0,$t0
return:	jr $ra

division:
	move $t0,$a0
	move $t1,$a1
	li $v0,0
	li $v1,0		# zerowanie wynikow
	li $t3,0 		# flaga wskazuj¹ca, czy dzielna jest ujemna
	lbu $t2,0($t0) 		# wczytanie pierwszego znaku
	beqz $t2,return2
	subi $t2,$t2,48 	# zmiana kodu ascii na liczbe
	li $t3,-3
	bne $t2,$t3,positive 	# je¿eli pierwszy znak to minus [ascii : 45]
	addi $t3,$t3,1	 	# zmiana flagi na "true"
positive:
	bnez $t3,next
	move $t4,$t3 		# je¿eli pierszy znak byl liczba wczytaj do rejestru przechowujacego wartosc dzielnej
next:	addi $t0,$t0,1 		# zmiana adresu na nastepny znak do pobrania
	
	bge $t4,$a1,div 	# jezeli wartosc dzielnej jest >= od dzielnika przystap do dzielenia
cnt:	



div:	
	div $t4,$a1
	mfhi $t5
	addi $t5,$t5,48 # zamiana liczby z powrotem na kod ascii
	sb $t5, 0($a3) 	# zapisanie znaku w pamieci przeznaaczonej na wynik
	addi $a3,$a3,1 	# przesiniecie wskaznika o 1 bajt w prawo
	mflo $t4 	# przeniesienie reszty z powrotem do rejestru dzielnej
	j cnt



	
return2:jr $ra 
