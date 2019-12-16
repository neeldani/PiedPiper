import java.util.*;

class Heap {

	int capacity;
	int size;
	Node[] list;

	Heap(int capacity) {

		this.size = 0;
		this.capacity = capacity;
		this.list = new Node[capacity];
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

	// copies y into x
	void copy(Node x, Node y) {

		x.key = y.key;
		x.frequency = y.frequency;
		//x.left = y.left;
		//x.right = y.left;
	}

	void swap(int x, int y) {

		Node temp = list[x];
		list[x] = list[y];
		list[y] = temp;
	}

	void insert(Node newNode) {

		int index = size;
		list[index] = newNode;

		while(index != 0 && list[index].frequency < list[parent(index)].frequency) {

			swap(index, parent(index));
			index = parent(index);
		}

		this.size += 1;
	}

	void heapify(int index) {

		int smallest = index;
		int l = left(index);
		int r = right(index);

		if (l < size && list[l].frequency < list[smallest].frequency)
			smallest = l;

		if (r < size && list[r].frequency < list[smallest].frequency)
			smallest = r;

		if (smallest != index) {
			swap(index, smallest);
			heapify(smallest);
		}
	}

	Node extractMin() {

		//printHeap();
		int index = this.size;

		//System.out.println("Minimum: " + list[0].key + " " + list[0].frequency + "\n");
		//System.out.println("Swapping " + list[0].frequency + " " + list[size-1].frequency + "\n");
		swap(0, index-1);

		//System.out.println("Minimum: " + list[index-1].key + " " + list[index-1].frequency + "\n");

		this.size -= 1;

		heapify(0);

		return list[index-1];
	}

	void printHeap() {

		for(int i=0; i<size; i++)
			System.out.println(list[i].key + " " + list[i].frequency);
		System.out.println("\n");
	}
}