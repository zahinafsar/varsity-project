// Write a C program to concatenate two strings using pointers

#include <stdio.h>

int main()
{
    printf("\n\n");
    char str1[100], str2[100], *ptr1, *ptr2;
    int i, j;
    printf("Enter the first string: ");
    scanf("%s", str1);
    printf("Enter the second string: ");
    scanf("%s", str2);
    ptr1 = str1;
    ptr2 = str2;
    i = 0;
    while (*ptr1 != '\0')
    {
        ptr1++;
        i++;
    }
    j = 0;
    while (*ptr2 != '\0')
    {
        *ptr1 = *ptr2;
        ptr1++;
        ptr2++;
        j++;
    }
    printf("\nThe concatenated string is: %s\n", str1);
printf("\n\n");
    return 0;
}