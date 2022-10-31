#include <stdio.h>

int main()
{
    printf("\n\n");
    int num, *ptr, i, j, bin[100];
    printf("Enter a decimal number: ");
    scanf("%d", &num);
    ptr = &num;
    i = 0;
    while (*ptr > 0)
    {
        bin[i] = *ptr % 2;
        *ptr = *ptr / 2;
        i++;
    }
    printf("\nThe binary equivalent of the given decimal number is: ");
    for (j = i - 1; j >= 0; j--)
    {
        printf("%d", bin[j]);
    }
    printf("\n\n");

    return 0;
}