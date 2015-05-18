
	.data
prompt:	.asciiz "Podaj ilosc cyfr ktore ma posiadac liczba o podanych wlasciwosciach"
exc3: 	.asciiz "Cos poszlo nie tak"
newLine:.asciiz "\n"
	
	
	.text
main:
	la $a0,prompt
	li $v0,4
	syscall
	li $v0,5
	syscall
	move $a0,$v0
	jal searchForNumbers
	li $v0,10
	syscall
	
#----------------F-cja modulo----------------#
# $a0 - wskaznik na miejsce w pamieci na dzielna
# $a1 - ilosc cyfr dzielnej
# $a2 - rejestr z intem ktory ma byc uzyty do modulo
# $v0 - wynik			
mod:
	
	move $t0,$a0		# $s0 - wskaznik na pamiec z dzielna
	move $t1,$a1		# $s1 - ilosc cyfr dzielnej
	move $t2,$a2		# $s2 - liczba do modulo
	
	div $t4,$t2,2
	mfhi $t4
	bnez $t4,modCnt
	add $t3,$t0,$t1
	subi $t3,$t3,1
	lb $t5,0($t3)
	subi $t5,$t5,48
	div $t5,$t5,2
	mfhi $t5
	bnez $t5,modReturnFalse

modCnt:	
	li $t4,0 		# wyzerowanie rejestru przechowujacego reszte
	
	modLoop:
		blez $t1,modEnd		# jezeli nie ma juz cyfr do pobrania zakoncz f-cje
		lb $t3,0($t0)		# pobranie znaku dzielnej
		subi $t3,$t3,48		# zamiana znaku na cyfre
		addi $t0,$t0,1		# przesuniecie wskaznika w prawo
		mul $t4,$t4,10		# pomnozenie aktualnej reszty przez 10 (przesuniecie cyfr w prawo)
		add $t4,$t4,$t3		# dodanie pobranej cyfry do reszty]
		div $t4,$t2		# dzielenie
		mfhi $t4		# przeniesienie reszty do rejestru przenaczonego na to
		subi $t1,$t1,1		# zmniejszenie licznika cyfr o 1
		j modLoop
	
modEnd:
	move $v0,$t4		# przeniesienie wyniku do odpowiedniego rejestru
modExit:
	jr $ra
	
modReturnFalse:
	li $v0,-1
	j modExit	
#--------f-cja odpowiedzialna za znalezienie liczb-------#
# $a0 - ilosc cyfr w liczbie
searchForNumbers:
	addi $sp,$sp,-4
	sw $ra,0($sp)

	
	move $t0,$a0		# $t0 - ilosc cyfr jaka musi posiadac liczba
	addi $a0,$a0,1		# dodanie jedynki do ilosci bajtow do zarezerwowania ze wzgledow bezpieczenstwa
	jal dopDo4
	move $t1,$v0		# $t1 - ilosc bajtow do zarezerwowania na liczbe
	move $a0,$t1
	li $v0,9
	syscall
	move $t2,$v0		# $t2 - wskaznik na pamiec z liczba
	move $a0,$t2
	move $a1,$t0
	jal fillWithOnes	# wypelnienie pamieci z liczba samymi jedynkami
	move $t2,$v0
	
	move $t3,$t0		# $t3 - rejestr na sume cyfry liczby (poczatkowo ma wartosc iloscy cyfr liczby gdyz poczatkowa liczba to same jedynki)
searchLoop:
		move $a0,$t2
		move $a1,$t0
		move $a2,$t3
		jal mod
		bnez $v0, searchCnt	# jezeli reszta z dzielenia == 0 znalezlismy liczbe
			la $a0,newLine
			li $v0,4
			syscall			# przejscie do nowej linii
			move $a0,$t2
			syscall			# wypisanie liczby spelniajacej warunek
	searchCnt:
		li $a0,0
		move $a1,$t2
		move $a2,$t0
		jal addition		# dodanie jedynki do liczby w pamieci
		addi $t3,$t3,1		# po dodaniu jedynki suma cyfr zwiekszyla sie op jeden
		bne $t0,$v1 searchOut	# jezeli po dodaniu zmienila sie ilosc cyfr w liczbie koniec szukania
			beqz $t8, searchForZerosLoopExit	# jezeli f-cja zwrocila w rejestrze liczacym przeniesiania liczbe wieksza od zera nie wykonujemy dodatkowych akcji
			mul $t4,$t8,9
			sub $t3,$t3,$t4		# odejmujemy od sumy cyfr odpowiednia liczbe
			li $t6,0		# $s6 - licznik cyfr ktore juz zostaly sprawdzone
			move $t4,$t2		# ustawienie $s4 na roboczy wskaznik ktorym bedziemy poruszac
		searchForZerosLoop:
			beqz $t8,searchForZerosLoopExit		# jezeli nie ma juz zer ktore powstaly z przesuniec w dodawaniu konczymy przegladanie cyfr liczby
			sub $t7,$t4,$t2
			bge $t7,$t0, searchLoopException	# jezeli wyszlismy juz poza pamiec przeznaczona na liczbe i nie znalezlismy wszystkich zer cos poszlo nie tak
				lb $t5,0($t4)				# pobieramy znak liczby
				bne $t5,48,searchForZerosLoopCnt	# jezeli kod pobranego znku nie rowna sie 48 ('0') to szukamy dalej
				addi $t5,$t5,1				# zmiana zera na jedynke
				sb $t5,0($t4)				# zapisanie jedynki na miejscu zera
				addi $t3,$t3,1				# dodanie jedynki do sumy cyfr
				subi $t8,$t8,1				# zmniejszenie ilosci pozostalych zer o jeden
		searchForZerosLoopCnt:
			addi $t4,$t4,1				# przesuwamy wskaznik w prawo
			j searchForZerosLoop		
		searchForZerosLoopExit:
			j searchLoop
				
				
searchOut:

	lw $ra,0($sp)
	addi $sp,$sp,4
	jr $ra
	
searchLoopException:
	la $a0,exc3
	li $v0,4
	syscall
	li $v0,10
	syscall
	
#---------f-cja wypelniajaca pamiec znakami '1'-------------#
# $a0 - wskaznik na pamiec do wypelnienia
# $a1 - ilosc cyfr do wpisania
# $v0 - wskaznik na pamiec po wpisaniu (to samo co podane w $a0)
fillWithOnes:
	addi $sp,$sp,-20
	sw $s0,0($sp)
	sw $s1,4($sp)
	sw $s2,8($sp)
	sw $s3,12($sp)
	sw $t0,16($sp)
	
	move $s0,$a0		# $s0 - uzytkowy wskaznik na pamiec do wypelnienia
	move $s1,$a1		# $s1 - ilosc cyfr pozostalych do wpisania
	move $s2,$a0		# $s2 - wskaznik na poczatek pamieci do wypelnienia NIE RUSZAC!!
	li $t0,49		# $t0 - wartosc kodowa znaku '1'
	
	fillLoop:
		blez $s1,fillEnd
		sb $t0,0($s0)		# zapisanie znaku
		addi $s0,$s0,1		# przesuniecie wskaznika w lewo
		subi $s1,$s1,1		# zniejszenie wskaznika pozostalych do wpisania znakow
		j fillLoop
fillEnd:
	move $v0,$s2
	lw $s0,0($sp)
	lw $s1,4($sp)
	lw $s2,8($sp)
	lw $s3,12($sp)
	lw $t0,16($sp)
	addi $sp,$sp,20
	jr $ra
	
.include "Zad2.asm"