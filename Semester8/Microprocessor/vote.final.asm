INCLUDE 'EMU8086.INC'

.MODEL SMALL
.STACK 100H

.DATA
    CANDIDATE_NAMES DB 'Zahin Afsar$', 88 DUP('$')      
                    DB 'Nurul Huda$', 89 DUP('$')        
                    DB 'Jubayer Al Mamun$', 83 DUP('$')      
                    DB 100 DUP('$')                    
                    
    USER_NAMES      DB 'Rahim Sarker$', 87 DUP('$')         
                    DB 'Mursalin Parvez$', 84 DUP('$')           
                    DB 'Sarwar Hosen$', 87 DUP('$')        
                    DB 100 DUP('$')                        
               
    VOTES           DW 100 DUP(0)              
    VOTED_USERS     DB 100 DUP(0)        
    
    NUM_CANDIDATES  DW 3               
    NUM_USERS       DW 3                    
    TEMP            DB 100 DUP('$')             
    
    MSG_HEADER      DB 'VOTING MANAGEMENT SYSTEM', 0DH, 0AH, '$'
    MSG_MENU        DB 'Menu:', 0DH, 0AH
                    DB '1. Add Candidate', 0DH, 0AH
                    DB '2. View Candidates', 0DH, 0AH
                    DB '3. Add User', 0DH, 0AH
                    DB '4. View Users', 0DH, 0AH
                    DB '5. Start Voting', 0DH, 0AH
                    DB '6. View Results', 0DH, 0AH
                    DB '7. Exit', 0DH, 0AH
                    DB 'Choose option: $'
    MSG_ENTER_NAME  DB 'Enter name: $'
    MSG_CANDIDATE_LIST DB 'Candidate List:', 0DH, 0AH, '$'
    MSG_USER_LIST   DB 'User List:', 0DH, 0AH, '$'
    MSG_PRESS_ENTER DB 'Press Enter to continue...', 0DH, 0AH, '$'
    NEW_LINE        DB 0DH, 0AH, '$'

.CODE

MAIN PROC
    MOV AX, @DATA
    MOV DS, AX
    
MENU:
    MOV AX, 0003H
    INT 10H
    
    LEA DX, MSG_HEADER
    MOV AH, 9
    INT 21H
    
    LEA DX, MSG_MENU
    MOV AH, 9
    INT 21H
    
    CALL SCAN_NUM
    
    CMP CX, 1
    JE ADD_CANDIDATE
    CMP CX, 2
    JE VIEW_CANDIDATES
    CMP CX, 3
    JE ADD_USER
    CMP CX, 4
    JE VIEW_USERS
    CMP CX, 5
    JE START_VOTING
    CMP CX, 6
    JE VIEW_RESULTS
    CMP CX, 7
    JE EXIT
    JMP MENU

ADD_CANDIDATE:
    CALL ADD_CANDIDATE_PROC
    JMP MENU
    
VIEW_CANDIDATES:
    CALL VIEW_CANDIDATES_PROC
    CALL WAIT_FOR_ENTER
    JMP MENU
    
ADD_USER:
    CALL ADD_USER_PROC
    JMP MENU
    
VIEW_USERS:
    CALL VIEW_USERS_PROC
    CALL WAIT_FOR_ENTER
    JMP MENU

START_VOTING:
    CALL START_VOTING_PROC
    JMP MENU

VIEW_RESULTS:
    CALL VIEW_RESULTS_PROC
    CALL WAIT_FOR_ENTER
    JMP MENU
    
EXIT:
    MOV AH, 4CH
    INT 21H
    
MAIN ENDP

ADD_CANDIDATE_PROC PROC
    PRINTN ''
    PRINT 'Enter candidate name: '
    
    LEA SI, CANDIDATE_NAMES
    MOV AX, NUM_CANDIDATES
    MOV BX, 100
    MUL BX
    ADD SI, AX
    
    MOV AH, 1
    
INPUT_LOOP:
    INT 21H
    CMP AL, 13  
    JE END_INPUT
    MOV [SI], AL
    INC SI
    JMP INPUT_LOOP
    
END_INPUT:
    INC NUM_CANDIDATES
    PRINTN ''
    RET
ADD_CANDIDATE_PROC ENDP

VIEW_CANDIDATES_PROC PROC
    PRINTN ''
    PRINT 'Candidates:'
    PRINTN ''
    
    MOV CX, NUM_CANDIDATES
    LEA SI, CANDIDATE_NAMES
    XOR BX, BX 
    
PRINT_LOOP:
    PUSH CX
    
    INC BX
    PRINT '['
    MOV AX, BX
    CALL PRINT_NUM
    PRINT '] '
    
    MOV DX, SI
    MOV AH, 9
    INT 21H
    PRINTN ''
    ADD SI, 100
    POP CX
    LOOP PRINT_LOOP
    
    RET
