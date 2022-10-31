#include <stdio.h>
#include <math.h>

void add(int a, int b) {
  int c = a + b;
  printf("%d + %d = %d\n", a, b, c);
}

void subtract(int a, int b) {
  int c = a - b;
  printf("%d - %d = %d\n", a, b, c);
}

void multiply(int a, int b) {
  int c = a * b;
  printf("%d * %d = %d\n", a, b, c);
}

void divide(int a, int b) {
  int c = a / b;
  printf("%d / %d = %d\n", a, b, c);
}

void menu() {
  printf("\n1. Add\n2. Subtract\n3. Multiply\n4. Divide\n5. Exit\n\n");
}

int main() {
  printf("\n\n");
  int a, b;
  printf("Enter two number: ");
  scanf("%d %d", &a, &b);
  menu();
  int choice;
  printf("Enter your choice: ");
  scanf("%d", &choice);
  switch (choice) {
  case 1:
    add(a, b);
    break;
  case 2:
    subtract(a, b);
    break;
  case 3:
    multiply(a, b);
    break;
  case 4:
    divide(a, b);
    break;
  case 5:
    printf("\n\nExiting...\n\n");
    break;
  default:
    printf("\n\nInvalid choice.\n\n");
    break;
  }
  printf("\n\n");
  return 0;
}
