#include <stdio.h>

int main()
{
    int a[11], i, j;
    a[1] = 0;
    for (i = 2; i <= 10; i++)
    {
        int prime = 1;
        for (j = 0; j*j <= i; i++)
        {
            if (i%j == 0)
            {
               prime = 0;
               break;
            }
            
        }
        if (prime)
        {   
            a[i] = a[i-1] + 1;
        }else{
            a[i] = a[i-1];
        }
    }
    for (i = 0; i < 10; i++)
    {
        printf("%d ", a[i]);
    }
    return 0;
}