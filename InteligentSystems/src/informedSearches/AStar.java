package informedSearches;

import java.util.Map;
import java.util.TreeMap;

public class AStar {

	private int goal[][];
	private int array[][];
	private Map<Integer, Integer> map = new TreeMap<Integer, Integer>();

	AStar(int array[][], int goal[][]) {
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) {
				if(array[i][j] != goal[i][j]){				
				int temp = findDistance(findLoc(array[i][j], array), findLoc(array[i][j], goal));
				map.put(array[i][j], temp);
				}
			}
		this.array = array;
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

//	public int findDistance(int i, int j) {
//		if (i > j)
//			if (i % 3 > j % 3)
//				return (i / 3) - (j / 3) + (i % 3) - (j % 3);
//
//			else
//				return (i / 3) - (j / 3) + (j % 3) - (i % 3);
//		else if (i % 3 > j % 3)
//			return (j / 3) - (i / 3) + (i % 3) - (j % 3);
//
//		else
//			return (j / 3) - (i / 3) + (j % 3) - (i % 3);
//
//	}
	
	
	
	public int findDistance(int i, int j) {
		//H diff
		int hDiff;
		hDiff=i%3-Math.abs(j%3);
		
		//V diff
		int vDiff;
		vDiff=i/3-Math.abs(j/3);
		
		vDiff= vDiff==0? 1:vDiff;
		hDiff= hDiff==0? 1:hDiff;
		
		return Math.abs(vDiff*hDiff);

	}
	
	
	

	public int heuristicMisplaced(int[][] array, int[][] goal) {
		int count = 0;

		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if (array[i][j] != goal[i][j])
					count++;
		return count;
	}

	public int heuristicManhatten(int[][] array, int[][] goal) {
		int tempSum = 0;
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] != goal[i][j]) {
					int temp = findDistance(findLoc(array[i][j], array), findLoc(array[i][j], goal));
					map.put(array[i][j], temp);
					tempSum += temp;
				}
			}
		return tempSum;
	}

	public void step(int[][] array) {
		int loc;
		// Map<Integer, Integer> upMap = map;
		// Map<Integer, Integer> leftMap = map;
		// Map<Integer, Integer> rightMap = map;
		// Map<Integer, Integer> downMap = map;
		int[][] upArray = array;
		int[][] leftArray = array;
		int[][] rightArray = array;
		int[][] downArray = array;
		int upHCount = 0, downHCount = 0, leftHCount = 0, rightHCount = 0;

		for (Map.Entry s : map.entrySet()) {
			System.out.println(s.getKey() + " " + s.getValue());
		}

		if (heuristicManhatten(array, goal) == 0)
			return;
		else
			loc = findLoc(0, array);
		// UP swap
		// if(loc-3>0)
		// upMap.put(loc,upMap.get(loc-3));
		// upMap.put(loc-3, 0);

		if (loc - 3 > 0) {
			upArray[loc / 3][(loc % 3) - 1] = upArray[(loc / 3) - 1][(loc % 3) - 1];
			upArray[(loc / 3) - 1][(loc % 3) - 1] = 0;
			upHCount = heuristicManhatten(upArray, goal);

		}

		// LEFT swap
		if ((loc - 1) % 3 > 0) {
			leftArray[loc / 3][(loc - 1) % 3] = leftArray[loc / 3][((loc - 1) % 3) - 1];
			leftArray[loc / 3][((loc - 1) % 3) - 1] = 0;
			leftHCount = heuristicManhatten(leftArray, goal);
		}
		// RIGHT swap
		if (loc % 3 != 0) {
			rightArray[loc / 3][(loc - 1) % 3] = rightArray[loc / 3][(loc + 1) / 3];
			rightArray[loc / 3][(loc + 1) / 3] = 0;
			rightHCount = heuristicManhatten(rightArray, goal);
		}
		// DOWN swap
		if (loc + 3 > 0) {
			downArray[loc % 3][loc / 3] = downArray[(loc % 3) + 1][loc / 3];
			downArray[(loc % 3) + 1][loc / 3] = 0;
			downHCount = heuristicManhatten(downArray, goal);
		}

		if (upHCount < leftHCount && upHCount < rightHCount && upHCount < downHCount)
			step(upArray);
		else if (leftHCount < upHCount && leftHCount < rightHCount && leftHCount < downHCount)
			step(leftArray);
		else if (rightHCount < upHCount && rightHCount < leftHCount && rightHCount < downHCount)
			step(rightArray);
		else
			step(downArray);

	}

	public static void main(String[] args) {
		int[][] begening = { { 1, 2, 3 },
							 { 7, 4, 5 },
							 { 6, 8, 0 } };
		int[][] goal = { { 1, 2, 3 },
						 { 8, 6, 4 },
						 { 7, 5, 0 } };

		AStar puzzle = new AStar(begening, goal);
		
	}

}