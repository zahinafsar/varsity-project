INCLUDE 'EMU8086.INC'

.MODEL SMALL
.STACK 100H

.DATA
    MAX_TASKS EQU 10                    ; Maximum number of tasks
    MAX_LENGTH EQU 50                   ; Maximum length of each task
    TASKS DB MAX_TASKS * MAX_LENGTH DUP('$')  ; Array to store tasks
    TASK_COUNT DB 0                     ; Counter for number of tasks
    
    ; Messages
    MENU_MSG DB 'TODO App Menu:',0DH,0AH
             DB '1. Add Task',0DH,0AH
             DB '2. View Tasks',0DH,0AH
             DB '3. Exit',0DH,0AH
             DB 'Enter choice: $'
    ADD_PROMPT DB 'Enter task: $'
    TASK_ADDED DB 'Task added successfully!$'
    NO_TASKS DB 'No tasks found!$'
    TASK_LIST DB 'Your Tasks:$'
    TASK_NUM DB '. $'
    NEWLINE DB 0DH,0AH,'$'

.CODE
MAIN PROC
    MOV AX, @DATA
    MOV DS, AX
    
    MENU:
        ; Display menu
        LEA DX, MENU_MSG
        MOV AH, 9
        INT 21H
        
        ; Get choice
        MOV AH, 1
        INT 21H
        
        CMP AL, '1'
        JE ADD_TASK
        CMP AL, '2'
        JE VIEW_TASKS
        CMP AL, '3'
        JE EXIT
        JMP MENU
        
    ADD_TASK:
        ; Check if task limit reached
        MOV AL, TASK_COUNT
        CMP AL, MAX_TASKS
        JGE MENU
        
        ; Display prompt
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        LEA DX, ADD_PROMPT
        INT 21H
        
        ; Calculate position for new task
        XOR AX, AX
        MOV AL, TASK_COUNT
        MOV BX, MAX_LENGTH
        MUL BX
        LEA SI, TASKS
        ADD SI, AX
        
        ; Get task input
        MOV CX, 0
    INPUT_LOOP:
        MOV AH, 1
        INT 21H
        CMP AL, 0DH    ; Check for Enter key
        JE END_INPUT
        MOV [SI], AL
        INC SI
        INC CX
        CMP CX, MAX_LENGTH-1
        JL INPUT_LOOP
    END_INPUT:
        MOV BYTE PTR [SI], '$'  ; Add string terminator
        INC TASK_COUNT
        
        ; Display success message
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        LEA DX, TASK_ADDED
        INT 21H
        LEA DX, NEWLINE
        INT 21H
        JMP MENU
        
    VIEW_TASKS:
        ; Check if there are any tasks
        CMP TASK_COUNT, 0
        JE NO_TASKS_FOUND
        
        ; Display task list header
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        LEA DX, TASK_LIST
        INT 21H
        LEA DX, NEWLINE
        INT 21H
        
        ; Display all tasks
        MOV CL, 1
        MOV CH, TASK_COUNT
        LEA SI, TASKS
    DISPLAY_LOOP:
        ; Display task number
        MOV DL, CL
        ADD DL, '0'
        MOV AH, 2
        INT 21H
        LEA DX, TASK_NUM
        MOV AH, 9
        INT 21H
        
        ; Display task
        MOV DX, SI
        INT 21H
        LEA DX, NEWLINE
        INT 21H
        
        ADD SI, MAX_LENGTH
        INC CL
        DEC CH
        JNZ DISPLAY_LOOP
        JMP MENU
        
    NO_TASKS_FOUND:
        LEA DX, NEWLINE
        MOV AH, 9
        INT 21H
        LEA DX, NO_TASKS
        INT 21H
        LEA DX, NEWLINE
        INT 21H
        JMP MENU
        
    EXIT:
        MOV AH, 4CH
        INT 21H
MAIN ENDP
END MAIN 