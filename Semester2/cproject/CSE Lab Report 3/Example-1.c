#include <stdio.h>

int main()
{
    int input;
    printf("\n");
    printf("Enter a number: ");
    scanf("%d", &input);

    if (input % 5 == 0 && input % 11 == 0)
        printf("%d is divisible by 5 and 11\n", input);
    else
        printf("%d is not divisible by 5 and 11\n", input);
    printf("\n");
    return 0;
}
