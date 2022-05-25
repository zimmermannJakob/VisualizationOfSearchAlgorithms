package model;

import java.util.ArrayList;

public class GridRepresentation {
	Grid grid;
	int size;
	
	public GridRepresentation(int size) {
		this.size = size;
		this.grid = new Grid(size);
	}
	
	public void updateGridRepresentation(int size, ArrayList<GridCell> cellsToUpdate){
		if(this.size == size) {
			//only cells need to be updated
			this.grid.updateCells(cellsToUpdate);
			//TODO MainWindow.updateGrid()
			return;
		}
		if(this.size != size) {
			this.grid = new Grid(size);
			//TODO MainWindow.updateGrid()
			return;
		}
	}
}
