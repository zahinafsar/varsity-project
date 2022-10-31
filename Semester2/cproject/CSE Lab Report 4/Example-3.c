#include <stdio.h>

int main()
{
    printf("\n");
    int num, rem, prod = 1;
    printf("Enter a number: ");
    scanf("%d", &num);
    while (num != 0)
    {
        rem = num % 10;
        prod *= rem;
        num /= 10;
    }
    printf("Product of numbers: %d", prod);
    printf("\n\n");
    return 0;
}
