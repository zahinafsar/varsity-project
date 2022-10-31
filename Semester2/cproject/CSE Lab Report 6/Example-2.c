#include <stdio.h>

int main()
{
    printf("\n\n");
    int a[10], n, i;
    printf("Enter a number: ");
    scanf("%d", &n);
    for (i = 0; n > 0; i++)
    {
        a[i] = n % 2;
        n = n / 2;
    }
    printf("Binary of the Given Number : ");
    for (i = i - 1; i >= 0; i--)
    {
        printf("%d", a[i]);
    }
    printf("\n\n");
    return 0;
}