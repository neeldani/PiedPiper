import java.util.*;

class HeaderEncoder {

	String header;
	String filePath;
	Map <String, Integer> frequencyTable;
	BitHandler bitHandlderObj;
	char nchars;

	HeaderEncoder(Encoder encoder) {

		this.frequencyTable = encoder.frequencyTable;
		this.filePath = encoder.filePath;
		this.bitHanlderObj = new BitHandler();
		this.nchars = (char)frequencyTable.size();
	}

	void createHeader(Node root) {

		if(root == null)
			return;

		else if(root.left == null && root.right == null)
			header += "1" + root.key;

		else {
			
			header += "0";
			createHeader(root.left);
			createHeader(root.right);
		}
	}

	void encodeHeader(Node root) {

		int nBits = (18 * nchars) - 1; //number of bits for storing the tree
		int nBytes = Math.ceil(nBits)/8.0 + 2; // extra 2 bytes for stroing the distinct characters in the file

		createHeader(root);
		this.header = nchars + header;

		System.out.println(header);
	}
}