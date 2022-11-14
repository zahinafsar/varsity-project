#include<stdio.h>

struct node {
  struct node* prev;
  int data;
  struct node* next;
};

struct node* head = NULL;

void insertFirst();
void insertLast();
void deleteFirst();
void deleteLast();
void print();

int main() {
  while(1){
    printf("\n\n----------------------\n");
    printf("Choose an option: \n 1. Insert at top \n 2. Insert at bottom\n 3. Delete from top\n 4. Delete from bottom\n 5. Print\n\n> ");
    int choice;
    scanf("%d", &choice);
    printf("----------------------\n\n");
    switch (choice) {
      case 1:
        insertFirst();
        break;
      case 2:
        insertLast();
        break;
      case 3:
        deleteFirst();
        break;
      case 4:
        deleteLast();
        break;
      case 5:
        print();
        break;
      default:
        printf("Invalid choice");
        break;
    }
  }
  return 0;
}

void insertFirst(){
  printf("Enter a value to insert\n> ");
  int value;
  scanf("%d", &value);
  struct node* newNode = (struct node*)(malloc(sizeof(struct node)));
  newNode->data = value;
  newNode->prev = NULL;
  newNode->next = head;
  head = newNode;
  printf("Insertion as top successfull");
}

void insertLast(){
  printf("Enter a value to insert\n> ");
  int value;
  scanf("%d", &value);
  struct node* newNode = (struct node*)(malloc(sizeof(struct node)));
  newNode->data = value;
  newNode->next = NULL;

  struct node* temp = head;
  while(temp->next != NULL){
    temp = temp->next;
  }
  temp->next = newNode;
  newNode->prev = temp;
  printf("Insertion at back successfull");
}

void deleteFirst(){
  if (head == NULL){
    printf("List is empty");
  } else {
    struct node* temp = head;
    head = head->next;
    free(temp);
    printf("Deletion from top successfull");
  }
}

void deleteLast(){
  if (head == NULL){
    printf("List is empty");
  } else {
    struct node* temp = head;
    while(temp->next != NULL){
      temp = temp->next;
    }
    (temp->prev)->next = NULL;
    free(temp);
    printf("Deletion from bottom successfull");
  }
}

void print(){
  if (head == NULL){
    printf("List is empty");
  } else {
    struct node* temp = head;
    while(temp != NULL){
      printf("%d ", temp->data);
      temp = temp->next;
    }
  }
}