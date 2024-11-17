include 'emu8086.inc'

.model small
.stack 1000h
.data
    ; Main Messages
    subject db 10,13,'MICROPORCESSOR AND INTERFACING $'
    course db 10,13,'CSE2006 $'
    heading db 10,13,'VOTING MANAGEMENT SYSTEM: USER INTERFACE $'
    exitmsg db 10,13,'SYSTEM CLOSED! $'

    ; Menu Messages 
    mainmsg0 db 10,13,'0. Exit$'
    mainmsg1 db 10,13,'1. Create Candidate$'
    mainmsg2 db 10,13,'2. Show Candidates$'
    mainmsg3 db 10,13,'3. Create User$'
    mainmsg4 db 10,13,'4. Show Users$'
    mainmsg5 db 10,13,'5. Create Event$'
    mainmsg6 db 10,13,'6. Show Events$'
    mainmsg7 db 10,13,'7. Vote$'
    mainmsg8 db 10,13,'Press Enter to return to main menu $'

    ; Input/Operation Messages
    inputmsg db 'Choose an option >>  $'
    blank db 10,13,'>>  $'
    blank2 db 10,13,'    $'

    ; Candidate Messages
    candidate_prompt db 10,13,'Enter candidate name: $'
    candidate_added db 10,13,'Candidate added successfully!$'
    candidate_full db 10,13,'Maximum candidates reached!$'

    ; User Messages
    user_prompt db 10,13,'Enter user name: $'
    user_added db 10,13,'User added successfully!$'
    user_full db 10,13,'Maximum users reached!$'

    ; Event Messages
    event_prompt db 10,13,'Enter event name: $'
    event_added db 10,13,'Event added successfully!$'
    event_full db 10,13,'Maximum events reached!$'

    ; Vote Messages
    vote_prompt db 10,13,'Enter event number to vote: $'
    vote_candidate_prompt db 10,13,'Enter candidate number to vote for: $'
    vote_success db 10,13,'Vote recorded successfully!$'
    vote_invalid db 10,13,'Invalid selection!$'

    ; Storage Variables
    max_candidates equ 5
    candidate_names db max_candidates dup(20 dup('$'))
    candidate_count db 0
    
    max_users equ 10
    user_names db max_users dup(20 dup('$'))
    user_count db 0
    
    max_events equ 5
    event_names db max_events dup(20 dup('$'))
    event_count db 0
    
    votes db max_events dup(max_candidates dup(0))
    temp_str db 20 dup('$')
    inputCode db ?

.code

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                           U T I L I T Y                           ;
;                         F U N C T I O N S                         ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

macro printString str 
    lea dx, str
    mov ah, 09h
    int 21h 
endm 

displaySub proc near 
    printString subject
    printString blank2
    printString course
    printString blank2
    ret
displaySub endp

displayHeading proc near 
    printString heading
    printString blank2
    ret 
displayHeading endp

displayinputMenu proc near
    printString mainmsg0
    printString mainmsg1
    printString mainmsg2
    printString mainmsg3
    printString mainmsg4
    printString mainmsg5
    printString mainmsg6
    printString mainmsg7
    printString mainmsg8
    printString blank2 
    ret
displayinputMenu endp 

inputMenu proc near 
    printString inputmsg 
    printString blank
    mov ah, 1
    int 21h 
    mov inputCode, al 
    ret 
inputMenu endp 

clearScreen proc near
    printString blank2
    printString blank2
    ret    
clearScreen endp

; Enter to continue
proc etc
   etcin:
      mov ah,1
      int 21h
      cmp al,13
      je mainloop
      jmp etcin
   ret 
etc endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                      C A N D I D A T E                           ;
;                     F U N C T I O N S                            ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

create_candidate proc
    call clearScreen
    
    ; Check if max reached
    mov al, candidate_count
    cmp al, max_candidates
    jge candidate_full_msg
    
    ; Get candidate name
    printString candidate_prompt
    printString blank
    
    mov cx, 0
    mov al, candidate_count
    mov bl, 20
    mul bl
    lea si, candidate_names
    add si, ax
    
    read_name:
        mov ah, 01h
        int 21h
        cmp al, 13
        je end_read_name
        mov [si], al
        inc si
        inc cx
        cmp cx, 19
        jl read_name
        
    end_read_name:
        mov byte ptr [si], '$'
        inc candidate_count
        printString candidate_added
        printString mainmsg8
        call etc
        ret
        
    candidate_full_msg:
        printString candidate_full
        printString mainmsg8
        call etc
        ret
create_candidate endp

show_candidates proc
    call clearScreen
    
    xor cx, cx
    mov cl, candidate_count
    cmp cl, 0
    je no_candidates
    
    show_candidates_loop:
        push cx
        mov al, candidate_count
        sub al, cl
        inc al          ; Add 1 to start counting from 1 instead of 0
        add al, '0'     ; Convert to ASCII
        mov dl, al
        mov ah, 02h     ; Print number
        int 21h
        
        mov dl, '.'     ; Print dot
        mov ah, 02h
        int 21h
        
        mov dl, ' '     ; Print space
        mov ah, 02h
        int 21h
        
        mov al, candidate_count
        sub al, cl
        mov bl, 20
        mul bl
        lea si, candidate_names
        add si, ax
        mov dx, si
        mov ah, 09h     ; Print candidate name
        int 21h
        
        printString blank2
        pop cx
        loop show_candidates_loop
    
    printString mainmsg8
    call etc
    ret
    
    no_candidates:
        printString blank2
        printString mainmsg8
        call etc
        ret
