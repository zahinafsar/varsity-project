#include <stdio.h>
int main()
{
  float sub1, sub2, sub3, sub4, sub5, total;
  printf("Input five number: ");
  scanf("%f %f %f %f %f",&sub1, &sub2, &sub3, &sub4, &sub5);
  total = sub1 + sub2 + sub3 + sub4 + sub5;
  printf("Average: %.2f \n", total / 5);
  printf("Total: %.2f \n", total);
  return 0;
}
