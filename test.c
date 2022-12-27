#include <stdio.h>
#include <stdlib.h>

#define MAX_NODES 100

// Data structure to store a graph
struct Graph {
  // An array of pointers to Node to represent adjacency list
  struct Node* head[MAX_NODES];
};

// Data structure to store a graph node
struct Node {
  int dest;
  struct Node* next;
};

// Data structure to store a queue for BFS
struct Queue {
  int front, rear;
  int size;
  unsigned capacity;
  int* array;
};

// Function to create a new graph node
struct Node* newNode(int dest)
{
  struct Node* newNode = (struct Node*)malloc(sizeof(struct Node));
  newNode->dest = dest;
  newNode->next = NULL;
  return newNode;
}

// Function to create a graph with V vertices
struct Graph* createGraph(int V)
{
  struct Graph* graph = (struct Graph*)malloc(sizeof(struct Graph));

  for (int i = 0; i < V; i++)
    graph->head[i] = NULL;

  return graph;
}

// Function to add an edge to a graph
void addEdge(struct Graph* graph, int src, int dest)
{
  // Add an edge from src to dest
  struct Node* newNode = newNode(dest);
  newNode->next = graph->head[src];
  graph->head[src] = newNode;

  // Add an edge from dest to src
  newNode = newNode(src);
  newNode->next = graph->head[dest];
  graph->head[dest] = newNode;
}

// Function to create a queue of given capacity
struct Queue* createQueue(unsigned capacity)
{
  struct Queue* queue = (struct Queue*)malloc(sizeof(struct Queue));
  queue->front = queue->size = 0;
  queue->rear = capacity - 1;
  queue->capacity = capacity;
  queue->array = (int*)malloc(queue->capacity * sizeof(int));
  return queue;
}

// Function to check if the queue is full
int isFull(struct Queue* queue)
{
  return (queue->size == queue->capacity);
}

// Function to check if the queue is empty
int isEmpty(struct Queue* queue)
{
  return (queue->size == 0);
}

// Function to add an item to the queue
void enqueue(struct Queue* queue, int item)
{
  if (isFull(queue))
    return;
  queue->rear = (queue->rear + 1) % queue->capacity;
  queue->array[queue->rear] = item;
  queue->size = queue->size + 1;
}

// Function to remove an item from the queue
int dequeue(struct Queue* queue)
{
  if (isEmpty(queue))
    return INT_MIN;
  int item = queue->array[queue->front];
  queue->front = (queue->front + 1) % queue->capacity;
  queue->size = queue->size - 1;
  return item;
}

// Function to get the front item from the queue
int front(struct Queue* queue)
{
  if (isEmpty(queue))
    return INT_MIN;
  return queue->array[queue->front];
}

// Function to get the rear item from the queue
int rear(struct Queue* queue)
{
  if (isEmpty(queue))
    return INT_MIN;
  return queue->array[queue->rear];
}

// Recursive function to print the shortest path between
// source and destination nodes
void printPath(int parent[], int j, int src)
{
  // Base case : If j is source
  if (parent[j] == -1)
    return;

  printPath(parent, parent[j], src);

  printf("%d ", j);
}

// Function to find shortest path between source and destination nodes
// using a breadth-first search algorithm
void BFS(struct Graph* graph, int src, int dest)
{
  // Create a queue for BFS
  struct Queue* queue = createQueue(MAX_NODES);

  // An array to store the parent nodes of the visited nodes
  int parent[MAX_NODES];

  // A boolean array to track the visited nodes
  int visited[MAX_NODES];

  // Initialize the arrays
  for (int i = 0; i < MAX_NODES; i++) {
    visited[i] = 0;
    parent[i] = -1;
  }

  // Add the source node to the queue
  enqueue(queue, src);
  visited[src] = 1;

  // Loop until the queue is empty
  while (!isEmpty(queue)) {
    // Get the front node from the queue
    int u = front(queue);
    dequeue(queue);

    // Get the node's adjacency list
    struct Node* temp = graph->head[u];

    // Loop through the adjacency list
    while (temp) {
      int v = temp->dest;

      // If the node has not been visited, visit it and add it to the queue
      if (!visited[v]) {
        visited[v] = 1;
        parent[v] = u;
        enqueue(queue, v);
      }

      // If the destination node has been reached, print the path and return
      if (v == dest) {
        printPath(parent, dest, src);
        return;
      }

      temp = temp->next;
    }
  }
}

int main()
{
  // Create a graph with 4 nodes
  int V = 4;
  struct Graph* graph = createGraph(V);

  // Add edges to the graph
  addEdge(graph, 0, 1);
  addEdge(graph, 0, 2);
  addEdge(graph, 1, 2);
  addEdge(graph, 2, 0);
  addEdge(graph, 2, 3);
  addEdge(graph, 3, 3);

  // Find the shortest path from node
  // 0 to node 3
  int src = 0, dest = 3;
  BFS(graph, src, dest);

  return 0;
}