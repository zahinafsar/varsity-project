display_odd_positions() {
    local i=1
    for num in $@; do
        if [ $((i % 2)) -eq 1 ]; then
            echo "Number at position $i: $num"
        fi
        ((i++))
    done
}

read -p "Enter numbers separated by spaces: " numbers

display_odd_positions $numbers
