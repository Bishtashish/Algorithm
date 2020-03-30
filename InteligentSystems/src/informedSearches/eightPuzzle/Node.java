package informedSearches.eightPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * @author Ashish Bisht <abisht1@uncc.edu>
 *
 */
public class Node {

	private int array[][];
	private int goal[][];
	private List<Node> childList;

	public Node(int[][] array, int[][] goal) {
		super();
		this.array = array;
		this.goal = goal;
	}

	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}

	public List<Node> getChildList() {
		return childList;
	}

	public int[][] getGoal() {
		return goal;
	}

	public void setChildList(List<Node> childList) {
		this.childList = childList;
	}

	public Comparator getManhattenComparator() {
		return new ManhattonComarator();
	}
	
	public Comparator getMisplacedComparator(){
		return new MisplacedComparator();
	}

	class ManhattonComarator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub

			return heuristicManhatten(o1.getArray(), goal) - heuristicManhatten(o2.getArray(), goal);
		}

	}
	
	
	class MisplacedComparator implements Comparator<Node>{

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			return heuristicMisplaced(o1.getArray(), goal)-heuristicManhatten(o2.getArray(), goal);
		}
		
	}

	public int heuristicManhatten(int[][] array, int[][] goal) {
		int tempSum = 0;
		// map.clear();
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] != 0 && array[i][j] != goal[i][j]) {
					int temp = findDistance(findLoc(array[i][j], array), findLoc(array[i][j], goal));
					// if (flag)
					// map.put(array[i][j], temp);
					tempSum += temp;
				}
			}
		return tempSum;
	}
	
	
	public int heuristicMisplaced(int[][] array, int[][] goal) {
		int count = 0;

		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if (array[i][j] != 0 && array[i][j] != goal[i][j])
					count++;
		return count;
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

	public void printSolution() {
		for (int i = 0; i < array.length; i++) {
			System.out.println();
			for (int j = 0; j < array[i].length; j++)
				System.out.print(" " + array[i][j]);
		}
	}

	public Collection<? extends Node> generateChildren(ArrayList<int[][]> explored,TreeSet<Node> fringe) {
		List<Node> nodeList = new ArrayList<>();
		int loc = findLoc(0, array);
		// UP swap

				if (loc - 3 > 0) {
//					upHCount = 0;
					int[][] upArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
					upArray[(loc - 1) / 3][(loc - 1) % 3] = upArray[((loc - 1) / 3) - 1][(loc - 1) % 3];
					upArray[((loc - 1) / 3) - 1][(loc - 1) % 3] = 0;
//					upHCount = heuristicManhatten(upArray, goal, false);
					if(isNotExplored(upArray, explored) && isNotInFringe(upArray, fringe))
						nodeList.add(new Node(upArray, goal));
				}
				// LEFT swap

				if ((loc - 1) % 3 > 0) {
//					leftHCount = 0;
					int[][] leftArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
					leftArray[(loc - 1) / 3][(loc - 1) % 3] = leftArray[(loc - 1) / 3][((loc - 1) % 3) - 1];
					leftArray[(loc - 1) / 3][((loc - 1) % 3) - 1] = 0;
//					leftHCount = heuristicManhatten(leftArray, goal, false);
					if(isNotExplored(leftArray, explored) && isNotInFringe(leftArray, fringe))
						nodeList.add(new Node(leftArray, goal));
				}
				// RIGHT swap

				if (loc % 3 != 0) {
//					rightHCount = 0;
					int[][] rightArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
					rightArray[(loc - 1) / 3][(loc - 1) % 3] = rightArray[(loc - 1) / 3][((loc - 1) % 3) + 1];
					rightArray[(loc - 1) / 3][((loc - 1) % 3) + 1] = 0;
//					rightHCount = heuristicManhatten(rightArray, goal, false);
					if(isNotExplored(rightArray, explored) && isNotInFringe(rightArray, fringe))
						nodeList.add(new Node(rightArray, goal));
				}
				// DOWN swap

				if (loc + 3 <= 9) {
//					downHCount = 0;
					int[][] downArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
					
					downArray[(loc - 1) / 3][(loc - 1) % 3] = downArray[((loc - 1) / 3) + 1][(loc - 1) % 3];
					downArray[((loc - 1) / 3) + 1][(loc - 1) % 3] = 0;
//					downHCount = heuristicManhatten(downArray, goal, false);
					if(isNotExplored(downArray, explored) && isNotInFringe(downArray, fringe))
						nodeList.add(new Node(downArray,goal));
				}
				return nodeList;
	}
    public static boolean isNotExplored(int[][] array, ArrayList<int[][]> explored) {
		for (int[][] is : explored) {
			if (Arrays.deepEquals(array, is))
				return false;
		}
		return true;
	}
    
    public static boolean isNotInFringe(int[][] array,TreeSet<Node> fringe){
    	for(Node node : fringe){
    		if(Arrays.deepEquals(array, node.getArray()))
    			return false;
    	}
    	return true;
    }

}
