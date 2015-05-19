# Krzysztof Heldt 220883
# grupa wtorek 11:15
	.data
dziel1: .asciiz "\npodaj wartosc dzielnej : "
dziel2:	.asciiz "\npodaj wartosc dzielnika : "
exc:	.asciiz "\ndzielna i dzielnik moga skladac sie tylko z cyfr od 0 do 9"
exc2:	.asciiz "\ndzielnik nie moze byc zerem"
result: .asciiz "wynik to : "
result2:.asciiz "\nreszta wynosi : "
	.globl division
	.globl addition
	.globl dopDo4
	.text
#-----------------------MAIN------------------------------#
	
mainDividend:
	la $a0, dziel1
	li $v0, 4
	syscall			# wypisanie komunikatu
	jal readString		# wczytanie liczby podanej przez uzytkownika
	bne $v0, 1, mainCnt2
	la $ra mainDividend
	j mainExc1
mainCnt2:	
	bne $v0, 2, mainCnt
	li $s0,1		# ustawienie flagi wskazujacej, ze dzielna jest zerem
mainCnt:
	move $s1,$v0		# $s1 - wskaznik na pamiec przechowujaca dzielna
	move $s2,$v1		# $s2 - ilosc cyfr dzielnej
	move $s3,$t9		# $s3 - flaga wskazujaca na znak dzielnej
mainDivisor:	
	la $a0, dziel2
	li $v0,4
	syscall			# wypisanie prosby o podanie dzielnika
	jal readString		# wczytanie liczby podanej przez uzytkownika
	bne $v0, 1, mainCnt3
	la $ra mainDivisor
	j mainExc1
mainCnt3:
	bne $v0,2 mainCnt4
	la $ra mainDivisor
	j mainExc2
mainCnt4:		
	move $s4,$v0		# $s4 - wskaznik na pamiec przechowujaca dzielnik
	move $s5,$v1		# $s5 - ilosc cyfr dzielnika
	move $s6,$t9		# $s6 - flaga wskazujaca na znak dzielnika
	
	move $a0, $s1
	move $a1, $s2
	move $a2, $s4
	move $a3, $s5
	jal division
	move $t6, $v0		# $t6 - iloraz
	move $t7, $v1		# $t7 - reszta
	la $a0,result
	li $v0,4
	syscall			# wypisanie komunikatu
	beq $s3,$s6, mainCnt5	# jezeli znaki dzielnej i dzielnika sa takie same to przejdz dalej
	li $a0, 45
	li $v0,11
	syscall
mainCnt5:
	bnez $s3 mainCnt6	# jezeli dzilena jest dodatnia nie rob nic dodatkowego
	lb $t0,0($t7)
	beq $t0,48,mainCnt6	# jezeli reszta wynosi zero nie rob nic
	li $a0,0
	move $a1,$t6		# przesun wwrtosci do odpowiednich rejestrow
	move $a2,$t9
	jal addition		# je¿eli dzielna byla ujemna dodaj do wyniku 1
	move $a0,$s4
	move $a1,$t7		# przesun wartosci do odpowiednich rejestrow
	move $a2,$s5
	move $a3,$t8
	li $t9,0
	jal substract		# a reszta to : dzielnik - reszta z dzielenia liczb bez znaku
	move $t7, $v0
	 
mainCnt6:	
	move $a0,$t6
	li $v0,4
	syscall			# wypisanie wyniku
	la $a0,result2
	li $v0, 4
	syscall			# wypisanie komunikatu o reszcie
	move $a0, $t7
	li $v0, 4
	syscall 		# wypisanie reszty
	j exit			# zakonczenie programu
		
exit:	li $v0, 10
	syscall
	
mainExc1:
	la $a0, exc
	li $v0, 4
	syscall			# wypisanie komunikatu o bledzie
	jr $ra
	
mainExc2:
	la $a0,exc2
	li $v0,4
	syscall			# wypisanie komunikatu o bledzie
	jr $ra	
#-----------------------------------F-cja dopelniajaca liczbe do wielkrotnosci 4----------------------------------------#
	
