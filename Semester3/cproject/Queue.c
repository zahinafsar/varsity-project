#include <stdio.h>
#include <stdlib.h>

struct node
{
    int data;
    struct node *next;
};
struct node *f = NULL;
struct node *r = NULL;

void enqueue()
{
    int option, n, data;
    printf("\nEnter the number of data: ");
    scanf("%d", &n);
    printf("\nEnter your data: ");
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &data);
        struct node *queue;
        queue = (struct node *)malloc(sizeof(struct node));
        queue->data = data;
        queue->next = NULL;

        if ((r == NULL) && (f == NULL))
        {
            f = r = queue;
            r->next = f;
        }
        else
        {
            r->next = queue;
            r = queue;
            queue->next = f;
        }
    }
}
void dequeue()
{
    struct node *temp;
    temp = f;
    if ((f == NULL) && (r == NULL))
    {
        printf("\nQueue is Empty");
    }
    else if (f == r)
    {
        f = r = NULL;
        free(temp);
    }
    else
    {
        f = f->next;
        r->next = f;
        free(temp);
    }
}
void print()
{
    struct node *temp;
    temp = f;
    if ((f == NULL) && (r == NULL))
        printf("\nQueue is Empty");
    else
    {   printf("Queue: ");
        do
        {
            printf(" %d", temp->data);
            temp = temp->next;
        } while (temp != f);
    }
}

int main()
{
    int option;
    do
    {
        printf("\n\n-----------------------------\n");
        printf("Enter Your Choice:\n\n");
        printf("(1) Insert the Data in Queue\n");
        printf("(2) Delete the data from the Queue\n");
        printf("(3) Display the Data in Queue\n");
        printf("(4) Exit\n");
        printf("> ");
        scanf("%d", &option);
        switch (option)
        {
        case 1:
            enqueue();
            break;
        case 2:
            dequeue();
            break;
        case 3:
            print();
            break;
        case 0:
            break;
        default:
            printf("\nInvailed Choice");
        }
    } while (option != 0);
    return 0;
}
