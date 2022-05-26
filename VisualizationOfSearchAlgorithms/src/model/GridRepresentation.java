package model;

import java.util.ArrayList;

import view.MainWindow;

public class GridRepresentation {
	Grid grid;
	int size;
	private MainWindow mainWindow;

	public GridRepresentation(int size, MainWindow mainWindow) {
		this.size = size;
		this.grid = new Grid(size);
		this.mainWindow = mainWindow;
		mainWindow.setNewGrid(grid, size);
	}

	public void updateGridRepresentation(int size, ArrayList<GridCell> cellsToUpdate) {
		if (this.size == size) {
			// only cells need to be updated
			this.grid.updateCells(cellsToUpdate);
			mainWindow.drawGridCells(cellsToUpdate);
			return;
		}
		if (this.size != size) {
			this.grid = new Grid(size);
			mainWindow.drawGridCells(cellsToUpdate);
			return;
		}
	}

	public Grid getGridForAnimation() {
		return this.grid;
	}
}
