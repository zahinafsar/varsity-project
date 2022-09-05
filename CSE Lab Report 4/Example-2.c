#include <stdio.h>
#include <math.h>

int main()
{
   printf("\n");
   int num, sw_num, first_digit, last_digit, full_digits;
   printf("Enter any number: ");
   scanf("%d", &num);
   last_digit = num % 10;
   full_digits = (int)log10(num);
   first_digit = (int)(num / pow(10, full_digits));
   sw_num = last_digit;
   sw_num *= (int)round(pow(10, full_digits));
   sw_num += num % ((int)round(pow(10, full_digits)));
   sw_num -= last_digit;
   sw_num += first_digit;
   printf("Swapped number is: %d\n", sw_num);
   printf("\n");
   return 0;
}
