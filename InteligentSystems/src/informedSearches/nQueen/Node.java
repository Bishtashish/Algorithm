package informedSearches.nQueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @author Ashish Bisht <abisht1@uncc.edu>
 *
 */
public class Node {
	private int array[][];
	private int queen;
	public int getQueen() {
		return queen;
	}


	private List<Node> neighbours;

	public Node(int[][] array, int queen) {

		this.array = array;
		this.queen = queen;
	}

	public int[][] getArray() {
		return array;
	}

	public void setArray(int[][] array) {
		this.array = array;
	}

	public List<Node> getNeighbourList() {
		return neighbours;
	}

	public void setNeighboursList(List<Node> neighbours) {
		this.neighbours = neighbours;
	}

	public Comparator getHillClimbComparator() {
		return new HillClimbComparator();
	}


	class HillClimbComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			// TODO Auto-generated method stub
			return heuristicHillClimb(o1.getArray()) - heuristicHillClimb(o2.getArray());
		}

	}
	

	public int findLoc(int n, int[][] array) {
		for (int i = 0; i < array.length; i++)
			for (int j = 0; j < array[i].length; j++)
				if (n == array[i][j])
					return (i * queen) + (j + 1);
		return 0;
	}
	
	public int findLoc(int row, int column){
		return (row*queen)+column+1;
	}

	public static int heuristicHillClimb(int[][] array) {
		int attack = 0;
		List<int[]> queens = new ArrayList<>();

		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length; j++) {
				if (array[i][j] == 1) {

					int[] temp = { i, j };
					queens.add(temp);
				}
			}
		}
		
		
		for(int i=0;i<queens.size();i++)
			for(int j=i+1;j<queens.size();j++)
			{
			if(queens.get(i)[0]==queens.get(j)[0] || queens.get(i)[1]==queens.get(j)[1])
				attack++;
			if(Math.abs(queens.get(i)[0]-queens.get(j)[0])==Math.abs(queens.get(i)[1]-queens.get(j)[1]))
				attack++;
			
			
			}



		return attack;
	}

	

	public Collection<? extends Node> generateNeighboursSideways(int row, int column) {
		List<Node> nodeList = new ArrayList();
		int loc = findLoc(row, column);


		// Left
		if ((loc - 1) % queen > 0) {
			int[][] leftArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			leftArray[(loc - 1) / queen][(loc - 1) % queen] = leftArray[(loc - 1) / queen][((loc - 1) % queen) - 1];
			leftArray[(loc - 1) / queen][((loc - 1) % queen) - 1] = 1;
			nodeList.add(new Node(leftArray,queen));
		}

		// Right

		if (loc % queen != 0) {
			int[][] rightArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			rightArray[(loc - 1) / queen][(loc - 1) % queen] = rightArray[(loc - 1) / queen][((loc - 1) % queen) + 1];
			rightArray[(loc - 1) / queen][((loc - 1) % queen) + 1] = 1;
			nodeList.add(new Node(rightArray,queen));
		}



		return nodeList;

	}
	
	
	
	
	public Collection<? extends Node> generateNeighboursSidewaysColumnSwap(int row, int column) {
		List<Node> nodeList = new ArrayList();
		int loc = findLoc(row, column);

		// Left
		if ((loc - 1) % queen > 0) {
			int[][] leftArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			
			for(int i=0; i<queen;i++){
				int temp=leftArray[i][(loc - 1) % queen];
				leftArray[i][(loc - 1) % queen] = leftArray[i][((loc - 1) % queen) - 1];
				leftArray[i][((loc - 1) % queen) - 1] = temp;				
			}						
			nodeList.add(new Node(leftArray,queen));
		}

		// Right

		if (loc % queen != 0) {
			int[][] rightArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			
			for( int i=0;i<queen;i++){
				int temp = rightArray[i][(loc - 1) % queen];
				rightArray[i][(loc - 1) % queen] = rightArray[i][((loc - 1) % queen) + 1];
				rightArray[i][((loc - 1) % queen) + 1] = 1;
			}
			nodeList.add(new Node(rightArray,queen));
		}



		return nodeList;

	}
	
	public void printSolution() {
		for (int i = 0; i < array.length; i++) {
			System.out.println();
			for (int j = 0; j < array[i].length; j++)
				System.out.print(" " + array[i][j]);
		}
	}
	
	
	public Collection<? extends Node> generateNeighboursBasic(int row, int column) {
		List<Node> nodeList = new ArrayList();
		int loc = findLoc(row, column);

		// Up
		if (loc - queen > 0) {
			int[][] upArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			upArray[(loc - 1) / queen][(loc - 1) % queen] = upArray[((loc - 1) / queen) - 1][(loc - 1) % queen];
			upArray[((loc - 1) / queen) - 1][(loc - 1) % queen] = 1;
			
			
			nodeList.add(new Node(upArray,queen));
		}

		// Left
//		if ((loc - 1) % queen > 0) {
//			int[][] leftArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
//			leftArray[(loc - 1) / queen][(loc - 1) % queen] = leftArray[(loc - 1) / queen][((loc - 1) % queen) - 1];
//			leftArray[(loc - 1) / queen][((loc - 1) % queen) - 1] = 0;
//			nodeList.add(new Node(leftArray));
//		}

		// Right
//		if (loc % queen != 0) {
//			int[][] rightArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
//			rightArray[(loc - 1) / queen][(loc - 1) % queen] = rightArray[(loc - 1) / queen][((loc - 1) % queen) + 1];
//			rightArray[(loc - 1) / queen][((loc - 1) % queen) + 1] = 0;
//			nodeList.add(new Node(rightArray));
//		}

		// Down
		if (loc + queen <= (queen*queen)) {
			int[][] downArray = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
			downArray[(loc - 1) / queen][(loc - 1) % queen] = downArray[((loc - 1) / queen) + 1][(loc - 1) % queen];
			downArray[((loc - 1) / queen) + 1][(loc - 1) % queen] = 1;
			nodeList.add(new Node(downArray,queen));
		}

		return nodeList;

	}
	
	
}
