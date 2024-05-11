count_characters() {
    local string="$1"
    local alphabets=0
    local digits=0
    local specials=0

    for (( i=0; i<${#string}; i++ )); do
        char="${string:$i:1}"
        if [[ $char =~ [[:alpha:]] ]]; then
            ((alphabets++))
        elif [[ $char =~ [[:digit:]] ]]; then
            ((digits++))
        else
            ((specials++))
        fi
    done

    echo "Total alphabets: $alphabets"
    echo "Total digits: $digits"
    echo "Total special characters: $specials"
}

read -p "Enter a string: " input_string

count_characters "$input_string"
