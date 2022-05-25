package controller;

import java.util.ArrayList;

import model.GridCell;
import model.GridRepresentation;

public class Controller {
	private GridRepresentation gridRepresentation;
	private int currentGridSize;
	private long stepSize;

	private Controller(int size, long stepSize) {
		this.currentGridSize = size;
		this.stepSize = stepSize;
		this.gridRepresentation = new GridRepresentation(currentGridSize);
	}

	public static void main(String[] args) {
		// initializes the program with a 10x10 Grid and a time between animations of
		// 1000ms
		new Controller(10, 1000);
	}

	public void applyChanges(int size, long stepSize, ArrayList<GridCell> cellsToUpdate) {
		this.currentGridSize = size;
		this.stepSize = stepSize;
		gridRepresentation.updateGridRepresentation(currentGridSize, cellsToUpdate);
	}

}
