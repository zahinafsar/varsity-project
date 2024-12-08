INCLUDE 'EMU8086.INC'

.MODEL SMALL
.STACK 100H

.DATA
    N DW ?
    SUM DW 0
    ARR DW DUP(100) ;DECLARING AN ARRAY WITH 100 SPACE

.CODE

MAIN PROC
    MOV AX,@DATA
    MOV DS, AX

    PRINTN ''
    PRINT 'Enter the value of N: '
    CALL SCAN_NUM
    MOV N, CX

    MOV SI, 0
    MOV CX, N ;INITIALIZING LOOP COUNTER
    
    START:
        PRINTN ''
        CALL TAKE_INPUT_FROM_USER
        INC SI
        CMP SI, N

    LOOP START

    PRINTN ''
    PRINT 'Average of the numbers is: '
    MOV AX, SUM
    MOV BX, N
    DIV BX
    CALL PRINT_NUM  ; Print decimal part
    PRINTN ''
    
    RETURN:
    MOV AH, 4CH
    INT 21H
    
MAIN ENDP

DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS
DEFINE_SCAN_NUM

TAKE_INPUT_FROM_USER PROC
    XOR BX, BX
    MOV BX, CX
    XOR CX, CX
    PRINT 'Enter a number: '
    CALL SCAN_NUM
    MOV ARR[SI], CX
    ADD SUM, CX
    MOV CX, BX
    
RET
TAKE_INPUT_FROM_USER ENDP


END MAIN