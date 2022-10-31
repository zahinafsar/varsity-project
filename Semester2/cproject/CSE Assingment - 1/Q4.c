#include <stdio.h>

int main()
{
    int p, ch, b, m, co;
    printf("Enter the marks of Physics, Chemistry, Biology, Math, Computer:\n");
    scanf("%d%d%d%d%d", &p, &ch, &b, &m, &co);
    float GPA = (p + ch + b + m + co) / 5.0;
    if (GPA >= 90)
        printf("Grade A");
    else if (GPA >= 80)
        printf("Grade B");
    else if (GPA >= 70)
        printf("Grade C");
    else if (GPA >= 60)
        printf("Grade D");
    else if (GPA >= 40)
        printf("Grade E");
    else
        printf("Grade F");
    return 0;
}