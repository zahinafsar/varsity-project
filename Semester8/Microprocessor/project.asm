include 'emu8086.inc'

.stack 100h
.model small
.data
    ; Messages
    welcome_msg db 'Welcome to Voting System$'
    menu_msg db 10,13,'1. Create Candidate',10,13,'2. Create User',10,13,'3. Create Event',10,13,'4. Vote',10,13,'5. View Results',10,13,'6. Exit$'
    input_msg db 10,13,'Enter your choice: $'
    candidate_prompt db 10,13,'Enter candidate name: $'
    candidate_added db 10,13,'Candidate added successfully!$'
    candidate_full db 10,13,'Maximum candidates reached!$'
    user_prompt db 10,13,'Enter user name: $'
    user_added db 10,13,'User added successfully!$'
    user_full db 10,13,'Maximum users reached!$'
    user_exists db 10,13,'User already exists!$'
    event_prompt db 10,13,'Enter event name: $'
    event_added db 10,13,'Event added successfully!$'
    event_full db 10,13,'Maximum events reached!$'
    select_candidates db 10,13,'Available candidates:$'
    select_prompt db 10,13,'Select candidate number (0 to finish):$'
    invalid_candidate db 10,13,'Invalid candidate number!$'
    newline db 10,13,'$'
    candidate_number db '. $'
    vote_user_prompt db 10,13,'Enter your username: $'
    vote_event_prompt db 10,13,'Select event number: $'
    vote_candidate_prompt db 10,13,'Select candidate number to vote: $'
    invalid_user db 10,13,'User not found!$'
    invalid_event db 10,13,'Invalid event number!$'
    already_voted db 10,13,'You have already voted in this event!$'
    vote_success db 10,13,'Vote recorded successfully!$'
    results_header db 10,13,'Voting Results:$'
    event_label db 10,13,'Event: $'
    votes_label db ' votes$'
    
    ; Storage for candidates
    max_candidates equ 5
    candidate_names db max_candidates dup(20 dup('$'))
    candidate_count db 0
    
    ; Storage for events
    max_events equ 5 
    event_names db max_events dup(20 dup('$'))
    event_count db 0
    event_candidates db max_events dup(max_candidates dup(0)) ; Store candidate associations (1=selected, 0=not selected)
    
    ; Storage for users
    max_users equ 10
    user_names db max_users dup(20 dup('$'))
    user_count db 0
    
    ; Storage for votes
    votes db max_events dup(max_candidates dup(0)) ; Votes per candidate per event
    user_event_votes db max_events dup(max_users dup(0)) ; Track if user voted in event
    
    ; Temp variables
    temp_str db 20 dup('$')
    choice db ?
    temp_candidate db ?
    temp_event db ?
    temp_user db ?

.code

