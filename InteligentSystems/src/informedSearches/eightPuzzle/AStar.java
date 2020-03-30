package informedSearches.eightPuzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

/**
 * This class contains a skeleton implementation of the A* algorithm. Its single
 * static method {@link #run} accepts the root node and runs the A* algorithm
 * using that node's methods to generate children, evaluate heuristics, etc.
 * This way, plugging in root nodes of different types, we can run this A* to
 * solve different problems.
 */

/**
 * @author Ashish Bisht <abisht1@uncc.edu>
 *
 */
public class AStar {
	static ArrayList<int[][]> explored = new ArrayList<>();

	/**
	 * Runs the A* algorithm given the root node. The class of the root node
	 * defines the problem that's being solved. The algorithm either prints out
	 * the solution (again, the format depends on the class of the root node) or
	 * says that there's no solution.
	 */
	/**
	 * @param rootNode
	 */
	public static void run(Node rootNode) {
		// TODO: add your code here

		/*
		 * Implementation notes:
		 * 
		 * We need our fringe to be some container capable of storing objects of
		 * class Node. Furthermore, the container should be ordered and should
		 * have an operation of extracting the Node with the smallest g+h value.
		 * There's a class in Java library that suits our needs - TreeSet. So we
		 * create a TreeSet and provide it with the node's comparator like this:
		 * 
		 * TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getComparator());
		 * 
		 * Useful operations with the fringe:
		 * 
		 * fringe.isEmpty() returns true if there're no nodes in the fringe
		 * 
		 * fringe.add(Node node) will add the node to the fringe
		 * 
		 * fringe.add(List<Node> nodes) will add all the nodes from the list to
		 * the fringe
		 * 
		 * fringe.first() will return the node with the smallest g+h value
		 * currently in the fringe, without removing the node from the fringe
		 * 
		 * fringe.remove() will remove the specified node from the fringe
		 * 
		 * General algorithm: 1. Create an empty fringe, as above 2. Put the
		 * root search node to the fringe 3. While fringe is not empty: 4. Get
		 * the first search node from the fringe 5. Remove that node from the
		 * fringe 6. If that's a goal node, print the solution using
		 * node.printSolution() 7. Otherwise, generate the successor nodes using
		 * node.generateChildren 8. Add all the generated nodes to the fringe 9.
		 * If no solution was found, print "No solution found."
		 */

//		 TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getManhattenComparator());
		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getMisplacedComparator());

		fringe.add(rootNode);
		while (!fringe.isEmpty()) {

			// Node node = fringe.first();
			// fringe.remove(node);
			Node node = fringe.pollFirst();
			explored.add(Arrays.stream(node.getArray()).map(int[]::clone).toArray(int[][]::new));
			node.printSolution();
//			 System.out.println("\t Heuristic Manhatten: " +node.heuristicManhatten(node.getArray(), node.getGoal()));
			System.out.println("\t Heuristic Misplaced: " + node.heuristicMisplaced(node.getArray(), node.getGoal()));

			if (Arrays.deepEquals(node.getArray(), node.getGoal())) {
				System.out
						.println("Number of Nodes generated: " + (explored.size()+fringe.size())+ " Nodes Explored: " + explored.size());
				return;
			} else
				fringe.addAll(node.generateChildren(explored, fringe));

		}
		System.out.println("No Solution can be found");

	}

	public static void main(String[] args) {
//		 int[][] begening = { { 1, 2, 3 }, { 7, 4, 5 }, { 6, 8, 0 } };
//		 int[][] goal = { { 1, 2, 3 }, { 8, 6, 4 }, { 7, 5, 0 } };

//		 int[][] begening = { { 2, 8, 1 }, { 3, 4, 6 }, { 7, 5, 0 } };
//		 int[][] goal = { { 3, 2, 1 }, { 8, 0, 4 }, { 7, 5, 6 } };

//		int[][] begening = { { 7, 2, 4 }, { 5, 0, 6 }, { 8, 3, 1 } };
//		int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		
//		int[][] begening = {  { 4, 0, 6 }, { 7, 2, 5 }, { 8, 3, 1 } };
//		int[][] goal = { { 4, 5, 6 }, { 1, 2, 3 },  { 7, 8, 0 } };
		
//		int[][] begening = {  { 4, 1, 7 }, { 6, 2, 5 }, { 8, 3, 0 } };
//		int[][] goal = { { 1, 2, 3 }, { 4, 5, 6 },  { 7, 8, 0 } };
		
//		int[][] begening = {  { 4, 1, 7 }, { 6, 2, 5 }, { 8, 3, 0 } };
//		int[][] goal = { { 1, 3, 0 }, { 4, 5, 6 },  { 7, 8, 2 } };
		
		
		int[][] begening = {  { 2, 8, 3 }, { 1, 6, 4 }, { 7, 0, 5 } };
		int[][] goal = { { 1, 2, 3 }, { 8, 0, 4 },  { 7, 6, 5 } };
		

		Node node = new Node(begening, goal);
		run(node);
	}

};