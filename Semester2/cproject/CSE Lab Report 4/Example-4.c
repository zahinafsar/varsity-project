#include <stdio.h>

int main()
{
   printf("\n");
   int n, t = 1, sum = 0, i;
   printf("Enter the number: ");
   scanf("%d", &n);
   for (i = 1; i <= n; i++)
   {
      sum += t;
      t = (t * 10) + 1;
   }
   printf("Sum of series = %d\n", sum);
   printf("\n");
   return 0;
}
