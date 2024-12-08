INCLUDE 'EMU8086.INC'

.MODEL SMALL
.STACK 100H

.DATA
    ; Arrays and counters for candidates
    MAX_CANDIDATES EQU 10
    CANDIDATE_COUNT DW 0
    CANDIDATE_NAMES DB MAX_CANDIDATES DUP(20 DUP('$'))  ; 10 candidates, 20 chars each
    
    ; Arrays and counters for users
    MAX_USERS EQU 20
    USER_COUNT DW 0
    USER_NAMES DB MAX_USERS DUP(20 DUP('$'))           ; 20 users, 20 chars each
    
    ; Arrays and counters for events
    MAX_EVENTS EQU 5
    EVENT_COUNT DW 0
    EVENT_NAMES DB MAX_EVENTS DUP(20 DUP('$'))         ; 5 events, 20 chars each
    EVENT_CANDIDATES DB MAX_EVENTS DUP(MAX_CANDIDATES DUP(0)) ; Stores associated candidates
    
    ; Vote tracking
    VOTES DB MAX_EVENTS DUP(MAX_CANDIDATES DUP(0))     ; Stores votes per candidate per event
    
    ; Messages
    MENU_MSG DB 'Voting System Menu:$'
    MENU_1 DB '1. Manage Candidates$'
    MENU_2 DB '2. Manage Users$'
    MENU_3 DB '3. Manage Events$'
    MENU_4 DB '4. Vote$'
    MENU_5 DB '5. Exit$'
    
    CANDIDATE_MENU DB '1. Create Candidate 2. View Candidates 3. Back$'
    USER_MENU DB '1. Create User 2. View Users 3. Back$'
    EVENT_MENU DB '1. Create Event 2. View Events 3. Back$'
    
    INPUT_NAME_MSG DB 'Enter name: $'
    SELECT_EVENT_MSG DB 'Select event (1-5): $'
    SELECT_CANDIDATE_MSG DB 'Select candidate (1-10): $'
    SELECT_USER_MSG DB 'Enter user ID (1-20): $'
    
    INVALID_MSG DB 'Invalid input!$'
    SUCCESS_MSG DB 'Operation successful!$'
    NEWLINE DB 13,10,'$'

.CODE
MAIN PROC
    MOV AX, @DATA
    MOV DS, AX
    
    MAIN_MENU:
        CALL CLEAR_SCREEN
        
        ; Display main menu
        LEA DX, MENU_MSG
        MOV AH, 9
        INT 21H
        PRINTN
        
        LEA DX, MENU_1
        MOV AH, 9
        INT 21H
        PRINTN
        
        LEA DX, MENU_2
        MOV AH, 9
        INT 21H
        PRINTN
        
        LEA DX, MENU_3
        MOV AH, 9
        INT 21H
        PRINTN
        
        LEA DX, MENU_4
        MOV AH, 9
        INT 21H
        PRINTN
        
        LEA DX, MENU_5
        MOV AH, 9
        INT 21H
        PRINTN
        
        ; Get user choice
        MOV AH, 1
        INT 21H
        SUB AL, '0'
        
        CMP AL, 1
        JE CANDIDATE_MANAGEMENT
        CMP AL, 2
        JE USER_MANAGEMENT
        CMP AL, 3
        JE EVENT_MANAGEMENT
        CMP AL, 4
        JE VOTING_PROCESS
        CMP AL, 5
        JE EXIT_PROGRAM
        
        JMP MAIN_MENU

CANDIDATE_MANAGEMENT:
    CALL MANAGE_CANDIDATES
    JMP MAIN_MENU
    
USER_MANAGEMENT:
    CALL MANAGE_USERS
    JMP MAIN_MENU
    
EVENT_MANAGEMENT:
    CALL MANAGE_EVENTS
    JMP MAIN_MENU
    
VOTING_PROCESS:
    CALL HANDLE_VOTING
    JMP MAIN_MENU
    
EXIT_PROGRAM:
    MOV AH, 4CH
    INT 21H
    
MAIN ENDP

