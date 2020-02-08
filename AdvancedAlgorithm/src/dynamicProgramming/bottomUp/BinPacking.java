package dynamicProgramming.bottomUp;

import java.util.Arrays;
import java.util.Collections;

public class BinPacking {

	private int maxSize;
	private int[] bins;
	private int[] weights;

	BinPacking(int maxSize, int[] weights) {
		this.maxSize = maxSize;
		this.bins = new int[weights.length];
		Arrays.fill(this.bins, maxSize);
		this.weights = sortDecendingOrder(weights);

		long start = System.currentTimeMillis();
		BP(bins, this.weights);
		long end = System.currentTimeMillis();
		System.out.println("Takes " + (end - start) / 1000 + "seconds " + (end - start) % 1000 + "milliseconds");

	}

	private void BP(int[] bins, int[] weights) {

		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < bins.length; j++) {
				if (weights[i] <= bins[j]) {
					bins[j] -= weights[i];
					bins = sortAscendingOrder(bins);
					break;
				}
			}
			System.out.println("Weight : " + weights[i] + "\t Bin Status : " + Arrays.toString(bins));
		}
	}

	int binCount() {

		int temp = 0;
		for (int i = 0; i < bins.length; i++)
			if (bins[i] < maxSize)
				temp++;
		return temp;
	}

	private int[] sortDecendingOrder(int[] array) {
		Arrays.parallelSort(array);
		Collections.reverse(Arrays.asList(array));
		return array;
	}

	private int[] sortAscendingOrder(int[] array) {
		Arrays.parallelSort(array);
		return array;
	}

	public static void main(String args[]) {

		// int[] objs = { 2, 3, 1, 2, 4, 7, 8, 1, 9 };

		// int[] objs = { 2, 5, 4, 7, 1, 3, 8, 5, 8, 10, 7, 1, 2, 3 ,4 };
		// For 15 Objects BottomUp is faster than TopDown.
		
		// int[] objs = { 2, 5, 4, 7, 1, 3, 8, 5, 8, 10, 7, 1, 2, 3, 4, 6, 5, 4,
		// 3, 2, 1, 7, 8, 9, 7, 6, 5, 4, 3, 2, 3, 5,
		// 7, 9, 8, 6, 4, 2, 4, 6, 8, 9, 4, 8, 6, 2, 4, 7, 9, 10 };
		// For 50 Objects BottomUp is slower than TopDown.
		
		int[] objs = { 2, 5, 4, 7, 1, 3, 8, 5, 8, 10, 7, 1, 2, 3, 4, 6, 5, 4, 3, 2, 1, 7, 8, 9, 7, 6, 5, 4, 3, 2, 3, 5,
				7, 9, 8, 6, 4, 2, 4, 6, 8, 9, 4, 8, 6, 2, 4, 7, 9, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 8, 7, 6, 5, 4, 3,
				2, 1, 2, 3, 4, 5, 6, 7, 8, 9, 8, 7, 6, 5, 4, 3, 2, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1, 4, 5, 6, 7, 8, 9, 1 };

		// For 100 Objects BottomUp is slower than TopDown.
		BinPacking bp = new BinPacking(10, objs);
		System.out.println("\t No of bin used: " + bp.binCount());

	}

}
