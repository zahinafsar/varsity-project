#include <stdio.h>
#include <math.h>

int main()
{
  printf("\n\n");
  int n;
  float mean = 0, median = 0, deviation = 0;
  printf("Enter the array length: ");
  scanf("%d", &n);
  int arr[n];
  printf("Input %d elements of the array: ", n);
  for (int i = 0; i < n; i++) {
    scanf("%d", &arr[i]);
  }
  for (int i = 0; i < n; i++) {
    mean += arr[i];
  }
  mean /= n;
  if (n % 2 == 0) {
    median = (arr[n / 2] + arr[n / 2 - 1]) / 2.0;
  } else {
    median = arr[n / 2];
  }
  for (int i = 0; i < n; i++) {
    deviation += (arr[i] - mean) * (arr[i] - mean);
  }
  deviation /= n;
  deviation = sqrt(deviation);
  printf("Mean: %f\n", mean);
  printf("Median: %f\n", median);
  printf("Standard Deviation: %f\n", deviation);
  printf("\n\n");
  return 0;
}