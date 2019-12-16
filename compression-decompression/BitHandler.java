import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

class BitHandler {

	int bitIndex;
	int blockIndex;

	BitHandler() {

		bitIndex = 7;
		blockIndex = 0;
	}


	void set() {

		//System.out.println("Setting bit " + bitIndex + " of block " + blockIndex);
		mem[blockIndex] = (byte) (mem[blockIndex] | (1 << bitIndex));
	}

	void unset() {

		//System.out.println("Unsetting bit " + bitIndex + " of block " + blockIndex);
		mem[blockIndex] = (byte) (mem[blockIndex] & ~(1 << bitIndex));
	}

	void encodeBits(int bit) {

		if(bitIndex == -1) {

			bitIndex = 7;
			blockIndex += 1;
		}

		if (bit == 1) {

			set();
			bitIndex += -1;
		}

		else {

			unset();
			bitIndex += -1;
		}
	}


	void writeHeaderBytesToFile(byte[] mem, boolean append) {

		try (FileOutputStream fos = new FileOutputStream("encoded.txt", append)) {
   		fos.write(mem);
		}

		catch(Exception e) {
			System.out.println("Error writing to FileOutputStream");
		}
	}

	
	byte[] encodeString(String sequence, int bytes) {

		byte[] mem = new byte[bytes];

		for(int i=0; i<sequence.length(); i++) {

			System.out.println("INPUT CHARACTER: " + sequence.charAt(i));
			if (sequence.charAt(i) == '1')
				encodeBits(1);

			else if (sequence.charAt(i) == '0') 
				encodeBits(0);

			else {

				int unicode = (int)sequence.charAt(i);
				System.out.println("Unicode: " + unicode);

				// 15 for unicode
				for(int j=15; j>=0; j--) {

					int bit = (unicode & (1 << j)) >> j; // might be problematic
					encodeBits(bit);
				}
			}
		}

		return mem;
	}

}