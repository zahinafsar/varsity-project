#include <stdio.h>
#include <stdbool.h>

int findLRU(int time[], int n) {
    int i, minimum = time[0], pos = 0;

    for(i = 1; i < n; ++i) {
        if(time[i] < minimum) {
            minimum = time[i];
            pos = i;
        }
    }

    return pos;
}

int main() {
    int numberOfFrames, numberOfPages, frames[10], pages[30], counter = 0, time[10], flag1, flag2, i, j, pos, pageFaultCount = 0;

    printf("Enter number of frames: ");
    scanf("%d", &numberOfFrames);

    printf("Enter number of pages: ");
    scanf("%d", &numberOfPages);

    printf("Enter reference string: ");
    for(i = 0; i < numberOfPages; ++i) {
        scanf("%d", &pages[i]);
    }

    for(i = 0; i < numberOfFrames; ++i) {
        frames[i] = -1;
    }

    for(i = 0; i < numberOfPages; ++i) {
        flag1 = flag2 = 0;

        for(j = 0; j < numberOfFrames; ++j) {
            if(frames[j] == pages[i]) {
                counter++;
                time[j] = counter;
                flag1 = flag2 = 1;
                break;
            }
        }

        if(flag1 == 0) {
            for(j = 0; j < numberOfFrames; ++j) {
                if(frames[j] == -1) {
                    counter++;
                    pageFaultCount++;
                    frames[j] = pages[i];
                    time[j] = counter;
                    flag2 = 1;
                    break;
                }
            }
        }

        if(flag2 == 0) {
            pos = findLRU(time, numberOfFrames);
            counter++;
            pageFaultCount++;
            frames[pos] = pages[i];
            time[pos] = counter;
        }

        printf("\n");

        for(j = 0; j < numberOfFrames; ++j) {
            printf("%d\t", frames[j]);
        }
    }

    printf("\n\nTotal Page Faults = %d\n", pageFaultCount);

    return 0;
}