; Procedure to manage candidates
MANAGE_CANDIDATES PROC
    PUSH AX
    PUSH DX
    
    DISPLAY_CANDIDATE_MENU:
        CALL CLEAR_SCREEN
        LEA DX, CANDIDATE_MENU
        MOV AH, 9
        INT 21H
        PRINTN
        
        MOV AH, 1
        INT 21H
        SUB AL, '0'
        
        CMP AL, 1
        JE CREATE_CANDIDATE
        CMP AL, 2
        JE VIEW_CANDIDATES
        CMP AL, 3
        JE EXIT_CANDIDATE_MENU
        
    CREATE_CANDIDATE:
        ; Check if max candidates reached
        MOV AX, CANDIDATE_COUNT
        CMP AX, MAX_CANDIDATES
        JAE DISPLAY_CANDIDATE_MENU
        
        ; Get candidate name
        LEA DX, INPUT_NAME_MSG
        MOV AH, 9
        INT 21H
        
        ; Calculate position in array
        MOV BX, CANDIDATE_COUNT
        MOV CL, 20
        MUL CL
        LEA SI, CANDIDATE_NAMES
        ADD SI, AX
        
        ; Read name
        MOV CX, 19          ; Max 19 chars + null
        CALL READ_STRING
        
        INC CANDIDATE_COUNT
        JMP DISPLAY_CANDIDATE_MENU
        
    VIEW_CANDIDATES:
        CALL DISPLAY_CANDIDATES
        MOV AH, 1           ; Wait for key press
        INT 21H
        JMP DISPLAY_CANDIDATE_MENU
        
    EXIT_CANDIDATE_MENU:
        POP DX
        POP AX
        RET
MANAGE_CANDIDATES ENDP

; Procedure to manage users
MANAGE_USERS PROC
    PUSH AX
    PUSH DX
    
    DISPLAY_USER_MENU:
        CALL CLEAR_SCREEN
        LEA DX, USER_MENU
        MOV AH, 9
        INT 21H
        PRINTN
        
        MOV AH, 1
        INT 21H
        SUB AL, '0'
        
        CMP AL, 1
        JE CREATE_USER
        CMP AL, 2
        JE VIEW_USERS
        CMP AL, 3
        JE EXIT_USER_MENU
        
    CREATE_USER:
        ; Check if max users reached
        MOV AX, USER_COUNT
        CMP AX, MAX_USERS
        JAE DISPLAY_USER_MENU
        
        ; Get user name
        LEA DX, INPUT_NAME_MSG
        MOV AH, 9
        INT 21H
        
        ; Calculate position in array
        MOV BX, USER_COUNT
        MOV CL, 20
        MUL CL
        LEA SI, USER_NAMES
        ADD SI, AX
        
        ; Read name
        MOV CX, 19          ; Max 19 chars + null
        CALL READ_STRING
        
        INC USER_COUNT
        JMP DISPLAY_USER_MENU
        
    VIEW_USERS:
        CALL DISPLAY_USERS
        MOV AH, 1           ; Wait for key press
        INT 21H
        JMP DISPLAY_USER_MENU
        
    EXIT_USER_MENU:
        POP DX
        POP AX
        RET
MANAGE_USERS ENDP

; Procedure to manage events
MANAGE_EVENTS PROC
    PUSH AX
    PUSH DX
    
    DISPLAY_EVENT_MENU:
        CALL CLEAR_SCREEN
        LEA DX, EVENT_MENU
        MOV AH, 9
        INT 21H
        PRINTN
        
        MOV AH, 1
        INT 21H
        SUB AL, '0'
        
        CMP AL, 1
        JE CREATE_EVENT
        CMP AL, 2
        JE VIEW_EVENTS
        CMP AL, 3
        JE EXIT_EVENT_MENU
        
    CREATE_EVENT:
        ; Check if max events reached
        MOV AX, EVENT_COUNT
        CMP AX, MAX_EVENTS
        JAE DISPLAY_EVENT_MENU
        
        ; Get event name
        LEA DX, INPUT_NAME_MSG
        MOV AH, 9
        INT 21H
        
        ; Calculate position in array
        MOV BX, EVENT_COUNT
        MOV CL, 20
        MUL CL
        LEA SI, EVENT_NAMES
        ADD SI, AX
        
        ; Read name
        MOV CX, 19          ; Max 19 chars + null
        CALL READ_STRING
        
        ; Associate candidates
        CALL ASSOCIATE_CANDIDATES
        
        INC EVENT_COUNT
        JMP DISPLAY_EVENT_MENU
        
    VIEW_EVENTS:
        CALL DISPLAY_EVENTS
        MOV AH, 1           ; Wait for key press
        INT 21H
        JMP DISPLAY_EVENT_MENU
        
    EXIT_EVENT_MENU:
        POP DX
        POP AX
        RET
MANAGE_EVENTS ENDP

