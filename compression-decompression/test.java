import java.util.*;
import java.io.File;
import java.io.FileInputStream;

class test {

	public static void main(String[] args) {

		FileInputStream fileInputStream = null;
        byte[] bytesArray = null;

        try {

            File file = new File("encoded.txt");
            bytesArray = new byte[(int) file.length()];

            //read file into bytes[]
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytesArray);

        } catch (Exception e) {
            e.printStackTrace();
        }

        for(int i=0; i<10; i++)
        	System.out.println(bytesArray[i]);
	}
}