#include <stdio.h>

int main()
{
    printf("\n\n");
    int a[100];
    int cnt[100];
    int n;
    printf("Enter the array length: ");
    scanf("%d", &n);
    printf("Input %d elements of the array: ", n);
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &a[i]);
        cnt[i] = -1;
    }
    for (int i = 0; i < n; i++)
    {
        int c = 1;
        for (int j = i + 1; j < n; j++)
        {
            if (a[i] == a[j])
            {
                c++;
                cnt[j] = 0;
            }
        }
        if (cnt[i] != 0)
        {
            cnt[i] = c;
        }
    }
    for (int i = 0; i < n; i++)
    {
        if (cnt[i] != 0)
        {
            printf("Frequency of %d is %d\n", a[i], cnt[i]);
        }
    }
    printf("\n\n");
    return 0;
}