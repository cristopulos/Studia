	.data
cyfra:	.asciiz "\npodaj ilosc cyfr dzielnej : "
dziel1: .asciiz "\npodaj wartosc dzielnej : "
dziel2:	.asciiz "\npodaj wartosc dzielnika : "
exc:	.asciiz "dzielna moze skladac sie tylko z cyfr od 0 do 9"
exc2:	.asciiz "dzielnik nie moze byc zerem"
exc3:	.asciiz "ilosc cyfr dzielnej musi byc wieksza od zera"
result: .asciiz "wynik to : "
result2:.asciiz "\nreszta wynosi : "

	.text
#-----------------------MAIN------------------------------#
start:	la $a0,cyfra
	li $v0, 4
	syscall 	# wypisanie komunikatu
	li $v0, 5
	syscall 	# wczytanie int-a
	move $s3, $v0	# $s3 - ilosc cyfr do wpisania
	
	bgtz $s3, else	# jezeli uzytkownik wpisal w pole ilosci cyfr liczbe mniejsza od zera 
	la $a0 ,exc3	# wyswietl stosowna informacje
	li $v0,4
	syscall
	j start		# i zakoncz program
else:	
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
	
start2:	la $a0,dziel2
	li $v0, 4
	syscall		# wypisanie prosby o wpisaniu dzielnika
	li $v0, 5
	syscall		# wczytanie dzielnika
	move $s1,$v0 	# dzielnik w rejestrze $s1
	bnez $s1, notZero
	la $a0,exc2
	li $v0, 4
	syscall 	# wypisanie komunikatu o blednie wprowadzonych danych
	j start2
notZero:	
	move $a0,$s0
	move $a1,$s1
	move $a2,$s2
	move $a3,$s3
	jal division 	# $a0 - dzielna, $a1 - dzielnik, $a2 adres na pamiec na wynik, $a3 ilosc cyfr dzielnika
	move $s0,$v0	# $s0 - adres na pamiec wyniku
	move $s1,$v1	# $s1 - reszta z dzielenia
	la $a0,result
	li $v0, 4
	syscall		# wypisanie komunikatu
	bnez $s0, string
	li $v0,1
	li $a0,0
	syscall
	j goOn
string:	move $a0,$s0
	li $v0,4
	syscall		# wypisanie wyniku
	
goOn:	la $a0,result2	
	li $v0,4
	syscall		# wypisanie drugiego komunikatu
	move $a0,$s1
	li $v0,1
	syscall
		
out:	li $v0, 10
	syscall
	
#-----------------------------------F-cja dopelniajaca liczbe do wielkrotnosci 4----------------------------------------#
	
dopDo4:
	addi, $sp,$sp, -8	# odpowiednie ustawienie wskaznika konca stosu
	sw $t0,0($sp)		# |
	sw $t1,4($sp)		# upewniamy sie ze nie starcimy zadnych danych
	li $t0,4
	div $v0,$t0		
	mfhi $t1		# reszta z dzielnia przez 4
	beqz $t1,return		# jezeli liczba juz jest wielokrotnoscia 4 nie dodawaj nic
	sub $t0,$t0,$t1
	add $v0,$a0,$t0		# dopelnienie do nast. wielokrotnosci 4
return:	lw $t0,0($sp)		# |
	lw $t1,4($sp)		# wczytanie wczesniej zapisanych danych
	addi $sp,$sp,8
	jr $ra

#-------------------------------------F-cja odpowiedzialna za dzielenie liczb----------------------------------------------------#
division:
#----Poczatkowe operacje----#
	addi $sp,$sp,-44	# odpowiednie przesuniecie znacznika konca stosu
	sw $s0,0($sp)		# |
	sw $s1,4($sp)		# |
	sw $s2,8($sp)		# |
	sw $s3,12($sp)		# |
	sw $s4, 16($sp)		# |
	sw $s5, 20($sp)		# |
	sw $s6, 24($sp)		# upewniamy sie ze nie stracimy zadnych danych
	sw $s7, 28($sp)		# |
	sw $t0,	32($sp)		# |
	sw $t3, 36($sp)		# |
	sw $t2, 40($sp)		# |
	sw $t5, 44($sp)		# |
	
	move $s0,$a0		# $t0 : adres na poczatek pamieci dzielnej
	move $s1,$a3		# $t1 : ilosc cyfr dzielnej
	move $s6,$a2		# $s6 : adres w pamieci gdzie zaczyna sie wynik
	
	li $s3,0 		# flaga wskazuj¹ca, czy dzielna jest ujemna
	li $s7,0		# flaga wskazujaca, czy dzielnik jest ujemny
	li $s5,0		# flaga wskaujaca ze rozpoczelo sie dzielenie
	
	lbu $t2,0($s0) 		# wczytanie pierwszego znaku
	subi $s1,$s1,1		# zmniejszenie ilosci cyfr do wczytania o jeden
	subi $t2,$t2,48 	# zmiana kodu ascii na liczbe
	li $t3,-3		# kod ascii znaku minus 45 : 45-48 = -3
	
	bne $t2,$t3,brn5 	# je¿eli pierwszy znak to nie minus [ascii : 45] -> dzielna jest dodatnia
	li $s3,1	 	# zmiana flagi dzielnej na "true"
	