dopDo4:
	addi, $sp,$sp, -8	# odpowiednie ustawienie wskaznika konca stosu
	sw $t0,0($sp)		# |
	sw $t1,4($sp)		# upewniamy sie ze nie starcimy zadnych danych
	li $t0,4
	div $a0,$t0		
	mfhi $t1		# reszta z dzielnia przez 4
	beqz $t1,return		# jezeli liczba juz jest wielokrotnoscia 4 nie dodawaj nic
	sub $t0,$t0,$t1
	add $v0,$a0,$t0		# dopelnienie do nast. wielokrotnosci 4
	j dopOut
return:	move $v0,$a0		# jezeli reszta wynosila zero nalezy tylko przesunac wartosc podana na wyjscie
dopOut:	lw $t0,0($sp)		# |
	lw $t1,4($sp)		# wczytanie wczesniej zapisanych danych
	addi $sp,$sp,8
	jr $ra

#------------f-cja dzielaca-----------#
# $a0 - wskaznik na pamiec z dzielna
# $a1 - ilosc cyfr dzielnej
# $a2 - wskaznik na pamiec z dzielnikiem
# $a3 - ilosc cyfr dzielnika
# $v0 - wskaznik na pamiec z ilorazem
# $v1 - wskaznik na pamiec z reszta
# $t9 - ilosc cyfr ilorazu
# $t8 - ilosc cyfr reszty
division:
	addi $sp, $sp,-52
	sw $ra,0($sp)
	sw $t0,4($sp)
	sw $t1,8($sp)
	sw $t2,12($sp)
	sw $t3,16($sp)
	sw $s0,20($sp)
	sw $s1,24($sp)		# zapisanie wartosci aby nie utracic danych
	sw $s2,28($sp)
	sw $s3,32($sp)
	sw $s4,36($sp)
	sw $s5,40($sp)
	sw $s6,44($sp)
	sw $s7,48($sp)
	
	move $s4,$a0		# $s4 - wskaznik na pamiec z dzielna
	move $s5,$a1		# $s5 - ilosc cyfr dzielnej
	move $s6,$a2		# $s6 - wskaznik na pamiec z dzielnikiem
	move $s7,$a3		# $s7 - ilosc cyfr dzielnika
	li $t9,0		# zerowanie licznika przesuniecia
	
	addi $sp,$sp,-4
	sw $a0,0($sp)		# zapisanie wartosci $a0
	move $a0,$a1
	jal dopDo4
	move $a0,$v0
	li $v0,9
	syscall			# alokacja pamieci na wynik
	move $s0,$v0		# $s0 - wskaznik na pamiec z ilorazem
	li $s2, 0		# $s2 - ilosc cyfr ilorazu
	
	move $a0,$a3
	addi $a0,$a0,1
	jal dopDo4		# dopelnienie ilosci znakow dzielnika +1 dla pozniejszej mozliwosci dodania czegos
	move $a0,$v0
	li $v0,9
	syscall			# alokacja pamieci na reszte
	move $s1,$v0		# $s1 - wskaznik na pamiec z reszta
	li $s3,0		# $s3 - ilosc cyfr reszty
	
	lw $a0,0($sp)		# wczytanie wartosci $a0
	add $sp,$sp,4
	
	sub $t0,$a1,$a3
	bltz $t0, divOut
	bgtz $t0, divDetShift
	jal divComp
	beqz $v0,divOut
	
	
	
divDetShift:
	move $t9,$t0
divLoop:
	jal divComp		# porownanie dzielnej i dzielnika przesunietego o $t0 cyfr w lewo
	bnez $v0, divSub	# jezeli dzielna jest wieksza odejmujemy
	beqz $t9, divOut	# jezeli dzielna jest mniejsza a dzielnik nie jest przesuniety to koniec dzielenia calkowitego
	subi $t9,$t9,1		# jezeli przesuniecie jest wieksze od zera,a dzielna jest mniejsza od przesunietego dzilenika, zmniejszamy przesuniecie
	j divLoop
divSub:	
	addi $sp,$sp,-16
	sw $a0,0($sp)
	sw $a1,4($sp)
	sw $a2,8($sp)
	sw $a3,12($sp)
	move $a0,$t9
	move $a1,$s0
	move $a2,$s2
	jal addition		# dodajemy do wyniku 10 podniesione do potegi przesuniecia dzielnika
	move $s2,$v1
	move $a0,$s4		# przepisanie argumentow do f-cji odejmujacej, rejestr $t9 przechowuje juz odp. wartosc wiec nic z nim nie robimy
	move $a1,$s6
	move $a2,$s5
	move $a3,$s7
	jal substract
	move $s5,$v1
	lw $a0,0($sp)
	lw $a1,4($sp)
	lw $a2,8($sp)
	lw $a3,12($sp)
	addi $sp,$sp,16
	move $a1, $s5
	j divLoop
		
