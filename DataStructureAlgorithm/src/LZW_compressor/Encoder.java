package LZW_compressor;

import java.io.File;
import java.io.FileNotFoundException;
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
public class Encoder {
	private int bit_lenght;
	private double MAX_TABLE_SIZE;
	private HashMap<String, Integer> dictionary = new HashMap<>();
	private List<Integer> result = new ArrayList<>();
	private StringBuilder str = new StringBuilder();
	// static ClassLoader cl =getClass().getClassLoader();

	public Integer encode(StringBuilder string, int length) {

		bit_lenght = length;
		MAX_TABLE_SIZE = Math.pow(2, bit_lenght);
		for (int i = 0; i < MAX_TABLE_SIZE; i++) {
			dictionary.put(Character.toString((char) i), i);
		}
		for (int i = 0; i < string.length(); i++) {
			Character ch = string.charAt(i);
			// StringBuilder strLocal = str;
			// strLocal.append(ch);
			if (dictionary.containsKey(str.toString() + ch))
				str.append(ch);
			else {
				result.add(dictionary.get(str.toString()));
				dictionary.put(str.toString() + ch, (int) MAX_TABLE_SIZE++);
				str.setLength(0);
				str.append(ch);
			}

		}
		// for (Entry<StringBuilder, Integer> set : dictionary.entrySet()) {
		// if (set.getKey().equals(str))
		// return set.getValue();
		// }

		if (!str.equals(""))
			result.add(dictionary.get(str.toString()));
		return 0;
	}

	public static void main(String[] args) throws IOException {
		File file = null;
		int length = 0;
		String fileName = "";

		if (args.length > 1) {
			fileName = args[0];
			file = new File(System.getProperty("user.dir") + "//src//LZW_compressor//" + fileName);
			// file = new
			// File(Encoder.class.getClass().getClassLoader().getResource(args[0]).getFile());
			
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

		// LZW object = new LZW(length);
		Encoder object = new Encoder();
		object.encode(new StringBuilder(content), length);
		System.out.println(object.result);
		String url = System.getProperty("user.dir") + "//src//LZW_compressor//" + fileName.split("\\.")[0] + ".lzw";
		FileWriter fWriter = new FileWriter(url);

		for (int i : object.result)
			fWriter.write(Integer.toBinaryString(0x10000 | i).substring(1) + "\n");

		fWriter.close();

		// System.out.println(object.dictionary);
		// for(Entry e : object.dictionary.entrySet())
		// System.out.println("Key :"+e.getKey()+" Value :"+e.getValue());
		// System.out.println(object.decode(object.getResult()));

	}
}