; Procedure to handle voting
HANDLE_VOTING PROC
    PUSH AX
    PUSH BX
    PUSH DX
    
    ; Display events
    CALL DISPLAY_EVENTS
    
    ; Select event
    LEA DX, SELECT_EVENT_MSG
    MOV AH, 9
    INT 21H
    
    MOV AH, 1
    INT 21H
    SUB AL, '0'
    DEC AL          ; Convert to 0-based index
    MOV BL, AL      ; Store event index
    
    ; Select user
    LEA DX, SELECT_USER_MSG
    MOV AH, 9
    INT 21H
    
    MOV AH, 1
    INT 21H
    SUB AL, '0'
    DEC AL          ; Convert to 0-based index
    
    ; Display associated candidates
    CALL DISPLAY_EVENT_CANDIDATES
    
    ; Select candidate
    LEA DX, SELECT_CANDIDATE_MSG
    MOV AH, 9
    INT 21H
    
    MOV AH, 1
    INT 21H
    SUB AL, '0'
    DEC AL          ; Convert to 0-based index
    
    ; Record vote
    PUSH BX         ; Save event index
    MOV BL, AL      ; Candidate index
    POP AX          ; Event index
    CALL RECORD_VOTE
    
    POP DX
    POP BX
    POP AX
    RET
HANDLE_VOTING ENDP

; Helper procedures
CLEAR_SCREEN PROC
    MOV AX, 0003H
    INT 10H
    RET
CLEAR_SCREEN ENDP

READ_STRING PROC
    PUSH AX
    PUSH CX
    PUSH BX
    MOV BX, SI      ; Save string position
    
    READ_CHAR:
        MOV AH, 1
        INT 21H
        
        CMP AL, 13  ; Check for Enter key
        JE DONE_READING
        
        MOV [BX], AL
        INC BX
        LOOP READ_CHAR
        
    DONE_READING:
        MOV BYTE PTR [BX], '$'  ; Add string terminator
        POP BX
        POP CX
        POP AX
        RET
READ_STRING ENDP

DISPLAY_CANDIDATES PROC
    PUSH AX
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH SI
    
    MOV CX, CANDIDATE_COUNT
    XOR SI, SI
    MOV BX, 1
    
    CMP CX, 0
    JE NO_CANDIDATES
    
    DISPLAY_NEXT_CANDIDATE:
        PUSH CX
        
        ; Display candidate number
        MOV AH, 2
        MOV DL, BL
        ADD DL, '0'
        INT 21H
        MOV DL, '.'
        INT 21H
        MOV DL, ' '
        INT 21H
        
        ; Display candidate name
        LEA DX, CANDIDATE_NAMES[SI]
        MOV AH, 9
        INT 21H
        
        ; New line
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        
        ADD SI, 20
        INC BX
        POP CX
        LOOP DISPLAY_NEXT_CANDIDATE
        JMP END_DISPLAY_CANDIDATES
        
    NO_CANDIDATES:
        LEA DX, INVALID_MSG
        MOV AH, 9
        INT 21H
        
    END_DISPLAY_CANDIDATES:
        POP SI
        POP DX
        POP CX
        POP BX
        POP AX
        RET
DISPLAY_CANDIDATES ENDP

DISPLAY_USERS PROC
    PUSH AX
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH SI
    
    MOV CX, USER_COUNT
    XOR SI, SI
    MOV BX, 1
    
    CMP CX, 0
    JE NO_USERS
    
    DISPLAY_NEXT_USER:
        PUSH CX
        
        ; Display user number
        MOV AH, 2
        MOV DL, BL
        ADD DL, '0'
        INT 21H
        MOV DL, '.'
        INT 21H
        MOV DL, ' '
        INT 21H
        
        ; Display user name
        LEA DX, USER_NAMES[SI]
        MOV AH, 9
        INT 21H
        
        ; New line
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        
        ADD SI, 20
        INC BX
        POP CX
        LOOP DISPLAY_NEXT_USER
        JMP END_DISPLAY_USERS
        
    NO_USERS:
        LEA DX, INVALID_MSG
        MOV AH, 9
        INT 21H
        
    END_DISPLAY_USERS:
        POP SI
        POP DX
        POP CX
        POP BX
        POP AX
        RET
DISPLAY_USERS ENDP

DISPLAY_EVENTS PROC
    PUSH AX
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH SI
    
    MOV CX, EVENT_COUNT
    XOR SI, SI
    MOV BX, 1
    
    CMP CX, 0
    JE NO_EVENTS
    
    DISPLAY_NEXT_EVENT:
        PUSH CX
        
        ; Display event number
        MOV AH, 2
        MOV DL, BL
        ADD DL, '0'
        INT 21H
        MOV DL, '.'
        INT 21H
        MOV DL, ' '
        INT 21H
        
        ; Display event name
        LEA DX, EVENT_NAMES[SI]
        MOV AH, 9
        INT 21H
        
        ; New line
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        
        ADD SI, 20
        INC BX
        POP CX
        LOOP DISPLAY_NEXT_EVENT
        JMP END_DISPLAY_EVENTS
        
    NO_EVENTS:
        LEA DX, INVALID_MSG
        MOV AH, 9
        INT 21H
        
    END_DISPLAY_EVENTS:
        POP SI
        POP DX
        POP CX
        POP BX
        POP AX
        RET