divOut:
	bnez $s2,divCnt		# jezeli iloraz nie ma zadnej cyfry
	li $t1,48
	sb $t1,0($s0)		# iloraz = 0
divCnt:	bnez $s3,divCnt2	# je¿eli reszta nie ma zadnej cyfry
	li $t1,48
	sb $t1,0($s1)
divCnt2:
	move $t1,$s1
	move $t3,$a0
	blez $a1, divCnt3
divRewrite:
	lb $t2,0($t3)		# przepisanie wartosci z dzielnej do reszty
	beqz $t2,divCnt3	# jezeli pobralismy znak pusty, to konczymy petle
	sb $t2,0($t1)
	addi $t1,$t1,1		# przesuniecie wskaznikow
	addi $t3,$t3,1
	addi $s3,$s3,1
	j divRewrite
	
divCnt3:	
	move $v0,$s0		# ustawienie rejesrtow wynikowych
	move $v1,$s1
	move $t8,$s3
	move $t9,$s2
divExit:
	lw $ra,0($sp)
	lw $t0,4($sp)
	lw $t1,8($sp)
	lw $t2,12($sp)
	lw $t3,16($sp)
	lw $s0,20($sp)
	lw $s1,24($sp)
	lw $s2,28($sp)
	lw $s3,32($sp)
	lw $s4,36($sp)
	lw $s5,40($sp)
	lw $s6,44($sp)
	lw $s7,48($sp)
	addi $sp, $sp,52
	jr $ra
#---pomocnicza metoda f-cji dzilenia zapisujaca argumenty przed wywolaniem f-cji porownujacej---#
divComp:	
	addi $sp,$sp,-20
	sw $a0,0($sp)
	sw $a1,4($sp)
	sw $a2,8($sp)
	sw $a3,12($sp)
	sw $ra,16($sp)
	jal compare
	lw $a0,0($sp)
	lw $a1,4($sp)
	lw $a2,8($sp)
	lw $a3,12($sp)
	lw $ra,16($sp)
	addi $sp,$sp,20
	jr $ra
	
	
	
#-----------f-cja porownujaca----------#
# $a0 - wskaznik na dzielna
# $a1 - ilosc cyfr dzielnej
# $a2 - wskaznik na dzielnik
# $a3 - ilosc cyfr dzielnika
# $t9 - przesuniecie w sys. dziesietnym dzielnika w lewo
# $v0 - 1 : dzielna wieksza, 0: dzielnik wiekszy
compare:
	addi $sp,$sp,-12
	sw $t0,0($sp)
	sw $t1,4($sp)
	sw $t2,8($sp)
	
	add $t0,$t9,$a3 	# dodanie przesuniecia do ilosci cyfr dzielnika aby wstepnie sprawdzic czy dzielna jest wieksza
	bgt $a1,$t0, compTrue	# jezeli ilosc cyfr dzielnej jest wieksza od ilosci cyfr dzielnika to na pewno musi byc wieksza
	blt $a1,$t0, compFalse	# analogicznie w druga strone
compLoop:
	blez $a1, compTrue
	blez $a3, compTrue
	lb $t1,0($a0)		# wczytanie znakow
	lb $t2,0($a2)
	subi $a1,$a1,1		# zmniejszenie licznikow pozostalych cyfr
	subi $a3,$a3,1
	addi $a0,$a0,1		# przesuniecie wskaznikow w prawo
	addi $a2,$a2,1	
	subi $t1,$t1,48		# zamiana znakow na cyfry
	subi $t2,$t2,48
	bgt $t1,$t2,compTrue	# jezeli pobrana cyfra dzilenej jest wieksza od cyfry dzielnika to dzielna jest wieksza
	blt $t1,$t2,compFalse	# analogicznie
	j compLoop		# jezeli sa rowne wracamy na poczatek petli
	
compTrue:
	li $v0,1
	j compExit
compFalse:
	li $v0,0
	j compExit	
	
compExit:
	lw $t0,0($sp)
	lw $t1,4($sp)
	lw $t2,8($sp)
	addi $sp,$sp,12
	jr $ra
	
