#include <stdio.h>
#include <stdlib.h>

typedef struct node
{
	char key;
	int freq;
	struct node *left;
	struct node *right;
}node;

typedef struct
{
	node **heapArray; // change to node** array
	int capacity;
	int size;
}heap;

node* getNode(char key, int freq) {

	node *newNode = (node*)malloc(sizeof(node));
	newNode->key = key;
	newNode->freq = freq;
	newNode->left = NULL;
	newNode->right = NULL;
	return newNode;
}

heap *createNewHeap(int capacity) {

	heap *h = (heap*)malloc(sizeof(heap)); 

	h->heapArray = (node**)malloc(sizeof(node*)*capacity); // change int to node*
	h->capacity = capacity;
	h->size = 0;

	return h;
}

int parent(int index) {
	return index/2;
}

int left(int index) {
	return 2*index + 1;
}

int right(int index) {
	return 2*index + 2;
}

void swap(node **x, node **y) {
	
	node* temp = *x;
	*x = *y;
	*y = temp;
}

void heapify(heap *h, int index) {

	int smallest = index;

	int l = left(index);
	int r = right(index);

	if (l < h->size && h->heapArray[l]->freq < h->heapArray[smallest]->freq)
		smallest = l;

	if (r < h->size && h->heapArray[r]->freq < h->heapArray[smallest]->freq)
		smallest = r;

	if (smallest != index){

		swap(&(h->heapArray[index]), &(h->heapArray[smallest]));
		heapify(h, smallest);
	}
}

void insert(heap *h, node* newNode) {

	int index = h->size;
	h->heapArray[index] = newNode;
	
	while (index != 0 && h->heapArray[parent(index)]->freq > h->heapArray[index]->freq) {
		
		swap(&(h->heapArray[index]), &(h->heapArray[parent(index)]));
		index = parent(index);
	}

	h->size += 1;
}

void buildHeap(heap *h) {

	for(int i=h->size/2 - 1; i>=0; i--) {
		heapify(h, i);
	}	
}

void printHeap(heap *h) {

	for(int i=0; i<h->size; i++)
		printf("%d ", h->heapArray[i]->freq);
	printf("\n");
}

node* extractMin(heap *h) {

	node* min = h->heapArray[0];
	swap(&(h->heapArray[0]), &(h->heapArray[h->size-1]));
	h->size -= 1;
	heapify(h, 0);
	return min;
}

void printCodes(node *root, int codes[], int i) {

	if (!root)
		return;

	if (!root->left && !root->right) {

		printf("%c %d: ", root->key, root->freq);
		for(int j=0; j<i; j++)
			printf("%d ", codes[j]);
		printf("\n");
	}

	if (root->left) {
		
		codes[i] = 0;
		printCodes(root->left, codes, i+1);
	}

	if (root->right) {

		codes[i] = 1;
		printCodes(root->right, codes, i+1);
	}
}

void preorder(node *root) {

	if (root) {
		printf("%c %d\n", root->key, root->freq);
		preorder(root->left);
		preorder(root->right);
	}
}

// driver program 

int main() {

	int n = 6;
	heap *h = createNewHeap(n);
	char charArr[] = { 'a', 'b', 'c', 'd', 'e', 'f' }; 
    int intArr[] = { 5, 9, 12, 13, 16, 45 }; 
    
	for(int i=0; i<n; i++)
		insert(h, getNode(charArr[i], intArr[i]));
	
	for(int i=0; i<n-1; i++){
		node* firstMin = extractMin(h);
		node *secondMin = extractMin(h);
		node *newNode = getNode('$', firstMin->freq + secondMin->freq);
		newNode->left = firstMin;
		newNode->right = secondMin;

		insert(h, newNode);

		/*
		printf("First Node: %d %c\n", firstMin->freq, firstMin->key);
		printf("Second Node: %d %c\n", secondMin->freq, secondMin->key);

		printf("New node: %d %c\n\n", newNode->freq, newNode->key);
		*/
	}

	node *root = h->heapArray[0];
	if (root == NULL)
		printf("NULL");

	//preorder(root);

	//test
	int codes[100];
	printCodes(root, codes, 0);

	return 0;
} 