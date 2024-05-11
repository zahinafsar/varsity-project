factorial() {
    local num=$1
    local result=1
    for ((i=1; i<=$num; i++)); do
        result=$((result * i))
    done
    echo $result
}

calculate_sum() {
    local num1=$1
    local num2=$2
    local sum=$((num1 + num2))
    echo $sum
}

read -p "Enter the first number: " num1

fact1=$(factorial $num1)
echo "Factorial of $num1 is: $fact1"

read -p "Enter the second number: " num2

fact2=$(factorial $num2)
echo "Factorial of $num2 is: $fact2"

sum=$(calculate_sum $num1 $num2)
echo "Sum of $num1 and $num2 is: $sum"