#------f-cja kopiujaca-----#
# $a0 - ciag znakow do skopiowania
# $a1 - ilosc znakow w ciagu
# $v0 - wskaznik na kopie ciagu $a0
copy:
	addi $sp,$sp,-16
	sw $t0,0($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)
	sw $ra,12($sp)
	sw $s2,16($sp)
	
	move $s0,$a0
	addi $a0,$a1,1
	jal dopDo4
	move $a0,$v0
	li $v0,9
	syscall		# alokacja pamieci na kopie ciagu
	move $s2,$v0
copyLoop:	
	blez $a1,copyOut
	lb $t0,0($s0)
	sb $t0,0($s2)
	addi $s0,$s0,1
	addi $s2,$s2,1
	subi $a1,$a1,1
	j copyLoop	
copyOut:
	lw $t0,0($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	lw $ra,12($sp)
	lw $s2,16($sp)		
	addi $sp,$sp,16
	jr $ra
	
#-------f-cja dodajaca okrojona------#
# f-cja dodaje tylko kolejne potegi 10
# $a0 - wykladnik potegi
# $a1 - wskaznik na pamiec z pierwszym skladnikiem
# $a2 - ilosc cyfr w pierwszym skladniku
# $v0 - adres na sume (taki sam jak $a1)
# $v1 - ilosc cyfr sumy
# $t8 - ilosc przeniesien podczas dodawania
addition:
	add $sp,$sp,-28
	sw $ra,0($sp)
	sw $s0,4($sp)
	sw $s1,8($sp)
	sw $s2,12($sp)
	sw $t0,16($sp)
	sw $t1,20($sp)
	sw $t2,24($sp)
	
	move $s0,$a0		# $s0 - wykladnik potegi
	move $s1,$a1		# $s1 - wskaznik na pamiec ze skladnikiem
	move $s2,$a2		# $s2 - ilosc cyfr w skladniku w pamieci
	addi $t1,$s0,1
	li $t8,0		# $t8 - ilosc przeniesien ktora wystapila podczas dodawania
	
	ble $t1,$s2, addCnt
	sub $t0,$t1,$s2
	move $a2,$t0
	move $a0,$s1
	move $a1,$s2
	add $s2,$s2,$t0		# przesuwamy liczbe o $t0 cyfr w prawo, aby zrobic miejsce na skladnik
	jal shiftRight
addCnt:
	add $t0,$s1,$s2
	subi $t0,$t0,1		# przesuniecie wskaznika na koniec ciagu cyfr skladnika
	sub $t0,$t0,$s0		# przesuniecie wskaznika na odpowiednia cyfre skladnika
addLoop:
	lb $t1,0($t0)		# wczytanie znaku z tego pola
	subi $t1,$t1,47		# zamiana na cyfre i dodanie jedynki
	bne $t1,10, addOut	# jezeli nie nastapilo przepelnienie konczymy f-cje
	addi $t8,$t8,1		# jezeli nastapilo przeniesienie dodajemy jeden do licznika przeniesien
	li $t1,48
	sb $t1,0($t0)		# zapisujemy zero na miejscy wczytanej cyfry
	sub $t2,$s1,$t0
	bnez $t2 addCnt2	# jezeli przepelnienie nie nastapilo na najbardziej znaczacej cyfrze dodajemy dalej
	li $a2,1
	move $a0,$s1
	move $a1,$s2
	addi $s2,$s2,1		# jezeli przepelnienie wystapilo na najbardzie znaczacej cyfrze przesuwamy cala liczbe w prawo 
	jal shiftRight
	j addLoop		# i wracamy do dodawania
	
addCnt2:	
	subi $t0,$t0,1		# przesuwamy wskaznik na nast cyfre po lewej
	j addLoop
addOut:	
	addi $t1,$t1,48		# zamiana na znak
	sb $t1,0($t0)		# zapisujemy wczytana cyfre
	move $v0, $s1
	move $v1, $s2
	lw $ra,0($sp)
	lw $s0,4($sp)
	lw $s1,8($sp)
	lw $s2,12($sp)
	lw $t0,16($sp)
	lw $t1,20($sp)
	lw $t2,24($sp)
	add $sp,$sp,28
	jr $ra
#-----------f-cja przesuwajaca---------#
# $a0 - adres na elementy do przesuniecia
# $a1 - ilosc cyfr w $a0
# $a2 - ilosc bajtow o ktore nalezy przesunac elementy w $a0
shiftRight:
	addi $sp,$sp,-12
	sw $s0,0($sp)
	sw $t0,4($sp)
	sw $t1,8($sp)
	
	move $v0,$a0
	move $t0,$a0
	add $a0,$a1,$a0
	subi $a0,$a0,1		# ustawienie wskaznika na koniec ciagu cyfr
	add $t0,$t0,$a1
	add $t0,$t0,$a2
	subi $t0,$t0,1		# ustawienei wskaznika na koniec ciagu cyfr i przesuniecie go o $a2 w prawo
shiftLoop:
	blez $a1, shiftFillWithZeros
	lb $t1,0($a0)		# wczytanie znaku
	sb $t1,0($t0)		# zapisanie znaku na prawo
	subi $a0,$a0,1		
	subi $t0,$t0,1		# przesuniecie wskaznikow
	subi $a1,$a1,1		# zminiejszenie pozostalej liczby cyfr do przesuniecia
	j shiftLoop
	
shiftFillWithZeros:
	blez $a2, shiftExit
	li $s0,48
	sb $s0,0($t0)
	subi $t0,$t0,1
	subi $a2,$a2,1
	j shiftFillWithZeros
	
shiftExit:
	lw $s0,0($sp)
	lw $t0,4($sp)
	lw $t1,8($sp)
	addi $sp,$sp,12
	jr $ra
	
	
#----------f-cja odejmujaca------------#
# $a0-odjemna 
# $a1-odjemnik 
# $a2-ilosc cyfr odjemnej 
# $a3-ilosc cyfr odjemnika 
# $t9 - przesunieczie odjemnika w prawo w sys. dzisietnym  
# $v0-roznica(wskaznik na to samo miejsce w pamieci co odjemna) 
# $v1-ilosc cyfr roznicy
substract:
	addi $sp,$sp,-40	# odpowiednie przesuniecie wskaznika konca stosu
	sw $s0,0($sp)		# |
	sw $s1,4($sp)		# |
	sw $s2,8($sp)		# |
	sw $s3,12($sp)		# |
	sw $t0,16($sp)		# |
	sw $t1,20($sp)		# |
	sw $t2,24($sp)		# |
	sw $t3,28($sp)		# |
	sw $t4,32($sp)		# |
	sw $t5,36($sp)		# |
	
	move $s0, $a2		# $s0 - ilosc cyfr odjemnej
	move $s1, $a3 		# $s1 - ilosc cyfr odjemnika
	move $s2, $a0		# $s2 - wskaznik na poczatek pamieci przeznaczonej na odjemna
	li $t5,0		# $t5 - flaga wskazujaca pobranie dziesuatki z bardziej znaczacej cyfry odjemnej
	
	add $a0,$s0,$a0		# |
	subi $a0,$a0,1		# przesuniecie wskaznika na koniec ciagu cyfr odjemnej
	sub $a0,$a0,$t9		# przesuniecie wskaznika o $t9 cyfr w lewo
	
	add $a1,$s1,$a1		# |
	subi $a1,$a1,1		# przesuniecie wskaznika na koniec ciagu cyfr odjemnika
	
	
#---Wlasciwa petla odejmujaca---#	
subLoop:blez $s0, subOut	# jezeli odjemna nie ma juz cyfr
	blez $s1, subOut	# jezeli odjemnik nie ma juz cyfr
	
	lb $t0,0($a0)		# pobranie znaku odjemnej
	subi $t0,$t0,48		# zamiana znaku na cyfre
	subi $s0,$s0,1		# zmniejszenie licznika pozostalych cyfr o jeden
	#subi $a0,$a0,1		# nie przesuwamy tu znacznika, poniewaz wynik zapiszemy odrazu w odjemnej na tym samym miejscu
	beqz $t5, subSkip	# jezeli w poprzednim kroku nie nastapilo przeniesienie dziesiatki (flaga w $t5 jest "false") przeskocz dalej
	subi $t0,$t0,1		# jezeli w poprzednim kroku pobralismy dziesiatke teraz nalezy odjac jeden
	li $t5, 0		# po odjeciu flaga niedoboru przestawiana jest na false
	
subSkip:lb $t1,0($a1)		# pobranie znaku odjemnej
	subi $t1,$t1,48		# zamiana znaku na cyfre
	subi $s1,$s1,1		# zmniejszenie licznika pozostalych cyfr o jeden
	subi $a1,$a1,1		# przesuniecie znacznika o znak w lewo
	
	sub $t0,$t0,$t1		# odjecie od siebie poszczegolnych cyfr
	bgez $t0,subCnt		# jezeli wynik jest nieujemny wszystko w porzadku
	addi $t0,$t0,10		# je¿eli nie to pobieramy dziesiatke z cyfry bardziej znaczacej odjemnej
	li $t5, 1		# ustawienie flagi pobrania na "true"
subCnt:	addi $t0,$t0,48		# zamiana cyfry na znak
	sb $t0,0($a0)		# zapisanie wyniku odejmowania na tym samym miejscu
	subi $a0,$a0,1		# przesuniecie znacznika odjemnej o jeden znak w lewo juz po zapisaniu wartosci
	j subLoop		# i powrot na poczatek petli
	
#---Czynnosci zakanczajace---#
subOut:
	beqz $t5, subCountZeros	# jezeli flaga niedoboru nie jest podniesiona to wszytko jest ok i przechodzimy dalej
	beqz $s0, subExc	# jezeli flaga niedoboru jest "true", a nie ma juz wiecej cyfr oznacza to, ze cos poszlo nie tak
	lb $t0,0($a0)		# pobierz znak z odjemnej
	subi $t0,$t0,49		# zamien znak na liczbe i odejmij od niej jeden
	li $t5,0		# ustawienie flagi na "false"
	bgez $t0, subCnt3	# jezeli po odjeciu liczba jest nieujemna to ok
	li $t5,1		# jezeli nie powtorne ustawienie flagi na true
	addi $t0,$t0,10		# i dodanie pobranej dziesiatki
subCnt3:addi $t0,$t0,48		# zamiana cyfry na znak
	sb $t0,0($a0)		# zapisanie znaku na powrot w tym samym miejscu
	subi $a0,$a0,1		# przesuniecie wskaznika w lewo gdyby trzeba bylo nadal odejmowac z powodu niedoboru
	subi $s0,$s0,1		# odjecie jedynki od ilosci pozostalych cyfr
	j subOut		# powrot na poczatek petli
subCountZeros:	
	li $t0,0		# licznik zer odjemnej
	move $t1,$s2			
subBrn:	lb $s3, 0($t1)		# ladujemy pierwsza cyfre odjemnej
	addi $t1,$t1,1		# przesuwany wskazniik w prawo 
	subi $s3,$s3,48		# zamieniamy znak na cyfre
	bnez $s3,subRemoveZero	# jezeli pierwsza cyfra odjemnej nie jest zerem, wszystko jest ok
	subi $a2,$a2,1		# zmniejszamy ilosc cyfr odjemnej
	addi $t0,$t0,1		# zwiekszamy ilosc zer
	j subBrn		
	
#---Czynnosci porzadkujace---#
subRemoveZero:
	beqz $t0,subEnd		# jezeli ilosc zer na pocztku wynosi zero nie nalezy nic przesuwac
	add $t2,$s2,$t0		# $t2 wskaznik na pocztek pola pamieci odjemnej przesuniety o ilosc zer na poczatku
	move $t1, $s2		# $t1 wskaznik na pocztek pola pamieci odjemnej
	move $t3,$a2	# $t3 ilosc pozostalych cyfr
subBrn2:
	beqz $t3,subAddNulls
	lb $t4,0($t2)		# zaladowanie znaku
	sb $t4,0($t1)		# zapisanie go $t0 bajtow na lewo
	subi $t3,$t3,1		# zmniejszenie ilosci cyfr pozostalych do przepisania o 1
	addi $t2,$t2,1		# przesuniecie wskaznika na nast cyfre
	addi $t1,$t1,1 		# j/w
	j subBrn2
	
subAddNulls:
	li $t2,0		# $t2 = NULL [pusty znak]
subBrn3:
	beqz $t0,subEnd
	sb $t2,0($t1)		# zapisz w miejscach pozostalych po przesunieciu puste znaki		
	addi $t1,$t1,1		# przesuwaj znacznik o znak w prawo
	subi $t0,$t0,1		# zmniejszenie liczby pozostalych miejsc do wypelnienia o jeden 
	j subBrn3
	
subEnd:
	move $v0,$s2		# $v0 - wskaznik na pamiec z roznica	
	move $v1,$a2		# $v1 - ilosc cyfr roznicy
	lw $s0,0($sp)		# |
	lw $s1,4($sp)		# |
	lw $s2,8($sp)		# |
	lw $s3,12($sp)		# |
	lw $t0,16($sp)		# |
	lw $t1,20($sp)		# |
	lw $t2,24($sp)		# |
	lw $t3,28($sp)		# |
	lw $t4,32($sp)		# |
	lw $t5,36($sp)		# |
	addi $sp,$sp,40		# odpowiednie przesuniecie wskaznika konca stosu
	jr $ra
	
	
subExc:
	j exit
	
	 
#---f-cja czytajaca wejscie z klawaitury---#
# $v0 adres na miejsce w pamieci pobranego string lub kod bledu:
# 1 - wpisano  cos innego niz cyfre, 2 - wartosc wpisanej cyfry wynosi 0
# $v1 dlugosc wpisanej liczby
# $t9 - znak wpisanej liczby 1 - dodatnia, 0 - ujuemna
readString:
	 addi $sp,$sp,-36
	 sw $t0,0($sp)
	 sw $t1,4($sp)
	 sw $t2,8($sp)
	 sw $t3,12($sp)
	 sw $t4,16($sp)
	 sw $t5,20($sp)
	 sw $t6,24($sp)
	 sw $t7,28($sp)
	 sw $t8,32($sp)
	 
	 li $t0,4		# licznik pozostalych znakow
	 li $t2,0		# licznik wpisanych znakow
	 li $t7,2		# flaga wskazujaca czy wczytana liczba jest ujemna czy nie
	 li $a0,4
	 li $v0,9
	 syscall		# alokacja pamieci na cztery znaki
	 move $t1,$v0		# $t1 wskaznik na poczatek stringa
	 move $t3,$t1		# $t3 wskaznik na ostatnio wpisany znak
readChar:li $v0,12
	 syscall		# wczytanie jednego znaku
	 move $t4,$v0
	 beq $v0,10,readOut
	 subi $t4,$t4,48	# zamiana znaku na cyfre
	 
	 bne $t7,2, readCnt2	# jezeli flaga dotyczaca znaku nie zostala jeszcze ustawiona
	 bne $t4,-3, readFlagTrue
	 li $t7,0
	 j readCnt3
readFlagTrue:
	 li $t7,1
	 j readCnt2	 
readCnt2:
	 div $t5,$t4,10		# sprawdzenie czy wczytany znak to rzeczywiscie cyfra
	 mfhi $t6
	 bnez $t5, readExc1	# jezeli wpisany znak nie jest cyfra zakoncz f-cja z odpowiednim kodem bledu
	 bltz $t6, readExc1	# j/w
	 bnez $t4, readCnt	# jezeli wpisany znak to '0'
	 bnez $t2, readCnt	# i jezeli jeszcze zaden znak nie zostal wpisany
	 j readChar		# pomin wczytywanie tego znaku
readCnt:
	addi $t2,$t2,1		# dodaj jeden do ilosci wpisanych znakow 
	sb $v0, 0($t3)		# zapisz znak w pamieci
	subi $t0,$t0,1		# odejmij jeden od ilosci pozostalej pamieci na znaki
	addi $t3,$t3,1		# przesun wskaznik o jeden znak w lewo
	bgt $t0,0,readCnt3	# jezeli konczy sie nam zaalokowana pamiec
	li $a0,4
	li $v0, 9
	syscall			# nalezy zaalokowac nastepne cztery bajty
	addi $t0,$t0,4
	 
readCnt3:	 
	 j readChar

readExc1:
	li $v0,1
	j readExit
	
readExc2:
	li $v0,2
	j readExit

readOut:
	beq $t2,0, readExc2
	li $t4,0
	sb $t4,0($t3)
	move $v0,$t1
	move $v1,$t2
	move $t9,$t7
	lw $t0,0($sp)
	lw $t1,4($sp)
	lw $t2,8($sp)
	lw $t3,12($sp)
	lw $t4,16($sp)
	lw $t5,20($sp)
	lw $t6,24($sp)
	lw $t7,28($sp)
	lw $t8,32($sp)
	addi $sp,$sp,36
readExit:
	jr $ra
