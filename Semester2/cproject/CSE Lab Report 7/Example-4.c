#include <stdio.h>

int sumOfDigits(int num)
{
  if (num == 0) {
    return 0;
  } else {
    return ((num % 10) + sumOfDigits(num / 10));
  }
}

int main()
{
  printf("\n\n");
  int num, sum;
  printf("Enter a number: ");
  scanf("%d", &num);
  sum = sumOfDigits(num);
  printf("Sum of digits of %d = %d", num, sum);
  printf("\n\n");
  return 0;
}
