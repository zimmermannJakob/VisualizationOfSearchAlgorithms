package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SearchAlgorithms {
	/*
	 * Color coding for animation: blue: current light_blue: every prior current
	 * grey: added to search space (queue/stack/...)
	 */
	private static final Color currentColor = new Color(14, 26, 126);
	private static final Color visitedColor = new Color(130, 150, 186);
	private static final Color searchSpaceExpansionColor = Color.LIGHT_GRAY;
	private static final Color pathColor = Color.yellow;

	private static final double cellCostMultiplicator = 1;

	public static Queue<AnimationInstruction> bfs(Grid grid, GridCell startCell, GridCell endCell) {
		int traversedCells = 0;

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
			traversedCells++;

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw,null ));
				animationQueue.addAll(printPath(endCell, "The end cell has been found! \n\nTaversed Cells: "+traversedCells));
				return animationQueue;
			}

			// getting adjacent cells which have not been visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {
					bfsQueue.add(neighbor);
					visited.add(neighbor);
					neighbor.setPriorVisitedCell(current);
					
					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached \n\nTaversedCells: "+traversedCells));
		return animationQueue;
	}

	public static Queue<AnimationInstruction> dfs(Grid grid, GridCell startCell, GridCell endCell) {
		int traversedCells = 0;

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
			traversedCells++;

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, null));
				animationQueue.addAll(printPath(endCell, "The end cell has been found! \n\nTaversed Cells: "+traversedCells));
				return animationQueue;
			}

			// getting adjacent cells which have not been visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {
					dfsStack.push(neighbor);
					visited.add(neighbor);
					neighbor.setPriorVisitedCell(current);

					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached \n\nTaversedCells: "+traversedCells));
		return animationQueue;
	}

	public static Queue<AnimationInstruction> aStar(Grid grid, GridCell startCell, GridCell endCell) {
		int traversedCells = 0;
		
		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();
		PriorityQueue<PQueueEntry> PQueue = new PriorityQueue<PQueueEntry>();
		PQueue.add(new PQueueEntry(startCell, 0));

		ArrayList<GridCell> visited = new ArrayList<GridCell>();
		GridCell priorCurrent = null;
		GridCell current = null;
		GridCell tmp;

		while (!PQueue.isEmpty()) {
			ArrayList<GridCell> cellsToDraw = new ArrayList<GridCell>();

			if (priorCurrent != null) {

				priorCurrent.setColor(visitedColor);
				cellsToDraw.add(priorCurrent);
			}

			// new AnimationStep
			current = PQueue.poll().getCell();
			traversedCells++;

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, null));
				animationQueue.addAll(printPath(endCell, "The end cell has been found! \n\nTaversed Cells: "+traversedCells));
				return animationQueue;
			}

			// getting adjacent cells which have not been visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {

					neighbor.setCostToThisCell(current.getCostToThisCell() + 1);
					neighbor.setPriorVisitedCell(current);

					PQueue.add(new PQueueEntry(neighbor, getHeuristicValue(neighbor, endCell)
							+ (neighbor.getCostToThisCell() * cellCostMultiplicator)));
					visited.add(neighbor);

					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached \n\nTaversedCells: "+traversedCells));
		return animationQueue;
	}
	
	public static Queue<AnimationInstruction> bestFirstSearch(Grid grid, GridCell startCell, GridCell endCell) {
		int traversedCells = 0;
		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();
		PriorityQueue<PQueueEntry> PQueue = new PriorityQueue<PQueueEntry>();
		PQueue.add(new PQueueEntry(startCell, 0));

		ArrayList<GridCell> visited = new ArrayList<GridCell>();
		GridCell priorCurrent = null;
		GridCell current = null;
		GridCell tmp;

		while (!PQueue.isEmpty()) {
			ArrayList<GridCell> cellsToDraw = new ArrayList<GridCell>();

			if (priorCurrent != null) {

				priorCurrent.setColor(visitedColor);
				cellsToDraw.add(priorCurrent);
			}

			// new AnimationStep
			current = PQueue.poll().getCell();
			traversedCells++;

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, null));
				animationQueue.addAll(printPath(endCell, "The end cell has been found! \n\nTaversed Cells: "+traversedCells));
				return animationQueue;
			}

			// getting adjacent cells which have not been visited yet
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {

					neighbor.setCostToThisCell(current.getCostToThisCell() + 1);
					neighbor.setPriorVisitedCell(current);

					PQueue.add(new PQueueEntry(neighbor, getHeuristicValue(neighbor, endCell)));
					visited.add(neighbor);

					tmp = new GridCell(neighbor.getX(), neighbor.getY());
					tmp.setColor(searchSpaceExpansionColor);
					cellsToDraw.add(tmp);

				}
			}
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached \n\nTaversedCells: "+traversedCells));
		return animationQueue;
	}
	
	public static Queue<AnimationInstruction> heuristicDepthFirstSearch(Grid grid, GridCell startCell, GridCell endCell) {
		int traversedCells = 0;
		
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
			traversedCells++;

			visited.add(current);
			// test
			tmp = new GridCell(current.getX(), current.getY());
			tmp.setColor(currentColor);
			cellsToDraw.add(tmp);

			if (current == endCell) {
				animationQueue.add(new AnimationInstruction(cellsToDraw, null));
				animationQueue.addAll(printPath(endCell, "The end cell has been found! \n\nTaversed Cells: "+traversedCells));
				return animationQueue;
			}

			// getting adjacent cells which have not been visited yet
			
			PriorityQueue<PQueueEntry> localMinHeuristic = new PriorityQueue<PQueueEntry>();
			for (GridCell neighbor : grid.getNeighbors(current)) {

				if (!(visited.contains(neighbor))) {
					
					localMinHeuristic.add(new PQueueEntry(neighbor, -getHeuristicValue(neighbor, endCell)));
					visited.add(neighbor);
					neighbor.setPriorVisitedCell(current);

				}
			}
			
			GridCell neighbor;
			while(!localMinHeuristic.isEmpty()) {
				
				neighbor = localMinHeuristic.poll().getCell();
				
				dfsStack.add(neighbor);
				
				tmp = new GridCell(neighbor.getX(), neighbor.getY());
				tmp.setColor(searchSpaceExpansionColor);
				cellsToDraw.add(tmp);
			}
			
			priorCurrent = new GridCell(current.getX(), current.getY());
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
		}
		animationQueue.add(new AnimationInstruction(null, "The end could not be reached \n\nTaversedCells: "+traversedCells));
		return animationQueue;
	}

	static final double getHeuristicValue(GridCell currentCell, GridCell endCell) {
		//sqrt(Dx^2 + Dy^2) D: delta
		return (Math.sqrt(
				Math.pow(endCell.getX() - currentCell.getX(), 2) + Math.pow(endCell.getY() - currentCell.getY(), 2)));
	}

	static final Queue<AnimationInstruction> printPath(GridCell endCell, String message) {
		int pathLength = 0;
		
		Queue<AnimationInstruction> animationQueue = new LinkedList<AnimationInstruction>();

		GridCell tmp;

		GridCell currentCell = endCell;
		ArrayList<GridCell> cellsToDraw;
		while (currentCell.getPriorVisitedCell() != null) {
			cellsToDraw = new ArrayList<GridCell>();

			tmp = new GridCell(currentCell.getX(), currentCell.getY());
			tmp.setColor(pathColor);
			cellsToDraw.add(tmp);
			pathLength++;
			animationQueue.add(new AnimationInstruction(cellsToDraw, null));
			currentCell = currentCell.getPriorVisitedCell();
		}
		cellsToDraw = new ArrayList<GridCell>();
		tmp = new GridCell(currentCell.getX(), currentCell.getY());
		tmp.setColor(pathColor);
		cellsToDraw.add(tmp);
		animationQueue.add(new AnimationInstruction(cellsToDraw, message + "\nPathlength: "+pathLength));

		return animationQueue;
	}

}
