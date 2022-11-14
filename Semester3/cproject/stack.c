#include <stdio.h>

#define MAX 10

void push(int arr[], int *top);
int pop(int arr[], int *top);
void printStack(int arr[], int top);
void printFirst(int arr[], int top);

int main()
{
    int arr[MAX], run = 1, top = -1, method, n;
    printf("\nEnter the size of the array: ");
    scanf("%d", &n);
    top = n-1;
    printf("Enter the elements of the array: ");
    for (int i = 0; i < n; i++) {
      scanf("%d", &arr[i]);
    }

    while(run){
    printf("\nChoose any of the following options:");
    printf("\n 0: Exit\n 1: Push\n 2: Pop\n 3: Print Stack\n 4: Print First Value");
    printf("\n-> ");
    scanf("%d", &method);

    switch(method){
        case 0:
            run = 0;
            break;
        case 1:
            push(arr, &top);
            break;
        case 2:
            pop(arr, &top);
            break;
        case 3:
            printStack(arr, top);
            break;
        case 4:
            printFirst(arr, top);
            break;
        default: printf("Please choose a correct option!");
        }
    }

    return 0;
}

void push(int arr[], int *top)
{
    if(*top == MAX-1)
        printf("\nStack overflow: can't add more elements\n");
    else{
    int x;
        printf("\nEnter element to be pushed into the stack: ");
        scanf("%d", &x);
        (*top)++;
        arr[*top] = x;
    }
}

int pop(int arr[], int *top)
{
    if(*top <= -1)
        printf("\nStack underflow: can't remove element\n");
    else{
        int item = arr[*top];
        (*top)--;
        printf("\nPopped the element %d from the stack\n", item);
        return item;
    }
    return -1;
}

void printStack(int arr[], int top)
{
    if(top == -1)
        printf("\nThe stack is empty!\n");
    else
    {   printf("\nElements: ");
        for(int i = 0; i <= top; i++)
            printf("%d ", arr[i]);
        printf("\n");
    }
}

void printFirst(int arr[], int top)
{
    if(top == -1)
        printf("\nThe stack is empty!\n");
    else
    {   printf("\nTopest Elements: ");
        printf("%d ", arr[top]);
    }
}
