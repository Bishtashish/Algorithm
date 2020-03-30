package informedSearches.nQueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.TreeSet;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import informedSearches.nQueen.Node;

public class nQueen {

	/**
	 * @author Ashish Bisht <abisht1@uncc.edu>
	 *
	 */
	
//  For  Experimentation Only
	private static int runHillClimb1(Node rootNode) {

		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getHillClimbComparator());
		fringe.add(rootNode);
		while (!fringe.isEmpty()) {
			Node node = fringe.pollFirst();
			node.printSolution();
			System.out.println("\t Heuristic Hill Climb: " + Node.heuristicHillClimb(rootNode.getArray()));
			int attack = Node.heuristicHillClimb(node.getArray());
			outerLoop: if (attack == 0) {
				System.out.println("Success");
				return 0;
			} else
				for (int row = 0; row < node.getArray().length; row++)
					for (int column = 0; column < node.getArray()[row].length; column++)
						if (node.getArray()[row][column] == 1) {
							ArrayList<Node> childList = (ArrayList) node.generateNeighboursBasic(row, column);
							Collections.sort(childList, node.getHillClimbComparator());
							if (!childList.isEmpty() && Node.heuristicHillClimb(childList.get(0).getArray()) < attack) {
								fringe.clear();
								fringe.add(childList.get(0));
								break outerLoop;
							}
						}
		}
		System.out.println("No Solution can be found.");
		return -1;
	}

	private static int runHillClimb(Node rootNode) {

		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getHillClimbComparator());
		fringe.add(rootNode);
		while (!fringe.isEmpty()) {
			Node node = fringe.pollFirst();
			node.printSolution();
			int attack = Node.heuristicHillClimb(node.getArray());
			System.out.println("\t Heuristic Hill Climb: " + attack);
			// outerLoop:
			outerLoop: if (attack == 0) {
				System.out.println("Success");
				return 0;
			} else
				for (int row = 0; row < node.getArray().length; row++)
					for (int column = 0; column < node.getArray()[row].length; column++)
						if (node.getArray()[column][row] == 1) {
							Node maxChild = null;
							for (Node child : node.generateNeighboursBasic(column, row)) {
								maxChild = Node.heuristicHillClimb(child.getArray()) < attack ? child : maxChild;
								attack = Node.heuristicHillClimb(child.getArray());
							}
							if (attack >= Node.heuristicHillClimb(node.getArray()))
								break;
							fringe.add(maxChild);
							break outerLoop;
						}
		}
		System.out.println("No Solution can be found.");
		return -1;
	}
//  For  Experimentation Only
	private static int runHillClimbSideways1(Node rootNode, int sideLimit) {

		// ---------------------------------------------------------------------------------------------------------

		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getHillClimbComparator());
		fringe.add(rootNode);
		while (!fringe.isEmpty()) {
			Node node = fringe.pollFirst();
			node.printSolution();
			int attack = Node.heuristicHillClimb(node.getArray());
			System.out.println("\t Heuristic Hill Climb: " + attack);
			outerLoop: if (attack == 0) {
				System.out.println("Success");
				return 0;
			} else
				for (int row = 0; row < node.getArray().length; row++)
					for (int column = 0; column < node.getArray()[row].length; column++)
						if (node.getArray()[column][row] == 1) {
							Node maxChild = null;
							for (Node child : node.generateNeighboursBasic(column, row)) {
								maxChild = Node.heuristicHillClimb(child.getArray()) < attack ? child : maxChild;
								attack = Node.heuristicHillClimb(child.getArray());
							}
							if (attack >= Node.heuristicHillClimb(node.getArray())) {
								if (sideLimit == 0)
									break;
								int val = 0;
								Node maxSideChild = null;
								for (Node sideChild : node.generateNeighboursBasic(column, row)) {
									val = runHillClimbSideways1(sideChild, sideLimit - 1);
									if (val < attack) {
										attack = val;
										maxSideChild = sideChild;
									}
								}
								if (attack >= Node.heuristicHillClimb(node.getArray()))
									break;
								fringe.add(maxSideChild);
								break outerLoop;
							}
						}
		}
		System.out.println("No Solution can be found.");
		return -1;
	}
