#include <stdio.h>
 
int main()
{
  float celsius, fahrenheit;
  printf("Input a Celsius value: ");
  scanf("%f", &celsius);
  fahrenheit = (celsius * 9 / 5) + 32;
  printf("Fahrenheit: %.2f", fahrenheit);
  return 0;
}