brn5:	bgtz $a1,brn6		# jezeli dzielnik jest wiekszy od zera
	li $s7,1		# zamiana flagi dzielnika na "true"
	
brn6:	beq $s3,$s7, brn7	# jezeli znaki dzielnika i dzielnej sa takie same nie ma potrzeby dopisywania minusa	
	li $t2,45		# kod ascii 45 -> znak minus
	sb $t2,0($a2)		# wypisanie minusa na poczatek
	addi $a2,$a2,1		# przesuniecie znacznika na adres w pamieci o jeden bajt w prawo
	
brn7:	beqz $s7,brn2		# jezeli dzielnik jest liczba ujemna -> flaga jest ustawiona na true w $s7
	sub $a1,$zero,$a1	# zamiana dzielnika na liczbe dodatnia
brn2:	
	beqz $s3, brn3		# jezeli dzielna byla ujemna -> miala na pocztaku minus
	addi $s1,$s1,1		# dodanie jedynki do licznika pozostalych cyfr gdyz zczytany znak nie byl cyfra a minusem
brn3:
	bnez $s3,next		# jezeli wczytany znak byl minusem przeskocz dalej
 	div $t0,$t2,10		# sprawdzamy, czy to co wczytalismy jest cyfra
 	bnez $t0,exception	# jezeli wczytany znak nie jest cyfra wyrzuc komunikat
	move $s4,$t2 		# je¿eli pierszy znak byl cyfra wczytaj ja do rejestru przechowujacego wartosc dzielnej
	
#----Wlasciwa petla dzialaca----#
next:	addi $s0,$s0,1 		# zmiana adresu na nastepny znak do pobrania
	bnez $s5,brn		# jezeli rozpoczelo sie juz dzielnie nie ma potrzeby dalej sprawdzac czy liczba reprez. dzielna jest wieksza od dzielnika
	blt  $s4,$a1,cnt 	# jezeli wartosc dzielnej jest >= od dzielnika przystap do dzielenia
	li $s5,1		# ustawienie flagi na 'dzielenie rozpoczelo sie'
brn:	div $s4,$a1		# dzielenie
	mflo $t5		# przeniesienie wyniku do rejestru w celu zapisania go
	addi $t5,$t5,48 	# zamiana cyfry z powrotem na kod ascii
	sb $t5, 0($a2) 		# zapisanie znaku w pamieci przeznaaczonej na wynik
	addi $a2,$a2,1 		# przesuniecie wskaznika o 1 bajt w prawo
	mfhi $s4 		# przeniesienie reszty z powrotem do rejestru dzielnej
cnt:	beqz $s1, end		# jezeli nie ma juz cyfr do wczytania, zakoncz
	mul $s4,$s4 10		# zwiekszenie wartosci dzielnej 10-krotnie czyli przesuniecie cyfr o jedna w lewo
	lbu $t2, 0($s0)		# wczytanie kolejnego znaku dzielnej
	subi $s1,$s1,1		# zminiejszenie ilosci pozostalych do pobrania cyfr o jeden
	subi $t2,$t2,48		# zamiana znaku na liczbe
	div $t0,$t2,10		# sprawdzenie, czy wczytany znak jest cyfra
	bnez $t0,exception	# jezeli wczytany znak nie jest cyfra wyrzuc kominukat
	add $s4,$t2,$s4		# dodanie tej liczby do zmiennej dla dzielnej
	j next
#----zakonczenie f-cji dzielacej----#
end : 	move $v0,$s6		# $v0 : adres pamieci zawierajacej dzielna
	move $v1,$s4		# $v1 : reszta z dzielenia
	bnez $s5,brn8		# jezeli dzielenie wcale nie nastapilo 
	li $v0,0		# ustawiamy rejestr wynikowy na zero zamiast na adres w pamieci
brn8:	lw $s0,0($sp)		# |
	lw $s1,4($sp)		# |
	lw $s2,8($sp)		# |
	lw $s3,12($sp)		# |
	lw $s4,16($sp)		# |
	lw $s5, 20($sp)		# wczytanie wartosci w rejestrach sprzed wywolania metody
	lw $s6, 24 ($sp)	# |
	lw $s7, 28($sp)		# |
	lw $t0,	32($sp)		# |
	lw $t3, 36($sp)		# |
	lw $t2, 40($sp)		# |
	lw $t5, 44($sp)		# |
	addi $sp,$sp,44		# odpowiednie przesuniecie wskaznika konca stosu
	jr $ra 			# zakonczenie metody/f-cji
			
#-----Wyjatek do f-cji dzielacej-----#			
exception:
	la $a0,exc		
	li $v0, 4
	syscall			# wypisanie odpowiedniego komunikatu o bledzie
	j out			# zkonczenie programu
