package dynamicProgramming.topDown;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class RodCutting {
	// private Map<Integer, Integer> map = new HashMap<>();
	private List<Entry<Integer, Integer>> list = new ArrayList<>();

	private int rod;

	RodCutting(int rod, int[][] values) {

		arrayToArrayList(values);

		Collections.sort(list, new Comparator<Entry<Integer, Integer>>() {

			@Override
			public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2) {
				// TODO Auto-generated method stub

				if ((o1.getValue() / o1.getKey()) > (o2.getValue() / o2.getKey()))
					return -1;
				else
					return 1;
			}

		});

		System.out.println(RC(rod));
	}

	private int RC(int size) {
		int value = 0;
		for (Map.Entry<Integer, Integer> entry : list) {
			if (entry.getKey() <= size) {
				value += entry.getValue();
				size -= entry.getKey();
				break;
			}
		}
		if (size >= 1)
			value += RC(size);

		return value;
	}

	private void arrayToArrayList(int[][] values) {
		Set<Entry<Integer, Integer>> set;
		HashMap<Integer, Integer> hMap = new HashMap<>();
		for (int i = 0; i < values.length; i++)			
			hMap.put(values[i][0], values[i][1]);

		set = hMap.entrySet();
		list = new ArrayList<Entry<Integer, Integer>>(set);
	}

	public static void main(String args[]) {

		int[][] objs = { { 1, 2 }, { 2, 1 }, { 3, 1 }, { 4, 4 } };

		RodCutting rc = new RodCutting(10, objs);
	}
}
