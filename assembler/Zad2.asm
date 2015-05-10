	.data
cyfra:	.asciiz "\npodaj ilosc cyfr dzielnej :"
dziel1: .asciiz "\npodaj wartosc dzielnej :"
dziel2:	.asciiz "\npodaj wartosc dzielnika :"
exc:	.asciiz "dzielna moze skladac sie tylko z cyfr od 0 do 9"
result: .asciiz "wynik to :"
result2:.asciiz "reszta wynosi :"

	.text
	la $a0,cyfra
	li $v0, 4
	syscall 	# wypisanie komunikatu
	li $v0, 5
	syscall 	# wczytanie int-a
	move $s3, $v0	# $s3 - ilosc cyfr do wpisania
	addi $v0,$v0,2
	move $t1,$v0
	move $a0,$v0	# wczytanie argmentow do metody
	jal dopDo4	# dopelnienie wartosci do wielokrotnosci 4
	move $a0,$v0	# pobranie wyniku
	li $v0, 9 	
	syscall		# alokacja pamieci na  $v0 znakow
	move $s0,$v0 	# adres do zaalokowanej pamieci na dzielna w $s0
	li $v0, 9
	syscall		# alokacja pamieci na wynik
	move $s2,$v0	# przesuniecie adresu do rejestru $s2
	la $a0, dziel1
	li $v0, 4
	syscall		# wypisanie komunikatu
	move $a0,$s0	# w $a0 adres do obszaru zarezerwowanego na dzielna
	move $a1,$t1
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
	move $a3,$s3
	jal division 	# $a0 - dzielna, $a1 - dzielnik, $a2 adres na pamiec na wynik, $a3 ilosc cyfr dzielnika
	move $v0,$s0	# $s0 - adres na pamiec wyniku
	move $v1,$s1	# $s1 - reszta z dzielenia
	la $a0,result
	li $v0, 4
	syscall		# wypisanie komunikatu
	move $a0,$s0
	syscall		# wypisanie wyniku
	la $a0,result2	
	syscall		# wypisanie drugiego komunikatu
	move $a0,$s1
	li $v0,1
	syscall
	
	
	
	
out:	li $v0, 10
	syscall
	

	
dopDo4:
	addi, $sp,$sp, -8
	sw $t0,0($sp)
	sw $t1,4($sp)
	li $t0,4
	div $v0,$t0
	mfhi $t1
	beqz $t1,return
	sub $t0,$t0,$t1
	add $v0,$a0,$t0
return:	lw $t0,0($sp)
	lw $t1,4($sp)
	addi $sp,$sp,8
	jr $ra

division:
	addi $sp,$sp,-20
	sw $s0,0($sp)
	sw $s1,4($sp)
	sw $s2,8($sp)
	sw $s3,12($sp)		# upewniamy sie ze nie stracimy zadnych danych
	sw $s4, 16($sp)
	
	move $s0,$a0		# $t0 : adres na poczatek pamieci dzielnej
	move $s1,$a3		# $t1 : ilosc cyfr dzielnej
	
	li $v0,0
	li $v1,0		# zerowanie wynikow
	li $s3,0 		# flaga wskazuj¹ca, czy dzielna jest ujemna
	
	lbu $t2,0($s0) 		# wczytanie pierwszego znaku
	subi $s1,$s1,1
	beqz $t2,return2
	subi $t2,$t2,48 	# zmiana kodu ascii na liczbe
	li $t3,-3
	bne $t2,$t3,positive 	# je¿eli pierwszy znak to minus [ascii : 45]
	addi $s3,$s3,1	 	# zmiana flagi na "true"
	addi $s1,$s1,1		
positive:
	bnez $s3,next
 	div $t0,$t2,10
 	bnez $t0,exception	# jezeli wczytany znak nie jest liczba wyrzuc komunikat
	move $s4,$t2 		# je¿eli pierszy znak byl liczba wczytaj do rejestru przechowujacego wartosc dzielnej
next:	addi $s0,$s0,1 		# zmiana adresu na nastepny znak do pobrania
	blt  $s4,$a1,cnt 	# jezeli wartosc dzielnej jest >= od dzielnika przystap do dzielenia
	div $s4,$a1
	mflo $t5
	addi $t5,$t5,48 	# zamiana liczby z powrotem na kod ascii
	sb $t5, 0($a2) 		# zapisanie znaku w pamieci przeznaaczonej na wynik
	addi $a2,$a2,1 		# przesuniecie wskaznika o 1 bajt w prawo
	mfhi $s4 		# przeniesienie reszty z powrotem do rejestru dzielnej
cnt:	beqz $s1, end		# jezeli nie ma juz cyfr do wczytania, zakoncz
	mul $s4,$s4 10		# zwiekszenie wartosci dzielnej 10-krotnie czyli przesuniecie cyfr o jedna w lewo
	lbu $t2, 0($s0)		# wczytanie kolejnego znaku dzielnej
	subi $s1,$s1,1		# zminiejszenie ilosci pozostalych do pobrania cyfr o jeden
	subi $t2,$t2,48		# zamiana znaku na liczbe
	div $t0,$t2,10
	bnez $t0,exception	# jezeli wczytany znak nie jest liczba wyrzuc kominukat
	add $s4,$t2,$s4		# dodanie tej liczby do zmiennej dla dzielnej
	j next




end : 	lw $s0,0($sp)
	lw $s1,4($sp)
	lw $s2,8($sp)
	lw $s3,12($sp)
	lw $s4,16($sp)
	addi $sp,$sp,20		# wczytanie wartosci w rejestrach sprzed wywolania metody
	move $v0,$a3		# $v0 : adres pamieci zawierajacej dzielna
	move $v1,$s4		# $v1 : reszta z dzielenia
return2:jr $ra 
	
exception:
	la $t0,exc
	li $v0, 4
	syscall
	lw $t0 , 0($sp)
	addi $sp,$sp,4
	j out
