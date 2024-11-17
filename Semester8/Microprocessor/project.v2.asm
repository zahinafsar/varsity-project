include 'emu8086.inc'

.stack 100h
.model small
.data
    ; Messages
    menu_msg db 13,10,'===== Voting System Menu =====',13,10
             db '1. Create Candidate',13,10
             db '2. Create User',13,10 
             db '3. Create Event',13,10
             db '4. Vote',13,10
             db '5. Results',13,10
             db '6. List Candidates',13,10
             db '7. List Users',13,10
             db '8. Exit',13,10
             db 'Enter choice (1-8): $'
             
    ; Storage arrays            
    candidates db 10 dup('$')  ; Store up to 10 candidates
    users db 10 dup('$')       ; Store up to 10 users
    events db 5 dup('$')       ; Store up to 5 events
    votes db 50 dup(0)         ; Store votes (candidate ID + event ID)
    
    ; Counters
    cand_count db 0
    user_count db 0  
    event_count db 0
    vote_count db 0
    
    ; Input buffers
    name_buffer db 30,?,30 dup('$')
    
    ; Messages for operations
    input_name db 13,10,'Enter Name:',13,10,'$'
    success_msg db 13,10,'Operation successful!',13,10,'$'
    error_msg db 13,10,'Error in operation!',13,10,'$'
    max_reached db 13,10,'Maximum limit reached!',13,10,'$'
    list_header db 13,10,'List of Names:',13,10,'$'
    newline db 13,10,'$'

.code

show_success proc
    ; Print empty line before
    mov ah, 9
    lea dx, newline
    int 21h
    
    ; Show success message
    lea dx, success_msg
    int 21h
    
    ; Print empty line after
    lea dx, newline
    int 21h
    
    ; Wait for keypress
    mov ah, 1
    int 21h
    ret
show_success endp

main proc
    mov ax, @data
    mov ds, ax
    

    menu:
        ; Clear screen
        mov ax, 3
        int 10h
        
        ; Print empty line
        mov dl, 10  ; Line feed
        mov ah, 02h
        int 21h
        
        ; Display menu
        mov ah, 9
        lea dx, menu_msg
        int 21h
        
        ; Get choice
        mov ah, 1
        int 21h
        
        ; Process choice
        cmp al, '1'
        je create_candidate
        cmp al, '2'
        je create_user
        cmp al, '3'
        je create_event
        cmp al, '4'
        je vote
        cmp al, '5'
        je show_result
        cmp al, '6'
        je list_candidates
        cmp al, '7'
        je list_users
        cmp al, '8'
        je exit_prog
        jmp menu
        
    create_candidate:
        ; Check if maximum candidates reached
        mov al, cand_count
        cmp al, 10
        jge max_candidates_reached
        
        ; Display input prompt
        mov ah, 9
        lea dx, input_name
        int 21h
        
        ; Get candidate name
        mov ah, 0Ah
        lea dx, name_buffer
        int 21h
        
        ; Calculate position in candidates array
        mov al, cand_count
        mov bl, 30  ; Each name takes 30 bytes
        mul bl
        lea si, candidates
        add si, ax
        
        ; Copy name from buffer to candidates array
        lea di, name_buffer + 2
        mov cl, [name_buffer + 1]  ; Get length of input
        mov ch, 0
        rep movsb
        
        ; Increment candidate count
        inc cand_count
        
        call show_success
        jmp menu
        
    max_candidates_reached:
        mov ah, 9
        lea dx, max_reached
        int 21h
        mov ah, 1
        int 21h
        jmp menu
        
    create_user:
        ; Check if maximum users reached
        mov al, user_count
        cmp al, 10
        jge max_users_reached
        
        ; Display input prompt
        mov ah, 9
        lea dx, input_name
        int 21h
        
        ; Get user name
        mov ah, 0Ah
        lea dx, name_buffer
        int 21h
        
        ; Calculate position in users array
        mov al, user_count
        mov bl, 30  ; Each name takes 30 bytes
        mul bl
        lea si, users
        add si, ax
        
        ; Copy name from buffer to users array
        lea di, name_buffer + 2
        mov cl, [name_buffer + 1]  ; Get length of input
        mov ch, 0
        rep movsb
        
        ; Increment user count
        inc user_count
        
        call show_success
        jmp menu
        
    max_users_reached:
        mov ah, 9
        lea dx, max_reached
        int 21h
        mov ah, 1
        int 21h
        jmp menu
        
    create_event:
        ; Add event logic here
        jmp menu
        
    vote:
        ; Add voting logic here
        jmp menu
        
    show_result:
        ; Add result display logic here
        jmp menu

    list_candidates:
        ; Display header
        mov ah, 9
        lea dx, list_header
        int 21h

        ; Initialize counter
        xor cx, cx
        mov cl, cand_count
        xor bx, bx

        list_candidates_loop:
            cmp cl, 0
            je list_candidates_done
            
            ; Calculate position and display name
            mov al, bl
            mov dl, 30
            mul dl
            lea si, candidates
            add si, ax
            mov dx, si
            mov ah, 9
            int 21h
            
            ; Print newline
            lea dx, newline
            int 21h
            
            inc bl
            dec cl
            jmp list_candidates_loop

        list_candidates_done:
            ; Wait for keypress
            mov ah, 1
            int 21h
            jmp menu

    list_users:
        ; Display header
        mov ah, 9
        lea dx, list_header
        int 21h

        ; Initialize counter
        xor cx, cx
        mov cl, user_count
        xor bx, bx

        list_users_loop:
            cmp cl, 0
            je list_users_done
            
            ; Calculate position and display name
            mov al, bl
            mov dl, 30
            mul dl
            lea si, users
            add si, ax
            mov dx, si
            mov ah, 9
            int 21h
            
            ; Print newline
            lea dx, newline
            int 21h
            
            inc bl
            dec cl
            jmp list_users_loop

        list_users_done:
            ; Wait for keypress
            mov ah, 1
            int 21h
            jmp menu
        
    exit_prog:
        mov ah, 4ch
        int 21h        
main endp
end main