package LZW_compressor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


/**
 * @author Ashish Bisht <abisht1@uncc.edu>
 *
 */
public class LZW {


	
	
	
	
	private int bit_lenght;
	private double MAX_TABLE_SIZE;
	private HashMap<String, Integer> dictionary = new HashMap<>();
	private List<Integer> result = new ArrayList<>();
	public List<Integer> getResult() {
		return result;
	}






	public void setResult(List<Integer> result) {
		this.result = result;
	}







	private StringBuilder str= new StringBuilder();
	
	
	
	
	
	
	LZW(int x){
		bit_lenght=x;
		MAX_TABLE_SIZE=Math.pow(2, bit_lenght);
		for(int i=0;i<MAX_TABLE_SIZE;i++){
			dictionary.put( Character.toString((char)i), i);
			
		}
//		for(Entry e : dictionary.entrySet())
//			System.out.println("Key :"+e.getKey()+" Value :"+e.getValue());
		
	}

	
	
	
	
	
	public Integer push(String sBuilder) {
		for (Entry<String, Integer> set : dictionary.entrySet())
			if (sBuilder.equals(set.getKey()))
				return  set.getValue();

		dictionary.put(sBuilder,0);
		return 0;
	}
	
	
	
	

	public Integer ascii(StringBuilder str) {

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			sb.append((byte) ch);
		}
		// System.out.println(sb.toString());

		return Integer.parseInt(sb.toString());
	}
	
	
	
//  Compress *string to a list of output Symbols
	public Integer encode(StringBuilder string) {
		for (int i = 0; i < string.length(); i++) {
			Character ch = string.charAt(i);
//			StringBuilder strLocal = str;
//			strLocal.append(ch);
			if (dictionary.containsKey(str.toString()+ch))
				str.append(ch);
			else {	
				result.add(dictionary.get(str.toString()));
				dictionary.put(str.toString()+ch, (int)MAX_TABLE_SIZE++);				
				str.setLength(0);
				str.append(ch);
			}

		}
//		for (Entry<StringBuilder, Integer> set : dictionary.entrySet()) {
//			if (set.getKey().equals(str))
//				return set.getValue();
//		}
		
		
		if(!str.equals(""))
			result.add(dictionary.get(str.toString()));
		return 0;
	}
	
	
	
	
	
	
	
	public String decode(List<Integer> code){
		
		
		StringBuilder result = new StringBuilder();
		
		String pointer = Character.toString((char)(int)code.remove(0));
		
		result.append(pointer);
//		code.remove(0);
		
		for(int i: code){
			StringBuilder str= new StringBuilder();
			if(dictionary.containsValue(i)){
				str.append(dictionary.entrySet().stream().filter(e -> e.getValue().equals(i)).findFirst().get().getKey());
//				str.append(dictionary.entrySet().stream().filter(e -> e.getValue().equals(i)).map(Map.Entry::getKey).findFirst().orElse(null));				
			}
			else if(i==MAX_TABLE_SIZE){
//				str.append(result).append(result.charAt(0));
				str.setLength(0);
				str.append(pointer).append(pointer.charAt(0));
			}
			else 
				throw new IllegalArgumentException("Can not Identify Character " +i);
			result.append(str);
			dictionary.put( new StringBuilder(str.charAt(0)).toString(), (int)MAX_TABLE_SIZE++);
		
		pointer=str.toString();
		}
		return result.toString();
	}
	
	
	
	

}
