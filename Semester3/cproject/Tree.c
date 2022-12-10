#include <stdio.h>
#include <stdlib.h>

struct node
{
    int data;
    struct node *left;
    struct node *right;
};

struct node *root = NULL;

void createTree();
struct node *insert(int, struct node *refRoot);
void inorder(struct node *refRoot);
void preorder(struct node *refRoot);
void postorder(struct node *refRoot);
void searchMain();
void search(struct node *refRoot, int value);
void printleaf(struct node *refRoot);

int main()
{
    int option;
    while (option != 7)
    {
        printf("\n\n----Enter Your Choice:----\n\n");
        printf("(1) Create a tree\n");
        printf("(2) Inorder\n");
        printf("(3) Preorder\n");
        printf("(4) Postorder\n");
        printf("(5) Search\n");
        printf("(6) Print leaf\n");
        printf("(7) Exit\n");
        printf("> ");
        scanf("%d", &option);
        printf("-----------------------------\n");
        switch (option)
        {
        case 1:
            createTree();
            break;
        case 2:
            inorder(root);
            break;
        case 3:
            preorder(root);
            break;
        case 4:
            postorder(root);
            break;
        case 5:
            searchMain();
            break;
        case 6:
            printleaf(root);
            break;
        case 7:
            printf("\nExiting...");
            break;
        default:
            printf("\nInvailed Choice");
        }
    }
    return 0;
}

void createTree()
{
    int n;
    int arr[n];
    printf("Enter the number of nodes: ");
    scanf("%d", &n);
    printf("Enter the value of nodes: ");
    for (int i = 0; i < n; i++)
    {
        scanf("%d", &arr[i]);
    }
    for (int i = 0; i < n; i++)
    {
        insert(arr[i], root);
    }
};

struct node *insert(int value, struct node *refRoot)
{
    struct node *newNode = (struct node *)malloc(sizeof(struct node));
    newNode->data = value;
    newNode->left = NULL;
    newNode->right = NULL;
    if (refRoot == NULL)
    {
        refRoot = newNode;
    }
    if (refRoot->data < value)
    {
        refRoot->right = insert(value, refRoot->right);
    }
    else if (refRoot->data > value)
    {
        refRoot->left = insert(value, refRoot->left);
    }
    root = refRoot;
    return refRoot;
}

void inorder(struct node *refRoot)
{
    if (root == NULL)
    {
        printf("Tree is empty");
        return;
    }
    if (refRoot == NULL)
    {
        return;
    }
    inorder(refRoot->left);
    printf("%d -> ", refRoot->data);
    inorder(refRoot->right);
};

void preorder(struct node *refRoot)
{
    if (root == NULL)
    {
        printf("Tree is empty");
        return;
    }
    if (refRoot == NULL)
    {
        return;
    }
    printf("%d -> ", refRoot->data);
    preorder(refRoot->left);
    preorder(refRoot->right);
};

void postorder(struct node *refRoot)
{
    if (root == NULL)
    {
        printf("Tree is empty");
        return;
    }
    if (refRoot == NULL)
    {
        return;
    }
    postorder(refRoot->left);
    postorder(refRoot->right);
    printf("%d -> ", refRoot->data);
};

void searchMain()
{
    int value;
    printf("Enter the value to search: ");
    scanf("%d", &value);
    search(root, value);
};

void search(struct node *refRoot, int value)
{
    if (root == NULL)
    {
        printf("Tree is empty");
        return;
    }
    if (refRoot == NULL)
    {
        return;
    }
    if (refRoot->data == value)
    {
        printf("Found the value at address %p", refRoot);
        return;
    }
    search(refRoot->left, value);
    search(refRoot->right, value);
};

void printleaf(struct node *refRoot)
{
    if (root == NULL)
    {
        printf("Tree is empty");
        return;
    }
    if (refRoot == NULL)
    {
        return;
    }
    printleaf(refRoot->left);
    if (refRoot->left == NULL && refRoot->right == NULL)
    {
        printf("%d, ", refRoot->data);
    }
    printleaf(refRoot->right);
};