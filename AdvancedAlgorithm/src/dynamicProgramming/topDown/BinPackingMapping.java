package dynamicProgramming.topDown;


import java.util.Arrays;
import java.util.Collections;

public class BinPackingMapping {

	private int maxSize;
	private int[] bins;
	private int[] weights;
	

	BinPackingMapping(int maxSize, int[] weights) {
		this.maxSize = maxSize;
		this.bins = new int[weights.length];
//		for (int i = 0; i < weights.length; i++)
//			this.bins[i] = this.maxSize;
		Arrays.fill(this.bins, maxSize);
		this.weights=sortDecendingOrder(weights);
		BP(bins, weights[0]);
		
	}

	void BP(int[] bins, int weight) {

		for (int j = 0; j < bins.length; j++) {
			if (weight <= bins[j]) {
				bins[j] -= weight;
				System.out.println("Weight: " + weight + " mapped to bin " + j); 
				break;
			}
		} // Sub optimal
//		System.out.println("Weight: " + weight + " mapped to bin " + binCount()); //Added this line only to re-iterate the output to bin mapping.
		//System.out.println("Weight : " + weights[0] + "\t Bin Status : " + Arrays.toString(bins));
		
		if (1 < this.weights.length){
			weights=Arrays.copyOfRange(weights, 1, weights.length);
			BP(sortAscendingOrder(bins),weights[0]);}
	}

	 int binCount() {

		int temp = 0;
		for (int i = 0; i < bins.length; i++)
			if (bins[i] < maxSize)
				temp++;
		return temp;
	}

	int[] sortDecendingOrder(int[] array) {
		Arrays.parallelSort(array);
		Collections.reverse(Arrays.asList(array));
		return array;
	}

	public static void main(String args[]) {

		int[] objs = { 2,3,1,2,4,7,8,1,9 };
		BinPackingMapping bp = new BinPackingMapping(10, objs);
		System.out.println("\t No of bin used: " + bp.binCount());

	}
	private int[] sortAscendingOrder(int[] array){
		Arrays.parallelSort(array);
		return array;
	}

}