check_triangle() {
    side1=$1
    side2=$2
    side3=$3

    if [ $((side1 + side2)) -gt $side3 ] && [ $((side2 + side3)) -gt $side1 ] && [ $((side1 + side3)) -gt $side2 ]; then
        echo "Triangle is valid."
    else
        echo "Triangle is not valid."
    fi
}

read -p "Enter the length of side 1: " side1
read -p "Enter the length of side 2: " side2
read -p "Enter the length of side 3: " side3

check_triangle $side1 $side2 $side3