//   Used in Final 
	private static int runHillClimbSideways(Node rootNode, int sideLimit) {

		// ---------------------------------------------------------------------------------------------------------

		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getHillClimbComparator());
		fringe.add(rootNode);
		while (!fringe.isEmpty()) {
			Node node = fringe.pollFirst();
			node.printSolution();
			int attack = Node.heuristicHillClimb(node.getArray());
			System.out.println("\t Heuristic Hill Climb: " + attack);
			outerLoop: if (attack == 0) {
				System.out.println("Success");
				return 0;
			} else
				for (int row = 0; row < node.getArray().length; row++)
					for (int column = 0; column < node.getArray()[row].length; column++)
						if (node.getArray()[column][row] == 1) {
							Node maxChild = null;
							for (Node child : node.generateNeighboursBasic(column, row)) {
								maxChild = Node.heuristicHillClimb(child.getArray()) < attack ? child : maxChild;
								attack = Node.heuristicHillClimb(child.getArray());
							}
							if (attack >= Node.heuristicHillClimb(node.getArray())) {
								if (sideLimit == 0)
									break;
								for (Node sideChild : node.generateNeighboursSideways(column, row)) {
									maxChild = Node.heuristicHillClimb(sideChild.getArray()) < attack ? sideChild
											: maxChild;
									attack = Node.heuristicHillClimb(sideChild.getArray());
									sideLimit--;
								}
							}
							if (attack >= Node.heuristicHillClimb(node.getArray()))
								break;
							fringe.add(maxChild);
							break outerLoop;
						}
		}
		System.out.println("No Solution can be found.");
		return -1;
	}
//     For  Experimentation Only
	private static int runHillClimbSideways2(Node rootNode, int sideLimit) {

		// ---------------------------------------------------------------------------------------------------------

		TreeSet<Node> fringe = new TreeSet<Node>(rootNode.getHillClimbComparator());
		fringe.add(rootNode);
		while (!fringe.isEmpty()) {
			Node node = fringe.pollFirst();
			node.printSolution();
			int attack = Node.heuristicHillClimb(node.getArray());
			System.out.println("\t Heuristic Hill Climb: " + attack);
			outerLoop: if (attack == 0) {
				System.out.println("Success");
				return 0;
			} else
				for (int row = 0; row < node.getArray().length; row++)
					for (int column = 0; column < node.getArray()[row].length; column++)
						if (node.getArray()[column][row] == 1) {
							ArrayList<Node> childList = (ArrayList) node.generateNeighboursBasic(row, column);
							Collections.sort(childList, node.getHillClimbComparator());
							if (!childList.isEmpty() && Node.heuristicHillClimb(childList.get(0).getArray()) < attack) {
								fringe.clear();
								fringe.add(childList.get(0));
								break outerLoop;
							} else {
								if (sideLimit == 0)
									return attack;
								int val = 0;
								for (Node side : node.generateNeighboursSideways(row, column)) {
									val = runHillClimbSideways(side, sideLimit - 1);
									if (val < attack)
										attack = val;
								}
							}
						}
		}
		System.out.println("No Solution can be found.");
		// System.exit(-1);
		return -1;
		// ------------------------------------------------------------------------------------------------------------------------

	}

	public static void runHillClimbRandomRestart(int queens) {
		int flag = -1;

		while (flag == -1) {
			int[][] board = new int[queens][queens];
			Arrays.stream(board).forEach(a -> Arrays.fill(a, 0));
			for (int i = 0; i < queens; i++)
				board[new Random().nextInt(queens)][i] = 1;
			Node node = new Node(board, queens);
			flag = runHillClimb(node);
		}
	}

	public static void runHillClimbSidewaysRandomRestart(int queens, int sidewayMoves) {
		int flag = -1;

		while (flag == -1) {
			int[][] board = new int[queens][queens];
			Arrays.stream(board).forEach(a -> Arrays.fill(a, 0));
			for (int i = 0; i < queens; i++)
				board[new Random().nextInt(queens)][i] = 1;
			Node node = new Node(board, queens);
			flag = runHillClimbSideways(node, sidewayMoves);
		}
	}

	public static void main(String[] args) {
		int queens = 8;
		int[][] board = new int[queens][queens];
		// Arrays.fill(board,);
		Arrays.stream(board).forEach(a -> Arrays.fill(a, 0));
		for (int i = 0; i < queens; i++) {
			board[new Random().nextInt(queens)][i] = 1;
		}
		Node node = new Node(board, queens);
		// runHillClimb1(node);
		// runHillClimbRandomRestart(8);
//		runHillClimbSideways(node, 100);
		 runHillClimbSidewaysRandomRestart(8, 100);
	}

}
