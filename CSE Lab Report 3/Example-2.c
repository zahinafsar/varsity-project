#include <stdio.h>
 
int main()
{
   int a, b, c, r;
 
   printf("\nEnter three numbers: ");
   scanf("%d", &a);
   scanf("%d", &b);
   scanf("%d", &c);
 
   if (a > b && a > c) r = a;
   else if (b > a && b > c) r = b;
   else r = c;
 
   printf("%d is the max number\n\n", r);
   return 0;
}
