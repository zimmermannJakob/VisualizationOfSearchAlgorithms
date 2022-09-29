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
		// 100ms
		new Controller(10, 100);
	}

	public void applyChanges(int size, long stepSize, ArrayList<GridCell> cellsToUpdate) {
		if (size != -1) {
			this.currentGridSize = size;
		}
		if (stepSize != -1) {
			accessStepSize(stepSize);
		}
		if(cellsToUpdate != null) {
			gridRepresentation.updateGridRepresentation(currentGridSize, cellsToUpdate);
		}
		
	}
	
	public synchronized long accessStepSize(long stepSize) {
		//if stepSize is -1 this method acts as a getter
		if(stepSize == -1) {
			return this.stepSize;
		}
		
		//if stepSize is != to -1 this method acts as a setter returning -1
		this.stepSize = stepSize;
		return -1;
		
	}
	
	public void createNewGridRepresentation() {
		this.gridRepresentation = new GridRepresentation(currentGridSize, mainWindow);
	}

	public Grid getCurrentGrid() {
		return gridRepresentation.getGridForAnimation();
	}
}
