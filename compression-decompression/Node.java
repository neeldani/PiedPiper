import java.util.*;

class Node {

	String key;
	int frequency;
	Node left;
	Node right;

	Node() {

	}

	Node(String key, int frequency) {

		this.key = key;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}
}