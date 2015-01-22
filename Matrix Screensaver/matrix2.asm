.model small
.stack 100h
.486	;TR advised to use this instead of .386
.data
seed	DW 0F55dh
arr	db 80 dup(0)	;Used to determine length of line
.code
main PROC
	mov ax, @data
	mov ds, ax
	mov ax, 0b800h
	mov es, ax

	;install interrupt handler
	push ds
	mov ax,@code
	mov ds,ax
	mov ah,25h
	mov al,9
	mov dx, offset My_int
	int 21h
	pop ds

;=======MATRIX SCREENSAVER BEGINS=======

Matrix:

;====Move Down====
	mov cx, 23
	mov bx, 0

	LoopY:
		mov bx, 0
		LoopX:
			call GetChar1
			call PutChar1
			cmp bx, 79
			JGE OutX
			inc bx
			jmp LoopX
		outX:
		cmp cx, 0
		JLE outY
		dec cx
		jmp LoopY
	outY:	

;====Fill Array====

	mov bx, 0	;INDEX	
	mov si, OFFSET arr
FillArray1:
	mov al, [si+bx]
	cmp al, 0
	JNE EnterValue1

	EnterValue1:			
		call Rand
		mov cx, 10
		xor dx, dx
		div cx
		mov ax, dx
		cmp ax, 1
		JGE Skip1

		call Rand
		mov cx, 10
		xor dx, dx
		div cx
		mov ax, dx
		cmp ax, 5
		JLE Skip1
		mov byte ptr [si+bx], al
	Skip1:
		cmp bx, 80
		JG FillArrayExit1
		inc bx
		JMP FillArray1
FillArrayExit1:	

;====Print First Row====
	
NewStep1:
	mov bx, 0
	mov ax, 0
	mov cx, 2
	mov si, 0
	mov si, OFFSET arr
NewLine1:	
	mov al, [si+bx]
	cmp al, 0
	JNE PrintChar1
	
	;If 0
	push bx
	mov ax, bx
	mul cx
	mov bx, ax
	mov ah, 0Ah
	mov al, 20h
	mov es:[bx], ax
	pop bx
	JMP NextChar1

	PrintChar1:
	dec al
	mov byte ptr [si+bx], al
	push bx
	mov ax, bx
	mul cx
	mov bx, ax
	CALL Rand
	mov ah, 0Ah
	mov es:[bx], ax		;Prints character
	pop bx
	
	NextChar1:
	mov ax, 0
	inc bx
	cmp bx, 80		;compares to last spot on the row
	JGE NewLineEnd1
	JMP NewLine1		;Loops until entire row is filled
NewLineEnd1:

	call BusyWait
	jmp Matrix

	mov ax, 4C00h
	int 21h
main ENDP

;=====================PROCEDURES======================

Rand PROC
;	Modified procedure 5th edition: p 429
;   Need to modify
;	Output is in DX:AX in the original thing
;	Range : 0 - FFFFh
	mov ax, 343Dh
	mul seed
	xor dx, dx
	add ax, 5C3h
	mov seed, ax
	ror ax, 4
	mov dh, dl
	mov dl, ah
	ror dx, 8
	ret
Rand ENDP

PutChar1 PROC
;	Parameters
;	CX = row
;	BX = column
;	AX = Character
	
	push bx
	;push cx		
	push ax

	mov ax, 160
	mul cx
	shl bx, 1	;With or without no changes
	add bx, ax

	pop ax

	;sub bx, 160	
	;mov es:[bx], ax	
	add bx, 160
	mov es:[bx], ax
	
	pop bx
	;pop cx		
	ret
PutChar1 ENDP

GetChar1 PROC	;WORKING
;	Parameters
;	CX = row
;	BX = column
;	AX = Character

	push bx
	;push cx		

	mov ax, 160
	mul cx
	shl bx, 1
	add bx, ax

	mov ax, es:[bx]
	
	pop bx
	;pop cx	
	ret
GetChar1 ENDP

BusyWait PROC
	mov esi, 1000000000
L1:	
	dec esi
	cmp esi, 0
	jge L1
	ret
BusyWait ENDP

my_int PROC
	sti
	mov ax, 4c00h
	int 21h
	cli
	iret
my_int ENDP

end main