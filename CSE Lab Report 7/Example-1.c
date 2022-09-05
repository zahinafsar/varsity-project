#include <stdio.h>
#include <math.h>

void decimal_to_binary(int n)
{
  int i = 0, rem = 0, binary = 0;
  while (n != 0)
  {
    rem = n % 2;
    n = n / 2;
    binary = binary + rem * pow(10, i);
    i++;
  }
  printf("%d", binary);
}

int main()
{
  int n;
  printf("\n\nEnter a decimal number: ");
  scanf("%d", &n);
  decimal_to_binary(n);
  printf("\n\n");
  return 0;
}