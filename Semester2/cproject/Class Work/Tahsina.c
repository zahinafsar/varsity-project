#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct library
{
    char bk_name[50];
    char writter[50];
    int pages;
    float price;
};

int main()
{
    struct library l[100];
    char wr_nm[50], bk_nm[50];
    int i, j, keepcount;
    i = j = keepcount = 0;

    while (j != 8)
    {
        printf("\n\n1.Include book information\n2. Show book information\n");
        printf("3. List all books of given writter\n");
        printf("4. List the title of specified book\n");
        printf("5. List the count of books in the library\n");
        printf("6. list the count of writter\n");
        printf("7. List writers name\n");
        printf("8. Exit");

        printf("\n\nEnter one of the above : ");
        scanf("%d", &j);

        switch (j)
        {
        /* Add book */
        case 1:
        {
            printf("Enter book name = ");
            scanf("%s", l[i].bk_name);

            printf("Enter writter name = ");
            scanf("%s", l[i].writter);

            printf("Enter pages = ");
            scanf("%d", &l[i].pages);

            printf("Enter price = ");
            scanf("%f", &l[i].price);
            keepcount++;

            break;
        }
        case 2:
        {
            printf("you have entered the following information\n");
            for (i = 0; i < keepcount; i++)
            {
                printf("book name = %s", l[i].bk_name);

                printf("\t witter name = %s", l[i].writter);

                printf("\t  pages = %d", l[i].pages);

                printf("\t  price = %f", l[i].price);
            }
            break;
        }
        case 3:
        {
            printf("Enter writter name : ");
            scanf("%s", wr_nm);
            for (i = 0; i < keepcount; i++)
            {
                if (strcmp(wr_nm, l[i].writter) == 0)
                    printf("%s %s %d %f", l[i].bk_name, l[i].writter, l[i].pages, l[i].price);
            }
            break;
        }
        case 4:
        {
            printf("Enter book name : ");
            scanf("%s", bk_nm);
            for (i = 0; i < keepcount; i++)
            {
                if (strcmp(bk_nm, l[i].bk_name) == 0)
                    printf("%s \t %s \t %d \t %f", l[i].bk_name, l[i].writter, l[i].pages, l[i].price);
            }
            break;
        }
        case 5:
            printf("\n No of books in library : %d", keepcount);
            break;
        case 6:
        {
            int writer_count = 0;
            char writers[100][50];;
            for (int k = 0; k < keepcount; k++)
            {
                strcpy(writers[k], l[k].writter);
            }

            for (int k = 0; k < keepcount; k++)
            {
                for (int l = 0; l < keepcount; l++)
                {
                    if (strcmp(writers[k], writers[l]) == 0)
                    {
                        writer_count++;
                    }
                }
            }
            printf("\n No of writters books in library: %d", writer_count);
            break;
        }
        case 7:
        {
            char writerss[10][50] = {};

            printf("\n List of writers: ");
            for (int k = 0; k < keepcount; k++)
            {
                strcpy(writerss[k], l[k].writter);
            }

            for (int k = 0; k < sizeof(writerss) / sizeof(writerss[0]); k++)
            {
                for (int l = 0; l < keepcount; l++)
                {
                    if (strcmp(writerss[k], writerss[l]) == 0)
                    {
                        printf("\n %s", writerss[k]);
                    }
                }
            }
            break;
        }
        case 8:
            exit(0);
        }
    }
    return 0;
}