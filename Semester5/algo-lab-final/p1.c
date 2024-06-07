#include <stdio.h>

char vertex1[20], vertex2[20];
int checked[128];
int que[20];
int first = 0, last = 0;

void enq(int n)
{
    if (!checked[n])
    {
        checked[n] = 1;
        que[last++] = n;
    }
}

int dq()
{
    return que[first++];
}

int main()
{
    int i, n;
    printf("Enter number of edges: ");
    scanf("%d", &n);

    printf("Enter the connected vertices:\n");
    for (i = 0; i < n; i++)
    {
        scanf(" %c", &vertex1[i]);
        scanf(" %c", &vertex2[i]);
    }

    for (i = 0; i < 128; i++)
    {
        checked[i] = 0;
    }

    enq(vertex1[0]);

    while (first < last)
    {
        int current = dq();
        printf("%c ", current);

        for (i = 0; i < n; i++)
        {
            if (vertex1[i] == current && !checked[vertex2[i]])
            {
                enq(vertex2[i]);
            }
            else if (vertex2[i] == current && !checked[vertex1[i]])
            {
                enq(vertex1[i]);
            }
        }
    }

    return 0;
}

// A B
// B C
// C E
// E F
// B E
// B D
// D E
// G D