find_second_third_highest() {
    sorted_array=($(printf "%s\n" "${numbers[@]}" | sort -n -r))

    second_highest=${sorted_array[1]}
    third_highest=${sorted_array[2]}

    sum=$((second_highest + third_highest))

    echo "Second highest number: $second_highest"
    echo "Third highest number: $third_highest"
    echo "Sum of second highest and third highest numbers: $sum"
}

read -p "Enter numbers separated by spaces: " -a numbers

find_second_third_highest
