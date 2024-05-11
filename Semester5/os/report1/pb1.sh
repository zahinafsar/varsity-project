is_even() {
    if (( $1 % 2 == 0 )); then
        return 0
    else
        return 1
    fi
}

sum_odd_even() {
    odd_sum=0
    even_sum=0

    for num in $@; do
        if is_even $num; then
            even_sum=$((even_sum + num))
        else
            odd_sum=$((odd_sum + num))
        fi
    done

    echo "Sum of odd numbers: $odd_sum"
    echo "Sum of even numbers: $even_sum"
}

read -p "Enter numbers separated by spaces: " numbers

sum_odd_even $numbers