#include <stdio.h>
#include <stdlib.h>

void bubble_sort(int *arr, int size, int *sorted_indices);

int main()
{
    int numberOfCandidate;
    system("clear");
    printf("-------------------------------\n");
    printf("   ## NUMBER OF CANDIDATES ##\n");
    printf("-------------------------------\n");
    printf("> ");
    scanf("%d", &numberOfCandidate);
    char candidates[100][255];
    int voteCount[100];
    int voteFor = 0;
    int maxVoteFor = 0;
    system("clear");
    printf("-------------------------------\n");
    printf("   ## REGISTER %d CANDIDATE ##\n", numberOfCandidate);
    printf("-------------------------------");
    for (int i = 0; i < numberOfCandidate; i++)
    {
        printf("\n%d > ", i + 1);
        scanf("%s", candidates[i]);
    }
    do
    {
        system("clear");
        printf("-------------------------------\n");
        printf("      ## START VOTING ##\n");
        printf("-------------------------------\n");
        for (int i = 0; i < numberOfCandidate; i++)
        {
            printf("(%d) %s\n", i + 1, candidates[i]);
        }
        printf("(0) End voting\n");
        printf("\n> ");
        scanf("%d", &voteFor);
        if (voteFor > numberOfCandidate || voteFor < 0)
        {
            printf("There is not candidate on this position.\n");
        }
        else
        {
            voteCount[voteFor - 1] += 1;
        }

    } while (voteFor != 0);
    system("clear");
    printf("-------------------------------\n");
    printf("         ## RESULT ##\n");
    printf("-------------------------------\n");
    int sortArray[numberOfCandidate];
    bubble_sort(voteCount, numberOfCandidate, sortArray);
    for (int i = 0; i < numberOfCandidate; i++)
    {
        int index = sortArray[i];
        if (voteCount[maxVoteFor] < voteCount[index])
        {
            maxVoteFor = index;
        }
        printf("\n%s got %d vote", candidates[index], voteCount[index]);
    }
    printf("\n\n");
    printf("-------------------------------\n");
    printf("Winner is %s\n", candidates[maxVoteFor]);
    printf("-------------------------------\n\n");
    return 0;
}

void bubble_sort(int *arr, int size, int *sorted_indices)
{
    for (int i = 0; i < size; i++)
    {
        sorted_indices[i] = i;
    }
    for (int i = 0; i < size - 1; i++)
    {
        for (int j = 0; j < size - i - 1; j++)
        {
            if (arr[sorted_indices[j]] < arr[sorted_indices[j + 1]])
            {
                int temp = sorted_indices[j];
                sorted_indices[j] = sorted_indices[j + 1];
                sorted_indices[j + 1] = temp;
            }
        }
    }
}
