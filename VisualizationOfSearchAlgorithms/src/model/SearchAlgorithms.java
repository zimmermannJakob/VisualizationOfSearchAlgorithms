package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class SearchAlgorithms {
	/*
	 * Color coding for animation: blue: current light_blue: every prior current
	 * grey: added to search space (queue/stack/...)
	 */
	private static final Color currentColor = new Color(14,26,126);
	private static final Color visitedColor = new Color(130,150,186);
	private static final Color searchSpaceExpansionColor = Color.LIGHT_GRAY;
	private static final Color pathColor = Color.yellow;

	public static Queue<AnimationInstruction> BFS(Grid grid, GridCell startCell, GridCell endCell) {

		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();
		Queue<GridCell> bfsQueue = new LinkedList<GridCell>();
		bfsQueue.add(startCell);

		ArrayList<GridCell> visited = new ArrayList<GridCell>();
		GridCell priorCurrent = null;
		GridCell current = null;
		GridCell tmp;
		// start of the search
		while (!bfsQueue.isEmpty()) {
			ArrayList<GridCell> cellsToDraw = new ArrayList<GridCell>();

			if (priorCurrent != null) {

				priorCurrent.setColor(visitedColor);
				cellsToDraw.add(priorCurrent);
			}

			// new AnimationStep
			current = bfsQueue.poll();

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, "The end cell has been found! :)"));
				return animationQueue;
			}

			// getting adjacent cells which have been not visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {
					bfsQueue.add(neighbor);
					visited.add(neighbor);

					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached :("));
		return animationQueue;
	}
	
	public static Queue<AnimationInstruction> DFS(Grid grid, GridCell startCell, GridCell endCell) {

		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();
		Stack<GridCell> dfsStack = new Stack<GridCell>();
		dfsStack.push(startCell);

		ArrayList<GridCell> visited = new ArrayList<GridCell>();
		GridCell priorCurrent = null;
		GridCell current = null;
		GridCell tmp;
		// start of the search
		while (!dfsStack.isEmpty()) {
			ArrayList<GridCell> cellsToDraw = new ArrayList<GridCell>();

			if (priorCurrent != null) {

				priorCurrent.setColor(visitedColor);
				cellsToDraw.add(priorCurrent);
			}

			// new AnimationStep
			current = dfsStack.pop();

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, "The end cell has been found! :)"));
				return animationQueue;
			}

			// getting adjacent cells which have been not visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {
					dfsStack.push(neighbor);
					visited.add(neighbor);

					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached :("));
		return animationQueue;
	}
	
}
