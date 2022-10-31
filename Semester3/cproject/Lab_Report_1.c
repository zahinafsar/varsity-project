#include <stdio.h>

int *insert(int *arr, int *size, int pos, int val)
{
    int i;
    *size = *size + 1;
    arr = (int *)realloc(arr, *size * sizeof(int));
    for (i = *size - 1; i > pos; i--)
    {
        arr[i] = arr[i - 1];
    }
    arr[pos] = val;
    return arr;
}

int *delete (int *arr, int *size, int pos)
{
    int i;
    for (i = pos; i < *size - 1; i++)
    {
        arr[i] = arr[i + 1];
    }
    *size = *size - 1;
    arr = (int *)realloc(arr, *size * sizeof(int));
    return arr;
}

int main()
{
    int *arr, size, i, pos, val;
    printf("Enter the size of the array: ");
    scanf("%d", &size);
    arr = (int *)malloc(size * sizeof(int));
    printf("Enter the elements of the array: ");
    for (i = 0; i < size; i++)
    {
        scanf("%d", &arr[i]);
    }
    printf("Enter the position and value to be inserted: ");
    scanf("%d %d", &pos, &val);
    arr = insert(arr, &size, pos, val);
    printf("The array after insertion is: ");
    for (i = 0; i < size; i++)
    {
        printf("%d ", arr[i]);
    }
    printf("\nEnter the position to be deleted: ");
    scanf("%d", &pos);
    arr = delete (arr, &size, pos);
    printf("The array after deletion is: ");
    for (i = 0; i < size; i++)
    {
        printf("%d ", arr[i]);
    }
    return 0;
}