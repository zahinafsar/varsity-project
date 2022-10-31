#include <stdio.h>
int main()
{

   float number1, number2, sum;

   printf("Enter the first number: ");
   scanf("%f", &number1);

   printf("Enter the second number: ");
   scanf("%f", &number2);

   sum = number1 + number2;

   printf("%f + %f = %f", number1, number2, sum);

   return 0;
}