DISPLAY_EVENTS ENDP

ASSOCIATE_CANDIDATES PROC
    PUSH AX
    PUSH BX
    PUSH CX
    PUSH DX
    
    ; Display available candidates
    CALL DISPLAY_CANDIDATES
    
    ; Get event index
    MOV BX, EVENT_COUNT
    
    ; For each candidate
    MOV CX, CANDIDATE_COUNT
    XOR SI, SI
    
    ASSOCIATE_NEXT:
        PUSH CX
        
        ; Ask if candidate should be associated
        MOV AH, 2
        MOV DL, 'A'
        INT 21H
        MOV DL, 's'
        INT 21H
        MOV DL, 's'
        INT 21H
        MOV DL, 'o'
        INT 21H
        MOV DL, 'c'
        INT 21H
        MOV DL, '?'
        INT 21H
        MOV DL, ' '
        INT 21H
        
        ; Get Y/N input
        MOV AH, 1
        INT 21H
        
        CMP AL, 'Y'
        JE ASSOCIATE_YES
        CMP AL, 'y'
        JE ASSOCIATE_YES
        JMP ASSOCIATE_NO
        
    ASSOCIATE_YES:
        MOV AL, 1
        JMP STORE_ASSOCIATION
        
    ASSOCIATE_NO:
        MOV AL, 0
        
    STORE_ASSOCIATION:
        ; Calculate offset in EVENT_CANDIDATES
        PUSH AX
        MOV AX, BX
        MOV CL, MAX_CANDIDATES
        MUL CL
        ADD AX, SI
        MOV BX, AX
        POP AX
        
        ; Store association
        MOV EVENT_CANDIDATES[BX], AL
        
        ; New line
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        
        INC SI
        POP CX
        LOOP ASSOCIATE_NEXT
        
    POP DX
    POP CX
    POP BX
    POP AX
    RET
ASSOCIATE_CANDIDATES ENDP

DISPLAY_EVENT_CANDIDATES PROC
    PUSH AX
    PUSH BX
    PUSH CX
    PUSH DX
    PUSH SI
    
    ; BL contains event index
    XOR BH, BH
    MOV AX, BX
    MOV CL, MAX_CANDIDATES
    MUL CL
    MOV BX, AX      ; BX = event offset
    
    MOV CX, CANDIDATE_COUNT
    XOR SI, SI      ; SI = candidate offset
    PUSH BX         ; Save event offset
    MOV BX, 1       ; BX = display counter
    
    DISPLAY_NEXT_EVENT_CANDIDATE:
        PUSH CX
        
        ; Check if candidate is associated
        POP DX      ; Get event offset
        MOV AL, EVENT_CANDIDATES[BX+SI]
        PUSH DX     ; Save event offset back
        
        CMP AL, 1
        JNE SKIP_CANDIDATE
        
        ; Display candidate number
        MOV AH, 2
        MOV DL, BL
        ADD DL, '0'
        INT 21H
        MOV DL, '.'
        INT 21H
        MOV DL, ' '
        INT 21H
        
        ; Display candidate name
        PUSH BX
        MOV AX, SI
        MOV CL, 20
        MUL CL
        MOV BX, AX
        LEA DX, CANDIDATE_NAMES[BX]
        MOV AH, 9
        INT 21H
        POP BX
        
        ; New line
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        
        INC BX
        
    SKIP_CANDIDATE:
        INC SI
        POP CX
        LOOP DISPLAY_NEXT_EVENT_CANDIDATE
        
    POP BX          ; Restore event offset
    POP SI
    POP DX
    POP CX
    POP BX
    POP AX
    RET
DISPLAY_EVENT_CANDIDATES ENDP

RECORD_VOTE PROC
    PUSH BX
    
    ; AX = event index, BL = candidate index
    MOV CL, MAX_CANDIDATES
    MUL CL
    ADD AL, BL
    MOV BX, AX
    
    ; Increment vote count
    INC VOTES[BX]
    
    ; Display success message
    LEA DX, SUCCESS_MSG
    MOV AH, 9
    INT 21H
    
    POP BX
    RET
RECORD_VOTE ENDP

END MAIN
