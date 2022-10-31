#include <stdio.h>

int main()
{
    printf("\n\n");
    int arr[100], i, n, *ptr, max, min;
    printf("Enter the number of array elements : ");
    scanf("%d", &n);
    printf("Enter the elements of the array: ");
    for (i = 0; i < n; i++)
    {
        scanf("%d", &arr[i]);
    }
    ptr = arr;
    max = *ptr;
    min = *ptr;
    for (i = 0; i < n; i++)
    {
        if (*ptr > max)
        {
            max = *ptr;
        }
        if (*ptr < min)
        {
            min = *ptr;
        }
        ptr++;
    }
    printf("\nThe maximum element in the array is %d", max);
    printf("\nThe minimum element in the array is %d\n", min);
    printf("\n\n");
    return 0;
}