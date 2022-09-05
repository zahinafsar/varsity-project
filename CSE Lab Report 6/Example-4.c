#include <stdio.h>

int main()
{
    printf("\n\n");
    int r, c;
    printf("Enter rows and columns of matrix: ");
    scanf("%d %d", &r, &c);
    int a[r][c], t[c][r];
    printf("Enter values of the matrix: ");
    for (int i = 0; i < r; ++i)
    {
        for (int j = 0; j < c; ++j)
        {
            scanf("%d", &a[i][j]);
        }
    }
    for (int i = 0; i < r; ++i)
    {
        for (int j = 0; j < c; ++j)
        {
            t[j][i] = a[i][j];
        }
    }
    printf("Transpose: \n");
    for (int i = 0; i < c; ++i)
    {
        for (int j = 0; j < r; ++j)
        {
            printf("%d  ", t[i][j]);
        }
        printf("\n");
    }
    printf("\n\n");
    return 0;
}