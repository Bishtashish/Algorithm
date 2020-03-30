package informedSearches.eightPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class AStar1 {

	private int goal[][];
	private Map<Integer, int[][]> fringe = new TreeMap<Integer, int[][]>();
	private ArrayList<int[][]> explored = new ArrayList();
	private Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

	AStar1(int array[][], int goal[][]) {
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] != goal[i][j]) {
					int temp = findDistance(findLoc(array[i][j], array), findLoc(array[i][j], goal));
					map.put(array[i][j], temp);
				}
			}
		this.goal = goal;

		step(array);

	}

	public int findLoc(int n, int[][] array) {
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if (n == array[i][j])
					return (i * 3) + (j + 1);
		return 0;
	}

	public int findDistance(int i, int j) {
		// H diff
		int hDiff;
		hDiff = Math.abs((i % 3 == 0 ? 3 : i % 3) - (j % 3 == 0 ? 3 : j % 3));

		// V diff
		int vDiff;
		vDiff = Math.abs((i % 3 == 0 ? (i / 3) - 1 : i / 3) - (j % 3 == 0 ? (j / 3) - 1 : j / 3));

		return vDiff + hDiff;

	}

	public int heuristicMisplaced(int[][] array, int[][] goal) {
		int count = 0;

		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if (array[i][j] != goal[i][j])
					count++;
		return count;
	}

	public int heuristicManhatten(int[][] array, int[][] goal, boolean flag) {
		int tempSum = 0;
		map.clear();
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] != 0 && array[i][j] != goal[i][j]) {
					int temp = findDistance(findLoc(array[i][j], array), findLoc(array[i][j], goal));
					if (flag)
						map.put(array[i][j], temp);
					tempSum += temp;
				}
			}
		return tempSum;
	}

	public void step(int[][] array) {
		int loc;
		int[][] upArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		int[][] leftArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		int[][] rightArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		int[][] downArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		int upHCount = 100, downHCount = 100, leftHCount = 100, rightHCount = 100;

		if (heuristicManhatten(array, goal, true) == 0)
			return;
		else
			loc = findLoc(0, array);

		System.out.print("\n");
		for (Map.Entry s : map.entrySet()) {
			if (!s.getValue().equals(0))
				System.out.println("Keys: " + s.getKey() + " Values: " + s.getValue());
		}

		// UP swap

		if (loc - 3 > 0) {
			upHCount = 0;
			upArray[(loc - 1) / 3][(loc - 1) % 3] = upArray[((loc - 1) / 3) - 1][(loc - 1) % 3];
			upArray[((loc - 1) / 3) - 1][(loc - 1) % 3] = 0;
			upHCount = heuristicManhatten(upArray, goal, false);

		}

		// LEFT swap

		if ((loc - 1) % 3 >= 0) {
			leftHCount = 0;
			leftArray[(loc - 1) / 3][(loc - 1) % 3] = leftArray[(loc - 1) / 3][((loc - 1) % 3) - 1];
			leftArray[(loc - 1) / 3][((loc - 1) % 3) - 1] = 0;
			leftHCount = heuristicManhatten(leftArray, goal, false);
		}
		// RIGHT swap

		if (loc % 3 != 0) {
			rightHCount = 0;
			rightArray[(loc - 1) / 3][(loc - 1) % 3] = rightArray[(loc - 1) / 3][((loc - 1) % 3) + 1];
			rightArray[(loc - 1) / 3][((loc - 1) % 3) + 1] = 0;
			rightHCount = heuristicManhatten(rightArray, goal, false);
		}
		// DOWN swap

		if (loc + 3 <= 9) {
			downHCount = 0;
			downArray[(loc - 1) / 3][(loc - 1) % 3] = downArray[((loc - 1) / 3) + 1][(loc - 1) % 3];
			downArray[((loc - 1) / 3) + 1][(loc - 1) % 3] = 0;
			downHCount = heuristicManhatten(downArray, goal, false);
		}

		explored.add(array);
		if (upHCount <= leftHCount && upHCount <= rightHCount && upHCount <= downHCount && isNotExplored(upArray))
			step(Arrays.stream(upArray).map(int[]::clone).toArray(int[][]::new));
		else if (rightHCount <= upHCount && rightHCount <= leftHCount && rightHCount <= downHCount
				&& isNotExplored(rightArray))
			step(Arrays.stream(rightArray).map(int[]::clone).toArray(int[][]::new));
		else if (leftHCount <= upHCount && leftHCount <= rightHCount && leftHCount <= downHCount
				&& isNotExplored(leftArray))
			step(Arrays.stream(leftArray).map(int[]::clone).toArray(int[][]::new));
		else if (downHCount <= upHCount && downHCount <= rightHCount && downHCount <= leftHCount
				&& isNotExplored(downArray))
			step(Arrays.stream(downArray).map(int[]::clone).toArray(int[][]::new));

	}

	public boolean isNotExplored(int[][] array) {
		for (int[][] is : explored) {
			if (Arrays.deepEquals(array, is))
				return false;
		}
		return true;

	}

	public static void main(String[] args) {
		int[][] begening = { { 1, 2, 3 }, { 7, 4, 5 }, { 6, 8, 0 } };
		int[][] goal = { { 1, 2, 3 }, { 8, 6, 4 }, { 7, 5, 0 } };

		AStar1 puzzle = new AStar1(begening, goal);

	}

}
