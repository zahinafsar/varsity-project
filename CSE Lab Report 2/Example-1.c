#include <stdio.h>
 
int main()
{
  float length, area;
  printf("Input a number: ");
  scanf("%f", &length);
  area = length * length;
  printf("Area: %.2f", area);
  return 0;
}
