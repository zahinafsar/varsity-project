#include <stdio.h>

void strong_numbers(int n) {
  for (int i = 1; i <= n; i++)
  {
    int num2 = i;
    int num1 = i;
    int sum = 0;
    int fact = 1;
    while (num1 != 0) {
      fact = 1;
      int rem = num1 % 10;
      num1 = num1 / 10;
      for (int j = 1; j <= rem; j++) {
        fact = fact * j;
      }
      sum = sum + fact;
    }
    if (sum == num2){
      printf("%d ", i);
    }
  }
}

int main() {
  int n;
  printf("\n\nEnter a number: ");
  scanf("%d", &n);
  strong_numbers(n);
  printf("\n\n");
  return 0;
}