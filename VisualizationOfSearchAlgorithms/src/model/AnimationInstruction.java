package model;

import java.util.ArrayList;

public class AnimationInstruction {

	private ArrayList<GridCell> cellsToRedraw;
	
	public void getCellsToRedraw(ArrayList<GridCell> cellsToRedraw) {
		this.cellsToRedraw = cellsToRedraw;
	}

	public ArrayList<GridCell> getCellsToRedraw() {
		return cellsToRedraw;
	}

}
