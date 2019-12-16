import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Encoder {

	String filePath;
	BufferedReader reader;
	Map <String, Integer> frequencyTable;
	Map <String, String> codesTable;
	Heap h;

	HeaderEncoder headerEnc;

	Encoder(String filePath) {

		this.filePath = filePath;
		this.frequencyTable = new HashMap<String, Integer>();
		this.codesTable = new HashMap<String, String>();

		this.headerEnc = new HeaderEncoder();

		try {
			reader = new BufferedReader(new FileReader(filePath));
		}

		catch(Exception e) {
			e.printStackTrace();
		}
	}

	String readLine() {
		
		try {

			String line = reader.readLine();
			
			if (line != null)
				return line + "\n";

			else {

				reader.close();
				return null;
			}
		} 

		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	void addToTable(String key) {

		if(frequencyTable.containsKey(key)) {
			frequencyTable.put(key, frequencyTable.get(key) + 1);
		}

		else {
			frequencyTable.put(key, 1);
		}

	}

	void printFrequencyTable() {

		System.out.println("\n\nFREQUENCY TABLE\n");

		for (String key : frequencyTable.keySet()) 
        {
        	int frequency = frequencyTable.get(key).intValue();

        	if (key.equals("\n"))
        		key = "New Line";

        	else if (key.equals(" "))
        		key = "Space";

            System.out.println(key + " : " + frequency);
        }
	}

	void createFrequencyTable() {

		String line = readLine();

		while(line != null) {

			//System.out.print(line);

			for(int i=0; i<line.length(); i++){

				//System.out.println("Adding " + (int)line.charAt(i) + ": " + String.valueOf(line.charAt(i)));
				addToTable(String.valueOf(line.charAt(i)));
			}

			line = readLine();
		}
	}


	//Huffman Tree helper functions

	void createHuffmanTree() {

		h = new Heap(frequencyTable.size());
		
		//Populate Heap
		for(String key : frequencyTable.keySet()){
			h.insert(new Node(key, frequencyTable.get(key)));
		}
		
		while(h.size != 1) {

			Node firstMin = h.extractMin();
			Node secondMin = h.extractMin();

			Node newNode = new Node("$", firstMin.frequency + secondMin.frequency);
			newNode.left = firstMin;
			newNode.right = secondMin;

			h.insert(newNode); 
		}
	}

	String arrayToString(int[] encoding, int index) {
		
		String stringEncoding = "";

		for(int i=0; i<index; i++)
			stringEncoding += encoding[i];

		return stringEncoding;
	}

	void createCodeTable(Node root, int[] encoding, int index) {

		if (root == null)
			return;

		if (root.left == null && root.right == null) 
			codesTable.put(root.key, arrayToString(encoding, index));

		if (root.left != null) {
		
			encoding[index] = 0;
			generateCodeTable(root.left, encoding, index+1);
		}

		if (root.right != null) {

			encoding[index] = 1;
			generateCodeTable(root.right, encoding, index+1);
		}
	}

	void createCodeTableDriver() {

		int encoding[] = new int[20];
		generateCodeTable(h.list[0], encoding, 0);
	}

	void printCodeTable() {

		System.out.println("\nCODE TABLE\n");
		for (String key : codesTable.keySet()) 
        {
        	String codeword = codesTable.get(key);

        	if (key.equals("\n"))
        		key = "New Line";

        	else if (key.equals(" "))
        		key = "Space";

            System.out.println(key + " : " + codeword);
        }
	}

	//driver function for Encoder class

	void encode() {

		createFrequencyTable();
		createHuffmanTree();
		createCodeTableDriver();

		printFrequencyTable();
		printCodeTable();

		headerEnc.encodeHeader(h.list[0]);
	}
}