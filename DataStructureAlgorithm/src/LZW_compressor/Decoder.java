
package LZW_compressor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * @author Ashish Bisht <abisht1@uncc.edu>
 *
 */
public class Decoder {
	private int bit_lenght;
	private double MAX_TABLE_SIZE;
	private HashMap<String, Integer> dictionary = new HashMap<>();

	public String decode(List<Integer> code, int length) {

		bit_lenght = length;
		MAX_TABLE_SIZE = Math.pow(2, bit_lenght);
		for (int i = 0; i < MAX_TABLE_SIZE; i++) {
			dictionary.put(Character.toString((char) i), i);
		}

		
		String pointer = Character.toString((char) (int) code.remove(0));
		StringBuilder result = new StringBuilder(pointer);

		

//		result.append(pointer);
		// code.remove(0);

		for (int i : code) {
			StringBuilder str = new StringBuilder();
			if (dictionary.containsValue(i)) {
				
				str.append(
						dictionary.entrySet().stream().filter(e -> e.getValue().equals(i)).findFirst().get().getKey());
			}else if(i==MAX_TABLE_SIZE){
				
//				str.setLength(0);
				str.append(pointer).append(pointer.charAt(0));
			
				
				
				// str.append(dictionary.entrySet().stream().filter(e ->
				// e.getValue().equals(i)).map(Map.Entry::getKey).findFirst().orElse(null));
			
			} else
				throw new IllegalArgumentException("Can not Identify Character " + i);
			result.append(str);
			
			dictionary.put(new StringBuilder(pointer+str.charAt(0)).toString(), (int) MAX_TABLE_SIZE++);
			pointer = str.toString();
		}
		return result.toString();
	}

	public static void main(String[] args) throws IOException {
		File file = null;
		int length = 0;
		String fileName = "";
		if (args.length > 1) {
			fileName = args[0];
			file = new File(System.getProperty("user.dir") + "//src//LZW_compressor//" + fileName);
			length = Integer.parseInt(args[1]);
			if(length>12 && length<9){
				System.out.println("Length value is not acceptable");
				return;
			}

		}

		String content = null;
		try (Scanner s = new Scanner(file)) {
			content = s.useDelimiter("\\A").next();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] strArray = content.toString().split("\n");
		List<Integer> intList = new ArrayList();
		for (String str : strArray)
			intList.add(Integer.parseInt(str, 2));

		// LZW object = new LZW(length);
		Decoder object = new Decoder();

		// object.encode(new StringBuilder(s.toString()));
		// System.out.println(object.getResult());
		// System.out.println(object.dictionary);
		// for(Entry e : object.dictionary.entrySet())
		// System.out.println("Key :"+e.getKey()+" Value :"+e.getValue());
		String str = object.decode(intList, length);
		System.out.println(str);
		String url = System.getProperty("user.dir") + "//src//LZW_compressor//" + fileName.split("\\.")[0] + "_decoded.txt";
		FileWriter fWriter = new FileWriter(url);

		fWriter.write(str);

		fWriter.close();

	}
}
