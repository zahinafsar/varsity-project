#include <limits.h>

#include <stdbool.h>

#include <stdio.h>

#define V 5

int minKey(int dist[], int sptSet[])
{
    int min = INT_MAX, min_index;
    for (int v = 0; v < V; v++)
    {

        if (sptSet[v] == 0 && dist[v] < min)
        {

            min = dist[v];
            min_index = v;
        }
    }

    return min_index;
}

int printSPT(int dist[])
{
    int sum = 0;

    printf("Vertex \tDistance from the Source Node\n");
    for (int i = 0; i < V; i++)
    {
        printf("%d - \t%d \n", i, dist[i]);
    }
}
void dijkstra(int graph[V][V], int src)
{

    int dist[V];
    int sptSet[V];
    int iteration = 0;

    for (int i = 0; i < V; i++)
    {
        dist[i] = INT_MAX;
        sptSet[i] = 0;
    }

    dist[0] = 0;

    for (int count = 0; count < V - 1; count++)
    {

        iteration++;

        int u = minKey(dist, sptSet);
        sptSet[u] = 1;

        for (int v = 0; v < V; v++)
            if (sptSet[v] == 0 && graph[u][v] != 0 && dist[u] != INT_MAX && dist[u] + graph[u][v] < dist[v])
            {
                dist[v] = dist[u] + graph[u][v];
            }
    }
    printf("Total iterations: %d \n", iteration);
    printSPT(dist);
}

int main()
{
    int graph[V][V] = {{0, 7, 3, 5, 0, 0},
                       {7, 0, 3, 0, 4, 0},
                       {3, 3, 0, 4, 8, 0},
                       {5, 0, 4, 0, 7, 9},
                       {0, 4, 8, 7, 0, 2},
                       {0, 0, 0, 9, 2, 0}};

    dijkstra(graph, 0);
    return 0;
}