VIEW_CANDIDATES_PROC ENDP

VIEW_RESULTS_PROC PROC
    PRINTN ''
    PRINT 'Election Results:'
    PRINTN ''
    
    MOV CX, NUM_CANDIDATES
    LEA SI, CANDIDATE_NAMES
    LEA DI, VOTES
    XOR BX, BX 
    
RESULTS_LOOP:
    PUSH CX
    
    INC BX
    PRINT '['
    MOV AX, BX
    CALL PRINT_NUM
    PRINT '] '
    
    MOV DX, SI
    MOV AH, 9
    INT 21H
    
    PRINT ' - Votes: '
    
    MOV AX, [DI]
    CALL PRINT_NUM
    PRINTN ''
    
    ADD SI, 100  
    ADD DI, 2    
    POP CX
    LOOP RESULTS_LOOP
    
    RET
VIEW_RESULTS_PROC ENDP

ADD_USER_PROC PROC
    PRINTN ''
    PRINT 'Enter user name: '
    
    LEA SI, USER_NAMES
    MOV AX, NUM_USERS
    MOV BX, 100
    MUL BX
    ADD SI, AX
    
    MOV AH, 1
    
USER_INPUT_LOOP:
    INT 21H
    CMP AL, 13  
    JE END_USER_INPUT
    MOV [SI], AL
    INC SI
    JMP USER_INPUT_LOOP
    
END_USER_INPUT:
    MOV BYTE PTR [SI], '$'  
    INC NUM_USERS
    PRINTN ''
    RET
ADD_USER_PROC ENDP

VIEW_USERS_PROC PROC
    PRINTN ''
    PRINT 'Users:'
    PRINTN ''
    
    MOV CX, NUM_USERS
    LEA SI, USER_NAMES
    XOR BX, BX 
    
PRINT_USERS_LOOP:
    PUSH CX
    
    INC BX
    PRINT '['
    MOV AX, BX
    CALL PRINT_NUM
    PRINT '] '
    
    MOV DX, SI
    MOV AH, 9
    INT 21H
    PRINTN ''
    ADD SI, 100
    POP CX
    LOOP PRINT_USERS_LOOP

    RET
VIEW_USERS_PROC ENDP

START_VOTING_PROC PROC
START_VOTING_LOOP:
    MOV AX, 0003H
    INT 10H
    
    CALL VIEW_CANDIDATES_PROC
    
    PRINTN ''
    PRINT 'Select Candidate Number (1-'
    MOV AX, NUM_CANDIDATES
    CALL PRINT_NUM
    PRINT ') or 0 to return to menu: '
    CALL SCAN_NUM
    
    CMP CX, 0
    JE MENU
    
    CMP CX, 1
    JL INVALID_CANDIDATE
    CMP CX, NUM_CANDIDATES
    JG INVALID_CANDIDATE
    
    PUSH CX
    
    PRINTN ''
    PRINT 'Enter Your User ID (1-'
    MOV AX, NUM_USERS
    CALL PRINT_NUM
    PRINT '): '
    CALL SCAN_NUM
    
    CMP CX, 1
    JL INVALID_USER
    CMP CX, NUM_USERS
    JG INVALID_USER
    
    DEC CX
    MOV SI, OFFSET VOTED_USERS
    ADD SI, CX
    CMP BYTE PTR [SI], 1
    JE ALREADY_VOTED
    
    MOV BYTE PTR [SI], 1
    
    POP CX  
    DEC CX
    SHL CX, 1  
    MOV SI, OFFSET VOTES
    ADD SI, CX
    INC WORD PTR [SI]
    
    PRINTN ''
    PRINT 'Vote cast successfully!'
    CALL WAIT_FOR_ENTER
    JMP START_VOTING_LOOP
    
INVALID_CANDIDATE:
    PRINTN ''
    PRINT 'Error: Invalid candidate number!'
    CALL WAIT_FOR_ENTER
    JMP START_VOTING_LOOP
    
INVALID_USER:
    POP CX  
    PRINTN ''
    PRINT 'Error: Invalid user ID!'
    CALL WAIT_FOR_ENTER
    JMP START_VOTING_LOOP
    
ALREADY_VOTED:
    POP CX  
    PRINTN ''
    PRINT 'Error: You have already voted!'
    CALL WAIT_FOR_ENTER
    JMP START_VOTING_LOOP
    
START_VOTING_PROC ENDP

WAIT_FOR_ENTER PROC
    PRINTN ''
    LEA DX, MSG_PRESS_ENTER
    MOV AH, 9
    INT 21H
    
WAIT_KEY:
    MOV AH, 1
    INT 21H
    CMP AL, 13  
    JNE WAIT_KEY
    
    RET
WAIT_FOR_ENTER ENDP

DEFINE_SCAN_NUM
DEFINE_PRINT_NUM
DEFINE_PRINT_NUM_UNS

END MAIN
