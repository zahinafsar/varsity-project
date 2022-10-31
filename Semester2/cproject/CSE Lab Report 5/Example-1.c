#include <stdio.h>

int main()
{
  printf("\n\n");
  int n, flag = 0;
  printf("Enter a number: ");
  scanf("%d", &n);
  if (n == 0 || n == 1) {
    flag = 1;
  }
  for (int i = 2; i <= n / 2; ++i) {
    if (n % i == 0) {
      flag = 1;
      break;
    }
  }
  if (flag == 0) {
    printf("%d is prime number.\n", n);
  } else {
    printf("%d is not prime number.\n", n);
  }
  printf("\n\n");
  return 0;
}