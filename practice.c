#include <stdio.h>
#include <stdbool.h>

#define N 1000
int top = -1;
int stack[N];

int push(){
    if(top == N-1) {
        printf("Overflow Stack\n");
    } else {
        int x;
        printf("Enter element to push: ");
        scanf("%d", &x);
        top+=1;
        stack[top] = x;
    }
    return 0;
}

int pop(){
    if(top == -1) {
        printf("Underflow Stack\n");
    } else {
      int x = stack[top];
      printf("Popping %d out of the stack\n", x);
      top-=1;
    }
    return 0;
}

int print(){
    if(top == -1) {
      printf("Stack is empty\n");
    } else {
      printf("Stack is: ");
      for(int i = 0; i <= top; i++){
        printf("%d ", stack[i]);
      }
    }
    return 0;
}

int main(){
    while(1){
      int choice;
      printf("\nChoose any of the following options:\n");
      printf(" 0: Exit            1: Push            2: Pop            3: print\n\n");
      scanf("%d", &choice);  
      switch(choice){
          case 0: exit(0);
          case 1: push(); break;
          case 2: pop(); break;
          case 3: print(); break;
          default: printf("Please choose a correct option!");
      }
    }
    return 0;
}