main proc
    mov ax, @data
    mov ds, ax
    
    main_loop:
        ; Print two line breaks
        lea dx, newline
        mov ah, 09h
        int 21h
        lea dx, newline 
        mov ah, 09h
        int 21h
        
        ; Display welcome message
        lea dx, welcome_msg
        mov ah, 09h
        int 21h
        
        ; Display menu
        lea dx, menu_msg
        mov ah, 09h
        int 21h
        
        ; Get user choice
        lea dx, input_msg
        mov ah, 09h
        int 21h
        
        mov ah, 01h
        int 21h
        sub al, '0'
        mov choice, al
        
        ; Process choice
        cmp choice, 1
        je create_candidate
        cmp choice, 2
        je create_user
        cmp choice, 3
        je create_event
        cmp choice, 4
        je vote
        cmp choice, 5
        je view_results
        cmp choice, 6
        je exit_program
        
        jmp main_loop
        
    create_candidate:
        ; Check if maximum candidates reached
        mov al, candidate_count
        cmp al, max_candidates
        jge candidate_full_msg
        
        ; Display prompt
        lea dx, candidate_prompt
        mov ah, 09h
        int 21h
        
        ; Get candidate name
        mov cx, 0  ; Character counter
        lea si, temp_str
        
        read_name:
            mov ah, 01h
            int 21h
            
            cmp al, 13  ; Check for Enter key
            je end_read_name
            
            mov [si], al
            inc si
            inc cx
            cmp cx, 19  ; Max 19 chars (leave room for $)
            jl read_name
            
        end_read_name:
            mov byte ptr [si], '$'  ; Add string terminator
            
        ; Copy name to candidate array
        mov al, candidate_count
        mov bl, 20
        mul bl  ; AL = candidate_count * 20
        lea si, candidate_names
        add si, ax  ; SI points to next free candidate slot
        
        lea di, temp_str
        mov cx, 20
        rep movsb  ; Copy the name
        
        ; Increment candidate count
        inc candidate_count
        
        ; Display success message
        lea dx, candidate_added
        mov ah, 09h
        int 21h
        jmp main_loop
        
    candidate_full_msg:
        lea dx, candidate_full
        mov ah, 09h
        int 21h
        jmp main_loop
        
    create_user:
        ; Check if maximum users reached
        mov al, user_count
        cmp al, max_users
        jge user_full_msg
        
        ; Display prompt
        lea dx, user_prompt
        mov ah, 09h
        int 21h
        
        ; Get user name
        mov cx, 0  ; Character counter
        lea si, temp_str
        
        read_user_name:
            mov ah, 01h
            int 21h
            
            cmp al, 13  ; Check for Enter key
            je end_read_user_name
            
            mov [si], al
            inc si
            inc cx
            cmp cx, 19  ; Max 19 chars (leave room for $)
            jl read_user_name
            
        end_read_user_name:
            mov byte ptr [si], '$'  ; Add string terminator
            
        ; Copy name to user array
        mov al, user_count
        mov bl, 20
        mul bl  ; AL = user_count * 20
        lea si, user_names
        add si, ax  ; SI points to next free user slot
        
        lea di, temp_str
        mov cx, 20
        rep movsb  ; Copy the name
        
        ; Increment user count
        inc user_count
        
        ; Display success message
        lea dx, user_added
        mov ah, 09h
        int 21h
        jmp main_loop
    
    user_full_msg:
        lea dx, user_full
        mov ah, 09h
        int 21h
        jmp main_loop
        
    create_event:
        ; Check if maximum events reached
        mov al, event_count
        cmp al, max_events
        jge event_full_msg
        
        ; Display prompt
        lea dx, event_prompt
        mov ah, 09h
        int 21h
        
        ; Get event name
        mov cx, 0
        lea si, temp_str
        
        read_event_name:
            mov ah, 01h
            int 21h
            
            cmp al, 13
            je end_read_event_name
            
            mov [si], al
            inc si
            inc cx
            cmp cx, 19
            jl read_event_name
            
        end_read_event_name:
            mov byte ptr [si], '$'
            
        ; Copy name to event array
        mov al, event_count
        mov bl, 20
        mul bl
        lea si, event_names
        add si, ax
        
        lea di, temp_str
        mov cx, 20
        rep movsb
        
        ; Display available candidates
        lea dx, select_candidates
        mov ah, 09h
        int 21h
        
        xor cx, cx  ; Counter for candidates
        display_candidates:
            cmp cl, candidate_count
            jge select_candidates_loop
            
            ; Display newline
            lea dx, newline
            mov ah, 09h
            int 21h
            
            ; Display candidate number
            mov al, cl
            inc al  ; Convert to 1-based
            add al, '0'
            mov dl, al
            mov ah, 02h
            int 21h
            
            ; Display dot and space
            lea dx, candidate_number
            mov ah, 09h
            int 21h
            
            ; Calculate offset to candidate name
            mov al, cl
            mov bl, 20
            mul bl
            lea si, candidate_names
            add si, ax
            
            ; Display candidate name
            mov dx, si
            mov ah, 09h
            int 21h
            
            inc cx
            jmp display_candidates
            
        select_candidates_loop:
            ; Display newline
            lea dx, newline
            mov ah, 09h
            int 21h
            
            ; Display selection prompt
            lea dx, select_prompt
            mov ah, 09h
            int 21h
            
            ; Get candidate number
            mov ah, 01h
            int 21h
            sub al, '0'
            mov temp_candidate, al
            
            ; Check if finished selecting
            cmp temp_candidate, 0
            je end_select_candidates
            
            ; Validate candidate number
            cmp temp_candidate, max_candidates
            jg invalid_candidate_msg
            
            ; Mark candidate as selected for this event
            mov al, event_count
            mov bl, max_candidates
            mul bl
            mov si, ax
            add si, offset event_candidates
            mov al, temp_candidate
            dec al  ; Convert to 0-based index
            add si, ax
            mov byte ptr [si], 1
            
            jmp select_candidates_loop
            
        invalid_candidate_msg:
            lea dx, invalid_candidate
            mov ah, 09h
            int 21h
            jmp select_candidates_loop
            
        end_select_candidates:
        
        ; Increment event count
        inc event_count
        
        ; Display success message
        lea dx, event_added
        mov ah, 09h
        int 21h
        jmp main_loop
        
    event_full_msg:
        lea dx, event_full
        mov ah, 09h
        int 21h
        jmp main_loop
        
    vote:
        ; Get username
        lea dx, vote_user_prompt
        mov ah, 09h
        int 21h
        
        ; Read username
        mov cx, 0
        lea si, temp_str
        read_vote_user:
            mov ah, 01h
            int 21h
            cmp al, 13
            je end_read_vote_user
            mov [si], al
            inc si
            inc cx
            cmp cx, 19
            jl read_vote_user
        end_read_vote_user:
            mov byte ptr [si], '$'
            
        ; Find user
        xor bx, bx
        find_user:
            cmp bl, user_count
            je user_not_found
            
            push bx
            mov al, bl
            mov bl, 20
            mul bl
            lea si, user_names
            add si, ax
            lea di, temp_str
            mov cx, 20
            repe cmpsb
            pop bx
            je user_found
            
            inc bl
            jmp find_user
            
        user_not_found:
            lea dx, invalid_user
            mov ah, 09h
            int 21h
            jmp main_loop
            
        user_found:
            mov temp_user, bl
            
            ; Display events
            xor cx, cx
            display_events:
                cmp cl, event_count
                jge get_event_choice
                
                lea dx, newline
                mov ah, 09h
                int 21h
                
                mov al, cl
                inc al
                add al, '0'
                mov dl, al
                mov ah, 02h
                int 21h
                
                lea dx, candidate_number
                mov ah, 09h
                int 21h
                
                mov al, cl
                mov bl, 20
                mul bl
                lea si, event_names
                add si, ax
                mov dx, si
                mov ah, 09h
                int 21h
                
                inc cx
                jmp display_events
                
            get_event_choice:
                lea dx, vote_event_prompt
                mov ah, 09h
                int 21h
                
                mov ah, 01h
                int 21h
                sub al, '0'
                dec al
                mov temp_event, al
                
                cmp al, event_count
                jge invalid_event_msg
                
                ; Check if already voted in this event
                mov al, temp_event
                mov bl, max_users
                mul bl
                add al, temp_user
                lea si, user_event_votes
                add si, ax
                cmp byte ptr [si], 1
                je already_voted_msg
                
                ; Display candidates for selected event
                lea dx, select_candidates
                mov ah, 09h
                int 21h
                
                xor cx, cx
                display_event_candidates:
                    cmp cl, candidate_count
                    jge get_vote_choice
                    
                    ; Check if candidate is in event
                    mov al, temp_event
                    mov bl, max_candidates
                    mul bl
                    add al, cl
                    lea si, event_candidates
                    add si, ax
                    cmp byte ptr [si], 1
                    jne skip_candidate
                    
                    lea dx, newline
                    mov ah, 09h
                    int 21h
                    
                    mov al, cl
                    inc al
                    add al, '0'
                    mov dl, al
                    mov ah, 02h
                    int 21h
                    
                    lea dx, candidate_number
                    mov ah, 09h
                    int 21h
                    
                    mov al, cl
                    mov bl, 20
                    mul bl
                    lea si, candidate_names
                    add si, ax
                    mov dx, si
                    mov ah, 09h
                    int 21h
                    
                    skip_candidate:
                    inc cx
                    jmp display_event_candidates
                    
                get_vote_choice:
                    lea dx, vote_candidate_prompt
                    mov ah, 09h
                    int 21h
                    
                    mov ah, 01h
                    int 21h
                    sub al, '0'
                    dec al
                    mov temp_candidate, al
                    
                    ; Record vote
                    mov al, temp_event
                    mov bl, max_candidates
                    mul bl
                    add al, temp_candidate
                    lea si, votes
                    add si, ax
                    inc byte ptr [si]
                    
                    ; Mark user as voted in this event
                    mov al, temp_event
                    mov bl, max_users
                    mul bl
                    add al, temp_user
                    lea si, user_event_votes
                    add si, ax
                    mov byte ptr [si], 1
                    
                    lea dx, vote_success
                    mov ah, 09h
                    int 21h
                    jmp main_loop
                    
            invalid_event_msg:
                lea dx, invalid_event
                mov ah, 09h
                int 21h
                jmp main_loop
                
            already_voted_msg:
                lea dx, already_voted
                mov ah, 09h
                int 21h
                jmp main_loop
        
    view_results:
        lea dx, results_header
        mov ah, 09h
        int 21h
        
        xor cx, cx  ; Event counter
        display_results:
            cmp cl, event_count
            jge main_loop
            
            lea dx, event_label
            mov ah, 09h
            int 21h
            
            mov al, cl
            mov bl, 20
            mul bl
            lea si, event_names
            add si, ax
            mov dx, si
            mov ah, 09h
            int 21h
            
            lea dx, newline
            mov ah, 09h
            int 21h
            
            xor bx, bx  ; Candidate counter
            display_candidate_votes:
                cmp bl, candidate_count
                jge next_event
                
                ; Check if candidate is in event
                mov al, cl
                mov dl, max_candidates
                mul dl
                add al, bl
                lea si, event_candidates
                add si, ax
                cmp byte ptr [si], 1
                jne skip_vote_display
                
                ; Display candidate name
                mov al, bl
                mov dl, 20
                mul dl
                lea si, candidate_names
                add si, ax
                mov dx, si
                mov ah, 09h
                int 21h
                
                ; Display votes
                mov al, cl
                mov dl, max_candidates
                mul dl
                add al, bl
                lea si, votes
                add si, ax
                mov al, [si]
                add al, '0'
                mov dl, al
                mov ah, 02h
                int 21h
                
                lea dx, votes_label
                mov ah, 09h
                int 21h
                
                lea dx, newline
                mov ah, 09h
                int 21h
                
                skip_vote_display:
                inc bl
                jmp display_candidate_votes
                
            next_event:
                inc cx
                jmp display_results
        
    exit_program:
        mov ah, 4ch
        int 21h
        
main endp
end main