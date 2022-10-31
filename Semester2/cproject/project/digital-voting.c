#include <stdio.h>

int main()
{
    int numberOfCandidate;
    printf("\n\n");
    printf("-------------------------------\n");
    printf("   ## NUMBER OF CANDIDATES ##\n");
    printf("-------------------------------\n");
    printf("> ");
    scanf("%d", &numberOfCandidate);
    char candidates[100][255];
    int voteCount[100];
    int voteFor = 0;
    int maxVoteFor = 0;
    printf("\n\n");
    printf("-------------------------------\n");
    printf("   ## REGISTER CANDIDATES ##\n");
    printf("-------------------------------\n");
    for (int i = 0; i < numberOfCandidate; i++)
    {
        printf("\n%d > ", i + 1);
        fgets(candidates[i]);
        // scanf("%s", candidates[i]);
    }
    printf("\n\n");
    printf("-------------------------------\n");
    printf("      ## START VOTING ##\n");
    printf("-------------------------------\n");
    printf("    [Type 0 to end voting]\n");
    do
    {
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
    printf("\n\n");
    printf("-------------------------------\n");
    printf("         ## RESULT ##\n");
    printf("-------------------------------\n");
    for (int i = 0; i < numberOfCandidate; i++)
    {
        if (voteCount[maxVoteFor] < voteCount[i])
        {
            maxVoteFor = i;
        }
        printf("\n%s got %d vote", candidates[i], voteCount[i]);
    }
    printf("\n\n");
    printf("-------------------------------\n");
    printf("Winner is %s\n", candidates[maxVoteFor]);
    printf("-------------------------------\n\n");
    return 0;
}
