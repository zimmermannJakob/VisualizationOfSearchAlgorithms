package controller;

import java.util.ArrayList;

import model.Grid;
import model.GridCell;
import model.GridRepresentation;
import view.MainWindow;

public class Controller {
	private GridRepresentation gridRepresentation;
	private MainWindow mainWindow;
	private int currentGridSize;
	private long stepSize;

	public long getStepSize() {
		return stepSize;
	}

	private Controller(int size, long stepSize) {
		this.currentGridSize = size;
		this.stepSize = stepSize;
		mainWindow = new MainWindow(this);
		this.gridRepresentation = new GridRepresentation(currentGridSize, mainWindow);
	}

	public int getCurrentGridSize() {
		return currentGridSize;
	}

	public static void main(String[] args) {
		// initializes the program with a 10x10 Grid and a time between animations of
		// 1000ms
		new Controller(10, 100);
	}

	public void applyChanges(int size, long stepSize, ArrayList<GridCell> cellsToUpdate) {
		if (size != 0) {
			this.currentGridSize = size;
		}
		if (stepSize != 0) {
			this.stepSize = stepSize;
		}

		gridRepresentation.updateGridRepresentation(currentGridSize, cellsToUpdate);
	}

	public Grid getCurrentGrid() {
		return gridRepresentation.getGridForAnimation();
	}
}