show_candidates endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                          U S E R                                  ;
;                     F U N C T I O N S                            ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

create_user proc
    call clearScreen
    
    mov al, user_count
    cmp al, max_users
    jge user_full_msg
    
    printString user_prompt
    printString blank
    
    mov cx, 0
    mov al, user_count
    mov bl, 20
    mul bl
    lea si, user_names
    add si, ax
    
    read_user:
        mov ah, 01h
        int 21h
        cmp al, 13
        je end_read_user
        mov [si], al
        inc si
        inc cx
        cmp cx, 19
        jl read_user
        
    end_read_user:
        mov byte ptr [si], '$'
        inc user_count
        printString user_added
        printString mainmsg8
        call etc
        ret
        
    user_full_msg:
        printString user_full
        printString mainmsg8
        call etc
        ret
create_user endp

show_users proc
    call clearScreen
    
    xor cx, cx
    mov cl, user_count
    cmp cl, 0
    je no_users
    
    show_users_loop:
        push cx
        mov al, user_count
        sub al, cl
        inc al          ; Add 1 to start counting from 1 instead of 0
        add al, '0'     ; Convert to ASCII
        mov dl, al
        mov ah, 02h     ; Print number
        int 21h
        
        mov dl, '.'     ; Print dot
        mov ah, 02h
        int 21h
        
        mov dl, ' '     ; Print space
        mov ah, 02h
        int 21h
        
        mov al, user_count
        sub al, cl
        mov bl, 20
        mul bl
        lea si, user_names
        add si, ax
        mov dx, si
        mov ah, 09h     ; Print user name
        int 21h
        
        printString blank2
        pop cx
        loop show_users_loop
    
    printString mainmsg8
    call etc
    ret
    
    no_users:
        printString blank2
        printString mainmsg8
        call etc
        ret
show_users endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                          E V E N T                                ;
;                     F U N C T I O N S                            ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

create_event proc
    call clearScreen
    
    mov al, event_count
    cmp al, max_events
    jge event_full_msg
    
    printString event_prompt
    printString blank
    
    mov cx, 0
    mov al, event_count
    mov bl, 20
    mul bl
    lea si, event_names
    add si, ax
    
    read_event:
        mov ah, 01h
        int 21h
        cmp al, 13
        je end_read_event
        mov [si], al
        inc si
        inc cx
        cmp cx, 19
        jl read_event
        
    end_read_event:
        mov byte ptr [si], '$'
        inc event_count
        printString event_added
        printString mainmsg8
        call etc
        ret
        
    event_full_msg:
        printString event_full
        printString mainmsg8
        call etc
        ret
create_event endp

show_events proc
    call clearScreen
    
    xor cx, cx
    mov cl, event_count
    cmp cl, 0
    je no_events
    
    show_events_loop:
        push cx
        mov al, event_count
        sub al, cl
        inc al          ; Add 1 to start counting from 1 instead of 0
        add al, '0'     ; Convert to ASCII
        mov dl, al
        mov ah, 02h     ; Print number
        int 21h
        
        mov dl, '.'     ; Print dot
        mov ah, 02h
        int 21h
        
        mov dl, ' '     ; Print space
        mov ah, 02h
        int 21h
        
        mov al, event_count
        sub al, cl
        mov bl, 20
        mul bl
        lea si, event_names
        add si, ax
        mov dx, si
        mov ah, 09h     ; Print event name
        int 21h
        
        printString blank2
        pop cx
        loop show_events_loop
    
    printString mainmsg8
    call etc
    ret
    
    no_events:
        printString blank2
        printString mainmsg8
        call etc
        ret
show_events endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                          V O T E                                  ;
;                     F U N C T I O N S                            ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

vote proc
    call clearScreen
    
    ; Show events
    call show_events
    
    ; Get event selection
    printString vote_prompt
    printString blank
    mov ah, 01h
    int 21h
    sub al, '0'
    
    ; Validate event selection
    cmp al, event_count
    jge invalid_vote
    
    ; Show candidates
    call show_candidates
    
    ; Get candidate selection
    printString vote_candidate_prompt
    printString blank
    mov ah, 01h
    int 21h
    sub al, '0'
    
    ; Validate candidate selection
    cmp al, candidate_count
    jge invalid_vote
    
    ; Record vote
    printString vote_success
    printString mainmsg8
    call etc
    ret
    
    invalid_vote:
        printString vote_invalid
        printString mainmsg8
        call etc
        ret
vote endp

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; 
;                                                                   ;
;                     E N T R Y    P O I N T                        ;
;                                                                   ;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

Main proc 
    mov ax, @data 
    mov ds, ax 

    call clearScreen
    call displaySub
    printString blank2

    mainLoop:
        call clearScreen
        call displayHeading
        printString blank2
        call displayinputMenu
        printString blank2
        call inputMenu 

        cmp inputCode, '0' 
        je exit 

        cmp inputCode, '1'
        je create_candidate

        cmp inputCode, '2'
        je show_candidates

        cmp inputCode, '3'
        je create_user

        cmp inputCode, '4'
        je show_users

        cmp inputCode, '5'
        je create_event

        cmp inputCode, '6'
        je show_events

        cmp inputCode, '7'
        je vote

        jmp mainLoop

    exit:
        printString blank2 
        printString blank2 
        printString exitmsg
        printString blank2
        printString blank2

        mov ah,4ch
        int 21h
    main endp 
end main