#include <stdio.h>
#include <stdlib.h>

#define MAX_VERTICES 100

int graph[MAX_VERTICES][MAX_VERTICES];
int visited[MAX_VERTICES];
int n;

void dfs(int v) {
  int i;
  
  visited[v] = 1;
  printf("Visited vertex %d\n", v);

  for (i = 0; i < n; i++) {
    if (graph[v][i] && !visited[i]) {
      dfs(i);
    }
  }
}

int main(int argc, char **argv) {
  int i, j;

  printf("Enter the number of vertices: ");
  scanf("%d", &n);
  printf("Enter the adjacency matrix:\n");
  for (i = 0; i < n; i++) {
    for (j = 0; j < n; j++) {
      scanf("%d", &graph[i][j]);
    }
  }

  for (i = 0; i < n; i++) {
    visited[i] = 0;
  }

  for (i = 0; i < n; i++) {
    if (!visited[i]) {
      dfs(i);
    }
  }

  return 0;
}