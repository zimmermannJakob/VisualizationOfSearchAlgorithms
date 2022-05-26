package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class SearchAlgorithms {
	/*
	 * Color coding for animation: blue: current light_blue: every prior current
	 * grey: added to search space (queue/stack/...)
	 */

	public static Queue<AnimationInstruction> BFS(Grid grid, GridCell startCell, GridCell endCell) {

		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();
		Queue<GridCell> BFSQueue = new LinkedList<GridCell>();
		BFSQueue.add(startCell);

		ArrayList<GridCell> visited = new ArrayList<GridCell>();
		GridCell priorCurrent = null;
		GridCell current = null;

		while (!BFSQueue.isEmpty()) {
			ArrayList<GridCell> cellsToDraw = new ArrayList<GridCell>();

			if (priorCurrent != null) {
				priorCurrent.setColor(Color.blue.brighter());
			}

			// new AnimationStep
			current = BFSQueue.poll();
			visited.add(current);
			current.setColor(Color.blue);
			cellsToDraw.add(current);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, "The end cell has been found! :)"));
			}

			// getting adjacent cells which have been not visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {

					BFSQueue.add(neighbor);
					neighbor.setColor(Color.LIGHT_GRAY);
					cellsToDraw.add(neighbor);
				}
			}
			priorCurrent = current;
			animationQueue.add(new AnimationInstruction(cellsToDraw, ""));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached :("));
		return animationQueue;
	}
}
