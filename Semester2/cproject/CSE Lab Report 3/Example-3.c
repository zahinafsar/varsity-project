#include <stdio.h>

int main()
{
    char input;

    printf("\nEnter an alphabet: ");
    scanf("%c", &input);

    switch (input)
    {
    case 'a':
    case 'e':
    case 'i':
    case 'o':
    case 'u':
        printf("%c is a vowel\n", input);
        break;

    default:
        if (input >= 'a' && input <= 'z')
        {

            printf("%c is a consonant\n", input);
        }
        else
        {

            printf("%c is not an alphabet\n", input);
        }
    }
    printf("\n");
    return 0;
}
