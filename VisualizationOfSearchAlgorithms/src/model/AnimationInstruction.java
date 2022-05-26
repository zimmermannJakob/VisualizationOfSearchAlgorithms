package model;

import java.util.ArrayList;

public class AnimationInstruction {

	private ArrayList<GridCell> cellsToRedraw;
	private String message;
	
	public AnimationInstruction(ArrayList<GridCell> cellsToRedraw, String message) {
		this.cellsToRedraw = cellsToRedraw;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public ArrayList<GridCell> getCellsToRedraw() {
		return cellsToRedraw;
	}

}
