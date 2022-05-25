package model;

import java.util.ArrayList;

public class Grid {
	private GridCell grid[][];
	private GridCell startCell;
	private GridCell endCell;
	private int size;
	
	Grid(int size){
		this.size = size;
		this.grid = new GridCell[size][size];
		for(int x = 0; x<size; x++) {
			for(int y = 0; y<size; y++) {
				grid[x][y] = new GridCell(x,y);
			}
		}
	}
	
	public ArrayList<GridCell> getNeighbors(GridCell cell){
		ArrayList<GridCell> neighborsOfCell = new ArrayList<GridCell>();
		int x = cell.getX();
		int y = cell.getY();
		
		if(x<size-1) {
			neighborsOfCell.add(grid[x+1][y]);
		}
		if(y<size-1) {
			neighborsOfCell.add(grid[x][y+1]);
		}
		if(x>0) {
			neighborsOfCell.add(grid[x-1][y]);
		}
		if(y>0) {
			neighborsOfCell.add(grid[x][y-1]);
		}
		
		return neighborsOfCell;
	}
	
	public void updateCells(ArrayList<GridCell> cellsToUpdate) {
		for(GridCell currentCell: cellsToUpdate) {
			grid[currentCell.getX()][currentCell.getY()] = currentCell;
		}
	}

	public GridCell getStartCell() {
		return startCell;
	}

	public GridCell getEndCell() {
		return endCell;
	}
	
}
